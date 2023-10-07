/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.internal.user.manager;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.osgi.util.configuration.ConfigurationFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.scim.client.configuration.SCIMClientOAuth2ApplicationConfiguration;
import com.liferay.scim.client.util.SCIMClientUtil;
import com.liferay.scim.user.manager.SCIMUser;
import com.liferay.scim.user.manager.SCIMUserManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = SCIMUserManager.class)
public class SCIMUserManagerImpl implements SCIMUserManager {

	@Override
	public SCIMUser addOrUpdateSCIMUser(SCIMUser scimUser)
		throws PortalException {

		Company company = _companyLocalService.getCompany(
			scimUser.getCompanyId());

		SCIMClientOAuth2ApplicationConfiguration
			scimClientOAuth2ApplicationConfiguration =
				_getSCIMClientOAuth2ApplicationConfiguration(
					company.getCompanyId());

		User user = _fetchUser(
			scimClientOAuth2ApplicationConfiguration, scimUser);

		Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

		birthdayCal.setTime(scimUser.getBirthday());

		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DAY_OF_MONTH);
		int birthdayYear = birthdayCal.get(Calendar.YEAR);

		if (user == null) {
			user = _addUser(
				birthdayMonth, birthdayDay, birthdayYear,
				scimClientOAuth2ApplicationConfiguration, scimUser);
		}
		else {
			user = _updateUser(
				birthdayMonth, birthdayDay, birthdayYear, user, scimUser,
				scimClientOAuth2ApplicationConfiguration);
		}

		return _toSCIMUser(user);
	}

	@Override
	public void deleteSCIMUser(long companyId, long userId)
		throws PortalException {

		SCIMUser scimUser = fetchSCIMUser(companyId, userId);

		if (scimUser == null) {
			return;
		}

		_userLocalService.updateStatus(
			userId, WorkflowConstants.STATUS_INACTIVE, new ServiceContext());
	}

	@Override
	public SCIMUser fetchSCIMUser(long companyId, long userId) {
		User user = _userLocalService.fetchUserById(userId);

		if (user == null) {
			return null;
		}

		SCIMClientOAuth2ApplicationConfiguration
			scimClientOAuth2ApplicationConfiguration =
				_getSCIMClientOAuth2ApplicationConfiguration(companyId);

		String scimClientId = SCIMClientUtil.generateSCIMClientId(
			scimClientOAuth2ApplicationConfiguration.applicationName());

		if (!Objects.equals(_getSCIMClientId(user), scimClientId)) {
			return null;
		}

		return _toSCIMUser(user);
	}

	private User _addUser(
			int birthdayMonth, int birthdayDay, int birthdayYear,
			SCIMClientOAuth2ApplicationConfiguration
				scimClientOAuth2ApplicationConfiguration,
			SCIMUser scimUser)
		throws PortalException {

		User user = _userLocalService.addUser(
			scimUser.getCreatorUserId(), scimUser.getCompanyId(),
			scimUser.isAutoPassword(), scimUser.getPassword(),
			scimUser.getPassword(), scimUser.isAutoScreenName(),
			scimUser.getScreenName(), scimUser.getEmailAddress(),
			scimUser.getLocale(), scimUser.getFirstName(),
			scimUser.getMiddleName(), scimUser.getLastName(), 0, 0,
			scimUser.isMale(), birthdayMonth, birthdayDay, birthdayYear,
			StringPool.BLANK, UserConstants.TYPE_REGULAR,
			scimUser.getGroupIds(), scimUser.getOrganizationIds(),
			scimUser.getRoleIds(), scimUser.getUserGroupIds(),
			scimUser.isSendEmail(), new ServiceContext());

		user.setExternalReferenceCode(scimUser.getExternalReferenceCode());

		user = _userLocalService.updateUser(user);

		user = _userLocalService.updateEmailAddressVerified(
			user.getUserId(), true);

		_saveSCIMClientId(
			SCIMClientUtil.generateSCIMClientId(
				scimClientOAuth2ApplicationConfiguration.applicationName()),
			user);

		return user;
	}

	private void _checkSCIMClientId(
			String userSCIMClientId, String scimClientId)
		throws PortalException {

		if (Validator.isNotNull(userSCIMClientId) &&
			!Objects.equals(userSCIMClientId, scimClientId)) {

			throw new PortalException(
				"User was provisioned by other SCIM client");
		}
	}

	private User _fetchUser(
		SCIMClientOAuth2ApplicationConfiguration
			scimClientOAuth2ApplicationConfiguration,
		SCIMUser scimUser) {

		User portalUser = _userLocalService.fetchUserByExternalReferenceCode(
			scimUser.getExternalReferenceCode(), scimUser.getCompanyId());

		if (portalUser != null) {
			return portalUser;
		}

		if (Objects.equals(
				scimClientOAuth2ApplicationConfiguration.matcherField(),
				_USER_SYNC_MATCHER_FIELD_EA)) {

			return _userLocalService.fetchUserByEmailAddress(
				scimUser.getCompanyId(), scimUser.getEmailAddress());
		}
		else if (Objects.equals(
					scimClientOAuth2ApplicationConfiguration.matcherField(),
					_USER_SYNC_MATCHER_FIELD_UN)) {

			return _userLocalService.fetchUserByScreenName(
				scimUser.getCompanyId(), scimUser.getScreenName());
		}

		return null;
	}

	private String _getSCIMClientId(User user) {
		ExpandoTable expandoTable = _expandoTableLocalService.fetchTable(
			user.getCompanyId(),
			_classNameLocalService.getClassNameId(User.class.getName()),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		if (expandoTable == null) {
			return StringPool.BLANK;
		}

		ExpandoColumn expandoColumn = _expandoColumnLocalService.fetchColumn(
			expandoTable.getTableId(), "scimClientId");

		if (expandoColumn == null) {
			return StringPool.BLANK;
		}

		ExpandoValue expandoValue = _expandoValueLocalService.getValue(
			expandoTable.getTableId(), expandoColumn.getColumnId(),
			user.getUserId());

		if (expandoValue == null) {
			return StringPool.BLANK;
		}

		return expandoValue.getData();
	}

	private SCIMClientOAuth2ApplicationConfiguration
		_getSCIMClientOAuth2ApplicationConfiguration(long companyId) {

		Configuration[] configurations = null;

		try {
			configurations = _configurationAdmin.listConfigurations(
				String.format(
					"(%s=%s*)", Constants.SERVICE_PID,
					SCIMClientOAuth2ApplicationConfiguration.class.getName()));

			if (ArrayUtil.isEmpty(configurations)) {
				return null;
			}

			for (Configuration configuration : configurations) {
				HashMap<String, Object> properties =
					HashMapBuilder.<String, Object>putAll(
						configuration.getProperties()
					).build();

				long configurationCompanyId =
					ConfigurationFactoryUtil.getCompanyId(
						_companyLocalService, properties);

				if (configurationCompanyId == companyId) {
					return ConfigurableUtil.createConfigurable(
						SCIMClientOAuth2ApplicationConfiguration.class,
						properties);
				}
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to get the ",
						"SCIMClientOAuth2ApplicationConfiguration for the ",
						"companyId ", companyId),
					exception);
			}

			throw new SystemException(exception);
		}

		return null;
	}

	private void _saveSCIMClientId(String scimClientId, User user)
		throws PortalException {

		ExpandoTable expandoTable = _expandoTableLocalService.fetchTable(
			user.getCompanyId(),
			_classNameLocalService.getClassNameId(User.class.getName()),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		if (expandoTable == null) {
			expandoTable = _expandoTableLocalService.addTable(
				user.getCompanyId(), User.class.getName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME);
		}

		ExpandoColumn expandoColumn = _expandoColumnLocalService.fetchColumn(
			expandoTable.getTableId(), "scimClientId");

		if (expandoColumn == null) {
			expandoColumn = _expandoColumnLocalService.addColumn(
				expandoTable.getTableId(), "scimClientId",
				ExpandoColumnConstants.STRING);
		}

		_expandoValueLocalService.addValue(
			user.getCompanyId(), User.class.getName(),
			ExpandoTableConstants.DEFAULT_TABLE_NAME, expandoColumn.getName(),
			user.getUserId(), scimClientId);
	}

	private SCIMUser _toSCIMUser(User user) {
		SCIMUser scimUser = new SCIMUser();

		try {
			scimUser.setActive(user.isActive());
			scimUser.setBirthday(user.getBirthday());
			scimUser.setCompanyId(user.getCompanyId());
			scimUser.setCreateDate(user.getCreateDate());
			scimUser.setFirstName(user.getFirstName());
			scimUser.setEmailAddress(user.getEmailAddress());
			scimUser.setExternalReferenceCode(user.getExternalReferenceCode());
			scimUser.setId(String.valueOf(user.getUserId()));
			scimUser.setJobTitle(user.getJobTitle());
			scimUser.setLastName(user.getLastName());
			scimUser.setLocale(user.getLocale());
			scimUser.setMale(user.isMale());
			scimUser.setMiddleName(user.getMiddleName());
			scimUser.setModifiedDate(user.getModifiedDate());
			scimUser.setScreenName(user.getScreenName());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to convert the User to a SCIMUser instance",
					portalException);
			}

			throw new SystemException(portalException);
		}

		return scimUser;
	}

	private User _updateUser(
			int birthdayMonth, int birthdayDay, int birthdayYear, User oldUser,
			SCIMUser scimUser,
			SCIMClientOAuth2ApplicationConfiguration
				scimClientOAuth2ApplicationConfiguration)
		throws PortalException {

		String scimClientId = SCIMClientUtil.generateSCIMClientId(
			scimClientOAuth2ApplicationConfiguration.applicationName());

		String userSCIMClientId = _getSCIMClientId(oldUser);

		_checkSCIMClientId(userSCIMClientId, scimClientId);

		Contact contact = oldUser.getContact();

		User user = _userLocalService.updateUser(
			oldUser.getUserId(), scimUser.getPassword(), StringPool.BLANK,
			StringPool.BLANK, false, oldUser.getReminderQueryQuestion(),
			oldUser.getReminderQueryAnswer(), oldUser.getScreenName(),
			scimUser.getEmailAddress(), false, null, oldUser.getLanguageId(),
			oldUser.getTimeZoneId(), oldUser.getGreeting(),
			oldUser.getComments(), scimUser.getFirstName(),
			scimUser.getMiddleName(), scimUser.getLastName(), 0, 0,
			scimUser.isMale(), birthdayMonth, birthdayDay, birthdayYear,
			contact.getSmsSn(), contact.getFacebookSn(), contact.getJabberSn(),
			contact.getSkypeSn(), contact.getTwitterSn(),
			scimUser.getJobTitle(), oldUser.getGroupIds(),
			oldUser.getOrganizationIds(), oldUser.getRoleIds(), null,
			oldUser.getUserGroupIds(), new ServiceContext());

		if (!Objects.equals(
				user.getExternalReferenceCode(),
				scimUser.getExternalReferenceCode())) {

			user.setExternalReferenceCode(scimUser.getExternalReferenceCode());

			user = _userLocalService.updateUser(user);
		}

		if (user.isActive() != scimUser.isActive()) {
			int status =
				scimUser.isActive() ? WorkflowConstants.STATUS_APPROVED :
					WorkflowConstants.STATUS_INACTIVE;

			user = _userLocalService.updateStatus(
				user.getUserId(), status, new ServiceContext());
		}

		if (Validator.isNull(userSCIMClientId)) {
			_saveSCIMClientId(scimClientId, user);
		}

		return user;
	}

	private static final String _USER_SYNC_MATCHER_FIELD_EA = "emailAddress";

	private static final String _USER_SYNC_MATCHER_FIELD_UN = "userName";

	private static final Log _log = LogFactoryUtil.getLog(
		SCIMUserManagerImpl.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private ExpandoValueLocalService _expandoValueLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
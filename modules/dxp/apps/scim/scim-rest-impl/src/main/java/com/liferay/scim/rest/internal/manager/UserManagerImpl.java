/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.manager;

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
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.scim.client.configuration.ScimClientOAuth2ApplicationConfiguration;
import com.liferay.scim.client.util.ScimClientUtil;
import com.liferay.scim.rest.internal.model.ScimUser;
import com.liferay.scim.rest.internal.util.ScimUserUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import org.wso2.charon3.core.exceptions.BadRequestException;
import org.wso2.charon3.core.exceptions.CharonException;
import org.wso2.charon3.core.exceptions.NotFoundException;
import org.wso2.charon3.core.exceptions.NotImplementedException;
import org.wso2.charon3.core.extensions.UserManager;
import org.wso2.charon3.core.objects.Group;
import org.wso2.charon3.core.objects.User;
import org.wso2.charon3.core.objects.plainobjects.GroupsGetResponse;
import org.wso2.charon3.core.objects.plainobjects.UsersGetResponse;
import org.wso2.charon3.core.utils.codeutils.SearchRequest;

/**
 * @author Rafael Praxedes
 */
public class UserManagerImpl implements UserManager {

	public UserManagerImpl(
		ClassNameLocalService classNameLocalService,
		CompanyLocalService companyLocalService,
		ConfigurationAdmin configurationAdmin,
		ExpandoColumnLocalService expandoColumnLocalService,
		ExpandoTableLocalService expandoTableLocalService,
		ExpandoValueLocalService expandoValueLocalService,
		UserLocalService userLocalService) {

		_classNameLocalService = classNameLocalService;
		_companyLocalService = companyLocalService;
		_configurationAdmin = configurationAdmin;
		_expandoColumnLocalService = expandoColumnLocalService;
		_expandoTableLocalService = expandoTableLocalService;
		_expandoValueLocalService = expandoValueLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	public Group createGroup(
			Group group, Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public User createMe(User user, Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public User createUser(User user, Map<String, Boolean> requiredAttributes)
		throws CharonException {

		return _addOrUpdateUser(user);
	}

	@Override
	public void deleteGroup(String id) throws NotImplementedException {
		throw new NotImplementedException();
	}

	@Override
	public void deleteMe(String userName) throws NotImplementedException {
		throw new NotImplementedException();
	}

	@Override
	public void deleteUser(String userId) throws NotImplementedException {
		throw new NotImplementedException();
	}

	@Override
	public Group getGroup(String id, Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public User getMe(String userName, Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public User getUser(String id, Map<String, Boolean> requiredAttributes)
		throws BadRequestException, CharonException, NotFoundException {

		ScimUser scimUser = _fetchScimUser(
			CompanyThreadLocal.getCompanyId(), GetterUtil.getLong(id));

		if (scimUser == null) {
			throw new NotFoundException("No user found with id : " + id);
		}

		try {
			return ScimUserUtil.toUser(scimUser);
		}
		catch (Exception exception) {
			throw new CharonException(exception.getMessage(), exception);
		}
	}

	@Override
	public GroupsGetResponse listGroupsWithPost(
			SearchRequest searchRequest,
			Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public UsersGetResponse listUsersWithPost(
			SearchRequest searchRequest,
			Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public Group updateGroup(
			Group oldGroup, Group newGroup,
			Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public User updateMe(
			User updatedUser, Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public User updateUser(User user, Map<String, Boolean> requiredAttributes)
		throws CharonException {

		return _addOrUpdateUser(user);
	}

	private ScimUser _addOrUpdateScimUser(ScimUser scimUser)
		throws PortalException {

		Company company = _companyLocalService.getCompany(
			scimUser.getCompanyId());

		ScimClientOAuth2ApplicationConfiguration
			scimClientOAuth2ApplicationConfiguration =
				_getScimClientOAuth2ApplicationConfiguration(
					company.getCompanyId());

		com.liferay.portal.kernel.model.User user = _fetchUser(
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

		return _toScimUser(user);
	}

	private User _addOrUpdateUser(User user) throws CharonException {
		try {
			Company company = _companyLocalService.getCompany(
				CompanyThreadLocal.getCompanyId());

			ScimUser scimUser = _addOrUpdateScimUser(
				ScimUserUtil.toScimUser(
					company.getCompanyId(), company.getLocale(), user));

			return ScimUserUtil.toUser(scimUser);
		}
		catch (Exception exception) {
			throw new CharonException(
				"Unable to provisioning an portal user for " +
					user.getDisplayName(),
				exception);
		}
	}

	private com.liferay.portal.kernel.model.User _addUser(
			int birthdayMonth, int birthdayDay, int birthdayYear,
			ScimClientOAuth2ApplicationConfiguration
				scimClientOAuth2ApplicationConfiguration,
			ScimUser scimUser)
		throws PortalException {

		com.liferay.portal.kernel.model.User user = _userLocalService.addUser(
			0, scimUser.getCompanyId(), scimUser.isAutoPassword(),
			scimUser.getPassword(), scimUser.getPassword(),
			scimUser.isAutoScreenName(), scimUser.getScreenName(),
			scimUser.getEmailAddress(), scimUser.getLocale(),
			scimUser.getFirstName(), scimUser.getMiddleName(),
			scimUser.getLastName(), 0, 0, scimUser.isMale(), birthdayMonth,
			birthdayDay, birthdayYear, StringPool.BLANK,
			UserConstants.TYPE_REGULAR, scimUser.getGroupIds(),
			scimUser.getOrganizationIds(), scimUser.getRoleIds(),
			scimUser.getUserGroupIds(), scimUser.isSendEmail(),
			new ServiceContext());

		user.setExternalReferenceCode(scimUser.getExternalReferenceCode());

		user = _userLocalService.updateUser(user);

		user = _userLocalService.updateEmailAddressVerified(
			user.getUserId(), true);

		_saveScimClientId(
			ScimClientUtil.generateScimClientId(
				scimClientOAuth2ApplicationConfiguration.applicationName()),
			user);

		return user;
	}

	private void _checkScimClientId(
			String userScimClientId, String scimClientId)
		throws PortalException {

		if (Validator.isNotNull(userScimClientId) &&
			!Objects.equals(userScimClientId, scimClientId)) {

			throw new PortalException(
				"User was provisioned by other SCIM client");
		}
	}

	private ScimUser _fetchScimUser(long companyId, long userId) {
		com.liferay.portal.kernel.model.User user =
			_userLocalService.fetchUserById(userId);

		if (user == null) {
			return null;
		}

		ScimClientOAuth2ApplicationConfiguration
			scimClientOAuth2ApplicationConfiguration =
				_getScimClientOAuth2ApplicationConfiguration(companyId);

		String scimClientId = ScimClientUtil.generateScimClientId(
			scimClientOAuth2ApplicationConfiguration.applicationName());

		if (!Objects.equals(_getScimClientId(user), scimClientId)) {
			return null;
		}

		return _toScimUser(user);
	}

	private com.liferay.portal.kernel.model.User _fetchUser(
		ScimClientOAuth2ApplicationConfiguration
			scimClientOAuth2ApplicationConfiguration,
		ScimUser scimUser) {

		com.liferay.portal.kernel.model.User portalUser =
			_userLocalService.fetchUserByExternalReferenceCode(
				scimUser.getExternalReferenceCode(), scimUser.getCompanyId());

		if (portalUser != null) {
			return portalUser;
		}

		if (Objects.equals(
				scimClientOAuth2ApplicationConfiguration.matcherField(),
				"emailAddress")) {

			return _userLocalService.fetchUserByEmailAddress(
				scimUser.getCompanyId(), scimUser.getEmailAddress());
		}
		else if (Objects.equals(
					scimClientOAuth2ApplicationConfiguration.matcherField(),
					"userName")) {

			return _userLocalService.fetchUserByScreenName(
				scimUser.getCompanyId(), scimUser.getScreenName());
		}

		return null;
	}

	private String _getScimClientId(com.liferay.portal.kernel.model.User user) {
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

	private ScimClientOAuth2ApplicationConfiguration
		_getScimClientOAuth2ApplicationConfiguration(long companyId) {

		Configuration[] configurations = null;

		try {
			configurations = _configurationAdmin.listConfigurations(
				String.format(
					"(%s=%s*)", Constants.SERVICE_PID,
					ScimClientOAuth2ApplicationConfiguration.class.getName()));

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
						ScimClientOAuth2ApplicationConfiguration.class,
						properties);
				}
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to get the ",
						"ScimClientOAuth2ApplicationConfiguration for the ",
						"companyId ", companyId),
					exception);
			}

			throw new SystemException(exception);
		}

		return null;
	}

	private void _saveScimClientId(
			String scimClientId, com.liferay.portal.kernel.model.User user)
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

	private ScimUser _toScimUser(com.liferay.portal.kernel.model.User user) {
		ScimUser scimUser = new ScimUser();

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
					"Unable to convert the User to a ScimUser instance",
					portalException);
			}

			throw new SystemException(portalException);
		}

		return scimUser;
	}

	private com.liferay.portal.kernel.model.User _updateUser(
			int birthdayMonth, int birthdayDay, int birthdayYear,
			com.liferay.portal.kernel.model.User oldUser, ScimUser scimUser,
			ScimClientOAuth2ApplicationConfiguration
				scimClientOAuth2ApplicationConfiguration)
		throws PortalException {

		String scimClientId = ScimClientUtil.generateScimClientId(
			scimClientOAuth2ApplicationConfiguration.applicationName());

		String userScimClientId = _getScimClientId(oldUser);

		_checkScimClientId(userScimClientId, scimClientId);

		Contact contact = oldUser.getContact();

		com.liferay.portal.kernel.model.User user =
			_userLocalService.updateUser(
				oldUser.getUserId(), scimUser.getPassword(), StringPool.BLANK,
				StringPool.BLANK, false, oldUser.getReminderQueryQuestion(),
				oldUser.getReminderQueryAnswer(), oldUser.getScreenName(),
				scimUser.getEmailAddress(), false, null,
				oldUser.getLanguageId(), oldUser.getTimeZoneId(),
				oldUser.getGreeting(), oldUser.getComments(),
				scimUser.getFirstName(), scimUser.getMiddleName(),
				scimUser.getLastName(), 0, 0, scimUser.isMale(), birthdayMonth,
				birthdayDay, birthdayYear, contact.getSmsSn(),
				contact.getFacebookSn(), contact.getJabberSn(),
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

		if (Validator.isNull(userScimClientId)) {
			_saveScimClientId(scimClientId, user);
		}

		return user;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserManagerImpl.class);

	private final ClassNameLocalService _classNameLocalService;
	private final CompanyLocalService _companyLocalService;
	private final ConfigurationAdmin _configurationAdmin;
	private final ExpandoColumnLocalService _expandoColumnLocalService;
	private final ExpandoTableLocalService _expandoTableLocalService;
	private final ExpandoValueLocalService _expandoValueLocalService;
	private final UserLocalService _userLocalService;

}
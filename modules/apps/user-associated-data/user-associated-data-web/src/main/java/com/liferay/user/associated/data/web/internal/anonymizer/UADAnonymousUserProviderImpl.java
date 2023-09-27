/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.anonymizer;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PasswordPolicyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.pwd.PwdToolkitUtil;
import com.liferay.user.associated.data.anonymizer.UADAnonymousUserProvider;
import com.liferay.user.associated.data.web.internal.configuration.AnonymousUserConfiguration;
import com.liferay.user.associated.data.web.internal.configuration.AnonymousUserConfigurationRetriever;

import java.util.Calendar;
import java.util.Dictionary;
import java.util.Locale;

import org.osgi.service.cm.Configuration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 * @author Erick Monteiro
 */
@Component(service = UADAnonymousUserProvider.class)
public class UADAnonymousUserProviderImpl implements UADAnonymousUserProvider {

	@Override
	public User getAnonymousUser(long companyId) throws Exception {
		return _getAnonymousUser(companyId);
	}

	@Override
	public boolean isAnonymousUser(User user) {
		try {
			User anonymousUser = getAnonymousUser(user.getCompanyId());

			if (user.getUserId() == anonymousUser.getUserId()) {
				return true;
			}

			return false;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return false;
		}
	}

	private User _createAnonymousUser(long companyId) throws Exception {
		long creatorUserId = 0;

		PasswordPolicy passwordPolicy =
			_passwordPolicyLocalService.getDefaultPasswordPolicy(companyId);

		String randomString = PwdToolkitUtil.generate(passwordPolicy);

		long counter = _counterLocalService.increment(
			UADAnonymousUserProvider.class.getName());

		String screenName = StringBundler.concat(
			"Anonymous", companyId, StringPool.UNDERLINE, counter);

		Company company = _companyLocalService.getCompany(companyId);

		String emailAddress = StringBundler.concat(
			screenName, StringPool.AT, company.getMx());

		Locale locale = LocaleThreadLocal.getDefaultLocale();
		String firstName = "Anonymous";
		String middleName = StringPool.BLANK;
		String lastName = "Anonymous";
		long prefixListTypeId = 0;
		long suffixListTypeId = 0;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;

		User anonymousUser = _userLocalService.addUser(
			creatorUserId, companyId, false, randomString, randomString, false,
			screenName, emailAddress, locale, firstName, middleName, lastName,
			prefixListTypeId, suffixListTypeId, true, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, UserConstants.TYPE_REGULAR,
			null, null, null, null, false, null);

		anonymousUser.setComments(
			StringBundler.concat(
				"This user is automatically created by the UAD application. ",
				"Application data anonymized by Personal Data Erasure will be ",
				"assigned to this user."));

		anonymousUser = _userLocalService.updateUser(anonymousUser);

		_userLocalService.updateStatus(
			anonymousUser.getUserId(), WorkflowConstants.STATUS_INACTIVE,
			new ServiceContext());

		return anonymousUser;
	}

	private User _getAnonymousUser(long companyId) throws Exception {
		Configuration configuration = _anonymousUserConfigurationRetriever.get(
			companyId);

		if (configuration == null) {
			User anonymousUser = _createAnonymousUser(companyId);

			_configurationProvider.saveCompanyConfiguration(
				AnonymousUserConfiguration.class, companyId,
				HashMapDictionaryBuilder.<String, Object>put(
					"companyId", companyId
				).put(
					"userId", anonymousUser.getUserId()
				).build());

			return anonymousUser;
		}

		Dictionary<String, Object> properties = configuration.getProperties();

		AnonymousUserConfiguration anonymousUserConfiguration =
			ConfigurableUtil.createConfigurable(
				AnonymousUserConfiguration.class, properties);

		User anonymousUser = _userLocalService.fetchUser(
			anonymousUserConfiguration.userId());

		if (anonymousUser != null) {
			return anonymousUser;
		}

		anonymousUser = _createAnonymousUser(companyId);

		properties.put("userId", anonymousUser.getUserId());

		configuration.update(properties);

		return anonymousUser;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UADAnonymousUserProviderImpl.class);

	@Reference
	private AnonymousUserConfigurationRetriever
		_anonymousUserConfigurationRetriever;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private PasswordPolicyLocalService _passwordPolicyLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
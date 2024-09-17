/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.configuration.web.internal.scheduler;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.scim.configuration.web.internal.constants.ScimWebKeys;

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro Saugar
 */
@Component(service = SchedulerJobConfiguration.class)
public class ScimNotificationSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	public static final int[] NOTIFICATION_DAYS = {30, 10, 1, -1};

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> _companyLocalService.forEachCompany(
			company -> _notify(company));
	}

	public OrderByComparator<OAuth2Authorization> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"OAuth2Authorization", "accessTokenExpirationDate", "asc");
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(1, TimeUnit.DAY);
	}

	public boolean hasToSendNotification(
		Date oAuth2AccessTokenExpirationDate, Date lastNotificationDate) {

		long currentTime = System.currentTimeMillis();

		long daysUntilExpiry = java.util.concurrent.TimeUnit.DAYS.convert(
			oAuth2AccessTokenExpirationDate.getTime() - currentTime,
			java.util.concurrent.TimeUnit.MILLISECONDS);

		long daysSinceLastNotification =
			java.util.concurrent.TimeUnit.DAYS.convert(
				oAuth2AccessTokenExpirationDate.getTime() -
					lastNotificationDate.getTime(),
				java.util.concurrent.TimeUnit.MILLISECONDS);

		for (int notificationDay : NOTIFICATION_DAYS) {
			if ((notificationDay >= daysUntilExpiry) &&
				(daysSinceLastNotification > notificationDay)) {

				return true;
			}
		}

		return false;
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	private String _generateBody(String strAccessTokenExpirationDate) {
		String body = StringPool.BLANK;

		try {
			body = StringUtil.read(
				getClassLoader(),
				"com/liferay/scim/configuration/web/internal/dependencies" +
				"/body.tmpl");

			body = StringUtil.replace(
				body, new String[] {"[$DATE_EXPIRATION_ACCESS_TOKEN$]"},
				new String[] {strAccessTokenExpirationDate});
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return body;
	}

	private boolean _isEnabled() {
		if (FeatureFlagManagerUtil.isEnabled("LPS-96845")) {
			return true;
		}

		return false;
	}

	private void _notify(Company company) {
		if (!_isEnabled() || !company.isActive()) {
			return;
		}

		_sendNotification(company.getCompanyId());
	}

	private void _sendNotification(long companyId) {
		List<OAuth2Application> oAuth2Applications = null;

		try {
			oAuth2Applications =
				_oAuth2ApplicationLocalService.getOAuth2Applications(companyId);

			for (OAuth2Application oAuth2Application : oAuth2Applications) {
				if (oAuth2Application.getClientId(
				).startsWith(
					_SCIM_CLIENT_ID_PREFIX
				)) {

					List<OAuth2Authorization> applicationOAuth2Authorizations =
						_oAuth2AuthorizationLocalService.
							getOAuth2Authorizations(
								oAuth2Application.getOAuth2ApplicationId(),
								QueryUtil.ALL_POS, QueryUtil.ALL_POS,
								getOrderByComparator());

					if ((applicationOAuth2Authorizations != null) &&
						!applicationOAuth2Authorizations.isEmpty()) {

						OAuth2Authorization applicationOAuth2Authorization =
							applicationOAuth2Authorizations.get(0);

						Date accessTokenExpirationDate =
							applicationOAuth2Authorization.
								getAccessTokenExpirationDate();

						ExpandoBridge expandoBridge =
							applicationOAuth2Authorization.getExpandoBridge();

						if (hasToSendNotification(
								accessTokenExpirationDate,
								(Date)expandoBridge.getAttribute(
									"lastSuccessfulNotificationDate", false))) {

							_sendNotificationExpirationToken(
								companyId, accessTokenExpirationDate);

							expandoBridge.setAttribute(
								"lastSuccessfulNotificationDate", new Date(),
								false);
						}
					}
				}
			}
		}
		catch (Exception exception) {
			_log.fatal(exception);

			if (_log.isDebugEnabled()) {
				throw new RuntimeException(exception);
			}
		}
	}

	private void _sendNotificationExpirationToken(
		long companyId, Date accessTokenExpirationDate)
		throws Exception {

		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		List<User> users = _userLocalService.getRoleUsers(role.getRoleId());

		users = ListUtil.filter(
			users,
			user -> {
				if (user.getType() ==
						UserConstants.TYPE_DEFAULT_SERVICE_ACCOUNT) {

					return false;
				}

				return true;
			});

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

		String strAccessTokenExpirationDate = formatter.format(
			accessTokenExpirationDate);

		Company company = _companyLocalService.getCompany(companyId);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", company.getLocale(), getClass());

		String subject = _language.get(resourceBundle, "scim-email-subject");

		String body = _generateBody(strAccessTokenExpirationDate);

		String defaultEmailFromAddress = "scim-notification@" + company.getMx();
		String defaultEmailFromName = "SCIM-Notification";

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setPortletId(ScimWebKeys.SCIM_CONFIGURATION);

		subscriptionSender.setEntryTitle(body);

		subscriptionSender.setNotificationType(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY);
		subscriptionSender.setBody(body);

		subscriptionSender.setFrom(
			defaultEmailFromAddress, defaultEmailFromName);

		for (int i = 0; i < users.size(); i++) {
			subscriptionSender.addRuntimeSubscribers(
				users.get(
					i
				).getEmailAddress(),
				users.get(
					i
				).getFullName());
		}

		subscriptionSender.setMailId("popPortletPrefix", "ids");
		subscriptionSender.setSubject(subject);

		subscriptionSender.flushNotifications();
	}

	private static final String _SCIM_CLIENT_ID_PREFIX = "SCIM_";

	private static final Log _log = LogFactoryUtil.getLog(
		ScimNotificationSchedulerJobConfiguration.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Language _language;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
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
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.Time;
import com.liferay.scim.configuration.web.internal.constants.ScimWebKeys;
import com.liferay.scim.rest.util.ScimClientUtil;

import java.text.Format;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro Saugar
 */
@Component(service = SchedulerJobConfiguration.class)
public class ScimNotificationSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> _companyLocalService.forEachCompany(
			company -> {
				if (!company.isActive()) {
					return;
				}

				for (OAuth2Application oAuth2Application :
						_oAuth2ApplicationLocalService.getOAuth2Applications(
							company.getCompanyId())) {

					_sendNotification(oAuth2Application);
				}
			});
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(1, TimeUnit.DAY);
	}

	protected boolean isSendNotification(
		Date accessTokenExpirationDate, long currentTime,
		Date lastNotificationDate) {

		long lastNotificationValidThresholdTime =
			accessTokenExpirationDate.getTime() -
				lastNotificationDate.getTime();
		long remainingAccessTokenTime =
			accessTokenExpirationDate.getTime() - currentTime;

		if (_isSendNotification(
				lastNotificationValidThresholdTime, 30 * Time.DAY,
				remainingAccessTokenTime) ||
			_isSendNotification(
				lastNotificationValidThresholdTime, 10 * Time.DAY,
				remainingAccessTokenTime) ||
			_isSendNotification(
				lastNotificationValidThresholdTime, Time.DAY,
				remainingAccessTokenTime) ||
			_isSendNotification(
				lastNotificationValidThresholdTime, 0,
				remainingAccessTokenTime)) {

			return true;
		}

		return false;
	}

	private boolean _isSendNotification(
		long lastNotificationThresholdTime, long notificationValidThresholdTime,
		long remainingAccessTokenTime) {

		if ((notificationValidThresholdTime >= remainingAccessTokenTime) &&
			(lastNotificationThresholdTime > notificationValidThresholdTime)) {

			return true;
		}

		return false;
	}

	private void _sendNotification(
			Date accessTokenExpirationDate, long companyId)
		throws Exception {

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		ListUtil.filter(
			_userLocalService.getRoleUsers(role.getRoleId()),
			user -> !user.isServiceAccountUser()
		).forEach(
			user -> subscriptionSender.addRuntimeSubscribers(
				user.getEmailAddress(), user.getFullName())
		);

		String body =
			"The access token for the SCIM client will expire on " +
				_format.format(accessTokenExpirationDate);

		subscriptionSender.setBody(body);
		subscriptionSender.setEntryTitle(body);

		Company company = _companyLocalService.getCompany(companyId);

		subscriptionSender.setFrom(
			"scim-notification@" + company.getMx(), "SCIM-Notification");

		subscriptionSender.setMailId("popPortletPrefix", "ids");
		subscriptionSender.setNotificationType(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY);
		subscriptionSender.setPortletId(ScimWebKeys.SCIM_CONFIGURATION);
		subscriptionSender.setSubject(
			_language.get(
				ResourceBundleUtil.getBundle(
					"content.Language", company.getLocale(), getClass()),
				"scim-access-token-email-subject"));

		subscriptionSender.flushNotifications();
	}

	private void _sendNotification(OAuth2Application oAuth2Application) {
		if (!Objects.equals(
				ScimClientUtil.generateScimClientId(
					oAuth2Application.getName()),
				oAuth2Application.getClientId())) {

			return;
		}

		List<OAuth2Authorization> oAuth2Authorizations =
			_oAuth2AuthorizationLocalService.getOAuth2Authorizations(
				oAuth2Application.getOAuth2ApplicationId(), 0, 1,
				OrderByComparatorFactoryUtil.create(
					"OAuth2Authorization", "accessTokenExpirationDate", "asc"));

		if (ListUtil.isEmpty(oAuth2Authorizations)) {
			return;
		}

		OAuth2Authorization oAuth2Authorization = oAuth2Authorizations.get(0);

		Date accessTokenExpirationDate =
			oAuth2Authorization.getAccessTokenExpirationDate();
		ExpandoBridge expandoBridge = oAuth2Authorization.getExpandoBridge();

		if (!isSendNotification(
				accessTokenExpirationDate, System.currentTimeMillis(),
				(Date)expandoBridge.getAttribute(
					"lastNotificationDate", false))) {

			return;
		}

		try {
			_sendNotification(
				accessTokenExpirationDate, oAuth2Application.getCompanyId());

			expandoBridge.setAttribute(
				"lastNotificationDate", new Date(), false);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ScimNotificationSchedulerJobConfiguration.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	private final Format _format =
		FastDateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

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
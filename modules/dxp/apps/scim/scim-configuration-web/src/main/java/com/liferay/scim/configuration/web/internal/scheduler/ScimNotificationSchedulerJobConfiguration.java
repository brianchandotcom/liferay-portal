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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.Time;
import com.liferay.scim.configuration.web.internal.constants.ScimWebKeys;
import com.liferay.scim.rest.util.ScimClientUtil;

import java.io.IOException;

import java.text.Format;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
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
		return () -> _companyLocalService.forEachCompany(this::_process);
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
		Date lastNotificationDate, Date oAuth2AccessTokenExpirationDate) {

		return hasToSendNotification(
			System.currentTimeMillis(), lastNotificationDate,
			oAuth2AccessTokenExpirationDate);
	}

	@Activate
	protected void activate() throws IOException {
		_body = StringUtil.read(getClass(), "dependencies/body.tmpl");
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	protected boolean hasToSendNotification(
		long currentTime, Date lastNotificationDate,
		Date oAuth2AccessTokenExpirationDate) {

		long toExpiryMillis =
			oAuth2AccessTokenExpirationDate.getTime() - currentTime;

		long toExpiryAtLastNotificationMillis =
			oAuth2AccessTokenExpirationDate.getTime() -
				lastNotificationDate.getTime();

		for (long notificationDurationMillis : _NOTIFICATION_DURATION_MILLIS) {
			if ((notificationDurationMillis >= toExpiryMillis) &&
				(toExpiryAtLastNotificationMillis >
					notificationDurationMillis)) {

				return true;
			}
		}

		return false;
	}

	private boolean _isEnabled() {
		if (FeatureFlagManagerUtil.isEnabled("LPS-96845")) {
			return true;
		}

		return false;
	}

	private void _process(Company company) {
		if (!_isEnabled() || !company.isActive()) {
			return;
		}

		for (OAuth2Application oAuth2Application :
				_oAuth2ApplicationLocalService.getOAuth2Applications(
					company.getCompanyId())) {

			if (!Objects.equals(
					ScimClientUtil.generateScimClientId(
						oAuth2Application.getName()),
					oAuth2Application.getClientId())) {

				continue;
			}

			List<OAuth2Authorization> applicationOAuth2Authorizations =
				_oAuth2AuthorizationLocalService.getOAuth2Authorizations(
					oAuth2Application.getOAuth2ApplicationId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					getOrderByComparator());

			if (ListUtil.isEmpty(applicationOAuth2Authorizations)) {
				continue;
			}

			OAuth2Authorization applicationOAuth2Authorization =
				applicationOAuth2Authorizations.get(0);

			Date accessTokenExpirationDate =
				applicationOAuth2Authorization.getAccessTokenExpirationDate();

			ExpandoBridge expandoBridge =
				applicationOAuth2Authorization.getExpandoBridge();

			if (hasToSendNotification(
					(Date)expandoBridge.getAttribute(
						"lastSuccessfulNotificationDate", false),
					accessTokenExpirationDate)) {

				try {
					_sendNotification(
						accessTokenExpirationDate, company.getCompanyId());

					expandoBridge.setAttribute(
						"lastSuccessfulNotificationDate", new Date(), false);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception);
					}
				}
			}
		}
	}

	private void _sendNotification(
			Date accessTokenExpirationDate, long companyId)
		throws Exception {

		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		ListUtil.filter(
			_userLocalService.getRoleUsers(role.getRoleId()),
			user -> !user.isServiceAccountUser()
		).forEach(
			user -> subscriptionSender.addRuntimeSubscribers(
				user.getEmailAddress(), user.getFullName())
		);

		String body = StringUtil.replace(
			_body, new String[] {"[$ACCESS_TOKEN_EXPIRATION_DATE$]"},
			new String[] {_format.format(accessTokenExpirationDate)});

		Company company = _companyLocalService.getCompany(companyId);

		subscriptionSender.setBody(body);
		subscriptionSender.setEntryTitle(body);
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

	private static final long[] _NOTIFICATION_DURATION_MILLIS = {
		30 * Time.DAY, 10 * Time.DAY, Time.DAY, 0
	};

	private static final Log _log = LogFactoryUtil.getLog(
		ScimNotificationSchedulerJobConfiguration.class);

	private String _body;

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
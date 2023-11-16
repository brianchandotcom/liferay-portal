/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.configuration.web.internal.scheduler;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.scim.configuration.web.internal.constants.ScimWebKeys;

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.mail.internet.InternetAddress;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro Saugar
 */
@Component(service = SchedulerJobConfiguration.class)
public class ScimNotificationSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	public static final int DAY = 1;

	public static final int MONTH = 30;

	public static final int WEEK = 7;

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
		return TriggerConfiguration.createTriggerConfiguration(
			1, TimeUnit.MINUTE);
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

	private void _notify(Company company) {
		if (!company.isActive()) {
			return;
		}

		_sendNotification(company.getCompanyId());
	}

	private void _sendEmail(
			Company company, String subject, String body, List<User> users)
		throws Exception {

		String defaultEmailFromAddress = "scim-notification@" + company.getMx();
		String defaultEmailFromName = "SCIM-Notification";

		InternetAddress from = new InternetAddress(
			defaultEmailFromName, defaultEmailFromAddress);

		List<InternetAddress> bcc = TransformUtil.transform(
			users,
			user -> {
				InternetAddress internetAddress = null;

				try {
					internetAddress = new InternetAddress(
						user.getEmailAddress(), user.getFullName());
				}
				catch (Exception exception) {
					_log.error(exception);
				}

				return internetAddress;
			});

		MailMessage mailMessage = new MailMessage(from, subject, body, true);

		mailMessage.setBCC(
			(InternetAddress[])bcc.toArray(new InternetAddress[0]));

		_mailService.sendEmail(mailMessage);
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

						int daysBetween = DateUtil.getDaysBetween(
							new Date(), accessTokenExpirationDate);

						if ((daysBetween == MONTH) || (daysBetween == WEEK) ||
							(daysBetween == DAY)) {

							Role role = _roleLocalService.getRole(
								companyId, RoleConstants.ADMINISTRATOR);

							List<User> users = _userLocalService.getRoleUsers(
								role.getRoleId());

							SimpleDateFormat formatter = new SimpleDateFormat(
								"dd-MMM-yyyy");

							String strAccessTokenExpirationDate =
								formatter.format(accessTokenExpirationDate);

							Company company = _companyLocalService.getCompany(
								companyId);

							ResourceBundle resourceBundle =
								ResourceBundleUtil.getBundle(
									"content.Language", company.getLocale(),
									getClass());

							String subject = _language.get(
								resourceBundle, "scim-email-subject");

							String body = _generateBody(
								strAccessTokenExpirationDate);

							_sendNotificationEvent(users, body);
							_sendEmail(company, subject, body, users);
						}
					}
				}
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				throw new RuntimeException(exception);
			}
		}
	}

	private void _sendNotificationEvent(List<User> users, String body) {
		try {
			for (User user : users) {
				NotificationEvent notificationEvent = new NotificationEvent(
					System.currentTimeMillis(), ScimWebKeys.SCIM_CONFIGURATION,
					JSONUtil.put("body", body));

				notificationEvent.setDeliveryType(
					UserNotificationDeliveryConstants.TYPE_WEBSITE);

				_userNotificationEventLocalService.addUserNotificationEvent(
					user.getUserId(), notificationEvent);
			}
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private static final String _SCIM_CLIENT_ID_PREFIX = "SCIM_";

	private static final Log _log = LogFactoryUtil.getLog(
		ScimNotificationSchedulerJobConfiguration.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Language _language;

	@Reference
	private MailService _mailService;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}
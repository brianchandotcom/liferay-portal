/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.messaging;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.osb.faro.constants.FaroProjectConstants;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
@Component(service = CheckFaroProjectsMessageListener.class)
public class CheckFaroProjectsMessageListener extends BaseMessageListener {

	@Activate
	protected void activate() {
		try {
			Class<?> clazz = getClass();

			_trigger = _triggerFactory.createTrigger(
				clazz.getName(), clazz.getName(), new Date(), null,
				"0 0/15 * * * ?");

			_schedulerEngineHelper.schedule(
				_trigger, StorageType.PERSISTED, null,
				DestinationNames.SCHEDULER_DISPATCH, null);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Deactivate
	protected void deactivate() {
		try {
			if (_trigger == null) {
				return;
			}

			_schedulerEngineHelper.unschedule(
				_trigger.getJobName(), _trigger.getGroupName(),
				StorageType.PERSISTED);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		Map<FaroProject, Exception> projectExceptions = new HashMap<>();

		for (FaroProject faroProject :
				_faroProjectLocalService.getFaroProjects(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			if (!StringUtil.equals(
					faroProject.getState(), FaroProjectConstants.STATE_READY)) {

				continue;
			}

			try {
				_contactsEngineClient.getIndividuals(
					faroProject, (String)null, true, 0, 0, null);
			}
			catch (Exception exception) {
				_log.error(exception);

				projectExceptions.put(faroProject, exception);
			}

			Thread.sleep(1000);
		}

		if ((_systemsDownStartTime > 0) != projectExceptions.isEmpty()) {
			return;
		}

		if (projectExceptions.isEmpty()) {
			String duration = Time.getDuration(
				System.currentTimeMillis() - _systemsDownStartTime);

			sendEmail(
				"SYSTEM ALERT: All projects up",
				"All projects are up after " + duration + " of downtime.");

			_systemsDownStartTime = 0;
		}
		else {
			StringBundler sb = new StringBundler(7);

			for (Map.Entry<FaroProject, Exception> projectException :
					projectExceptions.entrySet()) {

				FaroProject faroProject = projectException.getKey();

				sb.append(faroProject.getName());

				sb.append(StringPool.NEW_LINE);
				sb.append(faroProject.getWeDeployKey());
				sb.append(StringPool.NEW_LINE);

				Exception exception = projectException.getValue();

				sb.append(exception.getMessage());

				sb.append(StringPool.NEW_LINE);
				sb.append(StringPool.NEW_LINE);
			}

			sendEmail(
				"SYSTEM ALERT: " + projectExceptions.size() + " projects down",
				sb.toString());

			_systemsDownStartTime = System.currentTimeMillis();
		}
	}

	protected void sendEmail(String subject, String body) throws Exception {
		InternetAddress from = new InternetAddress(
			"ac@liferay.com", "Analytics Cloud");

		Role role = _roleLocalService.getRole(
			_portal.getDefaultCompanyId(), RoleConstants.ADMINISTRATOR);

		List<User> users = _userLocalService.getRoleUsers(role.getRoleId());

		List<InternetAddress> bcc = TransformUtil.transform(
			users,
			user -> {
				if (!StringUtil.equals(
						user.getEmailAddress(), "test@liferay.com")) {

					try {
						return new InternetAddress(
							user.getEmailAddress(), user.getFullName());
					}
					catch (Exception exception) {
						_log.error(exception);
					}
				}

				return null;
			});

		MailMessage mailMessage = new MailMessage(from, subject, body, false);

		mailMessage.setBCC((InternetAddress[])bcc.toArray());

		_mailService.sendEmail(mailMessage);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CheckFaroProjectsMessageListener.class);

	private static long _systemsDownStartTime;

	@Reference
	private ContactsEngineClient _contactsEngineClient;

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

	@Reference
	private MailService _mailService;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	private Trigger _trigger;

	@Reference
	private TriggerFactory _triggerFactory;

	@Reference
	private UserLocalService _userLocalService;

}
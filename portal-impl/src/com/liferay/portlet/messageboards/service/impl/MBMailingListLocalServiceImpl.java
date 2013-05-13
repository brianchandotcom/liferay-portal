/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.CronText;
import com.liferay.portal.kernel.scheduler.CronTrigger;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.MailingListEmailAddressException;
import com.liferay.portlet.messageboards.MailingListInServerNameException;
import com.liferay.portlet.messageboards.MailingListInUserNameException;
import com.liferay.portlet.messageboards.MailingListOutEmailAddressException;
import com.liferay.portlet.messageboards.MailingListOutServerNameException;
import com.liferay.portlet.messageboards.MailingListOutUserNameException;
import com.liferay.portlet.messageboards.messaging.MailingListRequest;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.service.base.MBMailingListLocalServiceBaseImpl;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Thiago Moreira
 */
public class MBMailingListLocalServiceImpl
	extends MBMailingListLocalServiceBaseImpl {

	@Override
	public MBMailingList addMailingList(
			long userId, long groupId, long categoryId, String emailAddress,
			String inProtocol, String inServerName, int inServerPort,
			boolean inUseSSL, String inUserName, String inPassword,
			int inReadInterval, String outEmailAddress, boolean outCustom,
			String outServerName, int outServerPort, boolean outUseSSL,
			String outUserName, String outPassword, boolean allowAnonymous,
			boolean active, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Mailing list

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(
			emailAddress, inServerName, inUserName, outEmailAddress, outCustom,
			outServerName, outUserName, active);

		long mailingListId = counterLocalService.increment();

		MBMailingList mailingList = mbMailingListPersistence.create(
			mailingListId);

		mailingList.setUuid(serviceContext.getUuid());
		mailingList.setGroupId(groupId);
		mailingList.setCompanyId(user.getCompanyId());
		mailingList.setUserId(user.getUserId());
		mailingList.setUserName(user.getFullName());
		mailingList.setCreateDate(serviceContext.getCreateDate(now));
		mailingList.setModifiedDate(serviceContext.getModifiedDate(now));
		mailingList.setCategoryId(categoryId);
		mailingList.setEmailAddress(emailAddress);
		mailingList.setInProtocol(inUseSSL ? inProtocol + "s" : inProtocol);
		mailingList.setInServerName(inServerName);
		mailingList.setInServerPort(inServerPort);
		mailingList.setInUseSSL(inUseSSL);
		mailingList.setInUserName(inUserName);
		mailingList.setInPassword(inPassword);
		mailingList.setInReadInterval(inReadInterval);
		mailingList.setOutEmailAddress(outEmailAddress);
		mailingList.setOutCustom(outCustom);
		mailingList.setOutServerName(outServerName);
		mailingList.setOutServerPort(outServerPort);
		mailingList.setOutUseSSL(outUseSSL);
		mailingList.setOutUserName(outUserName);
		mailingList.setOutPassword(outPassword);
		mailingList.setAllowAnonymous(allowAnonymous);
		mailingList.setActive(active);

		mbMailingListPersistence.update(mailingList);

		// Scheduler

		if (active) {
			scheduleMailingList(mailingList);
		}

		return mailingList;
	}

	@Override
	public void deleteCategoryMailingList(long groupId, long categoryId)
		throws PortalException, SystemException {

		MBMailingList mailingList = mbMailingListPersistence.findByG_C(
			groupId, categoryId);

		deleteMailingList(mailingList);
	}

	@Override
	public void deleteMailingList(long mailingListId)
		throws PortalException, SystemException {

		MBMailingList mailingList = mbMailingListPersistence.findByPrimaryKey(
			mailingListId);

		deleteMailingList(mailingList);
	}

	@Override
	public void deleteMailingList(MBMailingList mailingList)
		throws PortalException, SystemException {

		unscheduleMailingList(mailingList);

		mbMailingListPersistence.remove(mailingList);
	}

	@Override
	public MBMailingList getCategoryMailingList(long groupId, long categoryId)
		throws PortalException, SystemException {

		return mbMailingListPersistence.findByG_C(groupId, categoryId);
	}

	@Override
	public MBMailingList updateMailingList(
			long mailingListId, String emailAddress, String inProtocol,
			String inServerName, int inServerPort, boolean inUseSSL,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			int outServerPort, boolean outUseSSL, String outUserName,
			String outPassword, boolean allowAnonymous, boolean active,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Mailing list

		validate(
			emailAddress, inServerName, inUserName, outEmailAddress, outCustom,
			outServerName, outUserName, active);

		MBMailingList mailingList = mbMailingListPersistence.findByPrimaryKey(
			mailingListId);

		mailingList.setModifiedDate(serviceContext.getModifiedDate(null));
		mailingList.setEmailAddress(emailAddress);
		mailingList.setInProtocol(inUseSSL ? inProtocol + "s" : inProtocol);
		mailingList.setInServerName(inServerName);
		mailingList.setInServerPort(inServerPort);
		mailingList.setInUseSSL(inUseSSL);
		mailingList.setInUserName(inUserName);
		mailingList.setInPassword(inPassword);
		mailingList.setInReadInterval(inReadInterval);
		mailingList.setOutEmailAddress(outEmailAddress);
		mailingList.setOutCustom(outCustom);
		mailingList.setOutServerName(outServerName);
		mailingList.setOutServerPort(outServerPort);
		mailingList.setOutUseSSL(outUseSSL);
		mailingList.setOutUserName(outUserName);
		mailingList.setOutPassword(outPassword);
		mailingList.setAllowAnonymous(allowAnonymous);
		mailingList.setActive(active);

		mbMailingListPersistence.update(mailingList);

		// Scheduler

		if (active) {
			scheduleMailingList(mailingList);
		}

		return mailingList;
	}

	protected String getSchedulerGroupName(MBMailingList mailingList) {
		return DestinationNames.MESSAGE_BOARDS_MAILING_LIST.concat(
			StringPool.SLASH).concat(
				String.valueOf(mailingList.getMailingListId()));
	}

	protected void scheduleMailingList(MBMailingList mailingList)
		throws PortalException {

		String groupName = getSchedulerGroupName(mailingList);

		Calendar startDate = CalendarFactoryUtil.getCalendar();

		CronText cronText = new CronText(
			startDate, CronText.MINUTELY_FREQUENCY,
			mailingList.getInReadInterval());

		Trigger trigger = new CronTrigger(
			groupName, groupName, startDate.getTime(), null,
			cronText.toString());

		MailingListRequest mailingListRequest = new MailingListRequest();

		mailingListRequest.setCompanyId(mailingList.getCompanyId());
		mailingListRequest.setUserId(mailingList.getUserId());
		mailingListRequest.setGroupId(mailingList.getGroupId());
		mailingListRequest.setCategoryId(mailingList.getCategoryId());
		mailingListRequest.setInProtocol(mailingList.getInProtocol());
		mailingListRequest.setInServerName(mailingList.getInServerName());
		mailingListRequest.setInServerPort(mailingList.getInServerPort());
		mailingListRequest.setInUseSSL(mailingList.getInUseSSL());
		mailingListRequest.setInUserName(mailingList.getInUserName());
		mailingListRequest.setInPassword(mailingList.getInPassword());
		mailingListRequest.setAllowAnonymous(mailingList.getAllowAnonymous());

		SchedulerEngineHelperUtil.schedule(
			trigger, StorageType.PERSISTED, null,
			DestinationNames.MESSAGE_BOARDS_MAILING_LIST, mailingListRequest,
			0);
	}

	protected void unscheduleMailingList(MBMailingList mailingList)
		throws PortalException {

		String groupName = getSchedulerGroupName(mailingList);

		SchedulerEngineHelperUtil.unschedule(groupName, StorageType.PERSISTED);
	}

	protected void validate(
			String emailAddress, String inServerName, String inUserName,
			String outEmailAddress, boolean outCustom, String outServerName,
			String outUserName, boolean active)
		throws PortalException {

		if (!active) {
			return;
		}

		if (!Validator.isEmailAddress(emailAddress)) {
			throw new MailingListEmailAddressException();
		}
		else if (Validator.isNull(inServerName)) {
			throw new MailingListInServerNameException();
		}
		else if (Validator.isNull(inUserName)) {
			throw new MailingListInUserNameException();
		}
		else if (Validator.isNull(outEmailAddress)) {
			throw new MailingListOutEmailAddressException();
		}
		else if (outCustom) {
			if (Validator.isNull(outServerName)) {
				throw new MailingListOutServerNameException();
			}
			else if (Validator.isNull(outUserName)) {
				throw new MailingListOutUserNameException();
			}
		}
	}

}
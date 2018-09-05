/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.message.boards.service.impl;

import com.liferay.message.boards.exception.MailingListEmailAddressException;
import com.liferay.message.boards.exception.MailingListInServerNameException;
import com.liferay.message.boards.exception.MailingListInUserNameException;
import com.liferay.message.boards.exception.MailingListOutEmailAddressException;
import com.liferay.message.boards.exception.MailingListOutServerNameException;
import com.liferay.message.boards.exception.MailingListOutUserNameException;
import com.liferay.message.boards.internal.messaging.MailingListRequest;
import com.liferay.message.boards.model.MBMailingList;
import com.liferay.message.boards.service.base.MBMailingListLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.jabsorb.serializer.LiferayJSONDeserializationWhitelist;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Closeable;
import java.io.IOException;

import java.util.Calendar;

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
		throws PortalException {

		// Message boards mailing list

		User user = _userLocalService.getUser(userId);

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
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		_unregister = _liferayJSONDeserializationWhitelist.register(
			MailingListRequest.class.getName());
	}

	@Override
	public void deleteCategoryMailingList(long groupId, long categoryId)
		throws PortalException {

		MBMailingList mailingList = mbMailingListPersistence.findByG_C(
			groupId, categoryId);

		deleteMailingList(mailingList);
	}

	@Override
	public void deleteMailingList(long mailingListId) throws PortalException {
		MBMailingList mailingList = mbMailingListPersistence.findByPrimaryKey(
			mailingListId);

		deleteMailingList(mailingList);
	}

	@Override
	public void deleteMailingList(MBMailingList mailingList)
		throws PortalException {

		unscheduleMailingList(mailingList);

		mbMailingListPersistence.remove(mailingList);
	}

	@Override
	public void destroy() {
		super.destroy();

		try {
			_unregister.close();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public MBMailingList fetchCategoryMailingList(
		long groupId, long categoryId) {

		return mbMailingListPersistence.fetchByG_C(groupId, categoryId);
	}

	@Override
	public MBMailingList getCategoryMailingList(long groupId, long categoryId)
		throws PortalException {

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
		throws PortalException {

		// Message boards mailing list

		validate(
			emailAddress, inServerName, inUserName, outEmailAddress, outCustom,
			outServerName, outUserName, active);

		MBMailingList mailingList = mbMailingListPersistence.findByPrimaryKey(
			mailingListId);

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
		else {
			unscheduleMailingList(mailingList);
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

		Trigger trigger = TriggerFactoryUtil.createTrigger(
			groupName, groupName, startDate.getTime(),
			mailingList.getInReadInterval(), TimeUnit.MINUTE);

		MailingListRequest mailingListRequest = new MailingListRequest();

		mailingListRequest.setCompanyId(mailingList.getCompanyId());
		mailingListRequest.setUserId(mailingList.getUserId());
		mailingListRequest.setGroupId(mailingList.getGroupId());
		mailingListRequest.setCategoryId(mailingList.getCategoryId());
		mailingListRequest.setInProtocol(mailingList.getInProtocol());
		mailingListRequest.setInServerName(mailingList.getInServerName());
		mailingListRequest.setInServerPort(mailingList.getInServerPort());
		mailingListRequest.setInUseSSL(mailingList.isInUseSSL());
		mailingListRequest.setInUserName(mailingList.getInUserName());
		mailingListRequest.setInPassword(mailingList.getInPassword());
		mailingListRequest.setAllowAnonymous(mailingList.isAllowAnonymous());

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
			throw new MailingListEmailAddressException(emailAddress);
		}
		else if (Validator.isNull(inServerName)) {
			throw new MailingListInServerNameException(
				"In server name is null");
		}
		else if (Validator.isNull(inUserName)) {
			throw new MailingListInUserNameException("In user name is null");
		}
		else if (Validator.isNull(outEmailAddress)) {
			throw new MailingListOutEmailAddressException(
				"Out email address is null");
		}
		else if (outCustom) {
			if (Validator.isNull(outServerName)) {
				throw new MailingListOutServerNameException(
					"Out server name is null");
			}
			else if (Validator.isNull(outUserName)) {
				throw new MailingListOutUserNameException(
					"Out user name is null");
			}
		}
	}

	@ServiceReference(type = LiferayJSONDeserializationWhitelist.class)
	private LiferayJSONDeserializationWhitelist
		_liferayJSONDeserializationWhitelist;

	private Closeable _unregister;

	@ServiceReference(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}
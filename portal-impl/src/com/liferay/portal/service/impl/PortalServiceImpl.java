/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.sender.DirectSynchronousMessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.impl.ClassNameImpl;
import com.liferay.portal.service.PortalService;
import com.liferay.portal.service.base.PortalServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 */
@JSONWebService(mode = JSONWebServiceMode.MANUAL)
public class PortalServiceImpl extends PortalServiceBaseImpl {

	public String getAutoDeployDirectory() throws SystemException {
		return PrefsPropsUtil.getString(
			PropsKeys.AUTO_DEPLOY_DEPLOY_DIR,
			PropsValues.AUTO_DEPLOY_DEPLOY_DIR);
	}

	@JSONWebService
	public int getBuildNumber() {
		return ReleaseInfo.getBuildNumber();
	}

	public void testAddClassName_Rollback(String classNameValue)
		throws SystemException {

		addClassName(classNameValue);

		throw new SystemException();
	}

	public void testAddClassName_Success(String classNameValue)
		throws SystemException {

		addClassName(classNameValue);
	}

	public void testAddClassNameAndTestTransactionPortletBar_PortalRollback(
			String transactionPortletBarText)
		throws SystemException {

		addClassName(PortalService.class.getName());

		addTransactionPortletBar(transactionPortletBarText, false);

		throw new SystemException();
	}

	public void testAddClassNameAndTestTransactionPortletBar_PortletRollback(
			String transactionPortletBarText)
		throws SystemException {

		addClassName(PortalService.class.getName());

		addTransactionPortletBar(transactionPortletBarText, true);
	}

	public void testAddClassNameAndTestTransactionPortletBar_Success(
			String transactionPortletBarText)
		throws SystemException {

		addClassName(PortalService.class.getName());

		addTransactionPortletBar(transactionPortletBarText, false);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void testAutoSyncHibernateSessionStateOnTxCreation()
		throws SystemException {

		String testClassName = "testClassName";

		// 1) Add in new tx

		ClassName className = classNameLocalService.addClassName(testClassName);

		try {

			// 2) Fetch in current tx

			// Clear entity cache to force populate Hibernate 1st level cache

			EntityCacheUtil.clearCache();

			className = classNamePersistence.fetchByPrimaryKey(
				className.getClassNameId());

			Session currentSession = classNamePersistence.getCurrentSession();

			if (!currentSession.contains(className)) {
				throw new IllegalStateException(
					"After fetch ClassName is not cached by hibernate 1st " +
						"level cache");
			}

			String newTestClassName = "newTestClassName";
			ClassName newClassName = new ClassNameImpl();

			newClassName.setPrimaryKey(className.getClassNameId());
			newClassName.setValue(newTestClassName);

			// 3) Update in new tx

			classNameLocalService.updateClassName(newClassName);

			if (currentSession.contains(className)) {
				throw new IllegalStateException(
					"After launched new tx ClassName is still cached by " +
						"hibernate 1st level cache");
			}

			// 4) Refetch in current tx

			// Clear entity cache to force load through Hibernate 1st level
			// cache

			EntityCacheUtil.clearCache();

			className = classNamePersistence.fetchByPrimaryKey(
				className.getClassNameId());

			if (!className.getValue().equals(newTestClassName)) {
				throw new IllegalStateException(
					"Expected " + newTestClassName + ", found " +
						className.getClassName());
			}
		}
		finally {

			// 5) Clean up

			classNameLocalService.deleteClassName(className);
		}
	}

	public void testCounterIncrement_Rollback() throws SystemException {
		int counterIncrement = PropsValues.COUNTER_INCREMENT;

		for (int i = 0; i < counterIncrement * 2; i++) {
			counterLocalService.increment();
		}

		throw new SystemException();
	}

	public void testDeleteClassName() throws PortalException, SystemException {
		classNamePersistence.removeByValue(PortalService.class.getName());
	}

	public int testGetBuildNumber() {
		return portalService.getBuildNumber();
	}

	public void testGetUserId() {
		long userId = 0;

		try {
			userId = getUserId();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isInfoEnabled()) {
			_log.info("User id " + userId);
		}
	}

	public boolean testHasClassName() throws SystemException {
		int count = classNamePersistence.countByValue(
			PortalService.class.getName());

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void addClassName(String classNameValue) throws SystemException {
		long classNameId = counterLocalService.increment();

		ClassName className = classNamePersistence.create(classNameId);

		className.setValue(classNameValue);

		classNamePersistence.update(className);
	}

	protected void addTransactionPortletBar(
			String transactionPortletBarText, boolean rollback)
		throws SystemException {

		try {
			Message message = new Message();

			message.put("rollback", rollback);
			message.put("text", transactionPortletBarText);

			SynchronousMessageSender synchronousMessageSender =
				(SynchronousMessageSender)PortalBeanLocatorUtil.locate(
					DirectSynchronousMessageSender.class.getName());

			synchronousMessageSender.send(
				DestinationNames.TEST_TRANSACTION, message);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortalServiceImpl.class);

}
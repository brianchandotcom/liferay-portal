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

	@Override
	public String getAutoDeployDirectory() {
		return PrefsPropsUtil.getString(
			PropsKeys.AUTO_DEPLOY_DEPLOY_DIR,
			PropsValues.AUTO_DEPLOY_DEPLOY_DIR);
	}

	@JSONWebService
	@Override
	public int getBuildNumber() {
		return ReleaseInfo.getBuildNumber();
	}

	@Override
	public void testAddClassName_Rollback(String classNameValue) {
		addClassName(classNameValue);

		throw new SystemException();
	}

	@Override
	public void testAddClassName_Success(String classNameValue) {
		addClassName(classNameValue);
	}

	@Override
	public void testAddClassNameAndTestTransactionPortletBar_PortalRollback(
		String transactionPortletBarText) {

		addClassName(PortalService.class.getName());

		addTransactionPortletBar(transactionPortletBarText, false);

		throw new SystemException();
	}

	@Override
	public void testAddClassNameAndTestTransactionPortletBar_PortletRollback(
		String transactionPortletBarText) {

		addClassName(PortalService.class.getName());

		addTransactionPortletBar(transactionPortletBarText, true);
	}

	@Override
	public void testAddClassNameAndTestTransactionPortletBar_Success(
		String transactionPortletBarText) {

		addClassName(PortalService.class.getName());

		addTransactionPortletBar(transactionPortletBarText, false);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void testAutoSyncHibernateSessionStateOnTxCreation() {

		// Add in new transaction

		ClassName className = classNameLocalService.addClassName(
			"testAutoSyncHibernateSessionStateOnTxCreation1");

		try {

			// Fetch in current transaction

			// Clear entity cache to force Hibernate to populate its first level
			// cache

			EntityCacheUtil.clearCache();

			className = classNamePersistence.fetchByPrimaryKey(
				className.getClassNameId());

			Session currentSession = classNamePersistence.getCurrentSession();

			if (!currentSession.contains(className)) {
				throw new IllegalStateException(
					"Entities are not available in Hibernate's first level " +
						"cache");
			}

			ClassName newClassName = new ClassNameImpl();

			newClassName.setPrimaryKey(className.getClassNameId());

			String newValue = "testAutoSyncHibernateSessionStateOnTxCreation2";

			newClassName.setValue(newValue);

			// Update in new transaction

			classNameLocalService.updateClassName(newClassName);

			if (currentSession.contains(className)) {
				throw new IllegalStateException(
					"Entities are still available in Hibernate's first level " +
						"cache");
			}

			// Refetch in current transaction

			// Clear entity cache to force Hibernate to populate its first level
			// cache

			EntityCacheUtil.clearCache();

			className = classNamePersistence.fetchByPrimaryKey(
				className.getClassNameId());

			if (!newValue.equals(className.getValue())) {
				throw new IllegalStateException(
					"Expected " + newValue + " but found " +
						className.getClassName());
			}
		}
		finally {

			// Clean up

			classNameLocalService.deleteClassName(className);
		}
	}

	@Override
	public void testDeleteClassName() throws PortalException {
		classNamePersistence.removeByValue(PortalService.class.getName());
	}

	@Override
	public int testGetBuildNumber() {
		return portalService.getBuildNumber();
	}

	@Override
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

	@Override
	public boolean testHasClassName() {
		int count = classNamePersistence.countByValue(
			PortalService.class.getName());

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void addClassName(String classNameValue) {
		long classNameId = counterLocalService.increment();

		ClassName className = classNamePersistence.create(classNameId);

		className.setValue(classNameValue);

		classNamePersistence.update(className);
	}

	protected void addTransactionPortletBar(
		String transactionPortletBarText, boolean rollback) {

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

	private static final Log _log = LogFactoryUtil.getLog(
		PortalServiceImpl.class);

}
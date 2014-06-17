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

import com.liferay.portal.kernel.concurrent.LockRegistry;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortalPreferences;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.base.PortalPreferencesLocalServiceBaseImpl;
import com.liferay.portlet.PortalPreferencesImpl;
import com.liferay.portlet.PortalPreferencesWrapper;
import com.liferay.portlet.PortalPreferencesWrapperCacheUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.concurrent.locks.Lock;

import javax.portlet.PortletPreferences;

/**
 * @author Alexander Chow
 */
public class PortalPreferencesLocalServiceImpl
	extends PortalPreferencesLocalServiceBaseImpl {

	@Override
	public PortalPreferences addPortalPreferences(
		long ownerId, int ownerType, String defaultPreferences) {

		PortalPreferencesWrapperCacheUtil.remove(ownerId, ownerType);

		long portalPreferencesId = counterLocalService.increment();

		PortalPreferences portalPreferences =
			portalPreferencesPersistence.create(portalPreferencesId);

		portalPreferences.setOwnerId(ownerId);
		portalPreferences.setOwnerType(ownerType);

		if (Validator.isNull(defaultPreferences)) {
			defaultPreferences = PortletConstants.DEFAULT_PREFERENCES;
		}

		portalPreferences.setPreferences(defaultPreferences);

		try {
			portalPreferencesPersistence.update(portalPreferences);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Add failed, fetch {ownerId=" + ownerId + ", ownerType=" +
						ownerType + "}");
			}

			portalPreferences = portalPreferencesPersistence.fetchByO_O(
				ownerId, ownerType, false);

			if (portalPreferences == null) {
				throw se;
			}
		}

		return portalPreferences;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addPortalPreferences(long,
	 *             int, String)}
	 */
	@Deprecated
	@Override
	public PortalPreferences addPortalPreferences(
		long companyId, long ownerId, int ownerType,
		String defaultPreferences) {

		return addPortalPreferences(ownerId, ownerType, defaultPreferences);
	}

	@Override
	public PortletPreferences getPreferences(long ownerId, int ownerType) {
		return getPreferences(ownerId, ownerType, null);
	}

	@Override
	public PortletPreferences getPreferences(
		long ownerId, int ownerType, String defaultPreferences) {

		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (!dbType.equals(DB.TYPE_HYPERSONIC)) {
			return doGetPreferences(ownerId, ownerType, defaultPreferences);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(ownerId);
		sb.append(StringPool.POUND);
		sb.append(ownerType);
		sb.append(StringPool.POUND);

		String groupName = getClass().getName();
		String key = sb.toString();

		Lock lock = LockRegistry.allocateLock(groupName, key);

		lock.lock();

		try {
			return doGetPreferences(ownerId, ownerType, defaultPreferences);
		}
		finally {
			lock.unlock();

			LockRegistry.freeLock(groupName, key);
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getPreferences(long, int)}
	 */
	@Deprecated
	@Override
	public PortletPreferences getPreferences(
		long companyId, long ownerId, int ownerType) {

		return getPreferences(ownerId, ownerType);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getPreferences(long, int,
	 *             String)}
	 */
	@Deprecated
	@Override
	public PortletPreferences getPreferences(
		long companyId, long ownerId, int ownerType,
		String defaultPreferences) {

		return getPreferences(ownerId, ownerType, defaultPreferences);
	}

	@Override
	public PortalPreferences updatePreferences(
		long ownerId, int ownerType,
		com.liferay.portlet.PortalPreferences portalPreferences) {

		String xml = PortletPreferencesFactoryUtil.toXML(portalPreferences);

		return updatePreferences(ownerId, ownerType, xml);
	}

	@Override
	public PortalPreferences updatePreferences(
		long ownerId, int ownerType, String xml) {

		PortalPreferencesWrapperCacheUtil.remove(ownerId, ownerType);

		PortalPreferences portalPreferences =
			portalPreferencesPersistence.fetchByO_O(ownerId, ownerType);

		if (portalPreferences == null) {
			long portalPreferencesId = counterLocalService.increment();

			portalPreferences = portalPreferencesPersistence.create(
				portalPreferencesId);

			portalPreferences.setOwnerId(ownerId);
			portalPreferences.setOwnerType(ownerType);
		}

		portalPreferences.setPreferences(xml);

		portalPreferencesPersistence.update(portalPreferences);

		return portalPreferences;
	}

	protected PortletPreferences doGetPreferences(
		long ownerId, int ownerType, String defaultPreferences) {

		PortalPreferencesWrapper portalPreferencesWrapper =
			PortalPreferencesWrapperCacheUtil.get(ownerId, ownerType);

		if (portalPreferencesWrapper != null) {
			return portalPreferencesWrapper.clone();
		}

		PortalPreferences portalPreferences =
			portalPreferencesPersistence.fetchByO_O(ownerId, ownerType);

		if (portalPreferences == null) {
			portalPreferences =
				portalPreferencesLocalService.addPortalPreferences(
					ownerId, ownerType, defaultPreferences);
		}

		PortalPreferencesImpl portalPreferencesImpl =
			(PortalPreferencesImpl)PortletPreferencesFactoryUtil.fromXML(
				ownerId, ownerType, portalPreferences.getPreferences());

		portalPreferencesWrapper = new PortalPreferencesWrapper(
			portalPreferencesImpl);

		PortalPreferencesWrapperCacheUtil.put(
			ownerId, ownerType, portalPreferencesWrapper);

		return portalPreferencesWrapper.clone();
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalPreferencesLocalServiceImpl.class);

}
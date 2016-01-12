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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortalPreferences;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.base.PortalPreferencesLocalServiceBaseImpl;
import com.liferay.portlet.PortalPreferencesImpl;
import com.liferay.portlet.PortalPreferencesWrapper;
import com.liferay.portlet.PortalPreferencesWrapperCacheUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

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

	@Override
	public PortletPreferences getPreferences(long ownerId, int ownerType) {
		return getPreferences(ownerId, ownerType, null);
	}

	@Override
	public PortletPreferences getPreferences(
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

		PortalPreferencesImpl portalPreferencesImpl = new PortalPreferencesImpl(
			portalPreferences, false);

		portalPreferencesWrapper = new PortalPreferencesWrapper(
			portalPreferencesImpl);

		PortalPreferencesWrapperCacheUtil.put(
			ownerId, ownerType, portalPreferencesWrapper);

		return portalPreferencesWrapper.clone();
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

	private static final Log _log = LogFactoryUtil.getLog(
		PortalPreferencesLocalServiceImpl.class);

}
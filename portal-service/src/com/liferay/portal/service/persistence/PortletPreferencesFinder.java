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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @generated
 */
@ProviderType
public interface PortletPreferencesFinder {
	public long countByO_O_P(long ownerId, int ownerType,
		java.lang.String portletId, boolean excludeDefaultPreferences);

	public long countByO_O_P_P_P(long ownerId, int ownerType, long plid,
		java.lang.String portletId, boolean excludeDefaultPreferences);

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByPortletId(
		java.lang.String portletId);

	public java.util.Map<java.io.Serializable, com.liferay.portal.model.PortletPreferences> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByC_G_O_O_P_P(
		long companyId, long groupId, long ownerId, int ownerType,
		java.lang.String portletId, boolean privateLayout);
}
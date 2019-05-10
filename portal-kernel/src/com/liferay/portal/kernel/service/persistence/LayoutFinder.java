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

package com.liferay.portal.kernel.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface LayoutFinder {

	public java.util.List<com.liferay.portal.kernel.model.Layout>
		findByNoPermissions(long roleId);

	public java.util.List<com.liferay.portal.kernel.model.Layout>
		findByNullFriendlyURL();

	public java.util.List<com.liferay.portal.kernel.model.Layout>
		findByScopeGroup(long groupId);

	public java.util.List<com.liferay.portal.kernel.model.Layout>
		findByScopeGroup(long groupId, boolean privateLayout);

	public java.util.List<com.liferay.portal.kernel.model.LayoutReference>
		findByC_P_P(
			long companyId, String portletId, String preferencesKey,
			String preferencesValue);

}
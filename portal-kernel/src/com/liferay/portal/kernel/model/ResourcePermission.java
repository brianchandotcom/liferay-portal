/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ResourcePermission service. Represents a row in the &quot;ResourcePermission&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermissionModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.ResourcePermissionImpl")
@ProviderType
public interface ResourcePermission
	extends PersistedModel, ResourcePermissionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.ResourcePermissionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ResourcePermission, Long>
		RESOURCE_PERMISSION_ID_ACCESSOR =
			new Accessor<ResourcePermission, Long>() {

				@Override
				public Long get(ResourcePermission resourcePermission) {
					return resourcePermission.getResourcePermissionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ResourcePermission> getTypeClass() {
					return ResourcePermission.class;
				}

			};

	public void addResourceAction(String actionId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasAction(ResourceAction resourceAction);

	public boolean hasActionId(String actionId);

	public void removeResourceAction(String actionId)
		throws com.liferay.portal.kernel.exception.PortalException;

}
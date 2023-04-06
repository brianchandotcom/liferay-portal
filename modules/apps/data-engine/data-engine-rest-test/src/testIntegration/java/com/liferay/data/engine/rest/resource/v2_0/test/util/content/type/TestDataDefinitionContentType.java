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

package com.liferay.data.engine.rest.resource.v2_0.test.util.content.type;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Paulino
 */
@Component(
	property = "content.type=test",
	service = {
		DataDefinitionContentType.class, TestDataDefinitionContentType.class
	}
)
public class TestDataDefinitionContentType
	implements DataDefinitionContentType {

	@Override
	public boolean allowEmptyDataDefinition() {
		return _allowEmptyDataDefinition;
	}

	@Override
	public boolean allowInvalidAvailableLocalesForProperty() {
		return true;
	}

	@Override
	public boolean allowReferencedDataDefinitionDeletion() {
		return true;
	}

	@Override
	public long getClassNameId() {
		return _portal.getClassNameId(TestDataDefinitionContentType.class);
	}

	@Override
	public String getContentType() {
		return "test";
	}

	@Override
	public String getPortletResourceName() {
		return ModelResourceActionTestUtil.PORTLET_RESOURCE_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String resourceName, long primKey, long userId, String actionId)
		throws PortalException {

		return true;
	}

	@Override
	public boolean hasPortletPermission(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		return true;
	}

	@Override
	public boolean isDataRecordCollectionPermissionCheckingEnabled() {
		return true;
	}

	public void setAllowEmptyDataDefinition(boolean allowEmptyDataDefinition) {
		_allowEmptyDataDefinition = allowEmptyDataDefinition;
	}

	private boolean _allowEmptyDataDefinition = true;

	@Reference
	private Portal _portal;

}
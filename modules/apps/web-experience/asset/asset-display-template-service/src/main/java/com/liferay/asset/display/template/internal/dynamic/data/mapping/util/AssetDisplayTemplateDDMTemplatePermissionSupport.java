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

package com.liferay.asset.display.template.internal.dynamic.data.mapping.util;

import com.liferay.asset.display.template.constants.AssetDisplayConstants;
import com.liferay.dynamic.data.mapping.util.DDMTemplatePermissionSupport;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.asset.display.template.model.AssetDisplayTemplate"
)
public class AssetDisplayTemplateDDMTemplatePermissionSupport
	implements DDMTemplatePermissionSupport {

	@Override
	public String getResourceName(long classNameId) {
		return AssetDisplayConstants.RESOURCE_NAME;
	}

}
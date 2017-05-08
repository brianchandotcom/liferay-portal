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

package com.liferay.screens.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.screens.service.base.ScreensDDMStructureVersionServiceBaseImpl;

/**
 * @author Javier Gamarra
 */
@ProviderType
public class ScreensDDMStructureVersionServiceImpl
	extends ScreensDDMStructureVersionServiceBaseImpl {

	@Override
	public JSONObject getDDMStructureVersion(long structureId)
		throws PortalException {

		JSONObject ddmStructureVersionJSONObject =
			JSONFactoryUtil.createJSONObject();

		DDMStructureVersion ddmStructureVersion =
			ddmStructureVersionService.getLatestStructureVersion(structureId);

		DDMFormLayout ddmFormLayout = ddmStructureVersion.getDDMFormLayout();

		JSONObject ddmFormLayoutJSONObject = JSONFactoryUtil.createJSONObject(
			JSONFactoryUtil.looseSerializeDeep(ddmFormLayout));

		ddmStructureVersionJSONObject.put(
			"ddmFormLayout", ddmFormLayoutJSONObject);

		JSONObject ddmStructureJSONObject = JSONFactoryUtil.createJSONObject(
			JSONFactoryUtil.looseSerialize(ddmStructureVersion.getStructure()));

		ddmStructureVersionJSONObject.put(
			"ddmStructure", ddmStructureJSONObject);

		return ddmStructureVersionJSONObject;
	}

}
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

package com.liferay.batch.planner.rest.internal.helper;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.batch.engine.Field;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegateRegistry;
import com.liferay.portal.vulcan.resource.OpenAPIResource;
import com.liferay.portal.vulcan.util.OpenAPIUtil;
import com.liferay.portal.vulcan.yaml.YAMLUtil;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(service = FieldHelper.class)
public class FieldHelper {

	public List<Field> filter(
		List<Field> fields, Field.AccessType ignoredAccessType) {

		return ListUtil.filter(
			fields,
			dtoEntityField -> {
				if (dtoEntityField.getAccessType() == ignoredAccessType) {
					return false;
				}

				String name = dtoEntityField.getName();

				if (name.equals("actions") || name.startsWith("x-")) {
					return false;
				}

				return true;
			});
	}

	public List<Field> getFields(String internalClassName) throws Exception {
		OpenAPIYAML openAPIYAML = _getOpenAPIYAML(internalClassName);

		Map<String, Field> dtoEntityFields = OpenAPIUtil.getDTOEntityFields(
			internalClassName.substring(
				internalClassName.lastIndexOf(StringPool.PERIOD) + 1),
			openAPIYAML);

		return new ArrayList<>(dtoEntityFields.values());
	}

	private OpenAPIYAML _getOpenAPIYAML(String internalClassName)
		throws Exception {

		VulcanBatchEngineTaskItemDelegate vulcanBatchEngineTaskItemDelegate =
			_vulcanBatchEngineTaskItemDelegateRegistry.
				getVulcanBatchEngineTaskItemDelegate(internalClassName);

		Response response = _openAPIResource.getOpenAPI(
			Collections.singleton(
				vulcanBatchEngineTaskItemDelegate.getResourceClass()),
			"yaml");

		if (response.getStatus() != 200) {
			throw new IllegalArgumentException(
				"Unable to find Open API specification for " +
					internalClassName);
		}

		return YAMLUtil.loadOpenAPIYAML((String)response.getEntity());
	}

	@Reference
	private OpenAPIResource _openAPIResource;

	@Reference
	private VulcanBatchEngineTaskItemDelegateRegistry
		_vulcanBatchEngineTaskItemDelegateRegistry;

}
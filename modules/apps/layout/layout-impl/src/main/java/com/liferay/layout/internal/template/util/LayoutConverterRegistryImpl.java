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

package com.liferay.layout.internal.template.util;

import com.liferay.layout.util.template.LayoutConverter;
import com.liferay.layout.util.template.LayoutConverterRegistry;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = LayoutConverterRegistry.class)
public class LayoutConverterRegistryImpl implements LayoutConverterRegistry {

	@Override
	public LayoutConverter getLayoutConverter(String layoutTemplateId) {
		return _layoutConverters.get(layoutTemplateId);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC, target = "(layout.template.id=*)"
	)
	protected void setLayoutConverter(
		LayoutConverter layoutConverter, Map<String, Object> properties) {

		String layoutTemplateId = MapUtil.getString(
			properties, "layout.template.id");

		_layoutConverters.put(layoutTemplateId, layoutConverter);
	}

	protected void unsetLayoutConverter(
		LayoutConverter layoutConverter, Map<String, Object> properties) {

		String layoutTemplateId = MapUtil.getString(
			properties, "layout.template.id");

		_layoutConverters.remove(layoutTemplateId, layoutConverter);
	}

	private final Map<String, LayoutConverter> _layoutConverters =
		new ConcurrentHashMap<>();

}
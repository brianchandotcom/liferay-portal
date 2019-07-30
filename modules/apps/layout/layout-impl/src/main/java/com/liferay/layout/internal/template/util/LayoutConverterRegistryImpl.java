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

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = LayoutConverterRegistry.class)
public class LayoutConverterRegistryImpl implements LayoutConverterRegistry {

	@Override
	public LayoutConverter getLayoutConverter(String layoutTemplateId) {
		return _layoutConverters.get(layoutTemplateId);
	}

	private static final Map<String, LayoutConverter> _layoutConverters =
		new HashMap<String, LayoutConverter>() {
			{
				put("1_column", new OneColumnLayoutConverter());
				put("1_3_1_columns", new OneThreeOneColumnsLayoutConverter());
				put("1_3_2_columns", new OneThreeTwoColumnsLayoutConverter());
				put("1_2_columns_ii", new OneTwoColumnsIILayoutConverter());
				put("1_2_columns_i", new OneTwoColumnsILayoutConverter());
				put(
					"1_2_1_columns_ii",
					new OneTwoOneColumnsIILayoutConverter());
				put("1_2_1_columns_i", new OneTwoOneColumnsILayoutConverter());
				put("3_columns", new ThreeColumnsLayoutConverter());
				put("3_2_3_columns", new ThreeTwoThreeColumnsLayoutConverter());
				put("2_columns_iii", new TwoColumnsIIILayoutConverter());
				put("2_columns_ii", new TwoColumnsIILayoutConverter());
				put("2_columns_i", new TwoColumnsILayoutConverter());
				put("2_1_2_columns", new TwoOneTwoColumnsLayoutConverter());
				put("2_2_columns", new TwoTwoColumnsLayoutConverter());
			}
		};

}
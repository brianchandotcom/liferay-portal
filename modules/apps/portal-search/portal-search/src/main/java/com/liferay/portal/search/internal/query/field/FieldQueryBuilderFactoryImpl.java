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

package com.liferay.portal.search.internal.query.field;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.query.field.FieldQueryBuilder;
import com.liferay.portal.search.query.field.FieldQueryBuilderFactory;
import com.liferay.portal.search.query.field.QueryPreProcessConfiguration;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 * @author Rodrigo Paulino
 */
@Component(
	property = {
		"description.fields=content|description", "title.fields=name|title"
	},
	service = FieldQueryBuilderFactory.class
)
public class FieldQueryBuilderFactoryImpl implements FieldQueryBuilderFactory {

	@Override
	public FieldQueryBuilder getQueryBuilder(String fieldName) {
		if (queryPreProcessConfiguration.isSubstringSearchAlways(fieldName)) {
			return substringFieldQueryBuilder;
		}

		for (String descriptionFieldName : _descriptionFieldNames) {
			if (fieldName.startsWith(descriptionFieldName)) {
				return descriptionFieldQueryBuilder;
			}
		}

		for (String titleFieldName : _titleFieldNames) {
			if (fieldName.startsWith(titleFieldName)) {
				return titleFieldQueryBuilder;
			}
		}

		return null;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_descriptionFieldNames = _getFields(properties, "description.fields");
		_titleFieldNames = _getFields(properties, "title.fields");
	}

	@Reference
	protected DescriptionFieldQueryBuilder descriptionFieldQueryBuilder;

	@Reference
	protected QueryPreProcessConfiguration queryPreProcessConfiguration;

	@Reference
	protected SubstringFieldQueryBuilder substringFieldQueryBuilder;

	@Reference
	protected TitleFieldQueryBuilder titleFieldQueryBuilder;

	private Collection<String> _getFields(
		Map<String, Object> properties, String key) {

		String[] values = StringUtil.split(
			GetterUtil.getString(properties.get(key)), CharPool.PIPE);

		return new HashSet<>(Arrays.asList(values));
	}

	private volatile Collection<String> _descriptionFieldNames = Arrays.asList(
		"content", "description");
	private volatile Collection<String> _titleFieldNames = new HashSet<>(
		Arrays.asList("name", "title"));

}
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

package com.liferay.analytics.settings.rest.internal.resource.v1_0;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.dto.v1_0.Field;
import com.liferay.analytics.settings.rest.internal.constants.FieldPeopleConstants;
import com.liferay.analytics.settings.rest.internal.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.rest.resource.v1_0.FieldResource;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Ferrari
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/field.properties",
	scope = ServiceScope.PROTOTYPE, service = FieldResource.class
)
public class FieldResourceImpl extends BaseFieldResourceImpl {

	@Override
	public Page<Field> getFieldsPeoplePage(
			String keywords, Pagination pagination, Sort[] sorts)
		throws Exception {

		List<Field> fields = _getFieldsPeople(keywords, sorts);

		return Page.of(
			ListUtil.subList(
				fields, pagination.getStartPosition(),
				pagination.getEndPosition()),
			pagination, fields.size());
	}

	@Override
	public void patchFieldPeople(Field[] fields) throws Exception {
		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				contextCompany.getCompanyId());

		_analyticsSettingsManager.updateCompanyConfiguration(
			contextCompany.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedContactFieldNames",
				_updateSelectedFields(
					analyticsConfiguration.syncedContactFieldNames(), fields,
					FieldPeopleConstants.FIELD_CONTACT_REQUIRED_NAMES,
					"contact", FieldPeopleConstants.FIELD_CONTACT_NAMES)
			).put(
				"syncedUserFieldNames",
				_updateSelectedFields(
					analyticsConfiguration.syncedUserFieldNames(), fields,
					FieldPeopleConstants.FIELD_USER_REQUIRED_NAMES, "user",
					FieldPeopleConstants.FIELD_USER_NAMES)
			).build());
	}

	private List<Field> _filter(List<Field> fields, String keywords) {
		if (keywords == null) {
			return fields;
		}

		Stream<Field> stream = fields.stream();

		return stream.filter(
			field -> {
				String name = field.getName();

				return name.matches("(?i).*" + keywords + ".*");
			}
		).collect(
			Collectors.toList()
		);
	}

	private List<Field> _getFieldsPeople(String keyword, Sort[] sorts)
		throws Exception {

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				contextCompany.getCompanyId());

		List<Field> fields = new ArrayList<>();

		for (int i = 0; i < FieldPeopleConstants.FIELD_CONTACT_NAMES.length;
			 i++) {

			Field field = new Field();

			field.setExample(FieldPeopleConstants.FIELD_CONTACT_EXAMPLES[i]);
			field.setName(FieldPeopleConstants.FIELD_CONTACT_NAMES[i]);
			field.setRequired(
				ArrayUtil.contains(
					FieldPeopleConstants.FIELD_CONTACT_REQUIRED_NAMES,
					FieldPeopleConstants.FIELD_CONTACT_NAMES[i]));
			field.setSelected(
				ArrayUtil.contains(
					analyticsConfiguration.syncedContactFieldNames(),
					FieldPeopleConstants.FIELD_CONTACT_NAMES[i]) ||
				field.getRequired());
			field.setSource("contact");
			field.setType(FieldPeopleConstants.FIELD_CONTACT_TYPES[i]);

			fields.add(field);
		}

		for (int i = 0; i < FieldPeopleConstants.FIELD_USER_NAMES.length; i++) {
			Field field = new Field();

			field.setExample(FieldPeopleConstants.FIELD_USER_EXAMPLES[i]);
			field.setName(FieldPeopleConstants.FIELD_USER_NAMES[i]);
			field.setRequired(
				ArrayUtil.contains(
					FieldPeopleConstants.FIELD_USER_REQUIRED_NAMES,
					FieldPeopleConstants.FIELD_USER_NAMES[i]));
			field.setSelected(
				ArrayUtil.contains(
					analyticsConfiguration.syncedUserFieldNames(),
					FieldPeopleConstants.FIELD_USER_NAMES[i]) ||
				field.getRequired());
			field.setSource("user");
			field.setType(FieldPeopleConstants.FIELD_USER_TYPES[i]);

			fields.add(field);
		}

		fields = _filter(fields, keyword);

		return _sort(fields, sorts);
	}

	private List<Field> _sort(List<Field> fields, Sort[] sorts) {
		if (ArrayUtil.isEmpty(sorts)) {
			return fields;
		}

		Comparator<Field> fieldComparator = null;

		for (Sort sort : sorts) {
			if (!Objects.equals(sort.getFieldName(), "name")) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Skipping unsupported sort field: " +
							sort.getFieldName());
				}

				continue;
			}

			fieldComparator = Comparator.comparing(Field::getName);

			if (sort.isReverse()) {
				fieldComparator = fieldComparator.reversed();
			}
		}

		if (fieldComparator != null) {
			fields.sort(fieldComparator);
		}

		return fields;
	}

	private String[] _updateSelectedFields(
		String[] configurationFieldNames, Field[] fields,
		String[] requiredFieldNames, String source,
		String[] validateFieldNames) {

		Set<String> selectedFieldNames = new HashSet<>(
			Arrays.asList(configurationFieldNames));

		Stream.of(
			fields
		).filter(
			field ->
				Objects.equals(source, field.getSource()) &&
				!field.getSelected()
		).map(
			Field::getName
		).forEach(
			selectedFieldNames::remove
		);

		Stream.of(
			fields
		).filter(
			field ->
				Objects.equals(source, field.getSource()) && field.getSelected()
		).map(
			Field::getName
		).filter(
			name -> ArrayUtil.contains(validateFieldNames, name)
		).forEach(
			selectedFieldNames::add
		);

		Collections.addAll(selectedFieldNames, requiredFieldNames);

		return selectedFieldNames.toArray(new String[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FieldResourceImpl.class);

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

}
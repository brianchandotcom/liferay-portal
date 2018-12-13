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

package com.liferay.data.engine.web.internal.servlet.data.fetcher;

import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.service.DEDataDefinitionRequestBuilder;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinitionType;
import com.liferay.data.engine.web.internal.graphql.model.SaveDataDefinitionType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DESaveDataDefinitionDataFetcher.class)
public class DESaveDataDefinitionDataFetcher
	extends DEBaseDataDefinitionDataFetcher
	implements DataFetcher<SaveDataDefinitionType> {

	@Override
	public SaveDataDefinitionType get(DataFetchingEnvironment environment) {
		long userId = GetterUtil.getLong(environment.getArgument("userId"));
		long groupId = GetterUtil.getLong(environment.getArgument("groupId"));

		Map<String, Object> dataDefinition = environment.getArgument(
			"dataDefinition");

		String languageId = environment.getArgument("languageId");

		List<DEDataDefinitionField> deDataDefinitionFields =
			createDEDataDefinitionFields(
				(List<Map<String, Object>>)dataDefinition.get("fields"));
		long dataDefinitionId = GetterUtil.getLong(
			dataDefinition.get("dataDefinitionId"));
		Map<String, String> descriptions = getLocalizedValues(
			(List<Map<String, Object>>)dataDefinition.get("descriptions"));
		Map<String, String> names = getLocalizedValues(
			(List<Map<String, Object>>)dataDefinition.get("names"));
		String storageType = GetterUtil.getString(
			dataDefinition.get("storageType"), "json");

		DEDataDefinition deDataDefinition = new DEDataDefinition(
			deDataDefinitionFields);

		deDataDefinition.setDEDataDefinitionId(dataDefinitionId);
		deDataDefinition.setDescription(descriptions);
		deDataDefinition.setName(names);
		deDataDefinition.setStorageType(storageType);

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
			DEDataDefinitionRequestBuilder.saveBuilder(
				deDataDefinition
			).inGroup(
				groupId
			).onBehalfOf(
				userId
			).build();

		SaveDataDefinitionType saveDataDefinitionType =
			new SaveDataDefinitionType();

		String errorMessage = null;

		try {
			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			DataDefinitionType dataDefinitionType = createDataDefinitionType(
				deDataDefinitionSaveResponse.getDEDataDefinitionId(),
				deDataDefinition);

			saveDataDefinitionType.setDataDefinition(dataDefinitionType);
		}
		catch (DEDataDefinitionException.MustHavePermission mhp) {
			errorMessage = getMessage(
				languageId, "the-user-must-have-data-definition-permission",
				getActionMessage(languageId, mhp.getActionId()));
		}
		catch (DEDataDefinitionException.NoSuchDataDefinition nsdd) {
			errorMessage = getMessage(
				languageId, "no-such-data-definition-with-id",
				nsdd.getDEDataDefinitionId());
		}
		catch (Exception e) {
			errorMessage = getMessage(
				languageId, "unable-to-save-data-definition");
		}

		if (errorMessage != null) {
			handleErrorMessage(errorMessage);
		}

		return saveDataDefinitionType;
	}

	@Override
	public Portal getPortal() {
		return portal;
	}

	protected List<DEDataDefinitionField> createDEDataDefinitionFields(
		List<Map<String, Object>> fields) {

		Stream<Map<String, Object>> stream = fields.stream();

		return stream.map(
			this::map
		).collect(
			Collectors.toList()
		);
	}

	protected Map<String, String> getLocalizedValues(
		List<Map<String, Object>> values) {

		if (values == null) {
			return null;
		}

		Stream<Map<String, Object>> stream = values.stream();

		return stream.collect(
			Collectors.toMap(
				entry -> MapUtil.getString(entry, "key"),
				entry -> MapUtil.getString(entry, "value")));
	}

	protected DEDataDefinitionField map(Map<String, Object> fieldProperties) {
		DEDataDefinitionField deDataDefinitionField = new DEDataDefinitionField(
			MapUtil.getString(fieldProperties, "name"),
			MapUtil.getString(fieldProperties, "type"));

		deDataDefinitionField.setDefaultValue(
			fieldProperties.get("defaultValue"));
		deDataDefinitionField.setIndexable(
			MapUtil.getBoolean(fieldProperties, "indexable", true));

		Map<String, String> labels = getLocalizedValues(
			(List<Map<String, Object>>)fieldProperties.get("labels"));

		if (labels != null) {
			deDataDefinitionField.setLabel(labels);
		}

		deDataDefinitionField.setLocalizable(
			MapUtil.getBoolean(fieldProperties, "localizable", false));
		deDataDefinitionField.setRepeatable(
			MapUtil.getBoolean(fieldProperties, "repeatable", false));
		deDataDefinitionField.setRequired(
			MapUtil.getBoolean(fieldProperties, "required", false));

		Map<String, String> tips = getLocalizedValues(
			(List<Map<String, Object>>)fieldProperties.get("tips"));

		if (tips != null) {
			deDataDefinitionField.setTip(tips);
		}

		return deDataDefinitionField;
	}

	@Reference
	protected DEDataDefinitionService deDataDefinitionService;

	@Reference
	protected Portal portal;

}
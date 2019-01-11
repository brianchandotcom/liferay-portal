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
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinition;
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
	public SaveDataDefinitionType get(
		DataFetchingEnvironment dataFetchingEnvironment) {

		SaveDataDefinitionType saveDataDefinitionType =
			new SaveDataDefinitionType();

		String errorMessage = null;
		String languageId = dataFetchingEnvironment.getArgument("languageId");

		try {
			Map<String, Object> dataDefinitionAttributes =
				dataFetchingEnvironment.getArgument("dataDefinition");

			DEDataDefinition deDataDefinition = new DEDataDefinition();

			deDataDefinition.setDEDataDefinitionFields(
				createDEDataDefinitionFields(
					(List<Map<String, Object>>)dataDefinitionAttributes.get(
						"fields")));
			deDataDefinition.setDEDataDefinitionId(
				GetterUtil.getLong(
					dataDefinitionAttributes.get("dataDefinitionId")));
			deDataDefinition.setDescription(
				getLocalizedValues(
					(List<Map<String, Object>>)dataDefinitionAttributes.get(
						"descriptions")));
			deDataDefinition.setName(
				getLocalizedValues(
					(List<Map<String, Object>>)dataDefinitionAttributes.get(
						"names")));
			deDataDefinition.setStorageType(
				GetterUtil.getString(
					dataDefinitionAttributes.get("storageType"), "json"));

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				deDataDefinitionService.execute(
					DEDataDefinitionRequestBuilder.saveBuilder(
						deDataDefinition
					).inGroup(
						GetterUtil.getLong(
							dataFetchingEnvironment.getArgument("groupId"))
					).onBehalfOf(
						GetterUtil.getLong(
							dataFetchingEnvironment.getArgument("userId"))
					).build());

			DataDefinition dataDefinition = createDataDefinition(
				deDataDefinitionSaveResponse.getDEDataDefinitionId(),
				deDataDefinition);

			saveDataDefinitionType.setDataDefinition(dataDefinition);
		}
		catch (DEDataDefinitionException.MustHavePermission mhp) {
			errorMessage = getMessage(
				languageId, "the-user-must-have-permission",
				getActionMessage(languageId, mhp.getActionId()));
		}
		catch (DEDataDefinitionException.NoSuchDataDefinition nsdd) {
			errorMessage = getMessage(
				languageId, "no-data-definition-exists-with-id-x",
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
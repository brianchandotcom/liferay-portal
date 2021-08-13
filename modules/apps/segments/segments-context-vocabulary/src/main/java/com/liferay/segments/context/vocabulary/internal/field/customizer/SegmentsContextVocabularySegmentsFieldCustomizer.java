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

package com.liferay.segments.context.vocabulary.internal.field.customizer;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration;
import com.liferay.segments.field.Field;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizer;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	property = {
		"segments.field.customizer.entity.name=Context",
		"segments.field.customizer.key=" + SegmentsContextVocabularySegmentsFieldCustomizer.KEY,
		"segments.field.customizer.priority:Integer=-1"
	},
	service = SegmentsFieldCustomizer.class
)
public class SegmentsContextVocabularySegmentsFieldCustomizer
	implements SegmentsFieldCustomizer {

	public static final String KEY = "assetVocabulary";

	@Override
	public List<String> getFieldNames() {
		return Collections.singletonList(_entityField);
	}

	@Override
	public String getFieldValueName(String fieldValue, Locale locale) {
		return fieldValue;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(String fieldName, Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return ResourceBundleUtil.getString(
			resourceBundle, "field." + CamelCaseUtil.fromCamelCase(fieldName));
	}

	@Override
	public List<Field.Option> getOptions(Locale locale) {
		Long companyId = CompanyThreadLocal.getCompanyId();

		if (companyId == null) {
			return null;
		}

		String assetVocabulary = _assetVocabulary;

		Group group = _groupLocalService.fetchCompanyGroup(companyId);

		return Optional.ofNullable(
			_assetVocabularyLocalService.fetchGroupVocabulary(
				group.getGroupId(), assetVocabulary)
		).map(
			AssetVocabulary::getCategories
		).orElseGet(
			() -> {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"No vocabulary was found with name ",
							assetVocabulary, " in company ", companyId));
				}

				return Collections.emptyList();
			}
		).stream(
		).map(
			assetCategory -> new Field.Option(
				assetCategory.getTitle(locale), assetCategory.getName())
		).collect(
			Collectors.toList()
		);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		SegmentsContextVocabularyConfiguration
			segmentsContextVocabularyConfiguration =
				ConfigurableUtil.createConfigurable(
					SegmentsContextVocabularyConfiguration.class, properties);

		_entityField = segmentsContextVocabularyConfiguration.entityField();

		_assetVocabulary =
			segmentsContextVocabularyConfiguration.assetVocabulary();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsContextVocabularySegmentsFieldCustomizer.class);

	private volatile String _assetVocabulary;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	private volatile String _entityField;

	@Reference
	private GroupLocalService _groupLocalService;

}
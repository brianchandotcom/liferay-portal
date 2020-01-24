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

package com.liferay.segments.vocabulary.field.customizer.internal.options.provider;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"configuration.field.name=vocabularyName",
		"configuration.pid=com.liferay.segments.vocabulary.field.customizer.internal.configuration.SegmentsVocabularyFieldCustomizerConfiguration"
	},
	service = ConfigurationFieldOptionsProvider.class
)
public class VocabularyOptionsProvider
	implements ConfigurationFieldOptionsProvider {

	@Override
	public List<Option> getOptions() {
		List<Option> options = new ArrayList<>();

		try {
			Long companyId = CompanyThreadLocal.getCompanyId();

			if (companyId == null) {
				return Collections.emptyList();
			}

			List<AssetVocabulary> vocabularies =
				_assetVocabularyLocalService.getCompanyVocabularies(companyId);

			for (final AssetVocabulary vocabulary : vocabularies) {
				final String value = vocabulary.getName();

				options.add(
					new Option() {

						@Override
						public String getLabel(Locale locale) {
							return vocabulary.getTitle(locale);
						}

						@Override
						public String getValue() {
							return value;
						}

					});
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		Stream<Option> stream = options.stream();

		return stream.sorted(
			(a, b) -> {
				String valueA = a.getValue();
				String valueB = b.getValue();

				return valueA.compareTo(valueB);
			}
		).collect(
			Collectors.toList()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VocabularyOptionsProvider.class);

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}
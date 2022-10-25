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

package com.liferay.segments.context.vocabulary.internal.configuration.admin.definition;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;

import java.util.Collections;
import java.util.Comparator;
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
		"configuration.field.name=assetVocabularyName",
		"configuration.pid=com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyCompanyConfiguration",
		"configuration.pid=com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration"
	},
	service = ConfigurationFieldOptionsProvider.class
)
public class AssetVocabularyConfigurationFieldOptionsProvider
	implements ConfigurationFieldOptionsProvider {

	@Override
	public List<Option> getOptions() {
		try {
			Long companyId = CompanyThreadLocal.getCompanyId();

			if (companyId == null) {
				return Collections.emptyList();
			}

			Company company = _companyLocalService.getCompany(companyId);

			List<AssetVocabulary> assetVocabularies =
				_assetVocabularyLocalService.getGroupsVocabularies(
					new long[] {company.getGroupId()});

			Stream<AssetVocabulary> stream = assetVocabularies.stream();

			return stream.map(
				this::_toOption
			).sorted(
				Comparator.comparing(Option::getValue)
			).collect(
				Collectors.toList()
			);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return Collections.emptyList();
		}
	}

	private Option _toOption(AssetVocabulary assetVocabulary) {
		return new Option() {

			@Override
			public String getLabel(Locale locale) {
				return assetVocabulary.getTitle(locale);
			}

			@Override
			public String getValue() {
				return assetVocabulary.getName();
			}

		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetVocabularyConfigurationFieldOptionsProvider.class);

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

}
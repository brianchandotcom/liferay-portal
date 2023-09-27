/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.context.vocabulary.internal.configuration.admin.definition;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
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

			return ListUtil.sort(
				TransformUtil.transform(
					assetVocabularies,
					assetVocabularie -> _toOption(assetVocabularie)),
				Comparator.comparing(Option::getValue));
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
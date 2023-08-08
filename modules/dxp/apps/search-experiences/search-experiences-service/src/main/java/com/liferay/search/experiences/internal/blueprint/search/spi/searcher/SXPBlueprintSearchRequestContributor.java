/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.search.spi.searcher;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.spi.searcher.SearchRequestContributor;
import com.liferay.search.experiences.blueprint.search.request.enhancer.SXPBlueprintSearchRequestEnhancer;
import com.liferay.search.experiences.exception.SXPExceptionUtil;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false,
	property = "search.request.contributor.id=com.liferay.search.experiences.blueprint",
	service = SearchRequestContributor.class
)
public class SXPBlueprintSearchRequestContributor
	implements SearchRequestContributor {

	@Override
	public SearchRequest contribute(SearchRequest searchRequest) {
		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(searchRequest);

		_contributeSXPBlueprintExternalReferenceCode(searchRequestBuilder);
		_contributeSXPBlueprintId(searchRequestBuilder);

		return searchRequestBuilder.build();
	}

	private void _contributeSXPBlueprintExternalReferenceCode(
		SearchRequestBuilder searchRequestBuilder) {

		Object object = searchRequestBuilder.withSearchContextGet(
			searchContext -> searchContext.getAttribute(
				"search.experiences.blueprint.external.reference.code"));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Search experiences blueprint external reference code " +
					object);
		}

		if (object instanceof String) {
			String string = (String)object;

			if (Validator.isBlank(string)) {
				return;
			}

			String[] sxpBlueprintExternalReferenceCodes = StringUtil.split(
				string);

			for (String sxpBlueprintExternalReferenceCode :
					sxpBlueprintExternalReferenceCodes) {

				if (Validator.isBlank(sxpBlueprintExternalReferenceCode)) {
					continue;
				}

				_enhance(
					searchRequestBuilder,
					_sxpBlueprintLocalService.
						fetchSXPBlueprintByExternalReferenceCode(
							sxpBlueprintExternalReferenceCode,
							GetterUtil.getLong(
								searchRequestBuilder.withSearchContextGet(
									SearchContext::getCompanyId))));
			}
		}
		else if (object != null) {
			throw new IllegalArgumentException(
				"Invalid search experiences blueprint external reference " +
					"code " + object);
		}
	}

	private void _contributeSXPBlueprintId(
		SearchRequestBuilder searchRequestBuilder) {

		Object object = searchRequestBuilder.withSearchContextGet(
			searchContext -> searchContext.getAttribute(
				"search.experiences.blueprint.id"));

		if (_log.isDebugEnabled()) {
			_log.debug("Search experiences blueprint ID " + object);
		}

		long[] sxpBlueprintIds = null;

		if (object instanceof Number) {
			sxpBlueprintIds = new long[] {GetterUtil.getLong(object)};
		}
		else if (object instanceof String) {
			String string = (String)object;

			if (!Validator.isBlank(string)) {
				sxpBlueprintIds = GetterUtil.getLongValues(
					StringUtil.split(string));
			}
		}
		else if (object != null) {
			throw new IllegalArgumentException(
				"Invalid search experiences blueprint ID " + object);
		}

		if (sxpBlueprintIds == null) {
			return;
		}

		for (long sxpBlueprintId : sxpBlueprintIds) {
			if (sxpBlueprintId == 0) {
				continue;
			}

			_enhance(
				searchRequestBuilder,
				_sxpBlueprintLocalService.fetchSXPBlueprint(sxpBlueprintId));
		}
	}

	private void _enhance(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint) {

		if (_log.isDebugEnabled()) {
			_log.debug("Search experiences blueprint " + sxpBlueprint);
		}

		if (sxpBlueprint == null) {
			return;
		}

		RuntimeException runtimeException = new RuntimeException();

		try {
			_sxpBlueprintSearchRequestEnhancer.enhance(
				searchRequestBuilder, sxpBlueprint);
		}
		catch (Exception exception) {
			runtimeException.addSuppressed(exception);
		}

		if (ArrayUtil.isNotEmpty(runtimeException.getSuppressed())) {
			if (_log.isWarnEnabled()) {
				_log.warn(runtimeException);
			}
		}

		if (SXPExceptionUtil.hasErrors(runtimeException)) {
			throw runtimeException;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintSearchRequestContributor.class);

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Reference
	private SXPBlueprintSearchRequestEnhancer
		_sxpBlueprintSearchRequestEnhancer;

}
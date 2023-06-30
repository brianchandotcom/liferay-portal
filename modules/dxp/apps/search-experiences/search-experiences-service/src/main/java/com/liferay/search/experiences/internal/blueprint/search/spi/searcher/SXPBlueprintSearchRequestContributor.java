/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.blueprint.search.spi.searcher;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
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

import java.util.Arrays;
import java.util.List;

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

		for (EvaluatorFunction evaluatorFunction : _evaluatorFunctions) {
			try {
				if (evaluatorFunction.apply(searchRequestBuilder)) {
					break;
				}
			}
			catch (PortalException portalException) {
				throw new RuntimeException(portalException);
			}
		}

		return searchRequestBuilder.build();
	}

	@FunctionalInterface
	public interface EvaluatorFunction
		extends UnsafeFunction<SearchRequestBuilder, Boolean, PortalException> {
	}

	private boolean _contributeSXPBlueprintExternalReferenceCode(
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

			if (!Validator.isBlank(string)) {
				_enhanceWithExternalReferenceCode(
					searchRequestBuilder, StringUtil.split(string));

				return true;
			}
		}
		else if (object != null) {
			throw new IllegalArgumentException(
				"Invalid search experiences blueprint external reference " +
					"code " + object);
		}

		return false;
	}

	private boolean _contributeSXPBlueprintId(
		SearchRequestBuilder searchRequestBuilder) {

		Object object = searchRequestBuilder.withSearchContextGet(
			searchContext -> searchContext.getAttribute(
				"search.experiences.blueprint.id"));

		if (_log.isDebugEnabled()) {
			_log.debug("Search experiences blueprint ID " + object);
		}

		if (object instanceof Number) {
			_enhanceWithId(searchRequestBuilder, GetterUtil.getLong(object));
		}
		else if (object instanceof String) {
			String string = (String)object;

			if (!Validator.isBlank(string)) {
				_enhanceWithId(
					searchRequestBuilder,
					GetterUtil.getLongValues(StringUtil.split(string)));

				return true;
			}
		}
		else if (object != null) {
			throw new IllegalArgumentException(
				"Invalid search experiences blueprint ID " + object);
		}

		return false;
	}

	private boolean _contributeSXPBlueprintJSON(
		SearchRequestBuilder searchRequestBuilder) {

		String sxpBlueprintJSON = searchRequestBuilder.withSearchContextGet(
			searchContext -> GetterUtil.getString(
				searchContext.getAttribute(
					"search.experiences.blueprint.json")));

		if (_log.isDebugEnabled()) {
			_log.debug("Search experiences blueprint JSON " + sxpBlueprintJSON);
		}

		RuntimeException runtimeException = new RuntimeException();

		try {
			if (Validator.isNotNull(sxpBlueprintJSON)) {
				_sxpBlueprintSearchRequestEnhancer.enhance(
					searchRequestBuilder, sxpBlueprintJSON);

				return true;
			}
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

		return false;
	}

	private void _enhanceWithExternalReferenceCode(
		SearchRequestBuilder searchRequestBuilder,
		String... sxpBlueprintExternalReferenceCodes) {

		RuntimeException runtimeException = new RuntimeException();

		for (String sxpBlueprintExternalReferenceCode :
				sxpBlueprintExternalReferenceCodes) {

			if (sxpBlueprintExternalReferenceCode == null) {
				continue;
			}

			SXPBlueprint sxpBlueprint =
				_sxpBlueprintLocalService.
					fetchSXPBlueprintByExternalReferenceCode(
						sxpBlueprintExternalReferenceCode,
						GetterUtil.getLong(
							searchRequestBuilder.withSearchContextGet(
								SearchContext::getCompanyId)));

			if (_log.isDebugEnabled()) {
				_log.debug("Search experiences blueprint " + sxpBlueprint);
			}

			try {
				if (sxpBlueprint != null) {
					_sxpBlueprintSearchRequestEnhancer.enhance(
						searchRequestBuilder, sxpBlueprint);
				}
			}
			catch (Exception exception) {
				runtimeException.addSuppressed(exception);
			}
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

	private void _enhanceWithId(
		SearchRequestBuilder searchRequestBuilder, long... sxpBlueprintIds) {

		RuntimeException runtimeException = new RuntimeException();

		for (long sxpBlueprintId : sxpBlueprintIds) {
			if (sxpBlueprintId == 0) {
				continue;
			}

			SXPBlueprint sxpBlueprint =
				_sxpBlueprintLocalService.fetchSXPBlueprint(sxpBlueprintId);

			if (_log.isDebugEnabled()) {
				_log.debug("Search experiences blueprint " + sxpBlueprint);
			}

			try {
				if (sxpBlueprint != null) {
					_sxpBlueprintSearchRequestEnhancer.enhance(
						searchRequestBuilder, sxpBlueprint);
				}
			}
			catch (Exception exception) {
				runtimeException.addSuppressed(exception);
			}
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

	private final List<EvaluatorFunction> _evaluatorFunctions = Arrays.asList(
		this::_contributeSXPBlueprintExternalReferenceCode,
		this::_contributeSXPBlueprintId, this::_contributeSXPBlueprintJSON);

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Reference
	private SXPBlueprintSearchRequestEnhancer
		_sxpBlueprintSearchRequestEnhancer;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.search.request.body.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.collapse.CollapseBuilder;
import com.liferay.portal.search.collapse.CollapseBuilderFactory;
import com.liferay.portal.search.collapse.InnerHitBuilder;
import com.liferay.portal.search.collapse.InnerHitBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.sort.SortConverter;
import com.liferay.search.experiences.rest.dto.v1_0.AdvancedConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.Collapse;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.InnerCollapse;
import com.liferay.search.experiences.rest.dto.v1_0.InnerHit;
import com.liferay.search.experiences.rest.dto.v1_0.Source;

/**
 * @author Gustavo Lima
 * @author Petteri Karttunen
 */
public class AdvancedSXPSearchRequestBodyContributor
	implements SXPSearchRequestBodyContributor {

	public AdvancedSXPSearchRequestBodyContributor(
		CollapseBuilderFactory collapseBuilderFactory,
		InnerHitBuilderFactory innerHitBuilderFactory,
		SortConverter sortConverter) {

		_collapseBuilderFactory = collapseBuilderFactory;
		_innerHitBuilderFactory = innerHitBuilderFactory;
		_sortConverter = sortConverter;
	}

	@Override
	public void contribute(
		Configuration configuration, SearchRequestBuilder searchRequestBuilder,
		SXPParameterData sxpParameterData) {

		AdvancedConfiguration advancedConfiguration =
			configuration.getAdvancedConfiguration();

		if (advancedConfiguration == null) {
			return;
		}

		_setCollapse(searchRequestBuilder, advancedConfiguration.getCollapse());
		_setFields(searchRequestBuilder, advancedConfiguration.getFields());
		_setSourceFields(
			searchRequestBuilder, advancedConfiguration.getSource());
		_setStoredFields(
			searchRequestBuilder, advancedConfiguration.getStored_fields());
	}

	@Override
	public String getName() {
		return "advancedConfiguration";
	}

	private void _setCollapse(
		SearchRequestBuilder searchRequestBuilder, Collapse collapse) {

		if ((collapse == null) || (collapse.getField() == null)) {
			return;
		}

		CollapseBuilder collapseBuilder = _collapseBuilderFactory.builder();

		collapseBuilder.field(collapse.getField());

		if (ArrayUtil.isNotEmpty(collapse.getInnerHits())) {
			for (InnerHit innerHit : collapse.getInnerHits()) {
				collapseBuilder.addInnerHit(_translateInnerHit(innerHit));
			}
		}

		if (collapse.getMaxConcurrentGroupRequests() != null) {
			collapseBuilder.maxConcurrentGroupRequests(
				collapse.getMaxConcurrentGroupRequests());
		}

		searchRequestBuilder.collapse(collapseBuilder.build());
	}

	private void _setFields(
		SearchRequestBuilder searchRequestBuilder, String[] fields) {

		if (fields == null) {
			return;
		}

		if (fields.length == 0) {
			searchRequestBuilder.fields(StringPool.BLANK);
		}
		else {
			searchRequestBuilder.fields(fields);
		}
	}

	private void _setSourceFields(
		SearchRequestBuilder searchRequestBuilder, Source source) {

		if (source == null) {
			return;
		}

		if (source.getExcludes() != null) {
			searchRequestBuilder.fetchSourceExcludes(source.getExcludes());
		}

		if (source.getFetchSource() != null) {
			searchRequestBuilder.fetchSource(source.getFetchSource());
		}

		if (source.getIncludes() != null) {
			searchRequestBuilder.fetchSourceIncludes(source.getIncludes());
		}
	}

	private void _setStoredFields(
		SearchRequestBuilder searchRequestBuilder, String[] storedFields) {

		if (storedFields == null) {
			return;
		}

		if (storedFields.length == 0) {
			searchRequestBuilder.storedFields(StringPool.BLANK);
		}
		else {
			searchRequestBuilder.storedFields(storedFields);
		}
	}

	private com.liferay.portal.search.collapse.InnerHit _translateInnerHit(
		InnerHit innerHit) {

		InnerHitBuilder innerHitBuilder = _innerHitBuilderFactory.builder();

		if (innerHit.getInnerCollapse() != null) {
			InnerCollapse innerCollapse = innerHit.getInnerCollapse();

			innerHitBuilder.innerCollapse(
				new com.liferay.portal.search.collapse.InnerCollapse(
					innerCollapse.getField()));
		}

		if (!Validator.isBlank(innerHit.getName())) {
			innerHitBuilder.name(innerHit.getName());
		}

		if (innerHit.getSize() != null) {
			innerHitBuilder.size(innerHit.getSize());
		}

		if (innerHit.getSorts() != null) {
			for (Object object : innerHit.getSorts()) {
				innerHitBuilder.addSort(_sortConverter.convert(object));
			}
		}

		return innerHitBuilder.build();
	}

	private final CollapseBuilderFactory _collapseBuilderFactory;
	private final InnerHitBuilderFactory _innerHitBuilderFactory;
	private final SortConverter _sortConverter;

}
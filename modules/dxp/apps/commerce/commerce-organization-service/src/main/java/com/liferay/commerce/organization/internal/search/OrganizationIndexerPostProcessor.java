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

package com.liferay.commerce.organization.internal.search;

import com.liferay.portal.kernel.search.BaseIndexerPostProcessor;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.PrefixFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.kernel.model.Organization",
	service = IndexerPostProcessor.class
)
public class OrganizationIndexerPostProcessor extends BaseIndexerPostProcessor {

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter booleanFilter, SearchContext searchContext)
		throws Exception {

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params == null) {
			return;
		}

		String treePath = MapUtil.getString(params, Field.TREE_PATH);

		if (Validator.isNotNull(treePath)) {
			booleanFilter.add(
				new PrefixFilter(Field.TREE_PATH, treePath),
				BooleanClauseOccur.MUST);
		}

		List<String> organizationTreePaths = (List<String>)params.get(
			"organizationTreePaths");

		if (ListUtil.isNotEmpty(organizationTreePaths)) {
			BooleanFilter treePathBooleanFilter = new BooleanFilter();

			for (String organizationTreePath : organizationTreePaths) {
				treePathBooleanFilter.add(
					new PrefixFilter(Field.TREE_PATH, organizationTreePath),
					BooleanClauseOccur.SHOULD);
			}

			booleanFilter.add(treePathBooleanFilter, BooleanClauseOccur.MUST);
		}

		String organizationType = MapUtil.getString(params, Field.TYPE);

		if (Validator.isNotNull(organizationType)) {
			TermsFilter termsFilter = new TermsFilter(Field.TYPE);

			termsFilter.addValue(organizationType);

			booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
		}

		List<Long> excludedOrganizationIds = (List<Long>)params.get(
			"organizationIds");

		if (ListUtil.isNotEmpty(excludedOrganizationIds)) {
			TermsFilter termsFilter = new TermsFilter("organizationId");

			termsFilter.addValues(
				ArrayUtil.toStringArray(
					excludedOrganizationIds.toArray(
						new Long[excludedOrganizationIds.size()])));

			booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
		}
	}

}
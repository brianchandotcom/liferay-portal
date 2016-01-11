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

package com.liferay.portal.kernel.search.facet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raymond Augé
 */
public class ScopeFacet extends MultiValueFacet {

	public ScopeFacet(SearchContext searchContext) {
		super(searchContext);

		setFieldName(Field.GROUP_ID);
	}

	protected long[] addScopeGroup(long groupId) {
		try {
			List<Long> groupIds = new ArrayList<>();

			groupIds.add(groupId);

			Group group = GroupLocalServiceUtil.getGroup(groupId);

			List<Group> groups = GroupLocalServiceUtil.getGroups(
				group.getCompanyId(), Layout.class.getName(),
				group.getGroupId());

			for (Group scopeGroup : groups) {
				groupIds.add(scopeGroup.getGroupId());
			}

			return ArrayUtil.toLongArray(groupIds);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return new long[] {groupId};
	}

	@Override
	protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
		SearchContext searchContext = getSearchContext();

		FacetConfiguration facetConfiguration = getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		long[] groupIds = null;

		if (dataJSONObject.has("values")) {
			JSONArray valuesJSONArray = dataJSONObject.getJSONArray("values");

			groupIds = new long[valuesJSONArray.length()];

			for (int i = 0; i < valuesJSONArray.length(); i++) {
				groupIds[i] = valuesJSONArray.getLong(i);
			}
		}

		if (ArrayUtil.isEmpty(groupIds)) {
			groupIds = searchContext.getGroupIds();
		}

		String groupIdParam = GetterUtil.getString(
			searchContext.getAttribute("groupId"));

		if (Validator.isNotNull(groupIdParam)) {
			long groupId = GetterUtil.getLong(groupIdParam);

			groupIds = addScopeGroup(groupId);
		}

		if (ArrayUtil.isEmpty(groupIds) ||
			((groupIds.length == 1) && (groupIds[0] == 0))) {

			return null;
		}

		BooleanFilter facetBooleanFilter = new BooleanFilter();

		long ownerUserId = searchContext.getOwnerUserId();

		if (ownerUserId > 0) {
			facetBooleanFilter.addRequiredTerm(Field.USER_ID, ownerUserId);
		}

		TermsFilter groupIdsTermsFilter = new TermsFilter(Field.GROUP_ID);
		TermsFilter scopeGroupIdsTermsFilter = new TermsFilter(
			Field.SCOPE_GROUP_ID);

		for (int i = 0; i < groupIds.length; i ++) {
			long groupId = groupIds[i];

			if (groupId <= 0) {
				continue;
			}

			try {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				if (!group.isActive()) {
					continue;
				}

				long parentGroupId = groupId;

				if (group.isLayout()) {
					parentGroupId = group.getParentGroupId();
				}

				groupIdsTermsFilter.addValue(String.valueOf(parentGroupId));

				groupIds[i] = parentGroupId;

				if (group.isLayout() || searchContext.isScopeStrict()) {
					scopeGroupIdsTermsFilter.addValue(String.valueOf(groupId));
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}

		searchContext.setGroupIds(groupIds);

		if (!groupIdsTermsFilter.isEmpty()) {
			facetBooleanFilter.add(
				groupIdsTermsFilter, BooleanClauseOccur.MUST);
		}

		if (!scopeGroupIdsTermsFilter.isEmpty()) {
			facetBooleanFilter.add(
				scopeGroupIdsTermsFilter, BooleanClauseOccur.MUST);
		}

		return BooleanClauseFactoryUtil.createFilter(
			searchContext, facetBooleanFilter, BooleanClauseOccur.MUST);
	}

	private static final Log _log = LogFactoryUtil.getLog(ScopeFacet.class);

}
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;

/**
 * @author Raymond Augé
 */
public class ScopeFacet extends MultiValueFacet {

	public ScopeFacet(SearchContext searchContext) {
		super(searchContext);

		setFieldName(Field.GROUP_ID);
	}

	@Override
	protected BooleanClause doGetFacetClause() {
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

		if ((groupIds == null) || (groupIds.length == 0)) {
			groupIds = searchContext.getGroupIds();
		}

		String groupIdParam = GetterUtil.getString(
			searchContext.getAttribute("groupId"));

		if (Validator.isNotNull(groupIdParam)) {
			long groupId = GetterUtil.getLong(groupIdParam);

			groupIds = addScopeGroup(groupId);
		}

		if ((groupIds == null) || (groupIds.length == 0) ||
			((groupIds.length == 1) && (groupIds[0] == 0))) {

			return null;
		}

		BooleanQuery facetQuery = BooleanQueryFactoryUtil.create(searchContext);

		long ownerUserId = searchContext.getOwnerUserId();

		if (ownerUserId > 0) {
			facetQuery.addRequiredTerm(Field.USER_ID, ownerUserId);
		}

		BooleanQuery groupIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);
		BooleanQuery scopeGroupIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

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

				groupIdsQuery.addTerm(Field.GROUP_ID, parentGroupId);

				groupIds[i] = parentGroupId;

				if (group.isLayout() || searchContext.isScopeStrict()) {
					scopeGroupIdsQuery.addTerm(Field.SCOPE_GROUP_ID, groupId);
				}
			}
			catch (Exception e) {
				continue;
			}
		}

		searchContext.setGroupIds(groupIds);

		if (groupIdsQuery.hasClauses()) {
			try {
				facetQuery.add(groupIdsQuery, BooleanClauseOccur.MUST);
			}
			catch (ParseException pe) {
				_log.error(pe, pe);
			}
		}

		if (scopeGroupIdsQuery.hasClauses()) {
			try {
				facetQuery.add(scopeGroupIdsQuery, BooleanClauseOccur.MUST);
			}
			catch (ParseException pe) {
				_log.error(pe, pe);
			}
		}

		return BooleanClauseFactoryUtil.create(
			searchContext, facetQuery, BooleanClauseOccur.MUST.getName());
	}

	private long[] addScopeGroup(long groupId) {
		Layout layout = getSearchContext().getLayout();

		try {
			if ((layout != null) && (layout.getGroupId() == groupId) &&
				layout.hasScopeGroup()) {

				Group scopeGroup = layout.getScopeGroup();

				return new long[] {groupId, scopeGroup.getGroupId()};
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return new long[] {groupId};
	}

	private static Log _log = LogFactoryUtil.getLog(ScopeFacet.class);

}
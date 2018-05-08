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

package com.liferay.portal.search.internal.contributor.query;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.spi.model.query.contributor.QueryPreFilterContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = QueryPreFilterContributor.class)
public class GroupIdQueryPreFilterContributor
	implements QueryPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long[] groupIds = searchContext.getGroupIds();

		if (ArrayUtil.isEmpty(groupIds) ||
			((groupIds.length == 1) && (groupIds[0] == 0))) {

			return;
		}

		BooleanFilter scopeBooleanFilter = new BooleanFilter();

		long ownerUserId = searchContext.getOwnerUserId();

		if (ownerUserId > 0) {
			scopeBooleanFilter.addRequiredTerm(Field.USER_ID, ownerUserId);
		}

		TermsFilter groupIdsTermsFilter = new TermsFilter(Field.GROUP_ID);
		TermsFilter scopeGroupIdsTermsFilter = new TermsFilter(
			Field.SCOPE_GROUP_ID);

		for (int i = 0; i < groupIds.length; i++) {
			long groupId = groupIds[i];

			if (groupId <= 0) {
				continue;
			}

			try {
				Group group = groupLocalService.getGroup(groupId);

				if (!groupLocalService.isLiveGroupActive(group)) {
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
			scopeBooleanFilter.add(
				groupIdsTermsFilter, BooleanClauseOccur.MUST);
		}

		if (!scopeGroupIdsTermsFilter.isEmpty()) {
			scopeBooleanFilter.add(
				scopeGroupIdsTermsFilter, BooleanClauseOccur.MUST);
		}

		booleanFilter.add(scopeBooleanFilter, BooleanClauseOccur.MUST);
	}

	@Reference
	protected GroupLocalService groupLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		GroupIdQueryPreFilterContributor.class);

}
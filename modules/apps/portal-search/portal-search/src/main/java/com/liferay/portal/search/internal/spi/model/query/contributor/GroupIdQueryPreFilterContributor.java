/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.spi.model.query.contributor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.model.query.contributor.QueryPreFilterContributor;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = QueryPreFilterContributor.class)
public class GroupIdQueryPreFilterContributor
	implements QueryPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long[] groupIds = searchContext.getGroupIds();

		if (ArrayUtil.isEmpty(groupIds) ||
			((groupIds.length == 1) && (groupIds[0] == 0))) {

			_addInactiveGroupsBooleanFilter(booleanFilter, searchContext);

			return;
		}

		BooleanFilter scopeBooleanFilter = new BooleanFilter();

		_addOwnerBooleanFilter(scopeBooleanFilter, searchContext);

		TermsFilter groupIdsTermsFilter = new TermsFilter(Field.GROUP_ID);
		TermsFilter scopeGroupIdsTermsFilter = new TermsFilter(
			Field.SCOPE_GROUP_ID);

		for (int i = 0; i < groupIds.length; i++) {
			long groupId = groupIds[i];

			if (groupId <= 0) {
				continue;
			}

			Group group = _getGroup(groupId);

			if (!_groupLocalService.isLiveGroupActive(group)) {
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

		if (!groupIdsTermsFilter.isEmpty()) {
			scopeBooleanFilter.add(
				groupIdsTermsFilter, BooleanClauseOccur.MUST);
		}

		if (!scopeGroupIdsTermsFilter.isEmpty()) {
			scopeBooleanFilter.add(
				scopeGroupIdsTermsFilter, BooleanClauseOccur.MUST);
		}

		if (scopeBooleanFilter.hasClauses()) {
			booleanFilter.add(scopeBooleanFilter, BooleanClauseOccur.MUST);
		}
	}

	private void _addInactiveGroupsBooleanFilter(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		List<Long> inactiveGroupIds = _groupLocalService.getGroupIds(
			searchContext.getCompanyId(), false);

		if (ListUtil.isEmpty(inactiveGroupIds)) {
			return;
		}

		TermsFilter groupIdTermsFilter = new TermsFilter(Field.GROUP_ID);

		groupIdTermsFilter.addValues(
			ArrayUtil.toStringArray(inactiveGroupIds.toArray(new Long[0])));

		booleanFilter.add(groupIdTermsFilter, BooleanClauseOccur.MUST_NOT);
	}

	private void _addOwnerBooleanFilter(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long ownerUserId = searchContext.getOwnerUserId();

		if (ownerUserId > 0) {
			booleanFilter.addRequiredTerm(Field.USER_ID, ownerUserId);
		}
	}

	private Group _getGroup(long groupId) {
		try {
			return _groupLocalService.getGroup(groupId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	@Reference
	private GroupLocalService _groupLocalService;

}
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

package com.liferay.commerce.user.segment.internal.util;

import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.commerce.user.segment.util.CommerceUserSegmentHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component
public class CommerceUserSegmentHelperImpl
	implements CommerceUserSegmentHelper {

	@Override
	public long[] getCommerceUserSegmentIds(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		Organization organization =
			_commerceOrganizationHelper.getCurrentOrganization(
				httpServletRequest);

		long organizationId = 0;

		if (organization != null) {
			organizationId = organization.getOrganizationId();
		}

		User user = _portal.getUser(httpServletRequest);

		if (user == null) {
			user = _userLocalService.getDefaultUser(
				_portal.getCompanyId(httpServletRequest));
		}

		return _commerceUserSegmentEntryLocalService.
			getCommerceUserSegmentEntryIds(
				_portal.getScopeGroupId(httpServletRequest), organizationId,
				user.getUserId());
	}

	@Override
	public long[] getCommerceUserSegmentIds(
			long groupId, long organizationId, long userId)
		throws PortalException {

		return _commerceUserSegmentEntryLocalService.
			getCommerceUserSegmentEntryIds(groupId, organizationId, userId);
	}

	@Override
	public long[] getUserIds(
			long groupId, long organizationId,
			long[] commerceUserSegmentEntryIds, int start, int end)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(
			"commerceUserSegmentEntryIds", commerceUserSegmentEntryIds);
		attributes.put("organizationId", organizationId);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(group.getCompanyId());
		searchContext.setStart(start);
		searchContext.setEnd(end);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			User.class);

		Hits hits = indexer.search(searchContext, Field.ENTRY_CLASS_PK);

		List<Document> documents = hits.toList();

		Stream<Document> stream = documents.stream();

		return stream.mapToLong(
			field -> GetterUtil.getLong(field.get(Field.ENTRY_CLASS_PK))).
				toArray();
	}

	@Reference
	private CommerceOrganizationHelper _commerceOrganizationHelper;

	@Reference
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}
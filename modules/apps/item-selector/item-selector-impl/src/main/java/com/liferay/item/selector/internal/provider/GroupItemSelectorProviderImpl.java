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

package com.liferay.item.selector.internal.provider;

import com.liferay.item.selector.provider.GroupItemSelectorProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = "group.type=site", service = GroupItemSelectorProvider.class
)
public class GroupItemSelectorProviderImpl
	implements GroupItemSelectorProvider {

	@Override
	public List<Group> getGroups(
		long companyId, long groupId, String keywords, int start, int end) {

		return ListUtil.subList(_searchGroups(companyId, keywords), start, end);
	}

	@Override
	public int getGroupsCount(long companyId, long groupId, String keywords) {
		List<Group> groups = _searchGroups(companyId, keywords);

		return groups.size();
	}

	@Override
	public String getIcon() {
		return "site";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "site");
	}

	@Activate
	protected void activate() {
		_classNameIds = new long[] {
			_classNameLocalService.getClassNameId(Company.class),
			_classNameLocalService.getClassNameId(Group.class),
			_classNameLocalService.getClassNameId(Organization.class)
		};
	}

	private List<Group> _searchGroups(long companyId, String keywords) {
		LinkedHashMap<String, Object> groupParams =
			LinkedHashMapBuilder.<String, Object>put(
				"site", Boolean.TRUE
			).build();

		try {
			return _groupService.search(
				companyId, _classNameIds, keywords, groupParams,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return Collections.emptyList();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupItemSelectorProviderImpl.class);

	private long[] _classNameIds;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private GroupService _groupService;

	@Reference
	private Language _language;

}
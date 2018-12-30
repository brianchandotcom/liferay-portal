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

package com.liferay.content.space.apio.internal.architect.resource;

import com.liferay.apio.architect.annotation.Actions;
import com.liferay.apio.architect.annotation.EntryPoint;
import com.liferay.apio.architect.annotation.Id;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.ActionRouter;
import com.liferay.content.space.apio.architect.model.ContentSpace;
import com.liferay.content.space.apio.architect.util.ContentSpaceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.comparator.GroupIdComparator;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose content space resources through
 * a web API. The resources are mapped from the internal model {@code Group}.
 *
 * @author Javier Gamarra
 * @author Cristina González
 */
@Component(immediate = true, service = ActionRouter.class)
public class ContentSpaceActionRouter implements ActionRouter<ContentSpace> {

	@Actions.Retrieve
	public ContentSpace getContentSpace(@Id long contentSpaceId)
		throws PortalException {

		return new ContentSpaceImpl(
			_groupLocalService.getGroup(contentSpaceId));
	}

	@Actions.Retrieve
	@EntryPoint
	public PageItems<ContentSpace> getPageItems(
		Pagination pagination, Company company) {

		List<Group> groups = _groupLocalService.getActiveGroups(
			company.getCompanyId(), true, true, pagination.getStartPosition(),
			pagination.getEndPosition(), new GroupIdComparator(true));

		Stream<Group> stream = groups.stream();

		List<ContentSpace> contentSpaces = stream.map(
			ContentSpaceImpl::new
		).collect(
			Collectors.toList()
		);

		int count = _groupLocalService.getActiveGroupsCount(
			company.getCompanyId(), true, true);

		return new PageItems<>(contentSpaces, count);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	private static class ContentSpaceImpl implements ContentSpace {

		public ContentSpaceImpl(Group group) {
			_group = group;
		}

		@Override
		public List<String> getAvailableLanguages() {
			return Arrays.asList(
				LocaleUtil.toW3cLanguageIds(_group.getAvailableLanguageIds()));
		}

		@Override
		public Long getCreatorId() {
			return _group.getCreatorUserId();
		}

		@Override
		public String getDescription(Locale locale) {
			return _group.getDescription(locale);
		}

		@Override
		public Long getDocumentsRepositoryId() {
			return _group.getGroupId();
		}

		@Override
		public long getId() {
			return _group.getGroupId();
		}

		@Override
		public String getName(Locale locale) {
			return ContentSpaceUtil.getName(_group, locale);
		}

		private final Group _group;

	}

}
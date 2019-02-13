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

package com.liferay.headless.web.experience.internal.resource.v1_0;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.headless.web.experience.dto.v1_0.ContentStructure;
import com.liferay.headless.web.experience.internal.dto.v1_0.CreatorUtil;
import com.liferay.headless.web.experience.resource.v1_0.ContentStructureResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/content-structure.properties",
	scope = ServiceScope.PROTOTYPE, service = ContentStructureResource.class
)
public class ContentStructureResourceImpl
	extends BaseContentStructureResourceImpl {

	@Override
	public Page<ContentStructure> getContentSpacesContentStructuresPage(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		Group group = _groupService.getGroup(contentSpaceId);
		ClassName className = _classNameService.fetchClassName(
			JournalArticle.class.getName());

		return Page.of(
			transform(
				_ddmStructureService.getStructures(
					company.getCompanyId(), new long[] {group.getGroupId()},
					className.getClassNameId(), pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toContentStructure),
			pagination,
			_ddmStructureService.getStructuresCount(
				company.getCompanyId(), new long[] {group.getGroupId()},
				className.getClassNameId()));
	}

	@Override
	public ContentStructure getContentStructures(Long contentStructuresId)
		throws Exception {

		return _toContentStructure(
			_ddmStructureService.getStructure(contentStructuresId));
	}

	private ContentStructure _toContentStructure(DDMStructure ddmStructure)
		throws Exception {

		return new ContentStructure() {
			{
				setAvailableLanguages(
					LocaleUtil.toW3cLanguageIds(
						ddmStructure.getAvailableLanguageIds()));
				setContentSpace(ddmStructure.getGroupId());
				setCreator(
					CreatorUtil.toCreator(
						_userService.getUserById(ddmStructure.getUserId())));
				setDateCreated(ddmStructure.getCreateDate());
				setDateModified(ddmStructure.getModifiedDate());
				setDescription(
					ddmStructure.getDescription(
						acceptLanguage.getPreferredLocale()));
				setId(ddmStructure.getStructureId());
				setName(
					ddmStructure.getName(acceptLanguage.getPreferredLocale()));
			}
		};
	}

	@Reference
	private ClassNameService _classNameService;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private GroupService _groupService;

	@Reference
	private UserService _userService;

}
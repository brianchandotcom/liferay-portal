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

package com.liferay.headless.web.experience.internal.resource;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.headless.web.experience.dto.ContentStructure;
import com.liferay.headless.web.experience.dto.ContentStructureCollection;
import com.liferay.headless.web.experience.resource.ContentStructureResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameService;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.vulcan.context.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Javier Gamarra
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=headless-web-experience-application.rest)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=1.0.0"
	},
	scope = ServiceScope.PROTOTYPE, service = ContentStructureResource.class
)
public class ContentStructureResourceImpl implements ContentStructureResource {

	@Override
	public ContentStructureCollection<ContentStructure>
			getContentStructureCollection(Pagination pagination, String size)
		throws Exception {

		Company company = _companyService.getCompanyByWebId(
			PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

		Group group = company.getGroup();

		ClassName className = _classNameService.fetchClassName(
			JournalArticle.class.getName());

		List<DDMStructure> ddmStructures = _ddmStructureService.getStructures(
			company.getCompanyId(), new long[] {group.getGroupId()},
			className.getClassNameId(), pagination.getStartPosition(),
			pagination.getEndPosition(), null);

		List<ContentStructure> contentStructures = new ArrayList<>(
			ddmStructures.size());

		for (DDMStructure ddmStructure : ddmStructures) {
			ContentStructure contentStructure = new ContentStructure();

			contentStructure.setId(ddmStructure.getStructureId());

			contentStructures.add(contentStructure);
		}

		int count = _ddmStructureService.getStructuresCount(
			group.getGroupId(), new long[] {className.getClassNameId()},
			className.getClassNameId());

		return new ContentStructureCollection<>(contentStructures, count);
	}

	@Reference
	private ClassNameService _classNameService;

	@Reference
	private CompanyService _companyService;

	@Reference
	private DDMStructureService _ddmStructureService;

}
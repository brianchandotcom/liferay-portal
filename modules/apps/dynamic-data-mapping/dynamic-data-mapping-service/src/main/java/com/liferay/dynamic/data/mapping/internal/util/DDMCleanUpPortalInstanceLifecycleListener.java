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

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateVersionLocalService;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class DDMCleanUpPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstancePreunregistered(Company company)
		throws Exception {

		for (DDMTemplate ddmTemplate :
				_ddmTemplateLocalService.getTemplatesByGroupId(
					company.getGroupId())) {

			_deleteDDMTemplate(ddmTemplate);
		}

		Queue<DDMStructure> toBeVisited = new LinkedList<>(
			_ddmStructureLocalService.getStructures(company.getGroupId()));

		Deque<DDMStructure> visited = new LinkedList<>();

		DDMStructure currentDDMStructure = null;

		while ((currentDDMStructure = toBeVisited.poll()) != null) {
			visited.push(currentDDMStructure);

			toBeVisited.addAll(
				_ddmStructureLocalService.getChildrenStructures(
					currentDDMStructure.getStructureId()));
		}

		for (DDMStructure ddmStructure : visited) {
			long structureId = ddmStructure.getStructureId();

			_ddmStructureLinkLocalService.deleteStructureStructureLinks(
				structureId);

			for (DDMTemplate ddmTemplate :
					_ddmTemplateLocalService.getTemplates(structureId)) {

				_deleteDDMTemplate(ddmTemplate);
			}

			_ddmStructureLocalService.deleteStructure(ddmStructure);
		}
	}

	private void _deleteDDMTemplate(DDMTemplate ddmTemplate)
		throws PortalException {

		_ddmTemplateVersionLocalService.deleteTemplateVersions(
			ddmTemplate.getTemplateId());

		_ddmTemplateLinkLocalService.deleteTemplateLinks(
			ddmTemplate.getTemplateId());

		_ddmTemplateLocalService.deleteTemplate(ddmTemplate);
	}

	@Reference
	private DDMStructureLinkLocalService _ddmStructureLinkLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private DDMTemplateVersionLocalService _ddmTemplateVersionLocalService;

}
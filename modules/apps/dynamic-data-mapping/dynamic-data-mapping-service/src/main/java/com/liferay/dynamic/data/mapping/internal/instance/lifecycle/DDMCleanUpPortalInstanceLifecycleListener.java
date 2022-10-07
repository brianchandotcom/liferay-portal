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

package com.liferay.dynamic.data.mapping.internal.instance.lifecycle;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = PortalInstanceLifecycleListener.class)
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

		Queue<DDMStructure> queue = new LinkedList<>(
			_ddmStructureLocalService.getStructures(company.getGroupId()));

		Deque<DDMStructure> deque = new LinkedList<>();

		DDMStructure currentDDMStructure = null;

		while ((currentDDMStructure = queue.poll()) != null) {
			deque.push(currentDDMStructure);

			queue.addAll(
				_ddmStructureLocalService.getChildrenStructures(
					currentDDMStructure.getStructureId()));
		}

		for (DDMStructure ddmStructure : deque) {
			long structureId = ddmStructure.getStructureId();

			_ddmStructureLinkLocalService.deleteStructureStructureLinks(
				structureId);

			for (DDMTemplate ddmTemplate :
					_ddmTemplateLocalService.getTemplates(structureId)) {

				_deleteDDMTemplate(ddmTemplate);
			}

			_storageEngine.deleteByDDMStructure(ddmStructure.getStructureId());

			_ddmStructureLocalService.deleteStructure(ddmStructure);
		}
	}

	private void _deleteDDMTemplate(DDMTemplate ddmTemplate) throws Exception {
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

	@Reference
	private StorageEngine _storageEngine;

}
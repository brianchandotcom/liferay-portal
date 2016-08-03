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

package com.liferay.knowledge.base.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBTemplateLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class KBTemplateStagedModelDataHandler
	extends BaseStagedModelDataHandler<KBTemplate> {

	public static final String[] CLASS_NAMES = {KBTemplate.class.getName()};

	@Override
	public void deleteStagedModel(KBTemplate kbTemplate)
		throws PortalException {

		_kbTemplateLocalService.deleteKBTemplate(kbTemplate);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		KBTemplate kbTemplate = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (kbTemplate != null) {
			deleteStagedModel(kbTemplate);
		}
	}

	@Override
	public KBTemplate fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _kbTemplateLocalService.fetchKBTemplateByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<KBTemplate> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _kbTemplateLocalService.getKBTemplatesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<KBTemplate>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(KBTemplate kbTemplate) {
		return kbTemplate.getTitle();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, KBTemplate kbTemplate)
		throws Exception {

		Element kbTemplateElement = portletDataContext.getExportDataElement(
			kbTemplate);

		portletDataContext.addClassedModel(
			kbTemplateElement, ExportImportPathUtil.getModelPath(kbTemplate),
			kbTemplate);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, KBTemplate kbTemplate)
		throws Exception {

		long userId = portletDataContext.getUserId(kbTemplate.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			kbTemplate);

		KBTemplate importedKBTemplate = null;

		if (portletDataContext.isDataStrategyMirror()) {
			KBTemplate existingKBTemplate = fetchStagedModelByUuidAndGroupId(
				kbTemplate.getUuid(), portletDataContext.getScopeGroupId());

			if (existingKBTemplate == null) {
				serviceContext.setUuid(kbTemplate.getUuid());

				importedKBTemplate = _kbTemplateLocalService.addKBTemplate(
					userId, kbTemplate.getTitle(), kbTemplate.getContent(),
					serviceContext);
			}
			else {
				importedKBTemplate = _kbTemplateLocalService.updateKBTemplate(
					existingKBTemplate.getKbTemplateId(), kbTemplate.getTitle(),
					kbTemplate.getContent(), serviceContext);
			}
		}
		else {
			importedKBTemplate = _kbTemplateLocalService.addKBTemplate(
				userId, kbTemplate.getTitle(), kbTemplate.getContent(),
				serviceContext);
		}

		portletDataContext.importClassedModel(kbTemplate, importedKBTemplate);
	}

	@Reference(unbind = "-")
	protected void setKBTemplateLocalService(
		KBTemplateLocalService kbTemplateLocalService) {

		_kbTemplateLocalService = kbTemplateLocalService;
	}

	private KBTemplateLocalService _kbTemplateLocalService;

}
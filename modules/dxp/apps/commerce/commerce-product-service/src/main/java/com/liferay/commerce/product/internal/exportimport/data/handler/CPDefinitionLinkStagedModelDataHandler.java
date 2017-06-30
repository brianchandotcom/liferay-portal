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

package com.liferay.commerce.product.internal.exportimport.data.handler;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class CPDefinitionLinkStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPDefinitionLink> {

	public static final String[] CLASS_NAMES =
		{CPDefinitionLink.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionLink cpDefinitionLink)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, cpDefinitionLink,
			cpDefinitionLink.getCPDefinition1(),
			PortletDataContext.REFERENCE_TYPE_PARENT);
		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, cpDefinitionLink,
			cpDefinitionLink.getCPDefinition2(),
			PortletDataContext.REFERENCE_TYPE_STRONG);

		Element cpDefinitionLinkElement =
			portletDataContext.getExportDataElement(cpDefinitionLink);

		portletDataContext.addClassedModel(
			cpDefinitionLinkElement,
			ExportImportPathUtil.getModelPath(cpDefinitionLink),
			cpDefinitionLink);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpDefinitionLinkId)
		throws Exception {

		CPDefinitionLink existingCPDefinitionLink = fetchMissingReference(
			uuid, groupId);

		Map<Long, Long> cpDefinitionLinkIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinitionLink.class);

		cpDefinitionLinkIds.put(
			cpDefinitionLinkId,
			existingCPDefinitionLink.getCPDefinitionLinkId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionLink cpDefinitionLink)
		throws Exception {

		Map<Long, Long> cpDefinitionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinition.class);

		long cpDefinitionId1 = MapUtil.getLong(
			cpDefinitionIds, cpDefinitionLink.getCPDefinitionId1(),
			cpDefinitionLink.getCPDefinitionId1());
		long cpDefinitionId2 = MapUtil.getLong(
			cpDefinitionIds, cpDefinitionLink.getCPDefinitionId2(),
			cpDefinitionLink.getCPDefinitionId2());

		CPDefinitionLink importedCPDefinitionLink =
			(CPDefinitionLink)cpDefinitionLink.clone();

		importedCPDefinitionLink.setGroupId(
			portletDataContext.getScopeGroupId());
		importedCPDefinitionLink.setCPDefinitionId1(cpDefinitionId1);
		importedCPDefinitionLink.setCPDefinitionId1(cpDefinitionId2);

		CPDefinitionLink existingCPDefinitionLink =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpDefinitionLink.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCPDefinitionLink == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPDefinitionLink = _stagedModelRepository.addStagedModel(
				portletDataContext, importedCPDefinitionLink);
		}
		else {
			importedCPDefinitionLink.setCPDefinitionLinkId(
				existingCPDefinitionLink.getCPDefinitionLinkId());

			importedCPDefinitionLink = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedCPDefinitionLink);
		}

		portletDataContext.importClassedModel(
			cpDefinitionLink, importedCPDefinitionLink);
	}

	@Override
	protected StagedModelRepository<CPDefinitionLink>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPDefinitionLink)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPDefinitionLink> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CPDefinitionLink> _stagedModelRepository;

}
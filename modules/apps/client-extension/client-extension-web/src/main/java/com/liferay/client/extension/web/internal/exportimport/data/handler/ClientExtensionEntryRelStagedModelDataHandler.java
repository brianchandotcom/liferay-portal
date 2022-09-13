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

package com.liferay.client.extension.web.internal.exportimport.data.handler;

import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class ClientExtensionEntryRelStagedModelDataHandler
	extends BaseStagedModelDataHandler<ClientExtensionEntryRel> {

	public static final String[] CLASS_NAMES = {
		ClientExtensionEntryRel.class.getName()
	};

	@Override
	public void deleteStagedModel(
			ClientExtensionEntryRel clientExtensionEntryRel)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(clientExtensionEntryRel);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(
			uuid, groupId, className, extraData);
	}

	@Override
	public List<ClientExtensionEntryRel> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return null;
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			ClientExtensionEntryRel clientExtensionEntryRel)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(
			clientExtensionEntryRel);

		element.addAttribute(
			"client-extension-entry-rel-class-name",
			_portal.getClassName(clientExtensionEntryRel.getClassNameId()));

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(clientExtensionEntryRel),
			clientExtensionEntryRel);
	}

	@Override
	protected void doImportMissingReference(
		PortletDataContext portletDataContext, String uuid, long groupId,
		long clientExtensionEntryRelId) {

		ClientExtensionEntryRel existingClientExtensionEntryRel =
			fetchMissingReference(uuid, groupId);

		if (existingClientExtensionEntryRel == null) {
			return;
		}

		Map<Long, Long> layoutClassedModelUsageids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				ClientExtensionEntryRel.class);

		layoutClassedModelUsageids.put(
			clientExtensionEntryRelId,
			existingClientExtensionEntryRel.getClientExtensionEntryRelId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			ClientExtensionEntryRel clientExtensionEntryRel)
		throws Exception {

		ClientExtensionEntryRel importedClientExtensionEntryRel =
			(ClientExtensionEntryRel)clientExtensionEntryRel.clone();

		importedClientExtensionEntryRel.setGroupId(
			portletDataContext.getScopeGroupId());
		importedClientExtensionEntryRel.setCompanyId(
			portletDataContext.getCompanyId());

		importedClientExtensionEntryRel.setClassNameId(
			_portal.getClassNameId(clientExtensionEntryRel.getClassName()));

		Map<Long, Long> classPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				clientExtensionEntryRel.getClassName());

		long classPK = MapUtil.getLong(
			classPKs, clientExtensionEntryRel.getClassPK(),
			clientExtensionEntryRel.getClassPK());

		importedClientExtensionEntryRel.setClassPK(classPK);

		Element element = portletDataContext.getImportDataStagedModelElement(
			clientExtensionEntryRel);

		ClientExtensionEntryRel existingClientExtensionEntryRel =
			_clientExtensionEntryRelLocalService.fetchClientExtensionEntryRel(
				_portal.getClassNameId(
					element.attributeValue(
						"client-extension-entry-rel-class-name")),
				classPK, importedClientExtensionEntryRel.getType());

		if (existingClientExtensionEntryRel == null) {
			existingClientExtensionEntryRel =
				_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
					clientExtensionEntryRel.getUuid(),
					portletDataContext.getScopeGroupId());
		}

		if ((existingClientExtensionEntryRel == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedClientExtensionEntryRel =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedClientExtensionEntryRel);
		}
		else {
			importedClientExtensionEntryRel.setMvccVersion(
				existingClientExtensionEntryRel.getMvccVersion());
			importedClientExtensionEntryRel.setClientExtensionEntryRelId(
				existingClientExtensionEntryRel.getClientExtensionEntryRelId());

			importedClientExtensionEntryRel =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedClientExtensionEntryRel);
		}

		portletDataContext.importClassedModel(
			clientExtensionEntryRel, importedClientExtensionEntryRel);
	}

	@Override
	protected StagedModelRepository<ClientExtensionEntryRel>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(model.class.name=com.liferay.client.extension.model.ClientExtensionEntryRel)",
		unbind = "-"
	)
	private StagedModelRepository<ClientExtensionEntryRel>
		_stagedModelRepository;

}
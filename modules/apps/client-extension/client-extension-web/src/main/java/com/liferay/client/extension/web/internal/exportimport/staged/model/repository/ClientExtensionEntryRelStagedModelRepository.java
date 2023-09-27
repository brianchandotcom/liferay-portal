/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.web.internal.exportimport.staged.model.repository;

import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "model.class.name=com.liferay.client.extension.model.ClientExtensionEntryRel",
	service = StagedModelRepository.class
)
public class ClientExtensionEntryRelStagedModelRepository
	implements StagedModelRepository<ClientExtensionEntryRel> {

	@Override
	public ClientExtensionEntryRel addStagedModel(
			PortletDataContext portletDataContext,
			ClientExtensionEntryRel clientExtensionEntryRel)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			clientExtensionEntryRel.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			clientExtensionEntryRel);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(clientExtensionEntryRel.getUuid());
		}

		return _clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			userId, clientExtensionEntryRel.getGroupId(),
			clientExtensionEntryRel.getClassNameId(),
			clientExtensionEntryRel.getClassPK(),
			clientExtensionEntryRel.getCETExternalReferenceCode(),
			clientExtensionEntryRel.getType(),
			clientExtensionEntryRel.getTypeSettings(), serviceContext);
	}

	@Override
	public void deleteStagedModel(
			ClientExtensionEntryRel clientExtensionEntryRel)
		throws PortalException {

		_clientExtensionEntryRelLocalService.deleteClientExtensionEntryRel(
			clientExtensionEntryRel);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		ClientExtensionEntryRel clientExtensionEntryRel =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (clientExtensionEntryRel != null) {
			deleteStagedModel(clientExtensionEntryRel);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public ClientExtensionEntryRel fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _clientExtensionEntryRelLocalService.
			fetchClientExtensionEntryRelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<ClientExtensionEntryRel> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _clientExtensionEntryRelLocalService.
			getClientExtensionEntryRelsByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _clientExtensionEntryRelLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public ClientExtensionEntryRel getStagedModel(long id)
		throws PortalException {

		return _clientExtensionEntryRelLocalService.getClientExtensionEntryRel(
			id);
	}

	@Override
	public ClientExtensionEntryRel saveStagedModel(
			ClientExtensionEntryRel clientExtensionEntryRel)
		throws PortalException {

		return _clientExtensionEntryRelLocalService.
			updateClientExtensionEntryRel(clientExtensionEntryRel);
	}

	@Override
	public ClientExtensionEntryRel updateStagedModel(
			PortletDataContext portletDataContext,
			ClientExtensionEntryRel clientExtensionEntryRel)
		throws PortalException {

		return _clientExtensionEntryRelLocalService.
			updateClientExtensionEntryRel(
				clientExtensionEntryRel.getClientExtensionEntryRelId(),
				clientExtensionEntryRel.getClassNameId(),
				clientExtensionEntryRel.getClassPK(),
				clientExtensionEntryRel.getCETExternalReferenceCode(),
				clientExtensionEntryRel.getType(),
				clientExtensionEntryRel.getTypeSettings());
	}

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

}
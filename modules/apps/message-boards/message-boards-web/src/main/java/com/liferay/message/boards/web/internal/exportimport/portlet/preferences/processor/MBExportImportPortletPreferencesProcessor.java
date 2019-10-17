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

package com.liferay.message.boards.web.internal.exportimport.portlet.preferences.processor;

import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBBan;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThreadFlag;
import com.liferay.message.boards.service.MBBanLocalService;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBThreadFlagLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
	service = ExportImportPortletPreferencesProcessor.class
)
public class MBExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return ListUtil.fromArray(_mbRatingsExporterImporterCapability);
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return ListUtil.fromArray(_mbRatingsExporterImporterCapability);
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		if (!_exportImportHelper.isExportPortletData(portletDataContext)) {
			return portletPreferences;
		}

		try {
			portletDataContext.addPortletPermissions(MBConstants.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(MBPortletKeys.MESSAGE_BOARDS);
			pde.setType(PortletDataException.EXPORT_PORTLET_PERMISSIONS);

			throw pde;
		}

		try {
			String namespace = _mbPortletDataHandler.getNamespace();

			String portletId = portletDataContext.getPortletId();

			if (portletDataContext.getBooleanParameter(
					namespace, "categories") ||
				portletDataContext.getBooleanParameter(namespace, "messages")) {

				ActionableDynamicQuery categoryActionableDynamicQuery =
					_mbCategoryLocalService.getExportActionableDynamicQuery(
						portletDataContext);

				categoryActionableDynamicQuery.setPerformActionMethod(
					(MBCategory mbCategory) ->
						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext, portletId, mbCategory));

				categoryActionableDynamicQuery.performActions();
			}

			if (portletDataContext.getBooleanParameter(namespace, "messages")) {
				StagedModelRepository<?> mbMessageStagedModelRepository =
					StagedModelRepositoryRegistryUtil.getStagedModelRepository(
						MBMessage.class.getName());

				ActionableDynamicQuery messageActionableDynamicQuery =
					mbMessageStagedModelRepository.
						getExportActionableDynamicQuery(portletDataContext);

				messageActionableDynamicQuery.setPerformActionMethod(
					(MBMessage mbMessage) ->
						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext, portletId, mbMessage));

				messageActionableDynamicQuery.performActions();
			}

			if (portletDataContext.getBooleanParameter(
					namespace, "thread-flags")) {

				ActionableDynamicQuery threadFlagActionableDynamicQuery =
					_mbThreadFlagLocalService.getExportActionableDynamicQuery(
						portletDataContext);

				threadFlagActionableDynamicQuery.setPerformActionMethod(
					(MBThreadFlag mbThreadFlag) ->
						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext, portletId, mbThreadFlag));

				threadFlagActionableDynamicQuery.performActions();
			}

			if (portletDataContext.getBooleanParameter(
					namespace, "user-bans")) {

				ActionableDynamicQuery banActionableDynamicQuery =
					_mbBanLocalService.getExportActionableDynamicQuery(
						portletDataContext);

				banActionableDynamicQuery.setPerformActionMethod(
					(MBBan mbBan) ->
						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext, portletId, mbBan));

				banActionableDynamicQuery.performActions();
			}
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(MBPortletKeys.MESSAGE_BOARDS);
			pde.setType(PortletDataException.EXPORT_PORTLET_DATA);

			throw pde;
		}

		return portletPreferences;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			portletDataContext.importPortletPermissions(
				MBConstants.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(MBPortletKeys.MESSAGE_BOARDS);
			pde.setType(PortletDataException.IMPORT_PORTLET_PERMISSIONS);

			throw pde;
		}

		String namespace = _mbPortletDataHandler.getNamespace();

		if (portletDataContext.getBooleanParameter(namespace, "categories") ||
			portletDataContext.getBooleanParameter(namespace, "messages")) {

			Element categoriesElement =
				portletDataContext.getImportDataGroupElement(MBCategory.class);

			List<Element> categoryElements = categoriesElement.elements();

			for (Element categoryElement : categoryElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, categoryElement);
			}
		}

		if (portletDataContext.getBooleanParameter(namespace, "messages")) {
			Element messagesElement =
				portletDataContext.getImportDataGroupElement(MBMessage.class);

			List<Element> messageElements = messagesElement.elements();

			for (Element messageElement : messageElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, messageElement);
			}
		}

		if (portletDataContext.getBooleanParameter(namespace, "thread-flags")) {
			Element threadFlagsElement =
				portletDataContext.getImportDataGroupElement(
					MBThreadFlag.class);

			List<Element> threadFlagElements = threadFlagsElement.elements();

			for (Element threadFlagElement : threadFlagElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, threadFlagElement);
			}
		}

		if (portletDataContext.getBooleanParameter(namespace, "user-bans")) {
			Element userBansElement =
				portletDataContext.getImportDataGroupElement(MBBan.class);

			List<Element> userBanElements = userBansElement.elements();

			for (Element userBanElement : userBanElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, userBanElement);
			}
		}

		return portletPreferences;
	}

	@Reference(unbind = "-")
	protected void setMBBanLocalService(MBBanLocalService mbBanLocalService) {
		_mbBanLocalService = mbBanLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBCategoryLocalService(
		MBCategoryLocalService mbCategoryLocalService) {

		_mbCategoryLocalService = mbCategoryLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBThreadFlagLocalService(
		MBThreadFlagLocalService mbThreadFlagLocalService) {

		_mbThreadFlagLocalService = mbThreadFlagLocalService;
	}

	@Reference
	private ExportImportHelper _exportImportHelper;

	private MBBanLocalService _mbBanLocalService;
	private MBCategoryLocalService _mbCategoryLocalService;

	@Reference(
		target = "(javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS + ")"
	)
	private PortletDataHandler _mbPortletDataHandler;

	@Reference
	private MBRatingsExporterImporterCapability
		_mbRatingsExporterImporterCapability;

	private MBThreadFlagLocalService _mbThreadFlagLocalService;

}
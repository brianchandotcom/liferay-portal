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

package com.liferay.layout.internal.exportimport.data.handler;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	service = {
		LayoutClassedModelUsageStagedModelDataHandler.class,
		StagedModelDataHandler.class
	}
)
public class LayoutClassedModelUsageStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutClassedModelUsage> {

	public static final String[] CLASS_NAMES = {
		LayoutClassedModelUsage.class.getName()
	};

	@Override
	public void deleteStagedModel(
			LayoutClassedModelUsage layoutClassedModelUsage)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(layoutClassedModelUsage);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(
			uuid, groupId, className, extraData);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutClassedModelUsage layoutClassedModelUsage)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(
			layoutClassedModelUsage);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				layoutClassedModelUsage.getClassName());

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if (ExportImportThreadLocal.isStagingInProcess() &&
			(assetRendererFactory != null) &&
			stagingGroupHelper.isStagedPortlet(
				portletDataContext.getScopeGroupId(),
				assetRendererFactory.getPortletId())) {

			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
				layoutClassedModelUsage.getClassPK());

			if (assetRenderer.getStatus() ==
					WorkflowConstants.STATUS_APPROVED) {

				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, layoutClassedModelUsage,
					(StagedModel)assetRenderer.getAssetObject(),
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
			}
		}

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(layoutClassedModelUsage),
			layoutClassedModelUsage);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long layoutClassedModelUsageId)
		throws Exception {

		LayoutClassedModelUsage existingLayoutClassedModelUsage =
			fetchMissingReference(uuid, groupId);

		if (existingLayoutClassedModelUsage == null) {
			return;
		}

		Map<Long, Long> layoutClassedModelUsageids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				LayoutClassedModelUsage.class);

		layoutClassedModelUsageids.put(
			layoutClassedModelUsageId,
			existingLayoutClassedModelUsage.getLayoutClassedModelUsageId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutClassedModelUsage layoutClassedModelUsage)
		throws Exception {

		LayoutClassedModelUsage importedLayoutClassedModelUsage =
			(LayoutClassedModelUsage)layoutClassedModelUsage.clone();

		importedLayoutClassedModelUsage.setGroupId(
			portletDataContext.getScopeGroupId());

		Map<Long, Long> plids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		importedLayoutClassedModelUsage.setPlid(
			MapUtil.getLong(
				plids, layoutClassedModelUsage.getPlid(),
				layoutClassedModelUsage.getPlid()));

		importedLayoutClassedModelUsage.setClassNameId(
			_portal.getClassNameId(layoutClassedModelUsage.getClassName()));

		Map<Long, Long> classPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				layoutClassedModelUsage.getClassName());

		importedLayoutClassedModelUsage.setClassPK(
			MapUtil.getLong(
				classPKs, layoutClassedModelUsage.getClassPK(),
				layoutClassedModelUsage.getClassPK()));

		LayoutClassedModelUsage existingLayoutClassedModelUsage =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				layoutClassedModelUsage.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingLayoutClassedModelUsage == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedLayoutClassedModelUsage =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedLayoutClassedModelUsage);
		}
		else {
			importedLayoutClassedModelUsage.setMvccVersion(
				existingLayoutClassedModelUsage.getMvccVersion());
			importedLayoutClassedModelUsage.setLayoutClassedModelUsageId(
				existingLayoutClassedModelUsage.getLayoutClassedModelUsageId());

			importedLayoutClassedModelUsage =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedLayoutClassedModelUsage);
		}

		portletDataContext.importClassedModel(
			layoutClassedModelUsage, importedLayoutClassedModelUsage);
	}

	@Override
	protected StagedModelRepository<LayoutClassedModelUsage>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference
	private Portal _portal;

	@Reference(
		target = "(model.class.name=com.liferay.layout.model.LayoutClassedModelUsage)",
		unbind = "-"
	)
	private StagedModelRepository<LayoutClassedModelUsage>
		_stagedModelRepository;

}
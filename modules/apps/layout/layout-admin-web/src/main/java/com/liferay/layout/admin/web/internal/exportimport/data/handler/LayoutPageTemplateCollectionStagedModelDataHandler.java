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

package com.liferay.layout.admin.web.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.xml.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class LayoutPageTemplateCollectionStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutPageTemplateCollection> {

	public static final String[] CLASS_NAMES = {
		LayoutPageTemplateCollection.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {

		return layoutPageTemplateCollection.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws Exception {

		Element layoutPageTemplateCollectionElement =
			portletDataContext.getExportDataElement(
				layoutPageTemplateCollection);

		portletDataContext.addClassedModel(
			layoutPageTemplateCollectionElement,
			ExportImportPathUtil.getModelPath(layoutPageTemplateCollection),
			layoutPageTemplateCollection);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws Exception {

		LayoutPageTemplateCollection importedLayoutPageTemplateCollection =
			(LayoutPageTemplateCollection)layoutPageTemplateCollection.clone();

		importedLayoutPageTemplateCollection.setGroupId(
			portletDataContext.getScopeGroupId());

		LayoutPageTemplateCollection existingLayoutPageTemplateCollection =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				layoutPageTemplateCollection.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingLayoutPageTemplateCollection == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedLayoutPageTemplateCollection =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedLayoutPageTemplateCollection);
		}
		else {
			importedLayoutPageTemplateCollection.setMvccVersion(
				existingLayoutPageTemplateCollection.getMvccVersion());
			importedLayoutPageTemplateCollection.
				setLayoutPageTemplateCollectionId(
					existingLayoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId());

			importedLayoutPageTemplateCollection =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedLayoutPageTemplateCollection);
		}

		portletDataContext.importClassedModel(
			layoutPageTemplateCollection, importedLayoutPageTemplateCollection);
	}

	@Override
	protected StagedModelRepository<LayoutPageTemplateCollection>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateCollection)",
		unbind = "-"
	)
	private StagedModelRepository<LayoutPageTemplateCollection>
		_stagedModelRepository;

}
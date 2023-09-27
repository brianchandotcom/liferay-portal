/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = StagedModelDataHandler.class)
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
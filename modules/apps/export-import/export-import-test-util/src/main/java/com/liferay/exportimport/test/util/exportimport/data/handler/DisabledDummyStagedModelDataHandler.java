/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test.util.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.test.util.model.DisabledDummy;
import com.liferay.exportimport.test.util.model.DummyReference;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = StagedModelDataHandler.class)
public class DisabledDummyStagedModelDataHandler
	extends BaseStagedModelDataHandler<DisabledDummy> {

	public static final String[] CLASS_NAMES = {DisabledDummy.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, DisabledDummy disabledDummy)
		throws Exception {

		Element disabledDummyElement = portletDataContext.getExportDataElement(
			disabledDummy);

		List<DummyReference> dummyReferences =
			disabledDummy.getDummyReferences();

		for (DummyReference dummyReference : dummyReferences) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, disabledDummy, dummyReference,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}

		portletDataContext.addClassedModel(
			disabledDummyElement,
			ExportImportPathUtil.getModelPath(disabledDummy), disabledDummy);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long id)
		throws Exception {

		DisabledDummy existingDisabledDummy = fetchMissingReference(
			uuid, groupId);

		if (existingDisabledDummy == null) {
			return;
		}

		Map<Long, Long> ids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DisabledDummy.class);

		ids.put(id, existingDisabledDummy.getId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DisabledDummy disabledDummy)
		throws Exception {

		DisabledDummy importedDisabledDummy =
			(DisabledDummy)disabledDummy.clone();

		importedDisabledDummy.setGroupId(portletDataContext.getScopeGroupId());

		DisabledDummy existingDisabledDummy =
			_disabledDummyStagedModelRepository.
				fetchStagedModelByUuidAndGroupId(
					importedDisabledDummy.getUuid(),
					portletDataContext.getScopeGroupId());

		if ((existingDisabledDummy == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			_disabledDummyStagedModelRepository.addStagedModel(
				portletDataContext, importedDisabledDummy);
		}
		else {
			importedDisabledDummy.setId(existingDisabledDummy.getId());

			_disabledDummyStagedModelRepository.updateStagedModel(
				portletDataContext, importedDisabledDummy);
		}
	}

	@Override
	protected StagedModelRepository<DisabledDummy> getStagedModelRepository() {
		return _disabledDummyStagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.exportimport.test.util.model.DisabledDummy)"
	)
	private StagedModelRepository<DisabledDummy>
		_disabledDummyStagedModelRepository;

}
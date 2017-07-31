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

package com.liferay.commerce.currency.internal.exportimport.data.handler;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class CommerceCurrencyStagedModelDataHandler
	extends BaseStagedModelDataHandler<CommerceCurrency> {

	public static final String[] CLASS_NAMES =
		{CommerceCurrency.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CommerceCurrency commerceCurrency) {
		return commerceCurrency.getNameCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CommerceCurrency commerceCurrency)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(
			commerceCurrency);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(commerceCurrency),
			commerceCurrency);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long commerceCurrencyId)
		throws Exception {

		CommerceCurrency existingCommerceCurrency = fetchMissingReference(
			uuid, groupId);

		Map<Long, Long> commerceCurrencyIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CommerceCurrency.class);

		commerceCurrencyIds.put(
			commerceCurrencyId,
			existingCommerceCurrency.getCommerceCurrencyId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CommerceCurrency commerceCurrency)
		throws Exception {

		CommerceCurrency importedCommerceCurrency =
			(CommerceCurrency)commerceCurrency.clone();

		importedCommerceCurrency.setGroupId(
			portletDataContext.getScopeGroupId());

		CommerceCurrency existingCommerceCurrency =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				commerceCurrency.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCommerceCurrency == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCommerceCurrency = _stagedModelRepository.addStagedModel(
				portletDataContext, importedCommerceCurrency);
		}
		else {
			importedCommerceCurrency.setCommerceCurrencyId(
				existingCommerceCurrency.getCommerceCurrencyId());

			importedCommerceCurrency = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedCommerceCurrency);
		}

		portletDataContext.importClassedModel(
			commerceCurrency, importedCommerceCurrency);
	}

	@Override
	protected StagedModelRepository<CommerceCurrency>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.currency.model.CommerceCurrency)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CommerceCurrency> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CommerceCurrency> _stagedModelRepository;

}
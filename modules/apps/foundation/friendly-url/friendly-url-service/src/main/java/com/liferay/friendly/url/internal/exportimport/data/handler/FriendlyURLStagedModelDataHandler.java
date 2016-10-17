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

package com.liferay.friendly.url.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.friendly.url.model.FriendlyURL;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class FriendlyURLStagedModelDataHandler
	extends BaseStagedModelDataHandler<FriendlyURL> {

	public static final String[] CLASS_NAMES = {FriendlyURL.class.getName()};

	@Override
	public List<FriendlyURL> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Reference(
		target = "(model.class.name=com.liferay.friendly.url.model.FriendlyURL)",
		unbind = "-"
	)
	public void setStagedModelRepository(
		StagedModelRepository<FriendlyURL> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, FriendlyURL friendlyURL)
		throws Exception {

		Element friendlyURLElement = portletDataContext.getExportDataElement(
			friendlyURL);

		friendlyURLElement.addAttribute(
			"resource-class-name", friendlyURL.getClassName());

		portletDataContext.addClassedModel(
			friendlyURLElement, ExportImportPathUtil.getModelPath(friendlyURL),
			friendlyURL);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, FriendlyURL friendlyURL)
		throws Exception {

		Element friendlyURLElement =
			portletDataContext.getImportDataStagedModelElement(friendlyURL);

		String className = friendlyURLElement.attributeValue(
			"resource-class-name");

		long classNameId = _classNameLocalService.getClassNameId(className);

		Map<Long, Long> newPrimaryKeysMap =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(className);

		long classPK = MapUtil.getLong(
			newPrimaryKeysMap, friendlyURL.getClassPK());

		FriendlyURL existingFriendlyURL = fetchStagedModelByUuidAndGroupId(
			friendlyURL.getUuid(), friendlyURL.getGroupId());

		if ((existingFriendlyURL == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			FriendlyURL importedFriendlyURL = (FriendlyURL)friendlyURL.clone();

			importedFriendlyURL.setCompanyId(portletDataContext.getCompanyId());
			importedFriendlyURL.setGroupId(
				portletDataContext.getScopeGroupId());
			importedFriendlyURL.setClassNameId(classNameId);
			importedFriendlyURL.setClassPK(classPK);

			_stagedModelRepository.addStagedModel(
				portletDataContext, importedFriendlyURL);
		}
		else {
			existingFriendlyURL.setMain(friendlyURL.isMain());

			_stagedModelRepository.updateStagedModel(
				portletDataContext, existingFriendlyURL);
		}
	}

	@Override
	protected StagedModelRepository<FriendlyURL> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	@Reference(unbind = "-")
	protected void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	private ClassNameLocalService _classNameLocalService;
	private StagedModelRepository<FriendlyURL> _stagedModelRepository;

}
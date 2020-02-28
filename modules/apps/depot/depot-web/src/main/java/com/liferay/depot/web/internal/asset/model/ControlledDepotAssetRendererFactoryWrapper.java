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

package com.liferay.depot.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GroupThreadLocal;

/**
 * @author Alejandro Tardín
 */
public class ControlledDepotAssetRendererFactoryWrapper
	extends DepotAssetRendererFactoryWrapper {

	public ControlledDepotAssetRendererFactoryWrapper(
		AssetRendererFactory assetRendererFactory,
		DepotApplicationController depotApplicationController,
		DepotEntryLocalService depotEntryLocalService,
		GroupLocalService groupLocalService) {

		_assetRendererFactory = assetRendererFactory;
		_depotApplicationController = depotApplicationController;
		_depotEntryLocalService = depotEntryLocalService;
		_groupLocalService = groupLocalService;
	}

	@Override
	public ClassTypeReader getClassTypeReader() {
		if (isSelectable()) {
			return new DepotClassTypeReader(
				super.getClassTypeReader(), _depotEntryLocalService);
		}

		return super.getClassTypeReader();
	}

	@Override
	public boolean isSelectable() {
		Group group = _getGroup();

		if ((group != null) && (group.getType() == GroupConstants.TYPE_DEPOT) &&
			!_depotApplicationController.isClassNameEnabled(
				getClassName(), group.getGroupId())) {

			return false;
		}

		return _assetRendererFactory.isSelectable();
	}

	@Override
	protected AssetRendererFactory getAssetRendererFactory() {
		return _assetRendererFactory;
	}

	private Group _getGroup() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return _groupLocalService.fetchGroup(GroupThreadLocal.getGroupId());
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		if (themeDisplay != null) {
			return themeDisplay.getScopeGroup();
		}

		return _groupLocalService.fetchGroup(serviceContext.getScopeGroupId());
	}

	private final AssetRendererFactory _assetRendererFactory;
	private final DepotApplicationController _depotApplicationController;
	private final DepotEntryLocalService _depotEntryLocalService;
	private final GroupLocalService _groupLocalService;

}
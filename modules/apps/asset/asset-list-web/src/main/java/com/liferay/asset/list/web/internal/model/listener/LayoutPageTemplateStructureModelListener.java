/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.web.internal.model.listener;

import com.liferay.asset.list.service.AssetListEntryUsageLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = ModelListener.class)
public class LayoutPageTemplateStructureModelListener
	extends BaseModelListener<LayoutPageTemplateStructure> {

	@Override
	public void onBeforeRemove(
			LayoutPageTemplateStructure layoutPageTemplateStructure)
		throws ModelListenerException {

		_assetListEntryUsageLocalService.deleteAssetListEntryUsages(
			_portal.getClassNameId(
				CollectionStyledLayoutStructureItem.class.getName()),
			layoutPageTemplateStructure.getPlid());
	}

	@Reference
	private AssetListEntryUsageLocalService _assetListEntryUsageLocalService;

	@Reference
	private Portal _portal;

}
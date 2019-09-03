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

package com.liferay.layout.content.page.editor.web.internal.model.listener;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.service.AssetEntryUsageLocalService;
import com.liferay.layout.content.page.editor.web.internal.util.MappedContentUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;

import java.util.Optional;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ModelListener.class)
public class LayoutPageTemplateStructureModelListener
	extends BaseModelListener<LayoutPageTemplateStructure> {

	@Override
	public void onAfterUpdate(
			LayoutPageTemplateStructure layoutPageTemplateStructure)
		throws ModelListenerException {

		try {
			Set<AssetEntry> assetEntries =
				MappedContentUtil.getLayoutMappedAssetEntries(
					layoutPageTemplateStructure);

			for (AssetEntry assetEntry : assetEntries) {
				AssetEntryUsage assetEntryUsage =
					_assetEntryUsageLocalService.fetchAssetEntryUsage(
						assetEntry.getEntryId(),
						_portal.getClassNameId(
							LayoutPageTemplateStructure.class),
						String.valueOf(
							layoutPageTemplateStructure.
								getLayoutPageTemplateStructureId()),
						layoutPageTemplateStructure.getClassPK());

				if (assetEntryUsage != null) {
					continue;
				}

				ServiceContext serviceContext = Optional.ofNullable(
					ServiceContextThreadLocal.getServiceContext()
				).orElse(
					new ServiceContext()
				);

				_assetEntryUsageLocalService.addAssetEntryUsage(
					layoutPageTemplateStructure.getGroupId(),
					assetEntry.getEntryId(),
					_portal.getClassNameId(LayoutPageTemplateStructure.class),
					String.valueOf(
						layoutPageTemplateStructure.
							getLayoutPageTemplateStructureId()),
					layoutPageTemplateStructure.getClassPK(), serviceContext);
			}
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Override
	public void onBeforeRemove(
			LayoutPageTemplateStructure layoutPageTemplateStructure)
		throws ModelListenerException {

		_assetEntryUsageLocalService.deleteAssetEntryUsages(
			_portal.getClassNameId(LayoutPageTemplateStructure.class),
			String.valueOf(
				layoutPageTemplateStructure.getLayoutPageTemplateStructureId()),
			layoutPageTemplateStructure.getClassPK());
	}

	@Reference
	private AssetEntryUsageLocalService _assetEntryUsageLocalService;

	@Reference
	private Portal _portal;

}
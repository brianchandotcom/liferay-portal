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

package com.liferay.commerce.product.internal.model.listener;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
import com.liferay.commerce.product.service.CPFriendlyURLEntryLocalService;
import com.liferay.commerce.product.service.CPRuleAssetCategoryRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = ModelListener.class)
public class AssetCategoryModelListener
	extends BaseModelListener<AssetCategory> {

	@Override
	public void onBeforeRemove(AssetCategory assetCategory) {
		try {
			_cpAttachmentFileEntryLocalService.deleteCPAttachmentFileEntries(
				AssetCategory.class.getName(), assetCategory.getCategoryId());

			_cpDisplayLayoutLocalService.deleteCPDisplayLayout(
				AssetCategory.class, assetCategory.getCategoryId());

			_cpFriendlyURLEntryLocalService.deleteCPFriendlyURLEntries(
				assetCategory.getGroupId(), AssetCategory.class,
				assetCategory.getCategoryId());

			_cpRuleAssetCategoryRelLocalService.
				deleteCPRuleAssetCategoryRelsByAssetCategoryId(
					assetCategory.getCategoryId());
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryModelListener.class);

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

	@Reference
	private CPFriendlyURLEntryLocalService _cpFriendlyURLEntryLocalService;

	@Reference
	private CPRuleAssetCategoryRelLocalService
		_cpRuleAssetCategoryRelLocalService;

}
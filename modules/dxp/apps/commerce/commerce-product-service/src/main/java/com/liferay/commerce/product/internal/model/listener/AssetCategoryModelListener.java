/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
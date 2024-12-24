/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.entry.rel.internal.change.tracking.spi.resolver;

import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;
import com.liferay.asset.entry.rel.service.AssetEntryAssetCategoryRelLocalService;
import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(
	property = "service.ranking:Integer=200", service = ConstraintResolver.class
)
public class AssetEntryAssetCategoryRelConstraintResolver
	implements ConstraintResolver<AssetEntryAssetCategoryRel> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-asset-entry-asset-category-rel";
	}

	@Override
	public Class<AssetEntryAssetCategoryRel> getModelClass() {
		return AssetEntryAssetCategoryRel.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "duplicate-asset-entry-asset-category-rel-was-removed";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			locale, AssetEntryAssetCategoryRelConstraintResolver.class);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"assetEntryId", "assetCategoryId"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<AssetEntryAssetCategoryRel>
				constraintResolverContext)
		throws PortalException {

		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel =
			constraintResolverContext.getSourceCTModel();

		_assetEntryAssetCategoryRelLocalService.
			deleteAssetEntryAssetCategoryRel(assetEntryAssetCategoryRel);
	}

	@Reference
	private AssetEntryAssetCategoryRelLocalService
		_assetEntryAssetCategoryRelLocalService;

}
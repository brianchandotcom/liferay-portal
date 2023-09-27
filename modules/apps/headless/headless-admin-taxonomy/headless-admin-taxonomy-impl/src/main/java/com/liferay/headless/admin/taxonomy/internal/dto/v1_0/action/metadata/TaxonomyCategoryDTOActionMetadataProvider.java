/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.dto.v1_0.action.metadata;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.headless.admin.taxonomy.internal.resource.v1_0.TaxonomyCategoryResourceImpl;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.vulcan.dto.action.ActionInfo;

/**
 * @author Carlos Correa
 */
public class TaxonomyCategoryDTOActionMetadataProvider
	extends BaseTaxonomyCategoryDTOActionMetadataProvider {

	public TaxonomyCategoryDTOActionMetadataProvider() {
		registerActionInfo(
			new ActionInfo(
				ActionKeys.ADD_CATEGORY, TaxonomyCategoryResourceImpl.class,
				"postTaxonomyCategoryTaxonomyCategory"),
			"add-category");
	}

	@Override
	public String getPermissionName() {
		return AssetCategory.class.getName();
	}

}
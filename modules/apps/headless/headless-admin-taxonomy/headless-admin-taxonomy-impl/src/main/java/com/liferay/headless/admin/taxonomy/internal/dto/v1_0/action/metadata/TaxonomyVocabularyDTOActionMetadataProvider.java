/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.dto.v1_0.action.metadata;

import com.liferay.asset.kernel.model.AssetVocabulary;

/**
 * @author Carlos Correa
 */
public class TaxonomyVocabularyDTOActionMetadataProvider
	extends BaseTaxonomyVocabularyDTOActionMetadataProvider {

	@Override
	public String getPermissionName() {
		return AssetVocabulary.class.getName();
	}

}
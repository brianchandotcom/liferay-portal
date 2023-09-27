/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.uad.anonymizer;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.wiki.model.WikiPage;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = UADAnonymizer.class)
public class WikiPageUADAnonymizer extends BaseWikiPageUADAnonymizer {

	@Override
	protected AssetEntry fetchAssetEntry(WikiPage wikiPage) {
		return assetEntryLocalService.fetchEntry(
			WikiPage.class.getName(), wikiPage.getResourcePrimKey());
	}

}
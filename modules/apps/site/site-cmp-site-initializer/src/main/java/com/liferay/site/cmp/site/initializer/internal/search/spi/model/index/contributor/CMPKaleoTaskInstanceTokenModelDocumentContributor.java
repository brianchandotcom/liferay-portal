/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.search.spi.model.index.contributor;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jhosseph Gonzalez
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken",
	service = ModelDocumentContributor.class
)
public class CMPKaleoTaskInstanceTokenModelDocumentContributor
	implements ModelDocumentContributor<KaleoTaskInstanceToken> {

	@Override
	public void contribute(
		Document document, KaleoTaskInstanceToken kaleoTaskInstanceToken) {

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			kaleoTaskInstanceToken.getClassName(),
			kaleoTaskInstanceToken.getClassPK());

		if ((assetEntry == null) || !_hasCMPTaskTag(assetEntry)) {
			return;
		}

		Set<String> cmpAssignTos = new HashSet<>();

		for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
				kaleoTaskInstanceToken.getKaleoTaskAssignmentInstances()) {

			cmpAssignTos.add(
				StringBundler.concat(
					_classNameLocalService.getClassNameId(
						kaleoTaskAssignmentInstance.getAssigneeClassName()),
					StringPool.UNDERLINE,
					kaleoTaskAssignmentInstance.getAssigneeClassPK()));
		}

		if (!cmpAssignTos.isEmpty()) {
			document.addKeyword(
				"cmpAssignTo", cmpAssignTos.toArray(new String[0]));
		}
	}

	private boolean _hasCMPTaskTag(AssetEntry assetEntry) {
		for (String assetTagName : assetEntry.getTagNames()) {
			if (StringUtil.startsWith(
					StringUtil.toLowerCase(assetTagName), "l_cmp_task")) {

				return true;
			}
		}

		return false;
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.asset.validator;

import com.liferay.asset.kernel.validator.AssetEntryValidatorExclusionRule;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kevin Lee
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = AssetEntryValidatorExclusionRule.class
)
public class JournalArticleAssetEntryValidatorExclusionRule
	implements AssetEntryValidatorExclusionRule {

	@Override
	public boolean isValidationExcluded(
		long groupId, String className, long classPK, long classTypePK,
		long[] assetCategoryIds, String[] assetTagNames) {

		JournalArticle article = _journalArticleLocalService.fetchLatestArticle(
			classPK);

		if ((article != null) &&
			(article.getClassNameId() >
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT)) {

			return true;
		}

		return false;
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}
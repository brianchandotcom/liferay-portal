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

package com.liferay.portlet.asset.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class AssetVocabularyUtil {

	public static String getUnambiguousVocabularyTitle(
			List<AssetVocabulary> vocabularies,
			final AssetVocabulary vocabulary, long groupId, final Locale locale)
		throws PortalException, SystemException {

		final String vocabularyTitle = vocabulary.getTitle(locale);

		if (vocabulary.getGroupId() == groupId ) {
			return vocabularyTitle;
		}

		boolean hasAmbiguousVocabularies = ListUtil.exists(
			vocabularies,
			new PredicateFilter<AssetVocabulary>() {

				@Override
				public boolean filter(AssetVocabulary curVocabulary) {
					String curVocabularyTitle = curVocabulary.getTitle(locale);

					if (curVocabularyTitle.equals(vocabularyTitle) &&
						(curVocabulary.getVocabularyId() !=
							vocabulary.getVocabularyId())) {

						return true;
					}

					return false;
				}

			});

		if (hasAmbiguousVocabularies) {
			Group vocabularyGroup = GroupLocalServiceUtil.getGroup(
				vocabulary.getGroupId());

			StringBundler sb = new StringBundler(5);

			sb.append(vocabularyTitle);
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(vocabularyGroup.getDescriptiveName(locale));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		return vocabularyTitle;
	}

	public static List<AssetVocabulary> getVocabularies(Hits hits)
		throws PortalException, SystemException {

		List<Document> documents = hits.toList();

		List<AssetVocabulary> vocabularies = new ArrayList<AssetVocabulary>(
			documents.size());

		for (Document document : documents) {
			long vocabularyId = GetterUtil.getLong(
				document.get(Field.ASSET_VOCABULARY_ID));

			AssetVocabulary vocabulary =
				AssetVocabularyLocalServiceUtil.getVocabulary(vocabularyId);

			if (vocabulary == null) {
				vocabularies = null;

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					AssetVocabulary.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (vocabularies != null) {
				vocabularies.add(vocabulary);
			}
		}

		return vocabularies;
	}

}
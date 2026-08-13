/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.site.cms.site.initializer.internal.search.similarity.CMSContentTextSimilarityTextExtractor;
import com.liferay.site.cms.site.initializer.internal.search.similarity.TextSimilaritySignatureUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = DocumentContributor.class)
public class CMSContentTextSimilarityDocumentContributor
	implements DocumentContributor<ObjectEntry> {

	@Override
	public void contribute(
		Document document, BaseModel<ObjectEntry> baseModel) {

		if (!(baseModel instanceof ObjectEntry)) {
			return;
		}

		ObjectEntry objectEntry = (ObjectEntry)baseModel;

		try {
			ObjectDefinition objectDefinition =
				objectEntry.getObjectDefinition();

			if (!objectDefinition.isCMS()) {
				return;
			}

			List<String> similarityKeys = new ArrayList<>();

			for (String languageId :
					_cmsContentTextSimilarityTextExtractor.getLanguageIds(
						objectEntry)) {

				similarityKeys.addAll(
					TransformUtil.transformToList(
						TextSimilaritySignatureUtil.getSimilarityKeys(
							_cmsContentTextSimilarityTextExtractor.getText(
								languageId, objectEntry)),
						similarityKey -> StringBundler.concat(
							languageId, StringPool.UNDERLINE, similarityKey)));
			}

			if (similarityKeys.isEmpty()) {
				return;
			}

			document.addKeyword(
				"textSimilarityKeys", similarityKeys.toArray(new String[0]));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to contribute text similarity keys for object " +
						"entry " + objectEntry.getObjectEntryId(),
					exception);
			}
		}
	}

	@Activate
	protected void activate() {
		_cmsContentTextSimilarityTextExtractor =
			new CMSContentTextSimilarityTextExtractor(_htmlParser);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CMSContentTextSimilarityDocumentContributor.class);

	private CMSContentTextSimilarityTextExtractor
		_cmsContentTextSimilarityTextExtractor;

	@Reference
	private HtmlParser _htmlParser;

}
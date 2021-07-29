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

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.reindexer.IndexReindexer;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexNameBuilder;
import com.liferay.search.tuning.synonyms.model.STSynonymsEntry;
import com.liferay.search.tuning.synonyms.service.STSynonymsEntryLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	property = "model.class.name=com.liferay.search.tuning.synonyms.model.STSynonymsEntry",
	service = IndexReindexer.class
)
public class SynonymSetIndexReindexer implements IndexReindexer {

	public String getModelClassName() {
		return STSynonymsEntry.class.getName();
	}

	public void reindex(long[] companyIds) {
		for (long companyId : companyIds) {
			List<STSynonymsEntry> stSynonymsEntries =
				stSynonymsEntryLocalService.getSTSynonymsEntriesByCompanyId(
					companyId);

			SynonymSetIndexName synonymSetIndexName =
				synonymSetIndexNameBuilder.getSynonymSetIndexName(companyId);

			if (ListUtil.isEmpty(stSynonymsEntries)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Not reindexing ",
							synonymSetIndexName.getIndexName(),
							" because the database has no STSynonymsEntry ",
							"entries"));
				}

				continue;
			}

			try {
				synonymSetIndexCreator.delete(synonymSetIndexName);
			}
			catch (RuntimeException runtimeException) {
				_log.error(
					"Unable to delete index " +
						synonymSetIndexName.getIndexName());
			}

			synonymSetIndexCreator.create(synonymSetIndexName);

			for (STSynonymsEntry stSynonymsEntry : stSynonymsEntries) {
				synonymSetIndexWriter.create(
					synonymSetIndexName, _translate(stSynonymsEntry));
			}
		}
	}

	@Reference
	protected STSynonymsEntryLocalService stSynonymsEntryLocalService;

	@Reference
	protected SynonymSetIndexCreator synonymSetIndexCreator;

	@Reference
	protected SynonymSetIndexNameBuilder synonymSetIndexNameBuilder;

	@Reference
	protected SynonymSetIndexWriter synonymSetIndexWriter;

	private SynonymSet _translate(STSynonymsEntry stSynonymsEntry) {
		SynonymSet.SynonymSetBuilder synonymSetBuilder =
			new SynonymSet.SynonymSetBuilder();

		synonymSetBuilder.synonymSetDocumentId(
			stSynonymsEntry.getSynonymSetDocumentId()
		).synonyms(
			stSynonymsEntry.getSynonyms()
		);

		return synonymSetBuilder.build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SynonymSetIndexReindexer.class);

}
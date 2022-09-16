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

import com.liferay.json.storage.service.JSONStorageEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.reindexer.IndexReindexer;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexNameBuilder;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = {IndexReindexer.class, SynonymSetIndexReindexer.class})
public class SynonymSetIndexReindexer implements IndexReindexer {

	@Override
	public void reindex(long[] companyIds) {
		for (long companyId : companyIds) {
			List<Long> classPKs = jsonStorageEntryLocalService.getClassPKs(
				companyId,
				classNameLocalService.getClassNameId(SynonymSet.class),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			SynonymSetIndexName synonymSetIndexName =
				synonymSetIndexNameBuilder.getSynonymSetIndexName(companyId);

			if (ListUtil.isEmpty(classPKs)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Not reindexing ",
							synonymSetIndexName.getIndexName(),
							" because the database has no synonym set ",
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
						synonymSetIndexName.getIndexName(),
					runtimeException);
			}

			synonymSetIndexCreator.create(synonymSetIndexName);

			for (long classPK : classPKs) {
				synonymSetIndexWriter.create(
					synonymSetIndexName, _buildSynonymSet(classPK));
			}
		}
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected JSONStorageEntryLocalService jsonStorageEntryLocalService;

	@Reference
	protected SynonymSetIndexCreator synonymSetIndexCreator;

	@Reference
	protected SynonymSetIndexNameBuilder synonymSetIndexNameBuilder;

	@Reference
	protected SynonymSetIndexWriter synonymSetIndexWriter;

	private SynonymSet _buildSynonymSet(long classPK) {
		JSONObject jsonObject = jsonStorageEntryLocalService.getJSONObject(
			classNameLocalService.getClassNameId(SynonymSet.class), classPK);

		SynonymSet.SynonymSetBuilder synonymSetBuilder =
			new SynonymSet.SynonymSetBuilder();

		synonymSetBuilder.synonymSetDocumentId(
			jsonObject.getString("synonymSetDocumentId")
		).synonyms(
			jsonObject.getString("synonyms")
		);

		return synonymSetBuilder.build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SynonymSetIndexReindexer.class);

}
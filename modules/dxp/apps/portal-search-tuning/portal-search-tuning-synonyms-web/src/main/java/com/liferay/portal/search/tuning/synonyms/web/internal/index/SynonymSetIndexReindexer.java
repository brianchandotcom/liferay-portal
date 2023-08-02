/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.json.storage.service.JSONStorageEntryLocalService;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.index.SyncReindexManager;
import com.liferay.portal.search.spi.reindexer.IndexReindexer;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexNameBuilder;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = IndexReindexer.class)
public class SynonymSetIndexReindexer implements IndexReindexer {

	@Override
	public void reindex(long[] companyIds) throws Exception {
		reindex(companyIds, null);
	}

	@Override
	public void reindex(long[] companyIds, String executionMode)
		throws Exception {

		if (!searchCapabilities.isSynonymsSupported()) {
			return;
		}

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

			Date date = null;

			if (_isExecuteSyncReindex(executionMode)) {
				date = new Date();

				Thread.sleep(1000);
			}
			else {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Deleting index " + synonymSetIndexName.getIndexName());
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
			}

			if (!_isExecuteSyncReindex(executionMode)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Creating index " + synonymSetIndexName.getIndexName());
				}

				synonymSetIndexCreator.create(synonymSetIndexName);
			}

			for (long classPK : classPKs) {
				synonymSetIndexWriter.create(
					synonymSetIndexName, _buildSynonymSet(classPK));
			}

			if (_isExecuteSyncReindex(executionMode)) {
				SyncReindexManager syncReindexManager =
					_syncReindexManagerSnapshot.get();

				syncReindexManager.deleteStaleDocuments(
					synonymSetIndexName.getIndexName(), date,
					Collections.emptySet());
			}
		}
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected JSONStorageEntryLocalService jsonStorageEntryLocalService;

	@Reference
	protected SearchCapabilities searchCapabilities;

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

	private boolean _isExecuteSyncReindex(String executionMode) {
		if ((_syncReindexManagerSnapshot.get() != null) &&
			(executionMode != null) && executionMode.equals("sync")) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SynonymSetIndexReindexer.class);

	private static final Snapshot<SyncReindexManager>
		_syncReindexManagerSnapshot = new Snapshot<>(
			SynonymSetIndexReindexer.class, SyncReindexManager.class, null,
			true);

}
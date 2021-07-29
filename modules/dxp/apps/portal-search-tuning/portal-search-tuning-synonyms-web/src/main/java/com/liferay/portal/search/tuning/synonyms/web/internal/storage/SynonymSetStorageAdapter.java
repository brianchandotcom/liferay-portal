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

package com.liferay.portal.search.tuning.synonyms.web.internal.storage;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSet;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexWriter;
import com.liferay.search.tuning.synonyms.model.STSynonymsEntry;
import com.liferay.search.tuning.synonyms.service.STSynonymsEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = SynonymSetStorageAdapter.class)
public class SynonymSetStorageAdapter {

	public String create(
		SynonymSetIndexName synonymSetIndexName, SynonymSet synonymSet) {

		STSynonymsEntry stSynonymsEntry =
			stSynonymsEntryLocalService.addSTSynonymsEntry(
				synonymSetIndexName.getIndexName(), synonymSet.getSynonyms());

		SynonymSet.SynonymSetBuilder synonymSetBuilder =
			new SynonymSet.SynonymSetBuilder(synonymSet);

		synonymSetBuilder.synonymSetDocumentId(
			stSynonymsEntry.getSynonymSetDocumentId());

		synonymSetIndexWriter.create(
			synonymSetIndexName, synonymSetBuilder.build());

		return stSynonymsEntry.getSynonymSetDocumentId();
	}

	public void delete(
			SynonymSetIndexName synonymSetIndexName,
			String synonymSetDocumentId)
		throws PortalException {

		stSynonymsEntryLocalService.deleteSTSynonymsEntry(
			_getSTSynonymsEntryId(synonymSetDocumentId));

		synonymSetIndexWriter.remove(synonymSetIndexName, synonymSetDocumentId);
	}

	public void update(
			SynonymSetIndexName synonymSetIndexName, SynonymSet synonymSet)
		throws PortalException {

		stSynonymsEntryLocalService.updateSTSynonymsEntry(
			_getSTSynonymsEntryId(synonymSet.getSynonymSetDocumentId()),
			synonymSet.getSynonyms());

		synonymSetIndexWriter.update(synonymSetIndexName, synonymSet);
	}

	@Reference
	protected STSynonymsEntryLocalService stSynonymsEntryLocalService;

	@Reference
	protected SynonymSetIndexWriter synonymSetIndexWriter;

	private long _getSTSynonymsEntryId(String synonymSetDocumentId)
		throws PortalException {

		String[] parts = StringUtil.split(synonymSetDocumentId, "_PORTLET_");

		if (parts.length != 2) {
			_log.error(
				StringBundler.concat(
					"Non-standard synonymSetDocumentId: ", synonymSetDocumentId,
					". SynonymSets may need to be imported to the database ",
					"via the SynonymSetsDatabaseImporter groovy script before ",
					"they can be edited or deleted."));

			throw new PortalException();
		}

		return Long.valueOf(parts[1]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SynonymSetStorageAdapter.class);

}
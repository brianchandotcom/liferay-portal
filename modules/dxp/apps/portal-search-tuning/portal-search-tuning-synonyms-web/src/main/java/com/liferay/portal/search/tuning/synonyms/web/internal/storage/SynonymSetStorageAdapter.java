/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.search.tuning.synonyms.web.internal.storage.helper.SynonymSetJSONStorageHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = SynonymSetStorageAdapter.class)
public class SynonymSetStorageAdapter {

	public String create(
		SynonymSetIndexName synonymSetIndexName, SynonymSet synonymSet) {

		String synonymSetDocumentId =
			synonymSetJSONStorageHelper.addJSONStorageEntry(
				synonymSetIndexName.getIndexName(), synonymSet.getSynonyms());

		SynonymSet.SynonymSetBuilder synonymSetBuilder =
			new SynonymSet.SynonymSetBuilder(synonymSet);

		synonymSetBuilder.synonymSetDocumentId(synonymSetDocumentId);

		synonymSetIndexWriter.create(
			synonymSetIndexName, synonymSetBuilder.build());

		return synonymSetDocumentId;
	}

	public void delete(
			SynonymSetIndexName synonymSetIndexName,
			String synonymSetDocumentId)
		throws PortalException {

		synonymSetJSONStorageHelper.deleteJSONStorageEntry(
			_getClassPK(synonymSetDocumentId));

		synonymSetIndexWriter.remove(synonymSetIndexName, synonymSetDocumentId);
	}

	public void update(
			SynonymSetIndexName synonymSetIndexName, SynonymSet synonymSet)
		throws PortalException {

		synonymSetJSONStorageHelper.updateJSONStorageEntry(
			_getClassPK(synonymSet.getSynonymSetDocumentId()),
			synonymSet.getSynonyms());

		synonymSetIndexWriter.update(synonymSetIndexName, synonymSet);
	}

	@Reference
	protected SynonymSetIndexWriter synonymSetIndexWriter;

	@Reference
	protected SynonymSetJSONStorageHelper synonymSetJSONStorageHelper;

	private long _getClassPK(String synonymSetDocumentId)
		throws PortalException {

		String[] parts = StringUtil.split(synonymSetDocumentId, "_PORTLET_");

		if (parts.length != 2) {
			_log.error(
				StringBundler.concat(
					"Synonym set document ID ", synonymSetDocumentId,
					" has an unexpected format. Synonym sets may need to be ",
					"imported to the database via the synonym sets database ",
					"importer Groovy script before they can be edited or ",
					"deleted."));

			throw new PortalException();
		}

		return Long.valueOf(parts[1]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SynonymSetStorageAdapter.class);

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.storage.helper;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.json.storage.service.JSONStorageEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = RankingJSONStorageHelper.class)
public class RankingJSONStorageHelper {

	public String addJSONStorageEntry(Ranking ranking) {
		long classPK = counterLocalService.increment();

		String rankingDocumentId =
			Ranking.class.getName() + "_PORTLET_" + classPK;

		jsonStorageEntryLocalService.addJSONStorageEntries(
			CompanyThreadLocal.getCompanyId(),
			classNameLocalService.getClassNameId(Ranking.class), classPK,
			JSONUtil.put(
				"aliases", _jsonFactory.createJSONArray(ranking.getAliases())
			).put(
				"groupExternalReferenceCode",
				ranking.getGroupExternalReferenceCode()
			).put(
				"hiddenDocumentIds",
				_jsonFactory.createJSONArray(ranking.getHiddenDocumentIds())
			).put(
				"inactive", ranking.isInactive()
			).put(
				"indexName", ranking.getIndexName()
			).put(
				"name", ranking.getName()
			).put(
				"pins", _getPinsJSONArray(ranking)
			).put(
				"queryString", ranking.getQueryString()
			).put(
				"rankingDocumentId", rankingDocumentId
			).put(
				"sxpBlueprintExternalReferenceCode",
				ranking.getSXPBlueprintExternalReferenceCode()
			).toString());

		return rankingDocumentId;
	}

	public void deleteJSONStorageEntry(String rankingDocumentId)
		throws PortalException {

		jsonStorageEntryLocalService.deleteJSONStorageEntries(
			classNameLocalService.getClassNameId(Ranking.class),
			_getClassPK(rankingDocumentId));
	}

	public void updateJSONStorageEntry(Ranking ranking) throws PortalException {
		long classPK = _getClassPK(ranking.getRankingDocumentId());

		JSONObject jsonObject = jsonStorageEntryLocalService.getJSONObject(
			classNameLocalService.getClassNameId(Ranking.class), classPK);

		jsonObject.put(
			"aliases", _jsonFactory.createJSONArray(ranking.getAliases())
		).put(
			"groupExternalReferenceCode",
			ranking.getGroupExternalReferenceCode()
		).put(
			"hiddenDocumentIds",
			_jsonFactory.createJSONArray(ranking.getHiddenDocumentIds())
		).put(
			"inactive", ranking.isInactive()
		).put(
			"name", ranking.getName()
		).put(
			"pins", _getPinsJSONArray(ranking)
		).put(
			"sxpBlueprintExternalReferenceCode",
			ranking.getSXPBlueprintExternalReferenceCode()
		);

		jsonStorageEntryLocalService.updateJSONStorageEntries(
			CompanyThreadLocal.getCompanyId(),
			classNameLocalService.getClassNameId(Ranking.class), classPK,
			jsonObject.toString());
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected CounterLocalService counterLocalService;

	@Reference
	protected JSONStorageEntryLocalService jsonStorageEntryLocalService;

	private long _getClassPK(String rankingDocumentId) throws PortalException {
		String[] parts = StringUtil.split(rankingDocumentId, "_PORTLET_");

		if (parts.length != 2) {
			_log.error(
				StringBundler.concat(
					"Ranking document ID ", rankingDocumentId, " has an ",
					"unexpected format. Rankings may need to be imported to ",
					"the database via the rankings database importer Groovy ",
					"script before they can be edited or deleted."));

			throw new PortalException();
		}

		return Long.valueOf(parts[1]);
	}

	private JSONArray _getPinsJSONArray(Ranking ranking) {
		JSONArray pinsJSONArray = _jsonFactory.createJSONArray();

		for (Ranking.Pin pin : ranking.getPins()) {
			pinsJSONArray.put(
				JSONUtil.put(
					"documentId", pin.getDocumentId()
				).put(
					"position", pin.getPosition()
				));
		}

		return pinsJSONArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RankingJSONStorageHelper.class);

	@Reference
	private JSONFactory _jsonFactory;

}
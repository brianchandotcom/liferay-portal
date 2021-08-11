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

package com.liferay.search.tuning.rankings.service.impl;

import com.liferay.json.storage.service.JSONStorageEntryLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.search.tuning.rankings.model.STRankingsEntry;
import com.liferay.search.tuning.rankings.service.base.STRankingsEntryLocalServiceBaseImpl;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	property = "model.class.name=com.liferay.search.tuning.rankings.model.STRankingsEntry",
	service = AopService.class
)
public class STRankingsEntryLocalServiceImpl
	extends STRankingsEntryLocalServiceBaseImpl {

	@Override
	public STRankingsEntry addSTRankingsEntry(
		List<String> aliases, List<String> hiddenDocumentIds, boolean inactive,
		String indexName, String name, Map<Integer, String> documentIdsMap,
		String queryString) {

		STRankingsEntry stRankingsEntry = stRankingsEntryPersistence.create(
			counterLocalService.increment());

		JSONObject jsonObject = JSONUtil.put(
			"indexName", indexName
		).put(
			"queryString", queryString
		).put(
			"rankingDocumentId", uidFactory.getUID(stRankingsEntry)
		);

		jsonStorageLocalService.addJSONStorageEntries(
			stRankingsEntry.getCompanyId(),
			classNameLocalService.getClassNameId(STRankingsEntry.class),
			stRankingsEntry.getPrimaryKey(), jsonObject.toString());

		return updateSTRankingsEntry(
			stRankingsEntry, aliases, hiddenDocumentIds, inactive, name,
			documentIdsMap);
	}

	@Override
	public STRankingsEntry addSTRankingsEntry(
		String indexName, String name, String queryString) {

		STRankingsEntry stRankingsEntry = stRankingsEntryPersistence.create(
			counterLocalService.increment());

		JSONObject jsonObject = JSONUtil.put(
			"indexName", indexName
		).put(
			"name", name
		).put(
			"queryString", queryString
		).put(
			"rankingDocumentId", uidFactory.getUID(stRankingsEntry)
		);

		jsonStorageLocalService.addJSONStorageEntries(
			stRankingsEntry.getCompanyId(),
			classNameLocalService.getClassNameId(STRankingsEntry.class),
			stRankingsEntry.getPrimaryKey(), jsonObject.toString());

		return stRankingsEntryPersistence.update(stRankingsEntry);
	}

	@Override
	public STRankingsEntry deleteSTRankingsEntry(long stRankingsEntryId)
		throws PortalException {

		jsonStorageLocalService.deleteJSONStorageEntries(
			classNameLocalService.getClassNameId(STRankingsEntry.class),
			stRankingsEntryId);

		return super.deleteSTRankingsEntry(stRankingsEntryId);
	}

	@Override
	public List<STRankingsEntry> getSTRankingsEntriesByCompanyId(
		long companyId) {

		return stRankingsEntryPersistence.findBycompanyId(companyId);
	}

	@Override
	public STRankingsEntry updateSTRankingsEntry(
			long stRankingsEntryId, List<String> aliases,
			List<String> hiddenDocumentIds, boolean inactive, String name,
			Map<Integer, String> documentIdsMap)
		throws PortalException {

		return updateSTRankingsEntry(
			getSTRankingsEntry(stRankingsEntryId), aliases, hiddenDocumentIds,
			inactive, name, documentIdsMap);
	}

	protected STRankingsEntry updateSTRankingsEntry(
		STRankingsEntry stRankingsEntry, List<String> aliases,
		List<String> hiddenDocumentIds, boolean inactive, String name,
		Map<Integer, String> documentIdsMap) {

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				stRankingsEntry.getJSON());

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			for (Map.Entry<Integer, String> entry : documentIdsMap.entrySet()) {
				int position = entry.getKey();
				String documentId = entry.getValue();

				jsonArray.put(
					JSONUtil.put(
						"documentId", documentId
					).put(
						"position", position
					));
			}

			jsonObject.put(
				"aliases", JSONFactoryUtil.createJSONArray(aliases)
			).put(
				"hiddenDocumentIds",
				JSONFactoryUtil.createJSONArray(hiddenDocumentIds)
			).put(
				"inactive", inactive
			).put(
				"name", name
			).put(
				"pins", jsonArray
			);

			jsonStorageLocalService.updateJSONStorageEntries(
				stRankingsEntry.getCompanyId(),
				classNameLocalService.getClassNameId(STRankingsEntry.class),
				stRankingsEntry.getPrimaryKey(), jsonObject.toString());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to set json for STRankingsEntry with ID: " +
						stRankingsEntry.getSTRankingsEntryId());
			}
		}

		return stRankingsEntryPersistence.update(stRankingsEntry);
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected JSONStorageEntryLocalService jsonStorageLocalService;

	@Reference
	protected UIDFactory uidFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		STRankingsEntryLocalServiceImpl.class);

}
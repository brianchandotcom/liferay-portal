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

package com.liferay.search.tuning.synonyms.service.impl;

import com.liferay.json.storage.service.JSONStorageEntryLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.search.tuning.synonyms.model.STSynonymsEntry;
import com.liferay.search.tuning.synonyms.service.base.STSynonymsEntryLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	property = "model.class.name=com.liferay.search.tuning.synonyms.model.STSynonymsEntry",
	service = AopService.class
)
public class STSynonymsEntryLocalServiceImpl
	extends STSynonymsEntryLocalServiceBaseImpl {

	@Override
	public STSynonymsEntry addSTSynonymsEntry(
		String indexName, String synonyms) {

		STSynonymsEntry stSynonymsEntry = stSynonymsEntryPersistence.create(
			counterLocalService.increment());

		JSONObject jsonObject = JSONUtil.put(
			"indexName", indexName
		).put(
			"synonymSetDocumentId", uidFactory.getUID(stSynonymsEntry)
		);

		jsonStorageLocalService.addJSONStorageEntries(
			stSynonymsEntry.getCompanyId(),
			classNameLocalService.getClassNameId(STSynonymsEntry.class),
			stSynonymsEntry.getPrimaryKey(), jsonObject.toString());

		return updateSTSynonymsEntry(stSynonymsEntry, synonyms);
	}

	@Override
	public STSynonymsEntry deleteSTSynonymsEntry(long stSynonymsEntryId)
		throws PortalException {

		jsonStorageLocalService.deleteJSONStorageEntries(
			classNameLocalService.getClassNameId(STSynonymsEntry.class),
			stSynonymsEntryId);

		return super.deleteSTSynonymsEntry(stSynonymsEntryId);
	}

	@Override
	public List<STSynonymsEntry> getSTSynonymsEntriesByCompanyId(
		long companyId) {

		return stSynonymsEntryPersistence.findBycompanyId(companyId);
	}

	@Override
	public STSynonymsEntry updateSTSynonymsEntry(
			long stSynonymsEntryId, String synonyms)
		throws PortalException {

		return updateSTSynonymsEntry(
			getSTSynonymsEntry(stSynonymsEntryId), synonyms);
	}

	protected STSynonymsEntry updateSTSynonymsEntry(
		STSynonymsEntry stSynonymsEntry, String synonyms) {

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				stSynonymsEntry.getJSON());

			jsonObject.put("synonyms", synonyms);

			jsonStorageLocalService.updateJSONStorageEntries(
				stSynonymsEntry.getCompanyId(),
				classNameLocalService.getClassNameId(STSynonymsEntry.class),
				stSynonymsEntry.getPrimaryKey(), jsonObject.toString());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to set json for STSynonymsEntry with ID: " +
						stSynonymsEntry.getSTSynonymsEntryId());
			}
		}

		return stSynonymsEntryPersistence.update(stSynonymsEntry);
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected JSONStorageEntryLocalService jsonStorageLocalService;

	@Reference
	protected UIDFactory uidFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		STSynonymsEntryLocalServiceImpl.class);

}
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

package com.liferay.portal.search.tuning.synonyms.web.internal.storage.helper;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.json.storage.service.JSONStorageEntryLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = SynonymSetJSONStorageHelper.class)
public class SynonymSetJSONStorageHelper {

	public String addJSONStorageEntry(
		long companyId, String indexName, String synonyms) {

		long classPK = counterLocalService.increment();

		String synonymSetDocumentId =
			SynonymSet.class.getName() + "_PORTLET_" + classPK;

		jsonStorageEntryLocalService.addJSONStorageEntries(
			companyId, classNameLocalService.getClassNameId(SynonymSet.class),
			classPK,
			JSONUtil.put(
				"indexName", indexName
			).put(
				"synonyms", synonyms
			).put(
				"synonymSetDocumentId", synonymSetDocumentId
			).toString());

		return synonymSetDocumentId;
	}

	public String addJSONStorageEntry(String indexName, String synonyms) {
		return addJSONStorageEntry(
			CompanyThreadLocal.getCompanyId(), indexName, synonyms);
	}

	public void deleteJSONStorageEntry(long classPK) {
		jsonStorageEntryLocalService.deleteJSONStorageEntries(
			classNameLocalService.getClassNameId(SynonymSet.class), classPK);
	}

	public void updateJSONStorageEntry(long classPK, String synonyms) {
		JSONObject jsonObject = jsonStorageEntryLocalService.getJSONObject(
			classNameLocalService.getClassNameId(SynonymSet.class), classPK);

		jsonObject.put("synonyms", synonyms);

		jsonStorageEntryLocalService.updateJSONStorageEntries(
			CompanyThreadLocal.getCompanyId(),
			classNameLocalService.getClassNameId(SynonymSet.class), classPK,
			jsonObject.toString());
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected CounterLocalService counterLocalService;

	@Reference
	protected JSONStorageEntryLocalService jsonStorageEntryLocalService;

}
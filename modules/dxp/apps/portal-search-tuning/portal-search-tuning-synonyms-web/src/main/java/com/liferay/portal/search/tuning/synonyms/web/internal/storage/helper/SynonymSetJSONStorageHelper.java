/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.pim.site.initializer.test.util;

import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryFolderLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.site.pim.site.initializer.constants.PIMObjectDefinitionConstants;

import java.io.Serializable;

/**
 * @author Stefano Motta
 */
public class PIMBasicProductTestUtil {

	public static ObjectEntry addPIMBasicProductObjectEntry(long groupId)
		throws Exception {

		return addPIMBasicProductObjectEntry(
			groupId, null,
			ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_PRODUCTS,
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
	}

	public static ObjectEntry addPIMBasicProductObjectEntry(
			long groupId, String defaultLanguageId,
			String objectEntryFolderExternalReferenceCode, String sku,
			String title)
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.
				fetchObjectDefinitionByExternalReferenceCode(
					PIMObjectDefinitionConstants.
						EXTERNAL_REFERENCE_CODE_BASIC_PRODUCT,
					TestPropsValues.getCompanyId());
		ObjectEntryFolder objectEntryFolder =
			ObjectEntryFolderLocalServiceUtil.
				fetchObjectEntryFolderByExternalReferenceCode(
					objectEntryFolderExternalReferenceCode, groupId,
					TestPropsValues.getCompanyId());

		return ObjectEntryLocalServiceUtil.addObjectEntry(
			groupId, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			objectEntryFolder.getObjectEntryFolderId(), defaultLanguageId,
			HashMapBuilder.<String, Serializable>put(
				"sku", sku
			).put(
				"title_i18n",
				HashMapBuilder.put(
					GetterUtil.getString(
						defaultLanguageId,
						LocaleUtil.toLanguageId(
							PortalUtil.getSiteDefaultLocale(groupId))),
					title
				).build()
			).build(),
			ServiceContextTestUtil.getServiceContext(groupId));
	}

}
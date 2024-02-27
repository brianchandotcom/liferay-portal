/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.test.util;

import com.liferay.commerce.product.type.virtual.model.CPDVirtualSettingFileEntry;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDVirtualSettingFileEntryLocalServiceUtil;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class VirtualCPTypeTestUtil {

	public static CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
			long groupId, String className, long classPK, long fileEntryId,
			int activationStatus, long duration, long sampleFileEntryId,
			long termsOfUseJournalArticleResourcePrimKey)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		String url = null;

		if (fileEntryId <= 0) {
			url = "http://www.example.com/download";
		}

		String sampleURL = null;

		if (sampleFileEntryId <= 0) {
			sampleURL = "http://www.example.com/sample";
		}

		Map<Locale, String> termsOfUseContentMap = null;

		if (termsOfUseJournalArticleResourcePrimKey <= 0) {
			termsOfUseContentMap = RandomTestUtil.randomLocaleStringMap();
		}

		return CPDefinitionVirtualSettingLocalServiceUtil.
			addCPDefinitionVirtualSetting(
				className, classPK, fileEntryId, url, activationStatus,
				duration, RandomTestUtil.randomInt(), true, sampleFileEntryId,
				sampleURL, true, termsOfUseContentMap,
				termsOfUseJournalArticleResourcePrimKey, false, serviceContext);
	}

	public static void deleteCPDefinitionVirtualSetting(
			String className, long classPK)
		throws PortalException {

		CPDefinitionVirtualSettingLocalServiceUtil.
			deleteCPDefinitionVirtualSetting(className, classPK);
	}

	public static CPDVirtualSettingFileEntry updateCPDVirtualSettingFileEntry(
			long cpdVirtualSettingFileEntryId, long fileEntryId, String url,
			String version)
		throws PortalException {

		return CPDVirtualSettingFileEntryLocalServiceUtil.
			updateCPDVirtualSettingFileEntry(
				cpdVirtualSettingFileEntryId, fileEntryId, url, version);
	}

}
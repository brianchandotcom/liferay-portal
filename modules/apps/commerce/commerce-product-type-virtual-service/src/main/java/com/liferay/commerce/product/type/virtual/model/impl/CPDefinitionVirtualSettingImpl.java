/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.model.impl;

import com.liferay.commerce.product.type.virtual.model.CPDVirtualSettingFileEntry;
import com.liferay.commerce.product.type.virtual.service.CPDVirtualSettingFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.List;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 */
public class CPDefinitionVirtualSettingImpl
	extends CPDefinitionVirtualSettingBaseImpl {

	@Override
	public List<CPDVirtualSettingFileEntry> getCPDVirtualSettingFileEntries() {
		return CPDVirtualSettingFileEntryLocalServiceUtil.
			getCPDVirtualSettingFileEntries(getCPDefinitionVirtualSettingId());
	}

	@Override
	public List<CPDVirtualSettingFileEntry> getCPDVirtualSettingFileEntries(
		int start, int end) {

		return CPDVirtualSettingFileEntryLocalServiceUtil.
			getCPDVirtualSettingFileEntries(
				getCPDefinitionVirtualSettingId(), start, end);
	}

	@Override
	public int getCPDVirtualSettingFileEntriesCount() {
		return CPDVirtualSettingFileEntryLocalServiceUtil.
			getCPDVirtualSettingFileEntriesCount(
				getCPDefinitionVirtualSettingId());
	}

	@Override
	public FileEntry getSampleFileEntry() throws PortalException {
		if (isUseSampleURL()) {
			return null;
		}

		return DLAppLocalServiceUtil.getFileEntry(getSampleFileEntryId());
	}

	@Override
	public JournalArticle getTermsOfUseJournalArticle() throws PortalException {
		if (!isUseTermsOfUseJournal()) {
			return null;
		}

		return JournalArticleLocalServiceUtil.getLatestArticle(
			getTermsOfUseJournalArticleResourcePrimKey());
	}

	@Override
	public boolean isUseSampleURL() {
		if (getSampleFileEntryId() > 0) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isUseTermsOfUseJournal() {
		if (getTermsOfUseJournalArticleResourcePrimKey() > 0) {
			return true;
		}

		return false;
	}

}
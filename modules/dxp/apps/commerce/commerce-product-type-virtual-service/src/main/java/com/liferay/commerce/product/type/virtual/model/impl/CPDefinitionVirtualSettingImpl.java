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

package com.liferay.commerce.product.type.virtual.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 */
@ProviderType
public class CPDefinitionVirtualSettingImpl
	extends CPDefinitionVirtualSettingBaseImpl {

	public CPDefinitionVirtualSettingImpl() {
	}

	@Override
	public CPDefinition getCPDefinition() throws PortalException {
		return CPDefinitionLocalServiceUtil.getCPDefinition(
			getCPDefinitionId());
	}

	@Override
	public FileEntry getFileEntry() throws PortalException {
		if (isUseUrl()) {
			return null;
		}

		return DLAppLocalServiceUtil.getFileEntry(getFileEntryId());
	}

	@Override
	public FileEntry getSampleFileEntry() throws PortalException {
		if (isUseSampleUrl()) {
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
	public boolean isUseSampleUrl() {
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

	@Override
	public boolean isUseUrl() {
		if (getFileEntryId() > 0) {
			return false;
		}

		return true;
	}

}
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.journal.web.internal.display.context;

import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.web.configuration.JournalWebConfiguration;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalEditStructuresDisplayContext {

	public JournalEditStructuresDisplayContext(HttpServletRequest request) {
		_journalWebConfiguration =
			(JournalWebConfiguration)request.getAttribute(
				JournalWebConfiguration.class.getName());
	}

	public boolean autogenerateStructureKey() {
		return _journalWebConfiguration.autogenerateStructureKey();
	}

	public boolean changeableDefaultLanguage() {
		return _journalWebConfiguration.changeableDefaultLanguage();
	}

	public String getAvailableFields() {
		return "Liferay.FormBuilder.AVAILABLE_FIELDS.WCM_STRUCTURE";
	}

	public String getStorageType() {
		String storageType = StorageType.JSON.getValue();

		try {
			long companyId = CompanyThreadLocal.getCompanyId();

			JournalServiceConfiguration journalServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					JournalServiceConfiguration.class, companyId);

			storageType =
				journalServiceConfiguration.journalArticleStorageType();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return storageType;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalEditStructuresDisplayContext.class);

	private final JournalWebConfiguration _journalWebConfiguration;

}
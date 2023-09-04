/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.util;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.exception.FolderNameException;
import com.liferay.journal.util.JournalValidator;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Validator;

import org.apache.commons.lang.StringEscapeUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Zhang
 */
@Component(service = JournalValidator.class)
public final class JournalValidatorImpl implements JournalValidator {

	@Override
	public boolean isValidName(String name) {
		if (Validator.isNull(name)) {
			return false;
		}

		String[] charactersBlacklist = {};

		try {
			JournalServiceConfiguration journalServiceConfiguration =
				_configurationProvider.getCompanyConfiguration(
					JournalServiceConfiguration.class,
					CompanyThreadLocal.getCompanyId());

			charactersBlacklist =
				journalServiceConfiguration.charactersblacklist();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		for (String blacklistChar : charactersBlacklist) {
			blacklistChar = StringEscapeUtils.unescapeJava(blacklistChar);

			if (name.contains(blacklistChar)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void validateFolderName(String folderName)
		throws FolderNameException {

		if (!isValidName(folderName)) {
			String message =
				"Folder name \"" + folderName +
					"\" contains invalid characters";

			if (_log.isWarnEnabled()) {
				_log.warn(message);
			}

			if (!ExportImportThreadLocal.isImportInProcess()) {
				throw new FolderNameException(message);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalValidatorImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}
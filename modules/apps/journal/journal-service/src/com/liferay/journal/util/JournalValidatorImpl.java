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

package com.liferay.journal.util;

import com.liferay.journal.exception.FolderNameException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Zhang
 */
@Component(immediate = true, service = JournalValidator.class)
public final class JournalValidatorImpl implements JournalValidator {

	@Override
	public boolean isValidName(String name) {
		if (Validator.isNull(name)) {
			return false;
		}

		for (String blacklistChar : PropsValues.JOURNAL_CHAR_BLACKLIST) {
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
			throw new FolderNameException(folderName);
		}
	}

}
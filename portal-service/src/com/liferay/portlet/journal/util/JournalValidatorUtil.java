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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portlet.journal.FolderNameException;

/**
 * @author David Zhang
 */
public class JournalValidatorUtil {

	public static JournalValidator getJournalValidator() {
		PortalRuntimePermission.checkGetBeanProperty(
			JournalValidatorUtil.class);

		return _journalValidator;
	}

	public static boolean isValidName(String name) {
		return getJournalValidator().isValidName(name);
	}

	public static final void validateFolderName(String folderName)
		throws FolderNameException {

		getJournalValidator().validateFolderName(folderName);
	}

	public void setJournalValidator(JournalValidator journalValidator) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_journalValidator = journalValidator;
	}

	private static JournalValidator _journalValidator;

}
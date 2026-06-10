/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Eduardo García
 */
public class NoSuchExperienceAudienceEntryRelException
	extends NoSuchModelException {

	public NoSuchExperienceAudienceEntryRelException() {
	}

	public NoSuchExperienceAudienceEntryRelException(String msg) {
		super(msg);
	}

	public NoSuchExperienceAudienceEntryRelException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchExperienceAudienceEntryRelException(Throwable throwable) {
		super(throwable);
	}

}
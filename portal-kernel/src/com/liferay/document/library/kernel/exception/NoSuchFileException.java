/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchFileException extends NoSuchModelException {

	public NoSuchFileException() {
	}

	public NoSuchFileException(
		long companyId, long repositoryId, String fileName) {

		super(
			String.format(
				"{companyId=%s, repositoryId=%s, fileName=%s}", companyId,
				repositoryId, fileName));
	}

	public NoSuchFileException(
		long companyId, long repositoryId, String fileName, String version) {

		super(
			String.format(
				"{companyId=%s, repositoryId=%s, fileName=%s, version=%s}",
				companyId, repositoryId, fileName, version));
	}

	public NoSuchFileException(
		long companyId, long repositoryId, String fileName, String version,
		Throwable throwable) {

		super(
			String.format(
				"{companyId=%s, repositoryId=%s, fileName=%s, version=%s, " +
					"cause=%s}",
				companyId, repositoryId, fileName, version, throwable),
			throwable);
	}

	public NoSuchFileException(
		long companyId, long repositoryId, String fileName,
		Throwable throwable) {

		super(
			String.format(
				"{companyId=%s, repositoryId=%s, fileName=%s, cause=%s}",
				companyId, repositoryId, fileName, throwable),
			throwable);
	}

	public NoSuchFileException(String msg) {
		super(msg);
	}

	public NoSuchFileException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchFileException(Throwable throwable) {
		super(throwable);
	}

}
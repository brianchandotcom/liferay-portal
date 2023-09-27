/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateAssetVocabularyExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateAssetVocabularyExternalReferenceCodeException() {
	}

	public DuplicateAssetVocabularyExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateAssetVocabularyExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateAssetVocabularyExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}
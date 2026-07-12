/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Javier Moral
 */
public class NoSuchEntityException extends PortalException {

	public NoSuchEntityException(String entity, String externalReferenceCode) {
		super(
			StringBundler.concat(
				"No ", entity, " exists with the external reference code \"",
				externalReferenceCode, "\""));
	}

	public NoSuchEntityException(
		String entity, String externalReferenceCode, String parentEntity) {

		super(
			StringBundler.concat(
				"No ", entity, " with the external reference code \"",
				externalReferenceCode, "\" exists in this ", parentEntity));
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alberto Javier Moreno Lage
 */
public class InvalidTypeIdException extends PortalException {

	public InvalidTypeIdException(String typeId) {
		super(
			StringBundler.concat(
				"'", typeId, "' cannot be mapped to a valid entity subtype"));
	}

}
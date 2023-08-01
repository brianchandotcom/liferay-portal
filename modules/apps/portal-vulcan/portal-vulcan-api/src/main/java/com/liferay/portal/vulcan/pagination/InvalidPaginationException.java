/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.pagination;

import javax.ws.rs.BadRequestException;

/**
 * @author Alberto Javier Moreno Lage
 */
public class InvalidPaginationException extends BadRequestException {

	public InvalidPaginationException(String message) {
		super(message);
	}

}
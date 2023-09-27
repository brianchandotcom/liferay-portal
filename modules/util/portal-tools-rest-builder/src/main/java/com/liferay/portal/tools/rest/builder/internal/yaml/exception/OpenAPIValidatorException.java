/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.internal.yaml.exception;

/**
 * @author Javier de Arcos
 */
public class OpenAPIValidatorException extends Exception {

	public OpenAPIValidatorException(String message) {
		super(message);
	}

}
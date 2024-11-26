/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alberto Javier Moreno Lage
 */
public class TestEntityException extends PortalException {

	public TestEntityException(String msg) {
		super(msg);
	}

}
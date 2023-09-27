/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.scripting;

/**
 * @author Feliphe Marinho
 */
public interface ScriptingValidator {

	public String getLanguage();

	public void validate(String script) throws ScriptingException;

}
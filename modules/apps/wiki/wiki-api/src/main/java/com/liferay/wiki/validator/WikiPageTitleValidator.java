/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.validator;

import com.liferay.wiki.exception.PageTitleException;

/**
 * @author Roberto Díaz
 */
public interface WikiPageTitleValidator {

	public String normalize(String title);

	public void validate(String title) throws PageTitleException;

}
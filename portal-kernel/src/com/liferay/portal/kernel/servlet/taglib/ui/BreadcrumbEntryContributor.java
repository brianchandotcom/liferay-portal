/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet.taglib.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia Garcia
 */
public interface BreadcrumbEntryContributor {

	public List<BreadcrumbEntry> getBreadcrumbEntries(
		List<BreadcrumbEntry> breadcrumbEntries,
		HttpServletRequest httpServletRequest);

}
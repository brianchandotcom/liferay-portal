/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import java.util.HashMap;

/**
 * @author Julien Castelain
 */
public class PaginationBarLabels extends HashMap<String, String> {

	public PaginationBarLabels() {
	}

	public PaginationBarLabels(
		String paginationResults, String perPageItems,
		String selectPerPageItems) {

		setPaginationResults(paginationResults);
		setPerPageItems(perPageItems);
		setSelectPerPageItems(selectPerPageItems);
	}

	public void setPaginationResults(String paginationResults) {
		put("paginationResults", paginationResults);
	}

	public void setPerPageItems(String perPageItems) {
		put("perPageItems", perPageItems);
	}

	public void setSelectPerPageItems(String selectPerPageItems) {
		put("selectPerPageItems", selectPerPageItems);
	}

}
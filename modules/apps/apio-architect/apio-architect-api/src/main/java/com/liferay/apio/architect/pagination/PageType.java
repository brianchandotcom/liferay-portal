/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.apio.architect.pagination;

/**
 * Represents the different types of pages.
 *
 * @author Alejandro Hernández
 */
public enum PageType {

	CURRENT, FIRST, LAST, NEXT, PREVIOUS;

	/**
	 * Returns the corresponding page number for a certain page type.
	 *
	 * @param  page the original page
	 * @return the page number for the page type
	 */
	public Integer getPageNumber(Page page) {
		if (this == FIRST) {
			return 1;
		}

		if (this == LAST) {
			return page.getLastPageNumber();
		}

		int pageNumber = page.getPageNumber();

		if (this == CURRENT) {
			return pageNumber;
		}
		else if (this == PREVIOUS) {
			if (pageNumber == 1) {
				return 1;
			}

			return pageNumber - 1;
		}
		else {
			if (page.getLastPageNumber() == pageNumber) {
				return pageNumber;
			}

			return pageNumber + 1;
		}
	}

}
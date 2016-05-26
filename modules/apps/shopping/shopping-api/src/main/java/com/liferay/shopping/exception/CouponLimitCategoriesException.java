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

package com.liferay.shopping.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class CouponLimitCategoriesException extends PortalException {

	public CouponLimitCategoriesException() {
	}

	public CouponLimitCategoriesException(String msg) {
		super(msg);
	}

	public CouponLimitCategoriesException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CouponLimitCategoriesException(Throwable cause) {
		super(cause);
	}

	public List<Long> getCategoryIds() {
		return _categoryIds;
	}

	public void setCategoryIds(List<Long> categoryIds) {
		_categoryIds = categoryIds;
	}

	private List<Long> _categoryIds;

}
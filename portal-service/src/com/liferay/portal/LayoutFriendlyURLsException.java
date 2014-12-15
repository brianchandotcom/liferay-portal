/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergio González
 */
public class LayoutFriendlyURLsException extends PortalException {

	public LayoutFriendlyURLsException() {
		super();
	}

	public void addLayoutFriendlyURLException(
		LayoutFriendlyURLException lfurle) {

		_layoutFriendlyURLExceptions.add(lfurle);
	}

	public List<LayoutFriendlyURLException> getLayoutFriendlyURLExceptions() {
		return _layoutFriendlyURLExceptions;
	}

	private List<LayoutFriendlyURLException> _layoutFriendlyURLExceptions =
		new ArrayList<LayoutFriendlyURLException>();

}
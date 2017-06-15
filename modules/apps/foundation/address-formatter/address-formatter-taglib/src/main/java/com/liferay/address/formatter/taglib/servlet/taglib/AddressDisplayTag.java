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

package com.liferay.address.formatter.taglib.servlet.taglib;

import com.liferay.address.formatter.taglib.internal.servlet.ServletContextUtil;
import com.liferay.address.formatter.util.AddressFormatterHelper;
import com.liferay.portal.kernel.model.Address;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Pei-Jung Lan
 */
public class AddressDisplayTag extends IncludeTag {

	public void setAddress(Address address) {
		_address = address;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		_address = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-address-formatter:address-display:address", _address);
		request.setAttribute(
			"liferay-address-formatter:address-display:formattedAddress",
			_getFormattedAddress());
	}

	private String _getFormattedAddress() {
		return AddressFormatterHelper.format(_address);
	}

	private static final String _PAGE = "/address_display/page.jsp";

	private Address _address;

}
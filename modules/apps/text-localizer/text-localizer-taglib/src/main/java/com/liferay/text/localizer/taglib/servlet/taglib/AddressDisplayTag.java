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

package com.liferay.text.localizer.taglib.servlet.taglib;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;
import com.liferay.text.localizer.taglib.internal.address.util.AddressTextLocalizerUtil;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Pei-Jung Lan
 */
public class AddressDisplayTag extends ParamAndPropertyAncestorTagImpl {

	@Override
	public int doEndTag() throws JspException {
		JspWriter writer = pageContext.getOut();

		try {
			writer.write(
				StringUtil.replace(
					_getFormattedAddress(), CharPool.NEW_LINE, "<br />"));
		}
		catch (IOException ioe) {
			throw new JspException(ioe);
		}

		return BodyTag.EVAL_BODY_BUFFERED;
	}

	public void setAddress(Address address) {
		_address = address;
	}

	private String _getFormattedAddress() {
		return AddressTextLocalizerUtil.format(_address);
	}

	private Address _address;

}
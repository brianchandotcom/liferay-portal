/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.apache.bridges.struts;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.servlet.ServletInputStreamAdapter;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Michael Young
 * @author Deepak Gothe
 */
public class LiferayStrutsRequestImpl extends HttpServletRequestWrapper {

	public LiferayStrutsRequestImpl(HttpServletRequest request) {
		super(request);

		_strutsAttributes = (Map<String, Object>)request.getAttribute(
			WebKeys.STRUTS_BRIDGES_ATTRIBUTES);

		if (_strutsAttributes == null) {
			_strutsAttributes = new HashMap<String, Object>();

			request.setAttribute(
				WebKeys.STRUTS_BRIDGES_ATTRIBUTES, _strutsAttributes);
		}
	}

	@Override
	public Object getAttribute(String name) {
		Object value = null;

		if (name.startsWith(StrutsUtil.STRUTS_PACKAGE) &&
			_strutsAttributes.containsKey(name)) {

			value = _strutsAttributes.get(name);
		}
		else {
			value = super.getAttribute(name);
		}

		return value;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		List<String> attributeNames = new Vector<String>();

		Enumeration<String> enu = super.getAttributeNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (!name.startsWith(StrutsUtil.STRUTS_PACKAGE)) {
				attributeNames.add(name);
			}
		}

		attributeNames.addAll(_strutsAttributes.keySet());

		return Collections.enumeration(attributeNames);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (_bytes == null) {
			InputStream is = super.getInputStream();

			_bytes = FileUtil.getBytes(is);
		}

		return new ServletInputStreamAdapter(
			new UnsyncByteArrayInputStream(_bytes));
	}

	@Override
	public void removeAttribute(String name) {
		if (name.startsWith(StrutsUtil.STRUTS_PACKAGE) &&
			_strutsAttributes.containsKey(name)) {

			_strutsAttributes.remove(name);
		}
		else {
			super.removeAttribute(name);
		}
	}

	@Override
	public void setAttribute(String name, Object value) {
		if (name.startsWith(StrutsUtil.STRUTS_PACKAGE)) {
			_strutsAttributes.put(name, value);
		}
		else {
			super.setAttribute(name, value);
		}
	}

	private byte[] _bytes;
	private Map<String, Object> _strutsAttributes;

}
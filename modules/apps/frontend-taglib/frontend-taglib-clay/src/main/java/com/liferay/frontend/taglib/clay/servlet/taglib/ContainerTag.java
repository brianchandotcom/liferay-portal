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

package com.liferay.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.internal.servlet.taglib.BaseContainerTag;
import com.liferay.portal.kernel.util.Validator;

import java.util.Set;

import javax.servlet.jsp.JspException;

/**
 * @author Chema Balsas
 */
public class ContainerTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public boolean getFluid() {
		return _fluid;
	}

	public String getSize() {
		return _size;
	}

	public void setFluid(boolean fluid) {
		_fluid = fluid;
	}

	public void setSize(String size) {
		_size = size;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_fluid = false;
		_size = null;
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		if (!_fluid) {
			cssClasses.add("container");
		}
		else {
			cssClasses.add("container-fluid");

			if (Validator.isNotNull(_size)) {
				cssClasses.add("container-fluid-max-" + _size);
			}
		}

		return super.processCssClasses(cssClasses);
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:container:";

	private boolean _fluid;
	private String _size;

}
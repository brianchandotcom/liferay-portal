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
import com.liferay.petra.string.StringPool;

import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;

/**
 * @author Eduardo Allegrini
 */
public class PanelGroupTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);
		setDynamicAttribute(StringPool.BLANK, "aria-orentation", "vertical");
		setDynamicAttribute(StringPool.BLANK, "role", "tablist");

		return super.doStartTag();
	}

	public Boolean getFluid() {
		return _fluid;
	}

	public Boolean getFluidFirst() {
		return _fluidFirst;
	}

	public Boolean getFluidLast() {
		return _fluidLast;
	}

	public Boolean getFlush() {
		return _flush;
	}

	public Boolean getSmall() {
		return _small;
	}

	public void setFluid(Boolean fluid) {
		_fluid = fluid;
	}

	public void setFluidFirst(Boolean fluidFirst) {
		_fluidFirst = fluidFirst;
	}

	public void setFluidLast(Boolean fluidLast) {
		_fluidLast = fluidLast;
	}

	public void setFlush(Boolean flush) {
		_flush = flush;
	}

	public void setSmall(Boolean small) {
		_small = small;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_fluid = false;
		_fluidFirst = false;
		_fluidLast = false;
		_flush = false;
		_small = false;
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("fluid", _fluid);
		props.put("fluidFirst", _fluidFirst);
		props.put("fluidLast", _fluidLast);
		props.put("flush", _flush);
		props.put("small", _small);

		return super.prepareProps(props);
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		cssClasses.add("panel-group");

		if (_fluid) {
			cssClasses.add("panel-group-fluid");
		}

		if (_fluidFirst) {
			cssClasses.add("panel-group-fluid-first");
		}

		if (_fluidLast) {
			cssClasses.add("panel-group-fluid-last");
		}

		if (_flush) {
			cssClasses.add("panel-group-flush");
		}

		if (_small) {
			cssClasses.add("panel-group-sm");
		}

		return super.processCssClasses(cssClasses);
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:panel-group:";

	private boolean _fluid;
	private boolean _fluidFirst;
	private boolean _fluidLast;
	private boolean _flush;
	private boolean _small;

}
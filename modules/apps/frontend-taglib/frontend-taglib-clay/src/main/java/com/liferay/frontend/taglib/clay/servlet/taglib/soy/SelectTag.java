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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayTag;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption;

import java.util.List;

/**
 * @author Chema Balsas
 */
public class SelectTag extends BaseClayTag {

	@Override
	public int doStartTag() {
		setComponentBaseName("ClaySelect");
		setModuleBaseName("select");

		return super.doStartTag();
	}

	public void setDisabled(Boolean disabled) {
		putValue("disabled", disabled);
	}

	public void setLabel(String label) {
		putValue("label", label);
	}

	public void setMultiple(Boolean multiple) {
		putValue("multiple", multiple);
	}

	public void setName(String name) {
		putValue("name", name);
	}

	public void setOptions(List<SelectOption> selectOptions) {
		putValue("options", selectOptions);
	}

	public void setWrapperType(String wrapperType) {
		putValue("wrapperType", wrapperType);
	}

}
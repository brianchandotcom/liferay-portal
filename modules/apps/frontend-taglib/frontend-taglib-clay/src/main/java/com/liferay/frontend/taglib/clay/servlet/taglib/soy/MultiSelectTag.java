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

import java.util.HashMap;
import java.util.List;

/**
 * @author Luismi Barcos
 */
public class MultiSelectTag extends BaseClayTag {

	@Override
	public int doStartTag() {
		setComponentBaseName("ClayMultiSelect");
		setHydrate(true);
		setModuleBaseName("multi-select");

		return super.doStartTag();
	}

	public void setDataSource(Object dataSource) {
		putValue("dataSource", dataSource);
	}

	public void setExtractData(Object extractData) {
		putValue("extractData", extractData);
	}

	public void setHelpText(String helpText) {
		putValue("helpText", helpText);
	}

	public void setInputName(String inputName) {
		putValue("inputName", inputName);
	}

	public void setLabel(String label) {
		putValue("label", label);
	}

	public void setLabelLocator(String labelLocator) {
		putValue("labelLocator", labelLocator);
	}

	public void setRequestOptions(HashMap<String, Object> requestOptions) {
		putValue("requestOptions", requestOptions);
	}

	public void setSelectedItems(List<Object> selectedItems) {
		putValue("selectedItems", selectedItems);
	}

	public void setValueLocator(String valueLocator) {
		putValue("valueLocator", valueLocator);
	}

}
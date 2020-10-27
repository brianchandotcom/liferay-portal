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

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Carlos Lancha
 */
public class LabelItem extends HashMap<String, Object> {

	public LabelItem() {
		put("closeable", false);
		put("dismissible", false);
		put("large", false);
	}

	public void putData(String key, String value) {
		Map<String, Object> data = (Map<String, Object>)get("data");

		if (data == null) {
			data = new HashMap<>();

			put("data", data);
		}

		data.put(key, value);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #setDismissible()}
	 */
	@Deprecated
	public void setCloseable(boolean closeable) {
		put("closeable", closeable);
		setDismissible(closeable);
	}

	public void setData(Map<String, Object> data) {
		put("data", data);
	}

	public void setDismissible(boolean dismissible) {
		put("dismissible", dismissible);
	}

	public void setDisplayType(String displayType) {
		put("displayType", displayType);
	}

	public void setLabel(String label) {
		put("label", label);
	}

	public void setLarge(boolean large) {
		put("large", large);
	}

	public void setStatus(int status) {
		setLabel(WorkflowConstants.getStatusLabel(status));

		setStyle(WorkflowConstants.getStatusStyle(status));
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #setDisplayType()}
	 */
	@Deprecated
	public void setStyle(String style) {
		put("style", style);
		setDisplayType(style);
	}

}
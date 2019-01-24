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

	public static String getStyleFromWorkflowStatus(int status) {
		if (status == WorkflowConstants.STATUS_APPROVED) {
			return "success";
		}
		else if (status == WorkflowConstants.STATUS_DENIED) {
			return "danger";
		}
		else if (status == WorkflowConstants.STATUS_DRAFT) {
			return "secondary";
		}
		else if (status == WorkflowConstants.STATUS_EXPIRED) {
			return "danger";
		}
		else if (status == WorkflowConstants.STATUS_IN_TRASH) {
			return "info";
		}
		else if (status == WorkflowConstants.STATUS_INACTIVE) {
			return "secondary";
		}
		else if (status == WorkflowConstants.STATUS_INCOMPLETE) {
			return "warning";
		}
		else if (status == WorkflowConstants.STATUS_PENDING) {
			return "info";
		}
		else if (status == WorkflowConstants.STATUS_SCHEDULED) {
			return "info";
		}

		return "secondary";
	}

	public LabelItem() {
		put("closeable", false);
	}

	public void putData(String key, String value) {
		Map<String, Object> data = (Map<String, Object>)get("data");

		if (data == null) {
			data = new HashMap<>();

			put("data", data);
		}

		data.put(key, value);
	}

	public void setCloseable(boolean closeable) {
		put("closeable", closeable);
	}

	public void setData(Map<String, Object> data) {
		put("data", data);
	}

	public void setLabel(String label) {
		put("label", label);
	}

	public void setStatus(int status) {
		setLabel(WorkflowConstants.getStatusLabel(status));

		setStyle(getStyleFromWorkflowStatus(status));
	}

	public void setStyle(String style) {
		put("style", style);
	}

}
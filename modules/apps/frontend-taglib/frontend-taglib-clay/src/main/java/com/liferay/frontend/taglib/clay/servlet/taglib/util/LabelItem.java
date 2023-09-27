/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
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
		setLabel(
			LanguageUtil.get(
				LocaleUtil.getMostRelevantLocale(),
				WorkflowConstants.getStatusLabel(status)));
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
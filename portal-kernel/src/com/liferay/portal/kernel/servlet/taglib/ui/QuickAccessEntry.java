/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet.taglib.ui;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Eudaldo Alonso
 */
public class QuickAccessEntry {

	public String getContent() {
		if (Validator.isNotNull(_label)) {
			return _label;
		}

		return _bodySB.toString();
	}

	public String getData() {
		return _data;
	}

	public String getId() {
		return _id;
	}

	public String getLabel() {
		return _label;
	}

	public String getOnClick() {
		return _onClick;
	}

	public String getURL() {
		return _url;
	}

	public void setBody(StringBundler bodySB) {
		_bodySB = bodySB;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #setBody(StringBundler)}
	 */
	@Deprecated
	public void setBody(com.liferay.portal.kernel.util.StringBundler bodySB) {
		for (int i = 0; i < bodySB.index(); i++) {
			_bodySB.append(bodySB.stringAt(0));
		}
	}

	public void setData(String data) {
		_data = data;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	public void setURL(String url) {
		_url = url;
	}

	private StringBundler _bodySB;
	private String _data;
	private String _id;
	private String _label;
	private String _onClick;
	private String _url;

}
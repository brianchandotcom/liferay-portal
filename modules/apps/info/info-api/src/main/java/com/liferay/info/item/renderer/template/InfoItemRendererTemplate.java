/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item.renderer.template;

/**
 * @author Eudaldo Alonso
 */
public class InfoItemRendererTemplate {

	public InfoItemRendererTemplate(String label, String templateKey) {
		_label = label;
		_templateKey = templateKey;
	}

	public String getLabel() {
		return _label;
	}

	public String getTemplateKey() {
		return _templateKey;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setTemplateKey(String templateKey) {
		_templateKey = templateKey;
	}

	private String _label;
	private String _templateKey;

}
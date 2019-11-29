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
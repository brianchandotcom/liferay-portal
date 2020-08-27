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

package com.liferay.commerce.frontend.model;

/**
 * @author Alessio Antonio Rendina
 * @author Alec Sloan
 */
public class Sku {

	public Sku(String label, String href) {
		_label = label;
		_href = href;
	}

	public String getHref() {
		return _href;
	}

	public String getLabel() {
		return _label;
	}

	private final String _href;
	private final String _label;

}
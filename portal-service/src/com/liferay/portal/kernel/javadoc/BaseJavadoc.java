/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.javadoc;

/**
 * @author Igor Spasic
 */
public abstract class BaseJavadoc {

	public String getComment() {
		return _comment;
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	public void setComment(String comment) {
		this._comment = comment;
	}

	public void setServletContextName(String servletContextName) {
		this._servletContextName = servletContextName;
	}

	private String _comment;
	private String _servletContextName;

}
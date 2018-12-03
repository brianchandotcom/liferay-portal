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

/**
 * @author Chema Balsas
 */
public class StripeTag extends AlertTag {

	@Override
	public int doStartTag() {
		setComponentBaseName("ClayStripe");
		setHydrate(true);
		setModuleBaseName("alert");

		return super.doStartTag();
	}

	@Override
	public void setAutoclose(Boolean autoClose) {
		putValue("autoClose", autoClose);
	}

	@Override
	public void setCloseable(Boolean closeable) {
		putValue("closeable", closeable);
	}

	@Override
	public void setDestroyOnHide(Boolean destroyOnHide) {
		putValue("destroyOnHide", destroyOnHide);
	}

	@Override
	public void setMessage(String message) {
		putValue("message", message);
	}

	@Override
	public void setStyle(String style) {
		putValue("style", style);
	}

	@Override
	public void setTitle(String title) {
		putValue("title", title);
	}

	@Override
	public void setType(String type) {
		putValue("type", type);
	}

}
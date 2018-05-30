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

package com.liferay.portlet.internal;

import com.liferay.portlet.RenderData;

/**
 * @author Neil Griffin
 */
public class RenderDataImpl implements RenderData {

	public RenderDataImpl(String contentType, String content) {
		_contentType = contentType;
		_content = content;
	}

	@Override
	public String getContent() {
		return _content;
	}

	@Override
	public String getContentType() {
		return _contentType;
	}

	private final String _content;
	private final String _contentType;

}
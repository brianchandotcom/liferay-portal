/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class InvokerPortletResponse implements Serializable {

	public InvokerPortletResponse(String title, String content, long time) {
		_title = title;
		_content = content;
		_time = time;
	}

	public String getContent() {
		return _content;
	}

	public long getTime() {
		return _time;
	}

	public String getTitle() {
		return _title;
	}

	public void setContent(String content) {
		_content = content;
	}

	public void setTime(long time) {
		_time = time;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private String _content;
	private long _time;
	private String _title;

}
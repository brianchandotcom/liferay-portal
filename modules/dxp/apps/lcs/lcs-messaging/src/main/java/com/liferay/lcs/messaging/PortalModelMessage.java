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

package com.liferay.lcs.messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Riccardo Ferrari
 */
public class PortalModelMessage extends Message {

	public List<Map<String, Object>> getModels() {
		return _models;
	}

	public long getPageEnd() {
		return _pageEnd;
	}

	public long getPageStart() {
		return _pageStart;
	}

	public long getQueryStartTime() {
		return _queryStartTime;
	}

	public long getResultCount() {
		return _resultCount;
	}

	public Type getType() {
		return _type;
	}

	public void setModels(List<Map<String, Object>> models) {
		_models = models;
	}

	public void setPageEnd(long pageEnd) {
		_pageEnd = pageEnd;
	}

	public void setPageStart(long pageStart) {
		_pageStart = pageStart;
	}

	public void setQueryStartTime(long queryStartTime) {
		_queryStartTime = queryStartTime;
	}

	public void setResultCount(long resultCount) {
		_resultCount = resultCount;
	}

	public void setType(Type type) {
		_type = type;
	}

	public enum Type {

		ORGANIZATION, SITE, USER_GROUP

	}

	private List<Map<String, Object>> _models =
		new ArrayList<Map<String, Object>>();
	private long _pageEnd;
	private long _pageStart;
	private long _queryStartTime;
	private long _resultCount;
	private Type _type;

}
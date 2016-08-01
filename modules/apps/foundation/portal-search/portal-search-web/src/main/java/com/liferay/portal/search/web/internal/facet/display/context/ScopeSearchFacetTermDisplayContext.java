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

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;

import java.util.Locale;

/**
 * @author André de Oliveira
 */
public class ScopeSearchFacetTermDisplayContext {

	public ScopeSearchFacetTermDisplayContext(
		Group group, boolean selected, int count, boolean showCount,
		Locale locale) {

		_group = group;
		_selected = selected;
		_count = count;
		_showCount = showCount;
		_locale = locale;
	}

	public int getCount() {
		return _count;
	}

	public String getDescriptiveName() throws PortalException {
		return _group.getDescriptiveName(_locale);
	}

	public long getGroupId() {
		return _group.getGroupId();
	}

	public boolean isSelected() {
		return _selected;
	}

	public boolean isShowCount() {
		return _showCount;
	}

	private final int _count;
	private final Group _group;
	private final Locale _locale;
	private final boolean _selected;
	private final boolean _showCount;

}
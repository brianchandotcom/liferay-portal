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

/**
 * @author Lino Alves
 */
public class FolderSearchFacetTermDisplayContext {

	public FolderSearchFacetTermDisplayContext(
		long folderId, String displayName, boolean selected, int frequency,
		boolean showFrequency) {

		_folderId = folderId;
		_displayName = displayName;
		_selected = selected;
		_frequency = frequency;
		_showFrequency = showFrequency;
	}

	public String getDisplayName() throws PortalException {
		return _displayName;
	}

	public long getFolderId() {
		return _folderId;
	}

	public int getFrequency() {
		return _frequency;
	}

	public boolean isSelected() {
		return _selected;
	}

	public boolean isShowFrequency() {
		return _showFrequency;
	}

	private final String _displayName;
	private final long _folderId;
	private final int _frequency;
	private final boolean _selected;
	private final boolean _showFrequency;

}
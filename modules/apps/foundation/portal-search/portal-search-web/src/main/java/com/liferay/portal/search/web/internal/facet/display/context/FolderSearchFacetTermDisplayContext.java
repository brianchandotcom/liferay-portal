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

import java.io.Serializable;

/**
 * @author Lino Alves
 */
public class FolderSearchFacetTermDisplayContext implements Serializable {

	public String getDisplayName() throws PortalException {
		return _displayName;
	}

	public long getFolderId() {
		return _folderId;
	}

	public int getFrequency() {
		return _frequency;
	}

	public boolean isFrequencyVisible() {
		return _frequencyVisible;
	}

	public boolean isSelected() {
		return _selected;
	}

	public void setDisplayName(String displayName) {
		_displayName = displayName;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	public void setFrequency(int frequency) {
		_frequency = frequency;
	}

	public void setFrequencyVisible(boolean frequencyVisible) {
		_frequencyVisible = frequencyVisible;
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	private String _displayName;
	private long _folderId;
	private int _frequency;
	private boolean _frequencyVisible;
	private boolean _selected;

}
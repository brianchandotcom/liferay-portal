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

package com.liferay.dataset.view.internal.active;

import com.liferay.dataset.view.active.DatasetViewActiveSettings;
import com.liferay.dataset.view.model.DatasetViewStateEntry;

/**
 * @author Iván Zaera Avellón
 */
public class DatasetViewActiveSettingsImpl
	implements DatasetViewActiveSettings {

	public DatasetViewActiveSettingsImpl(
		DatasetViewStateEntry datasetViewStateEntry) {

		_datasetViewStateEntry = datasetViewStateEntry;
	}

	public DatasetViewStateEntry getDatasetViewStateEntry() {
		return _datasetViewStateEntry;
	}

	@Override
	public String getViewState() {
		return _datasetViewStateEntry.getViewState();
	}

	@Override
	public void setViewState(String viewState) {
		_datasetViewStateEntry.setViewState(viewState);
	}

	private final DatasetViewStateEntry _datasetViewStateEntry;

}
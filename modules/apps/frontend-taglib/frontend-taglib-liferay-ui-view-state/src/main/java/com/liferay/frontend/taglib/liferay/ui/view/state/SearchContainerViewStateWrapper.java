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

package com.liferay.frontend.taglib.liferay.ui.view.state;

import org.osgi.annotation.versioning.ConsumerType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ConsumerType
public abstract class SearchContainerViewStateWrapper
	implements SearchContainerViewState {

	public SearchContainerViewStateWrapper(
		SearchContainerViewState searchContainerViewState) {

		_searchContainerViewState = searchContainerViewState;
	}

	@Override
	public int getCur() {
		return _searchContainerViewState.getCur();
	}

	@Override
	public int getDelta() {
		return _searchContainerViewState.getDelta();
	}

	@Override
	public String getDisplayStyle() {
		return _searchContainerViewState.getDisplayStyle();
	}

	@Override
	public int getEnd() {
		return _searchContainerViewState.getEnd();
	}

	@Override
	public String getKeywords() {
		return _searchContainerViewState.getKeywords();
	}

	@Override
	public String getOrderByCol() {
		return _searchContainerViewState.getOrderByCol();
	}

	@Override
	public String getOrderByType() {
		return _searchContainerViewState.getOrderByType();
	}

	@Override
	public boolean getResetCur() {
		return _searchContainerViewState.getResetCur();
	}

	@Override
	public int getStart() {
		return _searchContainerViewState.getStart();
	}

	public SearchContainerViewState getWrapped() {
		return _searchContainerViewState;
	}

	private final SearchContainerViewState _searchContainerViewState;

}
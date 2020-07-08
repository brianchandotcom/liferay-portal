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

package com.liferay.frontend.taglib.clay.servlet.taglib.view.state;

import org.osgi.annotation.versioning.ConsumerType;

/**
 * @author  Neil Griffin
 */
@ConsumerType
public abstract class SearchContainerViewStateWrapper
	implements SearchContainerViewState {

	public SearchContainerViewStateWrapper(
		SearchContainerViewState searchContainerViewState) {

		_wrappedSearchContainerViewState = searchContainerViewState;
	}

	@Override
	public int getCur() {
		return _wrappedSearchContainerViewState.getCur();
	}

	@Override
	public int getDelta() {
		return _wrappedSearchContainerViewState.getDelta();
	}

	@Override
	public String getDisplayStyle() {
		return _wrappedSearchContainerViewState.getDisplayStyle();
	}

	@Override
	public int getEnd() {
		return _wrappedSearchContainerViewState.getEnd();
	}

	@Override
	public String getKeywords() {
		return _wrappedSearchContainerViewState.getKeywords();
	}

	@Override
	public String getOrderByCol() {
		return _wrappedSearchContainerViewState.getOrderByCol();
	}

	@Override
	public String getOrderByType() {
		return _wrappedSearchContainerViewState.getOrderByType();
	}

	@Override
	public boolean getResetCur() {
		return _wrappedSearchContainerViewState.getResetCur();
	}

	@Override
	public int getStart() {
		return _wrappedSearchContainerViewState.getStart();
	}

	public SearchContainerViewState getWrapped() {
		return _wrappedSearchContainerViewState;
	}

	private final SearchContainerViewState _wrappedSearchContainerViewState;

}
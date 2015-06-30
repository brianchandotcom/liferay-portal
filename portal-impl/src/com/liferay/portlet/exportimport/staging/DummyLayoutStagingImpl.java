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

package com.liferay.portlet.exportimport.staging;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.LayoutSetStagingHandler;
import com.liferay.portal.model.LayoutStagingHandler;
import com.liferay.portal.model.impl.LayoutRevisionImpl;
import com.liferay.portal.model.impl.LayoutSetBranchImpl;

/**
 * @author Daniel Kocsis
 */
public class DummyLayoutStagingImpl implements LayoutStaging {

	@Override
	public LayoutRevision getLayoutRevision(Layout layout) {
		return new LayoutRevisionImpl();
	}

	@Override
	public LayoutSetBranch getLayoutSetBranch(LayoutSet layoutSet) {
		return new LayoutSetBranchImpl();
	}

	@Override
	public LayoutSetStagingHandler getLayoutSetStagingHandler(
		LayoutSet layoutSet) {

		return new LayoutSetStagingHandler(layoutSet);
	}

	@Override
	public LayoutStagingHandler getLayoutStagingHandler(Layout layout) {
		return new LayoutStagingHandler(layout);
	}

	@Override
	public boolean isBranchingLayout(Layout layout) {
		return false;
	}

	@Override
	public boolean isBranchingLayoutSet(Group group, boolean privateLayout) {
		return false;
	}

}
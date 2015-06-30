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
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Daniel Kocsis
 */
public class BridgeLayoutStagingImpl implements LayoutStaging {

	public BridgeLayoutStagingImpl() {
		this(new DummyLayoutStagingImpl());
	}

	public BridgeLayoutStagingImpl(LayoutStaging defaultLayoutStaging) {
		_defaultLayoutStaging = defaultLayoutStaging;

		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(LayoutStaging.class);

		_serviceTracker.open();
	}

	@Override
	public LayoutRevision getLayoutRevision(Layout layout) {
		return getLayoutStaging().getLayoutRevision(layout);
	}

	@Override
	public LayoutSetBranch getLayoutSetBranch(LayoutSet layoutSet) {
		return getLayoutStaging().getLayoutSetBranch(layoutSet);
	}

	@Override
	public LayoutSetStagingHandler getLayoutSetStagingHandler(
		LayoutSet layoutSet) {

		return getLayoutStaging().getLayoutSetStagingHandler(layoutSet);
	}

	@Override
	public LayoutStagingHandler getLayoutStagingHandler(Layout layout) {
		return getLayoutStaging().getLayoutStagingHandler(layout);
	}

	@Override
	public boolean isBranchingLayout(Layout layout) {
		return getLayoutStaging().isBranchingLayout(layout);
	}

	@Override
	public boolean isBranchingLayoutSet(Group group, boolean privateLayout) {
		return getLayoutStaging().isBranchingLayoutSet(group, privateLayout);
	}

	protected LayoutStaging getLayoutStaging() {
		if (_serviceTracker.isEmpty()) {
			return _defaultLayoutStaging;
		}

		return _serviceTracker.getService();
	}

	private final LayoutStaging _defaultLayoutStaging;
	private final ServiceTracker<LayoutStaging, LayoutStaging> _serviceTracker;

}
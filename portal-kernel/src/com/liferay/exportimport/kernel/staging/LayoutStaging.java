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

package com.liferay.exportimport.kernel.staging;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutSetStagingHandler;
import com.liferay.portal.kernel.model.LayoutStagingHandler;

/**
 * @author Raymond Augé
 */
@ProviderType
public interface LayoutStaging {

	public LayoutRevision getLayoutRevision(Layout layout);

	public LayoutSetBranch getLayoutSetBranch(LayoutSet layoutSet);

	public LayoutSetStagingHandler getLayoutSetStagingHandler(
		LayoutSet layoutSet);

	public LayoutStagingHandler getLayoutStagingHandler(Layout layout);

	public boolean isBranchingLayout(Layout layout);

	public boolean isBranchingLayoutSet(Group group, boolean privateLayout);

	public Layout mergeLayoutRevisionIntoLayout(Layout layout);

	public LayoutSet mergeLayoutSetRevisionIntoLayoutSet(LayoutSet layoutSet);

	public boolean prepareLayoutStagingHandler(
		PortletDataContext portletDataContext, Layout layout);

}
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

package com.liferay.layout.set.prototype.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public interface LayoutSetPrototypeHelper {

	public List<Layout> getDuplicatedFriendlyURLLayouts(Layout layout)
		throws PortalException;

	public List<Long> getDuplicatedFriendlyURLPlids(LayoutSet layoutSet);

	public List<Long> getDuplicatedFriendlyURLPlids(
			LayoutSetPrototype layoutSetPrototype)
		throws PortalException;

	public boolean hasDuplicatedFriendlyURLs(
			long groupId, boolean privateLayout, String layoutUuid,
			String friendlyURL)
		throws PortalException;

}
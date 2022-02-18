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

package com.liferay.commerce.inventory.web.internal.frontend.data.set.view.timeline;

import com.liferay.commerce.inventory.web.internal.constants.CommerceInventoryFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.timeline.BaseTimelineFDSView;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = "frontend.data.set.name=" + CommerceInventoryFDSNames.INVENTORY_AUDIT,
	service = FDSView.class
)
public class CommerceInventoryAuditTimelineFDSView extends BaseTimelineFDSView {

	@Override
	public String getDate() {
		return "date";
	}

	@Override
	public String getDescription() {
		return "description";
	}

	@Override
	public String getTitle() {
		return "title";
	}

}
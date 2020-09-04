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

package com.liferay.commerce.price.list.portlet.action;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

/**
 * @author Alessio Antonio Rendina
 */
public interface CommercePriceListActionHelper {

	public List<CommercePriceEntry> getCommercePriceEntries(
			PortletRequest portletRequest)
		throws PortalException;

	public CommercePriceEntry getCommercePriceEntry(RenderRequest renderRequest)
		throws PortalException;

	public CommercePriceList getCommercePriceList(PortletRequest portletRequest)
		throws PortalException;

	public List<CommercePriceList> getCommercePriceLists(
			PortletRequest portletRequest)
		throws PortalException;

	public List<CommerceTierPriceEntry> getCommerceTierPriceEntries(
			PortletRequest portletRequest)
		throws PortalException;

	public CommerceTierPriceEntry getCommerceTierPriceEntry(
			RenderRequest renderRequest)
		throws PortalException;

}
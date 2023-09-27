/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
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

package com.liferay.commerce.price.list.web.internal.display.context;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.portlet.action.CommercePriceListActionHelper;
import com.liferay.commerce.product.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.CustomAttributesUtil;

import javax.portlet.PortletURL;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPInstanceCommerceTierPriceEntryDisplayContext
	extends BaseCPDefinitionsDisplayContext {

	public CPInstanceCommerceTierPriceEntryDisplayContext(
		ActionHelper actionHelper,
		CommercePriceListActionHelper commercePriceListActionHelper,
		HttpServletRequest httpServletRequest) {

		super(actionHelper, httpServletRequest);

		_commercePriceListActionHelper = commercePriceListActionHelper;
	}

	public CommercePriceEntry getCommercePriceEntry() throws PortalException {
		return _commercePriceListActionHelper.getCommercePriceEntry(
			cpRequestHelper.getRenderRequest());
	}

	public long getCommercePriceEntryId() throws PortalException {
		long commercePriceEntryId = 0;

		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		if (commercePriceEntry != null) {
			commercePriceEntryId = commercePriceEntry.getCommercePriceEntryId();
		}

		return commercePriceEntryId;
	}

	public CommerceTierPriceEntry getCommerceTierPriceEntry()
		throws PortalException {

		if (_commerceTierPriceEntry != null) {
			return _commerceTierPriceEntry;
		}

		_commerceTierPriceEntry =
			_commercePriceListActionHelper.getCommerceTierPriceEntry(
				cpRequestHelper.getRenderRequest());

		return _commerceTierPriceEntry;
	}

	public long getCommerceTierPriceEntryId() throws PortalException {
		CommerceTierPriceEntry commerceTierPriceEntry =
			getCommerceTierPriceEntry();

		if (commerceTierPriceEntry == null) {
			return 0;
		}

		return commerceTierPriceEntry.getCommerceTierPriceEntryId();
	}

	public String getContextTitle() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CommerceTierPriceEntry commerceTierPriceEntry =
			getCommerceTierPriceEntry();

		if (commerceTierPriceEntry == null) {
			return LanguageUtil.get(
				themeDisplay.getRequest(), "add-tier-price-entry");
		}

		StringBundler sb = new StringBundler(5);

		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		if (commercePriceEntry != null) {
			CPInstance cpInstance = commercePriceEntry.getCPInstance();

			if (cpInstance != null) {
				CPDefinition cpDefinition = cpInstance.getCPDefinition();

				if (cpDefinition != null) {
					sb.append(
						cpDefinition.getName(themeDisplay.getLanguageId()));
					sb.append(" - ");
					sb.append(cpInstance.getSku());

					CommercePriceList commercePriceList =
						commercePriceEntry.getCommercePriceList();

					if (commercePriceList != null) {
						sb.append(" - ");
						sb.append(commercePriceList.getName());
					}
				}
			}
		}

		return sb.toString();
	}

	public CPInstance getCPInstance() throws PortalException {
		if (_cpInstance != null) {
			return _cpInstance;
		}

		_cpInstance = actionHelper.getCPInstance(
			cpRequestHelper.getRenderRequest());

		return _cpInstance;
	}

	public long getCPInstanceId() throws PortalException {
		long cpInstanceId = 0;

		CPInstance cpInstance = getCPInstance();

		if (cpInstance != null) {
			cpInstanceId = cpInstance.getCPInstanceId();
		}

		return cpInstanceId;
	}

	public CreationMenu getCreationMenu() throws Exception {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(_getAddCommerceTierPriceEntryURL());
				dropdownItem.setLabel(StringPool.BLANK);
				dropdownItem.setTarget("modal-lg");
			}
		).build();
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCPInstanceCommerceTierPriceEntries");
		portletURL.setParameter(
			"commercePriceEntryId", String.valueOf(getCommercePriceEntryId()));
		portletURL.setParameter(
			"cpInstanceId", String.valueOf(getCPInstanceId()));

		return portletURL;
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return "price-lists";
	}

	public boolean hasCustomAttributes() throws Exception {
		return CustomAttributesUtil.hasCustomAttributes(
			cpRequestHelper.getCompanyId(),
			CommerceTierPriceEntry.class.getName(),
			getCommerceTierPriceEntryId(), null);
	}

	private String _getAddCommerceTierPriceEntryURL() throws Exception {
		RenderURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editCPInstanceCommerceTierPriceEntry");
		portletURL.setParameter("redirect", cpRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"commercePriceEntryId", String.valueOf(getCommercePriceEntryId()));
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(getCPDefinitionId()));
		portletURL.setParameter(
			"cpInstanceId", String.valueOf(getCPInstanceId()));
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	private final CommercePriceListActionHelper _commercePriceListActionHelper;
	private CommerceTierPriceEntry _commerceTierPriceEntry;
	private CPInstance _cpInstance;

}
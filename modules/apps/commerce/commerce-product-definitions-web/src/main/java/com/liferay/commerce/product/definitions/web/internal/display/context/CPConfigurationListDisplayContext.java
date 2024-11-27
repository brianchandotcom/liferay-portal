/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.commerce.product.model.CPConfigurationEntry;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPConfigurationEntryService;
import com.liferay.commerce.product.service.CPConfigurationListService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.commerce.product.servlet.taglib.ui.constants.CPDefinitionScreenNavigationConstants;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Sbarra
 */
public class CPConfigurationListDisplayContext {

	public CPConfigurationListDisplayContext(
		CommerceCatalogService commerceCatalogService,
		CPConfigurationEntryService cpConfigurationEntryService,
		CPConfigurationListService cpConfigurationListService,
		CPDefinitionService cpDefinitionService,
		HttpServletRequest httpServletRequest) {

		this.commerceCatalogService = commerceCatalogService;
		this.cpConfigurationEntryService = cpConfigurationEntryService;
		this.cpConfigurationListService = cpConfigurationListService;
		this.cpDefinitionService = cpDefinitionService;
		this.httpServletRequest = httpServletRequest;

		cpRequestHelper = new CPRequestHelper(httpServletRequest);

		liferayPortletResponse = cpRequestHelper.getLiferayPortletResponse();
	}

	public List<CommerceCatalog> getCommerceCatalogs() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return commerceCatalogService.search(
			themeDisplay.getCompanyId(), null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public Map<String, Object> getContext() {
		return HashMapBuilder.<String, Object>put(
			"addCPConfigurationListRenderURL",
			() -> PortletURLBuilder.createRenderURL(
				liferayPortletResponse
			).setMVCRenderCommandName(
				"/cp_configuration_lists/add_cp_configuration_list"
			).setBackURL(
				cpRequestHelper.getCurrentURL()
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString()
		).put(
			"editCPConfigurationListRenderURL",
			() -> PortletURLBuilder.createRenderURL(
				liferayPortletResponse
			).setMVCRenderCommandName(
				"/cp_configuration_lists/edit_cp_configuration_list"
			).setBackURL(
				cpRequestHelper.getCurrentURL()
			).buildString()
		).put(
			"namespace", liferayPortletResponse.getNamespace()
		).put(
			"windowState", LiferayWindowState.MAXIMIZED.toString()
		).build();
	}

	public CPConfigurationEntry getCPConfigurationEntry()
		throws PortalException {

		long cpConfigurationEntryId = getCPConfigurationEntryId();

		if (cpConfigurationEntryId == 0) {
			return null;
		}

		return cpConfigurationEntryService.getCPConfigurationEntry(
			cpConfigurationEntryId);
	}

	public List<FDSActionDropdownItem>
			getCPConfigurationEntryFDSActionDropdownItems()
		throws PortalException {

		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					PortletProviderUtil.getPortletURL(
						httpServletRequest, CPConfigurationList.class.getName(),
						PortletProvider.Action.MANAGE)
				).setMVCRenderCommandName(
					"/cp_configuration_lists/edit_cp_configuration_entry"
				).setParameter(
					"cpConfigurationEntryId", "{id}"
				).buildString(),
				"pencil", "edit", LanguageUtil.get(httpServletRequest, "edit"),
				"get", null, "sidePanel"),
			new FDSActionDropdownItem(
				"/o/headless-commerce-admin-catalog/v1.0" +
					"/product-configurations/{id}",
				"trash", "delete",
				LanguageUtil.get(httpServletRequest, "delete"), "delete",
				"delete", "async"));
	}

	public long getCPConfigurationEntryId() {
		return ParamUtil.getLong(httpServletRequest, "cpConfigurationEntryId");
	}

	public CPConfigurationList getCPConfigurationList() throws PortalException {
		long cpConfigurationListId = getCPConfigurationEntryId();

		if (cpConfigurationListId == 0) {
			return null;
		}

		return cpConfigurationListService.getCPConfigurationList(
			cpConfigurationListId);
	}

	public List<FDSActionDropdownItem>
			getCPConfigurationListFDSActionDropdownItems()
		throws PortalException {

		StringBundler sb = new StringBundler(
			"/o/headless-commerce-admin-catalog/v1.0" +
				"/product-configuration-lists/{id}");

		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					PortletProviderUtil.getPortletURL(
						httpServletRequest, CPConfigurationList.class.getName(),
						PortletProvider.Action.MANAGE)
				).setMVCRenderCommandName(
					"/cp_configuration_lists/edit_cp_configuration_list"
				).setParameter(
					"cpConfigurationListId", "{id}"
				).setParameter(
					"screenNavigationCategoryKey",
					CPDefinitionScreenNavigationConstants.CATEGORY_KEY_DETAILS
				).buildString(),
				"pencil", "edit", LanguageUtil.get(httpServletRequest, "edit"),
				"get", null, null),
			new FDSActionDropdownItem(
				sb.toString(), "trash", "delete",
				LanguageUtil.get(httpServletRequest, "delete"), "delete",
				"delete", "async"));
	}

	public long getCPConfigurationListId() {
		return ParamUtil.getLong(httpServletRequest, "cpConfigurationListId");
	}

	public CreationMenu getCreationMenu() throws Exception {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref("addCPConfigurationList");
				dropdownItem.setLabel("add-new-product-configuration");
				dropdownItem.setTarget("event");
			}
		).build();
	}

	public String getProductName() throws PortalException {
		CPConfigurationEntry cpConfigurationEntry = getCPConfigurationEntry();

		CPDefinition cpDefinition = cpDefinitionService.getCPDefinition(
			cpConfigurationEntry.getClassPK());

		return cpDefinition.getName(
			LocaleUtil.toLanguageId(cpRequestHelper.getLocale()));
	}

	protected final CommerceCatalogService commerceCatalogService;
	protected final CPConfigurationEntryService cpConfigurationEntryService;
	protected final CPConfigurationListService cpConfigurationListService;
	protected final CPDefinitionService cpDefinitionService;
	protected final CPRequestHelper cpRequestHelper;
	protected final HttpServletRequest httpServletRequest;
	protected final LiferayPortletResponse liferayPortletResponse;

}
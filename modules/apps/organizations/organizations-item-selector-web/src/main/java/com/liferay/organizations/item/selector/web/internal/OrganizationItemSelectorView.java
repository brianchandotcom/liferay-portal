/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.organizations.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.organizations.item.selector.OrganizationItemSelectorCriterion;
import com.liferay.organizations.item.selector.web.internal.display.context.OrganizationItemSelectorViewDisplayContext;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ItemSelectorView.class)
public class OrganizationItemSelectorView
	implements ItemSelectorView<OrganizationItemSelectorCriterion> {

	@Override
	public Class<OrganizationItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return OrganizationItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(
			_portal.getResourceBundle(locale), "organizations");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			OrganizationItemSelectorCriterion organizationItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)servletRequest;

		OrganizationItemSelectorViewDisplayContext
			organizationItemSelectorViewDisplayContext =
				new OrganizationItemSelectorViewDisplayContext(
					organizationItemSelectorCriterion,
					_organizationLocalService, _usersAdmin, httpServletRequest,
					portletURL);

		_itemSelectorViewDescriptorRenderer.renderHTML(
			httpServletRequest, servletResponse,
			organizationItemSelectorCriterion, portletURL,
			itemSelectedEventName, search,
			new OrganizationItemSelectorViewDescriptor(
				organizationItemSelectorCriterion,
				organizationItemSelectorViewDisplayContext));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new UUIDItemSelectorReturnType());

	@Reference
	private ItemSelectorViewDescriptorRenderer
		<OrganizationItemSelectorCriterion> _itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private Portal _portal;

	@Reference
	private UsersAdmin _usersAdmin;

}
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

package com.liferay.wiki.layout.prototype.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.LayoutPrototypeLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.DefaultLayoutPrototypesUtil;
import com.liferay.wiki.constants.WikiPortletKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 * @author Juergen Kappler
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AddLayoutPrototypePortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		long defaultUserId = _userLocalService.getDefaultUserId(
			company.getCompanyId());

		List<LayoutPrototype> layoutPrototypes =
			_layoutPrototypeLocalService.search(
				company.getCompanyId(), null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		addWikiPage(company.getCompanyId(), defaultUserId, layoutPrototypes);
	}

	protected void addWikiPage(
			long companyId, long defaultUserId,
			List<LayoutPrototype> layoutPrototypes)
		throws Exception {

		Map<Locale, String> nameMap = ResourceBundleUtil.getLocalizationMap(
			"content.Language", getClass(), "layout-prototype-wiki-title");
		Map<Locale, String> descriptionMap =
			ResourceBundleUtil.getLocalizationMap(
				"content.Language", getClass(),
				"layout-prototype-wiki-description");

		Layout layout = DefaultLayoutPrototypesUtil.addLayoutPrototype(
			companyId, defaultUserId, nameMap, descriptionMap, "2_columns_iii",
			layoutPrototypes);

		if (layout == null) {
			return;
		}

		DefaultLayoutPrototypesUtil.addPortletId(
			layout, WikiPortletKeys.WIKI, "column-1");
	}

	@Reference(unbind = "-")
	protected void setLayoutPrototypeLocalService(
		LayoutPrototypeLocalService layoutPrototypeLocalService) {

		_layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference(
		target = "(javax.portlet.name=" + WikiPortletKeys.WIKI + ")",
		unbind = "-"
	)
	protected void setWikiPortlet(Portlet portlet) {
	}

	private LayoutPrototypeLocalService _layoutPrototypeLocalService;
	private UserLocalService _userLocalService;

}
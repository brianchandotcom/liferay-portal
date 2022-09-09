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

package com.liferay.layout.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorView;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "view=private",
	service = {ItemSelectorView.class, PrivateLayoutsItemSelectorView.class}
)
public class PrivateLayoutsItemSelectorView
	extends BaseLayoutsItemSelectorView {

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public String getTitle(Locale locale) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			Group group = _groupLocalService.fetchGroup(
				serviceContext.getScopeGroupId());

			if (!group.isPrivateLayoutsEnabled() &&
				group.isLayoutSetPrototype()) {

				return ResourceBundleUtil.getString(
					_portal.getResourceBundle(locale), "pages");
			}
		}

		return ResourceBundleUtil.getString(
			_portal.getResourceBundle(locale), "private-pages");
	}

	@Override
	public boolean isPrivateLayout() {
		return true;
	}

	@Override
	public boolean isVisible(
		LayoutItemSelectorCriterion layoutItemSelectorCriterion,
		ThemeDisplay themeDisplay) {

		Group group = themeDisplay.getScopeGroup();

		if (!group.isLayoutSetPrototype() && !group.isPrivateLayoutsEnabled()) {
			return false;
		}

		return true;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.item.selector.web)"
	)
	private ServletContext _servletContext;

}
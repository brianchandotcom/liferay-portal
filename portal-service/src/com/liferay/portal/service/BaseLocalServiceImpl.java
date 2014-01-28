/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.persistence.LayoutPersistence;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseLocalServiceImpl implements BaseLocalService {

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	protected String getLayoutURL(
		Layout layout, ServiceContext serviceContext) {

		HttpServletRequest request = serviceContext.getRequest();

		if (request == null) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			return PortalUtil.getLayoutURL(layout, themeDisplay);
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	protected String getLayoutURL(
			long groupId, String portletId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		long plid = serviceContext.getPlid();

		long controlPanelPlid = PortalUtil.getControlPanelPlid(
			serviceContext.getCompanyId());

		if (plid == controlPanelPlid) {
			plid = PortalUtil.getPlidFromPortletId(groupId, portletId);
		}

		String layoutURL = StringPool.BLANK;

		if (plid != LayoutConstants.DEFAULT_PLID) {
			Layout layout = layoutPersistence.findByPrimaryKey(plid);

			layoutURL = getLayoutURL(layout, serviceContext);
		}

		return layoutURL;
	}

	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;

}
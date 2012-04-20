/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetpublisher.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portlet.asset.model.AssetEntry;

/**
 * @author Juan Fernández
 */
public class TemplateHelperUtil {

	public static String getAssetViewURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			AssetEntry assetEntry)
		throws SystemException {

		return getTemplateHelper().getAssetViewURL(
			liferayPortletRequest, liferayPortletResponse, assetEntry);
	}

	public static TemplateHelper getTemplateHelper() {
		return _templateHelper;
	}

	public void setTemplateHelper(TemplateHelper templateHelper) {
		_templateHelper = templateHelper;
	}

	private static TemplateHelper _templateHelper;

}
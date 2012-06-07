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

package com.liferay.portlet.assetpublisher.displaystyles;

import com.liferay.portal.kernel.displaystyles.DisplayStyleHandler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.asset.model.AssetEntry;

import java.util.Locale;

/**
 * @author Juan Fernández
 */
public class AssetPublisherDisplayStyleHandler implements DisplayStyleHandler {

	public String getClassName() {
		return AssetEntry.class.getName();
	}

	public String getDefaultTemplateLocation() {
		return PropsUtil.get(
			PropsKeys.ASSET_PUBLISHER_DISPLAY_STYLES_TEMPLATE_CONTENT);
	}

	public String getName(Locale locale) {
		StringBundler sb = new StringBundler();

		sb.append(
			PortalUtil.getPortletTitle(PortletKeys.ASSET_PUBLISHER, locale));
		sb.append(StringPool.SPACE);
		sb.append(LanguageUtil.get(locale, "template"));

		return sb.toString();
	}

	public String getResourceName() {
		return "com.liferay.portlet.assetpublisher";
	}

}
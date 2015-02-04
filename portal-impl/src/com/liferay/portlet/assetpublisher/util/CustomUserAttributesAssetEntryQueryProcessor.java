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

package com.liferay.portlet.assetpublisher.util;

import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.asset.util.AssetEntryQueryProcessor;

import javax.portlet.PortletPreferences;

/**
 * @author Jorge Ferrer
 */
@OSGiBeanProperties
public class CustomUserAttributesAssetEntryQueryProcessor
	implements AssetEntryQueryProcessor {

	@Override
	public void processAssetEntryQuery(
			User user, PortletPreferences preferences,
			AssetEntryQuery assetEntryQuery)
		throws Exception {

		String customUserAttributes = GetterUtil.getString(
			preferences.getValue("customUserAttributes", StringPool.BLANK));

		AssetPublisherUtil.addUserAttributes(
			user, StringUtil.split(customUserAttributes), assetEntryQuery);
	}

}
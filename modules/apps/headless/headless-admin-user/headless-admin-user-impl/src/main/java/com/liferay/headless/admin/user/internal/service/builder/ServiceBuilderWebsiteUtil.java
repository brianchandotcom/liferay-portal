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

package com.liferay.headless.admin.user.internal.service.builder;

import com.liferay.headless.admin.user.dto.v1_0.WebUrl;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.WebsiteLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Drew Brokke
 */
public class ServiceBuilderWebsiteUtil {

	public static Website toServiceBuilderWebsite(WebUrl webUrl, String type) {
		String url = webUrl.getUrl();

		if (Validator.isNull(url)) {
			return null;
		}

		Website website = WebsiteLocalServiceUtil.createWebsite(
			GetterUtil.getLong(webUrl.getId()));

		website.setUrl(url);
		website.setTypeId(
			ServiceBuilderListTypeUtil.toServiceBuilderListTypeId(
				"public", webUrl.getUrlType(), type));
		website.setPrimary(GetterUtil.getBoolean(webUrl.getPrimary()));

		return website;
	}

}
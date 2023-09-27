/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.util;

import com.liferay.headless.admin.user.dto.v1_0.WebUrl;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Website;

/**
 * @author Javier Gamarra
 */
public class WebUrlUtil {

	public static WebUrl toWebUrl(Website website) throws Exception {
		ListType listType = website.getListType();

		return new WebUrl() {
			{
				id = website.getWebsiteId();
				url = website.getUrl();
				urlType = listType.getName();
			}
		};
	}

}
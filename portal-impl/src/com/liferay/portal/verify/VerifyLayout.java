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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Kenneth Chang
 */
public class VerifyLayout extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyFriendlyURL();
		verifyUuid();
	}

	protected void verifyFriendlyURL() throws Exception {
		List<Layout> layouts =
			LayoutLocalServiceUtil.getNullFriendlyURLLayouts();

		for (Layout layout : layouts) {
			String friendlyURL = StringPool.SLASH + layout.getLayoutId();

			LayoutLocalServiceUtil.updateFriendlyURL(
				layout.getPlid(), friendlyURL);
		}
	}

	protected void verifyUuid() throws Exception {
		verifyUuid("AssetEntry");
		verifyUuid("JournalArticle");

		StringBundler sb = new StringBundler(4);

		sb.append("update Layout set uuid_ = sourcePrototypeLayoutUuid where ");
		sb.append("sourcePrototypeLayoutUuid is not null and ");
		sb.append("sourcePrototypeLayoutUuid != '' and ");
		sb.append("uuid_ != sourcePrototypeLayoutUuid");

		runSQL(sb.toString());
	}

	protected void verifyUuid(String tableName) throws Exception {
		StringBundler sb = new StringBundler(9);

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set layoutUuid = (select sourcePrototypeLayoutUuid from ");
		sb.append("Layout where ");
		sb.append(tableName);
		sb.append(".layoutUuid = Layout.uuid_ and ");
		sb.append("Layout.sourcePrototypeLayoutUuid is not null and ");
		sb.append("Layout.sourcePrototypeLayoutUuid != '' and ");
		sb.append("Layout.uuid_ != Layout.sourcePrototypeLayoutUuid)");

		runSQL(sb.toString());
	}

}
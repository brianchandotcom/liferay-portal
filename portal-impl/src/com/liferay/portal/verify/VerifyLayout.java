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
 */
public class VerifyLayout extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		List<Layout> layouts =
			LayoutLocalServiceUtil.getNullFriendlyURLLayouts();

		for (Layout layout : layouts) {
			String friendlyURL = StringPool.SLASH + layout.getLayoutId();

			LayoutLocalServiceUtil.updateFriendlyURL(
				layout.getPlid(), friendlyURL);
		}

		verifyLayoutUuid();
	}

	protected void verifyLayoutUuid() throws Exception {
			StringBundler sb = new StringBundler(6);

			sb.append("update assetentry, layout set layoutUuid = ");
			sb.append("sourcePrototypeLayoutUuid where layoutUuid in (select ");
			sb.append("uuid_ from layout where sourcePrototypeLayoutUuid is ");
			sb.append("not null and sourcePrototypeLayoutUuid not like '' ");
			sb.append("and uuid_ not like sourcePrototypeLayoutUuid) and ");
			sb.append("layout.uuid_ = layoutUuid");

			runSQL(sb.toString());

			sb = new StringBundler(6);

			sb.append("update journalarticle, layout set layoutUuid = ");
			sb.append("sourcePrototypeLayoutUuid where layoutUuid in (select ");
			sb.append("uuid_ from layout where sourcePrototypeLayoutUuid is ");
			sb.append("not null and sourcePrototypeLayoutUuid not like '' ");
			sb.append("and uuid_ not like sourcePrototypeLayoutUuid) and ");
			sb.append("layout.uuid_ = layoutUuid");

			runSQL(sb.toString());

			sb = new StringBundler(4);

			sb.append("update layout set uuid_ = sourcePrototypeLayoutUuid ");
			sb.append("where sourcePrototypeLayoutUuid is not null and ");
			sb.append("sourcePrototypeLayoutUuid not like '' and uuid_ not ");
			sb.append("like sourcePrototypeLayoutUuid");

			runSQL(sb.toString());
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.internal.resource.v1_0.util;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.headless.admin.fragment.dto.v1_0.FragmentSet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Rubén Pulido
 */
public class FragmentSetUtil {

	public static FragmentCollection addFragmentCollection(
			FragmentSet fragmentSet, ServiceContext serviceContext)
		throws Exception {

		return FragmentCollectionServiceUtil.addFragmentCollection(
			fragmentSet.getExternalReferenceCode(),
			serviceContext.getScopeGroupId(), fragmentSet.getKey(),
			fragmentSet.getName(), fragmentSet.getDescription(),
			GetterUtil.getBoolean(fragmentSet.getMarketplace()),
			serviceContext);
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.internal.resource.v1_0.util;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.headless.admin.fragment.dto.v1_0.FragmentSet;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class FragmentSetUtil {

	public static FragmentCollection addFragmentCollection(
			FragmentSet fragmentSet, long groupId,
			HttpServletRequest httpServletRequest)
		throws Exception {

		return FragmentCollectionServiceUtil.addFragmentCollection(
			fragmentSet.getExternalReferenceCode(), groupId,
			fragmentSet.getKey(), fragmentSet.getName(),
			fragmentSet.getDescription(),
			GetterUtil.getBoolean(fragmentSet.getMarketplace()),
			_getServiceContext(fragmentSet, groupId, httpServletRequest));
	}

	private static ServiceContext _getServiceContext(
		FragmentSet fragmentSet, long groupId,
		HttpServletRequest httpServletRequest) {

		ServiceContext serviceContext = ServiceContextBuilder.create(
			groupId, httpServletRequest, null
		).build();

		serviceContext.setCreateDate(fragmentSet.getDateCreated());
		serviceContext.setModifiedDate(fragmentSet.getDateModified());

		return serviceContext;
	}

}
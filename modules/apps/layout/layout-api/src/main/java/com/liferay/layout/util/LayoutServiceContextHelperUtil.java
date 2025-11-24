/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutServiceContextHelperUtil {

	public static AutoCloseable getServiceContextAutoCloseable(Company company)
		throws PortalException {

		LayoutServiceContextHelper layoutServiceContextHelper =
			_layoutServiceContextHelperSnapshot.get();

		return layoutServiceContextHelper.getServiceContextAutoCloseable(
			company);
	}

	public static AutoCloseable getServiceContextAutoCloseable(
			Company company, User user)
		throws PortalException {

		LayoutServiceContextHelper layoutServiceContextHelper =
			_layoutServiceContextHelperSnapshot.get();

		return layoutServiceContextHelper.getServiceContextAutoCloseable(
			company, user);
	}

	public static AutoCloseable getServiceContextAutoCloseable(Layout layout)
		throws PortalException {

		LayoutServiceContextHelper layoutServiceContextHelper =
			_layoutServiceContextHelperSnapshot.get();

		return layoutServiceContextHelper.getServiceContextAutoCloseable(
			layout);
	}

	public static AutoCloseable getServiceContextAutoCloseable(
			Layout layout, User user)
		throws PortalException {

		LayoutServiceContextHelper layoutServiceContextHelper =
			_layoutServiceContextHelperSnapshot.get();

		return layoutServiceContextHelper.getServiceContextAutoCloseable(
			layout, user);
	}

	private static final Snapshot<LayoutServiceContextHelper>
		_layoutServiceContextHelperSnapshot = new Snapshot<>(
			LayoutServiceContextHelperUtil.class,
			LayoutServiceContextHelper.class);

}
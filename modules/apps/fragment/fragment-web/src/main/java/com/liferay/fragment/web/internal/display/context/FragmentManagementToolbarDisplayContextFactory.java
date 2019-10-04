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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.web.internal.constants.FragmentTypeConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class FragmentManagementToolbarDisplayContextFactory {

	public static FragmentManagementToolbarDisplayContextFactory getInstance() {
		return _fragmentManagementToolbarDisplayContextFactory;
	}

	public FragmentManagementToolbarDisplayContext
		getFragmentManagementToolbarDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest httpServletRequest,
			FragmentDisplayContext fragmentDisplayContext) {

		String type = fragmentDisplayContext.getFragmentType();

		if (Objects.equals(type, FragmentTypeConstants.BASIC_FRAGMENT_TYPE)) {
			return new BasicFragmentManagementToolbarDisplayContext(
				liferayPortletRequest, liferayPortletResponse,
				httpServletRequest, fragmentDisplayContext);
		}

		if (Objects.equals(
				type, FragmentTypeConstants.INHERITED_FRAGMENT_TYPE)) {

			return new InheritedFragmentManagementToolbarDisplayContext(
				liferayPortletRequest, liferayPortletResponse,
				httpServletRequest, fragmentDisplayContext);
		}

		return null;
	}

	private FragmentManagementToolbarDisplayContextFactory() {
	}

	private static final FragmentManagementToolbarDisplayContextFactory
		_fragmentManagementToolbarDisplayContextFactory =
			new FragmentManagementToolbarDisplayContextFactory();

}
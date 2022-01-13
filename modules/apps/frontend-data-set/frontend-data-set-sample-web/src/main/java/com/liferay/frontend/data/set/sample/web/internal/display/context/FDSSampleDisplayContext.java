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

package com.liferay.frontend.data.set.sample.web.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.sample.web.internal.display.context.helper.FDSRequestHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;

import java.util.Arrays;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Javier Gamarra
 * @author Javier de Arcos
 */
public class FDSSampleDisplayContext {

	public FDSSampleDisplayContext(HttpServletRequest httpServletRequest) {
		_fdsRequestHelper = new FDSRequestHelper(httpServletRequest);
	}

	public String getAPIURL() {
		return "/o/c/fdssamples";
	}

	public CreationMenu getCreationMenu() throws Exception {
		return new CreationMenu();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		return Arrays.asList(
			new FDSActionDropdownItem(
				"#", "view", "sampleMessage",
				LanguageUtil.get(_fdsRequestHelper.getRequest(), "view"), null,
				null, null));
	}

	public PortletURL getPortletURL() throws PortletException {
		return PortletURLUtil.clone(
			PortletURLUtil.getCurrent(
				_fdsRequestHelper.getLiferayPortletRequest(),
				_fdsRequestHelper.getLiferayPortletResponse()),
			_fdsRequestHelper.getLiferayPortletResponse());
	}

	private final FDSRequestHelper _fdsRequestHelper;

}
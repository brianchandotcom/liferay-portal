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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.portlet.BasePortletLayoutFinder;
import com.liferay.portal.kernel.portlet.PortletLayoutFinder;
import com.liferay.portal.kernel.struts.StrutsAction;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public abstract class FindStrutsAction implements StrutsAction {

	public FindStrutsAction() {
		_findActionHelper = new BaseFindActionHelper() {

			@Override
			public long getGroupId(long primaryKey) throws Exception {
				return FindStrutsAction.this.getGroupId(primaryKey);
			}

			@Override
			public String getPrimaryKeyParameterName() {
				return FindStrutsAction.this.getPrimaryKeyParameterName();
			}

			@Override
			public PortletURL processPortletURL(
					HttpServletRequest request, PortletURL portletURL)
				throws Exception {

				return FindStrutsAction.this.processPortletURL(
					request, portletURL);
			}

			@Override
			public void setPrimaryKeyParameter(
					PortletURL portletURL, long primaryKey)
				throws Exception {

				FindStrutsAction.this.setPrimaryKeyParameter(
					portletURL, primaryKey);
			}

			@Override
			protected void addRequiredParameters(
				HttpServletRequest request, String portletId,
				PortletURL portletURL) {

				FindStrutsAction.this.addRequiredParameters(
					request, portletId, portletURL);
			}

			@Override
			protected PortletLayoutFinder getPortletLayoutFinder() {
				return FindStrutsAction.this._portletLayoutFinder;
			}

		};
	}

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		_findActionHelper.execute(request, response);

		return null;
	}

	protected abstract void addRequiredParameters(
		HttpServletRequest request, String portletId, PortletURL portletURL);

	protected abstract long getGroupId(long primaryKey) throws Exception;

	protected abstract String getPrimaryKeyParameterName();

	protected abstract String[] initPortletIds();

	protected PortletURL processPortletURL(
			HttpServletRequest request, PortletURL portletURL)
		throws Exception {

		return portletURL;
	}

	protected void setPrimaryKeyParameter(
			PortletURL portletURL, long primaryKey)
		throws Exception {

		portletURL.setParameter(
			getPrimaryKeyParameterName(), String.valueOf(primaryKey));
	}

	private final FindActionHelper _findActionHelper;

	private final PortletLayoutFinder _portletLayoutFinder =
		new BasePortletLayoutFinder() {

			@Override
			protected String[] getPortletIds() {
				return FindStrutsAction.this.initPortletIds();
			}

		};

}
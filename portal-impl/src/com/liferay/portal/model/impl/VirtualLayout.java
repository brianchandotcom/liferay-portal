/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutWrapper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Raymond Augé
 */
public class VirtualLayout extends LayoutWrapper {

	public static final String CANONICAL_URL_SEPARATOR = "/~";

	public VirtualLayout(Layout layout, Group hostGroup) {
		super(layout);

		_hostGroup = hostGroup;
	}

	@Override
	public java.lang.Object clone() {
		return new VirtualLayout((Layout)super.clone(), _hostGroup);
	}

	@Override
	public String getFriendlyURL() {
		StringBundler sb = new StringBundler();

		sb.append(CANONICAL_URL_SEPARATOR);

		try {
			sb.append(super.getGroup().getFriendlyURL());
		}
		catch (Exception e) {
		}

		sb.append(super.getFriendlyURL());

		return sb.toString();
	}

	@Override
	public Group getGroup() throws PortalException, SystemException {
		return getHostGroup();
	}

	@Override
	public long getGroupId() {
		return getHostGroupId();
	}

	public Group getHostGroup() throws PortalException, SystemException {
		return _hostGroup;
	}

	public long getHostGroupId() {
		return _hostGroup.getGroupId();
	}

	@Override
	public LayoutSet getLayoutSet() throws PortalException, SystemException {
		if (isPrivateLayout()) {
			return _hostGroup.getPrivateLayoutSet();
		}

		return _hostGroup.getPublicLayoutSet();
	}

	@Override
	public String getRegularURL(HttpServletRequest request)
		throws PortalException, SystemException {

		String layoutURL = super.getRegularURL(request);

		return injectHostGroupURL(layoutURL);
	}

	@Override
	public String getResetLayoutURL(HttpServletRequest request)
		throws PortalException, SystemException {

		String layoutURL = super.getResetLayoutURL(request);

		return injectHostGroupURL(layoutURL);
	}

	@Override
	public String getResetMaxStateURL(HttpServletRequest request)
		throws PortalException, SystemException {

		String layoutURL = super.getResetMaxStateURL(request);

		return injectHostGroupURL(layoutURL);
	}

	public long getSourceGroupId() {
		return super.getGroupId();
	}

	protected String injectHostGroupURL(String layoutURL) {
		try {
			Group group = super.getGroup();

			int pos = layoutURL.indexOf(group.getFriendlyURL());

			StringBundler sb = new StringBundler(3);

			sb.append(layoutURL.substring(0, pos));
			sb.append(_hostGroup.getFriendlyURL());
			sb.append(getFriendlyURL());

			return sb.toString();
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private Group _hostGroup;

}
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutWrapper;
import com.liferay.portal.model.VirtualLayoutConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Raymond Augé
 */
public class VirtualLayout extends LayoutWrapper {

	public VirtualLayout(Layout sourceLayout, Group targetGroup) {
		super(sourceLayout);

		_sourceLayout = sourceLayout;
		_targetGroup = targetGroup;
	}

	@Override
	public Object clone() {
		return new VirtualLayout((Layout)_sourceLayout.clone(), _targetGroup);
	}

	@Override
	public String getFriendlyURL() {
		return getFriendlyURL(null);
	}

	@Override
	public String getFriendlyURL(Locale locale) {
		StringBundler sb = new StringBundler(4);

		sb.append(VirtualLayoutConstants.CANONICAL_URL_SEPARATOR);

		try {
			Group group = _sourceLayout.getGroup();

			sb.append(group.getFriendlyURL());
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (locale == null) {
			sb.append(_sourceLayout.getFriendlyURL());
		}
		else {
			sb.append(_sourceLayout.getFriendlyURL(locale));
		}

		return sb.toString();
	}

	@Override
	public Group getGroup() {
		return getHostGroup();
	}

	@Override
	public long getGroupId() {
		return getVirtualGroupId();
	}

	public Group getHostGroup() {
		return _targetGroup;
	}

	@Override
	public LayoutSet getLayoutSet() {
		if (isPrivateLayout()) {
			return _targetGroup.getPrivateLayoutSet();
		}

		return _targetGroup.getPublicLayoutSet();
	}

	@Override
	public String getRegularURL(HttpServletRequest request)
		throws PortalException, SystemException {

		String layoutURL = _sourceLayout.getRegularURL(request);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return injectVirtualGroupURL(layoutURL, themeDisplay.getLocale());
	}

	@Override
	public String getResetLayoutURL(HttpServletRequest request)
		throws PortalException, SystemException {

		String layoutURL = _sourceLayout.getResetLayoutURL(request);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return injectVirtualGroupURL(layoutURL, themeDisplay.getLocale());
	}

	@Override
	public String getResetMaxStateURL(HttpServletRequest request)
		throws PortalException, SystemException {

		String layoutURL = _sourceLayout.getResetMaxStateURL(request);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return injectVirtualGroupURL(layoutURL, themeDisplay.getLocale());
	}

	public long getSourceGroupId() {
		return _sourceLayout.getGroupId();
	}

	public Layout getSourceLayout() {
		return _sourceLayout;
	}

	public long getVirtualGroupId() {
		return _targetGroup.getGroupId();
	}

	protected String injectVirtualGroupURL(String layoutURL, Locale locale) {
		if (_sourceLayout.isTypeURL()) {
			return layoutURL;
		}

		try {
			Group group = _sourceLayout.getGroup();

			StringBundler sb = new StringBundler(4);

			int pos = layoutURL.indexOf(group.getFriendlyURL());

			sb.append(layoutURL.substring(0, pos));
			sb.append(_targetGroup.getFriendlyURL());
			sb.append(getFriendlyURL(locale));

			pos = layoutURL.indexOf(StringPool.QUESTION);

			if (pos > 0) {
				sb.append(layoutURL.substring(pos));
			}

			return sb.toString();
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VirtualLayout.class);

	private Layout _sourceLayout;
	private Group _targetGroup;

}
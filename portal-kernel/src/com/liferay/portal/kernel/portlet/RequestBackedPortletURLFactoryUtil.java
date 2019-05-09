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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.impl.VirtualLayout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 * @author Roberto Díaz
 */
public class RequestBackedPortletURLFactoryUtil {

	public static RequestBackedPortletURLFactory create(
		HttpServletRequest httpServletRequest) {

		return new HttpServletRequestRequestBackedPortletURLFactory(
			httpServletRequest);
	}

	public static RequestBackedPortletURLFactory create(
		PortletRequest portletRequest) {

		PortletResponse portletResponse =
			(PortletResponse)portletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (portletResponse == null) {
			return create(PortalUtil.getHttpServletRequest(portletRequest));
		}

		return new LiferayPortletResponseRequestBackedPortletURLFactory(
			PortalUtil.getLiferayPortletRequest(portletRequest),
			PortalUtil.getLiferayPortletResponse(portletResponse));
	}

	private static Layout _getControlPanelLayout(
		Layout controlPanelLayout, Group group) {

		if (controlPanelLayout == null) {
			return null;
		}

		if (group.isControlPanel()) {
			return controlPanelLayout;
		}

		return new VirtualLayout(controlPanelLayout, group);
	}

	private static PortletURL _populateControlPanelPortletURL(
		LiferayPortletURL liferayPortletURL, long refererGroupId,
		long refererPlid) {

		if (refererGroupId > 0) {
			liferayPortletURL.setRefererGroupId(refererGroupId);
		}

		if (refererPlid > 0) {
			liferayPortletURL.setRefererPlid(refererPlid);
		}

		try {
			liferayPortletURL.setWindowState(WindowState.MAXIMIZED);
		}
		catch (WindowStateException wse) {
		}

		return liferayPortletURL;
	}

	private static class HttpServletRequestRequestBackedPortletURLFactory
		implements RequestBackedPortletURLFactory {

		@Override
		public PortletURL createActionURL(String portletId) {
			String actionPhase = PortletRequest.ACTION_PHASE;

			return createPortletURL(portletId, actionPhase);
		}

		@Override
		public PortletURL createControlPanelActionURL(
			String portletId, Group group, long refererGroupId,
			long refererPlid) {

			return createControlPanelPortletURL(
				portletId, group, refererGroupId, refererPlid,
				PortletRequest.ACTION_PHASE);
		}

		@Override
		public PortletURL createControlPanelPortletURL(
			String portletId, Group group, long refererGroupId,
			long refererPlid, String lifecycle) {

			Layout controlPanelLayout = null;

			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (themeDisplay != null) {
				controlPanelLayout = themeDisplay.getControlPanelLayout();

				if (group == null) {
					group = themeDisplay.getScopeGroup();
				}
			}

			LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
				_httpServletRequest, portletId,
				_getControlPanelLayout(controlPanelLayout, group), lifecycle);

			return _populateControlPanelPortletURL(
				liferayPortletURL, refererGroupId, refererPlid);
		}

		@Override
		public PortletURL createControlPanelRenderURL(
			String portletId, Group group, long refererGroupId,
			long refererPlid) {

			return createControlPanelPortletURL(
				portletId, group, refererGroupId, refererPlid,
				PortletRequest.RENDER_PHASE);
		}

		@Override
		public PortletURL createControlPanelResourceURL(
			String portletId, Group group, long refererGroupId,
			long refererPlid) {

			return createControlPanelPortletURL(
				portletId, group, refererGroupId, refererPlid,
				PortletRequest.RESOURCE_PHASE);
		}

		@Override
		public PortletURL createPortletURL(String portletId, String lifecycle) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return PortletURLFactoryUtil.create(
				_httpServletRequest, portletId, themeDisplay.getPlid(),
				lifecycle);
		}

		@Override
		public PortletURL createRenderURL(String portletId) {
			return createPortletURL(portletId, PortletRequest.RENDER_PHASE);
		}

		@Override
		public PortletURL createResourceURL(String portletId) {
			return createPortletURL(portletId, PortletRequest.RESOURCE_PHASE);
		}

		private HttpServletRequestRequestBackedPortletURLFactory(
			HttpServletRequest httpServletRequest) {

			_httpServletRequest = httpServletRequest;
		}

		private final HttpServletRequest _httpServletRequest;

	}

	private static class LiferayPortletResponseRequestBackedPortletURLFactory
		implements RequestBackedPortletURLFactory {

		@Override
		public PortletURL createActionURL(String portletId) {
			return _liferayPortletResponse.createActionURL(portletId);
		}

		@Override
		public PortletURL createControlPanelActionURL(
			String portletId, Group group, long refererGroupId,
			long refererPlid) {

			return createControlPanelPortletURL(
				portletId, group, refererGroupId, refererPlid,
				PortletRequest.ACTION_PHASE);
		}

		@Override
		public PortletURL createControlPanelPortletURL(
			String portletId, Group group, long refererGroupId,
			long refererPlid, String lifecycle) {

			Layout controlPanelLayout = null;

			ThemeDisplay themeDisplay =
				(ThemeDisplay)_liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (themeDisplay != null) {
				controlPanelLayout = themeDisplay.getControlPanelLayout();

				if (group == null) {
					group = themeDisplay.getScopeGroup();
				}
			}

			LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
				_liferayPortletRequest, portletId,
				_getControlPanelLayout(controlPanelLayout, group), lifecycle);

			return _populateControlPanelPortletURL(
				liferayPortletURL, refererGroupId, refererPlid);
		}

		@Override
		public PortletURL createControlPanelRenderURL(
			String portletId, Group group, long refererGroupId,
			long refererPlid) {

			return createControlPanelPortletURL(
				portletId, group, refererGroupId, refererPlid,
				PortletRequest.RENDER_PHASE);
		}

		@Override
		public PortletURL createControlPanelResourceURL(
			String portletId, Group group, long refererGroupId,
			long refererPlid) {

			return createControlPanelPortletURL(
				portletId, group, refererGroupId, refererPlid,
				PortletRequest.RESOURCE_PHASE);
		}

		@Override
		public PortletURL createPortletURL(String portletId, String lifecycle) {
			return _liferayPortletResponse.createLiferayPortletURL(
				portletId, lifecycle);
		}

		@Override
		public PortletURL createRenderURL(String portletId) {
			return _liferayPortletResponse.createRenderURL(portletId);
		}

		@Override
		public PortletURL createResourceURL(String portletId) {
			return _liferayPortletResponse.createResourceURL(portletId);
		}

		private LiferayPortletResponseRequestBackedPortletURLFactory(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) {

			_liferayPortletRequest = liferayPortletRequest;
			_liferayPortletResponse = liferayPortletResponse;
		}

		private final LiferayPortletRequest _liferayPortletRequest;
		private final LiferayPortletResponse _liferayPortletResponse;

	}

}
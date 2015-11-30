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

package com.liferay.portal.kernel.portlet.bridges.mvc;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortlet;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class MVCPortlet extends LiferayPortlet {

	@Override
	public void destroy() {
		super.destroy();

		_mvcActionCommandCache.close();
		_mvcRenderCommandCache.close();
		_mvcResourceCommandCache.close();
	}

	@Override
	public void doAbout(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(aboutTemplate, renderRequest, renderResponse);
	}

	@Override
	public void doConfig(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(configTemplate, renderRequest, renderResponse);
	}

	@Override
	public void doEdit(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		if (portletPreferences == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			include(editTemplate, renderRequest, renderResponse);
		}
	}

	@Override
	public void doEditDefaults(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		if (portletPreferences == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			include(editDefaultsTemplate, renderRequest, renderResponse);
		}
	}

	@Override
	public void doEditGuest(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		if (portletPreferences == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			include(editGuestTemplate, renderRequest, renderResponse);
		}
	}

	@Override
	public void doHelp(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(helpTemplate, renderRequest, renderResponse);
	}

	@Override
	public void doPreview(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(previewTemplate, renderRequest, renderResponse);
	}

	@Override
	public void doPrint(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(printTemplate, renderRequest, renderResponse);
	}

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(viewTemplate, renderRequest, renderResponse);
	}

	@Override
	public void init() throws PortletException {
		super.init();

		templatePath = _getInitParameter("template-path");

		if (Validator.isNull(templatePath)) {
			templatePath = StringPool.SLASH;
		}
		else if (templatePath.contains(StringPool.BACK_SLASH) ||
				 templatePath.contains(StringPool.DOUBLE_SLASH) ||
				 templatePath.contains(StringPool.PERIOD) ||
				 templatePath.contains(StringPool.SPACE)) {

			throw new PortletException(
				"template-path " + templatePath + " has invalid characters");
		}
		else if (!templatePath.startsWith(StringPool.SLASH) ||
				 !templatePath.endsWith(StringPool.SLASH)) {

			throw new PortletException(
				"template-path " + templatePath +
					" must start and end with a /");
		}

		aboutTemplate = _getInitParameter("about-template");
		configTemplate = _getInitParameter("config-template");
		editTemplate = _getInitParameter("edit-template");
		editDefaultsTemplate = _getInitParameter("edit-defaults-template");
		editGuestTemplate = _getInitParameter("edit-guest-template");
		helpTemplate = _getInitParameter("help-template");
		previewTemplate = _getInitParameter("preview-template");
		printTemplate = _getInitParameter("print-template");
		viewTemplate = _getInitParameter("view-template");

		clearRequestParameters = GetterUtil.getBoolean(
			getInitParameter("clear-request-parameters"));
		copyRequestParameters = GetterUtil.getBoolean(
			getInitParameter("copy-request-parameters"), true);

		_mvcActionCommandCache = new MVCCommandCache(
			MVCActionCommand.EMPTY,
			getInitParameter("mvc-action-command-package-prefix"),
			getPortletName(), MVCActionCommand.class.getName(),
			"ActionCommand");
		_mvcRenderCommandCache = new MVCCommandCache(
			MVCRenderCommand.EMPTY,
			getInitParameter("mvc-render-command-package-prefix"),
			getPortletName(), MVCRenderCommand.class.getName(),
			"RenderCommand");
		_mvcResourceCommandCache = new MVCCommandCache(
			MVCResourceCommand.EMPTY,
			getInitParameter("mvc-resource-command-package-prefix"),
			getPortletName(), MVCResourceCommand.class.getName(),
			"ResourceCommand");
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void invokeTaglibDiscussion(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		PortletConfig portletConfig = getPortletConfig();

		PortalUtil.invokeTaglibDiscussion(
			portletConfig, actionRequest, actionResponse);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void invokeTaglibDiscussionPagination(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		PortletConfig portletConfig = getPortletConfig();

		PortalUtil.invokeTaglibDiscussionPagination(
			portletConfig, resourceRequest, resourceResponse);
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		super.processAction(actionRequest, actionResponse);

		if (copyRequestParameters) {
			PortalUtil.copyRequestParameters(actionRequest, actionResponse);
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		invokeHideDefaultSuccessMessage(renderRequest);

		String mvcRenderCommandName = ParamUtil.getString(
			renderRequest, "mvcRenderCommandName", "/");

		String mvcPath = ParamUtil.getString(renderRequest, "mvcPath");

		if (!mvcRenderCommandName.equals("/") || Validator.isNull(mvcPath)) {
			MVCRenderCommand mvcRenderCommand =
				(MVCRenderCommand)_mvcRenderCommandCache.getMVCCommand(
					mvcRenderCommandName);

			mvcPath = null;

			if (mvcRenderCommand != MVCRenderCommand.EMPTY) {
				mvcPath = mvcRenderCommand.render(
					renderRequest, renderResponse);
			}

			renderRequest.setAttribute(
				getMVCPathAttributeName(renderResponse.getNamespace()),
				mvcPath);
		}

		super.render(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		invokeHideDefaultSuccessMessage(resourceRequest);

		String path = getPath(resourceRequest, resourceResponse);

		if (path != null) {
			include(
				path, resourceRequest, resourceResponse,
				PortletRequest.RESOURCE_PHASE);
		}

		boolean invokeTaglibDiscussion = GetterUtil.getBoolean(
			resourceRequest.getParameter("invokeTaglibDiscussion"));

		if (invokeTaglibDiscussion) {
			invokeTaglibDiscussionPagination(resourceRequest, resourceResponse);
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	@Override
	protected boolean callActionMethod(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		try {
			checkPermissions(actionRequest);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		String[] actionNames = ParamUtil.getParameterValues(
			actionRequest, ActionRequest.ACTION_NAME);

		String actionName = StringUtil.merge(actionNames);

		if (!actionName.contains(StringPool.COMMA)) {
			MVCActionCommand mvcActionCommand =
				(MVCActionCommand)_mvcActionCommandCache.getMVCCommand(
					actionName);

			if (mvcActionCommand != MVCActionCommand.EMPTY) {
				if (mvcActionCommand instanceof FormMVCActionCommand) {
					FormMVCActionCommand formMVCActionCommand =
						(FormMVCActionCommand)mvcActionCommand;

					if (!formMVCActionCommand.validateForm(
							actionRequest, actionResponse)) {

						return false;
					}
				}

				return mvcActionCommand.processAction(
					actionRequest, actionResponse);
			}
		}
		else {
			List<MVCActionCommand> mvcActionCommands =
				(List<MVCActionCommand>)_mvcActionCommandCache.getMVCCommands(
					actionName);

			if (!mvcActionCommands.isEmpty()) {
				boolean valid = true;

				for (MVCActionCommand mvcActionCommand : mvcActionCommands) {
					if (mvcActionCommand instanceof FormMVCActionCommand) {
						FormMVCActionCommand formMVCActionCommand =
							(FormMVCActionCommand)mvcActionCommand;

						valid &= formMVCActionCommand.validateForm(
							actionRequest, actionResponse);
					}
				}

				if (!valid) {
					return false;
				}

				for (MVCActionCommand mvcActionCommand : mvcActionCommands) {
					if (!mvcActionCommand.processAction(
							actionRequest, actionResponse)) {

						return false;
					}
				}

				return true;
			}
		}

		return super.callActionMethod(actionRequest, actionResponse);
	}

	@Override
	protected boolean callResourceMethod(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			checkPermissions(resourceRequest);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		String resourceID = GetterUtil.getString(
			resourceRequest.getResourceID());

		if (!resourceID.contains(StringPool.COMMA)) {
			MVCResourceCommand mvcResourceCommand =
				(MVCResourceCommand)_mvcResourceCommandCache.getMVCCommand(
					resourceID);

			if (mvcResourceCommand != MVCResourceCommand.EMPTY) {
				return mvcResourceCommand.serveResource(
					resourceRequest, resourceResponse);
			}
		}
		else {
			List<MVCResourceCommand> mvcResourceCommands =
				(List<MVCResourceCommand>)
					_mvcResourceCommandCache.getMVCCommands(resourceID);

			if (!mvcResourceCommands.isEmpty()) {
				for (MVCResourceCommand mvcResourceCommand :
						mvcResourceCommands) {

					if (!mvcResourceCommand.serveResource(
							resourceRequest, resourceResponse)) {

						return false;
					}
				}

				return true;
			}
		}

		return super.callResourceMethod(resourceRequest, resourceResponse);
	}

	protected void checkPath(String path) throws PortletException {
		if (Validator.isNotNull(path) &&
			(!path.startsWith(templatePath) ||
			 !PortalUtil.isValidResourceId(path) ||
			 !Validator.isFilePath(path, false))) {

			throw new PortletException(
				"Path " + path + " is not accessible by this portlet");
		}
	}

	protected void checkPermissions(PortletRequest portletRequest)
		throws Exception {
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String path = getPath(renderRequest, renderResponse);

		if (path != null) {
			if (!isProcessRenderRequest(renderRequest)) {
				renderRequest.setAttribute(
					WebKeys.PORTLET_DECORATE, Boolean.FALSE);

				return;
			}

			WindowState windowState = renderRequest.getWindowState();

			if (windowState.equals(WindowState.MINIMIZED)) {
				return;
			}

			include(path, renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected String getMVCPathAttributeName(String namespace) {
		return namespace.concat(StringPool.PERIOD).concat(_MVC_PATH);
	}

	protected String getPath(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String mvcPath = portletRequest.getParameter("mvcPath");

		if (mvcPath == null) {
			mvcPath = (String)portletRequest.getAttribute(
				getMVCPathAttributeName(portletResponse.getNamespace()));
		}

		// Check deprecated parameter

		if (mvcPath == null) {
			mvcPath = portletRequest.getParameter("jspPage");
		}

		return mvcPath;
	}

	protected void hideDefaultErrorMessage(PortletRequest portletRequest) {
		SessionMessages.add(
			portletRequest,
			PortalUtil.getPortletId(portletRequest) +
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
	}

	protected void hideDefaultSuccessMessage(PortletRequest portletRequest) {
		SessionMessages.add(
			portletRequest,
			PortalUtil.getPortletId(portletRequest) +
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
	}

	protected void include(
			String path, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException, PortletException {

		include(
			path, actionRequest, actionResponse, PortletRequest.ACTION_PHASE);
	}

	protected void include(
			String path, EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, PortletException {

		include(path, eventRequest, eventResponse, PortletRequest.EVENT_PHASE);
	}

	protected void include(
			String path, PortletRequest portletRequest,
			PortletResponse portletResponse, String lifecycle)
		throws IOException, PortletException {

		PortletContext portletContext = getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
			_log.error(path + " is not a valid include");
		}
		else {
			checkPath(path);

			portletRequestDispatcher.include(portletRequest, portletResponse);
		}

		if (clearRequestParameters) {
			if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
				portletResponse.setProperty(
					"clear-request-parameters", Boolean.TRUE.toString());
			}
		}
	}

	protected void include(
			String path, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws IOException, PortletException {

		include(
			path, renderRequest, renderResponse, PortletRequest.RENDER_PHASE);
	}

	protected void include(
			String path, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws IOException, PortletException {

		include(
			path, resourceRequest, resourceResponse,
			PortletRequest.RESOURCE_PHASE);
	}

	protected void invokeHideDefaultSuccessMessage(
		PortletRequest portletRequest) {

		boolean hideDefaultSuccessMessage = ParamUtil.getBoolean(
			portletRequest, "hideDefaultSuccessMessage");

		if (hideDefaultSuccessMessage) {
			hideDefaultSuccessMessage(portletRequest);
		}
	}

	protected String aboutTemplate;
	protected boolean clearRequestParameters;
	protected String configTemplate;
	protected boolean copyRequestParameters;
	protected String editDefaultsTemplate;
	protected String editGuestTemplate;
	protected String editTemplate;
	protected String helpTemplate;
	protected String previewTemplate;
	protected String printTemplate;
	protected String templatePath;
	protected String viewTemplate;

	private String _getInitParameter(String name) {
		String value = getInitParameter(name);

		if (value != null) {
			return value;
		}

		// Check deprecated parameter

		if (name.equals("template-path")) {
			return getInitParameter("jsp-path");
		}
		else if (name.endsWith("-template")) {
			name = name.substring(0, name.length() - 9) + "-jsp";

			return getInitParameter(name);
		}

		return null;
	}

	private static final String _MVC_PATH =
		MVCPortlet.class.getName() + "#MVC_PATH";

	private static final Log _log = LogFactoryUtil.getLog(MVCPortlet.class);

	private MVCCommandCache _mvcActionCommandCache;
	private MVCCommandCache _mvcRenderCommandCache;
	private MVCCommandCache _mvcResourceCommandCache;

}
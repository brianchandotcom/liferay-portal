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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.MimeResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayPortlet extends GenericPortlet {

	@Override
	public void init() throws PortletException {
		super.init();

		addProcessActionSuccessMessage = GetterUtil.getBoolean(
			getInitParameter("add-process-action-success-action"), true);
		alwaysSendRedirect = GetterUtil.getBoolean(
			getInitParameter("always-send-redirect"));
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		try {
			if (!callActionMethod(actionRequest, actionResponse)) {
				return;
			}

			if (!SessionErrors.isEmpty(actionRequest)) {
				return;
			}

			String portletId = PortalUtil.getPortletId(actionRequest);

			if (!SessionMessages.contains(
					actionRequest,
					portletId.concat(
						SessionMessages.KEY_SUFFIX_FORCE_SEND_REDIRECT)) &&
				(isEmptySessionMessages(actionRequest) ||
				 isAlwaysSendRedirect())) {

				sendRedirect(actionRequest, actionResponse);
			}

			if (isAddSuccessMessage(actionRequest)) {
				addSuccessMessage(actionRequest, actionResponse);
			}
		}
		catch (PortletException pe) {
			Throwable cause = pe.getCause();

			if (isSessionErrorException(cause)) {
				SessionErrors.add(actionRequest, cause.getClass(), cause);
			}
			else {
				throw pe;
			}
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		if (!callResourceMethod(resourceRequest, resourceResponse)) {
			return;
		}

		if (!SessionErrors.isEmpty(resourceRequest)) {
			return;
		}

		if (!SessionMessages.isEmpty(resourceRequest)) {
			return;
		}

		super.serveResource(resourceRequest, resourceResponse);
	}

	protected void addSuccessMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		if (!addProcessActionSuccessMessage) {
			return;
		}

		String successMessage = ParamUtil.getString(
			actionRequest, "successMessage");

		SessionMessages.add(actionRequest, "requestProcessed", successMessage);
	}

	protected boolean callActionMethod(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		String actionName = ParamUtil.getString(
			actionRequest, ActionRequest.ACTION_NAME);

		if (Validator.isNull(actionName) ||
			actionName.equals("callActionMethod") ||
			actionName.equals("processAction")) {

			return false;
		}

		try {
			Method method = getActionMethod(actionName);

			method.invoke(this, actionRequest, actionResponse);

			return true;
		}
		catch (NoSuchMethodException nsme) {
			try {
				super.processAction(actionRequest, actionResponse);

				return true;
			}
			catch (Exception e) {
				throw new PortletException(e);
			}
		}
		catch (InvocationTargetException ite) {
			Throwable cause = ite.getCause();

			if (cause != null) {
				throw new PortletException(cause);
			}

			throw new PortletException(ite);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected boolean callResourceMethod(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		String actionName = ParamUtil.getString(
			resourceRequest, ActionRequest.ACTION_NAME);

		if (Validator.isNull(actionName) ||
			actionName.equals("callResourceMethod") ||
			actionName.equals("serveResource")) {

			return false;
		}

		try {
			Method method = getResourceMethod(actionName);

			method.invoke(this, resourceRequest, resourceResponse);

			return true;
		}
		catch (NoSuchMethodException nsme) {
			try {
				super.serveResource(resourceRequest, resourceResponse);

				return true;
			}
			catch (Exception e) {
				throw new PortletException(e);
			}
		}
		catch (InvocationTargetException ite) {
			Throwable cause = ite.getCause();

			if (cause != null) {
				throw new PortletException(cause);
			}

			throw new PortletException(ite);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	protected void checkPath(String path) throws PortletException {
		if (Validator.isNotNull(path)) {
			throw new PortletException(
				"Path " + path + " is not accessible by this portlet");
		}
	}

	protected void doAbout(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		throw new PortletException("doAbout method not implemented");
	}

	protected void doConfig(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		throw new PortletException("doConfig method not implemented");
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		WindowState windowState = renderRequest.getWindowState();

		if (windowState.equals(WindowState.MINIMIZED)) {
			return;
		}

		PortletMode portletMode = renderRequest.getPortletMode();

		if (portletMode.equals(PortletMode.VIEW)) {
			doView(renderRequest, renderResponse);
		}
		else if (portletMode.equals(LiferayPortletMode.ABOUT)) {
			doAbout(renderRequest, renderResponse);
		}
		else if (portletMode.equals(LiferayPortletMode.CONFIG)) {
			doConfig(renderRequest, renderResponse);
		}
		else if (portletMode.equals(PortletMode.EDIT)) {
			doEdit(renderRequest, renderResponse);
		}
		else if (portletMode.equals(LiferayPortletMode.EDIT_DEFAULTS)) {
			doEditDefaults(renderRequest, renderResponse);
		}
		else if (portletMode.equals(LiferayPortletMode.EDIT_GUEST)) {
			doEditGuest(renderRequest, renderResponse);
		}
		else if (portletMode.equals(PortletMode.HELP)) {
			doHelp(renderRequest, renderResponse);
		}
		else if (portletMode.equals(LiferayPortletMode.PREVIEW)) {
			doPreview(renderRequest, renderResponse);
		}
		else if (portletMode.equals(LiferayPortletMode.PRINT)) {
			doPrint(renderRequest, renderResponse);
		}
		else {
			throw new PortletException(portletMode.toString());
		}
	}

	protected void doEditDefaults(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		throw new PortletException("doEditDefaults method not implemented");
	}

	protected void doEditGuest(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		throw new PortletException("doEditGuest method not implemented");
	}

	protected void doPreview(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		throw new PortletException("doPreview method not implemented");
	}

	protected void doPrint(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		throw new PortletException("doPrint method not implemented");
	}

	protected Method getActionMethod(String actionName)
		throws NoSuchMethodException {

		Method method = _actionMethods.get(actionName);

		if (method != null) {
			return method;
		}

		Class<?> clazz = getClass();

		method = clazz.getMethod(
			actionName, ActionRequest.class, ActionResponse.class);

		_actionMethods.put(actionName, method);

		return method;
	}

	protected String getJSONContentType(PortletRequest portletRequest) {
		if (BrowserSnifferUtil.isIe(
				PortalUtil.getHttpServletRequest(portletRequest))) {

			return ContentTypes.TEXT_HTML;
		}

		return ContentTypes.APPLICATION_JSON;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	protected Set<String> getPaths(String path, String extension) {
		Set<String> paths = new HashSet<>();

		PortletContext portletContext = getPortletContext();

		Queue<String> queue = new ArrayDeque<>();

		queue.add(path);

		while ((path = queue.poll()) != null) {
			Set<String> childPaths = portletContext.getResourcePaths(path);

			if (childPaths != null) {
				for (String childPath : childPaths) {
					if (childPath.charAt(childPath.length() - 1) ==
							CharPool.SLASH) {

						queue.add(childPath);
					}
					else if (childPath.endsWith(extension)) {
						paths.add(childPath);
					}
				}
			}
		}

		return paths;
	}

	protected String getRedirect(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String redirect = (String)actionRequest.getAttribute(WebKeys.REDIRECT);

		if (Validator.isBlank(redirect)) {
			redirect = ParamUtil.getString(actionRequest, "redirect");

			if (!Validator.isBlank(redirect)) {
				redirect = PortalUtil.escapeRedirect(redirect);
			}
		}

		return redirect;
	}

	protected Method getResourceMethod(String actionName)
		throws NoSuchMethodException {

		Method method = _resourceMethods.get(actionName);

		if (method != null) {
			return method;
		}

		Class<?> clazz = getClass();

		method = clazz.getMethod(
			actionName, ResourceRequest.class, ResourceResponse.class);

		_resourceMethods.put(actionName, method);

		return method;
	}

	protected ServletContext getServletContext() {
		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)getPortletConfig();

		Portlet portlet = liferayPortletConfig.getPortlet();

		PortletApp portletApp = portlet.getPortletApp();

		return portletApp.getServletContext();
	}

	@Override
	protected String getTitle(RenderRequest renderRequest) {
		try {
			return PortalUtil.getPortletTitle(renderRequest);
		}
		catch (Exception e) {
			return super.getTitle(renderRequest);
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	protected void initValidPaths(String rootPath, String extension) {
	}

	protected boolean isAddSuccessMessage(ActionRequest actionRequest) {
		if (!addProcessActionSuccessMessage) {
			return false;
		}

		String portletId = PortalUtil.getPortletId(actionRequest);

		if (SessionMessages.contains(
				actionRequest,
				portletId.concat(
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE))) {

			return false;
		}

		if (SessionMessages.isEmpty(actionRequest)) {
			return true;
		}

		int sessionMessagesSize = SessionMessages.size(actionRequest);

		for (String suffix : _IGNORED_SESSION_MESSAGE_SUFFIXES) {
			if (SessionMessages.contains(
					actionRequest, portletId.concat(suffix))) {

				sessionMessagesSize--;
			}
		}

		if (sessionMessagesSize == 0) {
			return true;
		}

		return false;
	}

	protected boolean isAlwaysSendRedirect() {
		return alwaysSendRedirect;
	}

	protected boolean isEmptySessionMessages(ActionRequest actionRequest) {
		if (SessionMessages.isEmpty(actionRequest)) {
			return true;
		}

		int sessionMessagesSize = SessionMessages.size(actionRequest);

		String portletId = PortalUtil.getPortletId(actionRequest);

		for (String suffix : _IGNORED_SESSION_MESSAGE_SUFFIXES) {
			if (SessionMessages.contains(
					actionRequest, portletId.concat(suffix))) {

				sessionMessagesSize--;
			}
		}

		if (sessionMessagesSize == 0) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	protected boolean isProcessActionRequest(ActionRequest actionRequest) {
		return isProcessPortletRequest(actionRequest);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	protected boolean isProcessPortletRequest(PortletRequest portletRequest) {
		return _PROCESS_PORTLET_REQUEST;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	protected boolean isProcessRenderRequest(RenderRequest renderRequest) {
		return isProcessPortletRequest(renderRequest);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	protected boolean isProcessResourceRequest(
		ResourceRequest resourceRequest) {

		return isProcessPortletRequest(resourceRequest);
	}

	protected boolean isSessionErrorException(Throwable cause) {
		if (_log.isDebugEnabled()) {
			_log.debug(cause, cause);
		}

		if (cause instanceof PortalException) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	protected boolean isValidPath(String path) {
		return false;
	}

	protected void sendRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		String redirect = getRedirect(actionRequest, actionResponse);

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	protected String translate(PortletRequest portletRequest, String key) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = getResourceBundle(
			themeDisplay.getLocale());

		return LanguageUtil.get(resourceBundle, key);
	}

	protected String translate(
		PortletRequest portletRequest, String key, Object... arguments) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = getResourceBundle(
			themeDisplay.getLocale());

		return LanguageUtil.format(resourceBundle, key, arguments);
	}

	protected void writeJSON(
			PortletRequest portletRequest, ActionResponse actionResponse,
			Object jsonObj)
		throws IOException {

		HttpServletResponse httpServletResponse =
			PortalUtil.getHttpServletResponse(actionResponse);

		httpServletResponse.setContentType(getJSONContentType(portletRequest));

		ServletResponseUtil.write(
			httpServletResponse, _toXSSSafeJSON(jsonObj.toString()));

		httpServletResponse.flushBuffer();
	}

	protected void writeJSON(
			PortletRequest portletRequest, MimeResponse mimeResponse,
			Object jsonObj)
		throws IOException {

		mimeResponse.setContentType(getJSONContentType(portletRequest));

		PortletResponseUtil.write(
			mimeResponse, _toXSSSafeJSON(jsonObj.toString()));

		mimeResponse.flushBuffer();
	}

	protected boolean addProcessActionSuccessMessage;
	protected boolean alwaysSendRedirect;

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	protected Set<String> validPaths;

	private String _toXSSSafeJSON(String json) {
		return StringUtil.replace(json, CharPool.LESS_THAN, "\\u003c");
	}

	private static final String[] _IGNORED_SESSION_MESSAGE_SUFFIXES = {
		SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA,
		SessionMessages.KEY_SUFFIX_FORCE_SEND_REDIRECT,
		SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE,
		SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE,
		SessionMessages.KEY_SUFFIX_REFRESH_PORTLET
	};

	private static final boolean _PROCESS_PORTLET_REQUEST = true;

	private static final Log _log = LogFactoryUtil.getLog(LiferayPortlet.class);

	private final Map<String, Method> _actionMethods =
		new ConcurrentHashMap<>();
	private final Map<String, Method> _resourceMethods =
		new ConcurrentHashMap<>();

}
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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayPortletRequestDispatcher;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionResponseImpl;

import java.io.IOException;

import java.lang.reflect.Constructor;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.tiles.TilesRequestProcessor;
import org.apache.struts.util.MessageResources;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortletRequestProcessor extends TilesRequestProcessor {

	public static PortletRequestProcessor getInstance(
			ActionServlet servlet, ModuleConfig moduleConfig)
		throws ServletException {

		try {
			String className = PropsValues.STRUTS_PORTLET_REQUEST_PROCESSOR;

			Class<?> clazz = Class.forName(className);

			Constructor<?> constructor = clazz.getConstructor(
				ActionServlet.class, ModuleConfig.class);

			PortletRequestProcessor portletReqProcessor =
				(PortletRequestProcessor)constructor.newInstance(
					servlet, moduleConfig);

			return portletReqProcessor;
		}
		catch (Exception e) {
			_log.error(e);

			return new PortletRequestProcessor(servlet, moduleConfig);
		}
	}

	public PortletRequestProcessor(
			ActionServlet actionServlet, ModuleConfig moduleConfig)
		throws ServletException {

		init(actionServlet, moduleConfig);
	}

	public void process(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String path)
		throws IOException, ServletException {

		ActionResponseImpl actionResponseImpl =
			(ActionResponseImpl)actionResponse;

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		ActionMapping actionMapping = processMapping(request, response, path);

		if (actionMapping == null) {
			return;
		}

		if (!processRoles(request, response, actionMapping, true)) {
			return;
		}

		ActionForm actionForm = processActionForm(
			request, response, actionMapping);

		processPopulate(request, response, actionForm, actionMapping);

		if (!processValidateAction(
				request, response, actionForm, actionMapping)) {

			return;
		}

		PortletAction portletAction = (PortletAction)processActionCreate(
			request, response, actionMapping);

		if (portletAction == null) {
			return;
		}

		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)actionRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		try {
			if (portletAction.isCheckMethodOnProcessAction()) {
				if (!PortalUtil.isMethodPost(actionRequest)) {
					String currentURL = PortalUtil.getCurrentURL(actionRequest);

					if (_log.isWarnEnabled()) {
						_log.warn(
							"This URL can only be invoked using POST: " +
								currentURL);
					}

					throw new PrincipalException(currentURL);
				}
			}

			portletAction.processAction(
				actionMapping, actionForm, liferayPortletConfig, actionRequest,
				actionResponse);
		}
		catch (Exception e) {
			String exceptionId =
				WebKeys.PORTLET_STRUTS_EXCEPTION + StringPool.PERIOD +
					liferayPortletConfig.getPortletId();

			actionRequest.setAttribute(exceptionId, e);
		}

		String forward = (String)actionRequest.getAttribute(
			PortletAction.getForwardKey(actionRequest));

		if (forward == null) {
			return;
		}

		String queryString = StringPool.BLANK;

		int pos = forward.indexOf(CharPool.QUESTION);

		if (pos != -1) {
			queryString = forward.substring(pos + 1);
			forward = forward.substring(0, pos);
		}

		ActionForward actionForward = actionMapping.findForward(forward);

		if ((actionForward != null) && actionForward.getRedirect()) {
			String forwardPath = actionForward.getPath();

			if (forwardPath.startsWith(StringPool.SLASH)) {
				LiferayPortletURL forwardURL =
					(LiferayPortletURL)actionResponseImpl.createRenderURL();

				forwardURL.setParameter("struts_action", forwardPath);

				StrutsURLEncoder.setParameters(forwardURL, queryString);

				forwardPath = forwardURL.toString();
			}

			actionResponse.sendRedirect(forwardPath);
		}
	}

	public void process(EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, ServletException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			eventRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			eventResponse);

		process(request, response);
	}

	public void process(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, ServletException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			renderResponse);

		process(request, response);
	}

	public void process(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, ServletException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			resourceRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			resourceResponse);

		process(request, response);
	}

	@Override
	public ActionMapping processMapping(
		HttpServletRequest request, HttpServletResponse response, String path) {

		if (path == null) {
			return null;
		}

		ActionMapping actionMapping = null;

		long companyId = PortalUtil.getCompanyId(request);

		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		try {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, liferayPortletConfig.getPortletId());

			if (StrutsActionRegistryUtil.getAction(path) != null) {
				actionMapping = (ActionMapping)moduleConfig.findActionConfig(
					path);

				if (actionMapping == null) {
					actionMapping = new ActionMapping();

					actionMapping.setModuleConfig(moduleConfig);
					actionMapping.setPath(path);

					request.setAttribute(Globals.MAPPING_KEY, actionMapping);
				}
			}
			else if (moduleConfig.findActionConfig(path) != null) {
				actionMapping = super.processMapping(request, response, path);
			}
			else if (Validator.isNotNull(portlet.getParentStrutsPath())) {
				int pos = path.indexOf(StringPool.SLASH, 1);

				String parentPath =
					StringPool.SLASH + portlet.getParentStrutsPath() +
						path.substring(pos);

				if (StrutsActionRegistryUtil.getAction(parentPath) != null) {
					actionMapping =
						(ActionMapping)moduleConfig.findActionConfig(
							parentPath);

					if (actionMapping == null) {
						actionMapping = new ActionMapping();

						actionMapping.setModuleConfig(moduleConfig);
						actionMapping.setPath(parentPath);

						request.setAttribute(
							Globals.MAPPING_KEY, actionMapping);
					}
				}
				else if (moduleConfig.findActionConfig(parentPath) != null) {
					actionMapping = super.processMapping(
						request, response, parentPath);
				}
			}
		}
		catch (Exception e) {
		}

		if (actionMapping == null) {
			MessageResources messageResources = getInternal();

			String msg = messageResources.getMessage("processInvalid");

			_log.error("User ID " + request.getRemoteUser());
			_log.error("Current URL " + PortalUtil.getCurrentURL(request));
			_log.error("Referer " + request.getHeader("Referer"));
			_log.error("Remote address " + request.getRemoteAddr());

			_log.error(msg + " " + path);
		}

		return actionMapping;
	}

	@Override
	protected void doForward(
			String uri, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		doInclude(uri, request, response);
	}

	@Override
	protected void doInclude(
			String uri, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		PortletContext portletContext =
			liferayPortletConfig.getPortletContext();

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		LiferayPortletRequestDispatcher liferayPortletRequestDispatcher =
			(LiferayPortletRequestDispatcher)
				portletContext.getRequestDispatcher(
					StrutsUtil.TEXT_HTML_DIR + uri);

		try {
			if (liferayPortletRequestDispatcher == null) {
				_log.error(uri + " is not a valid include");
			}
			else {
				liferayPortletRequestDispatcher.include(
					portletRequest, portletResponse, true);
			}
		}
		catch (PortletException pe) {
			Throwable cause = pe.getCause();

			if (cause instanceof ServletException) {
				throw (ServletException)cause;
			}
			else {
				_log.error(cause, cause);
			}
		}
	}

	@Override
	protected Action processActionCreate(
			HttpServletRequest request, HttpServletResponse response,
			ActionMapping actionMapping)
		throws IOException {

		PortletActionAdapter portletActionAdapter =
			(PortletActionAdapter)StrutsActionRegistryUtil.getAction(
				actionMapping.getPath());

		if (portletActionAdapter != null) {
			ActionConfig actionConfig = moduleConfig.findActionConfig(
				actionMapping.getPath());

			if (actionConfig != null) {
				PortletAction originalPortletAction =
					(PortletAction)super.processActionCreate(
						request, response, actionMapping);

				portletActionAdapter.setOriginalPortletAction(
					originalPortletAction);
			}

			return portletActionAdapter;
		}

		return super.processActionCreate(request, response, actionMapping);
	}

	@Override
	protected ActionForm processActionForm(
		HttpServletRequest request, HttpServletResponse response,
		ActionMapping actionMapping) {

		ActionForm actionForm = super.processActionForm(
			request, response, actionMapping);

		if (actionForm instanceof InitializableActionForm) {
			InitializableActionForm initializableActionForm =
				(InitializableActionForm)actionForm;

			initializableActionForm.init(request, response, actionMapping);
		}

		return actionForm;
	}

	@Override
	protected ActionForward processActionPerform(
			HttpServletRequest request, HttpServletResponse response,
			Action action, ActionForm actionForm, ActionMapping actionMapping)
		throws IOException, ServletException {

		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		String exceptionId =
			WebKeys.PORTLET_STRUTS_EXCEPTION + StringPool.PERIOD +
				liferayPortletConfig.getPortletId();

		Exception e = (Exception)request.getAttribute(exceptionId);

		if (e != null) {
			return processException(
				request, response, e, actionForm, actionMapping);
		}
		else {
			return super.processActionPerform(
				request, response, action, actionForm, actionMapping);
		}
	}

	@Override
	protected void processForwardConfig(
			HttpServletRequest request, HttpServletResponse response,
			ForwardConfig forward)
		throws IOException, ServletException {

		if (forward == null) {
			_log.error("Forward does not exist");
		}
		else {

			// Don't render a null path. This is useful if you're sending a file
			// in an exclusive window state.

			if (forward.getPath().equals(ActionConstants.COMMON_NULL)) {
				return;
			}
		}

		super.processForwardConfig(request, response, forward);
	}

	@Override
	protected HttpServletRequest processMultipart(HttpServletRequest request) {

		// Disable Struts from automatically wrapping a multipart request

		return request;
	}

	@Override
	protected String processPath(
		HttpServletRequest request, HttpServletResponse response) {

		String path = request.getParameter("struts_action");

		if (_log.isDebugEnabled()) {
			_log.debug("Getting request parameter path " + path);
		}

		if (Validator.isNull(path)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Getting request attribute path " + path);
			}

			path = (String)request.getAttribute(WebKeys.PORTLET_STRUTS_ACTION);
		}

		if (path == null) {
			LiferayPortletConfig liferayPortletConfig =
				(LiferayPortletConfig)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_CONFIG);

			_log.error(
				liferayPortletConfig.getPortletName() +
					" does not have any paths specified");
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Processing path " + path);
			}

			request.removeAttribute(WebKeys.PORTLET_STRUTS_ACTION);
		}

		return path;
	}

	@Override
	protected boolean processRoles(
			HttpServletRequest request, HttpServletResponse response,
			ActionMapping actionMapping)
		throws IOException, ServletException {

		return processRoles(request, response, actionMapping, false);
	}

	protected boolean processRoles(
			HttpServletRequest request, HttpServletResponse response,
			ActionMapping actionMapping, boolean action)
		throws IOException, ServletException {

		long companyId = PortalUtil.getCompanyId(request);

		String path = actionMapping.getPath();

		try {
			LiferayPortletConfig liferayPortletConfig =
				(LiferayPortletConfig)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_CONFIG);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, liferayPortletConfig.getPortletId());

			if (portlet == null) {
				return false;
			}

			String strutsPath = path.substring(
				1, path.lastIndexOf(CharPool.SLASH));

			if (!strutsPath.equals(portlet.getStrutsPath()) &&
				!strutsPath.equals(portlet.getParentStrutsPath())) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"The struts path " + strutsPath + " does not belong " +
							"to portlet " + portlet.getPortletId() + ". " +
								"Check the definition in liferay-portlet.xml");
				}

				throw new PrincipalException();
			}
			else if (!portlet.isActive()) {
				ForwardConfig forwardConfig = actionMapping.findForward(
					_PATH_PORTAL_PORTLET_INACTIVE);

				if (!action) {
					processForwardConfig(request, response, forwardConfig);
				}

				return false;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}

			ForwardConfig forwardConfig = actionMapping.findForward(
				_PATH_PORTAL_PORTLET_ACCESS_DENIED);

			if (!action) {
				processForwardConfig(request, response, forwardConfig);
			}

			return false;
		}

		return true;
	}

	protected boolean processValidateAction(
		HttpServletRequest request, HttpServletResponse response,
		ActionForm actionForm, ActionMapping actionMapping) {

		if (actionForm == null) {
			return true;
		}

		if (request.getAttribute(Globals.CANCEL_KEY) != null) {
			return true;
		}

		if (!actionMapping.getValidate()) {
			return true;
		}

		ActionErrors errors = actionForm.validate(actionMapping, request);

		if ((errors == null) || errors.isEmpty()) {
			return true;
		}

		if (actionForm.getMultipartRequestHandler() != null) {
			actionForm.getMultipartRequestHandler().rollback();
		}

		String input = actionMapping.getInput();

		if (input == null) {
			_log.error("Validation failed but no input form is available");

			return false;
		}

		request.setAttribute(Globals.ERROR_KEY, errors);

		// Struts normally calls internalModuleRelativeForward which breaks if
		// called inside processAction

		request.setAttribute(PortletAction.getForwardKey(request), input);

		return false;
	}

	private static final String _PATH_PORTAL_PORTLET_ACCESS_DENIED =
		"/portal/portlet_access_denied";

	private static final String _PATH_PORTAL_PORTLET_INACTIVE =
		"/portal/portlet_inactive";

	private static Log _log = LogFactoryUtil.getLog(
		PortletRequestProcessor.class);

}
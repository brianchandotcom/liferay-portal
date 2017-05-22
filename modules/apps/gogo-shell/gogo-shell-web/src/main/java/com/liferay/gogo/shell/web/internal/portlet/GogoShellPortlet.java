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

package com.liferay.gogo.shell.web.internal.portlet;

import com.liferay.gogo.shell.web.internal.constants.GogoShellPortletKeys;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintStream;

import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;

import org.apache.felix.service.command.CommandProcessor;
import org.apache.felix.service.command.CommandSession;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Gregory Amerson
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-gogo-shell",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.render-weight=50",
		"javax.portlet.display-name=Gogo Shell",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + GogoShellPortletKeys.GOGO_SHELL,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class GogoShellPortlet extends MVCPortlet {

	@Activate
	public void activate() {
		_outputUnsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
		_errorUnsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();

		_outputPrintStream = new PrintStream(
			_outputUnsyncByteArrayOutputStream);
		_errorPrintStream = new PrintStream(_errorUnsyncByteArrayOutputStream);

		_commandSession = _commandProcessor.createSession(
			null, _outputPrintStream, _errorPrintStream);
	}

	@Deactivate
	public void deactivate() {
		if (_commandSession != null) {
			_commandSession.close();
		}
	}

	public void executeCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String command = ParamUtil.getString(actionRequest, "command");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			SessionMessages.add(actionRequest, "command", command);

			checkCommand(command, themeDisplay);

			_commandSession.execute(command);

			_outputPrintStream.flush();
			_errorPrintStream.flush();

			SessionMessages.add(
				actionRequest, "commandOutput",
				_outputUnsyncByteArrayOutputStream.toString());

			String errorContent = _errorUnsyncByteArrayOutputStream.toString();

			if (Validator.isNotNull(errorContent)) {
				throw new Exception(errorContent);
			}
		}
		catch (Exception e) {
			hideDefaultErrorMessage(actionRequest);

			SessionErrors.add(actionRequest, "gogo", e);
		}
		finally {
			_outputUnsyncByteArrayOutputStream.reset();
			_errorUnsyncByteArrayOutputStream.reset();
		}
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		checkOmniAdmin();

		super.processAction(actionRequest, actionResponse);
	}

	protected void checkCommand(String command, ThemeDisplay themeDisplay)
		throws Exception {

		if (StringUtil.startsWith(command, "exit")) {
			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(
					themeDisplay.getLocale());

			throw new Exception(
				LanguageUtil.format(
					resourceBundle, "the-command-x-is-not-supported", command));
		}
	}

	protected void checkOmniAdmin() throws PortletException {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			PrincipalException principalException =
				new PrincipalException.MustBeOmniadmin(permissionChecker);

			throw new PortletException(principalException);
		}
	}

	@Reference
	private CommandProcessor _commandProcessor;

	private CommandSession _commandSession;
	private PrintStream _errorPrintStream;
	private UnsyncByteArrayOutputStream _errorUnsyncByteArrayOutputStream;
	private PrintStream _outputPrintStream;
	private UnsyncByteArrayOutputStream _outputUnsyncByteArrayOutputStream;

	@Reference
	private Portal _portal;

	@Reference(target = "(bundle.symbolic.name=com.liferay.gogo.shell.web)")
	private ResourceBundleLoader _resourceBundleLoader;

}
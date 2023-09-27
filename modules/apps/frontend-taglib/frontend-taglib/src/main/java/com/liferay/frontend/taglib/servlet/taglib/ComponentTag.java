/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolvedPackageNameUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.io.IOException;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Chema Balsas
 */
public class ComponentTag extends ParamAndPropertyAncestorTagImpl {

	@Override
	public int doEndTag() throws JspException {
		try {
			_renderJavaScript();
		}
		catch (Exception exception) {
			throw new JspException(exception);
		}
		finally {
			cleanUp();
		}

		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public String getComponentId() {
		return _componentId;
	}

	public String getContainerId() {
		return _containerId;
	}

	public String getModule() {
		return StringBundler.concat(getNamespace(), "/", _module);
	}

	public boolean isDestroyOnNavigate() {
		return _destroyOnNavigate;
	}

	@Override
	public void release() {
		super.release();

		_setServletContext = false;
	}

	public void setComponentId(String componentId) {
		_componentId = componentId;
	}

	public void setContainerId(String containerId) {
		_containerId = containerId;
	}

	public void setContext(Map<String, Object> context) {
		_context = context;
	}

	public void setDestroyOnNavigate(boolean destroyOnNavigate) {
		_destroyOnNavigate = destroyOnNavigate;
	}

	public void setModule(String module) {
		_module = module;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);

		_setServletContext = true;
	}

	protected void cleanUp() {
		_componentId = null;
		_containerId = null;
		_context = null;
		_destroyOnNavigate = true;
		_module = null;
		_setServletContext = false;
	}

	protected Map<String, Object> getContext() {
		return _context;
	}

	protected String getNamespace() {
		ServletContext servletContext = pageContext.getServletContext();

		if (_setServletContext) {
			servletContext = getServletContext();
		}

		return NPMResolvedPackageNameUtil.get(servletContext);
	}

	protected boolean isPositionInline() {
		Boolean positionInline = null;

		HttpServletRequest httpServletRequest = getRequest();

		String fragmentId = ParamUtil.getString(httpServletRequest, "p_f_id");

		if (Validator.isNotNull(fragmentId)) {
			positionInline = true;
		}

		if (positionInline == null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (themeDisplay.isIsolated() ||
				themeDisplay.isLifecycleResource() ||
				themeDisplay.isStateExclusive()) {

				positionInline = true;
			}

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			String portletId = portletDisplay.getId();

			if (Validator.isNotNull(portletId) &&
				themeDisplay.isPortletEmbedded(
					themeDisplay.getScopeGroupId(), themeDisplay.getLayout(),
					portletId)) {

				positionInline = true;
			}
		}

		if (positionInline == null) {
			positionInline = false;
		}

		return positionInline;
	}

	private String _getRenderInvocation(String variableName) {
		StringBundler sb = new StringBundler(14);

		sb.append("Liferay.component('");

		String componentId = getComponentId();

		if (componentId == null) {
			componentId = _UNNAMED_COMPONENT_NAME + PortalUUIDUtil.generate();
		}

		sb.append(componentId);

		sb.append("', new ");

		sb.append(variableName);

		sb.append(".default(");

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		sb.append(
			_jsonSerializer.serializeDeep(
				HashMapBuilder.putAll(
					getContext()
				).put(
					"namespace", portletDisplay.getNamespace()
				).put(
					"spritemap", themeDisplay.getPathThemeSpritemap()
				).build()));

		String containerId = getContainerId();

		if (Validator.isNotNull(containerId)) {
			sb.append(", '");
			sb.append(containerId);
			sb.append("'");
		}

		sb.append("), { destroyOnNavigate: ");
		sb.append(_destroyOnNavigate);
		sb.append(", portletId: '");
		sb.append(portletDisplay.getId());
		sb.append("'});");

		return sb.toString();
	}

	private String _getVariableName(String module) {
		String moduleName = StringUtil.extractLast(
			module, CharPool.FORWARD_SLASH);

		return StringUtil.removeChars(moduleName, _UNSAFE_MODULE_NAME_CHARS);
	}

	private void _renderJavaScript() throws IOException {
		String module = getModule();

		String variableName = _getVariableName(module);

		String javaScriptCode = _getRenderInvocation(variableName);

		HttpServletRequest httpServletRequest = getRequest();

		if (isPositionInline()) {
			ScriptData scriptData = new ScriptData();

			scriptData.append(
				PortalUtil.getPortletId(httpServletRequest), javaScriptCode,
				module + " as " + variableName, ScriptData.ModulesType.ES6);

			JspWriter jspWriter = pageContext.getOut();

			scriptData.writeTo(jspWriter);

			return;
		}

		ScriptData scriptData = (ScriptData)httpServletRequest.getAttribute(
			WebKeys.AUI_SCRIPT_DATA);

		if (scriptData == null) {
			scriptData = new ScriptData();

			httpServletRequest.setAttribute(
				WebKeys.AUI_SCRIPT_DATA, scriptData);
		}

		scriptData.append(
			PortalUtil.getPortletId(httpServletRequest), javaScriptCode,
			module + " as " + variableName, ScriptData.ModulesType.ES6);
	}

	private static final String _UNNAMED_COMPONENT_NAME =
		"__UNNAMED_COMPONENT__";

	private static final char[] _UNSAFE_MODULE_NAME_CHARS = {
		CharPool.PERIOD, CharPool.DASH
	};

	private String _componentId;
	private String _containerId;
	private Map<String, Object> _context;
	private boolean _destroyOnNavigate = true;
	private final JSONSerializer _jsonSerializer =
		JSONFactoryUtil.createJSONSerializer();
	private String _module;
	private boolean _setServletContext;

}
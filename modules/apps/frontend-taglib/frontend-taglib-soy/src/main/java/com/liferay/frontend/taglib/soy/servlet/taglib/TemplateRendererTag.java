/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.soy.servlet.taglib;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolvedPackageNameUtil;
import com.liferay.frontend.taglib.soy.internal.template.ComponentDescriptor;
import com.liferay.frontend.taglib.soy.internal.template.SoyComponentRenderer;
import com.liferay.frontend.taglib.soy.internal.util.SoyComponentRendererProvider;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Bruno Basto
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
public class TemplateRendererTag extends ParamAndPropertyAncestorTagImpl {

	@Override
	public int doEndTag() throws JspException {
		JspWriter jspWriter = pageContext.getOut();

		Map<String, Object> context = getContext();

		try {
			prepareContext(context);

			ComponentDescriptor componentDescriptor = new ComponentDescriptor(
				getTemplateNamespace(), getModule(), getComponentId(),
				_dependencies, isWrapper(), isRenderJavaScript(),
				isPositionInLine());

			SoyComponentRenderer soyComponentRenderer =
				SoyComponentRendererProvider.getSoyComponentRenderer();

			soyComponentRenderer.renderSoyComponent(
				getRequest(), jspWriter, componentDescriptor, context);
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

	public boolean getHydrate() {
		if (_hydrate != null) {
			return _hydrate;
		}

		Map<String, Object> context = getContext();

		if (Validator.isNotNull(_componentId) || Validator.isNotNull(_module) ||
			Validator.isNotNull(context.get("data"))) {

			return true;
		}

		return false;
	}

	public String getModule() {
		if (!_useNamespace) {
			return _module;
		}

		String namespace;

		if (_setServletContext) {
			namespace = NPMResolvedPackageNameUtil.get(getServletContext());
		}
		else {
			HttpServletRequest httpServletRequest =
				(HttpServletRequest)pageContext.getRequest();

			namespace = NPMResolvedPackageNameUtil.get(httpServletRequest);
		}

		return namespace + "/" + _module;
	}

	public String getTemplateNamespace() {
		return _templateNamespace;
	}

	public boolean getUseNamespace() {
		return _useNamespace;
	}

	public void putHTMLValue(String key, String value) {
		putValue(key, value);
	}

	public void putValue(String key, Object value) {
		Map<String, Object> context = getContext();

		context.put(key, value);
	}

	@Override
	public void release() {
		super.release();

		_setServletContext = false;
	}

	public void setComponentId(String componentId) {
		_componentId = componentId;
	}

	public void setContext(Map<String, Object> context) {
		_context = context;
	}

	public void setDependencies(Set<String> dependencies) {
		_dependencies = dependencies;
	}

	public void setHydrate(boolean hydrate) {
		_hydrate = hydrate;
	}

	public void setModule(String module) {
		_module = module;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);

		_setServletContext = true;
	}

	public void setTemplateNamespace(String namespace) {
		_templateNamespace = namespace;
	}

	public void setUseNamespace(boolean useNamespace) {
		_useNamespace = useNamespace;
	}

	public void setWrapper(boolean wrapper) {
		_wrapper = wrapper;
	}

	protected void cleanUp() {
		_componentId = null;
		_context = null;
		_dependencies = null;
		_hydrate = null;
		_module = null;
		_setServletContext = false;
		_templateNamespace = null;
		_useNamespace = true;
		_wrapper = null;
	}

	protected Map<String, Object> getContext() {
		if (_context == null) {
			_context = new HashMap<>();
		}

		return _context;
	}

	protected boolean isPositionInLine() {
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

	protected boolean isRenderJavaScript() {
		if (getHydrate() && Validator.isNotNull(getModule())) {
			return true;
		}

		return false;
	}

	protected boolean isWrapper() {
		if (_wrapper != null) {
			return _wrapper;
		}

		return isRenderJavaScript();
	}

	protected void prepareContext(Map<String, Object> context) {
	}

	private String _componentId;
	private Map<String, Object> _context;
	private Set<String> _dependencies;
	private Boolean _hydrate;
	private String _module;
	private boolean _setServletContext;
	private String _templateNamespace;
	private Boolean _useNamespace = true;
	private Boolean _wrapper;

}
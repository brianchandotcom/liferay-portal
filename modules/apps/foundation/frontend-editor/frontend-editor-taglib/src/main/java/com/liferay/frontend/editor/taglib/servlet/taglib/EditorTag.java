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

package com.liferay.frontend.editor.taglib.servlet.taglib;

import com.liferay.frontend.editor.api.EditorRenderer;
import com.liferay.frontend.editor.taglib.internal.EditorRendererUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.PortalWebResourcesUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.BaseValidatorTagSupport;
import com.liferay.taglib.aui.AUIUtil;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.io.IOException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * @author Iván Zaera Avellón
 */
public class EditorTag extends BaseValidatorTagSupport {

	@Override
	public int doStartTag() throws JspException {
		EditorRenderer editorRenderer = _getEditorProvider();

		setAttributeNamespace(
			editorRenderer.getAttributeNamespace() + StringPool.COLON);

		return super.doStartTag();
	}

	@Override
	public String getInputName() {
		return _getConfigKey();
	}

	public void setAllowBrowseDocuments(boolean allowBrowseDocuments) {
		_allowBrowseDocuments = allowBrowseDocuments;
	}

	public void setAutoCreate(boolean autoCreate) {
		_autoCreate = autoCreate;
	}

	public void setConfigKey(String configKey) {
		_configKey = configKey;
	}

	public void setConfigParams(Map<String, String> configParams) {
		_configParams = configParams;
	}

	public void setContents(String contents) {
		_contents = contents;
	}

	public void setContentsLanguageId(String contentsLanguageId) {
		_contentsLanguageId = contentsLanguageId;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setEditorName(String editorName) {
		_editorName = editorName;
	}

	public void setFileBrowserParams(Map<String, String> fileBrowserParams) {
		_fileBrowserParams = fileBrowserParams;
	}

	public void setHeight(String height) {
		_height = height;
	}

	public void setInlineEdit(boolean inlineEdit) {
		_inlineEdit = inlineEdit;
	}

	public void setInlineEditSaveURL(String inlineEditSaveURL) {
		_inlineEditSaveURL = inlineEditSaveURL;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOnBlurMethod(String onBlurMethod) {
		_onBlurMethod = onBlurMethod;
	}

	public void setOnChangeMethod(String onChangeMethod) {
		_onChangeMethod = onChangeMethod;
	}

	public void setOnFocusMethod(String onFocusMethod) {
		_onFocusMethod = onFocusMethod;
	}

	public void setOnInitMethod(String onInitMethod) {
		_onInitMethod = onInitMethod;
	}

	public void setPlaceholder(String placeholder) {
		_placeholder = placeholder;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	public void setResizable(boolean resizable) {
		_resizable = resizable;
	}

	public void setShowSource(boolean showSource) {
		_showSource = showSource;
	}

	public void setSkipEditorLoading(boolean skipEditorLoading) {
		_skipEditorLoading = skipEditorLoading;
	}

	public void setToolbarSet(String toolbarSet) {
		_toolbarSet = toolbarSet;
	}

	public void setWidth(String width) {
		_width = width;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_allowBrowseDocuments = true;
		_autoCreate = true;
		_configKey = null;
		_configParams = null;
		_contents = null;
		_contentsLanguageId = null;
		_cssClass = null;
		_data = null;
		_editorName = null;
		_fileBrowserParams = null;
		_height = null;
		_inlineEdit = false;
		_inlineEditSaveURL = null;
		_name = "editor";
		_onChangeMethod = null;
		_onBlurMethod = null;
		_onFocusMethod = null;
		_onInitMethod = null;
		_placeholder = null;
		_resizable = true;
		_required = false;
		_showSource = true;
		_skipEditorLoading = false;
		_toolbarSet = _TOOLBAR_SET_DEFAULT;
		_width = null;
	}

	@Override
	protected String getPage() {
		EditorRenderer editorRenderer = _getEditorProvider();

		return editorRenderer.getJspPath();
	}

	@Override
	protected void includePage(String page, HttpServletResponse response)
		throws IOException, ServletException {

		servletContext = PortalWebResourcesUtil.getServletContext(
			_getEditorResourceType());

		super.includePage(page, response);
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(
			request, "allowBrowseDocuments",
			String.valueOf(_allowBrowseDocuments));
		setNamespacedAttribute(
			request, "autoCreate", String.valueOf(_autoCreate));
		setNamespacedAttribute(request, "configParams", _configParams);
		setNamespacedAttribute(request, "contents", _contents);
		setNamespacedAttribute(
			request, "contentsLanguageId", _getContentsLanguageId());
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "cssClasses", _getCssClasses());
		setNamespacedAttribute(request, "editorName", _getResolvedEditorName());
		setNamespacedAttribute(
			request, "fileBrowserParams", _fileBrowserParams);
		setNamespacedAttribute(request, "height", _height);
		setNamespacedAttribute(request, "initMethod", "initEditor");
		setNamespacedAttribute(
			request, "inlineEdit", String.valueOf(_inlineEdit));
		setNamespacedAttribute(
			request, "inlineEditSaveURL", _inlineEditSaveURL);
		setNamespacedAttribute(request, "name", _name);
		setNamespacedAttribute(request, "onBlurMethod", _onBlurMethod);
		setNamespacedAttribute(request, "onChangeMethod", _onChangeMethod);
		setNamespacedAttribute(request, "onFocusMethod", _onFocusMethod);
		setNamespacedAttribute(request, "onInitMethod", _onInitMethod);

		ResourceBundle resourceBundle = TagResourceBundleUtil.getResourceBundle(
			pageContext);

		if (Validator.isNull(_placeholder)) {
			_placeholder = LanguageUtil.get(
				resourceBundle, "write-your-content-here");
		}

		setNamespacedAttribute(request, "placeholder", _placeholder);

		setNamespacedAttribute(request, "required", String.valueOf(_required));
		setNamespacedAttribute(
			request, "resizable", String.valueOf(_resizable));
		setNamespacedAttribute(
			request, "showSource", String.valueOf(_showSource));
		setNamespacedAttribute(
			request, "skipEditorLoading", String.valueOf(_skipEditorLoading));
		setNamespacedAttribute(request, "toolbarSet", _getToolbarSet());
		setNamespacedAttribute(request, "width", _width);

		setNamespacedAttribute(
			request, "data",
			ProxyUtil.newProxyInstance(
				ClassLoader.getSystemClassLoader(), new Class<?>[] {Map.class},
				new LazyDataInvocationHandler()));
	}

	private String _getConfigKey() {
		String configKey = _configKey;

		if (Validator.isNull(configKey)) {
			configKey = _name;
		}

		return configKey;
	}

	private String _getContentsLanguageId() {
		if (_contentsLanguageId == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_contentsLanguageId = themeDisplay.getLanguageId();
		}

		return _contentsLanguageId;
	}

	private String _getCssClasses() {
		Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);

		String cssClasses = "portlet ";

		if (portlet != null) {
			cssClasses += portlet.getCssClassWrapper();
		}

		return cssClasses;
	}

	private Map<String, Object> _getData() {
		String portletId = (String)request.getAttribute(WebKeys.PORTLET_ID);

		if (portletId == null) {
			return _data;
		}

		Map<String, Object> attributes = new HashMap<>();

		Enumeration<String> enumeration = request.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			String attributeName = enumeration.nextElement();

			if (attributeName.startsWith(getAttributeNamespace())) {
				attributes.put(
					attributeName, request.getAttribute(attributeName));
			}
		}

		attributes.put(getAttributeNamespace() + "namespace", _getNamespace());

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		EditorConfiguration editorConfiguration =
			EditorConfigurationFactoryUtil.getEditorConfiguration(
				PortletIdCodec.decodePortletName(portletId), _getConfigKey(),
				_getResolvedEditorName(), attributes, themeDisplay,
				_getRequestBackedPortletURLFactory());

		Map<String, Object> data = editorConfiguration.getData();

		if (MapUtil.isNotEmpty(_data)) {
			MapUtil.merge(_data, data);
		}

		return data;
	}

	private EditorRenderer _getEditorProvider() {
		String resolvedEditorName = _getResolvedEditorName();

		return EditorRendererUtil.getEditorRenderer(resolvedEditorName);
	}

	private String _getEditorResourceType() {
		EditorRenderer editorRenderer = _getEditorProvider();

		return editorRenderer.getResourceType();
	}

	private String _getNamespace() {
		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if ((portletRequest == null) || (portletResponse == null)) {
			return AUIUtil.getNamespace(request);
		}

		return AUIUtil.getNamespace(portletRequest, portletResponse);
	}

	private RequestBackedPortletURLFactory
		_getRequestBackedPortletURLFactory() {

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest == null) {
			return RequestBackedPortletURLFactoryUtil.create(request);
		}

		return RequestBackedPortletURLFactoryUtil.create(portletRequest);
	}

	private String _getResolvedEditorName() {
		if (!BrowserSnifferUtil.isRtf(request)) {
			return "simple";
		}

		if (Validator.isNull(_editorName)) {
			return _EDITOR_WYSIWYG_DEFAULT;
		}

		EditorRenderer editorRenderer = EditorRendererUtil.getEditorRenderer(
			_editorName);

		if (editorRenderer == null) {
			return _EDITOR_WYSIWYG_DEFAULT;
		}

		return _editorName;
	}

	private String _getToolbarSet() {
		if (Validator.isNotNull(_toolbarSet)) {
			return _toolbarSet;
		}

		return _TOOLBAR_SET_DEFAULT;
	}

	private static final String _EDITOR_WYSIWYG_DEFAULT = PropsUtil.get(
		PropsKeys.EDITOR_WYSIWYG_DEFAULT);

	private static final String _TOOLBAR_SET_DEFAULT = "liferay";

	private boolean _allowBrowseDocuments = true;
	private boolean _autoCreate = true;
	private String _configKey;
	private Map<String, String> _configParams;
	private String _contents;
	private String _contentsLanguageId;
	private String _cssClass;
	private Map<String, Object> _data;
	private String _editorName;
	private Map<String, String> _fileBrowserParams;
	private String _height;
	private boolean _inlineEdit;
	private String _inlineEditSaveURL;
	private String _name = "editor";
	private String _onBlurMethod;
	private String _onChangeMethod;
	private String _onFocusMethod;
	private String _onInitMethod;
	private String _placeholder;
	private boolean _required;
	private boolean _resizable = true;
	private boolean _showSource = true;
	private boolean _skipEditorLoading;
	private String _toolbarSet = _TOOLBAR_SET_DEFAULT;
	private String _width;

	private class LazyDataInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws ReflectiveOperationException {

			if (_data == null) {
				_data = _getData();
			}

			return method.invoke(_data, args);
		}

		private Map<String, Object> _data;

	}

}
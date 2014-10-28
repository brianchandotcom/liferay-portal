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

package com.liferay.portal.templateparser;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.UnknownDevice;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.templateparser.TransformException;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.xsl.XSLTemplateResource;
import com.liferay.portal.xsl.XSLURIResolver;
import com.liferay.portlet.journal.util.JournalXSLURIResolver;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateConstants;
import com.liferay.taglib.util.VelocityTaglib;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Wesley Gong
 * @author Angelo Jefferson
 * @author Hugo Huijser
 * @author Marcellus Tavares
 * @author Juan Fernández
 */
public class Transformer {

	public Transformer(String errorTemplatePropertyKey, boolean restricted) {
		Set<String> langTypes = TemplateManagerUtil.getSupportedLanguageTypes(
			errorTemplatePropertyKey);

		for (String langType : langTypes) {
			String errorTemplateId = PropsUtil.get(
				errorTemplatePropertyKey, new Filter(langType));

			if (Validator.isNotNull(errorTemplateId)) {
				_errorTemplateIds.put(langType, errorTemplateId);
			}
		}

		_restricted = restricted;
	}

	public Transformer(
		String transformerListenerPropertyKey, String errorTemplatePropertyKey,
		boolean restricted) {

		this(errorTemplatePropertyKey, restricted);

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		Set<String> transformerListenerClassNames = SetUtil.fromArray(
			PropsUtil.getArray(transformerListenerPropertyKey));

		for (String transformerListenerClassName :
				transformerListenerClassNames) {

			try {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Instantiating transformer listener " +
							transformerListenerClassName);
				}

				TransformerListener transformerListener =
					(TransformerListener)InstanceFactory.newInstance(
						classLoader, transformerListenerClassName);

				_transformerListeners.add(transformerListener);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public String transform(
			ThemeDisplay themeDisplay, Map<String, Object> contextObjects,
			String script, String langType)
		throws Exception {

		if (Validator.isNull(langType)) {
			return null;
		}

		long companyId = 0;
		long companyGroupId = 0;
		long scopeGroupId = 0;
		long siteGroupId = 0;

		if (themeDisplay != null) {
			companyId = themeDisplay.getCompanyId();
			companyGroupId = themeDisplay.getCompanyGroupId();
			scopeGroupId = themeDisplay.getScopeGroupId();
			siteGroupId = themeDisplay.getSiteGroupId();
		}

		String templateId = String.valueOf(contextObjects.get("template_id"));

		templateId = getTemplateId(
			templateId, companyId, companyGroupId, scopeGroupId);

		Template template = getTemplate(templateId, script, langType);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			prepareTemplate(themeDisplay, template);

			long classNameId = 0;

			if (contextObjects != null) {
				for (String key : contextObjects.keySet()) {
					template.put(key, contextObjects.get(key));
				}

				classNameId = GetterUtil.getLong(
					contextObjects.get(TemplateConstants.CLASS_NAME_ID));
			}

			template.put("company", getCompany(themeDisplay, companyId));
			template.put("companyId", companyId);
			template.put("device", getDevice(themeDisplay));

			String templatesPath = getTemplatesPath(
				companyId, scopeGroupId, classNameId);

			template.put(
				"permissionChecker",
				PermissionThreadLocal.getPermissionChecker());
			template.put(
				"randomNamespace",
				StringUtil.randomId() + StringPool.UNDERLINE);
			template.put("scopeGroupId", scopeGroupId);
			template.put("siteGroupId", siteGroupId);
			template.put("templatesPath", templatesPath);

			// Deprecated variables

			template.put("groupId", scopeGroupId);
			template.put("journalTemplatesPath", templatesPath);

			mergeTemplate(template, unsyncStringWriter, false);
		}
		catch (Exception e) {
			throw new TransformException("Unhandled exception", e);
		}

		return unsyncStringWriter.toString();
	}

	public String transform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId, Document document,
			PortletRequestModel portletRequestModel, String script,
			String langType)
		throws Exception {

		return doTransform(
			themeDisplay, tokens, viewMode, languageId, document,
			portletRequestModel, script, langType, false);
	}

	public String transform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId, Document document,
			PortletRequestModel portletRequestModel, String script,
			String langType, boolean propagateException)
		throws Exception {

		return doTransform(
			themeDisplay, tokens, viewMode, languageId, document,
			portletRequestModel, script, langType, propagateException);
	}

	protected String doTransform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId, Document document,
			PortletRequestModel portletRequestModel, String script,
			String langType, boolean propagateException)
		throws Exception {

		// Setup listeners

		if (_log.isDebugEnabled()) {
			_log.debug("Language " + languageId);
		}

		if (Validator.isNull(viewMode)) {
			viewMode = Constants.VIEW;
		}

		if (_logTokens.isDebugEnabled()) {
			String tokensString = PropertiesUtil.list(tokens);

			_logTokens.debug(tokensString);
		}

		if (_logTransformBefore.isDebugEnabled()) {
			_logTransformBefore.debug(document);
		}

		for (TransformerListener transformerListener : _transformerListeners) {

			// Modify XML

			if (_logXmlBeforeListener.isDebugEnabled()) {
				_logXmlBeforeListener.debug(document);
			}

			if (transformerListener != null) {
				document = transformerListener.onXml(
					document, languageId, tokens);

				if (_logXmlAfterListener.isDebugEnabled()) {
					_logXmlAfterListener.debug(document);
				}
			}

			// Modify script

			if (_logScriptBeforeListener.isDebugEnabled()) {
				_logScriptBeforeListener.debug(script);
			}

			if (transformerListener != null) {
				script = transformerListener.onScript(
					script, document, languageId, tokens);

				if (_logScriptAfterListener.isDebugEnabled()) {
					_logScriptAfterListener.debug(script);
				}
			}
		}

		// Transform

		String output = null;

		if (Validator.isNull(langType)) {
			output = LocalizationUtil.getLocalization(
				document.asXML(), languageId);
		}
		else {
			long companyId = 0;
			long companyGroupId = 0;
			long articleGroupId = 0;
			long classNameId = 0;

			if (tokens != null) {
				companyId = GetterUtil.getLong(tokens.get("company_id"));
				companyGroupId = GetterUtil.getLong(
					tokens.get("company_group_id"));
				articleGroupId = GetterUtil.getLong(
					tokens.get("article_group_id"));
				classNameId = GetterUtil.getLong(
					tokens.get(TemplateConstants.CLASS_NAME_ID));
			}

			long scopeGroupId = 0;
			long siteGroupId = 0;

			if (themeDisplay != null) {
				companyId = themeDisplay.getCompanyId();
				companyGroupId = themeDisplay.getCompanyGroupId();
				scopeGroupId = themeDisplay.getScopeGroupId();
				siteGroupId = themeDisplay.getSiteGroupId();
			}

			String templateId = tokens.get("template_id");

			templateId = getTemplateId(
				templateId, companyId, companyGroupId, articleGroupId);

			Template template = getTemplate(
				templateId, tokens, languageId, document, script, langType);

			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			try {
				if (document != null) {
					Element rootElement = document.getRootElement();

					List<TemplateNode> templateNodes = getTemplateNodes(
						themeDisplay, rootElement);

					if (templateNodes != null) {
						for (TemplateNode templateNode : templateNodes) {
							template.put(templateNode.getName(), templateNode);
						}
					}

					if (portletRequestModel != null) {
						template.put("request", portletRequestModel.toMap());

						if (langType.equals(TemplateConstants.LANG_TYPE_XSL)) {
							Document requestDocument = SAXReaderUtil.read(
								portletRequestModel.toXML());

							Element requestElement =
								requestDocument.getRootElement();

							template.put("xmlRequest", requestElement.asXML());
						}
					}
					else {
						Element requestElement = rootElement.element("request");

						template.put(
							"request", insertRequestVariables(requestElement));

						if (langType.equals(TemplateConstants.LANG_TYPE_XSL)) {
							template.put("xmlRequest", requestElement.asXML());
						}
					}
				}

				template.put("articleGroupId", articleGroupId);
				template.put("company", getCompany(themeDisplay, companyId));
				template.put("companyId", companyId);
				template.put("device", getDevice(themeDisplay));

				String templatesPath = getTemplatesPath(
					companyId, articleGroupId, classNameId);

				Locale locale = LocaleUtil.fromLanguageId(languageId);

				template.put("locale", locale);

				template.put(
					"permissionChecker",
					PermissionThreadLocal.getPermissionChecker());
				template.put(
					"randomNamespace",
					StringUtil.randomId() + StringPool.UNDERLINE);
				template.put("scopeGroupId", scopeGroupId);
				template.put("siteGroupId", siteGroupId);
				template.put("templatesPath", templatesPath);
				template.put("viewMode", viewMode);

				// Deprecated variables

				template.put("groupId", articleGroupId);
				template.put("journalTemplatesPath", templatesPath);

				mergeTemplate(template, unsyncStringWriter, propagateException);
			}
			catch (Exception e) {
				if (e instanceof DocumentException) {
					throw new TransformException(
						"Unable to read XML document", e);
				}
				else if (e instanceof IOException) {
					throw new TransformException("Error reading template", e);
				}
				else if (e instanceof TransformException) {
					throw (TransformException)e;
				}
				else {
					throw new TransformException("Unhandled exception", e);
				}
			}

			output = unsyncStringWriter.toString();
		}

		// Postprocess output

		for (TransformerListener transformerListener : _transformerListeners) {

			// Modify output

			if (_logOutputBeforeListener.isDebugEnabled()) {
				_logOutputBeforeListener.debug(output);
			}

			output = transformerListener.onOutput(output, languageId, tokens);

			if (_logOutputAfterListener.isDebugEnabled()) {
				_logOutputAfterListener.debug(output);
			}
		}

		if (_logTransfromAfter.isDebugEnabled()) {
			_logTransfromAfter.debug(output);
		}

		return output;
	}

	protected Company getCompany(ThemeDisplay themeDisplay, long companyId)
		throws Exception {

		if (themeDisplay != null) {
			return themeDisplay.getCompany();
		}

		return CompanyLocalServiceUtil.getCompany(companyId);
	}

	protected Device getDevice(ThemeDisplay themeDisplay) {
		if (themeDisplay != null) {
			return themeDisplay.getDevice();
		}

		return UnknownDevice.getInstance();
	}

	protected TemplateResource getErrorTemplateResource(String langType) {
		try {
			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			String errorTemplateId = _errorTemplateIds.get(langType);

			URL url = classLoader.getResource(errorTemplateId);

			return new URLTemplateResource(errorTemplateId, url);
		}
		catch (Exception e) {
		}

		return null;
	}

	protected Template getTemplate(
			String templateId, Map<String, String> tokens, String languageId,
			Document document, String script, String langType)
		throws Exception {

		TemplateResource templateResource = null;

		if (langType.equals(TemplateConstants.LANG_TYPE_XSL)) {
			XSLURIResolver xslURIResolver = new JournalXSLURIResolver(
				tokens, languageId);

			templateResource = new XSLTemplateResource(
				templateId, script, xslURIResolver, document.asXML());
		}
		else {
			templateResource = new StringTemplateResource(templateId, script);
		}

		TemplateResource errorTemplateResource = getErrorTemplateResource(
			langType);

		return TemplateManagerUtil.getTemplate(
			langType, templateResource, errorTemplateResource, _restricted);
	}

	protected Template getTemplate(
			String templateId, String script, String langType)
		throws Exception {

		TemplateResource templateResource = new StringTemplateResource(
			templateId, script);

		TemplateResource errorTemplateResource = getErrorTemplateResource(
			langType);

		return TemplateManagerUtil.getTemplate(
			langType, templateResource, errorTemplateResource, _restricted);
	}

	protected String getTemplateId(
		String templateId, long companyId, long companyGroupId, long groupId) {

		StringBundler sb = new StringBundler(5);

		sb.append(companyId);
		sb.append(StringPool.POUND);

		if (companyGroupId > 0) {
			sb.append(companyGroupId);
		}
		else {
			sb.append(groupId);
		}

		sb.append(StringPool.POUND);
		sb.append(templateId);

		return sb.toString();
	}

	protected List<TemplateNode> getTemplateNodes(
			ThemeDisplay themeDisplay, Element element)
		throws Exception {

		List<TemplateNode> templateNodes = new ArrayList<TemplateNode>();

		Map<String, TemplateNode> prototypeTemplateNodes =
			new HashMap<String, TemplateNode>();

		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			Element dynamicContentElement = dynamicElementElement.element(
				"dynamic-content");

			String data = StringPool.BLANK;

			if (dynamicContentElement != null) {
				data = dynamicContentElement.getText();
			}

			String name = dynamicElementElement.attributeValue(
				"name", StringPool.BLANK);

			if (name.length() == 0) {
				throw new TransformException(
					"Element missing \"name\" attribute");
			}

			String type = dynamicElementElement.attributeValue(
				"type", StringPool.BLANK);

			Map<String, String> attributes = new HashMap<String, String>();

			if (dynamicContentElement != null) {
				for (Attribute attribute : dynamicContentElement.attributes()) {
					attributes.put(attribute.getName(), attribute.getValue());
				}
			}

			TemplateNode templateNode = new TemplateNode(
				themeDisplay, name, StringUtil.stripCDATA(data), type,
				attributes);

			if (dynamicElementElement.element("dynamic-element") != null) {
				templateNode.appendChildren(
					getTemplateNodes(themeDisplay, dynamicElementElement));
			}
			else if ((dynamicContentElement != null) &&
					 (dynamicContentElement.element("option") != null)) {

				List<Element> optionElements = dynamicContentElement.elements(
					"option");

				for (Element optionElement : optionElements) {
					templateNode.appendOption(
						StringUtil.stripCDATA(optionElement.getText()));
				}
			}

			TemplateNode prototypeTemplateNode = prototypeTemplateNodes.get(
				name);

			if (prototypeTemplateNode == null) {
				prototypeTemplateNode = templateNode;

				prototypeTemplateNodes.put(name, prototypeTemplateNode);

				templateNodes.add(templateNode);
			}

			prototypeTemplateNode.appendSibling(templateNode);
		}

		return templateNodes;
	}

	protected String getTemplatesPath(
		long companyId, long groupId, long classNameId) {

		StringBundler sb = new StringBundler(7);

		sb.append(TemplateConstants.TEMPLATE_SEPARATOR);
		sb.append(StringPool.SLASH);
		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(groupId);
		sb.append(StringPool.SLASH);
		sb.append(classNameId);

		return sb.toString();
	}

	protected Map<String, Object> insertRequestVariables(Element element) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (element == null) {
			return map;
		}

		for (Element childElement : element.elements()) {
			String name = childElement.getName();

			if (name.equals("attribute")) {
				Element nameElement = childElement.element("name");
				Element valueElement = childElement.element("value");

				map.put(nameElement.getText(), valueElement.getText());
			}
			else if (name.equals("parameter")) {
				Element nameElement = childElement.element("name");

				List<Element> valueElements = childElement.elements("value");

				if (valueElements.size() == 1) {
					Element valueElement = valueElements.get(0);

					map.put(nameElement.getText(), valueElement.getText());
				}
				else {
					List<String> values = new ArrayList<String>();

					for (Element valueElement : valueElements) {
						values.add(valueElement.getText());
					}

					map.put(nameElement.getText(), values);
				}
			}
			else {
				List<Element> elements = childElement.elements();

				if (!elements.isEmpty()) {
					map.put(name, insertRequestVariables(childElement));
				}
				else {
					map.put(name, childElement.getText());
				}
			}
		}

		return map;
	}

	protected void mergeTemplate(
			Template template, UnsyncStringWriter unsyncStringWriter,
			boolean propagateException)
		throws Exception {

		VelocityTaglib velocityTaglib = (VelocityTaglib)template.get(
			PortletDisplayTemplateConstants.TAGLIB_LIFERAY);

		if (velocityTaglib != null) {
			velocityTaglib.setTemplate(template);
		}

		if (propagateException) {
			template.doProcessTemplate(unsyncStringWriter);
		}
		else {
			template.processTemplate(unsyncStringWriter);
		}
	}

	protected void prepareTemplate(ThemeDisplay themeDisplay, Template template)
		throws Exception {

		if (themeDisplay == null) {
			return;
		}

		template.prepare(themeDisplay.getRequest());
	}

	private static final Log _log = LogFactoryUtil.getLog(Transformer.class);

	private static final Log _logOutputAfterListener = LogFactoryUtil.getLog(
		Transformer.class.getName() + ".OutputAfterListener");
	private static final Log _logOutputBeforeListener = LogFactoryUtil.getLog(
		Transformer.class.getName() + ".OutputBeforeListener");
	private static final Log _logScriptAfterListener = LogFactoryUtil.getLog(
		Transformer.class.getName() + ".ScriptAfterListener");
	private static final Log _logScriptBeforeListener = LogFactoryUtil.getLog(
		Transformer.class.getName() + ".ScriptBeforeListener");
	private static final Log _logTokens = LogFactoryUtil.getLog(
		Transformer.class.getName() + ".Tokens");
	private static final Log _logTransformBefore = LogFactoryUtil.getLog(
		Transformer.class.getName() + ".TransformBefore");
	private static final Log _logTransfromAfter = LogFactoryUtil.getLog(
		Transformer.class.getName() + ".TransformAfter");
	private static final Log _logXmlAfterListener = LogFactoryUtil.getLog(
		Transformer.class.getName() + ".XmlAfterListener");
	private static final Log _logXmlBeforeListener = LogFactoryUtil.getLog(
		Transformer.class.getName() + ".XmlBeforeListener");

	private final Map<String, String> _errorTemplateIds =
		new HashMap<String, String>();
	private final boolean _restricted;
	private final Set<TransformerListener> _transformerListeners =
		new HashSet<TransformerListener>();

}
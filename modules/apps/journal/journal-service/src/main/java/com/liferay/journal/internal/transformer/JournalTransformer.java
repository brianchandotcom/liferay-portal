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

package com.liferay.journal.internal.transformer;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.UnknownDevice;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.templateparser.TransformException;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.xsl.XSLTemplateResource;
import com.liferay.portal.xsl.XSLURIResolver;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Wesley Gong
 * @author Angelo Jefferson
 * @author Hugo Huijser
 * @author Marcellus Tavares
 * @author Juan Fernández
 * @author Eduardo Garcia
 */
public class JournalTransformer {

	public JournalTransformer(boolean restricted) {
		_restricted = restricted;
	}

	public String transform(
			ThemeDisplay themeDisplay, Map<String, Object> contextObjects,
			Map<String, String> tokens, String viewMode, String languageId,
			Document document, PortletRequestModel portletRequestModel,
			String script, String langType, boolean propagateException)
		throws Exception {

		return doTransform(
			themeDisplay, contextObjects, tokens, viewMode, languageId,
			document, portletRequestModel, script, langType,
			propagateException);
	}

	public String transform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId, Document document,
			PortletRequestModel portletRequestModel, String script,
			String langType)
		throws Exception {

		return doTransform(
			themeDisplay, null, tokens, viewMode, languageId, document,
			portletRequestModel, script, langType, false);
	}

	public String transform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId, Document document,
			PortletRequestModel portletRequestModel, String script,
			String langType, boolean propagateException)
		throws Exception {

		return doTransform(
			themeDisplay, null, tokens, viewMode, languageId, document,
			portletRequestModel, script, langType, propagateException);
	}

	protected String doTransform(
			ThemeDisplay themeDisplay, Map<String, Object> contextObjects,
			Map<String, String> tokens, String viewMode, String languageId,
			Document document, PortletRequestModel portletRequestModel,
			String script, String langType, boolean propagateException)
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

		List<TransformerListener> transformerListeners =
			JournalTransformerListenerRegistryUtil.getTransformerListeners();

		for (TransformerListener transformerListener : transformerListeners) {

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

			if ((themeDisplay != null) && (themeDisplay.getRequest() != null)) {
				template.prepare(themeDisplay.getRequest());
			}

			if (contextObjects != null) {
				template.putAll(contextObjects);
			}

			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			try {
				Locale locale = LocaleUtil.fromLanguageId(languageId);

				if (document != null) {
					Element rootElement = document.getRootElement();

					List<TemplateNode> templateNodes = getTemplateNodes(
						themeDisplay, rootElement,
						Long.valueOf(tokens.get("ddm_structure_id")), locale);

					if (templateNodes != null) {
						for (TemplateNode templateNode : templateNodes) {
							template.put(templateNode.getName(), templateNode);
						}
					}

					if (portletRequestModel != null) {
						template.put("requestMap", portletRequestModel.toMap());

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
							"requestMap",
							insertRequestVariables(requestElement));

						if (langType.equals(TemplateConstants.LANG_TYPE_XSL)) {
							template.put("xmlRequest", requestElement.asXML());
						}
					}
				}

				template.put("articleGroupId", articleGroupId);
				template.put("company", getCompany(themeDisplay, companyId));
				template.put("companyId", companyId);
				template.put("device", getDevice(themeDisplay));
				template.put("locale", locale);
				template.put(
					"permissionChecker",
					PermissionThreadLocal.getPermissionChecker());
				template.put(
					"randomNamespace",
					StringUtil.randomId() + StringPool.UNDERLINE);
				template.put("scopeGroupId", scopeGroupId);
				template.put("siteGroupId", siteGroupId);

				String templatesPath = getTemplatesPath(
					companyId, articleGroupId, classNameId);

				template.put("templatesPath", templatesPath);

				template.put("viewMode", viewMode);

				if (themeDisplay != null) {
					TemplateManager templateManager =
						TemplateManagerUtil.getTemplateManager(langType);

					HttpServletRequest request = themeDisplay.getRequest();

					templateManager.addTaglibSupport(
						template, request, themeDisplay.getResponse());
					templateManager.addTaglibTheme(
						template, "taglibLiferay", request,
						new PipingServletResponse(
							themeDisplay.getResponse(), unsyncStringWriter));
				}

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

		for (TransformerListener transformerListener : transformerListeners) {

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

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
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
			long companyId = CompanyThreadLocal.getCompanyId();

			JournalServiceConfiguration journalServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					JournalServiceConfiguration.class, companyId);

			String template = StringPool.BLANK;

			if (langType.equals(TemplateConstants.LANG_TYPE_FTL)) {
				template = journalServiceConfiguration.errorTemplateFTL();
			}
			else if (langType.equals(TemplateConstants.LANG_TYPE_VM)) {
				template = journalServiceConfiguration.errorTemplateVM();
			}
			else if (langType.equals(TemplateConstants.LANG_TYPE_XSL)) {
				template = journalServiceConfiguration.errorTemplateXSL();
			}
			else {
				return null;
			}

			return new StringTemplateResource(langType, template);
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
			ThemeDisplay themeDisplay, Element element, long ddmStructureId)
		throws Exception {

		Locale locale = LocaleThreadLocal.getSiteDefaultLocale();

		if ((themeDisplay != null) && (themeDisplay.getLocale() != null)) {
			locale = themeDisplay.getLocale();
		}

		return getTemplateNodes(themeDisplay, element, ddmStructureId, locale);
	}

	protected List<TemplateNode> getTemplateNodes(
			ThemeDisplay themeDisplay, Element element, long ddmStructureId,
			Locale locale)
		throws Exception {

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		DDMForm ddmForm = ddmStructure.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		List<TemplateNode> templateNodes = new ArrayList<>();

		Map<String, TemplateNode> prototypeTemplateNodes = new HashMap<>();

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

			Map<String, String> attributes = new HashMap<>();

			if (type.equals("image")) {
				JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(
					data);

				Iterator<String> itr = dataJSONObject.keys();

				while (itr.hasNext()) {
					String key = itr.next();

					String value = dataJSONObject.getString(key);

					attributes.put(key, value);
				}
			}

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
					getTemplateNodes(
						themeDisplay, dynamicElementElement, ddmStructureId,
						locale));
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

			DDMFormField ddmFormField = ddmFormFieldsMap.get(name);

			if (ddmFormField != null) {
				DDMFormFieldOptions ddmFormFieldOptions =
					ddmFormField.getDDMFormFieldOptions();

				Map<String, LocalizedValue> options =
					ddmFormFieldOptions.getOptions();

				for (Map.Entry<String, LocalizedValue> entry :
						options.entrySet()) {

					String optionValue = StringUtil.stripCDATA(entry.getKey());

					LocalizedValue localizedLabel = entry.getValue();

					String optionLabel = localizedLabel.getString(locale);

					templateNode.appendOptionMap(optionValue, optionLabel);
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
		Map<String, Object> map = new HashMap<>();

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
					List<String> values = new ArrayList<>();

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

		if (propagateException) {
			template.doProcessTemplate(unsyncStringWriter);
		}
		else {
			template.processTemplate(unsyncStringWriter);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalTransformer.class);

	private static final Log _logOutputAfterListener = LogFactoryUtil.getLog(
		JournalTransformer.class.getName() + ".OutputAfterListener");
	private static final Log _logOutputBeforeListener = LogFactoryUtil.getLog(
		JournalTransformer.class.getName() + ".OutputBeforeListener");
	private static final Log _logScriptAfterListener = LogFactoryUtil.getLog(
		JournalTransformer.class.getName() + ".ScriptAfterListener");
	private static final Log _logScriptBeforeListener = LogFactoryUtil.getLog(
		JournalTransformer.class.getName() + ".ScriptBeforeListener");
	private static final Log _logTokens = LogFactoryUtil.getLog(
		JournalTransformer.class.getName() + ".Tokens");
	private static final Log _logTransformBefore = LogFactoryUtil.getLog(
		JournalTransformer.class.getName() + ".TransformBefore");
	private static final Log _logTransfromAfter = LogFactoryUtil.getLog(
		JournalTransformer.class.getName() + ".TransformAfter");
	private static final Log _logXmlAfterListener = LogFactoryUtil.getLog(
		JournalTransformer.class.getName() + ".XmlAfterListener");
	private static final Log _logXmlBeforeListener = LogFactoryUtil.getLog(
		JournalTransformer.class.getName() + ".XmlBeforeListener");

	private final boolean _restricted;

}
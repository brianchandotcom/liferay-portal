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

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
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
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.templateparser.TransformException;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Wesley Gong
 * @author Angelo Jefferson
 * @author Hugo Huijser
 * @author Marcellus Tavares
 * @author Juan Fernández
 * @author Eduardo García
 */
public class JournalTransformer {

	public String transform(
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

			String templateId = tokens.get("ddm_template_id");

			templateId = getTemplateId(
				templateId, companyId, companyGroupId, articleGroupId);

			Template template = getTemplate(templateId, script, langType);

			PortletRequest originalPortletRequest = null;
			PortletResponse originalPortletResponse = null;

			HttpServletRequest httpServletRequest = null;

			if ((themeDisplay != null) && (themeDisplay.getRequest() != null)) {
				httpServletRequest = themeDisplay.getRequest();

				if (portletRequestModel != null) {
					originalPortletRequest =
						(PortletRequest)httpServletRequest.getAttribute(
							JavaConstants.JAVAX_PORTLET_REQUEST);
					originalPortletResponse =
						(PortletResponse)httpServletRequest.getAttribute(
							JavaConstants.JAVAX_PORTLET_RESPONSE);

					httpServletRequest.setAttribute(
						JavaConstants.JAVAX_PORTLET_REQUEST,
						portletRequestModel.getPortletRequest());
					httpServletRequest.setAttribute(
						JavaConstants.JAVAX_PORTLET_RESPONSE,
						portletRequestModel.getPortletResponse());
					httpServletRequest.setAttribute(
						PortletRequest.LIFECYCLE_PHASE,
						portletRequestModel.getLifecycle());
				}

				template.prepare(httpServletRequest);
			}

			if (contextObjects != null) {
				template.putAll(contextObjects);
			}

			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			try {
				Locale locale = LocaleUtil.fromLanguageId(languageId);

				if (document != null) {
					Element rootElement = document.getRootElement();

					long ddmStructureId = GetterUtil.getLong(
						tokens.get("ddm_structure_id"));

					DDMStructure ddmStructure =
						DDMStructureLocalServiceUtil.getStructure(
							ddmStructureId);

					DDMForm ddmForm = ddmStructure.getDDMForm();

					List<TemplateNode> templateNodes = getTemplateNodes(
						themeDisplay, rootElement,
						ddmForm.getDDMFormFieldsMap(true), locale);

					templateNodes.addAll(
						includeBackwardsCompatibilityTemplateNodes(
							templateNodes, -1));

					for (TemplateNode templateNode : templateNodes) {
						template.put(templateNode.getName(), templateNode);
					}

					if (portletRequestModel != null) {
						template.put("requestMap", portletRequestModel.toMap());
					}
					else {
						Element requestElement = rootElement.element("request");

						template.put(
							"requestMap",
							insertRequestVariables(requestElement));
					}
				}

				template.put("articleGroupId", articleGroupId);
				template.put("articleLocale", locale);
				template.put("company", getCompany(themeDisplay, companyId));
				template.put("companyId", companyId);
				template.put("device", getDevice(themeDisplay));
				template.put("locale", getLocale(themeDisplay, locale));
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
					template.prepareTaglib(
						themeDisplay.getRequest(),
						new PipingServletResponse(
							themeDisplay.getResponse(), unsyncStringWriter));
				}

				// Deprecated variables

				template.put("groupId", articleGroupId);
				template.put("journalTemplatesPath", templatesPath);

				if (propagateException) {
					template.processTemplate(unsyncStringWriter);
				}
				else {
					template.processTemplate(
						unsyncStringWriter,
						() -> getErrorTemplateResource(langType));
				}
			}
			catch (Exception exception) {
				if (exception instanceof DocumentException) {
					throw new TransformException(
						"Unable to read XML document", exception);
				}
				else if (exception instanceof IOException) {
					throw new TransformException(
						"Error reading template", exception);
				}
				else if (exception instanceof TransformException) {
					throw (TransformException)exception;
				}
				else {
					throw new TransformException(
						"Unhandled exception", exception);
				}
			}
			finally {
				if ((httpServletRequest != null) &&
					(portletRequestModel != null)) {

					httpServletRequest.setAttribute(
						JavaConstants.JAVAX_PORTLET_REQUEST,
						originalPortletRequest);
					httpServletRequest.setAttribute(
						JavaConstants.JAVAX_PORTLET_RESPONSE,
						originalPortletResponse);
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
			JournalServiceConfiguration journalServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					JournalServiceConfiguration.class,
					CompanyThreadLocal.getCompanyId());

			String template = StringPool.BLANK;

			if (langType.equals(TemplateConstants.LANG_TYPE_FTL)) {
				template = journalServiceConfiguration.errorTemplateFTL();
			}
			else if (langType.equals(TemplateConstants.LANG_TYPE_VM)) {
				template = journalServiceConfiguration.errorTemplateVM();
			}
			else {
				return null;
			}

			return new StringTemplateResource(langType, template);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return null;
	}

	protected Locale getLocale(ThemeDisplay themeDisplay, Locale locale)
		throws Exception {

		if (themeDisplay != null) {
			return themeDisplay.getLocale();
		}

		return locale;
	}

	protected Template getTemplate(
			String templateId, String script, String langType)
		throws Exception {

		TemplateResource templateResource = new StringTemplateResource(
			templateId, script);

		return TemplateManagerUtil.getTemplate(
			langType, templateResource, true);
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
			ThemeDisplay themeDisplay, Element element,
			Map<String, DDMFormField> ddmFormFieldsMap, Locale locale)
		throws Exception {

		List<TemplateNode> templateNodes = new ArrayList<>();

		Map<String, TemplateNode> prototypeTemplateNodes = new HashMap<>();

		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			String name = dynamicElementElement.attributeValue("name");

			if (Validator.isNull(name)) {
				throw new TransformException(
					"Element missing \"name\" attribute");
			}

			DDMFormField ddmFormField = ddmFormFieldsMap.get(name);

			if (ddmFormField == null) {
				String data = StringPool.BLANK;

				Element dynamicContentElement = dynamicElementElement.element(
					"dynamic-content");

				if (dynamicContentElement != null) {
					data = dynamicContentElement.getText();
				}

				templateNodes.add(
					new TemplateNode(
						themeDisplay, name, StringUtil.stripCDATA(data),
						StringPool.BLANK, new HashMap<>()));

				continue;
			}

			TemplateNode templateNode = _createTemplateNode(
				ddmFormField, dynamicElementElement, locale, themeDisplay);

			if (dynamicElementElement.element("dynamic-element") != null) {
				templateNode.appendChildren(
					getTemplateNodes(
						themeDisplay, dynamicElementElement, ddmFormFieldsMap,
						locale));
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

		return StringBundler.concat(
			TemplateConstants.TEMPLATE_SEPARATOR, StringPool.SLASH, companyId,
			StringPool.SLASH, groupId, StringPool.SLASH, classNameId);
	}

	protected List<TemplateNode> includeBackwardsCompatibilityTemplateNodes(
		List<TemplateNode> templateNodes, int parentOffset) {

		List<TemplateNode> backwardsCompatibilityTemplateNodes =
			new ArrayList<>();

		parentOffset++;

		for (TemplateNode templateNode : templateNodes) {
			if (!Objects.equals(
					templateNode.getType(),
					DDMFormFieldTypeConstants.FIELDSET)) {

				if (parentOffset > 0) {
					backwardsCompatibilityTemplateNodes.add(
						(TemplateNode)templateNode.clone());
				}

				continue;
			}

			List<TemplateNode> childTemplateNodes = templateNode.getChildren();

			if (ListUtil.isEmpty(childTemplateNodes)) {
				continue;
			}

			TemplateNode firstChildTemplateNode = childTemplateNodes.get(0);

			firstChildTemplateNode =
				(TemplateNode)firstChildTemplateNode.clone();

			List<TemplateNode> newChildTemplateNodes = new ArrayList<>(
				childTemplateNodes);

			newChildTemplateNodes.remove(0);

			firstChildTemplateNode.appendChildren(
				includeBackwardsCompatibilityTemplateNodes(
					newChildTemplateNodes, parentOffset));

			List<TemplateNode> siblingsTemplateNodes =
				templateNode.getSiblings();

			if (!siblingsTemplateNodes.isEmpty()) {
				List<TemplateNode> firstChildSiblingsTemplateNodes =
					firstChildTemplateNode.getSiblings();

				firstChildSiblingsTemplateNodes.clear();

				firstChildSiblingsTemplateNodes.add(firstChildTemplateNode);

				List<TemplateNode> newSiblingsTemplateNodes = new ArrayList<>(
					siblingsTemplateNodes);

				newSiblingsTemplateNodes.remove(0);

				firstChildSiblingsTemplateNodes.addAll(
					includeBackwardsCompatibilityTemplateNodes(
						newSiblingsTemplateNodes, parentOffset));
			}

			backwardsCompatibilityTemplateNodes.add(firstChildTemplateNode);
		}

		return backwardsCompatibilityTemplateNodes;
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

	private String _convertToReferenceIfNeeded(
		String data, DDMFormField ddmFormField) {

		if (Validator.isNull(data)) {
			return data;
		}

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		Map<String, String> optionsReferences =
			ddmFormFieldOptions.getOptionsReferences();

		String type = ddmFormField.getType();

		if (Objects.equals(type, DDMFormFieldTypeConstants.SELECT) ||
			Objects.equals(type, DDMFormFieldTypeConstants.RADIO)) {

			return optionsReferences.getOrDefault(data, data);
		}

		if (Objects.equals(type, DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE)) {
			try {
				JSONArray nextJSONArray = JSONFactoryUtil.createJSONArray();

				JSONArray jsonArray = JSONFactoryUtil.createJSONArray(data);

				for (Object element : jsonArray) {
					String optionValue = (String)element;

					nextJSONArray.put(
						optionsReferences.getOrDefault(
							optionValue, optionValue));
				}

				return nextJSONArray.toJSONString();
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}
		}

		if (Objects.equals(type, DDMFormFieldTypeConstants.GRID)) {
			try {
				JSONObject nextJSONObject = JSONFactoryUtil.createJSONObject();

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(data);

				DDMFormFieldOptions rowsDDMFormFieldOptions =
					(DDMFormFieldOptions)ddmFormField.getProperty("rows");

				Map<String, String> rowOptionsReferences =
					rowsDDMFormFieldOptions.getOptionsReferences();

				DDMFormFieldOptions columnsDDMFormFieldOptions =
					(DDMFormFieldOptions)ddmFormField.getProperty("columns");

				Map<String, String> columnsReferences =
					columnsDDMFormFieldOptions.getOptionsReferences();

				for (String key : jsonObject.keySet()) {
					String value = jsonObject.getString(key);

					nextJSONObject.put(
						rowOptionsReferences.getOrDefault(key, key),
						columnsReferences.getOrDefault(value, value));
				}

				return nextJSONObject.toString();
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}
		}

		return data;
	}

	private TemplateNode _createTemplateNode(
			DDMFormField ddmFormField, Element dynamicElementElement,
			Locale locale, ThemeDisplay themeDisplay)
		throws Exception {

		String data = StringPool.BLANK;

		Element dynamicContentElement = dynamicElementElement.element(
			"dynamic-content");

		if (dynamicContentElement != null) {
			data = dynamicContentElement.getText();
		}

		String type = dynamicElementElement.attributeValue(
			"type", ddmFormField.getType());

		Map<String, String> attributes = new HashMap<>();

		if (type.equals(DDMFormFieldTypeConstants.IMAGE)) {
			JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(data);

			Iterator<String> iterator = dataJSONObject.keys();

			while (iterator.hasNext()) {
				String key = iterator.next();

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
			themeDisplay, ddmFormField.getFieldReference(),
			_convertToReferenceIfNeeded(
				StringUtil.stripCDATA(data), ddmFormField),
			type, attributes);

		if ((dynamicElementElement.element("dynamic-element") == null) &&
			(dynamicContentElement != null) &&
			(dynamicContentElement.element("option") != null)) {

			List<Element> optionElements = dynamicContentElement.elements(
				"option");

			for (Element optionElement : optionElements) {
				templateNode.appendOption(
					_convertToReferenceIfNeeded(
						StringUtil.stripCDATA(optionElement.getText()),
						ddmFormField));
			}
		}

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		Map<String, LocalizedValue> options = ddmFormFieldOptions.getOptions();
		Map<String, String> optionsReferences =
			ddmFormFieldOptions.getOptionsReferences();

		for (Map.Entry<String, LocalizedValue> entry : options.entrySet()) {
			String optionValue = StringUtil.stripCDATA(entry.getKey());

			String optionReference = optionsReferences.getOrDefault(
				optionValue, optionValue);

			LocalizedValue localizedLabel = entry.getValue();

			String optionLabel = localizedLabel.getString(locale);

			templateNode.appendOptionMap(optionReference, optionLabel);
		}

		return templateNode;
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

}
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.UnknownDevice;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.templateparser.TransformException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateConstants;
import com.liferay.taglib.util.VelocityTaglib;
import com.liferay.util.PwdGenerator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class TemplateParser {

	public TemplateParser(
		ThemeDisplay themeDisplay, Map<String, Object> contextObjects,
		Template template) {

		_themeDisplay = themeDisplay;
		_contextObjects = contextObjects;
		_template = template;
	}

	public TemplateParser(
		ThemeDisplay themeDisplay, Map<String, String> tokens, String viewMode,
		String languageId, String xml, Template template) {

		_themeDisplay = themeDisplay;
		_tokens = tokens;
		_viewMode = viewMode;
		_languageId = languageId;
		_xml = xml;
		_template = template;
	}

	public String transform() throws TransformException {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		boolean load = false;

		try {
			if (Validator.isNotNull(_xml)) {
				Document document = SAXReaderUtil.read(_xml);

				Element rootElement = document.getRootElement();

				List<TemplateNode> templateNodes = getTemplateNodes(
					rootElement);

				if (templateNodes != null) {
					for (TemplateNode templateNode : templateNodes) {
						_template.put(templateNode.getName(), templateNode);
					}
				}

				Element requestElement = rootElement.element("request");

				_template.put(
					"request", insertRequestVariables(requestElement));

				_template.put("xmlRequest", requestElement.asXML());
			}

			if (_contextObjects != null) {
				for (String key : _contextObjects.keySet()) {
					_template.put(key, _contextObjects.get(key));
				}
			}

			populateTemplateContext(_template);

			load = mergeTemplate(_template, unsyncStringWriter);
		}
		catch (Exception e) {
			if (e instanceof DocumentException) {
				throw new TransformException("Unable to read XML document", e);
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

		if (!load) {
			throw new TransformException(
				"Unable to dynamically load transform script");
		}

		return unsyncStringWriter.toString();
	}

	protected Company getCompany() throws Exception {
		if (_themeDisplay != null) {
			return _themeDisplay.getCompany();
		}

		return CompanyLocalServiceUtil.getCompany(getCompanyId());
	}

	protected long getCompanyId() {
		if (_themeDisplay != null) {
			return _themeDisplay.getCompanyId();
		}

		return GetterUtil.getLong(_tokens.get("company_id"));
	}

	protected Device getDevice() {
		if (_themeDisplay != null) {
			return _themeDisplay.getDevice();
		}

		return UnknownDevice.getInstance();
	}

	protected long getGroupId() {
		if (_themeDisplay != null) {
			return _themeDisplay.getScopeGroupId();
		}

		return GetterUtil.getLong(_tokens.get("group_id"));
	}

	protected String getJournalTemplatesPath() {
		StringBundler sb = new StringBundler(5);

		sb.append(TemplateConstants.JOURNAL_SEPARATOR);
		sb.append(StringPool.SLASH);
		sb.append(getCompanyId());
		sb.append(StringPool.SLASH);
		sb.append(getGroupId());

		return sb.toString();
	}

	protected List<TemplateNode> getTemplateNodes(Element element)
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

			TemplateNode templateNode = new TemplateNode(
				_themeDisplay, name, stripCDATA(data), type);

			if (dynamicElementElement.element("dynamic-element") != null) {
				templateNode.appendChildren(
					getTemplateNodes(dynamicElementElement));
			}
			else if ((dynamicContentElement != null) &&
					 (dynamicContentElement.element("option") != null)) {

				List<Element> optionElements = dynamicContentElement.elements(
					"option");

				for (Element optionElement : optionElements) {
					templateNode.appendOption(
						stripCDATA(optionElement.getText()));
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
			else if (childElement.elements().size() > 0) {
				map.put(name, insertRequestVariables(childElement));
			}
			else {
				map.put(name, childElement.getText());
			}
		}

		return map;
	}

	protected boolean mergeTemplate(
			Template template, UnsyncStringWriter unsyncStringWriter)
		throws Exception {

		VelocityTaglib velocityTaglib = (VelocityTaglib)template.get(
			PortletDisplayTemplateConstants.TAGLIB_LIFERAY);

		if (velocityTaglib != null) {
			velocityTaglib.setTemplate(template);
		}

		return template.processTemplate(unsyncStringWriter);
	}

	protected void populateTemplateContext(Template template) throws Exception {
		template.put("company", getCompany());
		template.put("companyId", getCompanyId());
		template.put("device", getDevice());
		template.put("groupId", getGroupId());
		template.put("journalTemplatesPath", getJournalTemplatesPath());

		Locale locale = LocaleUtil.fromLanguageId(_languageId);

		template.put("locale", locale);

		template.put(
			"permissionChecker", PermissionThreadLocal.getPermissionChecker());
		template.put("viewMode", _viewMode);

		String randomNamespace =
			PwdGenerator.getPassword(PwdGenerator.KEY3, 4) +
				StringPool.UNDERLINE;

		template.put("randomNamespace", randomNamespace);
	}

	protected String stripCDATA(String s) {
		if (s.startsWith(StringPool.CDATA_OPEN) &&
			s.endsWith(StringPool.CDATA_CLOSE)) {

			s = s.substring(
				StringPool.CDATA_OPEN.length(),
				s.length() - StringPool.CDATA_CLOSE.length());
		}

		return s;
	}

	private Map<String, Object> _contextObjects = new HashMap<String, Object>();
	private String _languageId;
	private Template _template;
	private ThemeDisplay _themeDisplay;
	private Map<String, String> _tokens;
	private String _viewMode;
	private String _xml;

}
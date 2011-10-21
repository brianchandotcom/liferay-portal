/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.templateparser;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

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
public abstract class BaseTemplateParser implements TemplateParser {

	public String getLanguageId() {
		return _languageId;
	}

	public String getScript() {
		return _script;
	}

	public ThemeDisplay getThemeDisplay() {
		return _themeDisplay;
	}

	public Map<String, String> getTokens() {
		return _tokens;
	}

	public String getViewMode() {
		return _viewMode;
	}

	public String getXML() {
		return _xml;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public void setScript(String script) {
		_script = script;
	}

	public void setThemeDisplay(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;
	}

	public void setTokens(Map<String, String> tokens) {
		_tokens = tokens;
	}

	public void setViewMode(String viewMode) {
		_viewMode = viewMode;
	}

	public void setXML(String xml) {
		_xml = xml;
	}

	public String transform() throws TransformException {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		boolean load = false;

		try {
			TemplateContext templateContext = getTemplateContext();

			Document document = SAXReaderUtil.read(_xml);

			Element rootElement = document.getRootElement();

			List<TemplateNode> templateNodes = getTemplateNodes(rootElement);

			if (templateNodes != null) {
				for (TemplateNode templateNode : templateNodes) {
					templateContext.put(templateNode.getName(), templateNode);
				}
			}

			Element requestElement = rootElement.element("request");

			templateContext.put(
				"request", insertRequestVariables(requestElement));

			templateContext.put("xmlRequest", requestElement.asXML());

			populateTemplateContext(templateContext);

			load = mergeTemplate(templateContext, unsyncStringWriter);
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
		long companyId = getCompanyId();

		return CompanyLocalServiceUtil.getCompany(companyId);
	}

	protected long getCompanyId() {
		return GetterUtil.getLong(_tokens.get("company_id"));
	}

	protected long getGroupId() {
		return GetterUtil.getLong(_tokens.get("group_id"));
	}

	protected abstract TemplateContext getTemplateContext() throws Exception;

	protected String getTemplateId() {
		long companyGroupId = GetterUtil.getLong(
			_tokens.get("company_group_id"));
		String templateId = _tokens.get("template_id");

		if (companyGroupId > 0) {
			return getCompanyId() + companyGroupId + templateId;
		}
		else {
			return getCompanyId() + getGroupId() + templateId;
		}
	}

	protected abstract List<TemplateNode> getTemplateNodes(Element element)
		throws Exception;

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

	protected abstract boolean mergeTemplate(
			TemplateContext templateContext,
			UnsyncStringWriter unsyncStringWriter)
		throws Exception;

	protected void populateTemplateContext(TemplateContext templateContext)
		throws Exception {

		templateContext.put("company", getCompany());
		templateContext.put("companyId", getCompanyId());
		templateContext.put("device", _themeDisplay.getDevice());
		templateContext.put("groupId", getGroupId());

		Locale locale = LocaleUtil.fromLanguageId(_languageId);

		templateContext.put("locale", locale);

		templateContext.put(
			"permissionChecker", PermissionThreadLocal.getPermissionChecker());
		templateContext.put("viewMode", _viewMode);
	}

	private String _languageId;
	private String _script;
	private ThemeDisplay _themeDisplay;
	private Map<String, String> _tokens;
	private String _viewMode;
	private String _xml;

}
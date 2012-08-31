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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateContextType;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.templateparser.BaseTemplateParser;
import com.liferay.portal.kernel.templateparser.TemplateContext;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.templateparser.TransformException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateConstants;
import com.liferay.taglib.util.VelocityTaglib;
import com.liferay.util.PwdGenerator;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class VelocityTemplateParser extends BaseTemplateParser {

	protected String getErrorTemplateId() {
		return PropsValues.JOURNAL_ERROR_TEMPLATE_VELOCITY;
	}

	protected TemplateResource getErrorTemplateResource() {
		try {
			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			URL url = classLoader.getResource(getErrorTemplateId());

			return new URLTemplateResource(getErrorTemplateId(), url);
		}
		catch (Exception e) {
		}

		return null;
	}

	protected String getJournalTemplatesPath() {
		StringBundler sb = new StringBundler(5);

		sb.append(TemplateResource.JOURNAL_SEPARATOR);
		sb.append(StringPool.SLASH);
		sb.append(getCompanyId());
		sb.append(StringPool.SLASH);
		sb.append(getGroupId());

		return sb.toString();
	}

	@Override
	protected TemplateContext getTemplateContext() throws Exception {
		TemplateResource templateResource = new StringTemplateResource(
			getTemplateId(), getScript());

		return TemplateManagerUtil.getTemplate(
			TemplateManager.VELOCITY, templateResource,
			getErrorTemplateResource(), TemplateContextType.RESTRICTED);
	}

	@Override
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
				getThemeDisplay(), name, stripCDATA(data), type);

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

	@Override
	protected boolean mergeTemplate(
			TemplateContext templateContext,
			UnsyncStringWriter unsyncStringWriter)
		throws Exception {

		Template template = (Template)templateContext;

		VelocityTaglib velocityTaglib = (VelocityTaglib)template.get(
			PortletDisplayTemplateConstants.TAGLIB_LIFERAY);

		if (velocityTaglib != null) {
			velocityTaglib.setTemplateContext(templateContext);
		}

		return template.processTemplate(unsyncStringWriter);
	}

	@Override
	protected void populateTemplateContext(TemplateContext templateContext)
		throws Exception {

		super.populateTemplateContext(templateContext);

		templateContext.put("journalTemplatesPath", getJournalTemplatesPath());

		String randomNamespace =
			PwdGenerator.getPassword(PwdGenerator.KEY3, 4) +
				StringPool.UNDERLINE;

		templateContext.put("randomNamespace", randomNamespace);
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

}
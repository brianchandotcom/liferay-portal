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

package com.liferay.fragment.entry.processor.invocation;

import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.invocation.provider.PortletInvocationProvider;
import com.liferay.fragment.invocation.provider.PortletInvocationProviderTracker;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.util.HtmlParserUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.util.List;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = FragmentEntryProcessor.class)
public class InvocationFragmentEntryProcessor
	implements FragmentEntryProcessor {

	@Override
	public void validateFragmentEntryHTML(String html) throws PortalException {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		StringBundler sb = new StringBundler(4);

		sb.append(StringPool.DOUBLE_SLASH);
		sb.append("*[starts-with(local-name(), '");
		sb.append(PortletInvocationProvider.INVOCATION_TAG_PREFIX);
		sb.append("')]");

		XPath invocableXpath = SAXReaderUtil.createXPath(sb.toString());

		Document document = _htmlParserUtil.parse(html);

		List<Node> invocableNodes = invocableXpath.selectNodes(document);

		for (Node node : invocableNodes) {
			Element element = (Element)node;

			String alias = StringUtil.replace(
				element.getName(),
				PortletInvocationProvider.INVOCATION_TAG_PREFIX,
				StringPool.BLANK);

			PortletInvocationProvider portletInvocationProvider =
				_portletInvocationProviderTracker.getPortletInvocationProvider(
					alias);

			if (portletInvocationProvider == null) {
				throw new FragmentEntryContentException(
					LanguageUtil.format(
						resourceBundle,
						"no-portlet-invocation-provider-available-for-alias-x",
						alias));
			}

			String[] requiredAttributes =
				portletInvocationProvider.getRequiredAttributes();

			for (String attribute : requiredAttributes) {
				if (Validator.isNull(element.attributeValue(attribute))) {
					throw new FragmentEntryContentException(
						LanguageUtil.format(
							resourceBundle,
							"missing-required-attribute-x-for-tag-x",
							new String[] {attribute, element.getName()}));
				}
			}
		}
	}

	@Reference
	private HtmlParserUtil _htmlParserUtil;

	@Reference
	private PortletInvocationProviderTracker _portletInvocationProviderTracker;

}
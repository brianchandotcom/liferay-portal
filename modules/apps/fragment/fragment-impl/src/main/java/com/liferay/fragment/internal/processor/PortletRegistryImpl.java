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

package com.liferay.fragment.internal.processor;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.Portlet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = PortletRegistry.class)
public class PortletRegistryImpl implements PortletRegistry {

	@Override
	public List<String> getFragmentEntryLinkPortletIds(
		FragmentEntryLink fragmentEntryLink) {

		List<String> portletIds = new ArrayList<>();

		Document document = Jsoup.parseBodyFragment(
			fragmentEntryLink.getHtml());

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		for (Element element : document.select("*")) {
			String tagName = element.tagName();

			if (!StringUtil.startsWith(tagName, "lfr-widget-")) {
				continue;
			}

			String alias = StringUtil.replace(
				tagName, "lfr-widget-", StringPool.BLANK);

			String portletName = getPortletName(alias);

			if (Validator.isNull(portletName)) {
				continue;
			}

			String portletId = PortletIdCodec.encode(
				PortletIdCodec.decodePortletName(portletName),
				PortletIdCodec.decodeUserId(portletName),
				fragmentEntryLink.getNamespace() + element.attr("id"));

			portletIds.add(portletId);
		}

		return portletIds;
	}

	@Override
	public List<String> getPortletAliases() {
		return new ArrayList<>(_portletNames.keySet());
	}

	@Override
	public String getPortletName(String alias) {
		return _portletNames.get(alias);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		target = "(com.liferay.fragment.entry.processor.portlet.alias=*)"
	)
	protected void setPortlet(Portlet portlet, Map<String, Object> properties) {
		String alias = MapUtil.getString(
			properties, "com.liferay.fragment.entry.processor.portlet.alias");
		String portletName = MapUtil.getString(
			properties, "javax.portlet.name");

		_portletNames.put(alias, portletName);
	}

	protected void unsetPortlet(
		Portlet portlet, Map<String, Object> properties) {

		String alias = MapUtil.getString(
			properties, "com.liferay.fragment.entry.processor.portlet.alias");
		String portletName = MapUtil.getString(
			properties, "javax.portlet.name");

		_portletNames.remove(alias, portletName);
	}

	private final Map<String, String> _portletNames = new ConcurrentHashMap<>();

}
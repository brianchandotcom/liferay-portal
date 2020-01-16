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
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletJSONUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				fragmentEntryLink.getEditableValues());

			String portletId = jsonObject.getString("portletId");
			String instanceId = jsonObject.getString("instanceId");

			if (Validator.isNotNull(portletId)) {
				portletIds.add(PortletIdCodec.encode(portletId, instanceId));
			}
		}
		catch (PortalException portalException) {
			_log.error("Unable to get portlet IDs", portalException);
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

	@Override
	public void writePortletPaths(
			FragmentEntryLink fragmentEntryLink,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		long plid = fragmentEntryLink.getClassPK();

		if (fragmentEntryLink.getClassNameId() == _portal.getClassNameId(
				LayoutPageTemplateEntry.class)) {

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntry(
					fragmentEntryLink.getClassPK());

			plid = layoutPageTemplateEntry.getPlid();
		}

		List<PortletPreferences> portletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid);

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			Portlet portlet = _portletLocalService.getPortletById(
				fragmentEntryLink.getCompanyId(),
				portletPreferences.getPortletId());

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			try {
				PortletJSONUtil.populatePortletJSONObject(
					httpServletRequest, StringPool.BLANK, portlet, jsonObject);

				PortletJSONUtil.writeHeaderPaths(
					httpServletResponse, jsonObject);

				PortletJSONUtil.writeFooterPaths(
					httpServletResponse, jsonObject);
			}
			catch (Exception exception) {
				_log.error(
					"Unable to write portlet paths " + portlet.getPortletId(),
					exception);
			}
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		target = "(com.liferay.fragment.entry.processor.portlet.alias=*)"
	)
	protected void setPortlet(
		javax.portlet.Portlet jxPortlet, Map<String, Object> properties) {

		String alias = MapUtil.getString(
			properties, "com.liferay.fragment.entry.processor.portlet.alias");
		String portletName = MapUtil.getString(
			properties, "javax.portlet.name");

		_portletNames.put(alias, portletName);
	}

	protected void unsetPortlet(
		javax.portlet.Portlet jxPortlet, Map<String, Object> properties) {

		String alias = MapUtil.getString(
			properties, "com.liferay.fragment.entry.processor.portlet.alias");
		String portletName = MapUtil.getString(
			properties, "javax.portlet.name");

		_portletNames.remove(alias, portletName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletRegistryImpl.class);

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	private final Map<String, String> _portletNames = new ConcurrentHashMap<>();

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}
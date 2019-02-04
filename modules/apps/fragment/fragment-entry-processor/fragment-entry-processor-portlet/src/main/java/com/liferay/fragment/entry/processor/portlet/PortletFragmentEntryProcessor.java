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

package com.liferay.fragment.entry.processor.portlet;

import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelHintsConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.PortletPreferences;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true, property = "fragment.entry.processor.priority:Integer=3",
	service = FragmentEntryProcessor.class
)
public class PortletFragmentEntryProcessor implements FragmentEntryProcessor {

	@Override
	public JSONArray getAvailableTagsJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String alias : _portletRegistry.getPortletAliases()) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			StringBundler sb = new StringBundler(5);

			sb.append("<lfr-widget-");
			sb.append(alias);
			sb.append("></lfr-widget-");
			sb.append(alias);
			sb.append(">");

			jsonObject.put("content", sb.toString());

			jsonObject.put("name", "lfr-widget-" + alias);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html, String mode,
			Locale locale, List<Long> segmentsIds)
		throws PortalException {

		validateFragmentEntryHTML(html);

		Document document = _getDocument(html);

		for (Element element : document.select("*")) {
			String tagName = element.tagName();

			if (!StringUtil.startsWith(tagName, "lfr-widget-")) {
				continue;
			}

			String alias = StringUtil.replace(
				tagName, "lfr-widget-", StringPool.BLANK);

			String portletName = _portletRegistry.getPortletName(alias);

			if (Validator.isNull(portletName)) {
				throw new FragmentEntryContentException(
					LanguageUtil.format(
						_resourceBundle,
						"there-is-no-widget-available-for-alias-x", alias));
			}

			Element runtimeTagElement = new Element(
				"@liferay_portlet.runtime", true);

			FragmentEntryLink originalFragmentEntryLink =
				_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
					fragmentEntryLink.getOriginalFragmentEntryLinkId());

			String portletPreferences = StringPool.BLANK;

			String instanceId = _getInstanceId(
				fragmentEntryLink.getNamespace(), element.attr("id"));

			if (originalFragmentEntryLink != null) {
				String originalInstanceId = _getInstanceId(
					originalFragmentEntryLink.getNamespace(),
					element.attr("id"));

				String defaultPreferences = _getPreferences(
					portletName, originalFragmentEntryLink, originalInstanceId,
					StringPool.BLANK, true);

				portletPreferences = _getPreferences(
					portletName, fragmentEntryLink, instanceId,
					defaultPreferences, false);
			}
			else {
				Portlet portlet = _portletLocalService.getPortletById(
					portletName);

				portletPreferences = _getPreferences(
					portletName, fragmentEntryLink, instanceId,
					portlet.getDefaultPreferences(), false);
			}

			runtimeTagElement.attr("defaultPreferences", portletPreferences);

			runtimeTagElement.attr("instanceId", instanceId);
			runtimeTagElement.attr("persistSettings=false", true);
			runtimeTagElement.attr("portletName", portletName);

			Element portletElement = new Element("div");

			portletElement.attr("class", "portlet");

			portletElement.appendChild(runtimeTagElement);

			element.replaceWith(portletElement);
		}

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	@Override
	public void validateFragmentEntryHTML(String html) throws PortalException {
		Document document = _getDocument(html);

		for (Element element : document.select("*")) {
			String htmlTagName = element.tagName();

			if (!StringUtil.startsWith(htmlTagName, "lfr-widget-")) {
				continue;
			}

			String alias = StringUtil.replace(
				htmlTagName, "lfr-widget-", StringPool.BLANK);

			if (Validator.isNull(_portletRegistry.getPortletName(alias))) {
				throw new FragmentEntryContentException(
					LanguageUtil.format(
						_resourceBundle,
						"there-is-no-widget-available-for-alias-x", alias));
			}

			String id = element.id();

			if (Validator.isNotNull(id) && !Validator.isAlphanumericName(id)) {
				throw new FragmentEntryContentException(
					LanguageUtil.format(
						_resourceBundle,
						"widget-id-must-contain-only-alphanumeric-characters",
						alias));
			}

			if (Validator.isNotNull(id)) {
				Elements elements = document.select("#" + id);

				if (elements.size() > 1) {
					throw new FragmentEntryContentException(
						LanguageUtil.get(
							_resourceBundle, "widget-id-must-be-unique"));
				}

				if (id.length() > GetterUtil.getInteger(
						ModelHintsConstants.TEXT_MAX_LENGTH)) {

					throw new FragmentEntryContentException(
						LanguageUtil.format(
							_resourceBundle,
							"widget-id-cannot-exceed-x-characters",
							ModelHintsConstants.TEXT_MAX_LENGTH));
				}
			}

			Elements elements = document.select(htmlTagName);

			if ((elements.size() > 1) && Validator.isNull(id)) {
				throw new FragmentEntryContentException(
					LanguageUtil.get(
						_resourceBundle,
						"duplicate-widgets-within-the-same-fragment-must-" +
							"have-an-id"));
			}
		}
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

	private String _getInstanceId(String namespace, String id) {
		if (Validator.isNull(namespace)) {
			namespace = StringUtil.randomId();
		}

		return namespace + id;
	}

	private String _getPreferences(
			String portletName, FragmentEntryLink fragmentEntryLink,
			String instanceId, String defaultPreferences, boolean controlPanel)
		throws PortalException {

		long groupId = fragmentEntryLink.getGroupId();

		Layout layout = null;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			if (groupId == 0) {
				groupId = serviceContext.getScopeGroupId();
			}

			layout = _layoutLocalService.fetchLayout(serviceContext.getPlid());
		}

		Group group = _groupLocalService.getGroup(groupId);

		if ((layout == null) || controlPanel) {
			layout = _layoutLocalService.fetchLayout(
				_portal.getControlPanelPlid(group.getCompanyId()));
		}

		String portletId = PortletIdCodec.encode(
			PortletIdCodec.decodePortletName(portletName),
			PortletIdCodec.decodeUserId(portletName), instanceId);

		PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(defaultPreferences);

		List<com.liferay.portal.kernel.model.PortletPreferences>
			portletPreferencesList =
				_portletPreferencesLocalService.getPortletPreferences(
					group.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, portletId);

		if (ListUtil.isNotEmpty(portletPreferencesList)) {
			jxPortletPreferences =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					group.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
					portletId, defaultPreferences);

			_updateLayoutPortletSetup(
				portletPreferencesList, jxPortletPreferences);
		}

		Document preferencesDocument = _getDocument(
			PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

		Element preferencesBody = preferencesDocument.body();

		return preferencesBody.html();
	}

	private void _updateLayoutPortletSetup(
		List<com.liferay.portal.kernel.model.PortletPreferences>
			portletPreferencesList,
		PortletPreferences jxPortletPreferences) {

		String portletPreferencesXml = PortletPreferencesFactoryUtil.toXML(
			jxPortletPreferences);

		for (com.liferay.portal.kernel.model.PortletPreferences
				portletPreferences : portletPreferencesList) {

			if (Objects.equals(
					portletPreferences.getPreferences(),
					portletPreferencesXml)) {

				continue;
			}

			portletPreferences.setPreferences(portletPreferencesXml);

			_portletPreferencesLocalService.updatePortletPreferences(
				portletPreferences);
		}
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private PortletRegistry _portletRegistry;

	private final ResourceBundle _resourceBundle = ResourceBundleUtil.getBundle(
		"content.Language", getClass());

}
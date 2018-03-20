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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ResourceBundle;

import javax.portlet.PortletPreferences;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {"fragment.entry.processor.priority:Integer=3"},
	service = FragmentEntryProcessor.class
)
public class PortletFragmentEntryProcessor implements FragmentEntryProcessor {

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html)
		throws PortalException {

		Document document = _getDocument(html);

		for (Element element : document.select("*")) {
			String tagName = element.tagName();

			if (!StringUtil.startsWith(tagName, "lfr-app-")) {
				continue;
			}

			String alias = StringUtil.replace(
				tagName, "lfr-app-", StringPool.BLANK);

			String portletName = _portletRegistry.getPortletName(alias);

			if (Validator.isNull(portletName)) {
				throw new FragmentEntryContentException(
					LanguageUtil.format(
						_resourceBundle,
						"there-is-no-portlet-available-for-alias-x", alias));
			}

			Element runtimeTagElement = new Element("@liferay_portlet.runtime");

			FragmentEntryLink originalFragmentEntryLink =
				_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
					fragmentEntryLink.getOriginalFragmentEntryLinkId());

			String defaultPreferences = StringPool.BLANK;

			if (originalFragmentEntryLink != null) {
				String portletId = PortletIdCodec.encode(
					PortletIdCodec.decodePortletName(portletName),
					PortletIdCodec.decodeUserId(portletName),
					String.valueOf(
						originalFragmentEntryLink.getFragmentEntryLinkId()));

				Group group = _groupLocalService.getGroup(
					originalFragmentEntryLink.getGroupId());

				long defaultPlid = _portal.getControlPanelPlid(
					group.getCompanyId());

				PortletPreferences portletPreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						group.getCompanyId(), 0,
						PortletKeys.PREFS_OWNER_TYPE_LAYOUT, defaultPlid,
						portletId, StringPool.BLANK);

				defaultPreferences = PortletPreferencesFactoryUtil.toXML(
					portletPreferences);
			}

			String instanceId = String.valueOf(
				fragmentEntryLink.getFragmentEntryLinkId());

			runtimeTagElement.attr("defaultPreferences", defaultPreferences);
			runtimeTagElement.attr("instanceId", instanceId);
			runtimeTagElement.attr("persistSettings=false", true);
			runtimeTagElement.attr("portletName", portletName);

			element.replaceWith(runtimeTagElement);
		}

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	@Override
	public void validateFragmentEntryHTML(String html) throws PortalException {
		Document document = _getDocument(html);

		for (Element element : document.select("*")) {
			String htmlTagName = element.tagName();

			if (!StringUtil.startsWith(htmlTagName, "lfr-app-")) {
				continue;
			}

			String alias = StringUtil.replace(
				htmlTagName, "lfr-app-", StringPool.BLANK);

			if (Validator.isNull(_portletRegistry.getPortletName(alias))) {
				throw new FragmentEntryContentException(
					LanguageUtil.format(
						_resourceBundle,
						"there-is-no-portlet-available-for-alias-x", alias));
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

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletRegistry _portletRegistry;

	private final ResourceBundle _resourceBundle = ResourceBundleUtil.getBundle(
		"content.Language", getClass());

}
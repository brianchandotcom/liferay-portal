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
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.renderer.FragmentPortletRenderer;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelHintsConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.util.SegmentsExperiencePortletUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.ResourceBundle;
import java.util.stream.LongStream;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

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
	public void deleteFragmentEntryLinkData(
		FragmentEntryLink fragmentEntryLink) {

		Document document = _getDocument(fragmentEntryLink.getHtml());

		for (Element element : document.select("*")) {
			String tagName = element.tagName();

			if (!StringUtil.startsWith(tagName, "lfr-widget-")) {
				continue;
			}

			String alias = StringUtil.removeSubstring(tagName, "lfr-widget-");

			String portletName = _portletRegistry.getPortletName(alias);

			if (Validator.isNull(portletName)) {
				continue;
			}

			try {
				_portletPreferencesLocalService.deletePortletPreferences(
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
					_getPlid(fragmentEntryLink),
					_getPortletId(
						portletName, fragmentEntryLink.getNamespace(),
						element.attr("id"), new long[0]));
			}
			catch (Exception exception) {
			}
		}
	}

	@Override
	public JSONArray getAvailableTagsJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String alias : _portletRegistry.getPortletAliases()) {
			StringBundler sb = new StringBundler(5);

			sb.append("<lfr-widget-");
			sb.append(alias);
			sb.append("></lfr-widget-");
			sb.append(alias);
			sb.append(">");

			JSONObject jsonObject = JSONUtil.put(
				"content", sb.toString()
			).put(
				"name", "lfr-widget-" + alias
			);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		Document document = _getDocument(html);

		_validateFragmentEntryHTMLDocument(document);

		String editableValues = fragmentEntryLink.getEditableValues();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			editableValues);

		if (Validator.isNotNull(jsonObject.getString("portletId"))) {
			return _renderWidgetHTML(
				editableValues, fragmentEntryProcessorContext);
		}

		FragmentEntryLink originalFragmentEntryLink = null;
		OptionalLong segmentsExperienceIdOptionalLong = null;

		for (Element element : document.select("*")) {
			String tagName = element.tagName();

			if (!StringUtil.startsWith(tagName, "lfr-widget-")) {
				continue;
			}

			String alias = tagName.substring(11);

			String portletName = _portletRegistry.getPortletName(alias);

			if (Validator.isNull(portletName)) {
				throw new FragmentEntryContentException(
					LanguageUtil.format(
						_resourceBundle,
						"there-is-no-widget-available-for-alias-x", alias));
			}

			if ((originalFragmentEntryLink == null) &&
				(fragmentEntryLink.getOriginalFragmentEntryLinkId() > 0)) {

				originalFragmentEntryLink =
					_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
						fragmentEntryLink.getOriginalFragmentEntryLinkId());
			}

			String id = element.attr("id");

			String instanceId = _getInstanceId(
				fragmentEntryLink.getNamespace(), id);

			if (segmentsExperienceIdOptionalLong == null) {
				segmentsExperienceIdOptionalLong =
					_getSegmentsExperienceIdOptional(
						fragmentEntryProcessorContext.
							getSegmentsExperienceIds());
			}

			if (segmentsExperienceIdOptionalLong.isPresent()) {
				instanceId =
					SegmentsExperiencePortletUtil.setSegmentsExperienceId(
						instanceId,
						segmentsExperienceIdOptionalLong.getAsLong());
			}

			String defaultPreferences = StringPool.BLANK;

			if (originalFragmentEntryLink != null) {
				defaultPreferences = _getPreferences(
					portletName, originalFragmentEntryLink, id,
					StringPool.BLANK, fragmentEntryProcessorContext);
			}
			else {
				Portlet portlet = _portletLocalService.getPortletById(
					portletName);

				defaultPreferences = portlet.getDefaultPreferences();
			}

			String portletHTML = _fragmentPortletRenderer.renderPortlet(
				fragmentEntryProcessorContext.getHttpServletRequest(),
				fragmentEntryProcessorContext.getHttpServletResponse(),
				portletName, instanceId,
				_getPreferences(
					portletName, fragmentEntryLink, id, defaultPreferences,
					fragmentEntryProcessorContext));

			Element portletElement = new Element("div");

			portletElement.attr("class", "portlet");

			portletElement.html(portletHTML);

			element.replaceWith(portletElement);
		}

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	@Override
	public void validateFragmentEntryHTML(String html, String configuration)
		throws PortalException {

		Document document = _getDocument(html);

		_validateFragmentEntryHTMLDocument(document);
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

	private long _getPlid(FragmentEntryLink fragmentEntryLink) {
		if (fragmentEntryLink.getClassNameId() == _portal.getClassNameId(
				Layout.class)) {

			return fragmentEntryLink.getClassPK();
		}

		if (fragmentEntryLink.getClassNameId() == _portal.getClassNameId(
				LayoutPageTemplateEntry.class)) {

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(
						fragmentEntryLink.getClassPK());

			if (layoutPageTemplateEntry != null) {
				return layoutPageTemplateEntry.getPlid();
			}
		}

		return PortletKeys.PREFS_PLID_SHARED;
	}

	private String _getPortletId(
		String portletName, String namespace, String id,
		long[] segmentsExperienceIds) {

		String instanceId = _getInstanceId(namespace, id);

		if (ArrayUtil.isNotEmpty(segmentsExperienceIds)) {
			OptionalLong segmentsExperienceIdOptionalLong =
				_getSegmentsExperienceIdOptional(segmentsExperienceIds);

			if (segmentsExperienceIdOptionalLong.isPresent()) {
				instanceId =
					SegmentsExperiencePortletUtil.setSegmentsExperienceId(
						instanceId,
						segmentsExperienceIdOptionalLong.getAsLong());
			}
		}

		return PortletIdCodec.encode(
			PortletIdCodec.decodePortletName(portletName),
			PortletIdCodec.decodeUserId(portletName), instanceId);
	}

	private String _getPreferences(
			String portletName, FragmentEntryLink fragmentEntryLink, String id,
			String defaultPreferences,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		long plid = _getPlid(fragmentEntryLink);
		String defaultPortletId = _getPortletId(
			portletName, fragmentEntryLink.getNamespace(), id, new long[0]);

		PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				fragmentEntryLink.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, defaultPortletId,
				defaultPreferences);

		String portletId = _getPortletId(
			portletName, fragmentEntryLink.getNamespace(), id,
			fragmentEntryProcessorContext.getSegmentsExperienceIds());

		List<com.liferay.portal.kernel.model.PortletPreferences>
			portletPreferencesList =
				_portletPreferencesLocalService.getPortletPreferences(
					fragmentEntryLink.getCompanyId(),
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, portletId);

		if (ListUtil.isNotEmpty(portletPreferencesList)) {
			jxPortletPreferences =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					fragmentEntryLink.getCompanyId(),
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId,
					PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

			_updateLayoutPortletSetup(
				portletPreferencesList, jxPortletPreferences);
		}

		Document preferencesDocument = _getDocument(
			PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

		Element preferencesBody = preferencesDocument.body();

		return preferencesBody.html();
	}

	private OptionalLong _getSegmentsExperienceIdOptional(
		long[] segmentsExperienceIds) {

		if (segmentsExperienceIds.length == 1) {
			return OptionalLong.of(segmentsExperienceIds[0]);
		}

		LongStream longStream = Arrays.stream(segmentsExperienceIds);

		return longStream.mapToObj(
			segmentsExperienceId ->
				_segmentsExperienceLocalService.fetchSegmentsExperience(
					segmentsExperienceId)
		).filter(
			segmentsExperience -> segmentsExperience != null
		).sorted(
			Comparator.comparingInt(
				SegmentsExperience::getPriority
			).reversed()
		).mapToLong(
			SegmentsExperience::getSegmentsExperienceId
		).findFirst();
	}

	private String _renderWidgetHTML(
			String editableValues,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			editableValues);

		String portletId = jsonObject.getString("portletId");

		if (Validator.isNull(portletId)) {
			return StringPool.BLANK;
		}

		String instanceId = jsonObject.getString("instanceId");
		Portlet portlet = _portletLocalService.getPortletById(
			SegmentsExperiencePortletUtil.decodePortletName(portletId));
		PortletPreferences portletPreferences = null;

		OptionalLong segmentsExperienceIdOptionalLong =
			_getSegmentsExperienceIdOptional(
				fragmentEntryProcessorContext.getSegmentsExperienceIds());

		if (segmentsExperienceIdOptionalLong.isPresent()) {
			String preferencesPortletId = portletId;

			String defaultPreferencesPortletId = portletId;

			if (!portlet.isInstanceable()) {
				instanceId = String.valueOf(CharPool.NUMBER_0);
			}
			else {
				defaultPreferencesPortletId = PortletIdCodec.encode(
					portletId,
					SegmentsExperiencePortletUtil.setSegmentsExperienceId(
						instanceId, SegmentsExperienceConstants.ID_DEFAULT));
			}

			instanceId = SegmentsExperiencePortletUtil.setSegmentsExperienceId(
				instanceId, segmentsExperienceIdOptionalLong.getAsLong());

			preferencesPortletId = PortletIdCodec.encode(portletId, instanceId);

			HttpServletRequest httpServletRequest =
				fragmentEntryProcessorContext.getHttpServletRequest();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			PortletPreferences defaultExperiencePortletPreferences =
				_portletPreferencesLocalService.fetchPreferences(
					themeDisplay.getCompanyId(),
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, themeDisplay.getPlid(),
					defaultPreferencesPortletId);

			if (defaultExperiencePortletPreferences == null) {
				defaultExperiencePortletPreferences =
					PortletPreferencesFactoryUtil.fromDefaultXML(
						portlet.getDefaultPreferences());
			}

			portletPreferences = PortletPreferencesFactoryUtil.getPortletSetup(
				fragmentEntryProcessorContext.getHttpServletRequest(),
				preferencesPortletId,
				PortletPreferencesFactoryUtil.toXML(
					defaultExperiencePortletPreferences));
		}
		else {
			portletPreferences =
				PortletPreferencesFactoryUtil.getPortletPreferences(
					fragmentEntryProcessorContext.getHttpServletRequest(),
					PortletIdCodec.encode(portletId, instanceId));
		}

		return _fragmentPortletRenderer.renderPortlet(
			fragmentEntryProcessorContext.getHttpServletRequest(),
			fragmentEntryProcessorContext.getHttpServletResponse(), portletId,
			instanceId,
			PortletPreferencesFactoryUtil.toXML(portletPreferences));
	}

	private void _updateLayoutPortletSetup(
		List<com.liferay.portal.kernel.model.PortletPreferences>
			portletPreferencesList,
		PortletPreferences jxPortletPreferences) {

		String portletPreferencesXml = PortletPreferencesFactoryUtil.toXML(
			jxPortletPreferences);

		long plid = 0L;

		if (jxPortletPreferences instanceof PortletPreferencesImpl) {
			PortletPreferencesImpl portletPreferencesImpl =
				(PortletPreferencesImpl)jxPortletPreferences;

			plid = portletPreferencesImpl.getPlid();
		}

		for (com.liferay.portal.kernel.model.PortletPreferences
				portletPreferencesImpl : portletPreferencesList) {

			if ((plid != portletPreferencesImpl.getPlid()) ||
				Objects.equals(
					portletPreferencesImpl.getPreferences(),
					portletPreferencesXml)) {

				continue;
			}

			portletPreferencesImpl.setPreferences(portletPreferencesXml);

			_portletPreferencesLocalService.updatePortletPreferences(
				portletPreferencesImpl);
		}
	}

	private void _validateFragmentEntryHTMLDocument(Document document)
		throws PortalException {

		for (Element element : document.select("*")) {
			String htmlTagName = element.tagName();

			if (!StringUtil.startsWith(htmlTagName, "lfr-widget-")) {
				continue;
			}

			String alias = StringUtil.removeSubstring(
				htmlTagName, "lfr-widget-");

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

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentPortletRenderer _fragmentPortletRenderer;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

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

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}
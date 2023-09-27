/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.portlet;

import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DocumentFragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.renderer.FragmentPortletRenderer;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.constants.LayoutWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.ModelHintsConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "fragment.entry.processor.priority:Integer=3",
	service = DocumentFragmentEntryProcessor.class
)
public class PortletDocumentFragmentEntryProcessor
	implements DocumentFragmentEntryProcessor {

	@Override
	public void processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, Document document,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		if (fragmentEntryLink.isTypePortlet()) {
			return;
		}

		_validateFragmentEntryHTMLDocument(
			document, fragmentEntryProcessorContext.getLocale());

		Set<String> processedPortletIds = new HashSet<>();

		for (Element element : document.select("*")) {
			String tagName = element.tagName();

			String portletName = _getPortletName(tagName);

			if (Validator.isNull(portletName)) {
				continue;
			}

			Portlet portlet = _portletLocalService.getPortletById(portletName);

			String instanceId = String.valueOf(CharPool.NUMBER_0);

			String id = element.attr("id");

			if (portlet.isInstanceable()) {
				instanceId = _getInstanceId(
					fragmentEntryLink.getNamespace(), id);
			}
			else if (processedPortletIds.contains(portletName) ||
					 _checkNoninstanceablePortletUsed(
						 fragmentEntryLink, portletName,
						 fragmentEntryProcessorContext.
							 getHttpServletRequest())) {

				throw new FragmentEntryContentException(
					_language.get(
						fragmentEntryProcessorContext.getLocale(),
						"noninstanceable-widgets-can-be-embedded-only-once-" +
							"on-the-same-page"));
			}

			long plid = ParamUtil.getLong(
				fragmentEntryProcessorContext.getHttpServletRequest(),
				"p_l_id");

			String defaultPreferences = portlet.getDefaultPreferences();

			boolean stagingAdvicesThreadLocalEnabled =
				StagingAdvicesThreadLocal.isEnabled();

			try {
				StagingAdvicesThreadLocal.setEnabled(false);

				defaultPreferences = _getPreferences(
					plid, portletName, fragmentEntryLink, id,
					portlet.getDefaultPreferences());
			}
			finally {
				StagingAdvicesThreadLocal.setEnabled(
					stagingAdvicesThreadLocalEnabled);
			}

			String portletHTML = _fragmentPortletRenderer.renderPortlet(
				fragmentEntryProcessorContext.getHttpServletRequest(),
				fragmentEntryProcessorContext.getHttpServletResponse(),
				portletName, instanceId,
				_getPreferences(
					plid, portletName, fragmentEntryLink, id,
					defaultPreferences));

			Element portletElement = new Element("div");

			portletElement.attr("class", "portlet");

			portletElement.html(portletHTML);

			element.replaceWith(portletElement);

			processedPortletIds.add(portletName);
		}
	}

	private boolean _checkNoninstanceablePortletUsed(
			FragmentEntryLink currentFragmentEntryLink,
			String currentPortletName, HttpServletRequest httpServletRequest)
		throws PortalException {

		if ((currentFragmentEntryLink.getFragmentEntryLinkId() <= 0) ||
			(currentFragmentEntryLink.getPlid() <= 0)) {

			return false;
		}

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.
				getFragmentEntryLinksBySegmentsExperienceId(
					currentFragmentEntryLink.getGroupId(),
					currentFragmentEntryLink.getSegmentsExperienceId(),
					currentFragmentEntryLink.getPlid());

		LayoutStructure layoutStructure = null;

		if (httpServletRequest != null) {
			layoutStructure = (LayoutStructure)httpServletRequest.getAttribute(
				LayoutWebKeys.LAYOUT_STRUCTURE);
		}

		if (layoutStructure == null) {
			LayoutPageTemplateStructure layoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						currentFragmentEntryLink.getGroupId(),
						currentFragmentEntryLink.getPlid());

			layoutStructure = LayoutStructure.of(
				layoutPageTemplateStructure.getData(
					currentFragmentEntryLink.getSegmentsExperienceId()));
		}

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			if (currentFragmentEntryLink.getFragmentEntryLinkId() ==
					fragmentEntryLink.getFragmentEntryLinkId()) {

				continue;
			}

			Set<String> portletNames = new HashSet<>();

			for (String portletId :
					_portletRegistry.getFragmentEntryLinkPortletIds(
						fragmentEntryLink)) {

				portletNames.add(PortletIdCodec.decodePortletName(portletId));
			}

			if (!portletNames.contains(currentPortletName)) {
				continue;
			}

			LayoutStructureItem layoutStructureItem =
				layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
					fragmentEntryLink.getFragmentEntryLinkId());

			if (!layoutStructure.isItemMarkedForDeletion(
					layoutStructureItem.getItemId())) {

				return true;
			}
		}

		return false;
	}

	private boolean _comparePreferences(
		PortletPreferences currentPortletPreferences,
		PortletPreferences sourcePortletPreferences) {

		Map<String, String[]> currentPreferences =
			currentPortletPreferences.getMap();

		Map<String, String[]> sourcePreferences =
			sourcePortletPreferences.getMap();

		if (currentPreferences.size() != sourcePreferences.size()) {
			return false;
		}

		for (Map.Entry<String, String[]> entry :
				currentPreferences.entrySet()) {

			if (!Arrays.equals(
					sourcePreferences.get(entry.getKey()), entry.getValue())) {

				return false;
			}
		}

		return true;
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

	private String _getPortletId(
		String portletName, String namespace, String id) {

		return PortletIdCodec.encode(
			PortletIdCodec.decodePortletName(portletName),
			PortletIdCodec.decodeUserId(portletName),
			_getInstanceId(namespace, id));
	}

	private String _getPortletName(String tagName) {
		if (!StringUtil.startsWith(tagName, "lfr-widget-")) {
			return StringPool.BLANK;
		}

		String alias = tagName.substring(11);

		return _portletRegistry.getPortletName(alias);
	}

	private String _getPreferences(
			long plid, String portletName, FragmentEntryLink fragmentEntryLink,
			String id, String defaultPreferences)
		throws PortalException {

		String defaultPortletId = _getPortletId(
			portletName, fragmentEntryLink.getNamespace(), id);

		PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				fragmentEntryLink.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				fragmentEntryLink.getPlid(), defaultPortletId,
				defaultPreferences);

		String portletId = _getPortletId(
			portletName, fragmentEntryLink.getNamespace(), id);

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
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
					fragmentEntryLink.getPlid(), portletId,
					PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

			_updateLayoutPortletSetup(
				plid, portletPreferencesList, jxPortletPreferences);
		}

		Document preferencesDocument = _getDocument(
			PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

		Element preferencesBody = preferencesDocument.body();

		return preferencesBody.html();
	}

	private void _updateLayoutPortletSetup(
		long layoutPlid,
		List<com.liferay.portal.kernel.model.PortletPreferences>
			portletPreferencesList,
		PortletPreferences jxPortletPreferences) {

		long plid = 0;

		if (jxPortletPreferences instanceof PortletPreferencesImpl) {
			plid = layoutPlid;
		}

		for (com.liferay.portal.kernel.model.PortletPreferences
				portletPreferencesImpl : portletPreferencesList) {

			PortletPreferences currentPortletPreferences =
				_portletPreferenceValueLocalService.getPreferences(
					portletPreferencesImpl);

			if ((plid != portletPreferencesImpl.getPlid()) ||
				_comparePreferences(
					currentPortletPreferences, jxPortletPreferences)) {

				continue;
			}

			_portletPreferencesLocalService.updatePreferences(
				portletPreferencesImpl.getOwnerId(),
				portletPreferencesImpl.getOwnerType(),
				portletPreferencesImpl.getPlid(),
				portletPreferencesImpl.getPortletId(), jxPortletPreferences);
		}
	}

	private void _validateFragmentEntryHTMLDocument(
			Document document, Locale locale)
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
					_language.format(
						locale, "there-is-no-widget-available-for-alias-x",
						alias));
			}

			String id = element.id();

			if (Validator.isNotNull(id) && !Validator.isAlphanumericName(id)) {
				throw new FragmentEntryContentException(
					_language.format(
						locale,
						"widget-id-must-contain-only-alphanumeric-characters",
						alias));
			}

			if (Validator.isNotNull(id)) {
				Elements elements = document.select("#" + id);

				if (elements.size() > 1) {
					throw new FragmentEntryContentException(
						_language.get(locale, "widget-id-must-be-unique"));
				}

				if (id.length() > GetterUtil.getInteger(
						ModelHintsConstants.TEXT_MAX_LENGTH)) {

					throw new FragmentEntryContentException(
						_language.format(
							locale, "widget-id-cannot-exceed-x-characters",
							ModelHintsConstants.TEXT_MAX_LENGTH));
				}
			}

			Elements elements = document.select(htmlTagName);

			if ((elements.size() > 1) && Validator.isNull(id)) {
				throw new FragmentEntryContentException(
					_language.get(
						locale,
						"duplicate-widgets-within-the-same-fragment-must-" +
							"have-an-id"));
			}

			if (elements.size() > 1) {
				Portlet portlet = _portletLocalService.getPortletById(
					_portletRegistry.getPortletName(alias));

				if (!portlet.isInstanceable()) {
					throw new FragmentEntryContentException(
						_language.format(
							locale,
							"you-cannot-add-the-widget-x-more-than-once",
							alias));
				}
			}
		}
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentPortletRenderer _fragmentPortletRenderer;

	@Reference
	private Language _language;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private PortletPreferenceValueLocalService
		_portletPreferenceValueLocalService;

	@Reference
	private PortletRegistry _portletRegistry;

}
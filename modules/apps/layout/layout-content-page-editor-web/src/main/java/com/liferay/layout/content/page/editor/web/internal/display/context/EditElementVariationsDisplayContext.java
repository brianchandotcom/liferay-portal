/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.display.context;

import com.liferay.audiences.service.AudiencesEntryService;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelElementVariationService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.service.SegmentsExperienceService;

import jakarta.portlet.PortletResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Víctor Galán
 */
public class EditElementVariationsDisplayContext {

	public EditElementVariationsDisplayContext(
		AudiencesEntryService audiencesEntryService,
		HttpServletRequest httpServletRequest,
		LayoutPageTemplateStructureRelElementVariationService
			layoutPageTemplateStructureRelElementVariationService,
		Portal portal, SegmentsExperienceService segmentsExperienceService) {

		_audiencesEntryService = audiencesEntryService;
		_httpServletRequest = httpServletRequest;
		_layoutPageTemplateStructureRelElementVariationService =
			layoutPageTemplateStructureRelElementVariationService;
		_portal = portal;
		_segmentsExperienceService = segmentsExperienceService;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getData() {
		return HashMapBuilder.<String, Object>put(
			"addElementVariationURL",
			_getActionURL(
				"/layout_content_page_editor" +
					"/save_layout_page_template_structure_rel_element" +
						"_variation")
		).put(
			"audiences", _getAudiencesEntries()
		).put(
			"deleteElementVariationURL",
			_getActionURL(
				"/layout_content_page_editor" +
					"/delete_layout_page_template_structure_rel_element" +
						"_variation")
		).put(
			"elementVariations",
			_getLayoutPageTemplateStructureRelElementVariations()
		).put(
			"experiences", _getSegmentsExperiences()
		).put(
			"languageId", _themeDisplay.getLanguageId()
		).put(
			"plid", _getPlid()
		).put(
			"portletNamespace",
			_portal.getPortletNamespace(
				ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET)
		).put(
			"redirect", getRedirect()
		).put(
			"selectedSegmentsExperienceId", _getSegmentsExperienceId()
		).build();
	}

	public String getRedirect() {
		if (Validator.isNotNull(_redirect)) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	private String _getActionURL(String actionName) {
		return PortletURLBuilder.createActionURL(
			_portal.getLiferayPortletResponse(
				(PortletResponse)_httpServletRequest.getAttribute(
					JavaConstants.JAKARTA_PORTLET_RESPONSE)),
			ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET
		).setActionName(
			actionName
		).buildString();
	}

	private List<Map<String, Object>> _getAudiencesEntries() {
		try {
			return TransformUtil.transform(
				_audiencesEntryService.getAudiencesEntries(
					_themeDisplay.getCompanyId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null),
				audiencesEntry -> HashMapBuilder.<String, Object>put(
					"label", audiencesEntry.getName()
				).put(
					"value", audiencesEntry.getExternalReferenceCode()
				).build());
		}
		catch (Exception exception) {
			_log.error(exception);

			return Collections.emptyList();
		}
	}

	private List<Map<String, Object>>
		_getLayoutPageTemplateStructureRelElementVariations() {

		try {
			Locale locale = _themeDisplay.getLocale();

			return TransformUtil.transform(
				_layoutPageTemplateStructureRelElementVariationService.
					getLayoutPageTemplateStructureRelElementVariations(
						_getPlid()),
				layoutPageTemplateStructureRelElementVariation ->
					HashMapBuilder.<String, Object>put(
						"audienceEntryERC",
						layoutPageTemplateStructureRelElementVariation.
							getAudienceEntryERC()
					).put(
						"externalReferenceCode",
						layoutPageTemplateStructureRelElementVariation.
							getExternalReferenceCode()
					).put(
						"hide",
						GetterUtil.getBoolean(
							layoutPageTemplateStructureRelElementVariation.
								getHide(locale))
					).put(
						"html",
						layoutPageTemplateStructureRelElementVariation.getHtml(
							locale)
					).put(
						"js",
						layoutPageTemplateStructureRelElementVariation.getJs(
							locale)
					).put(
						"name",
						layoutPageTemplateStructureRelElementVariation.getName()
					).put(
						"segmentsExperienceERC",
						layoutPageTemplateStructureRelElementVariation.
							getSegmentsExperienceERC()
					).put(
						"targetElement",
						layoutPageTemplateStructureRelElementVariation.
							getTargetElement()
					).build());
		}
		catch (Exception exception) {
			_log.error(exception);

			return Collections.emptyList();
		}
	}

	private long _getPlid() {
		if (_plid != null) {
			return _plid;
		}

		_plid = ParamUtil.getLong(_httpServletRequest, "plid");

		return _plid;
	}

	private long _getSegmentsExperienceId() {
		if (_segmentsExperienceId != null) {
			return _segmentsExperienceId;
		}

		_segmentsExperienceId = ParamUtil.getLong(
			_httpServletRequest, "segmentsExperienceId");

		return _segmentsExperienceId;
	}

	private List<Map<String, Object>> _getSegmentsExperiences() {
		try {
			return TransformUtil.transform(
				_segmentsExperienceService.getSegmentsExperiences(
					_themeDisplay.getScopeGroupId(), _getPlid(), true),
				segmentsExperience -> HashMapBuilder.<String, Object>put(
					"label",
					segmentsExperience.getName(_themeDisplay.getLocale())
				).put(
					"segmentsExperienceERC",
					segmentsExperience.getExternalReferenceCode()
				).put(
					"segmentsExperienceId",
					segmentsExperience.getSegmentsExperienceId()
				).build());
		}
		catch (Exception exception) {
			_log.error(exception);

			return Collections.emptyList();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditElementVariationsDisplayContext.class);

	private final AudiencesEntryService _audiencesEntryService;
	private final HttpServletRequest _httpServletRequest;
	private final LayoutPageTemplateStructureRelElementVariationService
		_layoutPageTemplateStructureRelElementVariationService;
	private Long _plid;
	private final Portal _portal;
	private String _redirect;
	private Long _segmentsExperienceId;
	private final SegmentsExperienceService _segmentsExperienceService;
	private final ThemeDisplay _themeDisplay;

}
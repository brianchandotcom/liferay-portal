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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.renderer.constants.FragmentRendererConstants;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.content.page.editor.listener.ContentPageEditorListener;
import com.liferay.layout.content.page.editor.listener.ContentPageEditorListenerTracker;
import com.liferay.layout.content.page.editor.web.internal.comment.CommentUtil;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.layout.util.structure.LayoutStructureItemCSSUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = FragmentEntryLinkManager.class)
public class FragmentEntryLinkManager {

	public void deleteFragmentEntryLink(
			ContentPageEditorListenerTracker contentPageEditorListenerTracker,
			long fragmentEntryLinkId, long plid)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLinkId);

		if (fragmentEntryLink == null) {
			_layoutClassedModelUsageLocalService.deleteLayoutClassedModelUsages(
				String.valueOf(fragmentEntryLinkId),
				_portal.getClassNameId(FragmentEntryLink.class), plid);

			return;
		}

		_fragmentEntryLinkLocalService.deleteFragmentEntryLink(
			fragmentEntryLinkId);

		_layoutClassedModelUsageLocalService.deleteLayoutClassedModelUsages(
			String.valueOf(fragmentEntryLinkId),
			_portal.getClassNameId(FragmentEntryLink.class), plid);

		List<ContentPageEditorListener> contentPageEditorListeners =
			contentPageEditorListenerTracker.getContentPageEditorListeners();

		for (ContentPageEditorListener contentPageEditorListener :
				contentPageEditorListeners) {

			contentPageEditorListener.onDeleteFragmentEntryLink(
				fragmentEntryLink);
		}
	}

	public FragmentEntry getFragmentEntry(
		long groupId,
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		String fragmentEntryKey, Locale locale) {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				groupId, fragmentEntryKey);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		Map<String, FragmentEntry> fragmentEntries =
			fragmentCollectionContributorTracker.getFragmentEntries(locale);

		return fragmentEntries.get(fragmentEntryKey);
	}

	public JSONObject getFragmentEntryLinkJSONObject(
			DefaultFragmentRendererContext defaultFragmentRendererContext,
			FragmentEntryLink fragmentEntryLink,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String portletId)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		boolean isolated = themeDisplay.isIsolated();

		themeDisplay.setIsolated(true);

		try {
			String languageId = ParamUtil.getString(
				httpServletRequest, "languageId", themeDisplay.getLanguageId());

			defaultFragmentRendererContext.setLocale(
				LocaleUtil.fromLanguageId(languageId));

			defaultFragmentRendererContext.setMode(
				FragmentEntryLinkConstants.EDIT);

			String configuration = _fragmentRendererController.getConfiguration(
				defaultFragmentRendererContext);

			FragmentEntry fragmentEntry = _getFragmentEntry(
				fragmentEntryLink, themeDisplay.getLocale());

			String fragmentEntryKey = null;
			String name = null;

			if (fragmentEntry != null) {
				fragmentEntryKey = fragmentEntry.getFragmentEntryKey();
				name = fragmentEntry.getName();
			}
			else {
				String rendererKey = fragmentEntryLink.getRendererKey();

				if (Validator.isNull(rendererKey)) {
					rendererKey =
						FragmentRendererConstants.
							FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY;
				}

				FragmentRenderer fragmentRenderer =
					_fragmentRendererTracker.getFragmentRenderer(rendererKey);

				if (fragmentRenderer != null) {
					fragmentEntryKey = fragmentRenderer.getKey();

					name = fragmentRenderer.getLabel(themeDisplay.getLocale());
				}

				if (Validator.isNotNull(portletId)) {
					name = _portal.getPortletTitle(
						portletId, themeDisplay.getLocale());
				}
			}

			JSONObject configurationJSONObject =
				JSONFactoryUtil.createJSONObject(configuration);

			FragmentEntryLinkItemSelectorUtil.
				addFragmentEntryLinkFieldsSelectorURL(
					_itemSelector, httpServletRequest, configurationJSONObject);

			String content = _fragmentRendererController.render(
				defaultFragmentRendererContext, httpServletRequest,
				httpServletResponse);

			JSONObject jsonObject = JSONUtil.put(
				"comments",
				_getFragmentEntryLinkCommentsJSONArray(
					fragmentEntryLink, httpServletRequest)
			).put(
				"configuration", configurationJSONObject
			).put(
				"content", content
			).put(
				"cssClass",
				LayoutStructureItemCSSUtil.getFragmentEntryLinkCssClass(
					fragmentEntryLink)
			).put(
				"defaultConfigurationValues",
				_fragmentEntryConfigurationParser.
					getConfigurationDefaultValuesJSONObject(configuration)
			).put(
				"editableTypes",
				EditableFragmentEntryProcessorUtil.getEditableTypes(content)
			).put(
				"editableValues",
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues())
			).put(
				"error",
				() -> {
					if (SessionErrors.contains(
							httpServletRequest,
							"fragmentEntryContentInvalid")) {

						SessionErrors.clear(httpServletRequest);

						return true;
					}

					return false;
				}
			).put(
				"fragmentEntryId",
				() -> {
					if (fragmentEntry != null) {
						return fragmentEntry.getFragmentEntryId();
					}

					return 0;
				}
			).put(
				"fragmentEntryKey", fragmentEntryKey
			).put(
				"fragmentEntryLinkId",
				String.valueOf(fragmentEntryLink.getFragmentEntryLinkId())
			).put(
				"fragmentEntryType",
				() -> {
					int fragmentEntryType = FragmentConstants.TYPE_COMPONENT;

					if (fragmentEntry != null) {
						fragmentEntryType = fragmentEntry.getType();
					}

					return FragmentConstants.getTypeLabel(fragmentEntryType);
				}
			).put(
				"icon",
				() -> {
					if (fragmentEntry != null) {
						return fragmentEntry.getIcon();
					}

					return null;
				}
			).put(
				"name", name
			).put(
				"portletId", portletId
			).put(
				"segmentsExperienceId",
				String.valueOf(fragmentEntryLink.getSegmentsExperienceId())
			);

			if (Validator.isNotNull(portletId)) {
				jsonObject.put("portletId", portletId);
			}
			else {
				portletId = _getPortletId(jsonObject.getString("content"));

				PortletConfig portletConfig = PortletConfigFactoryUtil.get(
					portletId);

				if (portletConfig != null) {
					jsonObject.put(
						"name",
						_portal.getPortletTitle(
							portletId, themeDisplay.getLocale())
					).put(
						"portletId", portletId
					);
				}
			}

			return jsonObject;
		}
		finally {
			themeDisplay.setIsolated(isolated);
		}
	}

	public JSONObject getFragmentEntryLinkJSONObject(
			FragmentEntryLink fragmentEntryLink,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String portletId)
		throws PortalException {

		return getFragmentEntryLinkJSONObject(
			new DefaultFragmentRendererContext(fragmentEntryLink),
			fragmentEntryLink, httpServletRequest, httpServletResponse,
			portletId);
	}

	private FragmentEntry _getFragmentEntry(
		FragmentEntryLink fragmentEntryLink, Locale locale) {

		if (fragmentEntryLink.getFragmentEntryId() <= 0) {
			return getFragmentEntry(
				fragmentEntryLink.getGroupId(),
				_fragmentCollectionContributorTracker,
				fragmentEntryLink.getRendererKey(), locale);
		}

		return _fragmentEntryLocalService.fetchFragmentEntry(
			fragmentEntryLink.getFragmentEntryId());
	}

	private JSONArray _getFragmentEntryLinkCommentsJSONArray(
		FragmentEntryLink fragmentEntryLink,
		HttpServletRequest httpServletRequest) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		try {
			if (!_commentManager.hasDiscussion(
					FragmentEntryLink.class.getName(),
					fragmentEntryLink.getFragmentEntryLinkId())) {

				return jsonArray;
			}

			List<Comment> rootComments = _commentManager.getRootComments(
				FragmentEntryLink.class.getName(),
				fragmentEntryLink.getFragmentEntryLinkId(),
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			for (Comment rootComment : rootComments) {
				JSONObject commentJSONObject = CommentUtil.getCommentJSONObject(
					rootComment, httpServletRequest);

				List<Comment> childComments = _commentManager.getChildComments(
					rootComment.getCommentId(),
					WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

				JSONArray childCommentsJSONArray =
					JSONFactoryUtil.createJSONArray();

				for (Comment childComment : childComments) {
					childCommentsJSONArray.put(
						CommentUtil.getCommentJSONObject(
							childComment, httpServletRequest));
				}

				commentJSONObject.put("children", childCommentsJSONArray);

				jsonArray.put(commentJSONObject);
			}
		}
		catch (PortalException portalException) {
			return jsonArray;
		}

		return jsonArray;
	}

	private String _getPortletId(String content) {
		Document document = Jsoup.parse(content);

		Elements elements = document.getElementsByAttributeValueStarting(
			"id", "portlet_");

		if (elements.size() != 1) {
			return StringPool.BLANK;
		}

		Element element = elements.get(0);

		String id = element.id();

		return PortletIdCodec.decodePortletName(id.substring(8));
	}

	@Reference
	private CommentManager _commentManager;

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private Portal _portal;

}
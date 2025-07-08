/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.cms.site.initializer.internal.util.CommentUtil;
import com.liferay.subscription.service.SubscriptionLocalService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sandro Chinea
 */
@Component(service = FragmentRenderer.class)
public class ContentEditorSidePanelComponentSectionFragmentRenderer
	extends BaseComponentSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "content-editor";
	}

	@Override
	protected String getLabelKey() {
		return "content-editor-side-panel";
	}

	@Override
	protected String getModuleName() {
		return "ContentEditorSidePanel";
	}

	@Override
	protected Map<String, Object> getProps(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest) {

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			(LayoutDisplayPageObjectProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

		if (layoutDisplayPageObjectProvider == null) {
			return Collections.emptyMap();
		}

		Object displayObject =
			layoutDisplayPageObjectProvider.getDisplayObject();

		if (!(displayObject instanceof ObjectEntry)) {
			return Collections.emptyMap();
		}

		ObjectEntry objectEntry = (ObjectEntry)displayObject;

		return HashMapBuilder.<String, Object>put(
			"comments",
			() -> {
				JSONArray jsonArray = _jsonFactory.createJSONArray();

				if (!_commentManager.hasDiscussion(
						objectEntry.getModelClassName(),
						objectEntry.getObjectEntryId())) {

					return jsonArray;
				}

				List<Comment> rootComments = _commentManager.getRootComments(
					objectEntry.getModelClassName(),
					objectEntry.getObjectEntryId(),
					WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

				for (Comment rootComment : rootComments) {
					JSONObject commentJSONObject =
						CommentUtil.getCommentJSONObject(
							rootComment, httpServletRequest);

					List<Comment> childComments =
						_commentManager.getChildComments(
							rootComment.getCommentId(),
							WorkflowConstants.STATUS_APPROVED,
							QueryUtil.ALL_POS, QueryUtil.ALL_POS);

					JSONArray childCommentsJSONArray =
						_jsonFactory.createJSONArray();

					for (Comment childComment : childComments) {
						childCommentsJSONArray.put(
							CommentUtil.getCommentJSONObject(
								childComment, httpServletRequest));
					}

					commentJSONObject.put("children", childCommentsJSONArray);

					jsonArray.put(commentJSONObject);
				}

				return jsonArray;
			}
		).put(
			"id", String.valueOf(objectEntry.getObjectEntryId())
		).put(
			"isSubscribed",
			() -> {
				ThemeDisplay themeDisplay = (ThemeDisplay)httpServletRequest;

				return _subscriptionLocalService.isSubscribed(
					themeDisplay.getCompanyId(), themeDisplay.getUserId(),
					objectEntry.getModelClassName(),
					objectEntry.getObjectEntryId());
			}
		).put(
			"type",
			() -> {
				ObjectDefinition objectDefinition =
					_objectDefinitionLocalService.fetchObjectDefinition(
						objectEntry.getObjectDefinitionId());

				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return objectDefinition.getLabel(themeDisplay.getLocale());
			}
		).put(
			"version", () -> String.valueOf(objectEntry.getVersion())
		).build();
	}

	@Reference
	private CommentManager _commentManager;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}
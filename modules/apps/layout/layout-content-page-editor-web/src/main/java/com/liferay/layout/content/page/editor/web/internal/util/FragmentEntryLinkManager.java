/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.helper.FragmentEntryLinkHelper;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererRegistry;
import com.liferay.fragment.renderer.constants.FragmentRendererConstants;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.content.page.editor.web.internal.comment.CommentUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItemUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = FragmentEntryLinkManager.class)
public class FragmentEntryLinkManager {

	public FragmentEntry getFragmentEntry(
		long groupId, String fragmentEntryKey, Locale locale) {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				groupId, fragmentEntryKey);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		Map<String, FragmentEntry> fragmentEntries =
			_fragmentCollectionContributorRegistry.getFragmentEntries(locale);

		return fragmentEntries.get(fragmentEntryKey);
	}

	public JSONObject getFragmentEntryLinkJSONObject(
			DefaultFragmentRendererContext defaultFragmentRendererContext,
			FragmentEntryLink fragmentEntryLink,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			LayoutStructure layoutStructure)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		boolean isolated = themeDisplay.isIsolated();

		themeDisplay.setIsolated(true);

		try {
			JSONObject editableValuesJSONObject = _jsonFactory.createJSONObject(
				fragmentEntryLink.getEditableValues());

			String content = _getContent(
				defaultFragmentRendererContext, editableValuesJSONObject,
				fragmentEntryLink, httpServletRequest, httpServletResponse,
				layoutStructure, themeDisplay);

			if (fragmentEntryLink.isTypePortlet()) {
				String portletId = editableValuesJSONObject.getString(
					"portletId");

				return JSONUtil.put(
					"comments",
					_getFragmentEntryLinkCommentsJSONArray(
						fragmentEntryLink, httpServletRequest)
				).put(
					"configuration", _jsonFactory.createJSONObject()
				).put(
					"content", content
				).put(
					"cssClass",
					() -> {
						FragmentStyledLayoutStructureItem
							fragmentStyledLayoutStructureItem =
								(FragmentStyledLayoutStructureItem)
									layoutStructure.
										getLayoutStructureItemByFragmentEntryLinkId(
											fragmentEntryLink.
												getFragmentEntryLinkId());

						if (fragmentStyledLayoutStructureItem != null) {
							return fragmentStyledLayoutStructureItem.
								getFragmentEntryLinkCssClass(fragmentEntryLink);
						}

						return null;
					}
				).put(
					"defaultConfigurationValues",
					_jsonFactory.createJSONObject()
				).put(
					"editableTypes", Collections.emptyMap()
				).put(
					"editableValues",
					_jsonFactory.createJSONObject(
						fragmentEntryLink.getEditableValues())
				).put(
					"fragmentEntryId", 0
				).put(
					"fragmentEntryKey",
					FragmentRendererConstants.
						FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY
				).put(
					"fragmentEntryLinkId",
					String.valueOf(fragmentEntryLink.getFragmentEntryLinkId())
				).put(
					"fragmentEntryType",
					FragmentConstants.getTypeLabel(
						FragmentConstants.TYPE_PORTLET)
				).put(
					"name",
					_portal.getPortletTitle(portletId, themeDisplay.getLocale())
				).put(
					"portletId", portletId
				).put(
					"segmentsExperienceId",
					String.valueOf(fragmentEntryLink.getSegmentsExperienceId())
				);
			}

			String configuration = _fragmentRendererController.getConfiguration(
				defaultFragmentRendererContext);

			JSONObject configurationJSONObject = _jsonFactory.createJSONObject(
				configuration);

			FragmentEntryLinkItemSelectorUtil.
				addFragmentEntryLinkFieldsSelectorURL(
					_itemSelector, httpServletRequest, configurationJSONObject);

			FragmentEntry fragmentEntry = _getFragmentEntry(
				fragmentEntryLink, themeDisplay.getLocale());

			return JSONUtil.put(
				"comments",
				_getFragmentEntryLinkCommentsJSONArray(
					fragmentEntryLink, httpServletRequest)
			).put(
				"configuration", configurationJSONObject
			).put(
				"content", content
			).put(
				"cssClass",
				() -> {
					FragmentStyledLayoutStructureItem
						fragmentStyledLayoutStructureItem =
							(FragmentStyledLayoutStructureItem)
								layoutStructure.
									getLayoutStructureItemByFragmentEntryLinkId(
										fragmentEntryLink.
											getFragmentEntryLinkId());

					if (fragmentStyledLayoutStructureItem != null) {
						return fragmentStyledLayoutStructureItem.
							getFragmentEntryLinkCssClass(fragmentEntryLink);
					}

					return null;
				}
			).put(
				"defaultConfigurationValues",
				_fragmentEntryConfigurationParser.
					getConfigurationDefaultValuesJSONObject(configuration)
			).put(
				"editableTypes",
				EditableFragmentEntryProcessorUtil.getEditableTypes(content)
			).put(
				"editableValues", editableValuesJSONObject
			).put(
				"fragmentEntryId",
				() -> {
					if (fragmentEntry != null) {
						return fragmentEntry.getFragmentEntryId();
					}

					return 0;
				}
			).put(
				"fragmentEntryKey",
				() -> {
					if (fragmentEntry != null) {
						return fragmentEntry.getFragmentEntryKey();
					}

					String rendererKey = fragmentEntryLink.getRendererKey();

					if (Validator.isNull(rendererKey)) {
						rendererKey =
							FragmentRendererConstants.
								FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY;
					}

					FragmentRenderer fragmentRenderer =
						_fragmentRendererRegistry.getFragmentRenderer(
							rendererKey);

					if (fragmentRenderer != null) {
						return fragmentRenderer.getKey();
					}

					return StringPool.BLANK;
				}
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
					else {
						FragmentRenderer fragmentRenderer =
							_fragmentRendererRegistry.getFragmentRenderer(
								fragmentEntryLink.getRendererKey());

						if (fragmentRenderer != null) {
							fragmentEntryType = fragmentRenderer.getType();
						}
					}

					return FragmentConstants.getTypeLabel(fragmentEntryType);
				}
			).put(
				"groupId",
				() -> {
					if (fragmentEntry != null) {
						return fragmentEntry.getGroupId();
					}

					return null;
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
				"name",
				_fragmentEntryLinkHelper.getFragmentEntryName(
					fragmentEntryLink, themeDisplay.getLocale())
			).put(
				"segmentsExperienceId",
				String.valueOf(fragmentEntryLink.getSegmentsExperienceId())
			);
		}
		finally {
			themeDisplay.setIsolated(isolated);
		}
	}

	public JSONObject getFragmentEntryLinkJSONObject(
			FragmentEntryLink fragmentEntryLink,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			LayoutStructure layoutStructure)
		throws PortalException {

		return getFragmentEntryLinkJSONObject(
			new DefaultFragmentRendererContext(fragmentEntryLink),
			fragmentEntryLink, httpServletRequest, httpServletResponse,
			layoutStructure);
	}

	private String _getContent(
		DefaultFragmentRendererContext defaultFragmentRendererContext,
		JSONObject editableValuesJSONObject,
		FragmentEntryLink fragmentEntryLink,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		LayoutStructure layoutStructure, ThemeDisplay themeDisplay) {

		if (fragmentEntryLink.isTypePortlet()) {
			String portletId = editableValuesJSONObject.getString("portletId");

			Portlet portlet = _portletLocalService.fetchPortletById(
				themeDisplay.getCompanyId(), portletId);

			if ((portlet == null) || portlet.isUndeployedPortlet()) {
				String message = _language.get(
					httpServletRequest,
					"this-portlet-could-not-be-found.-please-redeploy-it-or-" +
						"remove-it-from-the-page");

				return "<div class=\"alert alert-info\">" + message +
					"</div>";
			}
		}

		defaultFragmentRendererContext.setInfoForm(
			_getInfoForm(fragmentEntryLink, layoutStructure));

		String languageId = ParamUtil.getString(
			httpServletRequest, "languageId", themeDisplay.getLanguageId());

		defaultFragmentRendererContext.setLocale(
			LocaleUtil.fromLanguageId(languageId));

		defaultFragmentRendererContext.setMode(FragmentEntryLinkConstants.EDIT);

		return _fragmentRendererController.render(
			defaultFragmentRendererContext, httpServletRequest,
			httpServletResponse);
	}

	private FragmentEntry _getFragmentEntry(
		FragmentEntryLink fragmentEntryLink, Locale locale) {

		if (fragmentEntryLink.getFragmentEntryId() <= 0) {
			return getFragmentEntry(
				fragmentEntryLink.getGroupId(),
				fragmentEntryLink.getRendererKey(), locale);
		}

		return _fragmentEntryLocalService.fetchFragmentEntry(
			fragmentEntryLink.getFragmentEntryId());
	}

	private JSONArray _getFragmentEntryLinkCommentsJSONArray(
		FragmentEntryLink fragmentEntryLink,
		HttpServletRequest httpServletRequest) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

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
					_jsonFactory.createJSONArray();

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
			_log.error(portalException);

			return jsonArray;
		}

		return jsonArray;
	}

	private InfoForm _getInfoForm(
		FormStyledLayoutStructureItem formStyledLayoutStructureItem,
		long groupId) {

		if (formStyledLayoutStructureItem == null) {
			return null;
		}

		long classNameId = formStyledLayoutStructureItem.getClassNameId();

		if (classNameId <= 0) {
			return null;
		}

		InfoItemFormProvider<Object> infoItemFormProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormProvider.class, _portal.getClassName(classNameId));

		if (infoItemFormProvider != null) {
			try {
				return infoItemFormProvider.getInfoForm(
					String.valueOf(
						formStyledLayoutStructureItem.getClassTypeId()),
					groupId);
			}
			catch (NoSuchFormVariationException noSuchFormVariationException) {
				if (_log.isDebugEnabled()) {
					_log.debug(noSuchFormVariationException);
				}

				return null;
			}
		}

		return null;
	}

	private InfoForm _getInfoForm(
		FragmentEntryLink fragmentEntryLink, LayoutStructure layoutStructure) {

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			(FragmentStyledLayoutStructureItem)
				layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
					fragmentEntryLink.getFragmentEntryLinkId());

		if (fragmentStyledLayoutStructureItem == null) {
			return null;
		}

		LayoutStructureItem layoutStructureItem =
			LayoutStructureItemUtil.getAncestor(
				fragmentStyledLayoutStructureItem.getItemId(),
				LayoutDataItemTypeConstants.TYPE_FORM, layoutStructure);

		if (!(layoutStructureItem instanceof FormStyledLayoutStructureItem)) {
			return null;
		}

		return _getInfoForm(
			(FormStyledLayoutStructureItem)layoutStructureItem,
			fragmentEntryLink.getGroupId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkManager.class);

	@Reference
	private CommentManager _commentManager;

	@Reference
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLinkHelper _fragmentEntryLinkHelper;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private FragmentRendererRegistry _fragmentRendererRegistry;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

}
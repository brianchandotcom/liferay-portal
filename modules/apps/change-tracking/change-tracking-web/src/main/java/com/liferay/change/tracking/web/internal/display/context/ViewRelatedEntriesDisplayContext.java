/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryTable;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.spi.display.CTDisplayRendererRegistry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class ViewRelatedEntriesDisplayContext {

	public ViewRelatedEntriesDisplayContext(
		CTCollectionLocalService ctCollectionLocalService,
		CTDisplayRendererRegistry ctDisplayRendererRegistry,
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse, UserLocalService userLocalService) {

		_ctCollectionLocalService = ctCollectionLocalService;
		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_userLocalService = userLocalService;

		_ctCollectionId = ParamUtil.getLong(renderRequest, "ctCollectionId");
		_modelClassNameId = ParamUtil.getLong(
			renderRequest, "modelClassNameId");
		_modelClassPK = ParamUtil.getLong(renderRequest, "modelClassPK");
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public long getCTCollectionId() {
		return _ctCollectionId;
	}

	public Map<String, Object> getReactData() throws Exception {
		Map<Long, List<CTEntry>> relatedCTEntriesMap =
			_ctCollectionLocalService.getRelatedCTEntriesMap(
				_ctCollectionId, _modelClassNameId, _modelClassPK);

		List<CTEntry> ctEntries = new ArrayList<>();

		for (List<CTEntry> value : relatedCTEntriesMap.values()) {
			ctEntries.addAll(value);
		}

		return HashMapBuilder.<String, Object>put(
			"ctEntriesJSONArray",
			() -> {
				JSONArray ctEntriesJSONArray =
					JSONFactoryUtil.createJSONArray();

				for (CTEntry ctEntry : ctEntries) {
					ResourceURL dataURL = _renderResponse.createResourceURL();

					dataURL.setResourceID(
						"/change_tracking/get_entry_render_data");
					dataURL.setParameter(
						"ctEntryId", String.valueOf(ctEntry.getCtEntryId()));

					ctEntriesJSONArray.put(
						JSONUtil.put(
							"ctEntryId", ctEntry.getCtEntryId()
						).put(
							"dataURL", dataURL.toString()
						).put(
							"description",
							_ctDisplayRendererRegistry.getEntryDescription(
								_httpServletRequest, ctEntry)
						).put(
							"modelClassNameId", ctEntry.getModelClassNameId()
						).put(
							"modelClassPK", ctEntry.getModelClassPK()
						).put(
							"title",
							_ctDisplayRendererRegistry.getTitle(
								ctEntry.getCtCollectionId(), ctEntry,
								_themeDisplay.getLocale())
						).put(
							"userId", ctEntry.getUserId()
						));
				}

				return ctEntriesJSONArray;
			}
		).put(
			"spritemap", _themeDisplay.getPathThemeSpritemap()
		).put(
			"typeNames",
			DisplayContextUtil.getTypeNamesJSONObject(
				relatedCTEntriesMap.keySet(), _ctDisplayRendererRegistry,
				_themeDisplay)
		).put(
			"userInfo",
			DisplayContextUtil.getUserInfoJSONObject(
				CTEntryTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId),
				CTEntryTable.INSTANCE, _themeDisplay, _userLocalService,
				CTEntryTable.INSTANCE.ctEntryId.in(
					TransformUtil.transformToArray(
						ctEntries, ctEntry -> ctEntry.getCtEntryId(),
						Long.class)))
		).build();
	}

	public String getRedirectURL() {
		String redirect = ParamUtil.getString(_renderRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			return redirect;
		}

		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/change_tracking/view_changes"
		).setParameter(
			"ctCollectionId", _ctCollectionId
		).buildString();
	}

	public List<SelectOption> getSelectOptions() {
		List<SelectOption> selectOptions = new ArrayList<>();

		List<CTCollection> ctCollections =
			_ctCollectionLocalService.getCTCollections(
				_themeDisplay.getCompanyId(), WorkflowConstants.STATUS_DRAFT,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		selectOptions.add(
			new SelectOption(
				LanguageUtil.get(_themeDisplay.getLocale(), "none"),
				StringPool.BLANK));

		for (CTCollection ctCollection : ctCollections) {
			if (ctCollection.getCtCollectionId() != _ctCollectionId) {
				selectOptions.add(
					new SelectOption(
						ctCollection.getName(),
						String.valueOf(ctCollection.getCtCollectionId())));
			}
		}

		return selectOptions;
	}

	public String getSubmitDiscardURL() {
		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/change_tracking/discard_changes"
		).setRedirect(
			getRedirectURL()
		).setParameter(
			"ctCollectionId", _ctCollectionId
		).setParameter(
			"modelClassNameId", _modelClassNameId
		).setParameter(
			"modelClassPK", _modelClassPK
		).buildString();
	}

	public String getSubmitMoveURL() {
		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/change_tracking/move_changes"
		).setRedirect(
			getRedirectURL()
		).setParameter(
			"ctCollectionId", _ctCollectionId
		).setParameter(
			"modelClassNameId", _modelClassNameId
		).setParameter(
			"modelClassPK", _modelClassPK
		).buildString();
	}

	private final long _ctCollectionId;
	private final CTCollectionLocalService _ctCollectionLocalService;
	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;
	private final HttpServletRequest _httpServletRequest;
	private final long _modelClassNameId;
	private final long _modelClassPK;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;
	private final UserLocalService _userLocalService;

}
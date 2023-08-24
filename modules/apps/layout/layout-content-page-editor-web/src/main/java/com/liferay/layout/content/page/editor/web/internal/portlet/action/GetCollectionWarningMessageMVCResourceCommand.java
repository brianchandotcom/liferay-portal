/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.LayoutObjectReferenceUtil;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.list.retriever.DefaultLayoutListRetrieverContext;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverRegistry;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryRegistry;
import com.liferay.layout.util.CollectionPaginationUtil;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.Objects;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/get_collection_warning_message"
	},
	service = MVCResourceCommand.class
)
public class GetCollectionWarningMessageMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String layoutObjectReference = ParamUtil.getString(
			resourceRequest, "layoutObjectReference");

		try {
			jsonObject = JSONUtil.put(
				"warningMessage",
				_getWarningMessage(
					_portal.getHttpServletRequest(resourceRequest),
					layoutObjectReference, themeDisplay));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			jsonObject.put(
				"error",
				_language.get(
					themeDisplay.getLocale(), "an-unexpected-error-occurred"));
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

	private Object _getInfoItem(HttpServletRequest httpServletRequest) {
		long classNameId = ParamUtil.getLong(httpServletRequest, "classNameId");
		long classPK = ParamUtil.getLong(httpServletRequest, "classPK");

		if ((classNameId <= 0) && (classPK <= 0)) {
			return null;
		}

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			(InfoItemObjectProvider<Object>)
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemObjectProvider.class,
					_portal.getClassName(classNameId),
					ClassPKInfoItemIdentifier.INFO_ITEM_SERVICE_FILTER);

		if (infoItemObjectProvider == null) {
			return null;
		}

		try {
			return infoItemObjectProvider.getInfoItem(
				new ClassPKInfoItemIdentifier(classPK));
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchInfoItemException);
			}
		}

		return null;
	}

	private int _getTotalCount(
			CollectionStyledLayoutStructureItem
				collectionStyledLayoutStructureItem,
			HttpServletRequest httpServletRequest, String layoutObjectReference)
		throws Exception {

		JSONObject layoutObjectReferenceJSONObject =
			_jsonFactory.createJSONObject(layoutObjectReference);

		String type = layoutObjectReferenceJSONObject.getString("type");

		LayoutListRetriever<?, ListObjectReference> layoutListRetriever =
			(LayoutListRetriever<?, ListObjectReference>)
				_layoutListRetrieverRegistry.getLayoutListRetriever(type);

		if (layoutListRetriever == null) {
			throw new Exception("Unable to get collection for type " + type);
		}

		ListObjectReferenceFactory<?> listObjectReferenceFactory =
			_listObjectReferenceFactoryRegistry.getListObjectReference(type);

		if (listObjectReferenceFactory == null) {
			throw new Exception("Unable to get collection for type " + type);
		}

		DefaultLayoutListRetrieverContext defaultLayoutListRetrieverContext =
			new DefaultLayoutListRetrieverContext();

		defaultLayoutListRetrieverContext.setConfiguration(
			LayoutObjectReferenceUtil.getConfiguration(
				layoutObjectReferenceJSONObject));

		Object infoItem = _getInfoItem(httpServletRequest);

		if (infoItem != null) {
			defaultLayoutListRetrieverContext.setContextObject(infoItem);
		}

		InfoPage<?> infoPage = layoutListRetriever.getInfoPage(
			listObjectReferenceFactory.getListObjectReference(
				layoutObjectReferenceJSONObject),
			defaultLayoutListRetrieverContext);

		return Math.min(
			collectionStyledLayoutStructureItem.getNumberOfItems(),
			infoPage.getTotalCount());
	}

	private String _getWarningMessage(
			HttpServletRequest httpServletRequest, String layoutObjectReference,
			ThemeDisplay themeDisplay)
		throws Exception {

		long segmentsExperienceId = ParamUtil.getLong(
			httpServletRequest, "segmentsExperienceId");
		String itemId = ParamUtil.getString(httpServletRequest, "itemId");

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
				segmentsExperienceId);

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(itemId);

		if (!(layoutStructureItem instanceof
				CollectionStyledLayoutStructureItem)) {

			throw new Exception("Unable to get collection item for " + itemId);
		}

		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem =
				(CollectionStyledLayoutStructureItem)layoutStructureItem;

		int totalCount = _getTotalCount(
			collectionStyledLayoutStructureItem, httpServletRequest,
			layoutObjectReference);

		if (Objects.equals(
				collectionStyledLayoutStructureItem.getPaginationType(),
				CollectionPaginationUtil.PAGINATION_TYPE_NONE) &&
			(collectionStyledLayoutStructureItem.isDisplayAllItems() ||
			 (totalCount > PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA))) {

			return _language.format(
				httpServletRequest,
				"this-setting-can-affect-page-performance-severely-if-the-" +
					"number-of-collection-items-is-above-x.-we-strongly-" +
						"recommend-using-pagination-instead",
				PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA);
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetCollectionWarningMessageMVCResourceCommand.class);

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutListRetrieverRegistry _layoutListRetrieverRegistry;

	@Reference
	private ListObjectReferenceFactoryRegistry
		_listObjectReferenceFactoryRegistry;

	@Reference
	private Portal _portal;

}
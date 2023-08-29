/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.helper;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.layout.helper.LayoutWarningMessageHelper;
import com.liferay.layout.list.retriever.DefaultLayoutListRetrieverContext;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverRegistry;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryRegistry;
import com.liferay.layout.util.CollectionPaginationUtil;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Molina
 */
@Component(service = LayoutWarningMessageHelper.class)
public class LayoutWarningMessageHelperImpl
	implements LayoutWarningMessageHelper {

	@Override
	public String getCollectionWarningMessage(
			CollectionStyledLayoutStructureItem
				collectionStyledLayoutStructureItem,
			HttpServletRequest httpServletRequest)
		throws Exception {

		int totalCount = _getTotalCount(collectionStyledLayoutStructureItem);

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

	private Map<String, String[]> _getConfiguration(
		JSONObject layoutObjectReferenceJSONObject) {

		JSONObject configurationJSONObject =
			layoutObjectReferenceJSONObject.getJSONObject("config");

		if (configurationJSONObject == null) {
			return null;
		}

		Map<String, String[]> configuration = new HashMap<>();

		for (String key : configurationJSONObject.keySet()) {
			List<String> values = new ArrayList<>();

			Object object = configurationJSONObject.get(key);

			if (object instanceof JSONArray) {
				JSONArray jsonArray = configurationJSONObject.getJSONArray(key);

				for (int i = 0; i < jsonArray.length(); i++) {
					values.add(jsonArray.getString(i));
				}
			}
			else {
				values.add(String.valueOf(object));
			}

			configuration.put(key, values.toArray(new String[0]));
		}

		return configuration;
	}

	private Object _getInfoItem(JSONObject layoutObjectReferenceJSONObject) {
		long classNameId = layoutObjectReferenceJSONObject.getLong(
			"classNameId");
		long classPK = layoutObjectReferenceJSONObject.getLong("classPK");

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
				collectionStyledLayoutStructureItem)
		throws Exception {

		JSONObject layoutObjectReferenceJSONObject =
			collectionStyledLayoutStructureItem.getCollectionJSONObject();

		String type = layoutObjectReferenceJSONObject.getString("type");

		LayoutListRetriever<?, ListObjectReference> layoutListRetriever =
			(LayoutListRetriever<?, ListObjectReference>)
				_layoutListRetrieverRegistry.getLayoutListRetriever(type);

		if (layoutListRetriever == null) {
			throw new Exception(
				"Layout list retriever is null for type " + type);
		}

		ListObjectReferenceFactory<?> listObjectReferenceFactory =
			_listObjectReferenceFactoryRegistry.getListObjectReference(type);

		if (listObjectReferenceFactory == null) {
			throw new Exception(
				"List object reference factory is null for type " + type);
		}

		DefaultLayoutListRetrieverContext defaultLayoutListRetrieverContext =
			new DefaultLayoutListRetrieverContext();

		defaultLayoutListRetrieverContext.setConfiguration(
			_getConfiguration(layoutObjectReferenceJSONObject));

		Object infoItem = _getInfoItem(layoutObjectReferenceJSONObject);

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

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutWarningMessageHelperImpl.class);

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
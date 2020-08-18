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

package com.liferay.content.dashboard.web.internal.portlet.action;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardPortletKeys;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentDashboardPortletKeys.CONTENT_DASHBOARD_ADMIN,
		"mvc.command.name=/content_dashboard/get_content_dashboard_item_info"
	},
	service = MVCResourceCommand.class
)
public class GetContentDashboardItemInfoMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		Locale locale = _portal.getLocale(resourceRequest);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		try {
			String className = ParamUtil.getString(
				resourceRequest, "className");
			long classPK = GetterUtil.getLong(
				ParamUtil.getLong(resourceRequest, "classPK"));

			Optional<ContentDashboardItemFactory<?>>
				contentDashboardItemFactoryOptional =
					_contentDashboardItemFactoryTracker.
						getContentDashboardItemFactoryOptional(className);

			JSONObject jsonObject = contentDashboardItemFactoryOptional.flatMap(
				contentDashboardItemFactory -> {
					try {
						return Optional.of(
							contentDashboardItemFactory.create(classPK));
					}
					catch (PortalException portalException) {
						_log.error(portalException, portalException);

						return Optional.empty();
					}
				}
			).map(
				contentDashboardItem -> JSONUtil.put(
					"categories",
					_getAssetCategoriesJSONArray(contentDashboardItem, locale)
				).put(
					"className", _getClassName(contentDashboardItem)
				).put(
					"classPK", _getClassPK(contentDashboardItem)
				).put(
					"createDate",
					_toString(contentDashboardItem.getCreateDate())
				).put(
					"data", _getDataJSONObject(contentDashboardItem, locale)
				).put(
					"languageTag", locale.toLanguageTag()
				).put(
					"modifiedDate",
					_toString(contentDashboardItem.getModifiedDate())
				).put(
					"subType", _getSubtype(contentDashboardItem, locale)
				).put(
					"tags", _getAssetTagsJSONArray(contentDashboardItem)
				).put(
					"title", contentDashboardItem.getTitle(locale)
				).put(
					"userId", contentDashboardItem.getUserId()
				).put(
					"userName", _getUserName(contentDashboardItem.getUserId())
				).put(
					"userPortraitURL",
					_getUserPortraitURL(
						httpServletRequest, contentDashboardItem.getUserId())
				).put(
					"versions",
					_getVersionsJSONArray(contentDashboardItem, locale)
				).put(
					"viewURLs",
					_getViewURLsJSONArray(
						contentDashboardItem, httpServletRequest)
				)
			).orElseGet(
				JSONFactoryUtil::createJSONObject
			);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonObject);
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(exception, exception);
			}

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				locale, getClass());

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					ResourceBundleUtil.getString(
						resourceBundle, "an-unexpected-error-occurred")));
		}
	}

	private JSONArray _getAssetCategoriesJSONArray(
		ContentDashboardItem contentDashboardItem, Locale locale) {

		List<AssetCategory> assetCategories =
			contentDashboardItem.getAssetCategories();

		Stream<AssetCategory> stream = assetCategories.stream();

		return JSONUtil.putAll(
			stream.map(
				assetCategory -> assetCategory.getTitle(locale)
			).toArray());
	}

	private JSONArray _getAssetTagsJSONArray(
		ContentDashboardItem contentDashboardItem) {

		List<AssetTag> assetTags = contentDashboardItem.getAssetTags();

		Stream<AssetTag> stream = assetTags.stream();

		return JSONUtil.putAll(
			stream.map(
				AssetTag::getName
			).toArray());
	}

	private String _getClassName(ContentDashboardItem<?> contentDashboardItem) {
		InfoItemReference infoItemReference =
			contentDashboardItem.getInfoItemReference();

		return infoItemReference.getClassName();
	}

	private long _getClassPK(ContentDashboardItem<?> contentDashboardItem) {
		InfoItemReference infoItemReference =
			contentDashboardItem.getInfoItemReference();

		return infoItemReference.getClassPK();
	}

	private JSONObject _getDataJSONObject(
		ContentDashboardItem contentDashboardItem, Locale locale) {

		Map<String, Object> data = contentDashboardItem.getData(locale);

		Set<Map.Entry<String, Object>> entries = data.entrySet();

		Stream<Map.Entry<String, Object>> stream = entries.stream();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		stream.forEach(
			entry -> jsonObject.put(
				entry.getKey(),
				JSONUtil.put(
					"title", _language.get(locale, entry.getKey())
				).put(
					"value", _toString(entry.getValue())
				)));

		return jsonObject;
	}

	private String _getSubtype(
		ContentDashboardItem contentDashboardItem, Locale locale) {

		ContentDashboardItemType contentDashboardItemType =
			contentDashboardItem.getContentDashboardItemType();

		return contentDashboardItemType.getLabel(locale);
	}

	private String _getUserName(long userId) {
		return Optional.ofNullable(
			_userLocalService.fetchUser(userId)
		).map(
			User::getFullName
		).orElseGet(
			() -> StringPool.BLANK
		);
	}

	private String _getUserPortraitURL(
		HttpServletRequest httpServletRequest, long userId) {

		return Optional.ofNullable(
			_userLocalService.fetchUser(userId)
		).filter(
			user -> user.getPortraitId() > 0
		).map(
			user -> {
				try {
					return user.getPortraitURL(
						(ThemeDisplay)httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY));
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);

					return StringPool.BLANK;
				}
			}
		).orElse(
			StringPool.BLANK
		);
	}

	private JSONArray _getVersionsJSONArray(
		ContentDashboardItem contentDashboardItem, Locale locale) {

		List<ContentDashboardItem.Version> versions =
			contentDashboardItem.getVersions(locale);

		Stream<ContentDashboardItem.Version> stream = versions.stream();

		return JSONUtil.putAll(
			stream.map(
				ContentDashboardItem.Version::toJSONObject
			).toArray());
	}

	private String _getViewURL(
		HttpServletRequest httpServletRequest, String url) {

		String backURL = ParamUtil.getString(httpServletRequest, "backURL");

		if (Validator.isNotNull(backURL)) {
			return _http.setParameter(url, "p_l_back_url", backURL);
		}

		return url;
	}

	private JSONArray _getViewURLsJSONArray(
		ContentDashboardItem contentDashboardItem,
		HttpServletRequest httpServletRequest) {

		List<ContentDashboardItemAction> contentDashboardItemActions =
			contentDashboardItem.getContentDashboardItemActions(
				httpServletRequest, ContentDashboardItemAction.Type.VIEW);

		if (ListUtil.isEmpty(contentDashboardItemActions)) {
			return JSONFactoryUtil.createJSONArray();
		}

		ContentDashboardItemAction contentDashboardItemAction =
			contentDashboardItemActions.get(0);

		List<Locale> locales = contentDashboardItem.getAvailableLocales();

		Stream<Locale> stream = locales.stream();

		return JSONUtil.putAll(
			stream.map(
				locale -> JSONUtil.put(
					"default",
					Objects.equals(
						locale, contentDashboardItem.getDefaultLocale())
				).put(
					"languageId", _language.getBCP47LanguageId(locale)
				).put(
					"viewURL",
					_getViewURL(
						httpServletRequest,
						contentDashboardItemAction.getURL(locale))
				)
			).toArray());
	}

	private String _toString(Date date) {
		Instant instant = date.toInstant();

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

		LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

		return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	private String _toString(Object object) {
		if (object == null) {
			return null;
		}

		if (object instanceof Date) {
			return _toString((Date)object);
		}

		return String.valueOf(object);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetContentDashboardItemInfoMVCResourceCommand.class);

	@Reference
	private ContentDashboardItemFactoryTracker
		_contentDashboardItemFactoryTracker;

	@Reference
	private Http _http;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}
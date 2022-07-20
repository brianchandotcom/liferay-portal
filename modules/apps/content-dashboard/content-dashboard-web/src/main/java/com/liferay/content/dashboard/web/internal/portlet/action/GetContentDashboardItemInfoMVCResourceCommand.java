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
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardPortletKeys;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.content.dashboard.web.internal.util.ContentDashboardGroupUtil;
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
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
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

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);
		Locale locale = _portal.getLocale(resourceRequest);
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			String className = ParamUtil.getString(
				resourceRequest, "className");

			ContentDashboardItemFactory<?> contentDashboardItemFactory =
				_contentDashboardItemFactoryTracker.
					getContentDashboardItemFactory(className);

			if (contentDashboardItemFactory == null) {
				JSONPortletResponseUtil.writeJSON(
					resourceRequest, resourceResponse,
					JSONFactoryUtil.createJSONArray());

				return;
			}

			long classPK = GetterUtil.getLong(
				ParamUtil.getLong(resourceRequest, "classPK"));

			ContentDashboardItem<?> contentDashboardItem =
				contentDashboardItemFactory.create(classPK);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"className", _getClassName(contentDashboardItem)
				).put(
					"classPK", _getClassPK(contentDashboardItem)
				).put(
					"clipboard", _getClipboardJSONObject(contentDashboardItem)
				).put(
					"createDate",
					_toString(contentDashboardItem.getCreateDate())
				).put(
					"description", contentDashboardItem.getDescription(locale)
				).put(
					"fetchSharingButtonURL",
					_getFetchSharingButtonURL(
						contentDashboardItem, httpServletRequest)
				).put(
					"fetchSharingContactsURL",
					_getFetchSharingContactsURL(
						contentDashboardItem, httpServletRequest)
				).put(
					"languageTag", locale.toLanguageTag()
				).put(
					"modifiedDate",
					_toString(contentDashboardItem.getModifiedDate())
				).put(
					"preview",
					Optional.ofNullable(
						contentDashboardItem.getPreview()
					).map(
						ContentDashboardItem.Preview::toJSONObject
					).orElse(
						null
					)
				).put(
					"specificFields",
					_getSpecificFieldsJSONObject(contentDashboardItem, locale)
				).put(
					"subType",
					Optional.ofNullable(
						contentDashboardItem.getContentDashboardItemSubtype()
					).map(
						contentDashboardItemSubtype ->
							contentDashboardItemSubtype.getLabel(locale)
					).orElse(
						StringPool.BLANK
					)
				).put(
					"tags", _getAssetTagsJSONArray(contentDashboardItem)
				).put(
					"title", contentDashboardItem.getTitle(locale)
				).put(
					"type", contentDashboardItem.getTypeLabel(locale)
				).put(
					"user",
					_getUserJSONObject(contentDashboardItem, themeDisplay)
				).put(
					"versions",
					_getVersionsJSONArray(contentDashboardItem, locale)
				).put(
					"viewURLs",
					_getViewURLsJSONArray(
						contentDashboardItem, httpServletRequest)
				).put(
					"vocabularies",
					_getAssetVocabulariesJSONObject(
						contentDashboardItem, locale)
				));
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(exception);
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					ResourceBundleUtil.getString(
						ResourceBundleUtil.getBundle(locale, getClass()),
						"an-unexpected-error-occurred")));
		}
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

	private JSONObject _getAssetVocabulariesJSONObject(
		ContentDashboardItem contentDashboardItem, Locale locale) {

		List<AssetCategory> assetCategories =
			contentDashboardItem.getAssetCategories();

		Stream<AssetCategory> stream = assetCategories.stream();

		return JSONFactoryUtil.createJSONObject(
			stream.collect(_getCollector(locale)));
	}

	private Map<String, Object> _getAssetVocabularyData(
		Locale locale, AssetVocabulary assetVocabulary) {

		return HashMapBuilder.<String, Object>put(
			"categories", ListUtil.fromArray()
		).put(
			"groupName",
			Optional.ofNullable(
				_groupLocalService.fetchGroup(assetVocabulary.getGroupId())
			).map(
				group -> ContentDashboardGroupUtil.getGroupName(group, locale)
			).orElse(
				StringPool.BLANK
			)
		).put(
			"isPublic",
			assetVocabulary.getVisibilityType() ==
				AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC
		).put(
			"vocabularyName", assetVocabulary.getTitle(locale)
		).build();
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

	private JSONObject _getClipboardJSONObject(
		ContentDashboardItem contentDashboardItem) {

		return Optional.ofNullable(
			contentDashboardItem.getClipboard()
		).map(
			ContentDashboardItem.Clipboard::toJSONObject
		).orElse(
			null
		);
	}

	private Collector<AssetCategory, ?, Map<Long, Map<String, Object>>>
		_getCollector(Locale locale) {

		return Collector.of(
			() -> new HashMap<>(),
			(assetVocabulariesData, assetCategory) -> {
				assetVocabulariesData.computeIfAbsent(
					assetCategory.getVocabularyId(),
					vocabularyId -> _getAssetVocabularyData(
						locale,
						_assetVocabularyLocalService.fetchAssetVocabulary(
							vocabularyId)));

				Map<String, Object> assetVocabularyData =
					assetVocabulariesData.get(assetCategory.getVocabularyId());

				List<String> assetCategories =
					(List<String>)assetVocabularyData.get("categories");

				assetCategories.add(assetCategory.getTitle(locale));
			},
			(first, second) -> {
				first.putAll(second);

				return first;
			});
	}

	private String _getFetchSharingButtonURL(
		ContentDashboardItem contentDashboardItem,
		HttpServletRequest httpServletRequest) {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-152906"))) {
			return null;
		}

		List<ContentDashboardItemAction> contentDashboardItemActions =
			contentDashboardItem.getContentDashboardItemActions(
				httpServletRequest,
				ContentDashboardItemAction.Type.SHARING_BUTTON);

		if (ListUtil.isNotEmpty(contentDashboardItemActions)) {
			ContentDashboardItemAction contentDashboardItemAction =
				contentDashboardItemActions.get(0);

			return contentDashboardItemAction.getURL();
		}

		return null;
	}

	private String _getFetchSharingContactsURL(
		ContentDashboardItem contentDashboardItem,
		HttpServletRequest httpServletRequest) {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-153687"))) {
			return null;
		}

		List<ContentDashboardItemAction> contentDashboardItemActions =
			contentDashboardItem.getContentDashboardItemActions(
				httpServletRequest,
				ContentDashboardItemAction.Type.SHARING_COLLABORATORS);

		if (ListUtil.isNotEmpty(contentDashboardItemActions)) {
			ContentDashboardItemAction contentDashboardItemAction =
				contentDashboardItemActions.get(0);

			return contentDashboardItemAction.getURL();
		}

		return null;
	}

	private JSONObject _getSpecificFieldsJSONObject(
		ContentDashboardItem contentDashboardItem, Locale locale) {

		Map<String, Object> specificInformation =
			contentDashboardItem.getSpecificInformation(locale);

		Set<Map.Entry<String, Object>> entries = specificInformation.entrySet();

		Stream<Map.Entry<String, Object>> stream = entries.stream();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		stream.sorted(
			Comparator.comparing(entry -> entry.getKey())
		).forEach(
			entry -> jsonObject.put(
				entry.getKey(),
				JSONUtil.put(
					"title", _language.get(locale, entry.getKey())
				).put(
					"type", _getSpecificInformationType(entry.getValue())
				).put(
					"value", _toString(entry.getValue())
				))
		);

		return jsonObject;
	}

	private String _getSpecificInformationType(Object object) {
		if (object instanceof Date) {
			return "Date";
		}

		return "String";
	}

	private JSONObject _getUserJSONObject(
		ContentDashboardItem contentDashboardItem, ThemeDisplay themeDisplay) {

		return JSONUtil.put(
			"name", contentDashboardItem.getUserName()
		).put(
			"url",
			Optional.ofNullable(
				_userLocalService.fetchUser(contentDashboardItem.getUserId())
			).filter(
				user -> user.getPortraitId() > 0
			).map(
				user -> {
					try {
						return user.getPortraitURL(themeDisplay);
					}
					catch (PortalException portalException) {
						_log.error(portalException);

						return null;
					}
				}
			).orElse(
				null
			)
		).put(
			"userId", contentDashboardItem.getUserId()
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

	private JSONArray _getViewURLsJSONArray(
		ContentDashboardItem contentDashboardItem,
		HttpServletRequest httpServletRequest) {

		List<ContentDashboardItemAction> contentDashboardItemActions =
			contentDashboardItem.getContentDashboardItemActions(
				httpServletRequest, ContentDashboardItemAction.Type.VIEW);

		List<Locale> locales = contentDashboardItem.getAvailableLocales();

		Stream<Locale> stream = locales.stream();

		if (ListUtil.isEmpty(contentDashboardItemActions)) {
			return JSONUtil.putAll(
				stream.map(
					locale -> JSONUtil.put(
						"default",
						Objects.equals(
							locale, contentDashboardItem.getDefaultLocale())
					).put(
						"languageId", LocaleUtil.toBCP47LanguageId(locale)
					)
				).toArray());
		}

		ContentDashboardItemAction contentDashboardItemAction =
			contentDashboardItemActions.get(0);

		return JSONUtil.putAll(
			stream.map(
				locale -> JSONUtil.put(
					"default",
					Objects.equals(
						locale, contentDashboardItem.getDefaultLocale())
				).put(
					"languageId", LocaleUtil.toBCP47LanguageId(locale)
				).put(
					"viewURL", contentDashboardItemAction.getURL(locale)
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
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private ContentDashboardItemFactoryTracker
		_contentDashboardItemFactoryTracker;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}
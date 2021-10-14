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

package com.liferay.screens.service.impl;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletItem;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletItemLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portlet.asset.service.permission.AssetEntryPermission;
import com.liferay.screens.service.ScreensDDLRecordService;
import com.liferay.screens.service.base.ScreensAssetEntryServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Manuel Navarro
 */
@Component(
	property = {
		"json.web.service.context.name=screens",
		"json.web.service.context.path=ScreensAssetEntry"
	},
	service = AopService.class
)
public class ScreensAssetEntryServiceImpl
	extends ScreensAssetEntryServiceBaseImpl {

	@Override
	public JSONArray getAssetEntries(
			AssetEntryQuery assetEntryQuery, Locale locale)
		throws PortalException {

		List<AssetEntry> assetEntries = _assetEntryLocalService.getEntries(
			assetEntryQuery);

		assetEntries = filterAssetEntries(assetEntries);

		return toJSONArray(assetEntries, locale);
	}

	@Override
	public JSONArray getAssetEntries(
			long companyId, long groupId, String portletItemName, Locale locale,
			int max)
		throws PortalException {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PortletItem.class, getClassLoader());

		Property property = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(property.eq(portletItemName));

		dynamicQuery.setLimit(0, 1);

		List<PortletItem> portletItems = _portletItemLocalService.dynamicQuery(
			dynamicQuery);

		if (portletItems.isEmpty()) {
			throw new PortalException(
				"No portlet items associated with portlet item name " +
					portletItemName);
		}

		PortletItem portletItem = portletItems.get(0);

		PortletPreferences portletPreferences =
			_portletPreferencesLocalService.getPreferences(
				portletItem.getCompanyId(), portletItem.getPortletItemId(),
				PortletKeys.PREFS_OWNER_TYPE_ARCHIVED, 0,
				portletItem.getPortletId());

		String selectionStyle = GetterUtil.getString(
			portletPreferences.getValue("selectionStyle", null), "dynamic");

		if (selectionStyle.equals("dynamic")) {
			if (max > 500) {
				max = 500;
			}

			List<Layout> layouts = _layoutLocalService.getLayouts(companyId);

			if (!layouts.isEmpty()) {
				AssetEntryQuery assetEntryQuery =
					_assetPublisherHelper.getAssetEntryQuery(
						portletPreferences, groupId, layouts.get(0), null,
						null);

				assetEntryQuery.setEnd(max);
				assetEntryQuery.setStart(0);

				List<AssetEntry> assetEntries =
					_assetEntryLocalService.getEntries(assetEntryQuery);

				assetEntries = filterAssetEntries(assetEntries);

				return toJSONArray(assetEntries, locale);
			}

			return JSONFactoryUtil.createJSONArray();
		}

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(getUser());

			List<AssetEntry> assetEntries =
				_assetPublisherHelper.getAssetEntries(
					null, portletPreferences, permissionChecker,
					new long[] {groupId}, false, false, false);

			assetEntries = filterAssetEntries(assetEntries);

			return toJSONArray(assetEntries, locale);
		}
		catch (PortalException | SystemException exception) {
			throw exception;
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	@Override
	public JSONObject getAssetEntry(long entryId, Locale locale)
		throws PortalException {

		AssetEntry entry = _assetEntryLocalService.getEntry(entryId);

		AssetEntryPermission.check(
			getPermissionChecker(), entry, ActionKeys.VIEW);

		return toJSONObject(entry, locale);
	}

	@Override
	public JSONObject getAssetEntry(
			String className, long classPK, Locale locale)
		throws PortalException {

		AssetEntryPermission.check(
			getPermissionChecker(), className, classPK, ActionKeys.VIEW);

		return toJSONObject(
			_assetEntryLocalService.getEntry(className, classPK), locale);
	}

	protected List<AssetEntry> filterAssetEntries(
		List<AssetEntry> assetEntries) {

		List<AssetEntry> filteredAssetEntries = new ArrayList<>(
			assetEntries.size());

		for (AssetEntry assetEntry : assetEntries) {
			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(
						assetEntry.getClassName());

			try {
				if (assetRendererFactory.hasPermission(
						getPermissionChecker(), assetEntry.getClassPK(),
						ActionKeys.VIEW)) {

					filteredAssetEntries.add(assetEntry);
				}
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}
		}

		return filteredAssetEntries;
	}

	protected JSONObject getAssetObjectJSONObject(
			AssetEntry assetEntry, Locale locale)
		throws PortalException {

		String className = assetEntry.getClassName();

		if (className.equals(BlogsEntry.class.getName())) {
			return getBlogsEntryJSONObject(assetEntry);
		}
		else if (className.equals(DLFileEntry.class.getName())) {
			return getFileEntryJSONObject(assetEntry);
		}
		else if (className.equals(DDLRecord.class.getName())) {
			return _screensDDLRecordService.getDDLRecord(
				assetEntry.getClassPK(), locale);
		}
		else if (className.equals(JournalArticle.class.getName())) {
			return getJournalArticleJSONObject(assetEntry);
		}
		else if (className.equals(User.class.getName())) {
			return getUserJSONObject(assetEntry);
		}

		return JSONFactoryUtil.createJSONObject();
	}

	protected JSONObject getBlogsEntryJSONObject(AssetEntry assetEntry)
		throws PortalException {

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(
			assetEntry.getClassPK());

		return JSONUtil.put(
			"blogsEntry",
			JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerialize(blogsEntry)));
	}

	protected JSONObject getFileEntryJSONObject(AssetEntry assetEntry)
		throws PortalException {

		FileEntry fileEntry = _dlAppService.getFileEntry(
			assetEntry.getClassPK());

		return JSONUtil.put(
			"fileEntry",
			JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerialize(fileEntry))
		).put(
			"url", getFileEntryPreviewURL(fileEntry)
		);
	}

	protected String getFileEntryPreviewURL(FileEntry fileEntry) {
		return StringBundler.concat(
			_portal.getPathContext(), "/documents/",
			fileEntry.getRepositoryId(), StringPool.SLASH,
			fileEntry.getFolderId(), StringPool.SLASH,
			URLCodec.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())),
			StringPool.SLASH, fileEntry.getUuid());
	}

	protected JSONObject getJournalArticleJSONObject(AssetEntry assetEntry)
		throws PortalException {

		JournalArticle journalArticle = null;

		_journalArticleModelResourcePermission.check(
			getPermissionChecker(), assetEntry.getClassPK(), ActionKeys.VIEW);

		try {
			journalArticle = _journalArticleLocalService.getArticle(
				assetEntry.getClassPK());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			JournalArticleResource journalArticleResource =
				_journalArticleResourceLocalService.getArticleResource(
					assetEntry.getClassPK());

			journalArticle = _journalArticleLocalService.getLatestArticle(
				journalArticleResource.getGroupId(),
				journalArticleResource.getArticleId());
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			JSONFactoryUtil.looseSerialize(journalArticle));

		JSONObject journalArticleJSONObject = JSONUtil.put(
			"DDMStructure",
			JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerialize(
					journalArticle.getDDMStructure()))
		).put(
			"modelAttributes", jsonObject
		).put(
			"modelValues", jsonObject.getString("content")
		);

		jsonObject.remove("content");

		return journalArticleJSONObject;
	}

	protected JSONObject getUserJSONObject(AssetEntry assetEntry)
		throws PortalException {

		User user = userService.getUserById(assetEntry.getClassPK());

		return JSONUtil.put(
			"user",
			JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerialize(user)));
	}

	protected JSONArray toJSONArray(
			List<AssetEntry> assetEntries, Locale locale)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (AssetEntry assetEntry : assetEntries) {
			JSONObject jsonObject = toJSONObject(assetEntry, locale);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	protected JSONObject toJSONObject(AssetEntry assetEntry, Locale locale)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			JSONFactoryUtil.looseSerialize(assetEntry));

		jsonObject.put(
			"className", assetEntry.getClassName()
		).put(
			"description", assetEntry.getDescription(locale)
		).put(
			"locale", String.valueOf(locale)
		).put(
			"object", getAssetObjectJSONObject(assetEntry, locale)
		).put(
			"summary", assetEntry.getSummary(locale)
		).put(
			"title", assetEntry.getTitle(locale)
		);

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ScreensAssetEntryServiceImpl.class);

	private static volatile ModelResourcePermission<JournalArticle>
		_journalArticleModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				ScreensAssetEntryServiceImpl.class,
				"_journalArticleModelResourcePermission", JournalArticle.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetPublisherHelper _assetPublisherHelper;

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletItemLocalService _portletItemLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private ScreensDDLRecordService _screensDDLRecordService;

}
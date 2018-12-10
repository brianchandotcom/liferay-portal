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

package com.liferay.layout.type.controller.asset.display.internal.portlet;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.AssetDisplayContributorTracker;
import com.liferay.asset.display.contributor.constants.AssetDisplayWebKeys;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.asset.util.AssetHelper;
import com.liferay.layout.type.controller.asset.display.internal.constants.AssetDisplayLayoutTypeControllerConstants;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = FriendlyURLResolver.class)
public class AssetDisplayPageFriendlyURLResolver
	implements FriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		String urlSeparator = getURLSeparator();

		long versionClassPK = 0L;
		long assetEntryId = 0L;

		String path = friendlyURL.substring(urlSeparator.length());

		if (path.indexOf(CharPool.SLASH) != -1) {
			assetEntryId = GetterUtil.getLong(
				path.substring(0, path.indexOf(CharPool.SLASH)));

			versionClassPK = GetterUtil.getLong(
				path.substring(path.indexOf(CharPool.SLASH) + 1));
		}
		else {
			assetEntryId = GetterUtil.getLong(path);
		}

		AssetEntry assetEntry = _assetEntryService.getEntry(assetEntryId);

		AssetDisplayContributor assetDisplayContributor =
			_assetDisplayContributorTracker.getAssetDisplayContributor(
				assetEntry.getClassName());

		if (assetDisplayContributor == null) {
			throw new PortalException();
		}

		HttpServletRequest request = (HttpServletRequest)requestContext.get(
			"request");

		request.setAttribute(
			AssetDisplayWebKeys.ASSET_DISPLAY_CONTRIBUTOR,
			assetDisplayContributor);
		request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);
		request.setAttribute(
			AssetDisplayWebKeys.VERSION_CLASS_PK, versionClassPK);

		Locale locale = _portal.getLocale(request);

		_portal.setPageTitle(assetEntry.getTitle(locale), request);
		_portal.setPageDescription(assetEntry.getDescription(locale), request);

		_portal.setPageKeywords(
			_assetHelper.getAssetKeywords(
				assetEntry.getClassName(), assetEntry.getClassPK()),
			request);

		Layout layout = getAssetDisplayLayout(groupId);

		return _portal.getLayoutActualURL(layout, mainPath);
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		Layout layout = getAssetDisplayLayout(groupId);

		return new LayoutFriendlyURLComposite(layout, friendlyURL);
	}

	@Override
	public String getURLSeparator() {
		return "/a/";
	}

	protected Layout getAssetDisplayLayout(long groupId)
		throws PortalException {

		List<Layout> layouts = _layoutLocalService.getLayouts(
			groupId, false,
			AssetDisplayLayoutTypeControllerConstants.
				LAYOUT_TYPE_ASSET_DISPLAY);

		if (!ListUtil.isEmpty(layouts)) {
			return layouts.get(0);
		}

		return _createAssetDisplayLayout(groupId);
	}

	private Layout _createAssetDisplayLayout(long groupId)
		throws PortalException {

		Group group = _groupLocalService.fetchGroup(groupId);

		long defaultUserId = _userLocalService.getDefaultUserId(
			group.getCompanyId());

		Map<Locale, String> nameMap = new HashMap<>();

		Locale locale = LocaleUtil.getSiteDefault();

		nameMap.put(locale, "Asset Display Page");

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put("visible", Boolean.FALSE.toString());

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		return _layoutLocalService.addLayout(
			defaultUserId, groupId, false, 0, nameMap, null, null, null, null,
			"asset_display", typeSettingsProperties.toString(), true,
			new HashMap<>(), serviceContext);
	}

	@Reference
	private AssetDisplayContributorTracker _assetDisplayContributorTracker;

	@Reference
	private AssetEntryService _assetEntryService;

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}
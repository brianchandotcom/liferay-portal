/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

/**
 * @author Raymond Augé
 */
public class LayoutRevisionAssetRendererFactory
	extends BaseAssetRendererFactory {

	public static final String CLASS_NAME = LayoutRevision.class.getName();

	public static final String TYPE = "layout_revision";

	@Override
	public AssetEntry getAssetEntry(long assetEntryId)
		throws PortalException, SystemException {

		return getAssetEntry(getClassName(), assetEntryId);
	}

	@Override
	public AssetEntry getAssetEntry(String className, long classPK)
		throws PortalException, SystemException {

		LayoutRevision layoutRevision =
			LayoutRevisionLocalServiceUtil.getLayoutRevision(classPK);

		LayoutSetBranch layoutSetBranch =
			LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(
				layoutRevision.getLayoutSetBranchId());

		User user = UserLocalServiceUtil.getUserById(
			layoutRevision.getUserId());

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.createAssetEntry(
			classPK);

		assetEntry.setGroupId(layoutRevision.getGroupId());
		assetEntry.setCompanyId(user.getCompanyId());
		assetEntry.setUserId(user.getUserId());
		assetEntry.setUserName(user.getFullName());
		assetEntry.setCreateDate(layoutRevision.getCreateDate());
		assetEntry.setClassNameId(
			PortalUtil.getClassNameId(LayoutRevision.class.getName()));
		assetEntry.setClassPK(layoutRevision.getLayoutRevisionId());

		StringBundler sb = new StringBundler();

		sb.append(layoutRevision.getHTMLTitle(LocaleUtil.getDefault()));
		sb.append(" [");
		sb.append(layoutSetBranch.getName());
		sb.append("]");

		assetEntry.setTitle(sb.toString());

		return assetEntry;
	}

	public AssetRenderer getAssetRenderer(long layoutRevisionId, int type)
		throws PortalException, SystemException {

		LayoutRevision layoutRevision =
			LayoutRevisionLocalServiceUtil.getLayoutRevision(layoutRevisionId);

		return new LayoutRevisionAssetRenderer(layoutRevision);
	}

	public String getClassName() {
		return CLASS_NAME;
	}

	public String getType() {
		return TYPE;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/pages.png";
	}

}
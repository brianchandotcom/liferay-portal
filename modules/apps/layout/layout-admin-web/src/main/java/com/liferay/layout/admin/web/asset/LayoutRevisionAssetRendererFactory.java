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

package com.liferay.layout.admin.web.asset;

import com.liferay.layout.admin.web.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutRevisionLocalService;
import com.liferay.portal.service.LayoutSetBranchLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.asset.service.AssetEntryLocalService;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + LayoutAdminPortletKeys.LAYOUT_ADMIN},
	service = AssetRendererFactory.class
)
public class LayoutRevisionAssetRendererFactory
	extends BaseAssetRendererFactory<LayoutRevision> {

	public static final String TYPE = "layout_revision";

	public LayoutRevisionAssetRendererFactory() {
		setClassName(LayoutRevision.class.getName());
		setCategorizable(false);
		setPortletId(LayoutAdminPortletKeys.LAYOUT_ADMIN);
		setSelectable(false);
	}

	@Override
	public AssetEntry getAssetEntry(long assetEntryId) throws PortalException {
		return getAssetEntry(getClassName(), assetEntryId);
	}

	@Override
	public AssetEntry getAssetEntry(String className, long classPK)
		throws PortalException {

		LayoutRevision layoutRevision =
			_layoutRevisionLocalService.getLayoutRevision(classPK);

		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.getLayoutSetBranch(
				layoutRevision.getLayoutSetBranchId());

		User user = _userLocalService.getUserById(layoutRevision.getUserId());

		AssetEntry assetEntry = _assetEntryLocalService.createAssetEntry(
			classPK);

		assetEntry.setGroupId(layoutRevision.getGroupId());
		assetEntry.setCompanyId(user.getCompanyId());
		assetEntry.setUserId(user.getUserId());
		assetEntry.setUserName(user.getFullName());
		assetEntry.setCreateDate(layoutRevision.getCreateDate());
		assetEntry.setClassNameId(
			PortalUtil.getClassNameId(LayoutRevision.class.getName()));
		assetEntry.setClassPK(layoutRevision.getLayoutRevisionId());

		StringBundler sb = new StringBundler(4);

		sb.append(layoutRevision.getHTMLTitle(LocaleUtil.getSiteDefault()));
		sb.append(" [");
		sb.append(layoutSetBranch.getName());
		sb.append("]");

		assetEntry.setTitle(sb.toString());

		return assetEntry;
	}

	@Override
	public AssetRenderer<LayoutRevision> getAssetRenderer(
			long layoutRevisionId, int type)
		throws PortalException {

		LayoutRevision layoutRevision =
			_layoutRevisionLocalService.getLayoutRevision(layoutRevisionId);

		LayoutRevisionAssetRenderer layoutRevisionAssetRenderer =
			new LayoutRevisionAssetRenderer(layoutRevision);

		layoutRevisionAssetRenderer.setAssetRendererType(type);
		layoutRevisionAssetRenderer.setServletContext(_servletContext);

		return layoutRevisionAssetRenderer;
	}

	@Override
	public String getClassName() {
		return LayoutRevision.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "icon-file";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.admin.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/pages.png";
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutRevisionLocalService(
		LayoutRevisionLocalService layoutRevisionLocalService) {

		_layoutRevisionLocalService = layoutRevisionLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetBranchLocalService(
		LayoutSetBranchLocalService layoutSetBranchLocalService) {

		_layoutSetBranchLocalService = layoutSetBranchLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private volatile AssetEntryLocalService _assetEntryLocalService;
	private volatile LayoutRevisionLocalService _layoutRevisionLocalService;
	private volatile LayoutSetBranchLocalService _layoutSetBranchLocalService;
	private volatile ServletContext _servletContext;
	private volatile UserLocalService _userLocalService;

}
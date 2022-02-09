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

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.definitions.web.internal.frontend.constants.CommerceProductDataSetConstants;
import com.liferay.commerce.product.definitions.web.internal.model.ProductMedia;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"clay.data.provider.key=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_ATTACHMENTS,
		"clay.data.provider.key=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_IMAGES
	},
	service = ClayDataSetActionProvider.class
)
public class CommerceProductMediaDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		ProductMedia productMedia = (ProductMedia)model;

		CPAttachmentFileEntry cpAttachmentFileEntry =
			_cpAttachmentFileEntryService.getCPAttachmentFileEntry(
				productMedia.getCPAttachmentFileEntryId());

		return DropdownItemListBuilder.add(
			() -> _hasManagePermission(
				cpAttachmentFileEntry,
				PermissionThreadLocal.getPermissionChecker()),
			dropdownItem -> {
				dropdownItem.setHref(
					_getProductMediaEditURL(
						cpAttachmentFileEntry, httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
				dropdownItem.setTarget("sidePanel");
			}
		).add(
			() -> _hasManagePermission(
				cpAttachmentFileEntry,
				PermissionThreadLocal.getPermissionChecker()),
			dropdownItem -> {
				dropdownItem.setHref(
					_getProductMediaDeleteURL(
						cpAttachmentFileEntry.getCPAttachmentFileEntryId(),
						httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).build();
	}

	private PortletURL _getProductMediaDeleteURL(
			long cpAttachmentFileEntryId, HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				_portal.getOriginalServletRequest(httpServletRequest),
				CPPortletKeys.CP_DEFINITIONS, PortletRequest.ACTION_PHASE)
		).setActionName(
			"/cp_definitions/edit_cp_attachment_file_entry"
		).setCMD(
			Constants.DELETE
		).buildPortletURL();

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter(
			"cpAttachmentFileEntryId", String.valueOf(cpAttachmentFileEntryId));

		return portletURL;
	}

	private PortletURL _getProductMediaEditURL(
			CPAttachmentFileEntry cpAttachmentFileEntry,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest, CPDefinition.class.getName(),
				PortletProvider.Action.MANAGE)
		).setMVCRenderCommandName(
			"/cp_definitions/edit_cp_attachment_file_entry"
		).setParameter(
			"cpAttachmentFileEntryId",
			cpAttachmentFileEntry.getCPAttachmentFileEntryId()
		).setParameter(
			"cpDefinitionId", cpAttachmentFileEntry.getClassPK()
		).setParameter(
			"type", cpAttachmentFileEntry.getType()
		).buildPortletURL();

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			_log.error(windowStateException);
		}

		return portletURL;
	}

	private boolean _hasManagePermission(
		CPAttachmentFileEntry cpAttachmentFileEntry,
		PermissionChecker permissionChecker) {

		int type = cpAttachmentFileEntry.getType();

		if (type == CPAttachmentFileEntryConstants.TYPE_IMAGE) {
			return _portletResourcePermission.contains(
				permissionChecker, cpAttachmentFileEntry.getGroupId(),
				CPActionKeys.MANAGE_COMMERCE_PRODUCT_IMAGES);
		}
		else if (type == CPAttachmentFileEntryConstants.TYPE_OTHER) {
			return _portletResourcePermission.contains(
				permissionChecker, cpAttachmentFileEntry.getGroupId(),
				CPActionKeys.MANAGE_COMMERCE_PRODUCT_ATTACHMENTS);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceProductMediaDataSetActionProvider.class);

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + CPConstants.RESOURCE_NAME_PRODUCT + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}
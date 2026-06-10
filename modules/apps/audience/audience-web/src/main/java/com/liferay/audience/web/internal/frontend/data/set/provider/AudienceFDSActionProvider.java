/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.web.internal.frontend.data.set.provider;

import com.liferay.audience.constants.AudienceActionKeys;
import com.liferay.audience.constants.AudiencePortletKeys;
import com.liferay.audience.web.internal.constants.AudienceFDSNames;
import com.liferay.audience.web.internal.frontend.data.set.model.FDSAudienceEntry;
import com.liferay.audience.web.internal.security.permission.resource.AudienceResourcePermission;
import com.liferay.frontend.data.set.provider.FDSActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.Portal;

import jakarta.portlet.PortletURL;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "fds.data.provider.key=" + AudienceFDSNames.AUDIENCE_ENTRIES,
	service = FDSActionProvider.class
)
public class AudienceFDSActionProvider implements FDSActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			long groupId, HttpServletRequest httpServletRequest, Object model)
		throws PortalException {

		FDSAudienceEntry fdsAudienceEntry = (FDSAudienceEntry)model;

		boolean manageAudienceEntries = AudienceResourcePermission.contains(
			PermissionThreadLocal.getPermissionChecker(), groupId,
			AudienceActionKeys.MANAGE_AUDIENCE_ENTRIES);

		return DropdownItemListBuilder.add(
			() -> manageAudienceEntries,
			dropdownItem -> {
				dropdownItem.putData("id", "edit");
				dropdownItem.setHref(
					_getEditURL(
						fdsAudienceEntry.getAudienceEntryId(),
						httpServletRequest));
				dropdownItem.setIcon("pencil");
				dropdownItem.setLabel(
					_language.get(httpServletRequest, "edit"));
			}
		).add(
			() -> manageAudienceEntries,
			dropdownItem -> {
				dropdownItem.putData(
					"confirmationMessage",
					_language.get(
						httpServletRequest,
						"are-you-sure-you-want-to-delete-this"));
				dropdownItem.setHref(
					_getDeleteURL(
						fdsAudienceEntry.getAudienceEntryId(),
						httpServletRequest));
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					_language.get(httpServletRequest, "delete"));
				dropdownItem.setTarget("link");
			}
		).build();
	}

	private PortletURL _getDeleteURL(
		long audienceEntryId, HttpServletRequest httpServletRequest) {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		return PortletURLBuilder.create(
			requestBackedPortletURLFactory.createActionURL(
				AudiencePortletKeys.AUDIENCE)
		).setActionName(
			"/audience/delete_audience_entry"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"audienceEntryId", audienceEntryId
		).buildPortletURL();
	}

	private PortletURL _getEditURL(
		long audienceEntryId, HttpServletRequest httpServletRequest) {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		return PortletURLBuilder.create(
			requestBackedPortletURLFactory.createRenderURL(
				AudiencePortletKeys.AUDIENCE)
		).setMVCRenderCommandName(
			"/audience/edit_audience_entry"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"audienceEntryId", audienceEntryId
		).buildPortletURL();
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}
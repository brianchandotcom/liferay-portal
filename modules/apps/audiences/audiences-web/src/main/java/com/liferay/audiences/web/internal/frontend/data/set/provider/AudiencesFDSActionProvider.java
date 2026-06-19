/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.web.internal.frontend.data.set.provider;

import com.liferay.audiences.constants.AudiencesActionKeys;
import com.liferay.audiences.constants.AudiencesPortletKeys;
import com.liferay.audiences.web.internal.constants.AudiencesFDSNames;
import com.liferay.audiences.web.internal.frontend.data.set.model.FDSAudiencesEntry;
import com.liferay.audiences.web.internal.security.permission.resource.AudiencesResourcePermission;
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
	property = "fds.data.provider.key=" + AudiencesFDSNames.AUDIENCES_ENTRIES,
	service = FDSActionProvider.class
)
public class AudiencesFDSActionProvider implements FDSActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			long groupId, HttpServletRequest httpServletRequest, Object model)
		throws PortalException {

		FDSAudiencesEntry fdsAudiencesEntry = (FDSAudiencesEntry)model;

		boolean manageAudiencesEntries = AudiencesResourcePermission.contains(
			PermissionThreadLocal.getPermissionChecker(), groupId,
			AudiencesActionKeys.MANAGE_AUDIENCES_ENTRIES);

		return DropdownItemListBuilder.add(
			() -> manageAudiencesEntries,
			dropdownItem -> {
				dropdownItem.putData("id", "edit");
				dropdownItem.setHref(
					_getEditURL(
						fdsAudiencesEntry.getAudiencesEntryId(),
						httpServletRequest));
				dropdownItem.setIcon("pencil");
				dropdownItem.setLabel(
					_language.get(httpServletRequest, "edit"));
			}
		).add(
			() -> manageAudiencesEntries,
			dropdownItem -> {
				dropdownItem.putData(
					"confirmationMessage",
					_language.get(
						httpServletRequest,
						"are-you-sure-you-want-to-delete-this"));
				dropdownItem.setHref(
					_getDeleteURL(
						fdsAudiencesEntry.getAudiencesEntryId(),
						httpServletRequest));
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					_language.get(httpServletRequest, "delete"));
				dropdownItem.setTarget("link");
			}
		).build();
	}

	private PortletURL _getDeleteURL(
		long audiencesEntryId, HttpServletRequest httpServletRequest) {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		return PortletURLBuilder.create(
			requestBackedPortletURLFactory.createActionURL(
				AudiencesPortletKeys.AUDIENCES)
		).setActionName(
			"/audiences/delete_audiences_entry"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"audiencesEntryId", audiencesEntryId
		).buildPortletURL();
	}

	private PortletURL _getEditURL(
		long audiencesEntryId, HttpServletRequest httpServletRequest) {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		return PortletURLBuilder.create(
			requestBackedPortletURLFactory.createRenderURL(
				AudiencesPortletKeys.AUDIENCES)
		).setMVCRenderCommandName(
			"/audiences/edit_audiences_entry"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"audiencesEntryId", audiencesEntryId
		).buildPortletURL();
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}
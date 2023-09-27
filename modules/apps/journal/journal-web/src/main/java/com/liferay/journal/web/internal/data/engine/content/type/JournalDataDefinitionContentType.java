/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.data.engine.content.type;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	property = "content.type=journal", service = DataDefinitionContentType.class
)
public class JournalDataDefinitionContentType
	implements DataDefinitionContentType {

	@Override
	public boolean allowInvalidAvailableLocalesForProperty() {
		return true;
	}

	@Override
	public long getClassNameId() {
		return _portal.getClassNameId(JournalArticle.class);
	}

	@Override
	public String getContentType() {
		return "journal";
	}

	@Override
	public String getPortletResourceName() {
		return JournalConstants.RESOURCE_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String resourceName, long primKey, long userId, String actionId)
		throws PortalException {

		if (StringUtil.contains(DDLRecordSet.class.getName(), resourceName)) {
			DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
				primKey);

			DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

			resourceName = ResourceActionsUtil.getCompositeModelName(
				_portal.getClassName(ddmStructure.getClassNameId()),
				DDMStructure.class.getName());

			primKey = ddlRecordSet.getDDMStructureId();
		}

		if (permissionChecker.hasOwnerPermission(
				companyId, resourceName, primKey, userId, actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			groupId, resourceName, primKey, actionId);
	}

	@Override
	public boolean hasPortletPermission(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		if (actionId.equals("ADD_DATA_DEFINITION") ||
			actionId.equals("ADD_DATA_RECORD_COLLECTION")) {

			return _portletResourcePermission.contains(
				permissionChecker, groupId, "ADD_STRUCTURE");
		}

		return _portletResourcePermission.contains(
			permissionChecker, groupId, actionId);
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + JournalConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}
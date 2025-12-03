/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.sales.room.web.internal.application.list;

import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.digital.sales.room.web.internal.constants.DigitalSalesRoomPanelCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = {
		"panel.category.key=" + PanelCategoryKeys.COMMERCE,
		"panel.category.order:Integer=100"
	},
	service = PanelCategory.class
)
public class DigitalSalesRoomManagementPanelCategory extends BasePanelCategory {

	@Override
	public String getKey() {
		return DigitalSalesRoomPanelCategoryKeys.DIGITAL_SALES_ROOM_MANAGEMENT;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "digital-sales-room-management");
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled(
				group.getCompanyId(), "LPD-66359")) {

			return false;
		}

		return super.isShow(permissionChecker, group);
	}

	@Reference
	private Language _language;

}
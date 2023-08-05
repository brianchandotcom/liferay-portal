/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.sites.kernel.util.Sites;

import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class LayoutPrototypeImpl extends LayoutPrototypeBaseImpl {

	@Override
	public Group getGroup() throws PortalException {
		return GroupLocalServiceUtil.getLayoutPrototypeGroup(
			getCompanyId(), getLayoutPrototypeId());
	}

	@Override
	public long getGroupId() throws PortalException {
		Group group = getGroup();

		return group.getGroupId();
	}

	@Override
	public Layout getLayout() throws PortalException {
		Group group = getGroup();

		if (group.getPrivateLayoutsPageCount() > 0) {
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				group.getGroupId(), true);

			return layouts.get(0);
		}

		throw new NoSuchLayoutException("{groupId=" + group.getGroupId() + "}");
	}

	/**
	 * Returns the number of failed merge attempts for the layout prototype
	 * since its last reset or update.
	 *
	 * @return the number of failed merge attempts for the layout prototype
	 */
	@Override
	public int getMergeFailCount() throws PortalException {
		if (getLayoutPrototypeId() == 0) {
			return 0;
		}

		Layout layoutPrototypeLayout = getLayout();

		UnicodeProperties prototypeTypeSettingsUnicodeProperties =
			layoutPrototypeLayout.getTypeSettingsProperties();

		return GetterUtil.getInteger(
			prototypeTypeSettingsUnicodeProperties.getProperty(
				Sites.MERGE_FAIL_COUNT));
	}

	@Override
	public boolean hasSetModifiedDate() {
		return true;
	}

}
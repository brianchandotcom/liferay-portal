/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.internal.group.capability;

import com.liferay.portal.kernel.group.capability.GroupCapability;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.GroupCapabilityContributor;
import com.liferay.staging.StagingGroupHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = GroupCapabilityContributor.class)
public class CompanyGroupCapabilityContributor
	implements GroupCapabilityContributor {

	@Override
	public GroupCapability getGroupCapability(Group group) {
		if (!_stagingGroupHelper.isCompanyGroup(group)) {
			return null;
		}

		return new GroupCapability() {

			@Override
			public boolean isSupportPortlet(Portlet portlet) {
				return true;
			}

			@Override
			public boolean isSupportScope() {
				return false;
			}

			@Override
			public boolean isSupportsPages() {
				return false;
			}

		};
	}

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}
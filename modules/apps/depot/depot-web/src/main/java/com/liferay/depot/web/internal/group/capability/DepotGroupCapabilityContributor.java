/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.group.capability;

import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.portal.kernel.group.capability.GroupCapability;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.GroupCapabilityContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = GroupCapabilityContributor.class)
public class DepotGroupCapabilityContributor
	implements GroupCapabilityContributor {

	@Override
	public GroupCapability getGroupCapability(Group group) {
		if (!group.isDepot()) {
			return null;
		}

		return new GroupCapability() {

			@Override
			public boolean isSupportPortlet(Portlet portlet) {
				return _depotApplicationController.isEnabled(
					portlet.getPortletId(), group.getGroupId());
			}

			@Override
			public boolean isSupportsPages() {
				return false;
			}

		};
	}

	@Reference
	private DepotApplicationController _depotApplicationController;

}
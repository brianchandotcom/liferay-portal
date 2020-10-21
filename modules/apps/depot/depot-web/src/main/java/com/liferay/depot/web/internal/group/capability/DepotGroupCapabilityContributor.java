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

package com.liferay.depot.web.internal.group.capability;

import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.portal.kernel.group.capability.GroupCapability;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.GroupCapabilityContributor;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = GroupCapabilityContributor.class)
public class DepotGroupCapabilityContributor
	implements GroupCapabilityContributor {

	@Override
	public Optional<GroupCapability> getGroupCapabilityOptional(Group group) {
		if (!group.isDepot()) {
			return Optional.empty();
		}

		return Optional.of(
			new GroupCapability() {

				@Override
				public boolean isSupportPortlet(Portlet portlet) {
					return _depotApplicationController.isEnabled(
						portlet.getPortletId(), group.getGroupId());
				}

				@Override
				public boolean isSupportsPages() {
					return false;
				}

			});
	}

	@Reference
	private DepotApplicationController _depotApplicationController;

}
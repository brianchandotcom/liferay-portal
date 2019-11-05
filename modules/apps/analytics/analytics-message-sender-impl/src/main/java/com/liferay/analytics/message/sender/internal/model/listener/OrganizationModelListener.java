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

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalService;

import java.util.ArrayList;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = ModelListener.class)
public class OrganizationModelListener
	extends BaseEntityModelListener<Organization> {

	@Override
	public String getObjectType() {
		return "organization";
	}

	@Override
	public void onAfterCreate(Organization organization)
		throws ModelListenerException {

		send("add", organization);
	}

	@Override
	public void onBeforeRemove(Organization organization)
		throws ModelListenerException {

		send("delete", organization);
	}

	@Override
	public void onBeforeUpdate(Organization newOrganization)
		throws ModelListenerException {

		try {
			Organization oldOrganization =
				_organizationLocalService.getOrganization(
					newOrganization.getOrganizationId());

			if (_equals(newOrganization, oldOrganization)) {
				return;
			}

			send("update", newOrganization);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	private boolean _equals(
		Organization newOrganization, Organization oldOrganization) {

		Set<String> modifiedAttributes = getModifiedAttributes(
			new ArrayList<String>() {
				{
					add("comments");
					add("countryId");
					add("name");
					add("parentOrganizationId");
					add("regionId");
				}
			},
			newOrganization, oldOrganization);

		if (!modifiedAttributes.isEmpty()) {
			return false;
		}

		return true;
	}

	@Reference
	private OrganizationLocalService _organizationLocalService;

}
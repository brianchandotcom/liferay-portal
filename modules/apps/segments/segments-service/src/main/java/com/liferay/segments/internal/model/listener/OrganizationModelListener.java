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

package com.liferay.segments.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.segments.provider.SegmentsEntryProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = ModelListener.class)
public class OrganizationModelListener extends BaseModelListener<Organization> {

	@Override
	public void onBeforeRemove(Organization organization)
		throws ModelListenerException {

		clearCache(organization);
	}

	@Override
	public void onBeforeUpdate(Organization organization)
		throws ModelListenerException {

		clearCache(organization);
	}

	protected void clearCache(Organization organization) {
		if (organization == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Clearing segments entry cache for organization " +
					organization.getOrganizationId());
		}

		_segmentsEntryProvider.clearCache(
			Organization.class.getName(), organization.getOrganizationId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationModelListener.class);

	@Reference
	private SegmentsEntryProvider _segmentsEntryProvider;

}
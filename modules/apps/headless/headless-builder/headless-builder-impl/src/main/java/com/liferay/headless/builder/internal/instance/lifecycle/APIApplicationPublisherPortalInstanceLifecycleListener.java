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

package com.liferay.headless.builder.internal.instance.lifecycle;

import com.liferay.headless.builder.application.provider.APIApplicationProvider;
import com.liferay.headless.builder.application.publisher.APIApplicationPublisher;
import com.liferay.headless.builder.internal.helper.ObjectEntryHelper;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class APIApplicationPublisherPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		List<ObjectEntry> apiApplicationObjectEntries =
			_objectEntryHelper.getObjectEntries(
				company.getCompanyId(), "applicationStatus eq 'published'",
				"L_API_APPLICATION");

		if (apiApplicationObjectEntries == null) {
			return;
		}

		for (ObjectEntry apiApplicationObjectEntry :
				apiApplicationObjectEntries) {

			Map<String, Object> properties =
				apiApplicationObjectEntry.getProperties();

			_apiApplicationPublisher.publish(
				_apiApplicationProvider.fetchAPIApplication(
					(String)properties.get("baseURL"), company.getCompanyId()));
		}
	}

	@Reference
	private APIApplicationProvider _apiApplicationProvider;

	@Reference
	private APIApplicationPublisher _apiApplicationPublisher;

	@Reference
	private ObjectEntryHelper _objectEntryHelper;

}
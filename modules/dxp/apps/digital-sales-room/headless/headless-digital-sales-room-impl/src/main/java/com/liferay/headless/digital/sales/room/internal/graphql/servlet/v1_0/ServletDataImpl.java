/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.graphql.servlet.v1_0;

import com.liferay.headless.digital.sales.room.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.digital.sales.room.internal.graphql.query.v1_0.Query;
import com.liferay.headless.digital.sales.room.internal.resource.v1_0.DigitalSalesRoomResourceImpl;
import com.liferay.headless.digital.sales.room.resource.v1_0.DigitalSalesRoomResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import jakarta.annotation.Generated;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Stefano Motta
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setDigitalSalesRoomResourceComponentServiceObjects(
			_digitalSalesRoomResourceComponentServiceObjects);

		Query.setDigitalSalesRoomResourceComponentServiceObjects(
			_digitalSalesRoomResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Digital.Sales.Room";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-digital-sales-room-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#patchDigitalSalesRoom",
						new ObjectValuePair<>(
							DigitalSalesRoomResourceImpl.class,
							"patchDigitalSalesRoom"));
					put(
						"mutation#createDigitalSalesRoom",
						new ObjectValuePair<>(
							DigitalSalesRoomResourceImpl.class,
							"postDigitalSalesRoom"));

					put(
						"query#digitalSalesRoom",
						new ObjectValuePair<>(
							DigitalSalesRoomResourceImpl.class,
							"getDigitalSalesRoom"));
					put(
						"query#digitalSalesRooms",
						new ObjectValuePair<>(
							DigitalSalesRoomResourceImpl.class,
							"getDigitalSalesRoomsPage"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DigitalSalesRoomResource>
		_digitalSalesRoomResourceComponentServiceObjects;

}
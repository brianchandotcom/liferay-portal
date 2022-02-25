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

package com.liferay.headless.commerce.admin.shipment.resource.v1_0;

import com.liferay.headless.commerce.admin.shipment.dto.v1_0.Shipment;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.annotation.versioning.ProviderType;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-commerce-admin-shipment/v1.0
 *
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
@ProviderType
public interface ShipmentResource {

	public static Builder builder() {
		return FactoryHolder.factory.create();
	}

	public Page<Shipment> getShipmentsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public Shipment postShipment(Shipment shipment) throws Exception;

	public Response postShipmentBatch(String callbackURL, Object object)
		throws Exception;

	public void deleteShipmentByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception;

	public Shipment getShipmentByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception;

	public Shipment patchShipmentByExternalReferenceCode(
			String externalReferenceCode, Shipment shipment)
		throws Exception;

	public Shipment putShipmentByExternalReferenceCode(
			String externalReferenceCode, Shipment shipment)
		throws Exception;

	public Shipment postShipmentByExternalReferenceCodeStatusDelivered(
			String externalReferenceCode)
		throws Exception;

	public Shipment postShipmentByExternalReferenceCodeStatusFinishProcessing(
			String externalReferenceCode)
		throws Exception;

	public Shipment postShipmentByExternalReferenceCodeStatusShipped(
			String externalReferenceCode)
		throws Exception;

	public void deleteShipment(Long shipmentId) throws Exception;

	public Response deleteShipmentBatch(String callbackURL, Object object)
		throws Exception;

	public Shipment getShipment(Long shipmentId) throws Exception;

	public Shipment patchShipment(Long shipmentId, Shipment shipment)
		throws Exception;

	public Shipment postShipmentStatusDelivered(Long shipmentId)
		throws Exception;

	public Shipment postShipmentStatusFinishProcessing(Long shipmentId)
		throws Exception;

	public Shipment postShipmentStatusShipped(Long shipmentId) throws Exception;

	public default void setContextAcceptLanguage(
		AcceptLanguage contextAcceptLanguage) {
	}

	public void setContextCompany(
		com.liferay.portal.kernel.model.Company contextCompany);

	public default void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {
	}

	public default void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {
	}

	public default void setContextUriInfo(UriInfo contextUriInfo) {
	}

	public void setContextUser(
		com.liferay.portal.kernel.model.User contextUser);

	public void setExpressionConvert(
		ExpressionConvert<Filter> expressionConvert);

	public void setFilterParserProvider(
		FilterParserProvider filterParserProvider);

	public void setGroupLocalService(GroupLocalService groupLocalService);

	public void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService);

	public void setResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService);

	public void setRoleLocalService(RoleLocalService roleLocalService);

	public default Filter toFilter(String filterString) {
		return toFilter(
			filterString, Collections.<String, List<String>>emptyMap());
	}

	public default Filter toFilter(
		String filterString, Map<String, List<String>> multivaluedMap) {

		return null;
	}

	public static class FactoryHolder {

		public static volatile Factory factory;

	}

	@ProviderType
	public interface Builder {

		public ShipmentResource build();

		public Builder checkPermissions(boolean checkPermissions);

		public Builder httpServletRequest(
			HttpServletRequest httpServletRequest);

		public Builder httpServletResponse(
			HttpServletResponse httpServletResponse);

		public Builder preferredLocale(Locale preferredLocale);

		public Builder user(com.liferay.portal.kernel.model.User user);

	}

	@ProviderType
	public interface Factory {

		public Builder create();

	}

}
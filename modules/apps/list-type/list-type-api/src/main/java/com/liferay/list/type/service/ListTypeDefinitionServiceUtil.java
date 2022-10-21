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

package com.liferay.list.type.service;

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for ListTypeDefinition. This utility wraps
 * <code>com.liferay.list.type.service.impl.ListTypeDefinitionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Gabriel Albuquerque
 * @see ListTypeDefinitionService
 * @generated
 */
public class ListTypeDefinitionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.list.type.service.impl.ListTypeDefinitionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ListTypeDefinition addListTypeDefinition(
			Map<java.util.Locale, String> nameMap)
		throws PortalException {

		return getService().addListTypeDefinition(nameMap);
	}

	public static ListTypeDefinition deleteListTypeDefinition(
			ListTypeDefinition listTypeDefinition)
		throws PortalException {

		return getService().deleteListTypeDefinition(listTypeDefinition);
	}

	public static ListTypeDefinition deleteListTypeDefinition(
			long listTypeDefinitionId)
		throws PortalException {

		return getService().deleteListTypeDefinition(listTypeDefinitionId);
	}

	public static ListTypeDefinition getListTypeDefinition(
			long listTypeDefinitionId)
		throws PortalException {

		return getService().getListTypeDefinition(listTypeDefinitionId);
	}

	public static List<ListTypeDefinition> getListTypeDefinitions(
		int start, int end) {

		return getService().getListTypeDefinitions(start, end);
	}

	public static int getListTypeDefinitionsCount() {
		return getService().getListTypeDefinitionsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ListTypeDefinition updateListTypeDefinition(
			String externalReferenceCode, long listTypeDefinitionId,
			Map<java.util.Locale, String> nameMap)
		throws PortalException {

		return getService().updateListTypeDefinition(
			externalReferenceCode, listTypeDefinitionId, nameMap);
	}

	public static ListTypeDefinitionService getService() {
		return _service;
	}

	private static volatile ListTypeDefinitionService _service;

}
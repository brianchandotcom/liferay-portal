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

package com.liferay.commerce.shop.by.diagram.service;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CSDiagramEntry. This utility wraps
 * <code>com.liferay.commerce.shop.by.diagram.service.impl.CSDiagramEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramEntryService
 * @generated
 */
public class CSDiagramEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.shop.by.diagram.service.impl.CSDiagramEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CSDiagramEntry addCSDiagramEntry(
			long cpDefinitionId, long cpInstanceId, long cProductId,
			boolean diagram, int quantity, String sequence, String sku,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCSDiagramEntry(
			cpDefinitionId, cpInstanceId, cProductId, diagram, quantity,
			sequence, sku, serviceContext);
	}

	public static void deleteCSDiagramEntries(long cpDefinitionId)
		throws PortalException {

		getService().deleteCSDiagramEntries(cpDefinitionId);
	}

	public static void deleteCSDiagramEntry(CSDiagramEntry csDiagramEntry)
		throws PortalException {

		getService().deleteCSDiagramEntry(csDiagramEntry);
	}

	public static CSDiagramEntry fetchCSDiagramEntry(
			long cpDefinitionId, String sequence)
		throws PortalException {

		return getService().fetchCSDiagramEntry(cpDefinitionId, sequence);
	}

	public static List<CSDiagramEntry> getCSDiagramEntries(
			long cpDefinitionId, int start, int end)
		throws PortalException {

		return getService().getCSDiagramEntries(cpDefinitionId, start, end);
	}

	public static int getCSDiagramEntriesCount(long cpDefinitionId)
		throws PortalException {

		return getService().getCSDiagramEntriesCount(cpDefinitionId);
	}

	public static CSDiagramEntry getCSDiagramEntry(long csDiagramEntryId)
		throws PortalException {

		return getService().getCSDiagramEntry(csDiagramEntryId);
	}

	public static CSDiagramEntry getCSDiagramEntry(
			long cpDefinitionId, String sequence)
		throws PortalException {

		return getService().getCSDiagramEntry(cpDefinitionId, sequence);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CSDiagramEntry updateCSDiagramEntry(
			long csDiagramEntryId, long cpInstanceId, long cProductId,
			boolean diagram, int quantity, String sequence, String sku,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCSDiagramEntry(
			csDiagramEntryId, cpInstanceId, cProductId, diagram, quantity,
			sequence, sku, serviceContext);
	}

	public static CSDiagramEntryService getService() {
		return _service;
	}

	private static volatile CSDiagramEntryService _service;

}
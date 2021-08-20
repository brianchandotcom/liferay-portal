/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.service;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CPDefinitionDiagramEntry. This utility wraps
 * <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramEntryService
 * @generated
 */
public class CPDefinitionDiagramEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CPDefinitionDiagramEntry addCPDefinitionDiagramEntry(
			long cpDefinitionId, String cpInstanceUuid, long cProductId,
			boolean diagram, int number, String sku, String sequence,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCPDefinitionDiagramEntry(
			cpDefinitionId, cpInstanceUuid, cProductId, diagram, number, sku,
			sequence, serviceContext);
	}

	public static void deleteCPDefinitionDiagramEntry(
			long cpDefinitionDiagramEntryId)
		throws PortalException {

		getService().deleteCPDefinitionDiagramEntry(cpDefinitionDiagramEntryId);
	}

	public static CPDefinitionDiagramEntry fetchCPDefinitionDiagramEntry(
			long cpDefinitionId, String sequence)
		throws PortalException {

		return getService().fetchCPDefinitionDiagramEntry(
			cpDefinitionId, sequence);
	}

	public static List<CPDefinitionDiagramEntry> getCPDefinitionDiagramEntries(
			long cpDefinitionId, int start, int end)
		throws PortalException {

		return getService().getCPDefinitionDiagramEntries(
			cpDefinitionId, start, end);
	}

	public static int getCPDefinitionDiagramEntriesCount(long cpDefinitionId)
		throws PortalException {

		return getService().getCPDefinitionDiagramEntriesCount(cpDefinitionId);
	}

	public static CPDefinitionDiagramEntry getCPDefinitionDiagramEntry(
			long cpDefinitionDiagramEntryId)
		throws PortalException {

		return getService().getCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntryId);
	}

	public static CPDefinitionDiagramEntry getCPDefinitionDiagramEntry(
			long cpDefinitionId, String sequence)
		throws PortalException {

		return getService().getCPDefinitionDiagramEntry(
			cpDefinitionId, sequence);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPDefinitionDiagramEntry updateCPDefinitionDiagramEntry(
			long cpDefinitionDiagramEntryId, String cpInstanceUuid,
			long cProductId, boolean diagram, int number, String sku,
			String sequence,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntryId, cpInstanceUuid, cProductId, diagram,
			number, sku, sequence, serviceContext);
	}

	public static CPDefinitionDiagramEntryService getService() {
		return _service;
	}

	private static volatile CPDefinitionDiagramEntryService _service;

}
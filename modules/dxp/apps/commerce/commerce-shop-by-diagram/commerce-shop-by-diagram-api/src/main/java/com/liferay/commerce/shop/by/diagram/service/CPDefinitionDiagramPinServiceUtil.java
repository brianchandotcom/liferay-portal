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

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CPDefinitionDiagramPin. This utility wraps
 * <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramPinServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramPinService
 * @generated
 */
public class CPDefinitionDiagramPinServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramPinServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CPDefinitionDiagramPin addCPDefinitionDiagramPin(
			long cpDefinitionId, double positionX, double positionY,
			String sequence)
		throws PortalException {

		return getService().addCPDefinitionDiagramPin(
			cpDefinitionId, positionX, positionY, sequence);
	}

	public static void deleteCPDefinitionDiagramPin(
			long cpDefinitionDiagramPinId)
		throws PortalException {

		getService().deleteCPDefinitionDiagramPin(cpDefinitionDiagramPinId);
	}

	public static CPDefinitionDiagramPin getCPDefinitionDiagramPin(
			long cpDefinitionDiagramPinId)
		throws PortalException {

		return getService().getCPDefinitionDiagramPin(cpDefinitionDiagramPinId);
	}

	public static List<CPDefinitionDiagramPin> getCPDefinitionDiagramPins(
			long cpDefinitionId, int start, int end)
		throws PortalException {

		return getService().getCPDefinitionDiagramPins(
			cpDefinitionId, start, end);
	}

	public static int getCPDefinitionDiagramPinsCount(long cpDefinitionId)
		throws PortalException {

		return getService().getCPDefinitionDiagramPinsCount(cpDefinitionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPDefinitionDiagramPin updateCPDefinitionDiagramPin(
			long cpDefinitionDiagramPinId, double positionX, double positionY,
			String sequence)
		throws PortalException {

		return getService().updateCPDefinitionDiagramPin(
			cpDefinitionDiagramPinId, positionX, positionY, sequence);
	}

	public static CPDefinitionDiagramPinService getService() {
		return _service;
	}

	private static volatile CPDefinitionDiagramPinService _service;

}
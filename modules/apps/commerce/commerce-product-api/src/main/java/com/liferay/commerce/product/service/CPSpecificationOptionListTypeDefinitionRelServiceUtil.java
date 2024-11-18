/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPSpecificationOptionListTypeDefinitionRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * Provides the remote service utility for CPSpecificationOptionListTypeDefinitionRel. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPSpecificationOptionListTypeDefinitionRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPSpecificationOptionListTypeDefinitionRelService
 * @generated
 */
public class CPSpecificationOptionListTypeDefinitionRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPSpecificationOptionListTypeDefinitionRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CPSpecificationOptionListTypeDefinitionRel
			addCPSpecificationOptionListTypeDefinitionRel(
				long cpSpecificationOptionId, long listTypeDefinitionId)
		throws PortalException {

		return getService().addCPSpecificationOptionListTypeDefinitionRel(
			cpSpecificationOptionId, listTypeDefinitionId);
	}

	public static void deleteCPSpecificationOptionListTypeDefinitionRel(
			long cpSpecificationOptionId, long listTypeDefinitionId)
		throws PortalException {

		getService().deleteCPSpecificationOptionListTypeDefinitionRel(
			cpSpecificationOptionId, listTypeDefinitionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPSpecificationOptionListTypeDefinitionRelService
		getService() {

		return _serviceSnapshot.get();
	}

	private static final Snapshot
		<CPSpecificationOptionListTypeDefinitionRelService> _serviceSnapshot =
			new Snapshot<>(
				CPSpecificationOptionListTypeDefinitionRelServiceUtil.class,
				CPSpecificationOptionListTypeDefinitionRelService.class);

}
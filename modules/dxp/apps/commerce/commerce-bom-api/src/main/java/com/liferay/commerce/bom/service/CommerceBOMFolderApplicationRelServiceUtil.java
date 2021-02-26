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

package com.liferay.commerce.bom.service;

import com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CommerceBOMFolderApplicationRel. This utility wraps
 * <code>com.liferay.commerce.bom.service.impl.CommerceBOMFolderApplicationRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommerceBOMFolderApplicationRelService
 * @generated
 */
public class CommerceBOMFolderApplicationRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.bom.service.impl.CommerceBOMFolderApplicationRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceBOMFolderApplicationRel
			addCommerceBOMFolderApplicationRel(
				long userId, long commerceBOMFolderId,
				long commerceApplicationModelId)
		throws PortalException {

		return getService().addCommerceBOMFolderApplicationRel(
			userId, commerceBOMFolderId, commerceApplicationModelId);
	}

	public static void deleteCommerceBOMFolderApplicationRel(
			long commerceBOMFolderApplicationRelId)
		throws PortalException {

		getService().deleteCommerceBOMFolderApplicationRel(
			commerceBOMFolderApplicationRelId);
	}

	public static List<CommerceBOMFolderApplicationRel>
			getCommerceBOMFolderApplicationRelsByCAMId(
				long commerceApplicationModelId, int start, int end)
		throws PortalException {

		return getService().getCommerceBOMFolderApplicationRelsByCAMId(
			commerceApplicationModelId, start, end);
	}

	public static List<CommerceBOMFolderApplicationRel>
			getCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
				long commerceBOMFolderId, int start, int end)
		throws PortalException {

		return getService().
			getCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
				commerceBOMFolderId, start, end);
	}

	public static int getCommerceBOMFolderApplicationRelsCountByCAMId(
			long commerceApplicationModelId)
		throws PortalException {

		return getService().getCommerceBOMFolderApplicationRelsCountByCAMId(
			commerceApplicationModelId);
	}

	public static int
			getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderId(
				long commerceBOMFolderId)
		throws PortalException {

		return getService().
			getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderId(
				commerceBOMFolderId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceBOMFolderApplicationRelService getService() {
		return _service;
	}

	private static volatile CommerceBOMFolderApplicationRelService _service;

}
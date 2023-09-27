/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CTCollection. This utility wraps
 * <code>com.liferay.change.tracking.service.impl.CTCollectionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see CTCollectionService
 * @generated
 */
public class CTCollectionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTCollectionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CTCollection addCTCollection(
			String externalReferenceCode, long companyId, long userId,
			long ctRemoteId, String name, String description)
		throws PortalException {

		return getService().addCTCollection(
			externalReferenceCode, companyId, userId, ctRemoteId, name,
			description);
	}

	public static void deleteCTAutoResolutionInfo(long ctAutoResolutionInfoId)
		throws PortalException {

		getService().deleteCTAutoResolutionInfo(ctAutoResolutionInfoId);
	}

	public static CTCollection deleteCTCollection(CTCollection ctCollection)
		throws PortalException {

		return getService().deleteCTCollection(ctCollection);
	}

	public static void discardCTEntry(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws PortalException {

		getService().discardCTEntry(
			ctCollectionId, modelClassNameId, modelClassPK);
	}

	public static List<CTCollection> getCTCollections(
		long companyId, int[] statuses, int start, int end,
		OrderByComparator<CTCollection> orderByComparator) {

		return getService().getCTCollections(
			companyId, statuses, start, end, orderByComparator);
	}

	public static List<CTCollection> getCTCollections(
		long companyId, int[] statuses, String keywords, int start, int end,
		OrderByComparator<CTCollection> orderByComparator) {

		return getService().getCTCollections(
			companyId, statuses, keywords, start, end, orderByComparator);
	}

	public static int getCTCollectionsCount(
		long companyId, int[] statuses, String keywords) {

		return getService().getCTCollectionsCount(
			companyId, statuses, keywords);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void moveCTEntry(
			long fromCTCollectionId, long toCTCollectionId,
			long modelClassNameId, long modelClassPK)
		throws PortalException {

		getService().moveCTEntry(
			fromCTCollectionId, toCTCollectionId, modelClassNameId,
			modelClassPK);
	}

	public static void publishCTCollection(long userId, long ctCollectionId)
		throws PortalException {

		getService().publishCTCollection(userId, ctCollectionId);
	}

	public static CTCollection undoCTCollection(
			long ctCollectionId, long userId, String name, String description)
		throws PortalException {

		return getService().undoCTCollection(
			ctCollectionId, userId, name, description);
	}

	public static CTCollection updateCTCollection(
			long userId, long ctCollectionId, String name, String description)
		throws PortalException {

		return getService().updateCTCollection(
			userId, ctCollectionId, name, description);
	}

	public static CTCollectionService getService() {
		return _service;
	}

	public static void setService(CTCollectionService service) {
		_service = service;
	}

	private static volatile CTCollectionService _service;

}
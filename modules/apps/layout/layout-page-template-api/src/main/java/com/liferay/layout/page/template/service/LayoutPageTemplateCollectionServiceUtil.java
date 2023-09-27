/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.service;

import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for LayoutPageTemplateCollection. This utility wraps
 * <code>com.liferay.layout.page.template.service.impl.LayoutPageTemplateCollectionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateCollectionService
 * @generated
 */
public class LayoutPageTemplateCollectionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.layout.page.template.service.impl.LayoutPageTemplateCollectionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static LayoutPageTemplateCollection addLayoutPageTemplateCollection(
			long groupId, long parentLayoutPageTemplateCollection, String name,
			String description, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addLayoutPageTemplateCollection(
			groupId, parentLayoutPageTemplateCollection, name, description,
			type, serviceContext);
	}

	public static LayoutPageTemplateCollection
			deleteLayoutPageTemplateCollection(
				long layoutPageTemplateCollectionId)
		throws PortalException {

		return getService().deleteLayoutPageTemplateCollection(
			layoutPageTemplateCollectionId);
	}

	public static void deleteLayoutPageTemplateCollections(
			long[] layoutPageTemplateCollectionIds)
		throws PortalException {

		getService().deleteLayoutPageTemplateCollections(
			layoutPageTemplateCollectionIds);
	}

	public static LayoutPageTemplateCollection
			fetchLayoutPageTemplateCollection(
				long layoutPageTemplateCollectionId)
		throws PortalException {

		return getService().fetchLayoutPageTemplateCollection(
			layoutPageTemplateCollectionId);
	}

	public static List<LayoutPageTemplateCollection>
		getLayoutPageTemplateCollections(long groupId, int type) {

		return getService().getLayoutPageTemplateCollections(groupId, type);
	}

	public static List<LayoutPageTemplateCollection>
		getLayoutPageTemplateCollections(
			long groupId, int type, int start, int end) {

		return getService().getLayoutPageTemplateCollections(
			groupId, type, start, end);
	}

	public static List<LayoutPageTemplateCollection>
		getLayoutPageTemplateCollections(
			long groupId, int type, int start, int end,
			OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {

		return getService().getLayoutPageTemplateCollections(
			groupId, type, start, end, orderByComparator);
	}

	public static List<LayoutPageTemplateCollection>
		getLayoutPageTemplateCollections(
			long groupId, String name, int type, int start, int end,
			OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {

		return getService().getLayoutPageTemplateCollections(
			groupId, name, type, start, end, orderByComparator);
	}

	public static int getLayoutPageTemplateCollectionsCount(
		long groupId, int type) {

		return getService().getLayoutPageTemplateCollectionsCount(
			groupId, type);
	}

	public static int getLayoutPageTemplateCollectionsCount(
		long groupId, String name, int type) {

		return getService().getLayoutPageTemplateCollectionsCount(
			groupId, name, type);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static LayoutPageTemplateCollection
			updateLayoutPageTemplateCollection(
				long layoutPageTemplateCollectionId, String name,
				String description)
		throws PortalException {

		return getService().updateLayoutPageTemplateCollection(
			layoutPageTemplateCollectionId, name, description);
	}

	public static LayoutPageTemplateCollectionService getService() {
		return _service;
	}

	public static void setService(LayoutPageTemplateCollectionService service) {
		_service = service;
	}

	private static volatile LayoutPageTemplateCollectionService _service;

}
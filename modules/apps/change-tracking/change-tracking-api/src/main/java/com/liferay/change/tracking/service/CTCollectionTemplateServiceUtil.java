/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service;

import com.liferay.change.tracking.model.CTCollectionTemplate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CTCollectionTemplate. This utility wraps
 * <code>com.liferay.change.tracking.service.impl.CTCollectionTemplateServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see CTCollectionTemplateService
 * @generated
 */
public class CTCollectionTemplateServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTCollectionTemplateServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CTCollectionTemplate addCTCollectionTemplate(
			String name, String description, String json)
		throws PortalException {

		return getService().addCTCollectionTemplate(name, description, json);
	}

	public static List<CTCollectionTemplate> getCTCollectionTemplates(
		String keywords, int start, int end,
		OrderByComparator<CTCollectionTemplate> orderByComparator) {

		return getService().getCTCollectionTemplates(
			keywords, start, end, orderByComparator);
	}

	public static int getCTCollectionTemplatesCount(String keywords) {
		return getService().getCTCollectionTemplatesCount(keywords);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CTCollectionTemplate updateCTCollectionTemplate(
			long ctCollectionTemplateId, String name, String description,
			String json)
		throws PortalException {

		return getService().updateCTCollectionTemplate(
			ctCollectionTemplateId, name, description, json);
	}

	public static CTCollectionTemplateService getService() {
		return _service;
	}

	public static void setService(CTCollectionTemplateService service) {
		_service = service;
	}

	private static volatile CTCollectionTemplateService _service;

}
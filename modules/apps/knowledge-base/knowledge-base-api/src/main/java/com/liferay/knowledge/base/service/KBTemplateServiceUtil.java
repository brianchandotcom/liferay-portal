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

package com.liferay.knowledge.base.service;

import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for KBTemplate. This utility wraps
 * <code>com.liferay.knowledge.base.service.impl.KBTemplateServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see KBTemplateService
 * @generated
 */
public class KBTemplateServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.knowledge.base.service.impl.KBTemplateServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static KBTemplate addKBTemplate(
			String portletId, String title, String content,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addKBTemplate(
			portletId, title, content, serviceContext);
	}

	public static KBTemplate deleteKBTemplate(long kbTemplateId)
		throws PortalException {

		return getService().deleteKBTemplate(kbTemplateId);
	}

	public static void deleteKBTemplates(long groupId, long[] kbTemplateIds)
		throws PortalException {

		getService().deleteKBTemplates(groupId, kbTemplateIds);
	}

	public static List<KBTemplate> getGroupKBTemplates(
		long groupId, int start, int end,
		OrderByComparator<KBTemplate> orderByComparator) {

		return getService().getGroupKBTemplates(
			groupId, start, end, orderByComparator);
	}

	public static int getGroupKBTemplatesCount(long groupId) {
		return getService().getGroupKBTemplatesCount(groupId);
	}

	public static KBTemplate getKBTemplate(long kbTemplateId)
		throws PortalException {

		return getService().getKBTemplate(kbTemplateId);
	}

	public static com.liferay.knowledge.base.model.KBTemplateSearchDisplay
			getKBTemplateSearchDisplay(
				long groupId, String title, String content,
				java.util.Date startDate, java.util.Date endDate,
				boolean andOperator, int[] curStartValues, int cur, int delta,
				OrderByComparator<KBTemplate> orderByComparator)
		throws PortalException {

		return getService().getKBTemplateSearchDisplay(
			groupId, title, content, startDate, endDate, andOperator,
			curStartValues, cur, delta, orderByComparator);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static KBTemplate updateKBTemplate(
			long kbTemplateId, String title, String content,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateKBTemplate(
			kbTemplateId, title, content, serviceContext);
	}

	public static KBTemplateService getService() {
		return _service;
	}

	public static void setService(KBTemplateService service) {
		_service = service;
	}

	private static volatile KBTemplateService _service;

}
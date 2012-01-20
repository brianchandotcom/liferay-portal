/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the polls choice remote service. This utility wraps {@link com.liferay.portlet.polls.service.impl.PollsChoiceServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PollsChoiceService
 * @see com.liferay.portlet.polls.service.base.PollsChoiceServiceBaseImpl
 * @see com.liferay.portlet.polls.service.impl.PollsChoiceServiceImpl
 * @generated
 */
public class PollsChoiceServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.polls.service.impl.PollsChoiceServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static PollsChoiceService getService() {
		if (_service == null) {
			_service = (PollsChoiceService)PortalBeanLocatorUtil.locate(PollsChoiceService.class.getName());

			ReferenceRegistry.registerReference(PollsChoiceServiceUtil.class,
				"_service");
			MethodCache.remove(PollsChoiceService.class);
		}

		return _service;
	}

	public void setService(PollsChoiceService service) {
		MethodCache.remove(PollsChoiceService.class);

		_service = service;

		ReferenceRegistry.registerReference(PollsChoiceServiceUtil.class,
			"_service");
		MethodCache.remove(PollsChoiceService.class);
	}

	private static PollsChoiceService _service;
}
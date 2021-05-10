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

package com.liferay.frontend.view.state.service.impl;

import com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry;
import com.liferay.frontend.view.state.service.base.FrontendViewStateActiveEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the frontend view state active entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.frontend.view.state.service.FrontendViewStateActiveEntryLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FrontendViewStateActiveEntryLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry",
	service = AopService.class
)
public class FrontendViewStateActiveEntryLocalServiceImpl
	extends FrontendViewStateActiveEntryLocalServiceBaseImpl {

	public FrontendViewStateActiveEntry createFrontendViewStateActiveEntry(
		String datasetDisplayId, long frontendViewStateEntryId, long plid,
		String portletId, long userId) {

		FrontendViewStateActiveEntry frontendViewStateActiveEntry =
			frontendViewStateActiveEntryLocalService.
				createFrontendViewStateActiveEntry(
					counterLocalService.increment());

		frontendViewStateActiveEntry.setUserId(userId);
		frontendViewStateActiveEntry.setDatasetDisplayId(datasetDisplayId);
		frontendViewStateActiveEntry.setFrontendViewStateEntryId(
			frontendViewStateEntryId);
		frontendViewStateActiveEntry.setPlid(plid);
		frontendViewStateActiveEntry.setPortletId(portletId);

		return frontendViewStateActiveEntry;
	}

	public FrontendViewStateActiveEntry fetchFrontendViewStateActiveEntry(
		String datasetDisplayId, long plid, String portletId, long userId) {

		return frontendViewStateActiveEntryPersistence.fetchByU_D_P_P(
			userId, datasetDisplayId, plid, portletId);
	}

}
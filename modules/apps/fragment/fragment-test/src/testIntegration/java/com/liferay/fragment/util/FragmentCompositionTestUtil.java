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

package com.liferay.fragment.util;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentCompositionServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Lourdes Fernández Besada
 */
public class FragmentCompositionTestUtil {

	public static FragmentComposition addFragmentComposition(
			long fragmentCollectionId, String name)
		throws PortalException {

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.getFragmentCollection(
				fragmentCollectionId);

		return FragmentCompositionServiceUtil.addFragmentComposition(
			fragmentCollection.getGroupId(),
			fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), name, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				fragmentCollection.getGroupId()));
	}

}
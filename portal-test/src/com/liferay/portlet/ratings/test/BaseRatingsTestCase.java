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

package com.liferay.portlet.ratings.test;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portlet.ratings.util.test.RatingsTestUtil;
import com.liferay.ratings.kernel.exception.NoSuchStatsException;
import com.liferay.ratings.kernel.model.RatingsStats;
import com.liferay.ratings.kernel.service.RatingsStatsLocalServiceUtil;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Cristina González
 */
public abstract class BaseRatingsTestCase {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();
	}

	@Test(expected = NoSuchStatsException.class)
	public void testDeleteRatings() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		BaseModel<?> baseModel = addBaseModel(parentBaseModel, serviceContext);

		RatingsStats ratingsStats = RatingsTestUtil.addStats(
			_getBaseModelClassName(), getRatingsClassPK(baseModel));

		deleteBaseModel(baseModel, serviceContext);

		RatingsStatsLocalServiceUtil.getRatingsStats(ratingsStats.getStatsId());
	}

	protected abstract BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception;

	protected abstract BaseModel<?> deleteBaseModel(
			BaseModel<?> baseModel, ServiceContext serviceContext)
		throws Exception;

	protected abstract Class<?> getBaseModelClass();

	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return group;
	}

	protected long getRatingsClassPK(ClassedModel classedModel) {
		return (Long)classedModel.getPrimaryKeyObj();
	}

	@DeleteAfterTestRun
	protected Group group;

	private String _getBaseModelClassName() {
		Class<?> clazz = getBaseModelClass();

		return clazz.getName();
	}

}
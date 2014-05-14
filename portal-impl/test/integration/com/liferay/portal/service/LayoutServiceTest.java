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

package com.liferay.portal.service;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class LayoutServiceTest {

	@Test
	public void testUpdateLayoutFriendlyURLMap() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(group);

		long userId = layout.getUserId();

		layout.setUserId(-1);

		LayoutLocalServiceUtil.updateLayout(layout);

		Map<Locale, String> friendlyURLMap = layout.getFriendlyURLMap();

		friendlyURLMap.put(
			LocaleUtil.GERMANY,
			StringPool.SLASH + ServiceTestUtil.randomString());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(userId);

		try {
			LayoutLocalServiceUtil.updateLayout(
				group.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), layout.getParentLayoutId(),
				layout.getNameMap(), layout.getTitleMap(),
				layout.getDescriptionMap(), layout.getKeywordsMap(),
				layout.getRobotsMap(), layout.getType(), layout.isHidden(),
				friendlyURLMap, layout.getIconImage(), null, serviceContext);
		}
		catch (NoSuchUserException nsue) {
			Assert.fail(ExceptionUtils.getStackTrace(nsue));
		}
	}

}
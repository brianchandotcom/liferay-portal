/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.TestPropsValues;

import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@PrepareForTest({PortletLocalServiceUtil.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class LayoutPrototypePropagationTest
	extends BasePrototypePropagationTestCase {

	protected void doSetUp() throws Exception {
		_prototypeLayout = _layoutPrototypeLayout;

		_journalArticle = _globalJournalArticle;

		_journalContentPortletId =
			addJournalContentPortletToLayout(
				TestPropsValues.getUserId(), _layoutPrototypeLayout,
				_journalArticle, "column-1");

		_layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), ServiceTestUtil.randomString(), true,
			_layoutPrototype, true);

		_layout = propagateChanges(_layout);
	}

	protected void setLinkEnabled(boolean linkEnabled) throws Exception {
		_layout.setLayoutPrototypeLinkEnabled(linkEnabled);

		LayoutLocalServiceUtil.updateLayout(_layout);
	}

}
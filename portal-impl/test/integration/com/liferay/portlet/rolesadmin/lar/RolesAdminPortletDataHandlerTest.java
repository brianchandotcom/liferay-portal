/*
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

package com.liferay.portlet.rolesadmin.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.lar.PortletDataContextImpl;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;

import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Michael C. Han
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@PrepareForTest({PortletLocalServiceUtil.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class RolesAdminPortletDataHandlerTest {

	public void setUp() throws Exception {
		/*_group = GroupTestUtil.addGroup();

		_portletDataContextExport = new PortletDataContextImpl(
			_group.getCompanyId(), _group.getGroupId(),
			new HashMap<String, String[]>(), new HashSet<String>(),
			new Date(System.currentTimeMillis() - Time.HOUR), new Date(),
			testReaderWriter);

		Element rootElement = SAXReaderUtil.createElement("root");

		_portletDataContextExport.setExportDataRootElement(rootElement);

		_portletDataContextImport = new PortletDataContextImpl(
			_stagingGroup.getCompanyId(), _stagingGroup.getGroupId(),
			new HashMap<String, String[]>(), new HashSet<String>(),
			new CurrentUserIdStrategy(TestPropsValues.getUser()),
			testReaderWriter);

		_portletDataContextImport.setImportDataRootElement(rootElement);
		_portletDataContextImport.setSourceGroupId(_stagingGroup.getGroupId());*/
	} private Group _group;

	private PortletDataContextImpl _portletDataContextExport;

}
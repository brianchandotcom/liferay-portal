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

package com.liferay.portal.workflow.kaleo.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalServiceUtil;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@RunWith(Arquillian.class)
public class KaleoDefinitionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws PortalException {
		setUpServiceContext();
	}

	@Test
	public void testAddKaleoDefinition() throws Exception {
		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		Assert.assertEquals(1, kaleoDefinition.getVersion());
	}

	@Test
	public void testDeactivateKaleoDefinition() throws Exception {
		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		deactivateKaleoDefinition(kaleoDefinition);

		Assert.assertFalse(kaleoDefinition.isActive());
	}

	@Test(expected = WorkflowException.class)
	public void testDeleteKaleoDefinition1() throws Exception {
		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		deleteKaleoDefinition(kaleoDefinition);
	}

	@Test(expected = NoSuchDefinitionException.class)
	public void testDeleteKaleoDefinition2() throws Exception {
		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		deactivateKaleoDefinition(kaleoDefinition);

		deleteKaleoDefinition(kaleoDefinition);

		KaleoDefinitionLocalServiceUtil.getKaleoDefinition(
			kaleoDefinition.getKaleoDefinitionId());
	}

	@Test
	public void testUpdateKaleoDefinitionShouldIncrementVersion1()
		throws Exception {

		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		kaleoDefinition = updateKaleoDefinition(kaleoDefinition);

		Assert.assertEquals(2, kaleoDefinition.getVersion());
	}

	protected KaleoDefinition addKaleoDefinition()
		throws IOException, PortalException {

		KaleoDefinition kaleoDefinition =
			KaleoDefinitionLocalServiceUtil.addKaleoDefinition(
				StringUtil.randomString(), StringUtil.randomString(),
				StringUtil.randomString(),
				read("legal-marketing-definition.xml"), 1, _serviceContext);

		KaleoDefinitionLocalServiceUtil.activateKaleoDefinition(
			kaleoDefinition.getKaleoDefinitionId(), _serviceContext);

		return kaleoDefinition;
	}

	protected void deactivateKaleoDefinition(KaleoDefinition kaleoDefinition)
		throws PortalException {

		KaleoDefinitionLocalServiceUtil.deactivateKaleoDefinition(
			kaleoDefinition.getName(), kaleoDefinition.getVersion(),
			_serviceContext);
	}

	protected void deleteKaleoDefinition(KaleoDefinition kaleoDefinition)
		throws PortalException {

		KaleoDefinitionLocalServiceUtil.deleteKaleoDefinition(
			kaleoDefinition.getName(), _serviceContext);
	}

	protected String read(String name) throws IOException {
		ClassLoader classLoader =
			KaleoDefinitionLocalServiceTest.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/portal/workflow/kaleo/dependencies/" + name)) {

			return StringUtil.read(inputStream);
		}
	}

	protected void setUpServiceContext() throws PortalException {
		_serviceContext = new ServiceContext();

		_serviceContext.setCompanyId(TestPropsValues.getCompanyId());
		_serviceContext.setUserId(TestPropsValues.getUserId());
	}

	protected KaleoDefinition updateKaleoDefinition(
			KaleoDefinition kaleoDefinition)
		throws IOException, PortalException {

		kaleoDefinition =
			KaleoDefinitionLocalServiceUtil.updatedKaleoDefinition(
				kaleoDefinition.getKaleoDefinitionId(),
				StringUtil.randomString(), StringUtil.randomString(),
				kaleoDefinition.getContent(), _serviceContext);

		KaleoDefinitionLocalServiceUtil.activateKaleoDefinition(
			kaleoDefinition.getKaleoDefinitionId(), _serviceContext);

		return kaleoDefinition;
	}

	private ServiceContext _serviceContext;

}
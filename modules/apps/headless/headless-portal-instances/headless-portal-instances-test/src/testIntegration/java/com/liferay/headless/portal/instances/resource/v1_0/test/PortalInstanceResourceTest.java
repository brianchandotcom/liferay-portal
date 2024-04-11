/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.portal.instances.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.portal.instances.client.dto.v1_0.Admin;
import com.liferay.headless.portal.instances.client.dto.v1_0.PortalInstance;
import com.liferay.headless.portal.instances.client.pagination.Page;
import com.liferay.headless.portal.instances.client.problem.Problem;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class PortalInstanceResourceTest
	extends BasePortalInstanceResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_portalInstance = _toPortalInstance(_company);

		_portalInstancesLocalService.synchronizePortalInstances();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_deletePortalInstance(_portalInstance);
	}

	@Override
	@Test
	public void testDeletePortalInstance() throws Exception {
		_testDeletePortalInstanceNotExisting();
		_testDeletePortalInstanceExisting();
	}

	@Override
	@Test
	public void testGetPortalInstance() throws Exception {
		assertEquals(
			portalInstanceResource.getPortalInstance(
				_portalInstance.getPortalInstanceId()),
			_portalInstance);
	}

	@Override
	@Test
	public void testGetPortalInstancesPage() throws Exception {
		Page<PortalInstance> page =
			portalInstanceResource.getPortalInstancesPage(null);

		assertContains(_portalInstance, (List<PortalInstance>)page.getItems());
	}

	@Override
	@Test
	public void testPatchPortalInstance() throws Exception {
		PortalInstance randomPortalInstance = randomPortalInstance();

		PortalInstance patchPortalInstance =
			portalInstanceResource.patchPortalInstance(
				_portalInstance.getPortalInstanceId(), randomPortalInstance);

		assertEquals(_portalInstance, patchPortalInstance);
	}

	@Test
	public void testPostPortalInstance() throws Exception {
		_testPostPortalInstanceWithoutAdmin();
		_testPostPortalInstanceWithAdmin();
	}

	@Override
	@Test
	public void testPutPortalInstanceActivate() throws Exception {
		portalInstanceResource.putPortalInstanceActivate(
			_portalInstance.getPortalInstanceId());

		Company company = _companyLocalService.fetchCompany(
			_portalInstance.getCompanyId());

		Assert.assertTrue(company.isActive());
	}

	@Override
	@Test
	public void testPutPortalInstanceDeactivate() throws Exception {
		portalInstanceResource.putPortalInstanceDeactivate(
			_portalInstance.getPortalInstanceId());

		Company company = _companyLocalService.fetchCompany(
			_portalInstance.getCompanyId());

		Assert.assertFalse(company.isActive());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"active", "admin", "companyId", "domain", "portalInstanceId",
			"siteInitializerKey", "virtualHost"
		};
	}

	@Override
	protected PortalInstance randomPortalInstance() throws Exception {
		String randomName = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		String randomDomain =
			randomName + "." +
				StringUtil.toLowerCase(RandomTestUtil.randomString(3));

		return new PortalInstance() {
			{
				active = RandomTestUtil.randomBoolean();
				companyId = RandomTestUtil.randomLong();
				domain = randomDomain;
				portalInstanceId = randomName;
				virtualHost = randomDomain;
			}
		};
	}

	@Override
	protected PortalInstance testPostPortalInstance_addPortalInstance(
			PortalInstance portalInstance)
		throws Exception {

		return portalInstanceResource.postPortalInstance(portalInstance);
	}

	private static void _deletePortalInstance(PortalInstance portalInstance)
		throws Exception {

		String name = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		try {
			_companyLocalService.deleteCompany(portalInstance.getCompanyId());

			_portalInstancesLocalService.synchronizePortalInstances();
		}
		finally {
			PrincipalThreadLocal.setName(name);
		}
	}

	private static PortalInstance _toPortalInstance(Company company) {
		return new PortalInstance() {
			{
				setActive(company::isActive);
				setCompanyId(company::getCompanyId);
				setDomain(company::getMx);
				setPortalInstanceId(company::getWebId);
				setVirtualHost(company::getVirtualHostname);
			}
		};
	}

	private void _testDeletePortalInstanceExisting() throws Exception {
		PortalInstance randomPortalInstance = randomPortalInstance();

		PortalInstance portalInstance =
			portalInstanceResource.postPortalInstance(randomPortalInstance);

		assertValid(portalInstance);

		Assert.assertNotNull(
			_companyLocalService.fetchCompany(portalInstance.getCompanyId()));

		portalInstanceResource.deletePortalInstance(
			portalInstance.getPortalInstanceId());

		Assert.assertNull(
			_companyLocalService.fetchCompany(portalInstance.getCompanyId()));
	}

	private void _testDeletePortalInstanceNotExisting() throws Exception {

		// Nonexistent key

		String portalInstanceId = RandomTestUtil.randomString();

		try {
			portalInstanceResource.deletePortalInstance(portalInstanceId);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertEquals(
				StringBundler.concat(
					"No portal instance exists with the key {webId=",
					portalInstanceId, "}"),
				problem.getTitle());
		}
	}

	private void _testPostPortalInstanceWithAdmin() throws Exception {
		PortalInstance randomPortalInstance = randomPortalInstance();

		randomPortalInstance.setAdmin(
			Admin.toDTO(
				StringBundler.concat(
					"{\"emailAddress\": \"test123@liferay.com\",",
					"\"familyName\": \"Test123\",",
					"\"givenName\": \"Test123\"}")));

		PortalInstance postPortalInstance =
			testPostPortalInstance_addPortalInstance(randomPortalInstance);

		assertEquals(randomPortalInstance, postPortalInstance);
		assertValid(postPortalInstance);

		_deletePortalInstance(postPortalInstance);
	}

	private void _testPostPortalInstanceWithoutAdmin() throws Exception {
		PortalInstance randomPortalInstance = randomPortalInstance();

		PortalInstance postPortalInstance =
			testPostPortalInstance_addPortalInstance(randomPortalInstance);

		assertEquals(randomPortalInstance, postPortalInstance);
		assertValid(postPortalInstance);

		_deletePortalInstance(postPortalInstance);
	}

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static PortalInstance _portalInstance;

	@Inject
	private static PortalInstancesLocalService _portalInstancesLocalService;

}
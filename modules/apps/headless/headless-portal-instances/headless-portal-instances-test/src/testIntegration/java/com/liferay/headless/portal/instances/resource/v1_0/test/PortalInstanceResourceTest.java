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
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
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
		_testPatchPortalInstanceChangeActive();
		_testPatchPortalInstanceChangeCompanyId();
		_testPatchPortalInstanceChangeDomain();
		_testPatchPortalInstanceChangePortletInstanceId();
		_testPatchPortalInstanceChangeVirtualHostName();
	}

	@Test
	public void testPostPortalInstance() throws Exception {
		_testPostPortalInstanceWithoutAdmin();
		_testPostPortalInstanceWithAdmin();
	}

	@Override
	@Test
	public void testPutPortalInstanceActivate() throws Exception {
		_companyLocalService.updateCompany(
			_company.getCompanyId(), _company.getVirtualHostname(),
			_company.getMx(), _company.getMaxUsers(), false);

		Company company = _companyLocalService.fetchCompany(
			_portalInstance.getCompanyId());

		Assert.assertFalse(company.isActive());

		portalInstanceResource.putPortalInstanceActivate(
			_portalInstance.getPortalInstanceId());

		company = _companyLocalService.fetchCompany(
			_portalInstance.getCompanyId());

		Assert.assertTrue(company.isActive());
	}

	@Override
	@Test
	public void testPutPortalInstanceDeactivate() throws Exception {
		_companyLocalService.updateCompany(
			_company.getCompanyId(), _company.getVirtualHostname(),
			_company.getMx(), _company.getMaxUsers(), true);

		Company company = _companyLocalService.fetchCompany(
			_portalInstance.getCompanyId());

		Assert.assertTrue(company.isActive());

		portalInstanceResource.putPortalInstanceDeactivate(
			_portalInstance.getPortalInstanceId());

		company = _companyLocalService.fetchCompany(
			_portalInstance.getCompanyId());

		Assert.assertFalse(company.isActive());
	}

	@Override
	protected void assertValid(PortalInstance portalInstance) throws Exception {
		boolean valid = true;

		if (Validator.isNull(portalInstance.getActive()) ||
			Validator.isNull(portalInstance.getCompanyId()) ||
			Validator.isNull(portalInstance.getDomain()) ||
			Validator.isNull(portalInstance.getPortalInstanceId()) ||
			Validator.isNull(portalInstance.getVirtualHost())) {

			valid = false;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"active", "companyId", "domain", "portalInstanceId", "virtualHost"
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
				active = true;
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

	private PortalInstance _copyPortalInstance(
			boolean changeActive, boolean changeCompanyId, boolean changeDomain,
			boolean changePortletInstanceId, boolean changeVirtualHostName)
		throws Exception {

		String randomName = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		String randomDomain =
			randomName + "." +
				StringUtil.toLowerCase(RandomTestUtil.randomString(3));

		PortalInstance copyPortalInstance = _portalInstance.clone();

		if (changeActive) {
			copyPortalInstance.setActive(!copyPortalInstance.getActive());
		}

		if (changeCompanyId) {
			copyPortalInstance.setCompanyId(RandomTestUtil.randomLong());
		}

		if (changeDomain) {
			copyPortalInstance.setDomain(randomDomain);
		}

		if (changePortletInstanceId) {
			copyPortalInstance.setPortalInstanceId(randomName);
		}

		if (changeVirtualHostName) {
			copyPortalInstance.setVirtualHost(randomDomain);
		}

		return copyPortalInstance;
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

	private void _testPatchPortalInstace(
			boolean changeActive, boolean changeCompanyId,
			boolean changePortletInstanceId, PortalInstance portalInstance)
		throws Exception {

		PortalInstance patchPortalInstance =
			portalInstanceResource.patchPortalInstance(
				_portalInstance.getPortalInstanceId(), portalInstance);

		if (changeActive) {
			Assert.assertNotEquals(
				portalInstance.getActive(), patchPortalInstance.getActive());
		}
		else {
			Assert.assertEquals(
				portalInstance.getActive(), patchPortalInstance.getActive());
		}

		if (changeCompanyId) {
			Assert.assertNotEquals(
				portalInstance.getCompanyId(),
				patchPortalInstance.getCompanyId());
		}
		else {
			Assert.assertEquals(
				portalInstance.getCompanyId(),
				patchPortalInstance.getCompanyId());
		}

		Assert.assertEquals(
			portalInstance.getDomain(), patchPortalInstance.getDomain());

		if (changePortletInstanceId) {
			Assert.assertNotEquals(
				portalInstance.getPortalInstanceId(),
				patchPortalInstance.getPortalInstanceId());
		}
		else {
			Assert.assertEquals(
				portalInstance.getPortalInstanceId(),
				patchPortalInstance.getPortalInstanceId());
		}

		Assert.assertEquals(
			portalInstance.getVirtualHost(),
			patchPortalInstance.getVirtualHost());
	}

	private void _testPatchPortalInstanceChangeActive() throws Exception {
		PortalInstance portalInstance = _copyPortalInstance(
			true, false, false, false, false);

		_testPatchPortalInstace(true, false, false, portalInstance);
	}

	private void _testPatchPortalInstanceChangeCompanyId() throws Exception {
		PortalInstance portalInstance = _copyPortalInstance(
			false, true, false, false, false);

		_testPatchPortalInstace(false, true, false, portalInstance);
	}

	private void _testPatchPortalInstanceChangeDomain() throws Exception {
		PortalInstance portalInstance = _copyPortalInstance(
			false, false, true, false, false);

		_testPatchPortalInstace(false, false, false, portalInstance);
	}

	private void _testPatchPortalInstanceChangePortletInstanceId()
		throws Exception {

		PortalInstance portalInstance = _copyPortalInstance(
			false, false, false, true, false);

		_testPatchPortalInstace(false, false, true, portalInstance);
	}

	private void _testPatchPortalInstanceChangeVirtualHostName()
		throws Exception {

		PortalInstance portalInstance = _copyPortalInstance(
			false, false, false, false, true);

		_testPatchPortalInstace(false, false, false, portalInstance);
	}

	private void _testPostPortalInstanceWithAdmin() throws Exception {
		PortalInstance randomPortalInstance = randomPortalInstance();

		String firstName = RandomTestUtil.randomString();
		String lastName = RandomTestUtil.randomString();
		String email = StringUtil.toLowerCase(firstName + "@liferay.com");

		randomPortalInstance.setAdmin(
			Admin.toDTO(
				StringBundler.concat(
					"{\"emailAddress\": \"", email, "\",\"familyName\": \"",
					lastName, "\",\"givenName\": \"", firstName, "\"}")));

		PortalInstance postPortalInstance =
			testPostPortalInstance_addPortalInstance(randomPortalInstance);

		try {
			Assert.assertNotNull(
				_userLocalService.getUserByEmailAddress(
					postPortalInstance.getCompanyId(), email));

			assertEquals(randomPortalInstance, postPortalInstance);
			assertValid(postPortalInstance);
		}
		finally {
			if (postPortalInstance != null) {
				_deletePortalInstance(postPortalInstance);
			}
		}
	}

	private void _testPostPortalInstanceWithoutAdmin() throws Exception {
		PortalInstance randomPortalInstance = randomPortalInstance();

		PortalInstance postPortalInstance =
			testPostPortalInstance_addPortalInstance(randomPortalInstance);

		try {
			assertEquals(randomPortalInstance, postPortalInstance);
			assertValid(postPortalInstance);
		}
		finally {
			if (postPortalInstance != null) {
				_deletePortalInstance(postPortalInstance);
			}
		}
	}

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static PortalInstance _portalInstance;

	@Inject
	private static PortalInstancesLocalService _portalInstancesLocalService;

	@Inject
	private UserLocalService _userLocalService;

}
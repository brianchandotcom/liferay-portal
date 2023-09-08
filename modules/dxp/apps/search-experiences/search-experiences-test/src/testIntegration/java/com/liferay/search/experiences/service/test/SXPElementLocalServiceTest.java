/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.search.experiences.exception.DuplicateSXPElementExternalReferenceCodeException;
import com.liferay.search.experiences.exception.NoSuchSXPElementException;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class SXPElementLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testAddSXPElement() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();

		SXPElement sxpElement = _addSXPElement(externalReferenceCode);

		Assert.assertEquals(
			externalReferenceCode, sxpElement.getExternalReferenceCode());
		Assert.assertEquals("1.0", sxpElement.getVersion());

		// Duplicate external reference code in a different company

		User user = UserTestUtil.addCompanyAdminUser(
			CompanyTestUtil.addCompany());

		SXPElement differentCompanySXPElement = _addSXPElement(
			sxpElement.getExternalReferenceCode(), user.getUserId());

		Assert.assertEquals(
			sxpElement.getExternalReferenceCode(),
			differentCompanySXPElement.getExternalReferenceCode());

		// Duplicate external reference code in the same company

		try {
			_addSXPElement(sxpElement.getExternalReferenceCode());

			Assert.fail();
		}
		catch (DuplicateSXPElementExternalReferenceCodeException
					duplicateSXPElementExternalReferenceCodeException) {

			Assert.assertNotNull(
				duplicateSXPElementExternalReferenceCodeException);
		}

		// Null external reference code

		sxpElement = _addSXPElement(null);

		Assert.assertNotNull(sxpElement.getExternalReferenceCode());
		Assert.assertEquals("1.0", sxpElement.getVersion());
	}

	@Test
	public void testGetSXPElementByExternalReferenceCode() throws Exception {
		SXPElement sxpElement = _addSXPElement(RandomTestUtil.randomString());

		Assert.assertEquals(
			sxpElement,
			_sxpElementLocalService.getSXPElementByExternalReferenceCode(
				sxpElement.getExternalReferenceCode(),
				TestPropsValues.getCompanyId()));

		try {
			_sxpElementLocalService.getSXPElementByExternalReferenceCode(
				RandomTestUtil.randomString(), TestPropsValues.getCompanyId());

			Assert.fail();
		}
		catch (NoSuchSXPElementException noSuchSXPElementException) {
			Assert.assertNotNull(noSuchSXPElementException);
		}
	}

	@Test
	public void testUpdateSXPElement() throws Exception {
		SXPElement sxpElement = _addSXPElement(RandomTestUtil.randomString());

		String externalReferenceCode = RandomTestUtil.randomString();

		sxpElement.setExternalReferenceCode(externalReferenceCode);

		sxpElement = _sxpElementLocalService.updateSXPElement(sxpElement);

		Assert.assertEquals(
			externalReferenceCode, sxpElement.getExternalReferenceCode());

		sxpElement = _sxpElementLocalService.updateSXPElement(
			sxpElement.getExternalReferenceCode(), sxpElement.getUserId(),
			sxpElement.getSXPElementId(), sxpElement.getDescriptionMap(),
			sxpElement.getElementDefinitionJSON(), sxpElement.isHidden(),
			sxpElement.getSchemaVersion(), sxpElement.getTitleMap(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			externalReferenceCode, sxpElement.getExternalReferenceCode());
		Assert.assertEquals("1.1", sxpElement.getVersion());
	}

	@Test(expected = DuplicateSXPElementExternalReferenceCodeException.class)
	public void testUpdateSXPElementWithSameExternalReferenceCode()
		throws Exception {

		SXPElement sxpElement1 = _addSXPElement(RandomTestUtil.randomString());
		SXPElement sxpElement2 = _addSXPElement(RandomTestUtil.randomString());

		sxpElement2.setExternalReferenceCode(
			sxpElement1.getExternalReferenceCode());

		_sxpElementLocalService.updateSXPElement(sxpElement2);
	}

	private SXPElement _addSXPElement(String externalReferenceCode)
		throws Exception {

		return _addSXPElement(
			externalReferenceCode, TestPropsValues.getUserId());
	}

	private SXPElement _addSXPElement(String externalReferenceCode, long userId)
		throws Exception {

		SXPElement sxpElement = _sxpElementLocalService.addSXPElement(
			externalReferenceCode, userId,
			Collections.singletonMap(LocaleUtil.US, ""), "{}", false,
			RandomTestUtil.randomString(),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			0, ServiceContextTestUtil.getServiceContext());

		_sxpElements.add(sxpElement);

		return sxpElement;
	}

	@Inject
	private SXPElementLocalService _sxpElementLocalService;

	@DeleteAfterTestRun
	private List<SXPElement> _sxpElements = new ArrayList<>();

}
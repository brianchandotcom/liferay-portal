/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.search.experiences.exception.NoSuchSXPBlueprintException;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.PersistenceException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class SXPBlueprintLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_sxpBlueprint = _addSXPBlueprint(TestPropsValues.getUserId());
	}

	@Test
	public void testAddSXPBlueprint() throws Exception {
		Assert.assertNotNull(_sxpBlueprint.getExternalReferenceCode());
		Assert.assertEquals("1.0", _sxpBlueprint.getVersion());
	}

	@Test
	public void testGetSXPBlueprintByExternalReferenceCode() throws Exception {
		Assert.assertEquals(
			_sxpBlueprint,
			_sxpBlueprintLocalService.getSXPBlueprintByExternalReferenceCode(
				_group.getCompanyId(),
				_sxpBlueprint.getExternalReferenceCode()));

		try {
			_sxpBlueprintLocalService.getSXPBlueprintByExternalReferenceCode(
				_group.getCompanyId(), RandomTestUtil.randomString());

			Assert.fail();
		}
		catch (NoSuchSXPBlueprintException noSuchSXPBlueprintException) {
			Assert.assertNotNull(noSuchSXPBlueprintException);
		}
	}

	@Test
	public void testUpdateSXPBlueprint() throws Exception {
		SXPBlueprint sxpBlueprint = _addSXPBlueprint(
			TestPropsValues.getUserId());

		sxpBlueprint.setExternalReferenceCode(
			_sxpBlueprint.getExternalReferenceCode());

		try (LogCapture logCapture1 = LoggerTestUtil.configureLog4JLogger(
				"org.hibernate.engine.jdbc.batch.internal.BatchingBatch",
				LoggerTestUtil.ERROR);
			LogCapture logCapture2 = LoggerTestUtil.configureLog4JLogger(
				"org.hibernate.engine.jdbc.spi.SqlExceptionHelper",
				LoggerTestUtil.ERROR)) {

			try {
				_sxpBlueprintLocalService.updateSXPBlueprint(sxpBlueprint);

				Assert.fail();
			}
			catch (PersistenceException persistenceException) {
				Assert.assertNotNull(persistenceException);
			}
		}

		_company = CompanyTestUtil.addCompany();

		User user = UserTestUtil.addCompanyAdminUser(_company);

		sxpBlueprint = _addSXPBlueprint(user.getUserId());

		sxpBlueprint.setExternalReferenceCode(
			_sxpBlueprint.getExternalReferenceCode());

		sxpBlueprint = _sxpBlueprintLocalService.updateSXPBlueprint(
			sxpBlueprint);

		Assert.assertEquals(
			_sxpBlueprint.getExternalReferenceCode(),
			sxpBlueprint.getExternalReferenceCode());

		String externalReferenceCode = RandomTestUtil.randomString();

		_sxpBlueprint.setExternalReferenceCode(externalReferenceCode);

		_sxpBlueprint = _sxpBlueprintLocalService.updateSXPBlueprint(
			_sxpBlueprint);

		Assert.assertEquals(
			externalReferenceCode, _sxpBlueprint.getExternalReferenceCode());

		_sxpBlueprint = _sxpBlueprintLocalService.updateSXPBlueprint(
			_sxpBlueprint.getUserId(), _sxpBlueprint.getSXPBlueprintId(),
			_sxpBlueprint.getConfigurationJSON(),
			_sxpBlueprint.getDescriptionMap(),
			_sxpBlueprint.getElementInstancesJSON(),
			_sxpBlueprint.getSchemaVersion(), _sxpBlueprint.getTitleMap(),
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));

		Assert.assertEquals(
			externalReferenceCode, _sxpBlueprint.getExternalReferenceCode());
		Assert.assertEquals("1.1", _sxpBlueprint.getVersion());

		_sxpBlueprint = _sxpBlueprintLocalService.updateSXPBlueprint(
			_sxpBlueprint.getUserId(), _sxpBlueprint.getSXPBlueprintId(),
			_sxpBlueprint.getConfigurationJSON(),
			_sxpBlueprint.getDescriptionMap(),
			_sxpBlueprint.getElementInstancesJSON(),
			_sxpBlueprint.getSchemaVersion(), _sxpBlueprint.getTitleMap(),
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));

		Assert.assertEquals(
			externalReferenceCode, _sxpBlueprint.getExternalReferenceCode());
		Assert.assertEquals("1.2", _sxpBlueprint.getVersion());
	}

	private SXPBlueprint _addSXPBlueprint(long userId) throws Exception {
		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			userId, "{}", Collections.singletonMap(LocaleUtil.US, ""), null, "",
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), userId));

		_sxpBlueprints.add(sxpBlueprint);

		return sxpBlueprint;
	}

	@DeleteAfterTestRun
	private Company _company;

	@DeleteAfterTestRun
	private Group _group;

	private SXPBlueprint _sxpBlueprint;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@DeleteAfterTestRun
	private List<SXPBlueprint> _sxpBlueprints = new ArrayList<>();

}
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.internal.user.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.scim.client.configuration.SCIMClientOAuth2ApplicationConfiguration;
import com.liferay.scim.client.util.SCIMClientUtil;
import com.liferay.scim.user.manager.SCIMUser;
import com.liferay.scim.user.manager.SCIMUserManager;

import java.util.Date;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@FeatureFlags("LPS-96845")
@RunWith(Arquillian.class)
public class SCIMUserManagerImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		ConfigurationTestUtil.saveConfiguration(
			SCIMClientOAuth2ApplicationConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"applicationName", _SCIM_CLIENT_APPLICATION_NAME
			).put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"matcherField", "emailAddress"
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(
			SCIMClientOAuth2ApplicationConfiguration.class.getName());
	}

	@Test
	public void testAddOrUpdateSCIMUser() throws PortalException {
		SCIMUser scimUser = _scimUserManager.addOrUpdateSCIMUser(
			_createSCIMUser());

		Assert.assertNotNull(scimUser);
		Assert.assertTrue(scimUser.isActive());
		Assert.assertNotEquals("0", scimUser.getId());

		User user = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), scimUser.getEmailAddress());

		Assert.assertNotNull(user);
		Assert.assertTrue(user.isActive());

		Assert.assertEquals(
			SCIMClientUtil.generateSCIMClientId(_SCIM_CLIENT_APPLICATION_NAME),
			_getSCIMClientId(user));
	}

	@Test
	public void testAddOrUpdateSCIMUserAutoPassword() throws PortalException {
		SCIMUser scimUser = _createSCIMUser();

		scimUser.setAutoPassword(true);
		scimUser.setPassword(null);

		_scimUserManager.addOrUpdateSCIMUser(scimUser);
	}

	@Test
	public void testAddOrUpdateSCIMUserWithExistingInactiveUser()
		throws Exception {

		User user = UserTestUtil.addUser(
			RandomTestUtil.randomString() + RandomTestUtil.nextLong());

		user = _userLocalService.updateStatus(
			user.getUserId(), WorkflowConstants.STATUS_INACTIVE,
			new ServiceContext());

		SCIMUser scimUser = _scimUserManager.addOrUpdateSCIMUser(
			_createSCIMUser(user.getEmailAddress()));

		user = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), scimUser.getEmailAddress());

		Assert.assertTrue(scimUser.isActive());

		Assert.assertTrue(user.isActive());
	}

	@Test
	public void testAddOrUpdateSCIMUserWithExistingUser() throws Exception {
		User user = UserTestUtil.addUser(
			RandomTestUtil.randomString() + RandomTestUtil.nextLong());

		Assert.assertEquals(StringPool.BLANK, _getSCIMClientId(user));

		SCIMUser scimUser = _scimUserManager.addOrUpdateSCIMUser(
			_createSCIMUser(user.getEmailAddress()));

		Assert.assertNotNull(scimUser);
		Assert.assertEquals(String.valueOf(user.getUserId()), scimUser.getId());
		Assert.assertTrue(scimUser.isActive());

		Assert.assertTrue(user.isActive());

		Assert.assertEquals(
			SCIMClientUtil.generateSCIMClientId(_SCIM_CLIENT_APPLICATION_NAME),
			_getSCIMClientId(user));
	}

	@Test
	public void testAddOrUpdateSCIMUserWithProvisionedUserByOtherSCIMClient()
		throws Exception {

		expectedException.expect(PortalException.class);
		expectedException.expectMessage(
			"User was provisioned by other SCIM client");

		User user = UserTestUtil.addUser(
			RandomTestUtil.randomString() + RandomTestUtil.nextLong());

		_saveSCIMClientId(user, RandomTestUtil.randomString());

		_scimUserManager.addOrUpdateSCIMUser(
			_createSCIMUser(user.getEmailAddress()));
	}

	@Test
	public void testDeleteSCIMUser() throws Exception {
		SCIMUser scimUser = _scimUserManager.addOrUpdateSCIMUser(
			_createSCIMUser());

		User user = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), scimUser.getEmailAddress());

		Assert.assertTrue(user.isActive());

		_scimUserManager.deleteSCIMUser(
			TestPropsValues.getCompanyId(),
			GetterUtil.getLong(scimUser.getId()));

		user = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), scimUser.getEmailAddress());

		Assert.assertFalse(user.isActive());
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static SCIMUser _createSCIMUser() throws PortalException {
		return _createSCIMUser(
			RandomTestUtil.randomString() + RandomTestUtil.nextLong() +
				"@liferay.com");
	}

	private static SCIMUser _createSCIMUser(String emailAddress)
		throws PortalException {

		SCIMUser scimUser = new SCIMUser();

		scimUser.setActive(true);
		scimUser.setBirthday(new Date());
		scimUser.setCompanyId(TestPropsValues.getCompanyId());
		scimUser.setFirstName("firstName");
		scimUser.setEmailAddress(emailAddress);
		scimUser.setExternalReferenceCode(RandomTestUtil.randomString());
		scimUser.setJobTitle("Software Engineer");
		scimUser.setLastName("lastName");
		scimUser.setLocale(LocaleUtil.US);
		scimUser.setMale(true);
		scimUser.setMiddleName("middleName");
		scimUser.setPassword(RandomTestUtil.randomString());
		scimUser.setScreenName(
			RandomTestUtil.randomString(
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE));

		return scimUser;
	}

	private String _getSCIMClientId(User user) throws PortalException {
		ExpandoTable expandoTable = _expandoTableLocalService.getTable(
			user.getCompanyId(),
			_classNameLocalService.getClassNameId(User.class.getName()),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		ExpandoColumn expandoColumn = _expandoColumnLocalService.getColumn(
			expandoTable.getTableId(), "scimClientId");

		ExpandoValue expandoValue = _expandoValueLocalService.getValue(
			expandoTable.getTableId(), expandoColumn.getColumnId(),
			user.getUserId());

		if (expandoValue == null) {
			return StringPool.BLANK;
		}

		return expandoValue.getData();
	}

	private void _saveSCIMClientId(User user, String applicationName)
		throws Exception {

		ExpandoTable expandoTable = _expandoTableLocalService.getTable(
			user.getCompanyId(),
			_classNameLocalService.getClassNameId(User.class.getName()),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		ExpandoColumn expandoColumn = _expandoColumnLocalService.getColumn(
			expandoTable.getTableId(), "scimClientId");

		_expandoValueLocalService.addValue(
			user.getCompanyId(), User.class.getName(),
			ExpandoTableConstants.DEFAULT_TABLE_NAME, expandoColumn.getName(),
			user.getUserId(),
			SCIMClientUtil.generateSCIMClientId(applicationName));
	}

	private static final String _SCIM_CLIENT_APPLICATION_NAME =
		"scim-client-app-test";

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Inject
	private ExpandoTableLocalService _expandoTableLocalService;

	@Inject
	private ExpandoValueLocalService _expandoValueLocalService;

	@Inject
	private SCIMUserManager _scimUserManager;

	@Inject
	private UserLocalService _userLocalService;

}
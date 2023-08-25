/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.related.models.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.exception.RequiredObjectRelationshipException;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.object.related.models.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.related.models.test.util.ObjectEntryTestUtil;
import com.liferay.object.related.models.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortletCategoryKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Leo
 */
@RunWith(Arquillian.class)
public class ObjectRelatedModelsProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		_role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.USER);
		_user = UserTestUtil.addUser();
	}

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = ObjectDefinitionTestUtil.addObjectDefinition();
		_objectDefinition2 = ObjectDefinitionTestUtil.addObjectDefinition();

		_setUser(TestPropsValues.getUser());
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Ignore
	@Test
	public void testObjectEntry1to1ObjectRelatedModelsProviderImpl()
		throws Exception {

		_addObjectRelationship(
			_objectDefinition1, _objectDefinition2,
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			ObjectRelationshipConstants.TYPE_ONE_TO_ONE);

		ObjectEntry objectEntry1 = _addObjectEntry(
			_objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());
		ObjectEntry objectEntry2 = _addObjectEntry(
			_objectDefinition2.getObjectDefinitionId(), Collections.emptyMap());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			0, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId());

		ObjectEntry objectEntry3 = _addObjectEntry(
			_objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				_relationshipObjectField.getName(),
				objectEntry1.getObjectEntryId()
			).build());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			1, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId());

		AssertUtils.assertFailure(
			ObjectEntryValuesException.OneToOneConstraintViolation.class,
			String.format(
				"One to one constraint violation for %s.%s with value %s",
				_relationshipObjectField.getDBTableName(),
				_relationshipObjectField.getDBColumnName(),
				objectEntry1.getObjectEntryId()),
			() -> _addObjectEntry(
				_objectDefinition2.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					_relationshipObjectField.getName(),
					objectEntry1.getObjectEntryId()
				).build()));
		AssertUtils.assertFailure(
			ObjectEntryValuesException.OneToOneConstraintViolation.class,
			String.format(
				"One to one constraint violation for %s.%s with value %s",
				_relationshipObjectField.getDBTableName(),
				_relationshipObjectField.getDBColumnName(),
				objectEntry1.getObjectEntryId()),
			() -> _updateObjectEntry(
				objectEntry2.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					_relationshipObjectField.getName(),
					objectEntry1.getObjectEntryId()
				).build()));

		_updateObjectEntry(
			objectEntry3.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				_relationshipObjectField.getName(),
				objectEntry1.getObjectEntryId()
			).build());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			1, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId());

		_updateObjectEntry(
			objectEntry3.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				_relationshipObjectField.getName(), 0
			).build());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			0, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId());

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship);
	}

	@Test
	public void testObjectEntry1toMObjectRelatedModelsProviderImpl()
		throws Exception {

		// Get related models with database

		_addObjectRelationship(
			_objectDefinition1, _objectDefinition2,
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectEntry objectEntry1 = _addObjectEntry(
			_objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());
		ObjectEntry objectEntry2 = _addObjectEntry(
			_objectDefinition2.getObjectDefinitionId(), Collections.emptyMap());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			0, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId());

		ObjectEntry objectEntry3 = _addObjectEntry(
			_objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"able", "First Entry"
			).put(
				_relationshipObjectField.getName(),
				objectEntry1.getObjectEntryId()
			).build());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			1, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId());

		_addObjectEntry(
			_objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"able", "Second Entry"
			).put(
				_relationshipObjectField.getName(),
				objectEntry1.getObjectEntryId()
			).build());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			2, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId());

		_updateObjectEntry(
			objectEntry2.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"able", "Third Entry"
			).put(
				_relationshipObjectField.getName(),
				objectEntry1.getObjectEntryId()
			).build());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			3, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId());

		// Get related models with search

		ObjectRelationshipTestUtil.assertSearchRelatedModels(
			0, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), StringUtil.randomString());
		ObjectRelationshipTestUtil.assertSearchRelatedModels(
			1, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(),
			String.valueOf(objectEntry2.getObjectEntryId()));
		ObjectRelationshipTestUtil.assertSearchRelatedModels(
			1, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), "First ");
		ObjectRelationshipTestUtil.assertSearchRelatedModels(
			2, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), "d Entry");
		ObjectRelationshipTestUtil.assertSearchRelatedModels(
			3, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), "Entry");

		_updateObjectEntry(
			objectEntry3.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				_relationshipObjectField.getName(), 0
			).build());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			2, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId());

		// Get related models with view permission

		_setUser(_user);

		_assertViewPermission(
			_objectDefinition2, objectEntry1, objectEntry2.getObjectEntryId());

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship);

		_setUser(TestPropsValues.getUser());

		// Object relationship deletion type cascade

		ObjectDefinition scopeSiteObjectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"C" + RandomTestUtil.randomString(), null,
				PortletCategoryKeys.SITE_ADMINISTRATION_CONTENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_SITE,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		scopeSiteObjectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				scopeSiteObjectDefinition.getObjectDefinitionId());

		_addObjectRelationship(
			_objectDefinition1, scopeSiteObjectDefinition,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectEntry objectEntry4 = _addObjectEntry(
			_objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			0, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry4.getObjectEntryId());

		Group group = GroupTestUtil.addGroup();

		ObjectEntry objectEntry5 = ObjectEntryTestUtil.addObjectEntry(
			group.getGroupId(),
			scopeSiteObjectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				_relationshipObjectField.getName(),
				objectEntry4.getObjectEntryId()
			).build());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			1, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry4.getObjectEntryId());

		_objectEntryLocalService.deleteObjectEntry(objectEntry4);

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry5.getObjectEntryId()));

		// Object relationship deletion type disassociate

		ObjectRelationshipTestUtil.updateObjectRelationship(
			_objectRelationship.getObjectRelationshipId(),
			ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE,
			_objectRelationship.getLabelMap());

		ObjectEntry objectEntry6 = _addObjectEntry(
			_objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());

		ObjectEntry objectEntry7 = ObjectEntryTestUtil.addObjectEntry(
			group.getGroupId(),
			scopeSiteObjectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				_relationshipObjectField.getName(),
				objectEntry6.getObjectEntryId()
			).build());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			1, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry6.getObjectEntryId());

		_objectEntryLocalService.deleteObjectEntry(objectEntry6);

		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry7.getObjectEntryId()));

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			0, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry6.getObjectEntryId());

		// Object relationship deletion type prevent

		ObjectRelationshipTestUtil.updateObjectRelationship(
			_objectRelationship.getObjectRelationshipId(),
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			_objectRelationship.getLabelMap());

		ObjectEntry objectEntry8 = _addObjectEntry(
			_objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());

		_updateObjectEntry(
			objectEntry7.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				_relationshipObjectField.getName(),
				objectEntry8.getObjectEntryId()
			).build());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			1, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry8.getObjectEntryId());

		AssertUtils.assertFailure(
			RequiredObjectRelationshipException.class,
			StringBundler.concat(
				"Object relationship ",
				_objectRelationship.getObjectRelationshipId(),
				" does not allow deletes"),
			() -> _objectEntryLocalService.deleteObjectEntry(objectEntry8));

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			1, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry8.getObjectEntryId());

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship);

		_objectDefinitionLocalService.deleteObjectDefinition(
			scopeSiteObjectDefinition);
	}

	@Test
	public void testObjectEntry1toMObjectUnrelatedModelsProviderImpl()
		throws Exception {

		_testObjectEntry1toMObjectUnrelatedModelsProviderImpl(
			CompanyThreadLocal.getCompanyId());

		Company company = CompanyTestUtil.addCompany();

		_testObjectEntry1toMObjectUnrelatedModelsProviderImpl(
			company.getCompanyId());

		_companyLocalService.deleteCompany(company);
	}

	@Test
	public void testObjectEntryMtoMObjectRelatedModelsProviderImpl()
		throws Exception {

		// Get related models with database

		_testObjectEntryMtoMObjectRelatedModelsProviderImpl(
			_objectDefinition1, _objectDefinition1);
		_testObjectEntryMtoMObjectRelatedModelsProviderImpl(
			_objectDefinition1, _objectDefinition2);
	}

	private AccountEntry _addAccountEntry(long userId) throws Exception {
		return _accountEntryLocalService.addAccountEntry(
			userId, 0L, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null, null,
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
	}

	private ObjectEntry _addObjectEntry(
			long objectDefinitionId, Map<String, Serializable> values)
		throws Exception {

		return ObjectEntryTestUtil.addObjectEntry(
			0, objectDefinitionId, values);
	}

	private void _addObjectRelationship(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2, String deletionType,
			String relationshipType)
		throws Exception {

		_objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				objectDefinition1.getObjectDefinitionId(),
				objectDefinition2.getObjectDefinitionId(), 0, deletionType,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(), relationshipType);

		if (!StringUtil.equals(
				relationshipType,
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			_relationshipObjectField = _objectFieldLocalService.getObjectField(
				_objectRelationship.getObjectFieldId2());
		}

		_objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				objectDefinition2.getClassName(),
				objectDefinition2.getCompanyId(), relationshipType);
	}

	private void _assertViewPermission(
			int expectedRelatedModelsCount, ObjectDefinition objectDefinition,
			ObjectEntry parentObjectEntry, long primKey, int scope)
		throws Exception {

		Assert.assertEquals(
			0,
			_objectRelatedModelsProvider.getRelatedModelsCount(
				0, _objectRelationship.getObjectRelationshipId(),
				parentObjectEntry.getObjectEntryId(), null));

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), objectDefinition.getClassName(),
			scope, String.valueOf(primKey), _role.getRoleId(),
			new String[] {ActionKeys.VIEW});

		Assert.assertEquals(
			expectedRelatedModelsCount,
			_objectRelatedModelsProvider.getRelatedModelsCount(
				0, _objectRelationship.getObjectRelationshipId(),
				parentObjectEntry.getObjectEntryId(), null));

		_resourcePermissionLocalService.removeResourcePermission(
			TestPropsValues.getCompanyId(), objectDefinition.getClassName(),
			scope, String.valueOf(primKey), _role.getRoleId(), ActionKeys.VIEW);
	}

	private void _assertViewPermission(
			ObjectDefinition objectDefinition, ObjectEntry parentObjectEntry,
			long primKey)
		throws Exception {

		_assertViewPermission(
			2, objectDefinition, parentObjectEntry,
			TestPropsValues.getCompanyId(), ResourceConstants.SCOPE_COMPANY);
		_assertViewPermission(
			1, objectDefinition, parentObjectEntry, primKey,
			ResourceConstants.SCOPE_INDIVIDUAL);
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testObjectEntry1toMObjectUnrelatedModelsProviderImpl(
			long companyId)
		throws Exception {

		User user = UserTestUtil.getAdminUser(companyId);

		Assert.assertNotNull(user);

		String originalName = PrincipalThreadLocal.getName();
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(companyId)) {

			_setUser(user);

			ObjectDefinition objectDefinition =
				ObjectDefinitionTestUtil.addObjectDefinition();

			ObjectDefinition systemObjectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
					companyId, AccountEntry.class.getName());

			_addObjectRelationship(
				objectDefinition, systemObjectDefinition,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

			AccountEntry accountEntry1 = _addAccountEntry(user.getUserId());
			AccountEntry accountEntry2 = _addAccountEntry(user.getUserId());

			ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
				user.getUserId(), 0, objectDefinition.getObjectDefinitionId(),
				Collections.emptyMap(),
				ServiceContextTestUtil.getServiceContext());

			_objectEntryLocalService.
				addOrUpdateExtensionDynamicObjectDefinitionTableValues(
					user.getUserId(), systemObjectDefinition,
					accountEntry1.getAccountEntryId(),
					HashMapBuilder.<String, Serializable>put(
						_relationshipObjectField.getName(),
						objectEntry.getObjectEntryId()
					).build(),
					ServiceContextTestUtil.getServiceContext());

			List<ObjectEntry> unrelatedObjectEntries =
				_objectRelatedModelsProvider.getUnrelatedModels(
					companyId, 0, systemObjectDefinition,
					objectEntry.getObjectEntryId(),
					_objectRelationship.getObjectRelationshipId());

			Assert.assertEquals(
				unrelatedObjectEntries.toString(), 1,
				unrelatedObjectEntries.size());

			_objectEntryLocalService.
				addOrUpdateExtensionDynamicObjectDefinitionTableValues(
					user.getUserId(), systemObjectDefinition,
					accountEntry2.getAccountEntryId(),
					HashMapBuilder.<String, Serializable>put(
						_relationshipObjectField.getName(),
						objectEntry.getObjectEntryId()
					).build(),
					ServiceContextTestUtil.getServiceContext());

			unrelatedObjectEntries =
				_objectRelatedModelsProvider.getUnrelatedModels(
					companyId, 0, systemObjectDefinition,
					objectEntry.getObjectEntryId(),
					_objectRelationship.getObjectRelationshipId());

			Assert.assertEquals(
				unrelatedObjectEntries.toString(), 0,
				unrelatedObjectEntries.size());

			_objectRelationshipLocalService.deleteObjectRelationship(
				_objectRelationship.getObjectRelationshipId());

			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition.getObjectDefinitionId());

			_accountEntryLocalService.deleteAccountEntries(
				new long[] {
					accountEntry1.getAccountEntryId(),
					accountEntry2.getAccountEntryId()
				});
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}
	}

	private void _testObjectEntryMtoMObjectRelatedModelsProviderImpl(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2)
		throws Exception {

		_addObjectRelationship(
			objectDefinition1, objectDefinition2,
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectEntry objectEntry1 = _addObjectEntry(
			objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());
		ObjectEntry objectEntry2 = _addObjectEntry(
			objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"able", "First Entry"
			).build());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			0, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId());

		ObjectRelationshipTestUtil.addObjectRelationshipMappingTableValues(
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), objectEntry2.getObjectEntryId());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			1, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId());

		ObjectEntry objectEntry3 = _addObjectEntry(
			objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"able", "Second Entry"
			).build());

		ObjectRelationshipTestUtil.addObjectRelationshipMappingTableValues(
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), objectEntry3.getObjectEntryId());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			2, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId());

		// Get related models with search

		ObjectRelationshipTestUtil.assertSearchRelatedModels(
			0, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), StringUtil.randomString());
		ObjectRelationshipTestUtil.assertSearchRelatedModels(
			1, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(),
			String.valueOf(objectEntry2.getObjectEntryId()));
		ObjectRelationshipTestUtil.assertSearchRelatedModels(
			1, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), "First ");
		ObjectRelationshipTestUtil.assertSearchRelatedModels(
			2, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), " Entry");

		// View permission

		_setUser(_user);

		_assertViewPermission(
			objectDefinition2, objectEntry1, objectEntry2.getObjectEntryId());

		_setUser(TestPropsValues.getUser());

		// Object relationship deletion type cascade

		ObjectRelationshipTestUtil.updateObjectRelationship(
			_objectRelationship.getObjectRelationshipId(),
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			_objectRelationship.getLabelMap());

		_objectEntryLocalService.deleteObjectEntry(objectEntry3);

		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry1.getObjectEntryId()));
		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry3.getObjectEntryId()));

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			1, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId());

		_objectEntryLocalService.deleteObjectEntry(objectEntry1);

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry1.getObjectEntryId()));
		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry2.getObjectEntryId()));

		// Object relationship deletion type disassociate

		ObjectRelationshipTestUtil.updateObjectRelationship(
			_objectRelationship.getObjectRelationshipId(),
			ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE,
			_objectRelationship.getLabelMap());

		ObjectEntry objectEntry4 = _addObjectEntry(
			objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());
		ObjectEntry objectEntry5 = _addObjectEntry(
			objectDefinition2.getObjectDefinitionId(), Collections.emptyMap());
		ObjectEntry objectEntry6 = _addObjectEntry(
			objectDefinition2.getObjectDefinitionId(), Collections.emptyMap());

		ObjectRelationshipTestUtil.addObjectRelationshipMappingTableValues(
			_objectRelationship.getObjectRelationshipId(),
			objectEntry4.getObjectEntryId(), objectEntry5.getObjectEntryId());
		ObjectRelationshipTestUtil.addObjectRelationshipMappingTableValues(
			_objectRelationship.getObjectRelationshipId(),
			objectEntry4.getObjectEntryId(), objectEntry6.getObjectEntryId());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			2, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry4.getObjectEntryId());

		_objectEntryLocalService.deleteObjectEntry(objectEntry4);

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			0, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry4.getObjectEntryId());

		// Object relationship deletion type prevent

		ObjectRelationshipTestUtil.updateObjectRelationship(
			_objectRelationship.getObjectRelationshipId(),
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			_objectRelationship.getLabelMap());

		ObjectEntry objectEntry7 = _addObjectEntry(
			objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());

		ObjectRelationshipTestUtil.addObjectRelationshipMappingTableValues(
			_objectRelationship.getObjectRelationshipId(),
			objectEntry7.getObjectEntryId(), objectEntry5.getObjectEntryId());
		ObjectRelationshipTestUtil.addObjectRelationshipMappingTableValues(
			_objectRelationship.getObjectRelationshipId(),
			objectEntry7.getObjectEntryId(), objectEntry6.getObjectEntryId());

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			2, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry7.getObjectEntryId());

		AssertUtils.assertFailure(
			RequiredObjectRelationshipException.class,
			StringBundler.concat(
				"Object relationship ",
				_objectRelationship.getObjectRelationshipId(),
				" does not allow deletes"),
			() -> _objectEntryLocalService.deleteObjectEntry(objectEntry7));

		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry7.getObjectEntryId()));

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			2, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry7.getObjectEntryId());

		_objectEntryLocalService.deleteObjectEntry(objectEntry6);

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			1, _objectRelatedModelsProvider,
			_objectRelationship.getObjectRelationshipId(),
			objectEntry7.getObjectEntryId());

		// Reverse object relationship

		ObjectRelationship reverseObjectRelationship =
			_objectRelationshipLocalService.fetchReverseObjectRelationship(
				_objectRelationship, true);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship);

		Assert.assertNull(
			_objectRelationshipLocalService.fetchObjectRelationship(
				reverseObjectRelationship.getObjectRelationshipId()));
	}

	private ObjectEntry _updateObjectEntry(
			long objectEntryId, Map<String, Serializable> values)
		throws Exception {

		return _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntryId, values,
			ServiceContextTestUtil.getServiceContext());
	}

	private static PermissionChecker _originalPermissionChecker;
	private static Role _role;
	private static User _user;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	private ObjectRelatedModelsProvider<ObjectEntry>
		_objectRelatedModelsProvider;

	@Inject
	private ObjectRelatedModelsProviderRegistry
		_objectRelatedModelsProviderRegistry;

	private ObjectRelationship _objectRelationship;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private ObjectField _relationshipObjectField;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private UserLocalService _userLocalService;

}
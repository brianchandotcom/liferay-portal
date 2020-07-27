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

package com.liferay.app.builder.workflow.service.persistence.test;

import com.liferay.app.builder.workflow.exception.NoSuchTaskLinkException;
import com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink;
import com.liferay.app.builder.workflow.service.AppBuilderWorkflowTaskLinkLocalServiceUtil;
import com.liferay.app.builder.workflow.service.persistence.AppBuilderWorkflowTaskLinkPersistence;
import com.liferay.app.builder.workflow.service.persistence.AppBuilderWorkflowTaskLinkUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class AppBuilderWorkflowTaskLinkPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.app.builder.workflow.service"));

	@Before
	public void setUp() {
		_persistence = AppBuilderWorkflowTaskLinkUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AppBuilderWorkflowTaskLink> iterator =
			_appBuilderWorkflowTaskLinks.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
			_persistence.create(pk);

		Assert.assertNotNull(appBuilderWorkflowTaskLink);

		Assert.assertEquals(appBuilderWorkflowTaskLink.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AppBuilderWorkflowTaskLink newAppBuilderWorkflowTaskLink =
			addAppBuilderWorkflowTaskLink();

		_persistence.remove(newAppBuilderWorkflowTaskLink);

		AppBuilderWorkflowTaskLink existingAppBuilderWorkflowTaskLink =
			_persistence.fetchByPrimaryKey(
				newAppBuilderWorkflowTaskLink.getPrimaryKey());

		Assert.assertNull(existingAppBuilderWorkflowTaskLink);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAppBuilderWorkflowTaskLink();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderWorkflowTaskLink newAppBuilderWorkflowTaskLink =
			_persistence.create(pk);

		newAppBuilderWorkflowTaskLink.setMvccVersion(RandomTestUtil.nextLong());

		newAppBuilderWorkflowTaskLink.setCompanyId(RandomTestUtil.nextLong());

		newAppBuilderWorkflowTaskLink.setAppBuilderAppId(
			RandomTestUtil.nextLong());

		newAppBuilderWorkflowTaskLink.setAppBuilderAppVersionId(
			RandomTestUtil.nextLong());

		newAppBuilderWorkflowTaskLink.setDdmStructureLayoutId(
			RandomTestUtil.nextLong());

		newAppBuilderWorkflowTaskLink.setReadOnly(
			RandomTestUtil.randomBoolean());

		newAppBuilderWorkflowTaskLink.setWorkflowTaskName(
			RandomTestUtil.randomString());

		_appBuilderWorkflowTaskLinks.add(
			_persistence.update(newAppBuilderWorkflowTaskLink));

		AppBuilderWorkflowTaskLink existingAppBuilderWorkflowTaskLink =
			_persistence.findByPrimaryKey(
				newAppBuilderWorkflowTaskLink.getPrimaryKey());

		Assert.assertEquals(
			existingAppBuilderWorkflowTaskLink.getMvccVersion(),
			newAppBuilderWorkflowTaskLink.getMvccVersion());
		Assert.assertEquals(
			existingAppBuilderWorkflowTaskLink.
				getAppBuilderWorkflowTaskLinkId(),
			newAppBuilderWorkflowTaskLink.getAppBuilderWorkflowTaskLinkId());
		Assert.assertEquals(
			existingAppBuilderWorkflowTaskLink.getCompanyId(),
			newAppBuilderWorkflowTaskLink.getCompanyId());
		Assert.assertEquals(
			existingAppBuilderWorkflowTaskLink.getAppBuilderAppId(),
			newAppBuilderWorkflowTaskLink.getAppBuilderAppId());
		Assert.assertEquals(
			existingAppBuilderWorkflowTaskLink.getAppBuilderAppVersionId(),
			newAppBuilderWorkflowTaskLink.getAppBuilderAppVersionId());
		Assert.assertEquals(
			existingAppBuilderWorkflowTaskLink.getDdmStructureLayoutId(),
			newAppBuilderWorkflowTaskLink.getDdmStructureLayoutId());
		Assert.assertEquals(
			existingAppBuilderWorkflowTaskLink.isReadOnly(),
			newAppBuilderWorkflowTaskLink.isReadOnly());
		Assert.assertEquals(
			existingAppBuilderWorkflowTaskLink.getWorkflowTaskName(),
			newAppBuilderWorkflowTaskLink.getWorkflowTaskName());
	}

	@Test
	public void testCountByAppBuilderAppId() throws Exception {
		_persistence.countByAppBuilderAppId(RandomTestUtil.nextLong());

		_persistence.countByAppBuilderAppId(0L);
	}

	@Test
	public void testCountByA_A() throws Exception {
		_persistence.countByA_A(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByA_A(0L, 0L);
	}

	@Test
	public void testCountByA_A_W() throws Exception {
		_persistence.countByA_A_W(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "");

		_persistence.countByA_A_W(0L, 0L, "null");

		_persistence.countByA_A_W(0L, 0L, (String)null);
	}

	@Test
	public void testCountByA_A_D_W() throws Exception {
		_persistence.countByA_A_D_W(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), "");

		_persistence.countByA_A_D_W(0L, 0L, 0L, "null");

		_persistence.countByA_A_D_W(0L, 0L, 0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AppBuilderWorkflowTaskLink newAppBuilderWorkflowTaskLink =
			addAppBuilderWorkflowTaskLink();

		AppBuilderWorkflowTaskLink existingAppBuilderWorkflowTaskLink =
			_persistence.findByPrimaryKey(
				newAppBuilderWorkflowTaskLink.getPrimaryKey());

		Assert.assertEquals(
			existingAppBuilderWorkflowTaskLink, newAppBuilderWorkflowTaskLink);
	}

	@Test(expected = NoSuchTaskLinkException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AppBuilderWorkflowTaskLink>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"AppBuilderWorkflowTaskLink", "mvccVersion", true,
			"appBuilderWorkflowTaskLinkId", true, "companyId", true,
			"appBuilderAppId", true, "appBuilderAppVersionId", true,
			"ddmStructureLayoutId", true, "readOnly", true, "workflowTaskName",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AppBuilderWorkflowTaskLink newAppBuilderWorkflowTaskLink =
			addAppBuilderWorkflowTaskLink();

		AppBuilderWorkflowTaskLink existingAppBuilderWorkflowTaskLink =
			_persistence.fetchByPrimaryKey(
				newAppBuilderWorkflowTaskLink.getPrimaryKey());

		Assert.assertEquals(
			existingAppBuilderWorkflowTaskLink, newAppBuilderWorkflowTaskLink);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderWorkflowTaskLink missingAppBuilderWorkflowTaskLink =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAppBuilderWorkflowTaskLink);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AppBuilderWorkflowTaskLink newAppBuilderWorkflowTaskLink1 =
			addAppBuilderWorkflowTaskLink();
		AppBuilderWorkflowTaskLink newAppBuilderWorkflowTaskLink2 =
			addAppBuilderWorkflowTaskLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAppBuilderWorkflowTaskLink1.getPrimaryKey());
		primaryKeys.add(newAppBuilderWorkflowTaskLink2.getPrimaryKey());

		Map<Serializable, AppBuilderWorkflowTaskLink>
			appBuilderWorkflowTaskLinks = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, appBuilderWorkflowTaskLinks.size());
		Assert.assertEquals(
			newAppBuilderWorkflowTaskLink1,
			appBuilderWorkflowTaskLinks.get(
				newAppBuilderWorkflowTaskLink1.getPrimaryKey()));
		Assert.assertEquals(
			newAppBuilderWorkflowTaskLink2,
			appBuilderWorkflowTaskLinks.get(
				newAppBuilderWorkflowTaskLink2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AppBuilderWorkflowTaskLink>
			appBuilderWorkflowTaskLinks = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(appBuilderWorkflowTaskLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AppBuilderWorkflowTaskLink newAppBuilderWorkflowTaskLink =
			addAppBuilderWorkflowTaskLink();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAppBuilderWorkflowTaskLink.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AppBuilderWorkflowTaskLink>
			appBuilderWorkflowTaskLinks = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, appBuilderWorkflowTaskLinks.size());
		Assert.assertEquals(
			newAppBuilderWorkflowTaskLink,
			appBuilderWorkflowTaskLinks.get(
				newAppBuilderWorkflowTaskLink.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AppBuilderWorkflowTaskLink>
			appBuilderWorkflowTaskLinks = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(appBuilderWorkflowTaskLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AppBuilderWorkflowTaskLink newAppBuilderWorkflowTaskLink =
			addAppBuilderWorkflowTaskLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAppBuilderWorkflowTaskLink.getPrimaryKey());

		Map<Serializable, AppBuilderWorkflowTaskLink>
			appBuilderWorkflowTaskLinks = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, appBuilderWorkflowTaskLinks.size());
		Assert.assertEquals(
			newAppBuilderWorkflowTaskLink,
			appBuilderWorkflowTaskLinks.get(
				newAppBuilderWorkflowTaskLink.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AppBuilderWorkflowTaskLinkLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<AppBuilderWorkflowTaskLink>() {

				@Override
				public void performAction(
					AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink) {

					Assert.assertNotNull(appBuilderWorkflowTaskLink);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AppBuilderWorkflowTaskLink newAppBuilderWorkflowTaskLink =
			addAppBuilderWorkflowTaskLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderWorkflowTaskLink.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"appBuilderWorkflowTaskLinkId",
				newAppBuilderWorkflowTaskLink.
					getAppBuilderWorkflowTaskLinkId()));

		List<AppBuilderWorkflowTaskLink> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AppBuilderWorkflowTaskLink existingAppBuilderWorkflowTaskLink =
			result.get(0);

		Assert.assertEquals(
			existingAppBuilderWorkflowTaskLink, newAppBuilderWorkflowTaskLink);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderWorkflowTaskLink.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"appBuilderWorkflowTaskLinkId", RandomTestUtil.nextLong()));

		List<AppBuilderWorkflowTaskLink> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AppBuilderWorkflowTaskLink newAppBuilderWorkflowTaskLink =
			addAppBuilderWorkflowTaskLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderWorkflowTaskLink.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("appBuilderWorkflowTaskLinkId"));

		Object newAppBuilderWorkflowTaskLinkId =
			newAppBuilderWorkflowTaskLink.getAppBuilderWorkflowTaskLinkId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"appBuilderWorkflowTaskLinkId",
				new Object[] {newAppBuilderWorkflowTaskLinkId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAppBuilderWorkflowTaskLinkId = result.get(0);

		Assert.assertEquals(
			existingAppBuilderWorkflowTaskLinkId,
			newAppBuilderWorkflowTaskLinkId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderWorkflowTaskLink.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("appBuilderWorkflowTaskLinkId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"appBuilderWorkflowTaskLinkId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AppBuilderWorkflowTaskLink newAppBuilderWorkflowTaskLink =
			addAppBuilderWorkflowTaskLink();

		_persistence.clearCache();

		AppBuilderWorkflowTaskLink existingAppBuilderWorkflowTaskLink =
			_persistence.findByPrimaryKey(
				newAppBuilderWorkflowTaskLink.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(
				existingAppBuilderWorkflowTaskLink.getAppBuilderAppId()),
			ReflectionTestUtil.<Long>invoke(
				existingAppBuilderWorkflowTaskLink,
				"getOriginalAppBuilderAppId", new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(
				existingAppBuilderWorkflowTaskLink.getAppBuilderAppVersionId()),
			ReflectionTestUtil.<Long>invoke(
				existingAppBuilderWorkflowTaskLink,
				"getOriginalAppBuilderAppVersionId", new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(
				existingAppBuilderWorkflowTaskLink.getDdmStructureLayoutId()),
			ReflectionTestUtil.<Long>invoke(
				existingAppBuilderWorkflowTaskLink,
				"getOriginalDdmStructureLayoutId", new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingAppBuilderWorkflowTaskLink.getWorkflowTaskName(),
				ReflectionTestUtil.invoke(
					existingAppBuilderWorkflowTaskLink,
					"getOriginalWorkflowTaskName", new Class<?>[0])));
	}

	protected AppBuilderWorkflowTaskLink addAppBuilderWorkflowTaskLink()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
			_persistence.create(pk);

		appBuilderWorkflowTaskLink.setMvccVersion(RandomTestUtil.nextLong());

		appBuilderWorkflowTaskLink.setCompanyId(RandomTestUtil.nextLong());

		appBuilderWorkflowTaskLink.setAppBuilderAppId(
			RandomTestUtil.nextLong());

		appBuilderWorkflowTaskLink.setAppBuilderAppVersionId(
			RandomTestUtil.nextLong());

		appBuilderWorkflowTaskLink.setDdmStructureLayoutId(
			RandomTestUtil.nextLong());

		appBuilderWorkflowTaskLink.setReadOnly(RandomTestUtil.randomBoolean());

		appBuilderWorkflowTaskLink.setWorkflowTaskName(
			RandomTestUtil.randomString());

		_appBuilderWorkflowTaskLinks.add(
			_persistence.update(appBuilderWorkflowTaskLink));

		return appBuilderWorkflowTaskLink;
	}

	private List<AppBuilderWorkflowTaskLink> _appBuilderWorkflowTaskLinks =
		new ArrayList<AppBuilderWorkflowTaskLink>();
	private AppBuilderWorkflowTaskLinkPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
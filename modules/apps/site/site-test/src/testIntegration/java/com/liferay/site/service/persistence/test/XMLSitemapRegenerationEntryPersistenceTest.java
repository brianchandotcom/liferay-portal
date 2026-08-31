/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.site.exception.NoSuchXMLSitemapRegenerationEntryException;
import com.liferay.site.model.XMLSitemapRegenerationEntry;
import com.liferay.site.service.XMLSitemapRegenerationEntryLocalServiceUtil;
import com.liferay.site.service.persistence.XMLSitemapRegenerationEntryPersistence;
import com.liferay.site.service.persistence.XMLSitemapRegenerationEntryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
public class XMLSitemapRegenerationEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.site.service"));

	@Before
	public void setUp() {
		_persistence = XMLSitemapRegenerationEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<XMLSitemapRegenerationEntry> iterator =
			_xmlSitemapRegenerationEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		XMLSitemapRegenerationEntry xmlSitemapRegenerationEntry =
			_persistence.create(pk);

		Assert.assertNotNull(xmlSitemapRegenerationEntry);

		Assert.assertEquals(xmlSitemapRegenerationEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		XMLSitemapRegenerationEntry newXMLSitemapRegenerationEntry =
			addXMLSitemapRegenerationEntry();

		_persistence.remove(newXMLSitemapRegenerationEntry);

		XMLSitemapRegenerationEntry existingXMLSitemapRegenerationEntry =
			_persistence.fetchByPrimaryKey(
				newXMLSitemapRegenerationEntry.getPrimaryKey());

		Assert.assertNull(existingXMLSitemapRegenerationEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addXMLSitemapRegenerationEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		XMLSitemapRegenerationEntry newXMLSitemapRegenerationEntry =
			addXMLSitemapRegenerationEntry();

		newXMLSitemapRegenerationEntry.setGroupId(RandomTestUtil.nextLong());

		newXMLSitemapRegenerationEntry.setCompanyId(RandomTestUtil.nextLong());

		newXMLSitemapRegenerationEntry.setAssetTypeKey(
			RandomTestUtil.randomString());

		newXMLSitemapRegenerationEntry = _persistence.update(
			newXMLSitemapRegenerationEntry);

		_xmlSitemapRegenerationEntries.add(newXMLSitemapRegenerationEntry);

		XMLSitemapRegenerationEntry existingXMLSitemapRegenerationEntry =
			_persistence.findByPrimaryKey(
				newXMLSitemapRegenerationEntry.getPrimaryKey());

		Assert.assertEquals(
			existingXMLSitemapRegenerationEntry.getMvccVersion(),
			newXMLSitemapRegenerationEntry.getMvccVersion());
		Assert.assertEquals(
			existingXMLSitemapRegenerationEntry.
				getXmlSitemapRegenerationEntryId(),
			newXMLSitemapRegenerationEntry.getXmlSitemapRegenerationEntryId());
		Assert.assertEquals(
			existingXMLSitemapRegenerationEntry.getGroupId(),
			newXMLSitemapRegenerationEntry.getGroupId());
		Assert.assertEquals(
			existingXMLSitemapRegenerationEntry.getCompanyId(),
			newXMLSitemapRegenerationEntry.getCompanyId());
		Assert.assertEquals(
			existingXMLSitemapRegenerationEntry.getAssetTypeKey(),
			newXMLSitemapRegenerationEntry.getAssetTypeKey());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByG_C_A() throws Exception {
		_persistence.countByG_C_A(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "");

		_persistence.countByG_C_A(0L, 0L, "null");

		_persistence.countByG_C_A(0L, 0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		XMLSitemapRegenerationEntry newXMLSitemapRegenerationEntry =
			addXMLSitemapRegenerationEntry();

		XMLSitemapRegenerationEntry existingXMLSitemapRegenerationEntry =
			_persistence.findByPrimaryKey(
				newXMLSitemapRegenerationEntry.getPrimaryKey());

		Assert.assertEquals(
			existingXMLSitemapRegenerationEntry,
			newXMLSitemapRegenerationEntry);
	}

	@Test(expected = NoSuchXMLSitemapRegenerationEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<XMLSitemapRegenerationEntry>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"XMLSitemapRegenerationEntry", "mvccVersion", true,
			"xmlSitemapRegenerationEntryId", true, "groupId", true, "companyId",
			true, "assetTypeKey", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		XMLSitemapRegenerationEntry newXMLSitemapRegenerationEntry =
			addXMLSitemapRegenerationEntry();

		XMLSitemapRegenerationEntry existingXMLSitemapRegenerationEntry =
			_persistence.fetchByPrimaryKey(
				newXMLSitemapRegenerationEntry.getPrimaryKey());

		Assert.assertEquals(
			existingXMLSitemapRegenerationEntry,
			newXMLSitemapRegenerationEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		XMLSitemapRegenerationEntry missingXMLSitemapRegenerationEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingXMLSitemapRegenerationEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		XMLSitemapRegenerationEntry newXMLSitemapRegenerationEntry1 =
			addXMLSitemapRegenerationEntry();
		XMLSitemapRegenerationEntry newXMLSitemapRegenerationEntry2 =
			addXMLSitemapRegenerationEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newXMLSitemapRegenerationEntry1.getPrimaryKey());
		primaryKeys.add(newXMLSitemapRegenerationEntry2.getPrimaryKey());

		Map<Serializable, XMLSitemapRegenerationEntry>
			xmlSitemapRegenerationEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, xmlSitemapRegenerationEntries.size());
		Assert.assertEquals(
			newXMLSitemapRegenerationEntry1,
			xmlSitemapRegenerationEntries.get(
				newXMLSitemapRegenerationEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newXMLSitemapRegenerationEntry2,
			xmlSitemapRegenerationEntries.get(
				newXMLSitemapRegenerationEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, XMLSitemapRegenerationEntry>
			xmlSitemapRegenerationEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(xmlSitemapRegenerationEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		XMLSitemapRegenerationEntry newXMLSitemapRegenerationEntry =
			addXMLSitemapRegenerationEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newXMLSitemapRegenerationEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, XMLSitemapRegenerationEntry>
			xmlSitemapRegenerationEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, xmlSitemapRegenerationEntries.size());
		Assert.assertEquals(
			newXMLSitemapRegenerationEntry,
			xmlSitemapRegenerationEntries.get(
				newXMLSitemapRegenerationEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, XMLSitemapRegenerationEntry>
			xmlSitemapRegenerationEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(xmlSitemapRegenerationEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		XMLSitemapRegenerationEntry newXMLSitemapRegenerationEntry =
			addXMLSitemapRegenerationEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newXMLSitemapRegenerationEntry.getPrimaryKey());

		Map<Serializable, XMLSitemapRegenerationEntry>
			xmlSitemapRegenerationEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, xmlSitemapRegenerationEntries.size());
		Assert.assertEquals(
			newXMLSitemapRegenerationEntry,
			xmlSitemapRegenerationEntries.get(
				newXMLSitemapRegenerationEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			XMLSitemapRegenerationEntryLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<XMLSitemapRegenerationEntry>() {

				@Override
				public void performAction(
					XMLSitemapRegenerationEntry xmlSitemapRegenerationEntry) {

					Assert.assertNotNull(xmlSitemapRegenerationEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		XMLSitemapRegenerationEntry newXMLSitemapRegenerationEntry =
			addXMLSitemapRegenerationEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			XMLSitemapRegenerationEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"xmlSitemapRegenerationEntryId",
				newXMLSitemapRegenerationEntry.
					getXmlSitemapRegenerationEntryId()));

		List<XMLSitemapRegenerationEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		XMLSitemapRegenerationEntry existingXMLSitemapRegenerationEntry =
			result.get(0);

		Assert.assertEquals(
			existingXMLSitemapRegenerationEntry,
			newXMLSitemapRegenerationEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			XMLSitemapRegenerationEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"xmlSitemapRegenerationEntryId", RandomTestUtil.nextLong()));

		List<XMLSitemapRegenerationEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		XMLSitemapRegenerationEntry newXMLSitemapRegenerationEntry =
			addXMLSitemapRegenerationEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			XMLSitemapRegenerationEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("xmlSitemapRegenerationEntryId"));

		Object newXmlSitemapRegenerationEntryId =
			newXMLSitemapRegenerationEntry.getXmlSitemapRegenerationEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"xmlSitemapRegenerationEntryId",
				new Object[] {newXmlSitemapRegenerationEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingXmlSitemapRegenerationEntryId = result.get(0);

		Assert.assertEquals(
			existingXmlSitemapRegenerationEntryId,
			newXmlSitemapRegenerationEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			XMLSitemapRegenerationEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("xmlSitemapRegenerationEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"xmlSitemapRegenerationEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected XMLSitemapRegenerationEntry addXMLSitemapRegenerationEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		XMLSitemapRegenerationEntry xmlSitemapRegenerationEntry =
			_persistence.create(pk);

		xmlSitemapRegenerationEntry.setGroupId(RandomTestUtil.nextLong());

		xmlSitemapRegenerationEntry.setCompanyId(RandomTestUtil.nextLong());

		xmlSitemapRegenerationEntry.setAssetTypeKey(
			RandomTestUtil.randomString());

		_xmlSitemapRegenerationEntries.add(
			_persistence.update(xmlSitemapRegenerationEntry));

		return xmlSitemapRegenerationEntry;
	}

	private List<XMLSitemapRegenerationEntry> _xmlSitemapRegenerationEntries =
		new ArrayList<XMLSitemapRegenerationEntry>();
	private XMLSitemapRegenerationEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
// LIFERAY-SERVICE-BUILDER-HASH:1539711826
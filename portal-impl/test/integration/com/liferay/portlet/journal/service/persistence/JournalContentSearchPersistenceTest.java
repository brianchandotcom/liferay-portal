/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.journal.NoSuchContentSearchException;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.model.impl.JournalContentSearchModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalContentSearchPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (JournalContentSearchPersistence)PortalBeanLocatorUtil.locate(JournalContentSearchPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		JournalContentSearch journalContentSearch = _persistence.create(pk);

		assertNotNull(journalContentSearch);

		assertEquals(journalContentSearch.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		_persistence.remove(newJournalContentSearch);

		JournalContentSearch existingJournalContentSearch = _persistence.fetchByPrimaryKey(newJournalContentSearch.getPrimaryKey());

		assertNull(existingJournalContentSearch);
	}

	public void testUpdateNew() throws Exception {
		addJournalContentSearch();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		JournalContentSearch newJournalContentSearch = _persistence.create(pk);

		newJournalContentSearch.setGroupId(nextLong());

		newJournalContentSearch.setCompanyId(nextLong());

		newJournalContentSearch.setPrivateLayout(randomBoolean());

		newJournalContentSearch.setLayoutId(nextLong());

		newJournalContentSearch.setPortletId(randomString());

		newJournalContentSearch.setArticleId(randomString());

		_persistence.update(newJournalContentSearch, false);

		JournalContentSearch existingJournalContentSearch = _persistence.findByPrimaryKey(newJournalContentSearch.getPrimaryKey());

		assertEquals(existingJournalContentSearch.getContentSearchId(),
			newJournalContentSearch.getContentSearchId());
		assertEquals(existingJournalContentSearch.getGroupId(),
			newJournalContentSearch.getGroupId());
		assertEquals(existingJournalContentSearch.getCompanyId(),
			newJournalContentSearch.getCompanyId());
		assertEquals(existingJournalContentSearch.getPrivateLayout(),
			newJournalContentSearch.getPrivateLayout());
		assertEquals(existingJournalContentSearch.getLayoutId(),
			newJournalContentSearch.getLayoutId());
		assertEquals(existingJournalContentSearch.getPortletId(),
			newJournalContentSearch.getPortletId());
		assertEquals(existingJournalContentSearch.getArticleId(),
			newJournalContentSearch.getArticleId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		JournalContentSearch existingJournalContentSearch = _persistence.findByPrimaryKey(newJournalContentSearch.getPrimaryKey());

		assertEquals(existingJournalContentSearch, newJournalContentSearch);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchContentSearchException");
		}
		catch (NoSuchContentSearchException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		JournalContentSearch existingJournalContentSearch = _persistence.fetchByPrimaryKey(newJournalContentSearch.getPrimaryKey());

		assertEquals(existingJournalContentSearch, newJournalContentSearch);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		JournalContentSearch missingJournalContentSearch = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingJournalContentSearch);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalContentSearch.class,
				JournalContentSearch.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentSearchId",
				newJournalContentSearch.getContentSearchId()));

		List<JournalContentSearch> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		JournalContentSearch existingJournalContentSearch = result.get(0);

		assertEquals(existingJournalContentSearch, newJournalContentSearch);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalContentSearch.class,
				JournalContentSearch.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentSearchId",
				nextLong()));

		List<JournalContentSearch> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalContentSearch.class,
				JournalContentSearch.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"contentSearchId"));

		Object newContentSearchId = newJournalContentSearch.getContentSearchId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentSearchId",
				new Object[] { newContentSearchId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingContentSearchId = result.get(0);

		assertEquals(existingContentSearchId, newContentSearchId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalContentSearch.class,
				JournalContentSearch.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"contentSearchId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentSearchId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		_persistence.clearCache();

		JournalContentSearchModelImpl existingJournalContentSearchModelImpl = (JournalContentSearchModelImpl)_persistence.findByPrimaryKey(newJournalContentSearch.getPrimaryKey());

		assertEquals(existingJournalContentSearchModelImpl.getGroupId(),
			existingJournalContentSearchModelImpl.getOriginalGroupId());
		assertEquals(existingJournalContentSearchModelImpl.getPrivateLayout(),
			existingJournalContentSearchModelImpl.getOriginalPrivateLayout());
		assertEquals(existingJournalContentSearchModelImpl.getLayoutId(),
			existingJournalContentSearchModelImpl.getOriginalLayoutId());
		assertTrue(Validator.equals(
				existingJournalContentSearchModelImpl.getPortletId(),
				existingJournalContentSearchModelImpl.getOriginalPortletId()));
		assertTrue(Validator.equals(
				existingJournalContentSearchModelImpl.getArticleId(),
				existingJournalContentSearchModelImpl.getOriginalArticleId()));
	}

	protected JournalContentSearch addJournalContentSearch()
		throws Exception {
		long pk = nextLong();

		JournalContentSearch journalContentSearch = _persistence.create(pk);

		journalContentSearch.setGroupId(nextLong());

		journalContentSearch.setCompanyId(nextLong());

		journalContentSearch.setPrivateLayout(randomBoolean());

		journalContentSearch.setLayoutId(nextLong());

		journalContentSearch.setPortletId(randomString());

		journalContentSearch.setArticleId(randomString());

		_persistence.update(journalContentSearch, false);

		return journalContentSearch;
	}

	private JournalContentSearchPersistence _persistence;
}
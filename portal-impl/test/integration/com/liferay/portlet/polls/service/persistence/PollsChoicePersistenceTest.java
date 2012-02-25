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

package com.liferay.portlet.polls.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.polls.NoSuchChoiceException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.impl.PollsChoiceModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PollsChoicePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (PollsChoicePersistence)PortalBeanLocatorUtil.locate(PollsChoicePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		PollsChoice pollsChoice = _persistence.create(pk);

		assertNotNull(pollsChoice);

		assertEquals(pollsChoice.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		PollsChoice newPollsChoice = addPollsChoice();

		_persistence.remove(newPollsChoice);

		PollsChoice existingPollsChoice = _persistence.fetchByPrimaryKey(newPollsChoice.getPrimaryKey());

		assertNull(existingPollsChoice);
	}

	public void testUpdateNew() throws Exception {
		addPollsChoice();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		PollsChoice newPollsChoice = _persistence.create(pk);

		newPollsChoice.setUuid(randomString());

		newPollsChoice.setQuestionId(nextLong());

		newPollsChoice.setName(randomString());

		newPollsChoice.setDescription(randomString());

		_persistence.update(newPollsChoice, false);

		PollsChoice existingPollsChoice = _persistence.findByPrimaryKey(newPollsChoice.getPrimaryKey());

		assertEquals(existingPollsChoice.getUuid(), newPollsChoice.getUuid());
		assertEquals(existingPollsChoice.getChoiceId(),
			newPollsChoice.getChoiceId());
		assertEquals(existingPollsChoice.getQuestionId(),
			newPollsChoice.getQuestionId());
		assertEquals(existingPollsChoice.getName(), newPollsChoice.getName());
		assertEquals(existingPollsChoice.getDescription(),
			newPollsChoice.getDescription());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		PollsChoice newPollsChoice = addPollsChoice();

		PollsChoice existingPollsChoice = _persistence.findByPrimaryKey(newPollsChoice.getPrimaryKey());

		assertEquals(existingPollsChoice, newPollsChoice);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchChoiceException");
		}
		catch (NoSuchChoiceException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		PollsChoice newPollsChoice = addPollsChoice();

		PollsChoice existingPollsChoice = _persistence.fetchByPrimaryKey(newPollsChoice.getPrimaryKey());

		assertEquals(existingPollsChoice, newPollsChoice);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		PollsChoice missingPollsChoice = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingPollsChoice);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PollsChoice newPollsChoice = addPollsChoice();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsChoice.class,
				PollsChoice.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("choiceId",
				newPollsChoice.getChoiceId()));

		List<PollsChoice> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		PollsChoice existingPollsChoice = result.get(0);

		assertEquals(existingPollsChoice, newPollsChoice);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsChoice.class,
				PollsChoice.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("choiceId", nextLong()));

		List<PollsChoice> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PollsChoice newPollsChoice = addPollsChoice();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsChoice.class,
				PollsChoice.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("choiceId"));

		Object newChoiceId = newPollsChoice.getChoiceId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("choiceId",
				new Object[] { newChoiceId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingChoiceId = result.get(0);

		assertEquals(existingChoiceId, newChoiceId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsChoice.class,
				PollsChoice.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("choiceId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("choiceId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PollsChoice newPollsChoice = addPollsChoice();

		_persistence.clearCache();

		PollsChoiceModelImpl existingPollsChoiceModelImpl = (PollsChoiceModelImpl)_persistence.findByPrimaryKey(newPollsChoice.getPrimaryKey());

		assertEquals(existingPollsChoiceModelImpl.getQuestionId(),
			existingPollsChoiceModelImpl.getOriginalQuestionId());
		assertTrue(Validator.equals(existingPollsChoiceModelImpl.getName(),
				existingPollsChoiceModelImpl.getOriginalName()));
	}

	protected PollsChoice addPollsChoice() throws Exception {
		long pk = nextLong();

		PollsChoice pollsChoice = _persistence.create(pk);

		pollsChoice.setUuid(randomString());

		pollsChoice.setQuestionId(nextLong());

		pollsChoice.setName(randomString());

		pollsChoice.setDescription(randomString());

		_persistence.update(pollsChoice, false);

		return pollsChoice;
	}

	private PollsChoicePersistence _persistence;
}
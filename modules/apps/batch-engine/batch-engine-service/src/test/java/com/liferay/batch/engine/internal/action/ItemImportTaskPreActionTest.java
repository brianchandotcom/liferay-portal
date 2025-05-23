/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.action;

import com.fasterxml.jackson.annotation.JsonFilter;

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.context.ImportTaskContext;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class ItemImportTaskPreActionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_mockUser();
		_mockUserLocalService();

		PrincipalThreadLocal.setName(_CURRENT_USER_ID);

		ReflectionTestUtil.setFieldValue(
			_itemImportTaskPreAction, "_jsonFactory", new JSONFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			_itemImportTaskPreAction, "_userLocalService", _userLocalService);

		_testEntity = _createTestEntity();
	}

	@Test
	public void testRunWithImportCreatorStrategy1() throws Exception {
		Mockito.doReturn(
			_user
		).when(
			_userLocalService
		).getUser(
			Mockito.anyLong()
		);

		_run(
			BatchEngineImportTaskConstants.IMPORT_CREATOR_STRATEGY_KEEP_CREATOR,
			_importTaskContext);

		Assert.assertEquals(_user, _importTaskContext.getOriginalUser());
		Assert.assertEquals(
			String.valueOf(_user.getUserId()), PrincipalThreadLocal.getName());

		Mockito.verify(
			_batchEngineTaskItemDelegate
		).setContextUser(
			_user
		);
	}

	@Test
	public void testRunWithImportCreatorStrategy2() throws Exception {
		Mockito.doReturn(
			null
		).when(
			_userLocalService
		).fetchUserByExternalReferenceCode(
			Mockito.anyString(), Mockito.anyLong()
		);

		_run(
			BatchEngineImportTaskConstants.IMPORT_CREATOR_STRATEGY_KEEP_CREATOR,
			_importTaskContext);

		Assert.assertEquals(
			String.valueOf(_CURRENT_USER_ID), PrincipalThreadLocal.getName());
		Assert.assertNull(_importTaskContext.getOriginalUser());

		Mockito.verify(
			_batchEngineTaskItemDelegate, Mockito.never()
		).setContextUser(
			_user
		);
	}

	@Test
	public void testRunWithNoImportCreatorStrategy() throws Exception {
		_run(RandomTestUtil.randomString(), _importTaskContext);

		Assert.assertEquals(
			String.valueOf(_CURRENT_USER_ID), PrincipalThreadLocal.getName());
		Assert.assertNull(_importTaskContext.getOriginalUser());

		Mockito.verify(
			_batchEngineTaskItemDelegate, Mockito.never()
		).setContextUser(
			_user
		);
	}

	private TestEntity _createTestEntity() {
		TestEntity testEntity = new TestEntity();

		Creator creator = new Creator();

		creator.setExternalReferenceCode(_user.getExternalReferenceCode());
		creator.setId(_user.getUserId());

		testEntity.setCreator(creator);

		return testEntity;
	}

	private void _mockUser() throws Exception {
		Mockito.when(
			_user.getExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_user.getUserId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);
	}

	private void _mockUserLocalService() {
		Mockito.doReturn(
			_user
		).when(
			_userLocalService
		).fetchUserByExternalReferenceCode(
			Mockito.anyString(), Mockito.anyLong()
		);

		Mockito.doReturn(
			_user
		).when(
			_userLocalService
		).fetchUserById(
			Mockito.anyLong()
		);
	}

	private void _run(
			String importCreatorStrategy, ImportTaskContext importTaskContext)
		throws Exception {

		Mockito.when(
			_batchEngineImportTask.getParameterValue("importCreatorStrategy")
		).thenReturn(
			importCreatorStrategy
		);

		_itemImportTaskPreAction.run(
			_batchEngineImportTask, _batchEngineTaskItemDelegate, importTaskContext, _testEntity);
	}

	private static final long _CURRENT_USER_ID = RandomTestUtil.randomLong();

	private final BatchEngineImportTask _batchEngineImportTask = Mockito.mock(
		BatchEngineImportTask.class);
	private final BatchEngineTaskItemDelegate<?> _batchEngineTaskItemDelegate = Mockito.mock(
		BatchEngineTaskItemDelegate.class);
	private final ImportTaskContext _importTaskContext =
		new ImportTaskContext();
	private final ItemImportTaskPreAction _itemImportTaskPreAction =
		new ItemImportTaskPreAction();
	private TestEntity _testEntity;
	private final User _user = Mockito.mock(User.class);
	private final UserLocalService _userLocalService = Mockito.mock(
		UserLocalService.class);

	@JsonFilter("Liferay.Vulcan")
	private class Creator {

		public String getExternalReferenceCode() {
			return _externalReferenceCode;
		}

		public long getId() {
			return _id;
		}

		public void setExternalReferenceCode(String externalReferenceCode) {
			_externalReferenceCode = externalReferenceCode;
		}

		public void setId(long id) {
			_id = id;
		}

		private String _externalReferenceCode;
		private long _id;

	}

	@JsonFilter("Liferay.Vulcan")
	private class TestEntity {

		public Creator getCreator() {
			return _creator;
		}

		public void setCreator(Creator creator) {
			_creator = creator;
		}

		private Creator _creator;

	}

}
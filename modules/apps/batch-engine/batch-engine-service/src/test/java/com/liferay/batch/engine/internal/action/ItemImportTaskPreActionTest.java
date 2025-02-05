/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.action;

import com.fasterxml.jackson.annotation.JsonFilter;

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
	public void testImportWithKeepCreatorStrategy() throws Exception {
		_executeImportTask(
			BatchEngineImportTaskConstants.IMPORT_CREATOR_STRATEGY_KEEP_CREATOR,
			_importTaskContext);

		Assert.assertEquals(
			String.valueOf(_user.getUserId()), PrincipalThreadLocal.getName());
		Assert.assertEquals(
			String.valueOf(_CURRENT_USER_ID),
			_importTaskContext.getOriginalUserId());
	}

	@Test
	public void testImportWithKeepCreatorStrategyAndUserDoesNotExist()
		throws Exception {

		Mockito.doReturn(
			null
		).when(
			_userLocalService
		).fetchUserByExternalReferenceCode(
			Mockito.anyString(), Mockito.anyLong()
		);

		_executeImportTask(
			BatchEngineImportTaskConstants.IMPORT_CREATOR_STRATEGY_KEEP_CREATOR,
			_importTaskContext);

		Assert.assertEquals(
			String.valueOf(_CURRENT_USER_ID), PrincipalThreadLocal.getName());
		Assert.assertNull(_importTaskContext.getOriginalUserId());
	}

	@Test
	public void testImportWithNotKeepCreatorStrategy() throws Exception {
		_executeImportTask(RandomTestUtil.randomString(), _importTaskContext);

		Assert.assertEquals(
			String.valueOf(_CURRENT_USER_ID), PrincipalThreadLocal.getName());
		Assert.assertNull(_importTaskContext.getOriginalUserId());
	}

	private TestEntity _createTestEntity() {
		TestEntity testEntity = new TestEntity();

		Creator creator = new Creator();

		creator.setId(_user.getUserId());
		creator.setExternalReferenceCode(_user.getExternalReferenceCode());

		testEntity.setCreator(creator);

		return testEntity;
	}

	private void _executeImportTask(
			String importCreatorStrategy, ImportTaskContext importTaskContext)
		throws Exception {

		Mockito.when(
			_batchEngineImportTask.getParameterValue("importCreatorStrategy")
		).thenReturn(
			importCreatorStrategy
		);

		_itemImportTaskPreAction.run(
			_batchEngineImportTask, importTaskContext, _testEntity);
	}

	private void _mockUser() throws Exception {
		Mockito.when(
			_user.getUserId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			_user.getExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
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

	private static final long _CURRENT_USER_ID = RandomTestUtil.randomLong();

	private final BatchEngineImportTask _batchEngineImportTask = Mockito.mock(
		BatchEngineImportTask.class);
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
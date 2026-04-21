/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.security.permission.resource;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Stefano Motta
 */
public class CommerceOrderAttachmentObjectEntryModelResourcePermissionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		_commerceOrderAttachmentObjectEntryModelResourcePermission =
			new CommerceOrderAttachmentObjectEntryModelResourcePermission(
				_commerceOrderLocalService,
				_commerceOrderModelResourcePermission, _modelResourcePermission,
				_objectEntryLocalService);
	}

	@Test
	public void testContains() throws Exception {
		_assertPermission(
			RandomTestUtil.randomString(), _modelResourcePermission,
			_commerceOrderModelResourcePermission,
			_mockObjectEntry(RandomTestUtil.randomLong()));
		_assertPermission(
			ActionKeys.VIEW, _modelResourcePermission,
			_commerceOrderModelResourcePermission, _mockObjectEntry(0));

		long commerceOrderId = RandomTestUtil.randomLong();

		Mockito.when(
			_commerceOrderLocalService.fetchCommerceOrder(commerceOrderId)
		).thenReturn(
			null
		);

		_assertPermission(
			ActionKeys.VIEW, _modelResourcePermission,
			_commerceOrderModelResourcePermission,
			_mockObjectEntry(commerceOrderId));

		Mockito.when(
			_commerceOrderLocalService.fetchCommerceOrder(commerceOrderId)
		).thenReturn(
			_commerceOrder
		);

		_assertPermission(
			ActionKeys.DELETE, _commerceOrderModelResourcePermission,
			_modelResourcePermission, _mockObjectEntry(commerceOrderId));
		_assertPermission(
			ActionKeys.UPDATE, _commerceOrderModelResourcePermission,
			_modelResourcePermission, _mockObjectEntry(commerceOrderId));
		_assertPermission(
			ActionKeys.VIEW, _commerceOrderModelResourcePermission,
			_modelResourcePermission, _mockObjectEntry(commerceOrderId));
	}

	private void _assertPermission(
			String actionId,
			ModelResourcePermission<?> modelResourcePermission1,
			ModelResourcePermission<?> modelResourcePermission2,
			ObjectEntry objectEntry)
		throws Exception {

		Mockito.clearInvocations(modelResourcePermission2);

		Mockito.when(
			modelResourcePermission1.contains(
				Mockito.any(PermissionChecker.class), Mockito.any(),
				Mockito.anyString())
		).thenReturn(
			true
		);

		Assert.assertTrue(
			_commerceOrderAttachmentObjectEntryModelResourcePermission.contains(
				_permissionChecker, objectEntry, actionId));

		Mockito.verifyNoInteractions(modelResourcePermission2);
	}

	private ObjectEntry _mockObjectEntry(long commerceOrderId) {
		ObjectEntry objectEntry = Mockito.mock(ObjectEntry.class);

		Mockito.when(
			objectEntry.getValues()
		).thenReturn(
			HashMapBuilder.<String, Serializable>put(
				"r_commerceOrderToCommerceOrderAttachments_commerceOrderId",
				commerceOrderId
			).build()
		);

		return objectEntry;
	}

	@Mock
	private CommerceOrder _commerceOrder;

	private CommerceOrderAttachmentObjectEntryModelResourcePermission
		_commerceOrderAttachmentObjectEntryModelResourcePermission;

	@Mock
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Mock
	private ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;

	@Mock
	private ModelResourcePermission<ObjectEntry> _modelResourcePermission;

	@Mock
	private ObjectEntryLocalService _objectEntryLocalService;

	@Mock
	private PermissionChecker _permissionChecker;

}
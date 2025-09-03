/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.system.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectField;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lazy.referencing.LazyReferencingThreadLocal;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Matyas Wollner
 */
@RunWith(Arquillian.class)
public class AddressSystemObjectDefinitionManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_systemObjectDefinitionManager =
			_systemObjectDefinitionManagerRegistry.
				getSystemObjectDefinitionManager("Address");
	}

	@Test
	public void testGetObjectFields() throws Exception {
		List<ObjectField> objectFields =
			_systemObjectDefinitionManager.getObjectFields();

		Assert.assertEquals(objectFields.toString(), 12, objectFields.size());

		Assert.assertFalse(
			ListUtil.filter(
				objectFields,
				item -> Objects.equals(item.getName(), "addressCountry")
			).isEmpty());
		Assert.assertFalse(
			ListUtil.filter(
				objectFields,
				item -> Objects.equals(item.getName(), "addressLocality")
			).isEmpty());
		Assert.assertFalse(
			ListUtil.filter(
				objectFields,
				item -> Objects.equals(item.getName(), "addressRegion")
			).isEmpty());
		Assert.assertFalse(
			ListUtil.filter(
				objectFields,
				item -> Objects.equals(item.getName(), "addressSubtype")
			).isEmpty());
		Assert.assertFalse(
			ListUtil.filter(
				objectFields,
				item -> Objects.equals(item.getName(), "addressType")
			).isEmpty());
		Assert.assertFalse(
			ListUtil.filter(
				objectFields, item -> Objects.equals(item.getName(), "name")
			).isEmpty());
		Assert.assertFalse(
			ListUtil.filter(
				objectFields,
				item -> Objects.equals(item.getName(), "phoneNumber")
			).isEmpty());
		Assert.assertFalse(
			ListUtil.filter(
				objectFields,
				item -> Objects.equals(item.getName(), "postalCode")
			).isEmpty());
		Assert.assertFalse(
			ListUtil.filter(
				objectFields, item -> Objects.equals(item.getName(), "primary")
			).isEmpty());
		Assert.assertFalse(
			ListUtil.filter(
				objectFields,
				item -> Objects.equals(item.getName(), "streetAddressLine1")
			).isEmpty());
		Assert.assertFalse(
			ListUtil.filter(
				objectFields,
				item -> Objects.equals(item.getName(), "streetAddressLine2")
			).isEmpty());
		Assert.assertFalse(
			ListUtil.filter(
				objectFields,
				item -> Objects.equals(item.getName(), "streetAddressLine3")
			).isEmpty());
	}

	@Test
	@TestInfo("LPD-63933")
	public void testGetOrAddEmptyBaseModel() throws Exception {

		// Lazy referencing disable

		String originalName = PrincipalThreadLocal.getName();
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user1 = TestPropsValues.getUser();

		_setUser(user1);

		String externalReferenceCode = RandomTestUtil.randomString();

		AssertUtils.assertFailure(
			PortalException.class,
			StringBundler.concat(
				"No Address exists with the key {externalReferenceCode=",
				externalReferenceCode, ", companyId=",
				TestPropsValues.getCompanyId(), "}"),
			() -> _systemObjectDefinitionManager.getOrAddEmptyBaseModel(
				externalReferenceCode, user1));

		// Lazy referecing enabled

		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(true)) {

			// With permissions

			Address address =
				(Address)_systemObjectDefinitionManager.getOrAddEmptyBaseModel(
					RandomTestUtil.randomString(), user1);

			Assert.assertEquals(
				WorkflowConstants.STATUS_EMPTY, address.getStatus());

			// Without permissions

			User user2 = UserTestUtil.addUser();

			_setUser(user2);

			AssertUtils.assertFailure(
				PortalException.class,
				StringBundler.concat(
					"User ", user2.getUserId(),
					" must have UPDATE permission for ", User.class.getName(),
					" ", user1.getUserId()),
				() -> _systemObjectDefinitionManager.getOrAddEmptyBaseModel(
					RandomTestUtil.randomString(), user1));

			// Without permissions, existing address

			AssertUtils.assertFailure(
				PortalException.class,
				StringBundler.concat(
					"User ", user2.getUserId(),
					" must have VIEW permission for ", User.class.getName(),
					" ", user1.getUserId()),
				() -> _systemObjectDefinitionManager.getOrAddEmptyBaseModel(
					address.getExternalReferenceCode(), user2));
		}

		PermissionThreadLocal.setPermissionChecker(originalPermissionChecker);

		PrincipalThreadLocal.setName(originalName);
	}

	private void _setUser(User user) throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private SystemObjectDefinitionManager _systemObjectDefinitionManager;

	@Inject
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

}
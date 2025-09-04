/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.system.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectField;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ListUtil;
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
		_addressSystemObjectDefinitionManager =
			_systemObjectDefinitionManagerRegistry.
				getSystemObjectDefinitionManager("Address");
	}

	@Test
	public void testGetObjectFields() throws Exception {
		List<ObjectField> objectFields =
			_addressSystemObjectDefinitionManager.getObjectFields();

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

	private SystemObjectDefinitionManager _addressSystemObjectDefinitionManager;

	@Inject
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

}
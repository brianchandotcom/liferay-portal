/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.jaxrs.container.request.filter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.TreeTestUtil;
import com.liferay.object.tree.Edge;
import com.liferay.object.tree.Node;
import com.liferay.object.tree.Tree;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContextThreadLocal;

import jakarta.ws.rs.container.ContainerRequestFilter;

import java.lang.reflect.Constructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class NestedFieldsContainerRequestFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			NestedFieldsContainerRequestFilterTest.class);

		Bundle objectRestImplBundle = BundleUtil.getBundle(
			bundle.getBundleContext(), "com.liferay.object.rest.impl");

		Assert.assertNotNull(
			"Unable to find object-rest-impl bundle", objectRestImplBundle);

		Class<?> clazz = objectRestImplBundle.loadClass(
			"com.liferay.object.rest.internal.jaxrs.container.request.filter." +
				"NestedFieldsContainerRequestFilter");

		Constructor<ContainerRequestFilter> constructor =
			(Constructor<ContainerRequestFilter>)clazz.getConstructor(
				ObjectDefinitionLocalService.class,
				ObjectRelationshipLocalService.class);

		_containerRequestFilter = constructor.newInstance(
			_objectDefinitionLocalService, _objectRelationshipLocalService);

		NestedFieldsContextThreadLocal.setNestedFieldsContext(null);
	}

	@Test
	public void testFilterWithoutNestedFieldsContext() throws Exception {
		_containerRequestFilter.filter(null);

		Assert.assertNull(
			NestedFieldsContextThreadLocal.getNestedFieldsContext());
	}

	@Test
	public void testFilterWithoutRootModelHierarchyNestedField()
		throws Exception {

		String nestedField1 = RandomTestUtil.randomString();
		String nestedField2 = RandomTestUtil.randomString();
		String nestedField3 = RandomTestUtil.randomString();

		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			new NestedFieldsContext(
				1, Arrays.asList(nestedField1, nestedField2, nestedField3)));

		_containerRequestFilter.filter(null);

		NestedFieldsContext nestedFieldsContext =
			NestedFieldsContextThreadLocal.getNestedFieldsContext();

		Assert.assertEquals(
			SetUtil.fromArray(nestedField1, nestedField2, nestedField3),
			new HashSet<>(nestedFieldsContext.getNestedFields()));
	}

	@FeatureFlag("LPD-34594")
	@Test
	public void testFilterWithRootModelHierarchyNestedField() throws Exception {
		String nestedField1 = RandomTestUtil.randomString();
		String nestedField2 = RandomTestUtil.randomString();
		String nestedField3 = RandomTestUtil.randomString();

		try {
			Tree objectDefinitionTree = TreeTestUtil.createObjectDefinitionTree(
				_objectDefinitionLocalService, _objectRelationshipLocalService,
				true,
				LinkedHashMapBuilder.put(
					"A", new String[] {"AA", "AB"}
				).put(
					"AA", new String[] {"AAA", "AAB"}
				).put(
					"AB", new String[0]
				).put(
					"AAA", new String[0]
				).put(
					"AAB", new String[0]
				).build());

			List<String> nestedFields = Arrays.asList(
				nestedField1, nestedField2, nestedField3, "rootModelHierarchy");

			NestedFieldsContextThreadLocal.setNestedFieldsContext(
				new NestedFieldsContext(1, nestedFields));

			Node objectDefinitionRootNode = objectDefinitionTree.getRootNode();

			ReflectionTestUtil.setFieldValue(
				_containerRequestFilter, "_objectDefinition",
				_objectDefinitionLocalService.getObjectDefinition(
					objectDefinitionRootNode.getPrimaryKey()));

			_containerRequestFilter.filter(null);

			NestedFieldsContext nestedFieldsContext =
				NestedFieldsContextThreadLocal.getNestedFieldsContext();

			Set<String> expectedNestedFields = new HashSet<>(nestedFields);

			expectedNestedFields.addAll(
				_getObjectRelationshipNames(objectDefinitionRootNode));

			Assert.assertEquals(
				expectedNestedFields,
				new HashSet<>(nestedFieldsContext.getNestedFields()));
		}
		finally {
			TreeTestUtil.deleteObjectDefinitionHierarchy(
				_objectDefinitionLocalService,
				new String[] {"C_A", "C_AA", "C_AB", "C_AAAA", "C_AAB"},
				_objectEntryLocalService, _objectRelationshipLocalService);
		}
	}

	@Test
	public void testFilterWithRootModelHierarchyNestedFieldNoHierarchy()
		throws Exception {

		String nestedField1 = RandomTestUtil.randomString();
		String nestedField2 = RandomTestUtil.randomString();
		String nestedField3 = RandomTestUtil.randomString();

		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			new NestedFieldsContext(
				1,
				Arrays.asList(
					nestedField1, nestedField2, nestedField3,
					"rootModelHierarchy")));

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		try {
			ReflectionTestUtil.setFieldValue(
				_containerRequestFilter, "_objectDefinition", objectDefinition);

			_containerRequestFilter.filter(null);

			NestedFieldsContext nestedFieldsContext =
				NestedFieldsContextThreadLocal.getNestedFieldsContext();

			Assert.assertEquals(
				SetUtil.fromArray(
					nestedField1, nestedField2, nestedField3,
					"rootModelHierarchy"),
				new HashSet<>(nestedFieldsContext.getNestedFields()));
		}
		finally {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition.getObjectDefinitionId());
		}
	}

	private Set<String> _getObjectRelationshipNames(Node node)
		throws Exception {

		Set<String> objectRelationshipNames = new HashSet<>();

		Edge edge = node.getEdge();

		if (edge != null) {
			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.getObjectRelationship(
					edge.getObjectRelationshipId());

			objectRelationshipNames.add(objectRelationship.getName());
		}

		for (Node childNode : node.getChildNodes()) {
			objectRelationshipNames.addAll(
				_getObjectRelationshipNames(childNode));
		}

		return objectRelationshipNames;
	}

	private ContainerRequestFilter _containerRequestFilter;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}
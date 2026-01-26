/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Jhosseph Gonzalez
 */
@RunWith(Arquillian.class)
public class ObjectEntryModelDocumentContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testContribute() throws Exception {
		String objectFieldName = "a" + RandomTestUtil.randomString();

		ObjectField objectField = ObjectFieldUtil.createObjectField(
			0, ObjectFieldConstants.BUSINESS_TYPE_TEXT, null,
			ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
			RandomTestUtil.randomString(), objectFieldName, false, true);

		objectField.setLocalized(true);

		ObjectDefinition modifiableSystemObjectDefinition =
			ObjectDefinitionTestUtil.addModifiableSystemObjectDefinition(
				TestPropsValues.getUserId(), null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test" + ObjectDefinitionTestUtil.getRandomName(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE, null, 1,
				Arrays.asList(objectField));

		modifiableSystemObjectDefinition =
			_objectDefinitionLocalService.publishSystemObjectDefinition(
				TestPropsValues.getUserId(),
				modifiableSystemObjectDefinition.getObjectDefinitionId());

		String objectFieldValue = RandomTestUtil.randomString();

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			TestPropsValues.getGroupId(), modifiableSystemObjectDefinition,
			HashMapBuilder.<String, Serializable>put(
				objectFieldName, objectFieldValue
			).put(
				objectFieldName + "_i18n",
				HashMapBuilder.<String, Serializable>put(
					"en_US", objectFieldValue
				).put(
					"pt_BR", objectFieldValue + "pt_BR"
				).build()
			).build());

		Document document = new DocumentImpl();

		ModelDocumentContributor<ObjectEntry>
			objectEntryModelDocumentContributor =
				_getObjectEntryModelDocumentContributor(
					modifiableSystemObjectDefinition);

		objectEntryModelDocumentContributor.contribute(document, objectEntry);

		Field field = document.getField("objectEntryContent");

		String value = field.getValue();

		Assert.assertTrue(
			value,
			value.contains(
				StringBundler.concat(objectFieldName, ": ", objectFieldValue)));
		Assert.assertTrue(
			value,
			value.contains(
				StringBundler.concat(
					objectFieldName, ": ", objectFieldValue, "pt_BR")));
	}

	private ModelDocumentContributor<ObjectEntry>
			_getObjectEntryModelDocumentContributor(
				ObjectDefinition objectDefinition)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			ObjectEntryModelDocumentContributorTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		List<ServiceReference<ModelDocumentContributor<ObjectEntry>>>
			serviceReferences = new ArrayList<>(
				bundleContext.getServiceReferences(
					(Class<ModelDocumentContributor<ObjectEntry>>)
						(Class<?>)ModelDocumentContributor.class,
					"(indexer.class.name=" + objectDefinition.getClassName() +
						")"));

		return bundleContext.getService(serviceReferences.get(0));
	}

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

}
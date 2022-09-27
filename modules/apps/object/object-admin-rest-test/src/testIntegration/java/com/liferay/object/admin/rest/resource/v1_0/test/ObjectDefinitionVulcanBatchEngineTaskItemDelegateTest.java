/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.object.admin.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.dto.v1_0.Status;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Beslic
 */
@RunWith(Arquillian.class)
public class ObjectDefinitionVulcanBatchEngineTaskItemDelegateTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule liferayIntegrationTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_testGroup = GroupTestUtil.addGroup();

		_testCompany = CompanyLocalServiceUtil.getCompany(
			_testGroup.getCompanyId());

		_objectDefinitionResource.setContextCompany(_testCompany);

		_objectDefinitionResource.setContextAcceptLanguage(
			new AcceptLanguage() {

				@Override
				public List<Locale> getLocales() {
					return Arrays.asList(LocaleUtil.getDefault());
				}

				@Override
				public String getPreferredLanguageId() {
					return LocaleUtil.toLanguageId(LocaleUtil.getDefault());
				}

				@Override
				public Locale getPreferredLocale() {
					return LocaleUtil.getDefault();
				}

			});

		PermissionThreadLocal.setPermissionChecker(
			_defaultPermissionCheckerFactory.create(
				UserTestUtil.getAdminUser(_testCompany.getCompanyId())));
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(_testGroup);
	}

	@Test
	public void testCreate() throws Exception {
		ObjectDefinition objectDefinition1 = getTestObjectDefinition(
			"approved");

		objectDefinition1.setStatus(
			new Status() {
				{
					code = Integer.valueOf(WorkflowConstants.STATUS_APPROVED);
					label = "approved";
					label_i18n = "Approved";
				}
			});

		ObjectDefinition objectDefinition2 = getTestObjectDefinition("draft");

		objectDefinition2.setStatus(
			new Status() {
				{
					code = Integer.valueOf(WorkflowConstants.STATUS_DRAFT);
					label = "draft";
					label_i18n = "Draft";
				}
			});

		VulcanBatchEngineTaskItemDelegate<ObjectDefinition>
			vulcanBatchEngineTaskItemDelegate =
				(VulcanBatchEngineTaskItemDelegate<ObjectDefinition>)
					_objectDefinitionResource;

		vulcanBatchEngineTaskItemDelegate.create(
			Arrays.asList(objectDefinition1, objectDefinition2),
			Collections.emptyMap());

		com.liferay.object.model.ObjectDefinition
			serviceBuilderObjectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					_testCompany.getCompanyId(), "C_Oapproved");

		Assert.assertNotNull(serviceBuilderObjectDefinition);
		Assert.assertTrue(serviceBuilderObjectDefinition.getActive());

		serviceBuilderObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				_testCompany.getCompanyId(), "C_Odraft");

		Assert.assertNotNull(serviceBuilderObjectDefinition);
		Assert.assertFalse(serviceBuilderObjectDefinition.getActive());
	}

	protected ObjectDefinition getTestObjectDefinition(String name) {
		String sanitizedName = name.toLowerCase(LocaleUtil.getDefault());

		ObjectDefinition objectDefinition = new ObjectDefinition() {
			{
				accountEntryRestricted = false;
				accountEntryRestrictedObjectFieldId = Long.valueOf(0);
				active = false;
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				enableCategorization = RandomTestUtil.randomBoolean();
				enableComments = RandomTestUtil.randomBoolean();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				label = Collections.singletonMap("en_US", "O" + sanitizedName);
				name = "O" + sanitizedName;
				objectFields = new ObjectField[] {
					new ObjectField() {
						{
							businessType = BusinessType.TEXT;
							DBType = ObjectField.DBType.create("String");
							indexed = false;
							indexedAsKeyword = false;
							label = Collections.singletonMap("en_US", "Column");
							name = "column";
							required = false;
							system = false;
						}
					}
				};

				panelAppOrder = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				panelCategoryKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				parameterRequired = RandomTestUtil.randomBoolean();
				pluralLabel = Collections.singletonMap(
					"en_US", "O" + sanitizedName + "s");
				portlet = RandomTestUtil.randomBoolean();
				restContextPath = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				scope = ObjectDefinitionConstants.SCOPE_COMPANY;
				storageType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				system = RandomTestUtil.randomBoolean();
				titleObjectFieldName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-135430"))) {
			objectDefinition.setStorageType(StringPool.BLANK);
		}

		return objectDefinition;
	}

	@Inject
	private PermissionCheckerFactory _defaultPermissionCheckerFactory;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectDefinitionResource _objectDefinitionResource;

	private Company _testCompany;
	private Group _testGroup;

}
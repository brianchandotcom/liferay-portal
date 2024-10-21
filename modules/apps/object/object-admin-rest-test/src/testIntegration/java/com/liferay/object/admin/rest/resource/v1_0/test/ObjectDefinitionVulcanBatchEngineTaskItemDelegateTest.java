/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.dto.v1_0.Status;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
		_objectDefinitionResource.setContextCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		_objectDefinitionResource.setContextUser(TestPropsValues.getUser());
	}

	@Test
	public void testCreate() throws Exception {
		VulcanBatchEngineTaskItemDelegate<ObjectDefinition>
			vulcanBatchEngineTaskItemDelegate =
				(VulcanBatchEngineTaskItemDelegate<ObjectDefinition>)
					_objectDefinitionResource;

		ObjectDefinition objectDefinition1 = _createObjectDefinition(
			WorkflowConstants.STATUS_APPROVED);
		ObjectDefinition objectDefinition2 = _createObjectDefinition(
			WorkflowConstants.STATUS_DRAFT);

		vulcanBatchEngineTaskItemDelegate.create(
			Arrays.asList(objectDefinition1, objectDefinition2),
			Collections.emptyMap());

		_assertObjectDefinitionActive(true, objectDefinition1);
		_assertObjectDefinitionActive(false, objectDefinition2);
	}

	private void _assertObjectDefinitionActive(
			boolean expectedActive, ObjectDefinition objectDefinition)
		throws Exception {

		com.liferay.object.model.ObjectDefinition
			serviceBuilderObjectDefinition =
				_objectDefinitionLocalService.
					fetchObjectDefinitionByExternalReferenceCode(
						objectDefinition.getExternalReferenceCode(),
						TestPropsValues.getCompanyId());

		Assert.assertEquals(
			expectedActive, serviceBuilderObjectDefinition.isActive());
	}

	private ObjectDefinition _createObjectDefinition(int statusCode) {
		return new ObjectDefinition() {
			{
				active = false;
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				label = Collections.singletonMap(
					"en_US", RandomTestUtil.randomString());
				name = ObjectDefinitionTestUtil.getRandomName();
				objectFields = new ObjectField[] {
					new ObjectField() {
						{
							businessType = BusinessType.TEXT;
							label = Collections.singletonMap(
								"en_US", RandomTestUtil.randomString());
							name = "a" + RandomTestUtil.randomString();
						}
					}
				};
				pluralLabel = Collections.singletonMap(
					"en_US", RandomTestUtil.randomString());
				scope = ObjectDefinitionConstants.SCOPE_COMPANY;
				status = new Status() {
					{
						code = statusCode;
					}
				};
			}
		};
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectDefinitionResource _objectDefinitionResource;

}
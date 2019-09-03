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

package com.liferay.layout.page.template.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.NoSuchClassTypeException;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeField;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateEntryPersistence;
import com.liferay.layout.page.template.service.persistence.impl.constants.LayoutPersistenceConstants;
import com.liferay.layout.page.template.util.DisplayPageTemplateTestUtil;
import com.liferay.portal.kernel.exception.NoSuchClassNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class DisplayPageTemplateServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				LayoutPersistenceConstants.BUNDLE_SYMBOLIC_NAME));

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_className = ClassNameLocalServiceUtil.addClassName(
			TestInfoDisplayContributor.class.getName());
	}

	@After
	public void tearDown() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Test
	public void testAddDisplayPageTemplate() throws PortalException {
		String name = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateEntry displayPageTemplate =
			_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
				_group.getGroupId(), 0, name,
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
				serviceContext);

		LayoutPageTemplateEntry persistedDisplayPageTemplate =
			_layoutPageTemplateEntryPersistence.fetchByPrimaryKey(
				displayPageTemplate.getLayoutPageTemplateEntryId());

		Assert.assertEquals(name, persistedDisplayPageTemplate.getName());
	}

	@Test
	public void testAddDisplayPageWithClassNameIdAndNoClassTypes()
		throws PortalException {

		_registerInfoDisplayContributor(Collections.emptyList());

		LayoutPageTemplateEntry displayPageTemplate = _createDisplayPageEntry(
			_className.getClassNameId(), 0);

		Assert.assertEquals(
			_className.getClassNameId(), displayPageTemplate.getClassNameId());
		Assert.assertEquals(0, displayPageTemplate.getClassTypeId());
		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, displayPageTemplate.getStatus());
	}

	@Test(expected = NoSuchClassNameException.class)
	public void testAddDisplayPageWithInvalidClassNameId()
		throws PortalException {

		_createDisplayPageEntry(0, RandomTestUtil.randomLong());
	}

	@Test
	public void testAddDisplayPageWithValidClassNameIdAndClassType()
		throws PortalException {

		_registerInfoDisplayContributor(
			Collections.singletonList(_CLASS_TYPE_ID));

		LayoutPageTemplateEntry displayPageTemplate = _createDisplayPageEntry(
			_className.getClassNameId(), _CLASS_TYPE_ID);

		Assert.assertEquals(
			_className.getClassNameId(), displayPageTemplate.getClassNameId());
		Assert.assertEquals(
			_CLASS_TYPE_ID, displayPageTemplate.getClassTypeId());
		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, displayPageTemplate.getStatus());
	}

	@Test(expected = NoSuchClassTypeException.class)
	public void testAddDisplayPageWithValidClassNameIdAndInvalidClassType()
		throws PortalException {

		_registerInfoDisplayContributor(
			Collections.singletonList(_CLASS_TYPE_ID));

		_createDisplayPageEntry(_className.getClassNameId(), 0);
	}

	@Test
	public void testDeleteDisplayPageTemplate() throws PortalException {
		LayoutPageTemplateEntry displayPageTemplate =
			DisplayPageTemplateTestUtil.addDisplayPageTemplate(
				_group.getGroupId());

		_layoutPageTemplateEntryService.deleteLayoutPageTemplateEntry(
			displayPageTemplate.getLayoutPageTemplateEntryId());

		Assert.assertNull(
			_layoutPageTemplateEntryPersistence.fetchByPrimaryKey(
				displayPageTemplate.getLayoutPageTemplateEntryId()));
	}

	private LayoutPageTemplateEntry _createDisplayPageEntry(
			long classNameId, long classTypeId)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return _layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
			_group.getGroupId(), 0, RandomTestUtil.randomString(),
			WorkflowConstants.STATUS_DRAFT, classNameId, classTypeId,
			serviceContext);
	}

	private void _registerInfoDisplayContributor(List<Long> classTypeIds) {
		TestInfoDisplayContributor infoDisplayContributor =
			new TestInfoDisplayContributor(classTypeIds);

		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistration = registry.registerService(
			InfoDisplayContributor.class, infoDisplayContributor);
	}

	private static final long _CLASS_TYPE_ID = 99999L;

	@DeleteAfterTestRun
	private ClassName _className;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutPageTemplateEntryPersistence
		_layoutPageTemplateEntryPersistence;

	@Inject
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Inject
	private Portal _portal;

	private ServiceRegistration<InfoDisplayContributor> _serviceRegistration;

	private static class TestInfoDisplayContributor
		implements InfoDisplayContributor<Object> {

		public TestInfoDisplayContributor(List<Long> classTypeIds) {
			_classTypeIds = classTypeIds;
		}

		@Override
		public String getClassName() {
			return TestInfoDisplayContributor.class.getName();
		}

		@Override
		public List<ClassType> getClassTypes(long groupId, Locale locale) {
			Stream<Long> stream = _classTypeIds.stream();

			return stream.map(
				this::_toClassType
			).collect(
				Collectors.toList()
			);
		}

		@Override
		public Set<InfoDisplayField> getInfoDisplayFields(
			long classTypeId, Locale locale) {

			return Collections.emptySet();
		}

		@Override
		public Map<String, Object> getInfoDisplayFieldsValues(
			Object o, Locale locale) {

			return Collections.emptyMap();
		}

		@Override
		public InfoDisplayObjectProvider getInfoDisplayObjectProvider(
			long classPK) {

			return null;
		}

		@Override
		public InfoDisplayObjectProvider<Object> getInfoDisplayObjectProvider(
			long groupId, String urlTitle) {

			return null;
		}

		@Override
		public String getInfoURLSeparator() {
			return RandomTestUtil.randomString();
		}

		private ClassType _toClassType(long classTypeId) {
			return new ClassType() {

				@Override
				public ClassTypeField getClassTypeField(String fieldName) {
					return null;
				}

				@Override
				public List<ClassTypeField> getClassTypeFields() {
					return null;
				}

				@Override
				public List<ClassTypeField> getClassTypeFields(
					int start, int end) {

					return null;
				}

				@Override
				public int getClassTypeFieldsCount() {
					return 0;
				}

				@Override
				public long getClassTypeId() {
					return classTypeId;
				}

				@Override
				public String getName() {
					return null;
				}

			};
		}

		private final List<Long> _classTypeIds;

	}

}
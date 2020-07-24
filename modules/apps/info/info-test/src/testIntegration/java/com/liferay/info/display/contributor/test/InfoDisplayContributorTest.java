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

package com.liferay.info.display.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeField;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Yang Cao
 */
@RunWith(Arquillian.class)
public class InfoDisplayContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_className = _classNameLocalService.addClassName(
			TestInfoDisplayContributor.class.getName());
	}

	@After
	public void tearDown() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Test
	public void testRegisterInfoDisplayContributerAsInfoItemFormProvider()
		throws PortalException {

		_registerInfoDisplayContributor(Collections.emptyList());

		Assert.assertNotNull(_infoItemServiceTracker.getAllInfoItemServices(
			InfoItemFormProvider.class));
	}

	private void _registerInfoDisplayContributor(List<Long> classTypeIds) {
		TestInfoDisplayContributor infoDisplayContributor =
			new TestInfoDisplayContributor(classTypeIds);

		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistration = registry.registerService(
			(Class<InfoDisplayContributor<?>>)
				(Class<?>)InfoDisplayContributor.class,
			infoDisplayContributor);
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	@DeleteAfterTestRun
	private ClassName _className;

	private ServiceRegistration<InfoDisplayContributor<?>> _serviceRegistration;

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

			return null;
		}

		@Override
		public Map<String, Object> getInfoDisplayFieldsValues(
			Object object, Locale locale) {

			return null;
		}

		@Override
		public InfoDisplayObjectProvider<Object> getInfoDisplayObjectProvider(
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

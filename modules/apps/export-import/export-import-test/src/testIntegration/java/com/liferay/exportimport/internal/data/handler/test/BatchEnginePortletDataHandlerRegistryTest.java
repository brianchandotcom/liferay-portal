/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.portlet.data.handler.provider.PortletDataHandlerProvider;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.FeatureFlagTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.portlet.GenericPortlet;
import jakarta.portlet.Portlet;

import jakarta.ws.rs.core.UriInfo;

import java.io.Serializable;

import java.util.Collection;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import org.hamcrest.CoreMatchers;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Alejandro Tardín
 */
@FeatureFlag("LPD-35914")
@RunWith(Arquillian.class)
public class BatchEnginePortletDataHandlerRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws PortalException {
		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), true, "LPD-35914");
	}

	@AfterClass
	public static void tearDownClass() throws PortalException {
		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), false, "LPD-35914");
	}

	@Test
	@TestInfo("LPD-56301")
	public void test() throws Exception {
		String portletId = RandomTestUtil.randomString();

		Assert.assertNull(
			_portletDataHandlerProvider.provide(
				TestPropsValues.getCompanyId(), portletId));

		try (SafeCloseable safeCloseable1 = _registerServiceWithSafeCloseable(
				Portlet.class,
				new GenericPortlet() {
				},
				HashMapDictionaryBuilder.<String, Object>put(
					"com.liferay.portlet.company",
					TestPropsValues.getCompanyId()
				).put(
					"jakarta.portlet.name", portletId
				).build());
			SafeCloseable safeCloseable2 = _registerServiceWithSafeCloseable(
				VulcanBatchEngineTaskItemDelegate.class,
				new TestExportImportVulcanBatchEngineTaskItemDelegate(
					portletId),
				HashMapDictionaryBuilder.put(
					"batch.engine.task.item.delegate", "true"
				).put(
					"batch.engine.task.item.delegate.class.name",
					RandomTestUtil.randomString()
				).put(
					"batch.engine.task.item.delegate.name",
					RandomTestUtil.randomString()
				).build());
			SafeCloseable safeCloseable3 = _registerServiceWithSafeCloseable(
				VulcanBatchEngineTaskItemDelegate.class,
				new TestExportImportVulcanBatchEngineTaskItemDelegate(null),
				HashMapDictionaryBuilder.put(
					"batch.engine.task.item.delegate", "true"
				).put(
					"batch.engine.task.item.delegate.class.name",
					RandomTestUtil.randomString()
				).put(
					"batch.engine.task.item.delegate.name",
					RandomTestUtil.randomString()
				).build())) {

			Thread.sleep(1000);

			Assert.assertThat(
				ClassUtil.getClassName(
					_portletDataHandlerProvider.provide(
						TestPropsValues.getCompanyId(), portletId)),
				CoreMatchers.containsString("BatchEnginePortletDataHandler"));
		}
	}

	private <S> SafeCloseable _registerServiceWithSafeCloseable(
		Class<S> clazz, S service, Dictionary<String, ?> properties) {

		Bundle bundle = FrameworkUtil.getBundle(
			BatchEnginePortletDataHandlerRegistryTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<S> serviceRegistration =
			bundleContext.registerService(clazz, service, properties);

		return serviceRegistration::unregister;
	}

	@Inject
	private PortletDataHandlerProvider _portletDataHandlerProvider;

	private static class TestExportImportVulcanBatchEngineTaskItemDelegate
		implements ExportImportVulcanBatchEngineTaskItemDelegate<Object>,
				   VulcanBatchEngineTaskItemDelegate<Object> {

		public TestExportImportVulcanBatchEngineTaskItemDelegate(
			String portletId) {

			_portletId = portletId;
		}

		@Override
		public void create(
			Collection<Object> items, Map<String, Serializable> parameters) {
		}

		@Override
		public void delete(
			Collection<Object> items, Map<String, Serializable> parameters) {
		}

		@Override
		public EntityModel getEntityModel(
			Map<String, List<String>> multivaluedMap) {

			return null;
		}

		@Override
		public ExportImportDescriptor getExportImportDescriptor() {
			return new ExportImportDescriptor() {

				@Override
				public String getItemClassName() {
					return _itemClassName;
				}

				@Override
				public String getPortletId() {
					return _portletId;
				}

				@Override
				public Scope getScope() {
					return Scope.COMPANY;
				}

			};
		}

		@Override
		public Page<Object> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search) {

			return null;
		}

		@Override
		public void setContextBatchUnsafeBiConsumer(
			UnsafeBiConsumer
				<Collection<Object>, UnsafeFunction<Object, Object, Exception>,
				 Exception> contextBatchUnsafeBiConsumer) {
		}

		@Override
		public void setContextCompany(Company contextCompany) {
		}

		@Override
		public void setContextUriInfo(UriInfo uriInfo) {
		}

		@Override
		public void setContextUser(User contextUser) {
		}

		@Override
		public void setGroupLocalService(GroupLocalService groupLocalService) {
		}

		@Override
		public void setLanguageId(String languageId) {
		}

		@Override
		public void setResourceActionLocalService(
			ResourceActionLocalService resourceActionLocalService) {
		}

		@Override
		public void setResourcePermissionLocalService(
			ResourcePermissionLocalService resourcePermissionLocalService) {
		}

		@Override
		public void setRoleLocalService(RoleLocalService roleLocalService) {
		}

		@Override
		public void update(
			Collection<Object> items, Map<String, Serializable> parameters) {
		}

		private static String _itemClassName = RandomTestUtil.randomString();

		private final String _portletId;

	}

}
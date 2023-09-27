/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.pdf.internal.configuration.admin.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.ManagedServiceFactory;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class PDFPreviewManagedServiceFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetCompanyGroupAndSystemMaxNumberOfPages()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.preview.pdf.internal." +
						"configuration.PDFPreviewConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"maxNumberOfPages", 100
					).build())) {

			_withCompanyConfiguration(
				HashMapDictionaryBuilder.<String, Object>put(
					"maxNumberOfPages", 10
				).build(),
				() -> _withGroupConfiguration(
					HashMapDictionaryBuilder.<String, Object>put(
						"maxNumberOfPages", 5
					).build(),
					() -> {
						Assert.assertEquals(
							10,
							_getMaxNumberOfPages(
								ExtendedObjectClassDefinition.Scope.COMPANY.
									getValue(),
								TestPropsValues.getCompanyId()));
						Assert.assertEquals(
							5,
							_getMaxNumberOfPages(
								ExtendedObjectClassDefinition.Scope.GROUP.
									getValue(),
								TestPropsValues.getGroupId()));
						Assert.assertEquals(
							100,
							_getMaxNumberOfPages(
								ExtendedObjectClassDefinition.Scope.SYSTEM.
									getValue(),
								CompanyConstants.SYSTEM));
					}));
		}
	}

	@Test
	public void testGetCompanyMaxNumberOfPagesSpecificValue() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.preview.pdf.internal." +
						"configuration.PDFPreviewConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"maxNumberOfPages", 100
					).build())) {

			_withCompanyConfiguration(
				HashMapDictionaryBuilder.<String, Object>put(
					"maxNumberOfPages", 10
				).build(),
				() -> Assert.assertEquals(
					10,
					_getMaxNumberOfPages(
						ExtendedObjectClassDefinition.Scope.COMPANY.getValue(),
						TestPropsValues.getCompanyId())));
		}
	}

	@Test
	public void testGetCompanyMaxNumberOfPagesSystemLimited() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.preview.pdf.internal." +
						"configuration.PDFPreviewConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"maxNumberOfPages", 10
					).build())) {

			_withCompanyConfiguration(
				HashMapDictionaryBuilder.<String, Object>put(
					"maxNumberOfPages", 100
				).build(),
				() -> Assert.assertEquals(
					10,
					_getMaxNumberOfPages(
						ExtendedObjectClassDefinition.Scope.COMPANY.getValue(),
						TestPropsValues.getCompanyId())));
		}
	}

	@Test
	public void testGetCompanyMaxNumberOfPagesSystemValue() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.preview.pdf.internal." +
						"configuration.PDFPreviewConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"maxNumberOfPages", 2
					).build())) {

			Assert.assertEquals(
				2,
				_getMaxNumberOfPages(
					ExtendedObjectClassDefinition.Scope.COMPANY.getValue(),
					TestPropsValues.getCompanyId()));
		}
	}

	@Test
	public void testGetGroupMaxNumberOfPagesCompanyLimited() throws Exception {
		_withCompanyConfiguration(
			HashMapDictionaryBuilder.<String, Object>put(
				"maxNumberOfPages", 10
			).build(),
			() -> _withGroupConfiguration(
				HashMapDictionaryBuilder.<String, Object>put(
					"maxNumberOfPages", 100
				).build(),
				() -> Assert.assertEquals(
					10,
					_getMaxNumberOfPages(
						ExtendedObjectClassDefinition.Scope.GROUP.getValue(),
						TestPropsValues.getGroupId()))));
	}

	@Test
	public void testGetGroupMaxNumberOfPagesSystemLimited() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.preview.pdf.internal." +
						"configuration.PDFPreviewConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"maxNumberOfPages", 5
					).build())) {

			_withGroupConfiguration(
				HashMapDictionaryBuilder.<String, Object>put(
					"maxNumberOfPages", 100
				).build(),
				() -> Assert.assertEquals(
					5,
					_getMaxNumberOfPages(
						ExtendedObjectClassDefinition.Scope.GROUP.getValue(),
						TestPropsValues.getGroupId())));
		}
	}

	@Test
	public void testGetGroupMaxNumberOfPagesSystemValue() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.preview.pdf.internal." +
						"configuration.PDFPreviewConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"maxNumberOfPages", 2
					).build())) {

			Assert.assertEquals(
				2,
				_getMaxNumberOfPages(
					ExtendedObjectClassDefinition.Scope.GROUP.getValue(),
					TestPropsValues.getGroupId()));
		}
	}

	@Test
	public void testGetGroupMaxNumberOfPagesWithInvalidGroupId()
		throws Exception {

		Assert.assertEquals(
			0,
			_getMaxNumberOfPages(
				ExtendedObjectClassDefinition.Scope.GROUP.getValue(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID));
	}

	@Test
	public void testGetSystemMaxNumberOfPages() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.preview.pdf.internal." +
						"configuration.PDFPreviewConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"maxNumberOfPages", 10
					).build())) {

			Assert.assertEquals(
				10,
				_getMaxNumberOfPages(
					ExtendedObjectClassDefinition.Scope.SYSTEM.getValue(),
					CompanyConstants.SYSTEM));
		}
	}

	@Test
	public void testGetSystemMaxNumberOfPagesDefaultValue() throws Exception {
		Assert.assertEquals(
			0,
			_getMaxNumberOfPages(
				ExtendedObjectClassDefinition.Scope.SYSTEM.getValue(),
				CompanyConstants.SYSTEM));
	}

	private int _getMaxNumberOfPages(String scope, long scopePK)
		throws Exception {

		return ReflectionTestUtil.invoke(
			ReflectionTestUtil.<Object>getFieldValue(
				_managedServiceFactory, "_pdfPreviewConfigurationHelper"),
			"getMaxNumberOfPages", new Class<?>[] {String.class, long.class},
			scope, scopePK);
	}

	private <E extends Exception> void _withCompanyConfiguration(
			Dictionary<String, Object> properties,
			UnsafeRunnable<E> unsafeRunnable)
		throws Exception {

		String pid =
			"com.liferay.document.library.preview.pdf.internal.configuration." +
				"PDFPreviewConfiguration.scoped~" +
					RandomTestUtil.randomString();

		try {
			properties.put("companyId", TestPropsValues.getCompanyId());

			_managedServiceFactory.updated(pid, properties);

			unsafeRunnable.run();
		}
		finally {
			_managedServiceFactory.deleted(pid);
		}
	}

	private <E extends Exception> void _withGroupConfiguration(
			Dictionary<String, Object> properties,
			UnsafeRunnable<E> unsafeRunnable)
		throws Exception {

		String pid =
			"com.liferay.document.library.preview.pdf.internal.configuration." +
				"PDFPreviewConfiguration.scoped~" +
					RandomTestUtil.randomString();

		try {
			properties.put("groupId", TestPropsValues.getGroupId());

			_managedServiceFactory.updated(pid, properties);

			unsafeRunnable.run();
		}
		finally {
			_managedServiceFactory.deleted(pid);
		}
	}

	@Inject(
		filter = "component.name=com.liferay.document.library.preview.pdf.internal.configuration.admin.service.PDFPreviewManagedServiceFactory"
	)
	private ManagedServiceFactory _managedServiceFactory;

}
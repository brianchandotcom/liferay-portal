/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.configuration.admin.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.configuration.DLFileEntryConfigurationProvider;
import com.liferay.document.library.constants.DLFileEntryConfigurationConstants;
import com.liferay.document.library.exception.DLFileEntryConfigurationException;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
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
 * @author Marco Galluzzi
 */
@RunWith(Arquillian.class)
public class DLFileEntryConfigurationProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetCompanyAndGroupValuesSystemLimited() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_CLASS_NAME, _createDictionary(10, 1000))) {

			_withCompanyConfiguration(
				20, 2000,
				() -> _withGroupConfiguration(
					30, 3000,
					() -> {
						Assert.assertEquals(
							10,
							_dlFileEntryConfigurationProvider.
								getCompanyMaxNumberOfPages(
									TestPropsValues.getCompanyId()));

						Assert.assertEquals(
							1000,
							_dlFileEntryConfigurationProvider.
								getCompanyPreviewableProcessorMaxSize(
									TestPropsValues.getCompanyId()));

						Assert.assertEquals(
							10,
							_dlFileEntryConfigurationProvider.
								getGroupMaxNumberOfPages(
									TestPropsValues.getGroupId()));
						Assert.assertEquals(
							1000,
							_dlFileEntryConfigurationProvider.
								getGroupPreviewableProcessorMaxSize(
									TestPropsValues.getGroupId()));
					}));
		}
	}

	@Test
	public void testGetCompanyGroupAndSystemLimitValues() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_CLASS_NAME, _createDictionary(10, 1000))) {

			_withCompanyConfiguration(
				5, 100,
				() -> _withGroupConfiguration(
					2, 10,
					() -> {
						Assert.assertEquals(
							10,
							_dlFileEntryConfigurationProvider.
								getMaxNumberOfPagesLimit(
									ExtendedObjectClassDefinition.Scope.COMPANY,
									TestPropsValues.getCompanyId()));
						Assert.assertEquals(
							1000,
							_dlFileEntryConfigurationProvider.
								getPreviewableProcessorMaxSizeLimit(
									ExtendedObjectClassDefinition.Scope.COMPANY,
									TestPropsValues.getCompanyId()));

						Assert.assertEquals(
							5,
							_dlFileEntryConfigurationProvider.
								getMaxNumberOfPagesLimit(
									ExtendedObjectClassDefinition.Scope.GROUP,
									TestPropsValues.getGroupId()));
						Assert.assertEquals(
							100,
							_dlFileEntryConfigurationProvider.
								getPreviewableProcessorMaxSizeLimit(
									ExtendedObjectClassDefinition.Scope.GROUP,
									TestPropsValues.getGroupId()));

						Assert.assertEquals(
							DLFileEntryConfigurationConstants.
								UNLIMITED_MAX_NUMBER_OF_PAGES,
							_dlFileEntryConfigurationProvider.
								getMaxNumberOfPagesLimit(
									ExtendedObjectClassDefinition.Scope.SYSTEM,
									0L));
						Assert.assertEquals(
							DLFileEntryConfigurationConstants.
								UNLIMITED_PREVIEWABLE_PROCESSOR_MAX_SIZE,
							_dlFileEntryConfigurationProvider.
								getPreviewableProcessorMaxSizeLimit(
									ExtendedObjectClassDefinition.Scope.SYSTEM,
									0L));
					}));
		}
	}

	@Test
	public void testGetCompanyGroupAndSystemValues() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_CLASS_NAME, _createDictionary(10, 1000))) {

			_withCompanyConfiguration(
				5, 100,
				() -> _withGroupConfiguration(
					2, 10,
					() -> {
						Assert.assertEquals(
							5,
							_dlFileEntryConfigurationProvider.
								getCompanyMaxNumberOfPages(
									TestPropsValues.getCompanyId()));
						Assert.assertEquals(
							100,
							_dlFileEntryConfigurationProvider.
								getCompanyPreviewableProcessorMaxSize(
									TestPropsValues.getCompanyId()));

						Assert.assertEquals(
							2,
							_dlFileEntryConfigurationProvider.
								getGroupMaxNumberOfPages(
									TestPropsValues.getGroupId()));
						Assert.assertEquals(
							10,
							_dlFileEntryConfigurationProvider.
								getGroupPreviewableProcessorMaxSize(
									TestPropsValues.getGroupId()));

						Assert.assertEquals(
							10,
							_dlFileEntryConfigurationProvider.
								getSystemMaxNumberOfPages());
						Assert.assertEquals(
							1000,
							_dlFileEntryConfigurationProvider.
								getSystemPreviewableProcessorMaxSize());
					}));
		}
	}

	@Test
	public void testGetGroupLimitValuesWithInvalidGroupId() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_CLASS_NAME, _createDictionary(10, 1000))) {

			_withCompanyConfiguration(
				5, 500,
				() -> {
					Assert.assertEquals(
						10,
						_dlFileEntryConfigurationProvider.
							getMaxNumberOfPagesLimit(
								ExtendedObjectClassDefinition.Scope.GROUP,
								GroupConstants.DEFAULT_PARENT_GROUP_ID));
					Assert.assertEquals(
						1000,
						_dlFileEntryConfigurationProvider.
							getPreviewableProcessorMaxSizeLimit(
								ExtendedObjectClassDefinition.Scope.GROUP,
								GroupConstants.DEFAULT_PARENT_GROUP_ID));
				});
		}
	}

	@Test
	public void testGetGroupValuesCompanyLimited() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_CLASS_NAME,
					_createDictionary(
						DLFileEntryConfigurationConstants.
							UNLIMITED_MAX_NUMBER_OF_PAGES,
						DLFileEntryConfigurationConstants.
							UNLIMITED_PREVIEWABLE_PROCESSOR_MAX_SIZE))) {

			_withCompanyConfiguration(
				20, 2000,
				() -> _withGroupConfiguration(
					30, 3000,
					() -> {
						Assert.assertEquals(
							20,
							_dlFileEntryConfigurationProvider.
								getGroupMaxNumberOfPages(
									TestPropsValues.getGroupId()));
						Assert.assertEquals(
							2000,
							_dlFileEntryConfigurationProvider.
								getGroupPreviewableProcessorMaxSize(
									TestPropsValues.getGroupId()));
					}));
		}
	}

	@Test
	public void testGetGroupValuesWithInvalidGroupId() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_CLASS_NAME, _createDictionary(10, 1000))) {

			Assert.assertEquals(
				10,
				_dlFileEntryConfigurationProvider.getGroupMaxNumberOfPages(
					GroupConstants.DEFAULT_PARENT_GROUP_ID));
			Assert.assertEquals(
				1000,
				_dlFileEntryConfigurationProvider.
					getGroupPreviewableProcessorMaxSize(
						GroupConstants.DEFAULT_PARENT_GROUP_ID));
		}
	}

	@Test
	public void testGetSystemDefaultValues() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_CLASS_NAME, new HashMapDictionary<>())) {

			Assert.assertEquals(
				DLFileEntryConfigurationConstants.DEFAULT_MAX_NUMBER_OF_PAGES,
				_dlFileEntryConfigurationProvider.getSystemMaxNumberOfPages());
			Assert.assertEquals(
				DLFileEntryConfigurationConstants.
					DEFAULT_PREVIEWABLE_PROCESSOR_MAX_SIZE,
				_dlFileEntryConfigurationProvider.
					getSystemPreviewableProcessorMaxSize());
		}
	}

	@Test(
		expected = DLFileEntryConfigurationException.InvalidMaxNumberOfPagesException.class
	)
	public void testSetCompanyMaxNumberOfPagesGreaterThanSystemValue()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_CLASS_NAME, _createDictionary(10, 1000))) {

			_dlFileEntryConfigurationProvider.update(
				1000, 20, ExtendedObjectClassDefinition.Scope.COMPANY,
				TestPropsValues.getCompanyId());
		}
	}

	@Test(
		expected = DLFileEntryConfigurationException.InvalidPreviewableProcessorMaxSizeException.class
	)
	public void testSetCompanyPreviewableProcessorMaxSizeGreaterThanSystemValue()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_CLASS_NAME, _createDictionary(10, 1000))) {

			_dlFileEntryConfigurationProvider.update(
				2000, 10, ExtendedObjectClassDefinition.Scope.COMPANY,
				TestPropsValues.getCompanyId());
		}
	}

	@Test(
		expected = DLFileEntryConfigurationException.InvalidMaxNumberOfPagesException.class
	)
	public void testSetGroupMaxNumberOfPagesGreaterThanCompanyValue()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_CLASS_NAME, _createDictionary(10, 1000))) {

			_withCompanyConfiguration(
				5, 100,
				() -> _dlFileEntryConfigurationProvider.update(
					100, 8, ExtendedObjectClassDefinition.Scope.GROUP,
					TestPropsValues.getGroupId()));
		}
	}

	@Test(
		expected = DLFileEntryConfigurationException.InvalidMaxNumberOfPagesException.class
	)
	public void testSetGroupMaxNumberOfPagesGreaterThanSystemValue()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_CLASS_NAME, _createDictionary(10, 1000))) {

			_dlFileEntryConfigurationProvider.update(
				1000, 20, ExtendedObjectClassDefinition.Scope.GROUP,
				TestPropsValues.getGroupId());
		}
	}

	@Test(
		expected = DLFileEntryConfigurationException.InvalidPreviewableProcessorMaxSizeException.class
	)
	public void testSetGroupPreviewableProcessorMaxSizeGreaterThanCompanyValue()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_CLASS_NAME, _createDictionary(10, 1000))) {

			_withCompanyConfiguration(
				5, 100,
				() -> _dlFileEntryConfigurationProvider.update(
					200, 5, ExtendedObjectClassDefinition.Scope.GROUP,
					TestPropsValues.getGroupId()));
		}
	}

	@Test(
		expected = DLFileEntryConfigurationException.InvalidPreviewableProcessorMaxSizeException.class
	)
	public void testSetGroupPreviewableProcessorMaxSizeGreaterThanSystemValue()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_CLASS_NAME, _createDictionary(10, 1000))) {

			_dlFileEntryConfigurationProvider.update(
				2000, 10, ExtendedObjectClassDefinition.Scope.GROUP,
				TestPropsValues.getGroupId());
		}
	}

	private HashMapDictionary<String, Object> _createDictionary(
		int maxNumberOfPages, long previewableProcessorMaxSize) {

		return HashMapDictionaryBuilder.<String, Object>put(
			"maxNumberOfPages", maxNumberOfPages
		).put(
			"previewableProcessorMaxSize", previewableProcessorMaxSize
		).build();
	}

	private <E extends Exception> void _withCompanyConfiguration(
			int maxNumberOfPages, long previewableProcessorMaxSize,
			UnsafeRunnable<E> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> properties = _createDictionary(
			maxNumberOfPages, previewableProcessorMaxSize);

		properties.put("companyId", TestPropsValues.getCompanyId());

		_withConfiguration(properties, unsafeRunnable);
	}

	private <E extends Exception> void _withConfiguration(
			Dictionary<String, Object> properties,
			UnsafeRunnable<E> unsafeRunnable)
		throws Exception {

		String pid =
			_CONFIGURATION_CLASS_NAME + ".scoped~" +
				RandomTestUtil.randomString();

		try {
			_managedServiceFactory.updated(pid, properties);

			unsafeRunnable.run();
		}
		finally {
			_managedServiceFactory.deleted(pid);
		}
	}

	private <E extends Exception> void _withGroupConfiguration(
			int maxNumberOfPages, long previewableProcessorMaxSize,
			UnsafeRunnable<E> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> properties = _createDictionary(
			maxNumberOfPages, previewableProcessorMaxSize);

		properties.put("groupId", TestPropsValues.getGroupId());

		_withConfiguration(properties, unsafeRunnable);
	}

	private static final String _CONFIGURATION_CLASS_NAME =
		"com.liferay.document.library.configuration.DLFileEntryConfiguration";

	@Inject
	private DLFileEntryConfigurationProvider _dlFileEntryConfigurationProvider;

	@Inject(
		filter = "component.name=com.liferay.document.library.internal.configuration.admin.service.DLFileEntryManagedServiceFactory"
	)
	private ManagedServiceFactory _managedServiceFactory;

}
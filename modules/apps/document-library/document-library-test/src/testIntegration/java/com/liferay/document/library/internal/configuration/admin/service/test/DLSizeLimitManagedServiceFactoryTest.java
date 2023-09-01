/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.configuration.admin.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
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
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class DLSizeLimitManagedServiceFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetCompanyMimeTypeSizeLimitSpecificValue()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.internal.configuration." +
						"DLSizeLimitConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"mimeTypeSizeLimit", new String[] {"image/png:1234"}
					).build())) {

			Assert.assertEquals(
				1234, _getCompanyMimeTypeSizeLimit("image/png"));
		}
	}

	@Test
	public void testGetCompanyMimeTypeSizeLimitSystemDefaultValue()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.internal.configuration." +
						"DLSizeLimitConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"mimeTypeSizeLimit", new String[0]
					).build())) {

			Assert.assertEquals(0, _getCompanyMimeTypeSizeLimit("image/png"));
		}
	}

	@Test
	public void testGetCompanyMimeTypeSizeLimitWildcard() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.internal.configuration." +
						"DLSizeLimitConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"mimeTypeSizeLimit", new String[] {"image/*:1234"}
					).build())) {

			Assert.assertEquals(
				1234, _getCompanyMimeTypeSizeLimit("image/png"));
		}
	}

	@Test
	public void testGetCompanyMimeTypeSizeLimitWildcardAndSpecificValue()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.internal.configuration." +
						"DLSizeLimitConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"mimeTypeSizeLimit",
						new String[] {"image/*:1234", "image/png:5678"}
					).build())) {

			Assert.assertEquals(
				1234, _getCompanyMimeTypeSizeLimit("image/jpg"));
			Assert.assertEquals(
				5678, _getCompanyMimeTypeSizeLimit("image/png"));
		}
	}

	@Test
	public void testGetGroupMimeTypeSizeLimitSystemDefaultValue()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.internal.configuration." +
						"DLSizeLimitConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"mimeTypeSizeLimit", new String[0]
					).build())) {

			Assert.assertEquals(0, _getGroupMimeTypeSizeLimit("image/png"));
		}
	}

	@Test
	public void testGetGroupMimeTypeSizeLimitSystemSpecificValue()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.internal.configuration." +
						"DLSizeLimitConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"mimeTypeSizeLimit", new String[] {"image/png:1234"}
					).build())) {

			Assert.assertEquals(1234, _getGroupMimeTypeSizeLimit("image/png"));
		}
	}

	@Test
	public void testGetGroupMimeTypeSizeLimitWildcard() throws Exception {
		_withGroupConfiguration(
			HashMapDictionaryBuilder.<String, Object>put(
				"mimeTypeSizeLimit", new String[] {"image/*:1234"}
			).build(),
			() -> Assert.assertEquals(
				1234, _getGroupMimeTypeSizeLimit("image/png")));
	}

	@Test
	public void testGetGroupMimeTypeSizeLimitWildcardAndSystemSpecificValue()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.internal.configuration." +
						"DLSizeLimitConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"mimeTypeSizeLimit",
						new String[] {"image/*:1234", "image/png:5678"}
					).build())) {

			Assert.assertEquals(5678, _getGroupMimeTypeSizeLimit("image/png"));
		}
	}

	@Test
	public void testGetGroupMimeTypeSizeLimitWithInvalidGroupId()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.internal.configuration." +
						"DLSizeLimitConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"mimeTypeSizeLimit",
						new String[] {"image/*:1234", "image/png:5678"}
					).build())) {

			Assert.assertEquals(
				5678,
				_getGroupMimeTypeSizeLimit(
					GroupConstants.DEFAULT_PARENT_GROUP_ID, "image/png"));
		}
	}

	private long _getCompanyMimeTypeSizeLimit(String mimeType)
		throws Exception {

		return ReflectionTestUtil.invoke(
			_getDLSizeLimitConfigurationHelper(), "getCompanyMimeTypeSizeLimit",
			new Class<?>[] {long.class, String.class},
			TestPropsValues.getCompanyId(), mimeType);
	}

	private Object _getDLSizeLimitConfigurationHelper() {
		return ReflectionTestUtil.getFieldValue(
			_managedServiceFactory, "_dlSizeLimitConfigurationHelper");
	}

	private long _getGroupMimeTypeSizeLimit(long groupId, String mimeType)
		throws Exception {

		return ReflectionTestUtil.invoke(
			_getDLSizeLimitConfigurationHelper(), "getGroupMimeTypeSizeLimit",
			new Class<?>[] {long.class, String.class}, groupId, mimeType);
	}

	private long _getGroupMimeTypeSizeLimit(String mimeType) throws Exception {
		return _getGroupMimeTypeSizeLimit(
			TestPropsValues.getGroupId(), mimeType);
	}

	private <E extends Exception> void _withGroupConfiguration(
			Dictionary<String, Object> properties,
			UnsafeRunnable<E> unsafeRunnable)
		throws Exception {

		String pid =
			"com.liferay.document.library.internal.configuration." +
				"DLSizeLimitConfiguration.scoped~" +
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
		filter = "component.name=com.liferay.document.library.internal.configuration.admin.service.DLSizeLimitManagedServiceFactory"
	)
	private ManagedServiceFactory _managedServiceFactory;

}
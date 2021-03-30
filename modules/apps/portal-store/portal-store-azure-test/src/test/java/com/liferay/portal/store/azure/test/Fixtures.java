/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
 * <p>
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */
package com.liferay.portal.store.azure.test;

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.store.azure.AzureBlobStorageStore;
import com.liferay.portal.store.azure.internal.FullPathsMapper;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portlet.documentlibrary.util.DLImpl;
import org.apache.commons.lang.reflect.FieldUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Josef Sustacek
 */
public class Fixtures {

	// properties from gradle.properties (or their overrides) as set to ENV, see build.gradle -> test
	public static final String GRADLE_TO_UNIT_TESTS_ENV_VARS_PREFIX_AzureBlobStorageStore = "tests.AzureBlobStorageStore.";

	/**
	 * Returns a brand new store to be used in tests, with references resolved +
	 * configuration properties based on test settings (can be used to activate the store,
	 * eventually modify + activate).
	 *
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Fixture<AzureBlobStorageStore> getAzureBlobStorageStore()
		throws IllegalAccessException {

		// init our service under test + its dependencies set manually (OSGi will be used in container)
		AzureBlobStorageStore sut = new AzureBlobStorageStore();

		// simulate @Reference on the field
		FieldUtils.getDeclaredField(
				AzureBlobStorageStore.class, "_liferayToAzurePathsMapper", true)
			.set(sut, new FullPathsMapper());

		Map<String, Object> sutProps =
			getConfigurationProperties(
				GRADLE_TO_UNIT_TESTS_ENV_VARS_PREFIX_AzureBlobStorageStore);

		return new Fixture<>(sut, sutProps);
	}

	private static Map<String, Object> getConfigurationProperties(String prefix) {

		Map<String, Object> sutProps = System.getenv().entrySet().stream()
			.filter(e -> e.getKey().startsWith(prefix))
			.collect(Collectors.toMap(
				k -> k.getKey().substring(prefix.length()),
				Map.Entry::getValue
			));

		// auto-generate the container name, if it should differ per-run and is not provided via gradle.properties
		sutProps.putIfAbsent("containerName", "unit-tests-" + System.currentTimeMillis());

		System.out.println(
			"Unit test props (" + prefix + "* only) from gradle.properties: ");

		sutProps.forEach((k, v) -> {
			// mask SAS token or AccountKey in the connection String, if any
			Object maskedV = Validator.isNull(v)
				? v :
				v.toString()
					.replaceAll("sig=[^&;]*", "sig=***MASKED***")
					.replaceAll("AccountKey=[^&;]*", "AccountKey=***MASKED***");
			System.out.println("  " + k + ": " + maskedV);
		});

		return sutProps;
	}

	public static class Fixture<T> {

		private final T _sut;
		private final Map<String, Object> _sutProps;

		public Fixture(
			T sut, Map<String, Object> sutProps) {
			_sut = Objects.requireNonNull(sut);
			_sutProps = Collections.unmodifiableMap(Objects.requireNonNull(sutProps));
		}

		/**
		 * ~ Service Under Test
		 */
		public T getSut() {
			return _sut;
		}

		/**
		 * ~ Service Under Test Properties
		 * @return
		 */
		public Map<String, Object> getSutProps() {
			return _sutProps;
		}
	}

	static {
		// TODO How to get logging properly set up during unit tests?
		
		// override the default Jdk14LogFactoryImpl
//		LogFactoryUtil.setLogFactory(new Log4jLogFactoryImpl());
		// simulate Log4jExtenderBundleActivator
//		Log4JLoggerTestUtil.configureLog4JLogger("com.liferay.store.azure", Log4JLoggerTestUtil.DEBUG);
//		Log4JLoggerTestUtil.configureLog4JLogger("com.azure", Log4JLoggerTestUtil.DEBUG);

		// need to mock before any test - used internally by the store
		PropsUtil.setProps(new PropsImpl());
		new FileUtil().setFile(new FileImpl());
		new FastDateFormatFactoryUtil().setFastDateFormatFactory(new FastDateFormatFactoryImpl());
		new DLUtil().setDL(new DLImpl());
	}

}

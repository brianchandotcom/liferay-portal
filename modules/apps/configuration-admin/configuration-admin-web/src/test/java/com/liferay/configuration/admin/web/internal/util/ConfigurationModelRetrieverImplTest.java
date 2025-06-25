/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Raymond Augé
 */
public class ConfigurationModelRetrieverImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_configurationModelRetrieverImpl =
			new ConfigurationModelRetrieverImpl();
	}

	@Test
	public void testPidFilterCompany() throws Exception {
		String pid = "foo";

		String pidProperty = ConfigurationAdmin.SERVICE_FACTORYPID;

		String pidFilterString =
			_configurationModelRetrieverImpl.getPidFilterString(
				pid, ExtendedObjectClassDefinition.Scope.COMPANY);

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).build()
		).doesNotMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).put(
				"companyId", "any"
			).build()
		).doesMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid + ".scoped"
			).put(
				"companyId", "any"
			).build()
		).doesMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).put(
				"groupId", "any"
			).build()
		).doesNotMatch();
	}

	@Test
	public void testPidFilterGroup() throws Exception {
		String pid = "foo";

		String pidProperty = ConfigurationAdmin.SERVICE_FACTORYPID;

		String pidFilterString =
			_configurationModelRetrieverImpl.getPidFilterString(
				pid, ExtendedObjectClassDefinition.Scope.GROUP);

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).build()
		).doesNotMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).put(
				"companyId", "any"
			).build()
		).doesNotMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).put(
				"groupId", "any"
			).build()
		).doesMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid + ".scoped"
			).put(
				"groupId", "any"
			).build()
		).doesMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).put(
				"portletInstanceId", "any"
			).build()
		).doesNotMatch();
	}

	@Test
	public void testPidFilterPortletInstance() throws Exception {
		String pid = "foo";

		String pidProperty = ConfigurationAdmin.SERVICE_FACTORYPID;

		String pidFilterString =
			_configurationModelRetrieverImpl.getPidFilterString(
				pid, ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE);

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).build()
		).doesNotMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).put(
				"companyId", "any"
			).build()
		).doesNotMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).put(
				"groupId", "any"
			).build()
		).doesNotMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).put(
				"portletInstanceId", "any"
			).build()
		).doesNotMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).put(
				"groupId", "any"
			).put(
				"portletInstanceId", "any"
			).build()
		).doesMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid + ".scoped"
			).put(
				"groupId", "any"
			).put(
				"portletInstanceId", "any"
			).build()
		).doesMatch();
	}

	@Test
	public void testPidFilterSystem() throws Exception {
		String pid = "foo";

		String pidProperty = Constants.SERVICE_PID;

		String pidFilterString =
			_configurationModelRetrieverImpl.getPidFilterString(
				pid, ExtendedObjectClassDefinition.Scope.SYSTEM);

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).build()
		).doesMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid + ".scoped"
			).build()
		).doesNotMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).put(
				"companyId", "any"
			).build()
		).doesNotMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).put(
				"groupId", "any"
			).build()
		).doesNotMatch();
	}

	@Test
	public void testPidFilterSystemFactory() throws Exception {
		String pid = "foo~1234";

		String pidProperty = ConfigurationAdmin.SERVICE_FACTORYPID;

		String pidFilterString =
			_configurationModelRetrieverImpl.getPidFilterString(
				pid, ExtendedObjectClassDefinition.Scope.SYSTEM);

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).build()
		).doesMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid + ".scoped"
			).build()
		).doesNotMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).put(
				"companyId", "any"
			).build()
		).doesNotMatch();

		_filterAsserter(
			pidFilterString,
			HashMapBuilder.put(
				pidProperty, pid
			).put(
				"groupId", "any"
			).build()
		).doesNotMatch();
	}

	public class FilterAsserter {

		public FilterAsserter(Filter filter, Map<String, String> payload) {
			_filter = filter;
			_payload = payload;
		}

		public void doesMatch() throws Exception {
			if (!_filter.matches(_payload)) {
				throw new AssertionFailedError(
					StringBundler.concat(
						_filter, " does not match ", _payload));
			}
		}

		public void doesNotMatch() throws Exception {
			if (_filter.matches(_payload)) {
				throw new AssertionFailedError(
					StringBundler.concat(_filter, " matches ", _payload));
			}
		}

		private final Filter _filter;
		private final Map<String, String> _payload;

	}

	private FilterAsserter _filterAsserter(
			String pidFilterString, Map<String, String> payload)
		throws Exception {

		Filter filter = FrameworkUtil.createFilter(pidFilterString);

		return new FilterAsserter(filter, payload);
	}

	private ConfigurationModelRetrieverImpl _configurationModelRetrieverImpl;

}
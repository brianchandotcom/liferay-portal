/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.cluster.internal.messaging;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.configuration.persistence.ReloadablePersistenceManager;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Dictionary;
import java.util.EnumSet;
import java.util.Set;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationEvent;

/**
 * @author Raymond Augé
 */
public class ConfigurationMessageListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		_configurationMessageListener = new ConfigurationMessageListener();

		ReflectionTestUtil.setFieldValue(
			_configurationMessageListener, "_configurationAdmin",
			_configurationAdmin);
		ReflectionTestUtil.setFieldValue(
			_configurationMessageListener, "_reloadablePersistenceManager",
			_reloadablePersistenceManager);
	}

	@Test
	public void testReadOnlyConfigurationDelete() throws Exception {
		_invokeConfigurationListener(
			EnumSet.of(Configuration.ConfigurationAttribute.READ_ONLY),
			ConfigurationEvent.CM_DELETED, new HashMapDictionary<>(),
			configuration -> {
				Mockito.verify(
					configuration, Mockito.times(1)
				).delete();

				Mockito.verify(
					configuration, Mockito.times(0)
				).addAttributes(
					Configuration.ConfigurationAttribute.READ_ONLY
				);
			});
	}

	@Test
	public void testReadOnlyConfigurationUpdate() throws Exception {
		_invokeConfigurationListener(
			EnumSet.of(Configuration.ConfigurationAttribute.READ_ONLY),
			ConfigurationEvent.CM_UPDATED,
			HashMapDictionaryBuilder.<String, Object>put(
				"foo", "bar"
			).build(),
			configuration -> {
				Mockito.verify(
					configuration, Mockito.times(1)
				).update(
					Mockito.any()
				);

				Mockito.verify(
					configuration, Mockito.times(1)
				).addAttributes(
					Configuration.ConfigurationAttribute.READ_ONLY
				);
			});
	}

	@Test
	public void testReadOnlyConfigurationUpdateEmpty() throws Exception {
		_invokeConfigurationListener(
			EnumSet.of(Configuration.ConfigurationAttribute.READ_ONLY),
			ConfigurationEvent.CM_UPDATED, null,
			configuration -> {
				Mockito.verify(
					configuration, Mockito.times(1)
				).update();

				Mockito.verify(
					configuration, Mockito.times(1)
				).addAttributes(
					Configuration.ConfigurationAttribute.READ_ONLY
				);
			});
	}

	@Test
	public void testReadWriteConfigurationDelete() throws Exception {
		_invokeConfigurationListener(
			Collections.emptySet(), ConfigurationEvent.CM_DELETED, null,
			configuration -> {
				Mockito.verify(
					configuration, Mockito.times(1)
				).delete();

				Mockito.verify(
					configuration, Mockito.times(0)
				).addAttributes(
					Mockito.any()
				);
			});
	}

	@Test
	public void testReadWriteConfigurationUpdate() throws Exception {
		_invokeConfigurationListener(
			Collections.emptySet(), ConfigurationEvent.CM_UPDATED,
			HashMapDictionaryBuilder.<String, Object>put(
				"foo", "bar"
			).build(),
			configuration -> {
				Mockito.verify(
					configuration, Mockito.times(1)
				).update(
					Mockito.any()
				);

				Mockito.verify(
					configuration, Mockito.times(0)
				).addAttributes(
					Mockito.any()
				);
			});
	}

	@Test
	public void testReadWriteConfigurationUpdateEmpty() throws Exception {
		_invokeConfigurationListener(
			Collections.emptySet(), ConfigurationEvent.CM_UPDATED, null,
			configuration -> {
				Mockito.verify(
					configuration, Mockito.times(1)
				).update();

				Mockito.verify(
					configuration, Mockito.times(0)
				).addAttributes(
					Mockito.any()
				);
			});
	}

	private void _invokeConfigurationListener(
			Set<Configuration.ConfigurationAttribute> attributes,
			int configuratonEventType, Dictionary<String, Object> properties,
			UnsafeConsumer<Configuration, Exception> unsafeConsumer)
		throws Exception {

		Mockito.when(
			_reloadablePersistenceManager.load(Mockito.anyString())
		).thenReturn(
			properties
		);

		Configuration configuration = Mockito.mock(Configuration.class);

		Mockito.when(
			configuration.getAttributes()
		).thenReturn(
			attributes
		);

		Mockito.when(
			_configurationAdmin.listConfigurations(Mockito.anyString())
		).thenReturn(
			new Configuration[] {configuration}
		);

		Message message = new Message();

		message.put(Constants.SERVICE_PID, "test");
		message.put("configuration.event.type", configuratonEventType);

		_configurationMessageListener.doReceive(message);

		unsafeConsumer.accept(configuration);
	}

	@Mock
	private ConfigurationAdmin _configurationAdmin;

	private ConfigurationMessageListener _configurationMessageListener;

	@Mock
	private ReloadablePersistenceManager _reloadablePersistenceManager;

}
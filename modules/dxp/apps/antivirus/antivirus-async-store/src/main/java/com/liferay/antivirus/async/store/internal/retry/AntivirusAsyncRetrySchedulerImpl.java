/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.antivirus.async.store.internal.retry;

import com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration;
import com.liferay.antivirus.async.store.retry.AntivirusAsyncRetryScheduler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.TransientValue;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = AntivirusAsyncRetryScheduler.class
)
public class AntivirusAsyncRetrySchedulerImpl
	implements AntivirusAsyncRetryScheduler {

	@Override
	public void schedule(Message message) {
		Map<String, Object> map = message.getValues();

		Set<Map.Entry<String, Object>> entrySet = map.entrySet();

		Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();

			if (entry.getValue() instanceof TransientValue) {
				iterator.remove();
			}
		}

		_componentInstances.computeIfAbsent(
			message.getString("jobName"),
			key -> _componentFactory.newInstance(
				HashMapDictionaryBuilder.<String, Object>put(
					"jobName", key
				).put(
					"message", message
				).put(
					"retryCronExpression",
					_antivirusAsyncConfiguration.retryCronExpression()
				).build()));
	}

	@Override
	public void unschedule(Message message) {
		ComponentInstance<?> componentInstance = _componentInstances.remove(
			message.getString("jobName"));

		if (componentInstance != null) {
			componentInstance.dispose();
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_antivirusAsyncConfiguration = ConfigurableUtil.createConfigurable(
			AntivirusAsyncConfiguration.class, properties);
	}

	@Deactivate
	protected void deactivate() {
		for (ComponentInstance<?> componentInstance :
				_componentInstances.values()) {

			componentInstance.dispose();
		}
	}

	private AntivirusAsyncConfiguration _antivirusAsyncConfiguration;

	@Reference(
		target = "(component.factory=com.liferay.antivirus.async.store.internal.scheduler.AntivirusAsyncFileSchedulerJobConfiguration)"
	)
	private ComponentFactory<?> _componentFactory;

	private final Map<String, ComponentInstance<?>> _componentInstances =
		new ConcurrentHashMap<>();

}
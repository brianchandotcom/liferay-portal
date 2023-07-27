/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.util;

import com.liferay.document.library.kernel.util.DLProcessor;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rafael Praxedes
 */
@Component(service = {})
public class DLProcessorRegister {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		_bundleContext = bundleContext;

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		for (String dlProcessorClassName : _DL_FILE_ENTRY_PROCESSORS) {
			DLProcessor dlProcessor = (DLProcessor)InstanceFactory.newInstance(
				classLoader, dlProcessorClassName);

			dlProcessor.afterPropertiesSet();

			_register(dlProcessor);

			_dlProcessors.add(dlProcessor);
		}
	}

	@Deactivate
	protected void deactivate() throws Exception {
		UnsafeConsumer.accept(
			_dlProcessors,
			dlProcessor -> {
				_unregister(dlProcessor);

				dlProcessor.destroy();
			},
			Exception.class);

		_dlProcessors.clear();
	}

	private void _register(DLProcessor dlProcessor) {
		Class<?>[] classes = ReflectionUtil.getInterfaces(dlProcessor);

		String[] classNames = new String[classes.length];

		for (int i = 0; i < classes.length; i++) {
			classNames[i] = classes[i].getName();
		}

		ServiceRegistration<?> serviceRegistration =
			_bundleContext.registerService(
				classNames, dlProcessor,
				MapUtil.singletonDictionary("type", dlProcessor.getType()));

		ServiceRegistration<?> previousServiceRegistration =
			_serviceRegistrations.put(dlProcessor, serviceRegistration);

		if (previousServiceRegistration != null) {
			previousServiceRegistration.unregister();
		}
	}

	private void _unregister(DLProcessor dlProcessor) {
		ServiceRegistration<?> serviceRegistration =
			_serviceRegistrations.remove(dlProcessor);

		serviceRegistration.unregister();
	}

	private static final String[] _DL_FILE_ENTRY_PROCESSORS =
		PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PROCESSORS);

	private BundleContext _bundleContext;
	private final List<DLProcessor> _dlProcessors = new ArrayList<>(
		_DL_FILE_ENTRY_PROCESSORS.length);
	private final Map<DLProcessor, ServiceRegistration<?>>
		_serviceRegistrations = new ConcurrentHashMap<>();

}
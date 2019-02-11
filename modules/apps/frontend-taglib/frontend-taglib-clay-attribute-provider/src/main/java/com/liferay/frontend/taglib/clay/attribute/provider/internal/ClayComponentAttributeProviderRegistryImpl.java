package com.liferay.frontend.taglib.clay.attribute.provider.internal;

import com.liferay.frontend.taglib.clay.attribute.provider.ClayComponentAttributeProvider;
import com.liferay.frontend.taglib.clay.attribute.provider.ClayComponentAttributeProviderRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	immediate = true,
	service = ClayComponentAttributeProviderRegistry.class
)
public class ClayComponentAttributeProviderRegistryImpl
	implements ClayComponentAttributeProviderRegistry {

	@Override
	public ClayComponentAttributeProvider get(String key) {
		ServiceWrapper<ClayComponentAttributeProvider> service =
			_serviceTrackerMap.getService(key);

		if (service == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No ClayComponentAttributeProvider registered with key " +
						key);
			}

			return null;
		}

		return service.getService();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ClayComponentAttributeProvider.class,
			"clay.component.attribute.provider.key",
			ServiceTrackerCustomizerFactory.
				<ClayComponentAttributeProvider>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClayComponentAttributeProviderRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<ClayComponentAttributeProvider>>
		_serviceTrackerMap;
}
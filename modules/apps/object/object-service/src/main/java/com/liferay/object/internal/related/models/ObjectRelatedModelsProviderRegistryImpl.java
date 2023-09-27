/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.related.models;

import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(service = ObjectRelatedModelsProviderRegistry.class)
public class ObjectRelatedModelsProviderRegistryImpl
	implements ObjectRelatedModelsProviderRegistry {

	@Override
	public ObjectRelatedModelsProvider getObjectRelatedModelsProvider(
			String className, long companyId, String type)
		throws PortalException {

		String key = _getKey(className, companyId, type);

		ObjectRelatedModelsProvider objectRelatedModelsProvider =
			_serviceTrackerMap.getService(key);

		if (objectRelatedModelsProvider == null) {
			throw new IllegalArgumentException(
				"No object related models provider found with key " + key);
		}

		return objectRelatedModelsProvider;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectRelatedModelsProvider.class, null,
			(serviceReference, emitter) -> {
				ObjectRelatedModelsProvider objectRelatedModelsProvider =
					bundleContext.getService(serviceReference);

				emitter.emit(
					_getKey(
						objectRelatedModelsProvider.getClassName(),
						objectRelatedModelsProvider.getCompanyId(),
						objectRelatedModelsProvider.
							getObjectRelationshipType()));
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private String _getKey(String className, long companyId, String type) {
		return StringBundler.concat(
			className, StringPool.POUND, companyId, StringPool.POUND, type);
	}

	private ServiceTrackerMap<String, ObjectRelatedModelsProvider>
		_serviceTrackerMap;

}
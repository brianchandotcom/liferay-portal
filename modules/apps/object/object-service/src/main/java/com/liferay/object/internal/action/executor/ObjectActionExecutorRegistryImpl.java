/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.action.executor;

import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.action.executor.ObjectActionExecutorRegistry;
import com.liferay.object.scope.CompanyScoped;
import com.liferay.object.scope.ObjectDefinitionScoped;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(service = ObjectActionExecutorRegistry.class)
public class ObjectActionExecutorRegistryImpl
	implements ObjectActionExecutorRegistry {

	@Override
	public ObjectActionExecutor getObjectActionExecutor(
		long companyId, String objectActionExecutorKey) {

		ObjectActionExecutor objectActionExecutor =
			_serviceTrackerMap.getService(objectActionExecutorKey);

		if (objectActionExecutor == null) {
			objectActionExecutor = _serviceTrackerMap.getService(
				_getCompanyScopedKey(objectActionExecutorKey, companyId));
		}

		if (objectActionExecutor == null) {
			throw new IllegalArgumentException(
				"No object action executor found with key " +
					objectActionExecutorKey);
		}

		return objectActionExecutor;
	}

	@Override
	public List<ObjectActionExecutor> getObjectActionExecutors(
		long companyId, String objectDefinitionName) {

		Collection<ObjectActionExecutor> objectActionExecutorsCollection =
			_serviceTrackerMap.values();

		if (objectActionExecutorsCollection == null) {
			return Collections.<ObjectActionExecutor>emptyList();
		}

		return ListUtil.sort(
			ListUtil.filter(
				ListUtil.fromCollection(objectActionExecutorsCollection),
				objectActionExecutor -> {
					boolean companyAllowed = true;

					if (objectActionExecutor instanceof CompanyScoped) {
						CompanyScoped objectActionExecutorCompanyScoped =
							(CompanyScoped)objectActionExecutor;

						companyAllowed =
							objectActionExecutorCompanyScoped.isAllowedCompany(
								companyId);
					}

					boolean objectDefinitionAllowed = true;

					if (objectActionExecutor instanceof
							ObjectDefinitionScoped) {

						ObjectDefinitionScoped
							objectActionExecutorObjectDefinitionScoped =
								(ObjectDefinitionScoped)objectActionExecutor;

						objectDefinitionAllowed =
							objectActionExecutorObjectDefinitionScoped.
								isAllowedObjectDefinition(objectDefinitionName);
					}

					return companyAllowed && objectDefinitionAllowed;
				}),
			(ObjectActionExecutor objectActionExecutor1,
			 ObjectActionExecutor objectActionExecutor2) -> {

				String key1 = objectActionExecutor1.getKey();
				String key2 = objectActionExecutor2.getKey();

				return key1.compareTo(key2);
			});
	}

	@Override
	public boolean hasObjectActionExecutor(String objectActionExecutorKey) {
		return _serviceTrackerMap.containsKey(objectActionExecutorKey);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectActionExecutor.class, null,
			(serviceReference, emitter) -> {
				ObjectActionExecutor objectActionExecutor =
					bundleContext.getService(serviceReference);

				String key = objectActionExecutor.getKey();

				if (objectActionExecutor instanceof CompanyScoped) {
					CompanyScoped objectActionExecutorCompanyScoped =
						(CompanyScoped)objectActionExecutor;

					key = _getCompanyScopedKey(
						key,
						objectActionExecutorCompanyScoped.
							getAllowedCompanyId());
				}

				emitter.emit(key);
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private String _getCompanyScopedKey(String key, long company) {
		return StringBundler.concat(key, StringPool.POUND, company);
	}

	private ServiceTrackerMap<String, ObjectActionExecutor> _serviceTrackerMap;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.validation.rule;

import com.liferay.object.scope.CompanyScoped;
import com.liferay.object.scope.ObjectDefinitionScoped;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.object.validation.rule.ObjectValidationRuleEngineRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collection;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(service = ObjectValidationRuleEngineRegistry.class)
public class ObjectValidationRuleEngineRegistryImpl
	implements ObjectValidationRuleEngineRegistry {

	@Override
	public ObjectValidationRuleEngine getObjectValidationRuleEngine(
		String name) {

		return _serviceTrackerMap.getService(name);
	}

	@Override
	public List<ObjectValidationRuleEngine> getObjectValidationRuleEngines(
		long companyId, String objectDefinitionName) {

		Collection<ObjectValidationRuleEngine>
			objectValidationRuleEnginesCollection = _serviceTrackerMap.values();

		return ListUtil.sort(
			ListUtil.filter(
				ListUtil.fromCollection(objectValidationRuleEnginesCollection),
				objectValidationRuleEngine -> {
					boolean allowed = true;

					if (objectValidationRuleEngine instanceof CompanyScoped) {
						CompanyScoped objectValidationRuleEngineCompanyScoped =
							(CompanyScoped)objectValidationRuleEngine;

						allowed =
							objectValidationRuleEngineCompanyScoped.
								isAllowedCompany(companyId);
					}

					if (objectValidationRuleEngine instanceof
							ObjectDefinitionScoped) {

						ObjectDefinitionScoped
							objectValidationRuleEngineObjectDefinitionScoped =
								(ObjectDefinitionScoped)
									objectValidationRuleEngine;

						allowed =
							objectValidationRuleEngineObjectDefinitionScoped.
								isAllowedObjectDefinition(objectDefinitionName);
					}

					return allowed;
				}),
			(ObjectValidationRuleEngine objectValidationRuleEngine1,
			 ObjectValidationRuleEngine objectValidationRuleEngine2) -> {

				String name1 = objectValidationRuleEngine1.getName();
				String name2 = objectValidationRuleEngine2.getName();

				return name1.compareTo(name2);
			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectValidationRuleEngine.class, null,
			new ServiceReferenceMapper<String, ObjectValidationRuleEngine>() {

				@Override
				public void map(
					ServiceReference<ObjectValidationRuleEngine>
						serviceReference,
					Emitter<String> emitter) {

					ObjectValidationRuleEngine objectValidationRuleEngine =
						bundleContext.getService(serviceReference);

					emitter.emit(objectValidationRuleEngine.getName());
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ObjectValidationRuleEngine>
		_serviceTrackerMap;

}
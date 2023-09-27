/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.validation.rule;

import com.liferay.object.exception.ObjectValidationRuleEngineException;
import com.liferay.object.scope.CompanyScoped;
import com.liferay.object.scope.ObjectDefinitionScoped;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.object.validation.rule.ObjectValidationRuleEngineRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collection;
import java.util.List;

import org.osgi.framework.BundleContext;
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
			long companyId, String key)
		throws PortalException {

		ObjectValidationRuleEngine objectValidationRuleEngine =
			_serviceTrackerMap.getService(key);

		if (objectValidationRuleEngine == null) {
			objectValidationRuleEngine = _serviceTrackerMap.getService(
				_getCompanyScopedKey(companyId, key));
		}

		if (objectValidationRuleEngine == null) {
			throw new ObjectValidationRuleEngineException.NoSuchEngine(key);
		}

		return objectValidationRuleEngine;
	}

	@Override
	public List<ObjectValidationRuleEngine> getObjectValidationRuleEngines(
		long companyId, String objectDefinitionName) {

		Collection<ObjectValidationRuleEngine>
			objectValidationRuleEnginesCollection = _serviceTrackerMap.values();

		return ListUtil.filter(
			ListUtil.fromCollection(objectValidationRuleEnginesCollection),
			objectValidationRuleEngine -> {
				boolean companyAllowed = true;

				if (objectValidationRuleEngine instanceof CompanyScoped) {
					CompanyScoped objectValidationRuleEngineCompanyScoped =
						(CompanyScoped)objectValidationRuleEngine;

					companyAllowed =
						objectValidationRuleEngineCompanyScoped.
							isAllowedCompany(companyId);
				}

				boolean objectDefinitionAllowed = true;

				if (objectValidationRuleEngine instanceof
						ObjectDefinitionScoped) {

					ObjectDefinitionScoped
						objectValidationRuleEngineObjectDefinitionScoped =
							(ObjectDefinitionScoped)objectValidationRuleEngine;

					objectDefinitionAllowed =
						objectValidationRuleEngineObjectDefinitionScoped.
							isAllowedObjectDefinition(objectDefinitionName);
				}

				return companyAllowed && objectDefinitionAllowed;
			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectValidationRuleEngine.class, null,
			(serviceReference, emitter) -> {
				ObjectValidationRuleEngine objectValidationRuleEngine =
					bundleContext.getService(serviceReference);

				String key = objectValidationRuleEngine.getKey();

				if (objectValidationRuleEngine instanceof CompanyScoped) {
					CompanyScoped objectValidationRuleEngineCompanyScoped =
						(CompanyScoped)objectValidationRuleEngine;

					key = _getCompanyScopedKey(
						objectValidationRuleEngineCompanyScoped.
							getAllowedCompanyId(),
						key);
				}

				if (!(objectValidationRuleEngine instanceof
						FunctionObjectValidationRuleEngineImpl) ||
					FeatureFlagManagerUtil.isEnabled("LPS-188898")) {

					emitter.emit(key);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private String _getCompanyScopedKey(long company, String key) {
		return StringBundler.concat(key, StringPool.POUND, company);
	}

	private ServiceTrackerMap<String, ObjectValidationRuleEngine>
		_serviceTrackerMap;

}
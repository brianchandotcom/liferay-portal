/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.internal.translator;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.translation.translator.Translator;
import com.liferay.translation.translator.TranslatorRegistry;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alicia Garcia
 */
@Component(service = TranslatorRegistry.class)
public class TranslatorRegistryImpl implements TranslatorRegistry {

	@Override
	public Translator getCompanyTranslator(long companyId)
		throws ConfigurationException {

		for (Translator translator : _serviceTrackerList) {
			if (translator.isEnabled(companyId)) {
				return translator;
			}
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, Translator.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private ServiceTrackerList<Translator> _serviceTrackerList;

}
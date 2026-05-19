/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.sharing;

import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juanjo Fernandez
 */
@Component(service = ObjectDefinitionDeployer.class)
public class DataSetSnapshotObjectDefinitionDeployer
	implements ObjectDefinitionDeployer {

	@Override
	public List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition) {

		if (!Objects.equals(
				objectDefinition.getExternalReferenceCode(),
				_DATA_SET_SNAPSHOT_EXTERNAL_REFERENCE_CODE)) {

			return Collections.emptyList();
		}

		return Collections.singletonList(
			_bundleContext.registerService(
				SharingEntryInterpreter.class,
				new DataSetSnapshotSharingEntryInterpreter(
					_companyLocalService, _language, _objectEntryLocalService),
				HashMapDictionaryBuilder.<String, Object>put(
					"model.class.name", objectDefinition.getClassName()
				).build()));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private static final String _DATA_SET_SNAPSHOT_EXTERNAL_REFERENCE_CODE =
		"L_DATA_SET_SNAPSHOT";

	private BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Language _language;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}
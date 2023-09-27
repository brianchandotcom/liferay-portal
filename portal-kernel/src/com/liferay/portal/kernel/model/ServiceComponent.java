/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ServiceComponent service. Represents a row in the &quot;ServiceComponent&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceComponentModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.ServiceComponentImpl")
@ProviderType
public interface ServiceComponent
	extends PersistedModel, ServiceComponentModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.ServiceComponentImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ServiceComponent, Long>
		SERVICE_COMPONENT_ID_ACCESSOR = new Accessor<ServiceComponent, Long>() {

			@Override
			public Long get(ServiceComponent serviceComponent) {
				return serviceComponent.getServiceComponentId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ServiceComponent> getTypeClass() {
				return ServiceComponent.class;
			}

		};

	public String getIndexesSQL();

	public String getSequencesSQL();

	public String getTablesSQL();

}
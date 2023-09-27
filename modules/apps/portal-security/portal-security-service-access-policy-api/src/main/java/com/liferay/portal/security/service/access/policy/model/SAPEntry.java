/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SAPEntry service. Represents a row in the &quot;SAPEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.security.service.access.policy.model.impl.SAPEntryImpl"
)
@ProviderType
public interface SAPEntry extends PersistedModel, SAPEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.security.service.access.policy.model.impl.SAPEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SAPEntry, Long> SAP_ENTRY_ID_ACCESSOR =
		new Accessor<SAPEntry, Long>() {

			@Override
			public Long get(SAPEntry sapEntry) {
				return sapEntry.getSapEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SAPEntry> getTypeClass() {
				return SAPEntry.class;
			}

		};

	public java.util.List<String> getAllowedServiceSignaturesList();

	public boolean isSystem()
		throws com.liferay.portal.kernel.module.configuration.
			ConfigurationException;

}
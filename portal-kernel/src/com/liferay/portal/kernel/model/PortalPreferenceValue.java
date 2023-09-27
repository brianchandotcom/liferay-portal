/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the PortalPreferenceValue service. Represents a row in the &quot;PortalPreferenceValue&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see PortalPreferenceValueModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.model.impl.PortalPreferenceValueImpl"
)
@ProviderType
public interface PortalPreferenceValue
	extends PersistedModel, PortalPreferenceValueModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.PortalPreferenceValueImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<PortalPreferenceValue, Long>
		PORTAL_PREFERENCE_VALUE_ID_ACCESSOR =
			new Accessor<PortalPreferenceValue, Long>() {

				@Override
				public Long get(PortalPreferenceValue portalPreferenceValue) {
					return portalPreferenceValue.getPortalPreferenceValueId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<PortalPreferenceValue> getTypeClass() {
					return PortalPreferenceValue.class;
				}

			};

	public String getValue();

	public void setValue(String value);

}
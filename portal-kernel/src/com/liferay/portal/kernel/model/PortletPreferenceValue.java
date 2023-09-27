/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the PortletPreferenceValue service. Represents a row in the &quot;PortletPreferenceValue&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferenceValueModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.model.impl.PortletPreferenceValueImpl"
)
@ProviderType
public interface PortletPreferenceValue
	extends PersistedModel, PortletPreferenceValueModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.PortletPreferenceValueImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<PortletPreferenceValue, Long>
		PORTLET_PREFERENCE_VALUE_ID_ACCESSOR =
			new Accessor<PortletPreferenceValue, Long>() {

				@Override
				public Long get(PortletPreferenceValue portletPreferenceValue) {
					return portletPreferenceValue.getPortletPreferenceValueId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<PortletPreferenceValue> getTypeClass() {
					return PortletPreferenceValue.class;
				}

			};

	public String getValue();

	public void setValue(String value);

}
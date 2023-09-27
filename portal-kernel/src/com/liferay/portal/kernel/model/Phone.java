/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the Phone service. Represents a row in the &quot;Phone&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see PhoneModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.PhoneImpl")
@ProviderType
public interface Phone extends PersistedModel, PhoneModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.PhoneImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Phone, Long> PHONE_ID_ACCESSOR =
		new Accessor<Phone, Long>() {

			@Override
			public Long get(Phone phone) {
				return phone.getPhoneId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Phone> getTypeClass() {
				return Phone.class;
			}

		};

	public ListType getListType()
		throws com.liferay.portal.kernel.exception.PortalException;

}
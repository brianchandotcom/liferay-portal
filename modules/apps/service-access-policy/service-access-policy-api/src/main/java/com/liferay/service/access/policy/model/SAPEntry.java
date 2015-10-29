/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.service.access.policy.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the SAPEntry service. Represents a row in the &quot;SAPEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryModel
 * @see com.liferay.service.access.policy.model.impl.SAPEntryImpl
 * @see com.liferay.service.access.policy.model.impl.SAPEntryModelImpl
 * @generated
 */
@ProviderType
public interface SAPEntry extends SAPEntryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.service.access.policy.model.impl.SAPEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SAPEntry, Long> SAP_ENTRY_ID_ACCESSOR = new Accessor<SAPEntry, Long>() {
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

	public java.util.List<java.lang.String> getAllowedServiceSignaturesList();

	public boolean isSystem()
		throws com.liferay.portal.kernel.module.configuration.ConfigurationException;
}
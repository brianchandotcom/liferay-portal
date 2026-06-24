/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ReassociateEntry service. Represents a row in the &quot;ReassociateEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ReassociateEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.model.impl.ReassociateEntryImpl"
)
@ProviderType
public interface ReassociateEntry extends ReassociateEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.model.impl.ReassociateEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ReassociateEntry, Long>
		REASSOCIATE_ENTRY_ID_ACCESSOR = new Accessor<ReassociateEntry, Long>() {

			@Override
			public Long get(ReassociateEntry reassociateEntry) {
				return reassociateEntry.getReassociateEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ReassociateEntry> getTypeClass() {
				return ReassociateEntry.class;
			}

		};

}
// LIFERAY-SERVICE-BUILDER-HASH:895221612
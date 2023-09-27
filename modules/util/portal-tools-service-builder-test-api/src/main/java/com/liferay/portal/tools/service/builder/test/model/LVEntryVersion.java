/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the LVEntryVersion service. Represents a row in the &quot;LVEntryVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryVersionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.model.impl.LVEntryVersionImpl"
)
@ProviderType
public interface LVEntryVersion extends LVEntryVersionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.model.impl.LVEntryVersionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LVEntryVersion, Long>
		LV_ENTRY_VERSION_ID_ACCESSOR = new Accessor<LVEntryVersion, Long>() {

			@Override
			public Long get(LVEntryVersion lvEntryVersion) {
				return lvEntryVersion.getLvEntryVersionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<LVEntryVersion> getTypeClass() {
				return LVEntryVersion.class;
			}

		};

}
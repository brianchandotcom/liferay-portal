/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the RedirectEntry service. Represents a row in the &quot;RedirectEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see RedirectEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.redirect.model.impl.RedirectEntryImpl")
@ProviderType
public interface RedirectEntry extends PersistedModel, RedirectEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.redirect.model.impl.RedirectEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<RedirectEntry, Long>
		REDIRECT_ENTRY_ID_ACCESSOR = new Accessor<RedirectEntry, Long>() {

			@Override
			public Long get(RedirectEntry redirectEntry) {
				return redirectEntry.getRedirectEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<RedirectEntry> getTypeClass() {
				return RedirectEntry.class;
			}

		};

}
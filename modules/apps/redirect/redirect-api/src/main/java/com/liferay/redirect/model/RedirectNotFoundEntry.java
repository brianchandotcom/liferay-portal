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
 * The extended model interface for the RedirectNotFoundEntry service. Represents a row in the &quot;RedirectNotFoundEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see RedirectNotFoundEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.redirect.model.impl.RedirectNotFoundEntryImpl"
)
@ProviderType
public interface RedirectNotFoundEntry
	extends PersistedModel, RedirectNotFoundEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.redirect.model.impl.RedirectNotFoundEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<RedirectNotFoundEntry, Long>
		REDIRECT_NOT_FOUND_ENTRY_ID_ACCESSOR =
			new Accessor<RedirectNotFoundEntry, Long>() {

				@Override
				public Long get(RedirectNotFoundEntry redirectNotFoundEntry) {
					return redirectNotFoundEntry.getRedirectNotFoundEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<RedirectNotFoundEntry> getTypeClass() {
					return RedirectNotFoundEntry.class;
				}

			};

	public long getHits();

	public long getRequestCount();

}
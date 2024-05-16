/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the RememberMeToken service. Represents a row in the &quot;RememberMeToken&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see RememberMeTokenModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.RememberMeTokenImpl")
@ProviderType
public interface RememberMeToken extends PersistedModel, RememberMeTokenModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.RememberMeTokenImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<RememberMeToken, Long>
		REMEMBER_ME_TOKEN_ID_ACCESSOR = new Accessor<RememberMeToken, Long>() {

			@Override
			public Long get(RememberMeToken rememberMeToken) {
				return rememberMeToken.getRememberMeTokenId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<RememberMeToken> getTypeClass() {
				return RememberMeToken.class;
			}

		};

	public boolean isExpired();

}
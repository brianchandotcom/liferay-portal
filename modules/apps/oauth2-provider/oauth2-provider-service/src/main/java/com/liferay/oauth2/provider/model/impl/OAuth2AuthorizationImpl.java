/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.model.impl;

/**
 * The extended model implementation for the OAuth2Authorization service.
 * Represents a row in the &quot;OAuth2Authorization&quot; database table, with
 * each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class.
 * Whenever methods are added, rerun ServiceBuilder to copy their definitions
 * into the {@link com.liferay.oauth2.provider.model.OAuth2Authorization}
 * interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class OAuth2AuthorizationImpl extends OAuth2AuthorizationBaseImpl {

	@Override
	public void setAccessTokenContent(String accessTokenContent) {
		super.setAccessTokenContent(accessTokenContent);

		if (accessTokenContent != null) {
			setAccessTokenContentHash(accessTokenContent.hashCode());
		}
		else {
			setAccessTokenContentHash(0);
		}
	}

	@Override
	public void setRefreshTokenContent(String refreshTokenContent) {
		super.setRefreshTokenContent(refreshTokenContent);

		if (refreshTokenContent != null) {
			setRefreshTokenContentHash(refreshTokenContent.hashCode());
		}
		else {
			setRefreshTokenContentHash(0);
		}
	}

}
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

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a o auth2
	 * authorization model instance should use the {@link
	 * com.liferay.oauth2.provider.model.OAuth2Authorization} interface instead.
	 */
	public OAuth2AuthorizationImpl() {
	}

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
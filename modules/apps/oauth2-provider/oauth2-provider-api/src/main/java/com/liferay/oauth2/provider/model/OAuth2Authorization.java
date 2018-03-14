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

package com.liferay.oauth2.provider.model;

import java.util.Date;
import java.util.List;

/**
 * @author Tomas Polesovsky
 */
public interface OAuth2Authorization extends OAuth2AuthorizationModel {

	public Date getAccessTokenExpirationDate();

	public long getCompanyId();

	public Date getCreateDate();

	public long getOAuth2AccessTokenId();

	public long getOAuth2ApplicationId();

	public long getOAuth2RefreshTokenId();

	public Date getRefreshTokenExpirationDate();

	public String getRemoteIPInfo();

	public String getScopeAliases();

	public List<String> getScopeAliasesList();

	public long getUserId();

	public String getUserName();

	public void setAccessTokenExpirationDate(Date accessTokenExpirationDate);

	public void setCompanyId(long companyId);

	public void setCreateDate(Date createDate);

	public void setOAuth2AccessTokenId(long oAuth2AccessTokenId);

	public void setOAuth2ApplicationId(long oAuth2ApplicationId);

	public void setOAuth2RefreshTokenId(long oAuth2RefreshTokenId);

	public void setRefreshTokenExpirationDate(Date refreshTokenExpirationDate);

	public void setRemoteIPInfo(String remoteIPInfo);

	public void setScopes(String scopes);

	public void setScopesList(List<String> scopesList);

	public void setUserId(long userId);

	public void setUserName(String userName);

}
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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.model.OAuth2AuthorizationModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Tomas Polesovsky
 */
public class OAuth2AuthorizationModelImpl
	implements BaseModel<OAuth2Authorization>, OAuth2AuthorizationModel {

	@Override
	public Object clone() {
		return null;
	}

	@Override
	public int compareTo(OAuth2Authorization o) {
		return 0;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return null;
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		return null;
	}

	@Override
	public Class<?> getModelClass() {
		return null;
	}

	@Override
	public String getModelClassName() {
		return null;
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return null;
	}

	@Override
	public boolean isCachedModel() {
		return false;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return false;
	}

	@Override
	public boolean isEscapedModel() {
		return false;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return false;
	}

	@Override
	public boolean isNew() {
		return false;
	}

	@Override
	public void resetOriginalValues() {
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
	}

	@Override
	public void setNew(boolean n) {
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
	}

	@Override
	public CacheModel<OAuth2Authorization> toCacheModel() {
		return null;
	}

	@Override
	public OAuth2Authorization toEscapedModel() {
		final OAuth2Authorization parent = (OAuth2Authorization)this;

		OAuth2Authorization escapedModel = new OAuth2AuthorizationImpl() {

			@Override
			public Date getAccessTokenExpirationDate() {
				return parent.getAccessTokenExpirationDate();
			}

			@Override
			public long getCompanyId() {
				return parent.getCompanyId();
			}

			@Override
			public Date getCreateDate() {
				return parent.getCreateDate();
			}

			@Override
			public long getOAuth2ApplicationId() {
				return parent.getOAuth2ApplicationId();
			}

			@Override
			public long getOAuth2RefreshTokenId() {
				return parent.getOAuth2RefreshTokenId();
			}

			@Override
			public long getOAuth2TokenId() {
				return parent.getOAuth2TokenId();
			}

			@Override
			public Date getRefreshTokenExpirationDate() {
				return parent.getRefreshTokenExpirationDate();
			}

			@Override
			public String getRemoteIPInfo() {
				return HtmlUtil.escape(parent.getRemoteIPInfo());
			}

			@Override
			public String getScopes() {
				return HtmlUtil.escape(parent.getScopes());
			}

			@Override
			public List<String> getScopesList() {
				return parent.getScopesList();
			}

			@Override
			public long getUserId() {
				return parent.getUserId();
			}

			@Override
			public String getUserName() {
				return HtmlUtil.escape(parent.getUserName());
			}

		};

		return escapedModel;
	}

	@Override
	public OAuth2Authorization toUnescapedModel() {
		return (OAuth2Authorization)this;
	}

	@Override
	public String toXmlString() {
		return toString();
	}

}
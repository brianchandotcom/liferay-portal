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

package com.liferay.oauth2.provider.service.persistence.impl;

import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.service.persistence.OAuth2ScopeGrantPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OAuth2ScopeGrantFinderBaseImpl extends BasePersistenceImpl<OAuth2ScopeGrant> {
	public OAuth2ScopeGrantFinderBaseImpl() {
		setModelClass(OAuth2ScopeGrant.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("oAuth2ApplicationScopeAliasesId",
				"oA2AScopeAliasesId");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getOAuth2ScopeGrantPersistence().getBadColumnNames();
	}

	/**
	 * Returns the o auth2 scope grant persistence.
	 *
	 * @return the o auth2 scope grant persistence
	 */
	public OAuth2ScopeGrantPersistence getOAuth2ScopeGrantPersistence() {
		return oAuth2ScopeGrantPersistence;
	}

	/**
	 * Sets the o auth2 scope grant persistence.
	 *
	 * @param oAuth2ScopeGrantPersistence the o auth2 scope grant persistence
	 */
	public void setOAuth2ScopeGrantPersistence(
		OAuth2ScopeGrantPersistence oAuth2ScopeGrantPersistence) {
		this.oAuth2ScopeGrantPersistence = oAuth2ScopeGrantPersistence;
	}

	@BeanReference(type = OAuth2ScopeGrantPersistence.class)
	protected OAuth2ScopeGrantPersistence oAuth2ScopeGrantPersistence;
	private static final Log _log = LogFactoryUtil.getLog(OAuth2ScopeGrantFinderBaseImpl.class);
}
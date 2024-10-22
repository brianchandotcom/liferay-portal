/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.model.listener;

import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.security.sso.openid.connect.internal.AuthorizationServerMetadataResolver;

import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

import org.osgi.service.component.annotations.Component;

/**
 * @author Istvan Sajtos
 */
@Component(service = ModelListener.class)
public class OAuthClientEntryModelListener
	extends BaseModelListener<OAuthClientEntry> {

	public void onAfterRemove(OAuthClientEntry oAuthClientEntry)
		throws ModelListenerException {

		_oidcProviderMetadataPortalCache.remove(oAuthClientEntry.getClientId());
	}

	public void onAfterUpdate(
			OAuthClientEntry originalOAuthClientEntry,
			OAuthClientEntry oAuthClientEntry)
		throws ModelListenerException {

		_oidcProviderMetadataPortalCache.remove(
			originalOAuthClientEntry.getClientId());
	}

	private final PortalCache<String, OIDCProviderMetadata>
		_oidcProviderMetadataPortalCache = PortalCacheHelperUtil.getPortalCache(
			PortalCacheManagerNames.SINGLE_VM,
			AuthorizationServerMetadataResolver.class.getName());

}
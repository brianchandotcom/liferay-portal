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

package com.liferay.oauth2.provider.jsonws.internal.security.auth.verifier;

import com.liferay.oauth2.provider.constants.OAuth2ProviderConstants;
import com.liferay.oauth2.provider.jsonws.internal.service.access.policy.scope.SAPEntryScope;
import com.liferay.oauth2.provider.jsonws.internal.service.access.policy.scope.SAPEntryScopeDescriptorFinderRegistrator;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.rest.spi.bearer.token.provider.BearerTokenProvider;
import com.liferay.oauth2.provider.rest.spi.bearer.token.provider.BearerTokenProviderAccessor;
import com.liferay.oauth2.provider.scope.liferay.OAuth2ProviderScopeLiferayConstants;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.service.OAuth2ApplicationScopeAliasesLocalService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.oauth2.provider.service.OAuth2ScopeGrantLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.security.service.access.policy.ServiceAccessPolicy;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Tomas Polesovsky
 */
@Component(
	property = "auth.verifier.OAuth2JSONWSAuthVerifier.urls.includes=/api/jsonws/*",
	service = AuthVerifier.class
)
public class OAuth2JSONWSAuthVerifier implements AuthVerifier {

	@Override
	public String getAuthType() {
		return OAuth2ProviderScopeLiferayConstants.AUTH_VERIFIER_OAUTH2_TYPE;
	}

	@Override
	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties properties)
		throws AuthException {

		AuthVerifierResult authVerifierResult = new AuthVerifierResult();

		OAuth2Authorization oAuth2Authorization = getOAuth2Authorization(
			accessControlContext);

		try {
			BearerTokenProvider.AccessToken accessToken = getAccessToken(
				oAuth2Authorization);

			if (accessToken == null) {
				return authVerifierResult;
			}

			OAuth2Application oAuth2Application =
				accessToken.getOAuth2Application();

			long companyId = oAuth2Application.getCompanyId();

			BearerTokenProvider bearerTokenProvider =
				_bearerTokenProviderAccessor.getBearerTokenProvider(
					companyId, oAuth2Application.getClientId());

			if (bearerTokenProvider == null) {
				return authVerifierResult;
			}

			if (!bearerTokenProvider.isValid(accessToken)) {
				return authVerifierResult;
			}

			List<OAuth2ScopeGrant> oAuth2AuthorizationOAuth2ScopeGrants =
				_oAuth2ScopeGrantLocalService.
					getOAuth2AuthorizationOAuth2ScopeGrants(
						oAuth2Authorization.getOAuth2AuthorizationId());

			Stream<OAuth2ScopeGrant> stream =
				oAuth2AuthorizationOAuth2ScopeGrants.stream();

			List<String> scopes = stream.filter(
				oAuth2ScopeGrant -> _jaxRsApplicationNames.contains(
					oAuth2ScopeGrant.getApplicationName())
			).map(
				OAuth2ScopeGrant::getScope
			).collect(
				Collectors.toList()
			);

			List<SAPEntryScope> sapEntryScopes =
				_sapEntryScopeDescriptorFinderRegistrator.
					getRegisteredSAPEntryScopes(companyId);

			List<String> serviceAccessPolicyNames = new ArrayList<>(
				sapEntryScopes.size());

			for (SAPEntryScope sapEntryScope : sapEntryScopes) {
				if (scopes.contains(sapEntryScope.getScope())) {
					serviceAccessPolicyNames.add(
						sapEntryScope.getSAPEntryName());
				}
			}

			Map<String, Object> settings = authVerifierResult.getSettings();

			settings.put(
				BearerTokenProvider.AccessToken.class.getName(), accessToken);
			settings.put(
				ServiceAccessPolicy.SERVICE_ACCESS_POLICY_NAMES,
				serviceAccessPolicyNames);

			authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
			authVerifierResult.setUserId(accessToken.getUserId());

			return authVerifierResult;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to verify OAuth2 access token", e);
			}

			return authVerifierResult;
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(&(osgi.jaxrs.name=*)(sap.scope.finder=true))"
	)
	protected void addJaxRsApplicationName(
		ServiceReference<ScopeFinder> serviceReference) {

		_jaxRsApplicationNames.add(
			GetterUtil.getString(
				serviceReference.getProperty("osgi.jaxrs.name")));
	}

	protected BearerTokenProvider.AccessToken getAccessToken(
			OAuth2Authorization oAuth2Authorization)
		throws PortalException {

		if (oAuth2Authorization == null) {
			return null;
		}

		String accessTokenContent = oAuth2Authorization.getAccessTokenContent();

		if (OAuth2ProviderConstants.EXPIRED_TOKEN.equals(accessTokenContent)) {
			return null;
		}

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.getOAuth2Application(
				oAuth2Authorization.getOAuth2ApplicationId());

		Date createDate = oAuth2Authorization.getAccessTokenCreateDate();
		Date expirationDate =
			oAuth2Authorization.getAccessTokenExpirationDate();

		long expiresIn =
			(expirationDate.getTime() - createDate.getTime()) / 1000;

		long issuedAt = createDate.getTime() / 1000;

		List<String> scopeAliasesList = Collections.emptyList();

		long oAuth2ApplicationScopeAliasesId =
			oAuth2Authorization.getOAuth2ApplicationScopeAliasesId();

		if (oAuth2ApplicationScopeAliasesId > 0) {
			scopeAliasesList =
				_oAuth2ApplicationScopeAliasesLocalService.getScopeAliasesList(
					oAuth2ApplicationScopeAliasesId);
		}

		return new BearerTokenProvider.AccessToken(
			oAuth2Application, new ArrayList<>(), StringPool.BLANK, expiresIn,
			new HashMap<>(), StringPool.BLANK, StringPool.BLANK, issuedAt,
			StringPool.BLANK, StringPool.BLANK, new HashMap<>(),
			StringPool.BLANK, StringPool.BLANK, scopeAliasesList,
			accessTokenContent, _TOKEN_KEY, oAuth2Authorization.getUserId(),
			oAuth2Authorization.getUserName());
	}

	protected OAuth2Authorization getOAuth2Authorization(
		AccessControlContext accessControlContext) {

		HttpServletRequest httpServletRequest =
			accessControlContext.getRequest();

		String authorization = httpServletRequest.getHeader(
			HttpHeaders.AUTHORIZATION);

		if (Validator.isBlank(authorization)) {
			return null;
		}

		String[] authorizationParts = authorization.split("\\s");

		String scheme = authorizationParts[0];

		if (!StringUtil.equalsIgnoreCase(scheme, _TOKEN_KEY)) {
			return null;
		}

		String token = authorizationParts[1];

		if (Validator.isBlank(token)) {
			return null;
		}

		return _oAuth2AuthorizationLocalService.
			fetchOAuth2AuthorizationByAccessTokenContent(token);
	}

	protected void removeJaxRsApplicationName(
		ServiceReference<ScopeFinder> serviceReference) {

		_jaxRsApplicationNames.remove(
			GetterUtil.getString(
				serviceReference.getProperty("osgi.jaxrs.name")));
	}

	private static final String _TOKEN_KEY = "Bearer";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2JSONWSAuthVerifier.class);

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile BearerTokenProviderAccessor _bearerTokenProviderAccessor;

	private final Set<String> _jaxRsApplicationNames =
		Collections.newSetFromMap(new ConcurrentHashMap<>());

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private OAuth2ApplicationScopeAliasesLocalService
		_oAuth2ApplicationScopeAliasesLocalService;

	@Reference
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

	@Reference
	private OAuth2ScopeGrantLocalService _oAuth2ScopeGrantLocalService;

	@Reference
	private SAPEntryScopeDescriptorFinderRegistrator
		_sapEntryScopeDescriptorFinderRegistrator;

	@Reference
	private ScopeLocator _scopeLocator;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.web.internal.opensaml.integration.field.expression.resolver;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.saml.opensaml.integration.field.expression.resolver.UserFieldExpressionResolver;
import com.liferay.saml.opensaml.integration.resolver.UserResolver;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.web.internal.exception.UserIdentifierExpressionException;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(
	property = {"display.index:Integer=100", "key=attribute"},
	service = UserFieldExpressionResolver.class
)
public class AttributeUserFieldExpressionResolver
	implements UserFieldExpressionResolver {

	@Override
	public String getDescription(Locale locale) {
		return ResourceBundleUtil.getString(
			ResourceBundleUtil.getBundle(
				locale, AttributeUserFieldExpressionResolver.class),
			"match-using-a-specific-saml-attribute-mapping");
	}

	@Override
	public String resolveUserFieldExpression(
			Map<String, List<Serializable>> incomingAttributeValues,
			UserResolver.UserResolverSAMLContext userResolverSAMLContext)
		throws Exception {

		SamlSpIdpConnection samlSpIdpConnection =
			_samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
				CompanyThreadLocal.getCompanyId(),
				userResolverSAMLContext.resolvePeerEntityId());

		String userIdentifierExpression =
			samlSpIdpConnection.getUserIdentifierExpression();

		String userFieldExpression = userIdentifierExpression.substring(
			"attribute:".length());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Resolving user with user field expression: " +
					userFieldExpression);
		}

		if (!incomingAttributeValues.containsKey(userFieldExpression)) {
			throw new UserIdentifierExpressionException(
				"No SAML attribute value mapped for user field expression " +
					userFieldExpression);
		}

		return userFieldExpression;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AttributeUserFieldExpressionResolver.class);

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

}
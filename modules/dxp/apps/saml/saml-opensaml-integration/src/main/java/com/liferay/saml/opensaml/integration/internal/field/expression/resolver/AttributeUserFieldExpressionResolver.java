/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.opensaml.integration.internal.field.expression.resolver;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.saml.opensaml.integration.field.expression.resolver.UserFieldExpressionResolver;
import com.liferay.saml.opensaml.integration.resolver.UserResolver;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;

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
				"Matching against user identifier expression: \"" +
					userFieldExpression + "\"");
		}

		if (!incomingAttributeValues.containsKey(userFieldExpression)) {
			throw new Exception(
				"No SAML attribute value mapped/received for \"" +
					userIdentifierExpression + "\"");
		}

		return userFieldExpression;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AttributeUserFieldExpressionResolver.class);

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

}
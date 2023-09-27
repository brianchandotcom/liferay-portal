/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.field.expression.resolver;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.saml.opensaml.integration.field.expression.resolver.UserFieldExpressionResolver;
import com.liferay.saml.opensaml.integration.resolver.UserResolver;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.opensaml.saml.saml2.core.NameIDType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stian Sigvartsen
 */
@Component(
	property = {"default=true", "display.index:Integer=50", "key=dynamic"},
	service = UserFieldExpressionResolver.class
)
public class DynamicUserFieldExpressionResolver
	implements UserFieldExpressionResolver {

	@Override
	public String getDescription(Locale locale) {
		return ResourceBundleUtil.getString(
			ResourceBundleUtil.getBundle(
				locale, DynamicUserFieldExpressionResolver.class),
			"match-a-user-field-chosen-dynamically-based-on-name-id-format");
	}

	@Override
	public String resolveUserFieldExpression(
		Map<String, List<Serializable>> incomingAttributeValues,
		UserResolver.UserResolverSAMLContext userResolverSAMLContext) {

		String userIdentifierExpression = _resolverUserFieldExpression(
			userResolverSAMLContext.resolveSubjectNameFormat());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Dynamically resolved with user identifier expression: " +
					userIdentifierExpression);
		}

		return userIdentifierExpression;
	}

	private String _resolverUserFieldExpression(String subjectNameFormat) {
		if (Objects.equals(subjectNameFormat, NameIDType.EMAIL)) {
			return CompanyConstants.AUTH_TYPE_EA;
		}

		return CompanyConstants.AUTH_TYPE_SN;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicUserFieldExpressionResolver.class);

}
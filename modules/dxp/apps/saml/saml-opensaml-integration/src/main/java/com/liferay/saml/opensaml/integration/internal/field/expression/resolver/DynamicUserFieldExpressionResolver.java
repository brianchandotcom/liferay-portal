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
			"dynamically-match-nameid");
	}

	@Override
	public String resolveUserFieldExpression(
		UserResolver.UserResolverSAMLContext userResolverSAMLContext,
		Map<String, List<Serializable>> incomingAttributeValues) {

		String userIdentifierExpression = doResolverUserFieldExpression(
			userResolverSAMLContext.resolveSubjectNameFormat());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Dynamically resolved user identifier expression: \"" +
					userIdentifierExpression + "\"");
		}

		return userIdentifierExpression;
	}

	protected String doResolverUserFieldExpression(String subjectNameFormat) {
		if (Objects.equals(subjectNameFormat, NameIDType.EMAIL)) {
			return CompanyConstants.AUTH_TYPE_EA;
		}

		return CompanyConstants.AUTH_TYPE_SN;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicUserFieldExpressionResolver.class);

}
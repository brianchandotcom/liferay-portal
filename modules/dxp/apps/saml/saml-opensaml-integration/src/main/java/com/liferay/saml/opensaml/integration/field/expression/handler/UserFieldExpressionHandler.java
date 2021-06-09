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

package com.liferay.saml.opensaml.integration.field.expression.handler;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.saml.opensaml.integration.processor.context.UserProcessorContext;

import java.util.List;
import java.util.Locale;

/**
 * @author Stian Sigvartsen
 */
@ProviderType
public interface UserFieldExpressionHandler
	extends FieldExpressionHandler<User, UserProcessorContext> {

	public User getLdapUser(
			long companyId, String userIdentifier,
			String userIdentifierExpression)
		throws Exception;

	public String getSectionLabel(Locale locale);

	public User getUser(
			long companyId, String userIdentifier,
			String userIdentifierExpression)
		throws PortalException;

	public List<String> getValidFieldExpressions();

	public boolean isSupportedForUserMatching(String userIdentifier);

}
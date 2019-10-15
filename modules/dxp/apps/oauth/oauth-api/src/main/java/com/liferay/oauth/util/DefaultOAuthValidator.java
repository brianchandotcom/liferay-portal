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

package com.liferay.oauth.util;

import com.liferay.portal.kernel.oauth.OAuthException;

import net.oauth.SimpleOAuthValidator;

/**
 * @author Ivica Cardic
 */
public class DefaultOAuthValidator implements OAuthValidator {

	@Override
	public void validateOAuthMessage(
			OAuthMessage oAuthMessage, OAuthAccessor oAuthAccessor)
		throws OAuthException {

		try {
			_oAuthValidator.validateMessage(
				(net.oauth.OAuthMessage)oAuthMessage.getWrappedOAuthMessage(),
				(net.oauth.OAuthAccessor)
					oAuthAccessor.getWrappedOAuthAccessor());
		}
		catch (Exception e) {
			throw new OAuthException(e);
		}
	}

	private final net.oauth.OAuthValidator _oAuthValidator =
		new SimpleOAuthValidator();

}
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

package com.liferay.digital.signature.rest.internal.dto.v1_0.util;

import com.liferay.digital.signature.rest.dto.v1_0.DSEnvelopeSignatureURL;
import com.liferay.digital.signature.rest.dto.v1_0.DSRecipientViewDefinition;
import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author José Abelenda
 */
public class DSRecipientViewDefinitionUtil {

	public static DSEnvelopeSignatureURL toDSEnvelopeSignUrl(
		JSONObject jsonObject) {

		return new DSEnvelopeSignatureURL() {
			{
				url = jsonObject.getString("url");
			}
		};
	}

	public static DSRecipientViewDefinition toDSRecipientViewDefinition(
		com.liferay.digital.signature.model.DSRecipientViewDefinition
			dsRecipientViewDefinition) {

		return new DSRecipientViewDefinition() {
			{
				authenticationMethod =
					dsRecipientViewDefinition.getAuthenticationMethod();
				clientUserId = dsRecipientViewDefinition.getClientUserId();
				email = dsRecipientViewDefinition.getEmail();
				returnUrl = dsRecipientViewDefinition.getReturnUrl();
				userName = dsRecipientViewDefinition.getUserName();
			}
		};
	}

	public static com.liferay.digital.signature.model.DSRecipientViewDefinition
		toDSRecipientViewDefinition(
			DSRecipientViewDefinition dsRecipientViewDefinition) {

		return new com.liferay.digital.signature.model.
			DSRecipientViewDefinition() {

			{
				authenticationMethod =
					dsRecipientViewDefinition.getAuthenticationMethod();
				clientUserId = dsRecipientViewDefinition.getClientUserId();
				email = dsRecipientViewDefinition.getEmail();
				returnUrl = dsRecipientViewDefinition.getReturnUrl();
				userName = dsRecipientViewDefinition.getUserName();
			}
		};
	}

}
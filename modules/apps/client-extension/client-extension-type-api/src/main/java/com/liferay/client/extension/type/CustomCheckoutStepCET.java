/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type;

import com.liferay.client.extension.type.annotation.CETProperty;
import com.liferay.client.extension.type.annotation.CETType;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Andrea Sbarra
 */
@CETType(description = "This is a description.", name = "customCheckoutStep")
@ProviderType
public interface CustomCheckoutStepCET extends CET {

	@CETProperty(
		defaultValue = "", name = "actionURL", type = CETProperty.Type.String
	)
	public String getActionURL();

	@CETProperty(
		defaultValue = "", name = "active", type = CETProperty.Type.Boolean
	)
	public boolean getActive();

	@CETProperty(
		defaultValue = "", name = "checkoutStepLabel",
		type = CETProperty.Type.String
	)
	public String getCheckoutStepLabel();

	@CETProperty(
		defaultValue = "", name = "checkoutStepName",
		type = CETProperty.Type.String
	)
	public String getCheckoutStepName();

	@CETProperty(
		defaultValue = "", name = "checkoutStepOrder",
		type = CETProperty.Type.String
	)
	public int getCheckoutStepOrder();

	@CETProperty(
		defaultValue = "", name = "oAuth2ApplicationExternalReferenceCode",
		type = CETProperty.Type.String
	)
	public String getOAuth2ApplicationExternalReferenceCode();

	@CETProperty(
		defaultValue = "", name = "order", type = CETProperty.Type.Boolean
	)
	public boolean getOrder();

	@CETProperty(
		defaultValue = "", name = "readyURL", type = CETProperty.Type.String
	)
	public String getReadyURL();

	@CETProperty(
		defaultValue = "", name = "renderURL", type = CETProperty.Type.URL
	)
	public String getRenderURL();

	@CETProperty(
		defaultValue = "", name = "sennaDisabled",
		type = CETProperty.Type.Boolean
	)
	public boolean getSennaDisabled();

	@CETProperty(
		defaultValue = "", name = "showControls",
		type = CETProperty.Type.Boolean
	)
	public boolean getShowControls();

	@CETProperty(
		defaultValue = "", name = "visible", type = CETProperty.Type.Boolean
	)
	public boolean getVisible();

}
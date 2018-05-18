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

package com.liferay.commerce.product.definitions.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Marco Leo
 */
@ExtendedObjectClassDefinition(
	category = "commerce", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.product.definitions.web.configuration.AttachmentsConfiguration",
	localization = "content/Language",
	name = "commerce-attachments-configuration-name"
)
public interface AttachmentsConfiguration {

	@Meta.AD(
		deflt = ".gif,.jpeg,.jpg,.png,.mpg,.ogg", name = "image-extensions",
		required = false
	)
	public String[] imageExtensions();

	@Meta.AD(deflt = "5242880", name = "image-max-size", required = false)
	public long imageMaxSize();

}
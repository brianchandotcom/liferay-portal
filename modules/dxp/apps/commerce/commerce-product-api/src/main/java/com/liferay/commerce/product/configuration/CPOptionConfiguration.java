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

package com.liferay.commerce.product.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(category = "commerce")
@Meta.OCD(
	id = "com.liferay.commerce.product.configuration.CPOptionConfiguration",
	localization = "content/Language", name = "cp-option-configuration-name"
)
public interface CPOptionConfiguration {

	@Meta.AD(
		deflt = "select|radio|date|checkbox|checkbox_multiple|numeric",
		name = "product-option-form-field-types-allowed", required = false
	)
	public String[] ddmFormFieldTypesAllowed();

}
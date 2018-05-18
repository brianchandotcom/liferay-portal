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

package com.liferay.commerce.product.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Marco Leo
 */
@ProviderType
public interface DDMFormValuesHelper {

	public String cleanDDMFormValuesJSON(String json) throws PortalException;

	public DDMFormValues deserialize(
			DDMForm ddmForm, String json, Locale locale)
		throws PortalException;

	public boolean equals(String json1, String json2) throws PortalException;

	public String serialize(DDMFormValues ddmFormValues);

}
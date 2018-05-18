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

package com.liferay.commerce.product.type;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public interface CPType {

	public void deleteCPDefinition(long cpDefinitionId) throws PortalException;

	public String getCPDefinitionEditUrl(
			long cpDefinitionId, HttpServletRequest httpServletRequest)
		throws PortalException;

	public String getLabel(Locale locale);

	public String getName();

}
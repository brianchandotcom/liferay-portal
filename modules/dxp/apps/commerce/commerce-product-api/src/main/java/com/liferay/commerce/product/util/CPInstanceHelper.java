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

package com.liferay.commerce.product.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
@ProviderType
public interface CPInstanceHelper {

	public String render(
		long cpDefinitionId,RenderRequest renderRequest,
		RenderResponse renderResponse)
		throws PortalException;

	public DDMForm getDDMForm(
		long cpDefinitionId, Locale locale)
		throws PortalException;

	public String getDDMContent(
		long cpDefinitionId, Locale locale,
		String serializedDDMFormValues);

	public DDMFormValues getDDMFormValues(
		long cpDefinitionId, Locale locale, String serializedDDMFormValues);

	public Map<CPDefinitionOptionRel,List<CPDefinitionOptionValueRel>>
	parseCPInstanceDDMContent(long cpInstanceId)
		throws PortalException;

}
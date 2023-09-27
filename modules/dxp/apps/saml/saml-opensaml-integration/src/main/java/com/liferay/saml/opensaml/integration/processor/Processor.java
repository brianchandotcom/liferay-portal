/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.processor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Stian Sigvartsen
 */
public interface Processor<M extends BaseModel<M>> {

	public M process(ServiceContext serviceContext) throws PortalException;

	public <T, V extends T> void setValueArray(
		Class<T> clazz, String fieldExpression, V[] value);

	public void setValueArray(String fieldExpression, String[] value);

}
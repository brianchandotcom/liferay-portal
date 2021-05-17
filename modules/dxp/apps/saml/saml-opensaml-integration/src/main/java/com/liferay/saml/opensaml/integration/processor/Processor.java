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
		String fieldExpression, Class<T> clazz, V[] value);

	public void setValueArray(String fieldExpression, String[] value);

}
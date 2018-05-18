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

package com.liferay.commerce.order;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public interface CommerceOrderHelper {

	public List<ObjectValuePair<Long, String>> getWorkflowTransitions(
			long userId, CommerceOrder commerceOrder)
		throws PortalException;

}
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.helper;

import com.liferay.commerce.frontend.model.StepModel;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Locale;

/**
 * @author Andrea Sbarra
 */
public interface CommerceOrderStepTrackerHelper {

	public List<StepModel> getCommerceOrderSteps(
			boolean admin, CommerceOrder commerceOrder, Locale locale)
		throws PortalException;

}
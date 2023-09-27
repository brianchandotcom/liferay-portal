/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.form;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskForm;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance;

/**
 * @author Michael C. Han
 */
public interface FormValueProcessor {

	public KaleoTaskFormInstance processFormValues(
			KaleoTaskForm kaleoTaskForm,
			KaleoTaskFormInstance kaleoTaskFormInstance, String formValues,
			ServiceContext serviceContext)
		throws PortalException;

}
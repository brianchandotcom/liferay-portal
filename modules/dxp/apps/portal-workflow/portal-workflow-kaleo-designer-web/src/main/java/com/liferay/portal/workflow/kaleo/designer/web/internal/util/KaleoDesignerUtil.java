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

package com.liferay.portal.workflow.kaleo.designer.web.internal.util;

import com.liferay.portal.workflow.kaleo.designer.web.internal.configuration.FFKaleoDesignerConfigurationUtil;

/**
 * @author Feliphe Marinho
 */
public class KaleoDesignerUtil {

	public static String getEditJspPath() {
		if (FFKaleoDesignerConfigurationUtil.newKaleoDesignerEnabled()) {
			return "/designer/edit_workflow_definition.jsp";
		}

		return "/designer/edit_kaleo_definition_version.jsp";
	}

}
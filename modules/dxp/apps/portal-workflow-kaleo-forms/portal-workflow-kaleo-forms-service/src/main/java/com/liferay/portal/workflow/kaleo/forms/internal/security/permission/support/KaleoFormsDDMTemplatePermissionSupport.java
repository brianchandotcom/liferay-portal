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

package com.liferay.portal.workflow.kaleo.forms.internal.security.permission.support;

import com.liferay.dynamic.data.mapping.util.DDMTemplatePermissionSupport;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess",
	service = DDMTemplatePermissionSupport.class
)
public class KaleoFormsDDMTemplatePermissionSupport
	implements DDMTemplatePermissionSupport {

	@Override
	public String getResourceName(long classNameId) {
		return KaleoFormsConstants.RESOURCE_NAME;
	}

}
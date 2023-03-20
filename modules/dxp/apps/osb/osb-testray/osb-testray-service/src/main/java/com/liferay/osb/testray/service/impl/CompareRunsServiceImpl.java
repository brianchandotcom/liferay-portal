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

package com.liferay.osb.testray.service.impl;

import com.liferay.osb.testray.service.base.CompareRunsServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * @author José Abelenda
 */
@Component(
	property = {
		"json.web.service.context.name=osb",
		"json.web.service.context.path=CompareRuns"
	},
	service = AopService.class
)
public class CompareRunsServiceImpl extends CompareRunsServiceBaseImpl {
}
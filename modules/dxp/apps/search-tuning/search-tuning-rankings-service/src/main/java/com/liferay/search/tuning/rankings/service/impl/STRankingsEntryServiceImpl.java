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

package com.liferay.search.tuning.rankings.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.search.tuning.rankings.service.base.STRankingsEntryServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(
	property = {
		"json.web.service.context.name=searchtuning",
		"json.web.service.context.path=STRankingsEntry"
	},
	service = AopService.class
)
public class STRankingsEntryServiceImpl extends STRankingsEntryServiceBaseImpl {
}
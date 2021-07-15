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

package com.liferay.portal.search.tuning.rankings.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.search.tuning.rankings.service.base.RankingServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the ranking remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.portal.search.tuning.rankings.service.RankingService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Bryan Engler
 * @see RankingServiceBaseImpl
 */
@Component(
	property = {
		"json.web.service.context.name=resultrankings",
		"json.web.service.context.path=Ranking"
	},
	service = AopService.class
)
public class RankingServiceImpl extends RankingServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use <code>com.liferay.portal.search.tuning.rankings.service.RankingServiceUtil</code> to access the ranking remote service.
	 */

}
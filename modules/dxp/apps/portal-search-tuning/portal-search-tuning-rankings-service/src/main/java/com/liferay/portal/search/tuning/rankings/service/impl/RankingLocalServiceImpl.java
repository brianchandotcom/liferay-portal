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
import com.liferay.portal.search.tuning.rankings.service.base.RankingLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the ranking local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.portal.search.tuning.rankings.service.RankingLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Bryan Engler
 * @see RankingLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.portal.search.tuning.rankings.model.Ranking",
	service = AopService.class
)
public class RankingLocalServiceImpl extends RankingLocalServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>com.liferay.portal.search.tuning.rankings.service.RankingLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.portal.search.tuning.rankings.service.RankingLocalServiceUtil</code>.
	 */
}
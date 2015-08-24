/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.service.SocialRequestLocalServiceUtil;

/**
 * @author Bryan Engler
 * @author Sherry Yang
 */
public class VerifySocial extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		ActionableDynamicQuery socialRequestActionableDynamicQuery =
			SocialRequestLocalServiceUtil.getActionableDynamicQuery();

		socialRequestActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					long classNameId = PortalUtil.getClassNameId(Group.class);

					dynamicQuery.add(classNameIdProperty.eq(classNameId));

					Property classPKProperty = PropertyFactoryUtil.forName(
						"classPK");

					DynamicQuery groupDynamicQuery =
						DynamicQueryFactoryUtil.forClass(Group.class);

					Projection projection = ProjectionFactoryUtil.property(
						"groupId");

					groupDynamicQuery.setProjection(projection);

					dynamicQuery.add(classPKProperty.notIn(groupDynamicQuery));
				}

			});
		socialRequestActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<SocialRequest>() {

				@Override
				public void performAction(SocialRequest socialRequest) {
					SocialRequestLocalServiceUtil.deleteRequest(socialRequest);
				}

			});

		socialRequestActionableDynamicQuery.performActions();
	}

}
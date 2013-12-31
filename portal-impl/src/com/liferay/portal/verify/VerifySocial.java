/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.service.SocialRequestLocalServiceUtil;
import com.liferay.portlet.social.service.persistence.SocialRequestActionableDynamicQuery;

/**
 * @author Bryan Engler
 * @auther Sherry Yang
 */
public class VerifySocial extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		ActionableDynamicQuery socialRequestActionableDynamicQuery =
			new SocialRequestActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				Property classNameId = PropertyFactoryUtil.forName(
					"classNameId");

				dynamicQuery.add(
					classNameId.eq(PortalUtil.getClassNameId(Group.class)));

				Property classPK = PropertyFactoryUtil.forName("classPK");

				DynamicQuery groupDynamicQuery =
					DynamicQueryFactoryUtil.forClass(
						Group.class, PortalClassLoaderUtil.getClassLoader());

				groupDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("groupId"));

				dynamicQuery.add(classPK.notIn(groupDynamicQuery));
			}

			@Override
			protected void performAction(Object object)
				throws PortalException, SystemException {

				SocialRequestLocalServiceUtil.deleteRequest(
					(SocialRequest)object);
			}

		};

		long requestCount = socialRequestActionableDynamicQuery.performCount();

		if (requestCount == 0) {
			return;
		}

		socialRequestActionableDynamicQuery.performActions();
	}

}
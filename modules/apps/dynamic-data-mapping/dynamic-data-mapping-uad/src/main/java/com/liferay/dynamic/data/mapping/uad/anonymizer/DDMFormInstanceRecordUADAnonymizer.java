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

package com.liferay.dynamic.data.mapping.uad.anonymizer;

import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.uad.constants.DDMUADConstants;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = UADAnonymizer.class)
public class DDMFormInstanceRecordUADAnonymizer
	extends BaseDDMFormInstanceRecordUADAnonymizer {

	@Override
	public void autoAnonymize(
			DDMFormInstanceRecord ddmFormInstanceRecord, long userId,
			User anonymousUser)
		throws PortalException {

		super.autoAnonymize(ddmFormInstanceRecord, userId, anonymousUser);

		List<DDMFormInstanceRecordVersion> ddmFormInstanceRecordVersions =
			ddmFormInstanceRecordVersionLocalService.
				getFormInstanceRecordVersions(
					ddmFormInstanceRecord.getFormInstanceRecordId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		_anonymizeDDMFormIntanceRecordVersions(
			ddmFormInstanceRecordVersions, userId, anonymousUser);
	}

	@Override
	protected ActionableDynamicQuery getActionableDynamicQuery(long userId) {
		ActionableDynamicQuery actionableDynamicQuery =
			doGetActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

				for (String userIdFieldName :
						DDMUADConstants.
							USER_ID_FIELD_NAMES_DDM_FORM_INSTANCE_RECORD) {

					disjunction.add(
						RestrictionsFactoryUtil.eq(userIdFieldName, userId));
				}

				DynamicQuery dynamicSubquery =
					ddmFormInstanceRecordVersionLocalService.dynamicQuery();

				dynamicSubquery.setProjection(
					ProjectionFactoryUtil.property("formInstanceRecordId"));

				dynamicSubquery.add(
					RestrictionsFactoryUtil.eq("statusByUserId", userId));

				Property formInstanceRecordIdProperty =
					PropertyFactoryUtil.forName("formInstanceRecordId");

				disjunction.add(
					formInstanceRecordIdProperty.in(dynamicSubquery));

				dynamicQuery.add(disjunction);
			});

		return actionableDynamicQuery;
	}

	@Reference
	protected DDMContentLocalService ddmContentLocalService;

	@Reference
	protected DDMFormInstanceRecordVersionLocalService
		ddmFormInstanceRecordVersionLocalService;

	private void _anonymizeDDMContents(
		List<DDMContent> ddmContents, long userId, User anonymousUser) {

		ddmContents.forEach(
			ddmContent -> {
				if (ddmContent.getUserId() == userId) {
					ddmContent.setUserId(anonymousUser.getUserId());
					ddmContent.setUserName(anonymousUser.getFullName());
				}

				ddmContentLocalService.updateDDMContent(ddmContent);
			});
	}

	private void _anonymizeDDMFormIntanceRecordVersions(
		List<DDMFormInstanceRecordVersion> ddmFormInstanceRecordVersions,
		long userId, User anonymousUser) {

		List<DDMContent> ddmContents = new ArrayList<>();

		ddmFormInstanceRecordVersions.forEach(
			ddmFormInstanceRecordVersion -> {
				if (ddmFormInstanceRecordVersion.getUserId() == userId) {
					ddmFormInstanceRecordVersion.setUserId(
						anonymousUser.getUserId());

					ddmFormInstanceRecordVersion.setUserName(
						anonymousUser.getFullName());
				}

				if (ddmFormInstanceRecordVersion.getStatusByUserId() ==
						userId) {

					ddmFormInstanceRecordVersion.setStatusByUserId(
						anonymousUser.getUserId());

					ddmFormInstanceRecordVersion.setStatusByUserName(
						anonymousUser.getFullName());
				}

				if (ddmFormInstanceRecordVersion.getStatusByUserId() ==
						userId) {

					ddmFormInstanceRecordVersion.setStatusByUserId(
						anonymousUser.getUserId());

					ddmFormInstanceRecordVersion.setStatusByUserName(
						anonymousUser.getFullName());
				}

				ddmContents.add(
					ddmContentLocalService.fetchDDMContent(
						ddmFormInstanceRecordVersion.getStorageId()));

				ddmFormInstanceRecordVersionLocalService.
					updateDDMFormInstanceRecordVersion(
						ddmFormInstanceRecordVersion);
			});

		_anonymizeDDMContents(ddmContents, userId, anonymousUser);
	}

}
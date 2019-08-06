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

package com.liferay.portal.workflow.kaleo.service.impl;

import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Junction;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.exception.IncompleteWorkflowInstancesException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoConditionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTransitionLocalService;
import com.liferay.portal.workflow.kaleo.service.base.KaleoDefinitionVersionLocalServiceBaseImpl;
import com.liferay.portal.workflow.kaleo.util.comparator.KaleoDefinitionVersionIdComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion",
	service = AopService.class
)
public class KaleoDefinitionVersionLocalServiceImpl
	extends KaleoDefinitionVersionLocalServiceBaseImpl {

	@Override
	public KaleoDefinitionVersion addKaleoDefinitionVersion(
			String name, String title, String description, String content,
			String version, ServiceContext serviceContext)
		throws PortalException {

		// Kaleo definition version

		Date createDate = serviceContext.getCreateDate(new Date());
		Date modifiedDate = serviceContext.getModifiedDate(new Date());
		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());

		long kaleoDefinitionVersionId = counterLocalService.increment();

		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionPersistence.create(kaleoDefinitionVersionId);

		long groupId = _staging.getLiveGroupId(
			serviceContext.getScopeGroupId());

		kaleoDefinitionVersion.setGroupId(groupId);

		kaleoDefinitionVersion.setCompanyId(user.getCompanyId());
		kaleoDefinitionVersion.setUserId(user.getUserId());
		kaleoDefinitionVersion.setUserName(user.getFullName());
		kaleoDefinitionVersion.setCreateDate(createDate);
		kaleoDefinitionVersion.setModifiedDate(modifiedDate);
		kaleoDefinitionVersion.setName(name);
		kaleoDefinitionVersion.setTitle(title);
		kaleoDefinitionVersion.setDescription(description);
		kaleoDefinitionVersion.setContent(content);
		kaleoDefinitionVersion.setVersion(version);

		int status = GetterUtil.getInteger(
			serviceContext.getAttribute("status"),
			WorkflowConstants.STATUS_APPROVED);

		kaleoDefinitionVersion.setStatus(status);

		kaleoDefinitionVersion.setStatusByUserId(user.getUserId());
		kaleoDefinitionVersion.setStatusByUserName(user.getFullName());
		kaleoDefinitionVersion.setStatusDate(modifiedDate);

		kaleoDefinitionVersionPersistence.update(kaleoDefinitionVersion);

		return kaleoDefinitionVersion;
	}

	@Override
	public KaleoDefinitionVersion deleteKaleoDefinitionVersion(
			KaleoDefinitionVersion kaleoDefinitionVersion)
		throws PortalException {

		// Kaleo definition version

		int kaleoInstancesCount =
			_kaleoInstanceLocalService.getKaleoInstancesCount(
				kaleoDefinitionVersion.getKaleoDefinitionVersionId(), false);

		if (kaleoInstancesCount > 0) {
			throw new IncompleteWorkflowInstancesException(kaleoInstancesCount);
		}

		kaleoDefinitionVersionPersistence.remove(kaleoDefinitionVersion);

		// Kaleo condition

		_kaleoConditionLocalService.deleteKaleoDefinitionVersionKaleoCondition(
			kaleoDefinitionVersion.getKaleoDefinitionVersionId());

		// Kaleo instances

		_kaleoInstanceLocalService.deleteKaleoDefinitionVersionKaleoInstances(
			kaleoDefinitionVersion.getKaleoDefinitionVersionId());

		// Kaleo nodes

		_kaleoNodeLocalService.deleteKaleoDefinitionVersionKaleoNodes(
			kaleoDefinitionVersion.getKaleoDefinitionVersionId());

		// Kaleo tasks

		_kaleoTaskLocalService.deleteKaleoDefinitionVersionKaleoTasks(
			kaleoDefinitionVersion.getKaleoDefinitionVersionId());

		// Kaleo transitions

		_kaleoTransitionLocalService.
			deleteKaleoDefinitionVersionKaleoTransitions(
				kaleoDefinitionVersion.getKaleoDefinitionVersionId());

		return kaleoDefinitionVersion;
	}

	@Override
	public void deleteKaleoDefinitionVersion(
			long companyId, String name, String version)
		throws PortalException {

		KaleoDefinitionVersion kaleoDefinitionVersion =
			getKaleoDefinitionVersion(companyId, name, version);

		deleteKaleoDefinitionVersion(kaleoDefinitionVersion);
	}

	@Override
	public void deleteKaleoDefinitionVersions(
			List<KaleoDefinitionVersion> kaleoDefinitionVersions)
		throws PortalException {

		for (KaleoDefinitionVersion kaleoDefinitionVersion :
				kaleoDefinitionVersions) {

			deleteKaleoDefinitionVersion(kaleoDefinitionVersion);
		}
	}

	@Override
	public void deleteKaleoDefinitionVersions(long companyId, String name)
		throws PortalException {

		List<KaleoDefinitionVersion> kaleoDefinitionVersions =
			getKaleoDefinitionVersions(companyId, name);

		deleteKaleoDefinitionVersions(kaleoDefinitionVersions);
	}

	@Override
	public KaleoDefinitionVersion fetchKaleoDefinitionVersion(
		long companyId, String name, String version) {

		return kaleoDefinitionVersionPersistence.fetchByC_N_V(
			companyId, name, version);
	}

	@Override
	public KaleoDefinitionVersion fetchLatestKaleoDefinitionVersion(
			long companyId, String name)
		throws PortalException {

		return kaleoDefinitionVersionPersistence.fetchByC_N_First(
			companyId, name, new KaleoDefinitionVersionIdComparator(false));
	}

	@Override
	public KaleoDefinitionVersion fetchLatestKaleoDefinitionVersion(
			long companyId, String name,
			OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws PortalException {

		return kaleoDefinitionVersionPersistence.fetchByC_N_Last(
			companyId, name, orderByComparator);
	}

	@Override
	public KaleoDefinitionVersion getFirstKaleoDefinitionVersion(
			long companyId, String name)
		throws PortalException {

		return kaleoDefinitionVersionPersistence.findByC_N_First(
			companyId, name, new KaleoDefinitionVersionIdComparator(true));
	}

	@Override
	public KaleoDefinitionVersion getKaleoDefinitionVersion(
			long companyId, String name, String version)
		throws PortalException {

		return kaleoDefinitionVersionPersistence.findByC_N_V(
			companyId, name, version);
	}

	@Override
	public List<KaleoDefinitionVersion> getKaleoDefinitionVersions(
		long companyId, int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		return kaleoDefinitionVersionPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public List<KaleoDefinitionVersion> getKaleoDefinitionVersions(
			long companyId, String name)
		throws PortalException {

		return kaleoDefinitionVersionPersistence.findByC_N(companyId, name);
	}

	@Override
	public List<KaleoDefinitionVersion> getKaleoDefinitionVersions(
		long companyId, String name, int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		return kaleoDefinitionVersionPersistence.findByC_N(
			companyId, name, start, end, orderByComparator);
	}

	@Override
	public int getKaleoDefinitionVersionsCount(long companyId) {
		return kaleoDefinitionVersionPersistence.countByCompanyId(companyId);
	}

	@Override
	public int getKaleoDefinitionVersionsCount(long companyId, String name) {
		return kaleoDefinitionVersionPersistence.countByC_N(companyId, name);
	}

	@Override
	public KaleoDefinitionVersion[] getKaleoDefinitionVersionsPrevAndNext(
			long companyId, String name, String version)
		throws PortalException {

		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionPersistence.findByC_N_V(
				companyId, name, version);

		return kaleoDefinitionVersionPersistence.findByC_N_PrevAndNext(
			kaleoDefinitionVersion.getKaleoDefinitionVersionId(), companyId,
			name, new KaleoDefinitionVersionIdComparator(true));
	}

	@Override
	public KaleoDefinitionVersion getLatestKaleoDefinitionVersion(
			long companyId, String name)
		throws PortalException {

		return kaleoDefinitionVersionPersistence.findByC_N_First(
			companyId, name, new KaleoDefinitionVersionIdComparator(false));
	}

	@Override
	public List<KaleoDefinitionVersion> getLatestKaleoDefinitionVersions(
		long companyId, int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		return getLatestKaleoDefinitionVersions(
			companyId, null, WorkflowConstants.STATUS_ANY, start, end,
			orderByComparator);
	}

	@Override
	public List<KaleoDefinitionVersion> getLatestKaleoDefinitionVersions(
		long companyId, String keywords, int status, int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		List<Long> kaleoDefinitionVersionIds = getKaleoDefinitionVersionIds(
			companyId, keywords, status);

		if (kaleoDefinitionVersionIds.isEmpty()) {
			return Collections.emptyList();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KaleoDefinitionVersion.class, getClassLoader());

		Property property = PropertyFactoryUtil.forName(
			"kaleoDefinitionVersionId");

		dynamicQuery.add(property.in(kaleoDefinitionVersionIds));

		return dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public int getLatestKaleoDefinitionVersionsCount(
		long companyId, String keywords, int status) {

		List<Long> kaleoDefinitionVersionIds = getKaleoDefinitionVersionIds(
			companyId, keywords, status);

		return kaleoDefinitionVersionIds.size();
	}

	protected void addKeywordsCriterion(
		DynamicQuery dynamicQuery, String keywords) {

		if (Validator.isNull(keywords)) {
			return;
		}

		Junction junction = RestrictionsFactoryUtil.disjunction();

		for (String keyword : _customSQL.keywords(keywords)) {
			junction.add(RestrictionsFactoryUtil.ilike("name", keyword));
			junction.add(RestrictionsFactoryUtil.ilike("title", keyword));
		}

		dynamicQuery.add(junction);
	}

	protected void addStatusCriterion(DynamicQuery dynamicQuery, int status) {
		if (status != WorkflowConstants.STATUS_ANY) {
			Junction junction = RestrictionsFactoryUtil.disjunction();

			junction.add(RestrictionsFactoryUtil.eq("status", status));

			dynamicQuery.add(junction);
		}
	}

	protected List<Long> getKaleoDefinitionVersionIds(
		long companyId, String keywords, int status) {

		List<Long> kaleoDefinitionVersionIds = new ArrayList<>();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KaleoDefinitionVersion.class, getClassLoader());

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		addKeywordsCriterion(dynamicQuery, keywords);

		addStatusCriterion(dynamicQuery, status);

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(
			ProjectionFactoryUtil.max("kaleoDefinitionVersionId"));
		projectionList.add(ProjectionFactoryUtil.groupProperty("name"));

		dynamicQuery.setProjection(projectionList);

		List<Object[]> results = dynamicQuery(dynamicQuery);

		for (Object[] result : results) {
			kaleoDefinitionVersionIds.add((Long)result[0]);
		}

		return kaleoDefinitionVersionIds;
	}

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private KaleoConditionLocalService _kaleoConditionLocalService;

	@Reference
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Reference
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@Reference
	private KaleoTaskLocalService _kaleoTaskLocalService;

	@Reference
	private KaleoTransitionLocalService _kaleoTransitionLocalService;

	@Reference
	private Staging _staging;

}
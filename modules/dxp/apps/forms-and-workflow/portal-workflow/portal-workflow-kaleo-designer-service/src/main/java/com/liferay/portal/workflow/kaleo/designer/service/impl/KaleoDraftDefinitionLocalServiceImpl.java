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

package com.liferay.portal.workflow.kaleo.designer.service.impl;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Junction;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.designer.exception.DuplicateKaleoDraftDefinitionNameException;
import com.liferay.portal.workflow.kaleo.designer.exception.KaleoDraftDefinitionNameException;
import com.liferay.portal.workflow.kaleo.designer.exception.NoSuchKaleoDraftDefinitionException;
import com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition;
import com.liferay.portal.workflow.kaleo.designer.service.base.KaleoDraftDefinitionLocalServiceBaseImpl;
import com.liferay.portal.workflow.kaleo.designer.util.KaleoDesignerUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * Provides the local service for accessing, adding, deleting, and updating
 * Kaleo draft definitions.
 *
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
public class KaleoDraftDefinitionLocalServiceImpl
	extends KaleoDraftDefinitionLocalServiceBaseImpl {

	/**
	 * Adds a Kaleo draft definition.
	 *
	 * @param  userId the primary key of the Kaleo draft definition's
	 *         creator/owner
	 * @param  groupId the primary key of the Kaleo draft definition's group
	 * @param  name the Kaleo draft definition's name
	 * @param  titleMap the Kaleo draft definition's locales and localized
	 *         titles
	 * @param  content the content wrapped in XML
	 * @param  version the Kaleo draft definition's published version
	 * @param  draftVersion the Kaleo draft definition's draft version
	 * @param  serviceContext the service context to be applied. This can set
	 *         guest permissions and group permissions for the Kaleo draft
	 *         definition.
	 * @return the Kaleo draft definition
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public KaleoDraftDefinition addKaleoDraftDefinition(
			long userId, long groupId, String name,
			Map<Locale, String> titleMap, String content, int version,
			int draftVersion, ServiceContext serviceContext)
		throws PortalException {

		// Kaleo draft definition

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		validate(user.getCompanyId(), name, version, draftVersion);

		long kaleoDraftDefinitionId = counterLocalService.increment();

		KaleoDraftDefinition kaleoDraftDefinition =
			kaleoDraftDefinitionPersistence.create(kaleoDraftDefinitionId);

		kaleoDraftDefinition.setGroupId(groupId);
		kaleoDraftDefinition.setCompanyId(user.getCompanyId());
		kaleoDraftDefinition.setUserId(user.getUserId());
		kaleoDraftDefinition.setUserName(user.getFullName());
		kaleoDraftDefinition.setCreateDate(now);
		kaleoDraftDefinition.setModifiedDate(now);
		kaleoDraftDefinition.setName(name);
		kaleoDraftDefinition.setTitleMap(titleMap);
		kaleoDraftDefinition.setContent(content);
		kaleoDraftDefinition.setVersion(version);
		kaleoDraftDefinition.setDraftVersion(draftVersion);

		kaleoDraftDefinitionPersistence.update(kaleoDraftDefinition);

		// Resources

		resourceLocalService.addModelResources(
			kaleoDraftDefinition, serviceContext);

		return kaleoDraftDefinition;
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		Bundle bundle = FrameworkUtil.getBundle(
			KaleoDraftDefinitionLocalServiceImpl.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.singleValueMap(
			bundleContext, WorkflowDefinitionManager.class, "proxy.bean");

		_serviceTrackerMap.open();
	}

	/**
	 * Deletes the Kaleo draft definition and its resources.
	 *
	 * @param  kaleoDraftDefinition the Kaleo draft definition
	 * @return the deleted Kaleo draft definition
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public KaleoDraftDefinition deleteKaleoDraftDefinition(
			KaleoDraftDefinition kaleoDraftDefinition)
		throws PortalException {

		// Kaleo draft definition

		kaleoDraftDefinitionPersistence.remove(kaleoDraftDefinition);

		// Resources

		resourceLocalService.deleteResource(
			kaleoDraftDefinition, ResourceConstants.SCOPE_COMPANY);

		return kaleoDraftDefinition;
	}

	/**
	 * Deletes the Kaleo draft definition and its resources matching the name,
	 * published version, and draft version.
	 *
	 * @param  name the Kaleo draft definition's name
	 * @param  version the Kaleo draft definition's published version
	 * @param  draftVersion the Kaleo draft definition's draft version
	 * @param  serviceContext the service context to be applied. This can set
	 *         guest permissions and group permissions for the Kaleo draft
	 *         definition.
	 * @return the deleted Kaleo draft definition
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public KaleoDraftDefinition deleteKaleoDraftDefinition(
			String name, int version, int draftVersion,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoDraftDefinition kaleoDraftDefinition = getKaleoDraftDefinition(
			name, version, draftVersion, serviceContext);

		return deleteKaleoDraftDefinition(kaleoDraftDefinition);
	}

	/**
	 * Deletes the kaleo draft definition and its resources matching the name
	 * and version.
	 *
	 * @param  name the Kaleo draft definition's name
	 * @param  version the Kaleo draft definition's published version
	 * @param  serviceContext the service context to be applied. This can set
	 *         guest permissions and group permissions for the Kaleo draft
	 *         definition.
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteKaleoDraftDefinitions(
			String name, int version, ServiceContext serviceContext)
		throws PortalException {

		List<KaleoDraftDefinition> kaleoDraftDefinitions =
			kaleoDraftDefinitionPersistence.findByC_N_V(
				serviceContext.getCompanyId(), name, version);

		for (KaleoDraftDefinition kaleoDraftDefinition :
				kaleoDraftDefinitions) {

			deleteKaleoDraftDefinition(kaleoDraftDefinition);
		}
	}

	@Override
	public void destroy() {
		super.destroy();

		_serviceTrackerMap.close();
	}

	/**
	 * Returns the Kaleo draft definition matching the name, published version,
	 * and draft version.
	 *
	 * @param  name the Kaleo draft definition's name
	 * @param  version the Kaleo draft definition's published version
	 * @param  draftVersion the Kaleo draft definition's draft version
	 * @param  serviceContext the service context to be applied. This can set
	 *         guest permissions and group permissions for the Kaleo draft
	 *         definition.
	 * @return the matching Kaleo draft definition
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public KaleoDraftDefinition getKaleoDraftDefinition(
			String name, int version, int draftVersion,
			ServiceContext serviceContext)
		throws PortalException {

		return kaleoDraftDefinitionPersistence.findByC_N_V_D(
			serviceContext.getCompanyId(), name, version, draftVersion);
	}

	/**
	 * Returns an ordered range of the Kaleo draft definitions matching the name
	 * and version.
	 *
	 * @param  name the Kaleo draft definition's name
	 * @param  version the Kaleo draft definition's published version
	 * @param  start the lower bound of the range of Kaleo draft definitions to
	 *         return
	 * @param  end the upper bound of the range of Kaleo draft definitions to
	 *         return (not inclusive)
	 * @param  orderByComparator the comparator to order the Kaleo draft
	 *         definitions
	 * @param  serviceContext the service context to be applied. This can set
	 *         guest permissions and group permissions for the Kaleo draft
	 *         definition.
	 * @return the range of matching Kaleo draft definitions ordered by the
	 *         comparator
	 */
	@Override
	public List<KaleoDraftDefinition> getKaleoDraftDefinitions(
		String name, int version, int start, int end,
		OrderByComparator orderByComparator, ServiceContext serviceContext) {

		return kaleoDraftDefinitionPersistence.findByC_N_V(
			serviceContext.getCompanyId(), name, version, start, end,
			orderByComparator);
	}

	/**
	 * Returns the number of Kaleo draft definition matching the name and
	 * version.
	 *
	 * @param  name the Kaleo draft definition's name
	 * @param  version the Kaleo draft definition's published version
	 * @param  serviceContext the service context to be applied. This can set
	 *         guest permissions and group permissions for the Kaleo draft
	 *         definition.
	 * @return the number of matching Kaleo draft definitions
	 */
	@Override
	public int getKaleoDraftDefinitionsCount(
		String name, int version, ServiceContext serviceContext) {

		return kaleoDraftDefinitionPersistence.countByC_N_V(
			serviceContext.getCompanyId(), name, version);
	}

	/**
	 * Returns the latest Kaleo draft definition matching the name and version.
	 *
	 * @param  name the Kaleo draft definition's name
	 * @param  version the Kaleo draft definition's published version
	 * @param  serviceContext the service context to be applied. This can set
	 *         guest permissions and group permissions for the Kaleo draft
	 *         definition.
	 * @return the latest matching Kaleo draft definition
	 * @throws PortalException if a matching Kaleo draft definition could not be
	 *         found
	 */
	@Override
	public KaleoDraftDefinition getLatestKaleoDraftDefinition(
			String name, int version, ServiceContext serviceContext)
		throws PortalException {

		List<KaleoDraftDefinition> kaleoDraftDefinitions =
			kaleoDraftDefinitionPersistence.findByC_N_V(
				serviceContext.getCompanyId(), name, version, 0, 1);

		if (kaleoDraftDefinitions.isEmpty()) {
			throw new NoSuchKaleoDraftDefinitionException();
		}

		return kaleoDraftDefinitions.get(0);
	}

	/**
	 * Returns an ordered range of the latest Kaleo draft definitions matching
	 * the company and version.
	 *
	 * @param  companyId the primary key of the Kaleo draft definition's company
	 * @param  version the Kaleo draft definition's published version
	 * @param  start the lower bound of the range of Kaleo draft definitions to
	 *         return
	 * @param  end the upper bound of the range of Kaleo draft definitions to
	 *         return (not inclusive)
	 * @param  orderByComparator the comparator to order the Kaleo draft
	 *         definitions
	 * @return the range of matching Kaleo draft definitions ordered by the
	 *         comparator
	 */
	@Override
	public List<KaleoDraftDefinition> getLatestKaleoDraftDefinitions(
		long companyId, int version, int start, int end,
		OrderByComparator orderByComparator) {

		return getLatestKaleoDraftDefinitions(
			companyId, null, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of the latest Kaleo draft definitions matching
	 * the company, keywords, and version.
	 *
	 * @param  companyId the primary key of the Kaleo draft definition's company
	 * @param  keywords the Kaleo draft definition's name or title
	 * @param  version the Kaleo draft definition's published version
	 * @param  start the lower bound of the range of Kaleo draft definitions to
	 *         return
	 * @param  end the upper bound of the range of Kaleo draft definitions to
	 *         return (not inclusive)
	 * @param  orderByComparator the comparator to order the Kaleo draft
	 *         definitions
	 * @return the range of matching Kaleo draft definitions ordered by the
	 *         comparator
	 */
	@Override
	public List<KaleoDraftDefinition> getLatestKaleoDraftDefinitions(
		long companyId, String keywords, int version, int start, int end,
		OrderByComparator orderByComparator) {

		List<Long> kaleoDraftDefinitioIds = getKaleoDraftDefinitionIds(
			companyId, keywords, version);

		if (kaleoDraftDefinitioIds.isEmpty()) {
			return Collections.emptyList();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KaleoDraftDefinition.class, getClassLoader());

		Property property = PropertyFactoryUtil.forName(
			"kaleoDraftDefinitionId");

		dynamicQuery.add(property.in(kaleoDraftDefinitioIds));

		return dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of Kaleo draft definitions matching the company and
	 * version.
	 *
	 * @param  companyId the primary key of the Kaleo draft definition's company
	 * @param  version the Kaleo draft definition's published version
	 * @return the number of matching Kaleo draft definitions
	 */
	@Override
	public int getLatestKaleoDraftDefinitionsCount(
		long companyId, int version) {

		return getLatestKaleoDraftDefinitionsCount(companyId, null, version);
	}

	/**
	 * Returns the number of Kaleo draft definitions matching the company,
	 * keywords, and version.
	 *
	 * @param  companyId the primary key of the Kaleo draft definition's company
	 * @param  keywords the Kaleo draft definition's name or title
	 * @param  version the Kaleo draft definition's published version
	 * @return the number of matching Kaleo draft definitions
	 */
	@Override
	public int getLatestKaleoDraftDefinitionsCount(
		long companyId, String keywords, int version) {

		List<Long> kaleoDraftDefinitioIds = getKaleoDraftDefinitionIds(
			companyId, keywords, version);

		if (kaleoDraftDefinitioIds.isEmpty()) {
			return 0;
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KaleoDraftDefinition.class, getClassLoader());

		Property property = PropertyFactoryUtil.forName(
			"kaleoDraftDefinitionId");

		dynamicQuery.add(property.in(kaleoDraftDefinitioIds));

		return (int)dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Adds a Kaleo draft definition with a draft version increment.
	 *
	 * @param  userId the primary key of the Kaleo draft definition's
	 *         creator/owner
	 * @param  name the Kaleo draft definition's name
	 * @param  version the Kaleo draft definition's published version
	 * @param  serviceContext the service context to be applied. This can set
	 *         guest permissions and group permissions for the Kaleo draft
	 *         deifnition.
	 * @return the Kaleo draft definition
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public KaleoDraftDefinition incrementKaleoDraftDefinitionDraftVersion(
			long userId, String name, int version,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoDraftDefinition kaleoDraftDefinition =
			getLatestKaleoDraftDefinition(name, version, serviceContext);

		return addKaleoDraftDefinition(
			userId, kaleoDraftDefinition.getGroupId(),
			kaleoDraftDefinition.getName(), kaleoDraftDefinition.getTitleMap(),
			kaleoDraftDefinition.getContent(),
			kaleoDraftDefinition.getVersion(),
			kaleoDraftDefinition.getDraftVersion() + 1, serviceContext);
	}

	/**
	 * Publishes the Kaleo draft definition.
	 *
	 * @param  userId the primary key of the Kaleo draft definition's
	 *         creator/owner
	 * @param  groupId the primary key of the Kaleo draft definition's group
	 * @param  name the Kaleo draft definition's name
	 * @param  titleMap the Kaleo draft definition's locales and localized
	 *         titles
	 * @param  content the content wrapped in XML
	 * @param  serviceContext the service context to be applied. This can set
	 *         guest permissions and group permissions for the Kaleo draft
	 *         definition.
	 * @return the published Kaleo draft definition
	 * @throws PortalException if the user did not have the required permissions
	 *         to publish the Kaleo draft definition or if a portal exception
	 *         occurred
	 */
	@Override
	public KaleoDraftDefinition publishKaleoDraftDefinition(
			long userId, long groupId, String name,
			Map<Locale, String> titleMap, String content,
			ServiceContext serviceContext)
		throws PortalException {

		validate(content);

		WorkflowDefinition workflowDefinition =
			KaleoDesignerUtil.deployWorkflowDefinition(
				serviceContext.getCompanyId(), serviceContext.getUserId(),
				titleMap, content);

		int version = workflowDefinition.getVersion();

		KaleoDraftDefinition kaleoDraftDefinition =
			KaleoDesignerUtil.addMissingKaleoDraftDefinition(
				workflowDefinition.getName(), version,
				workflowDefinition.getTitle(), workflowDefinition.getContent(),
				serviceContext);

		if (version == 1) {
			deleteKaleoDraftDefinitions(name, 0, serviceContext);
		}

		return kaleoDraftDefinition;
	}

	/**
	 * Updates the Kaleo draft definition by replacing its content and title and
	 * incrementing the draft version.
	 *
	 * @param  userId the primary key of the Kaleo draft definition's
	 *         creator/owner
	 * @param  name the Kaleo draft definition's name
	 * @param  titleMap the Kaleo draft definition's locales and localized
	 *         titles
	 * @param  content the content wrapped in XML
	 * @param  version the Kaleo draft definition's published version
	 * @param  serviceContext the service context to be applied. This can set
	 *         guest permissions and group permissions for the Kaleo draft
	 *         definition.
	 * @return the updated Kaleo draft definition
	 * @throws PortalException if the user did not have the required permissions
	 *         to update the Kaleo draft definition or if a portal exception
	 *         occurred
	 */
	@Override
	public KaleoDraftDefinition updateKaleoDraftDefinition(
			long userId, String name, Map<Locale, String> titleMap,
			String content, int version, ServiceContext serviceContext)
		throws PortalException {

		KaleoDraftDefinition kaleoDraftDefinition =
			incrementKaleoDraftDefinitionDraftVersion(
				userId, name, version, serviceContext);

		kaleoDraftDefinition.setTitleMap(titleMap);
		kaleoDraftDefinition.setContent(content);

		kaleoDraftDefinitionPersistence.update(kaleoDraftDefinition);

		return kaleoDraftDefinition;
	}

	protected void addKeywordsCriterion(
		DynamicQuery dynamicQuery, String keywords) {

		if (Validator.isNull(keywords)) {
			return;
		}

		Junction junction = RestrictionsFactoryUtil.disjunction();

		for (String keyword : CustomSQLUtil.keywords(keywords)) {
			junction.add(RestrictionsFactoryUtil.ilike("name", keyword));
			junction.add(RestrictionsFactoryUtil.ilike("title", keyword));
		}

		dynamicQuery.add(junction);
	}

	protected void addVersionCriterion(DynamicQuery dynamicQuery, int version) {
		if (version < 0) {
			return;
		}

		Property versionProperty = PropertyFactoryUtil.forName("version");

		dynamicQuery.add(versionProperty.eq(version));
	}

	protected List<Long> getKaleoDraftDefinitionIds(
		long companyId, int version) {

		return getKaleoDraftDefinitionIds(companyId, null, version);
	}

	protected List<Long> getKaleoDraftDefinitionIds(
		long companyId, String keywords, int version) {

		List<Long> kaleoDraftDefinitionIds = new ArrayList<>();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KaleoDraftDefinition.class, getClassLoader());

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		addKeywordsCriterion(dynamicQuery, keywords);
		addVersionCriterion(dynamicQuery, version);

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(ProjectionFactoryUtil.max("kaleoDraftDefinitionId"));
		projectionList.add(ProjectionFactoryUtil.groupProperty("name"));

		dynamicQuery.setProjection(projectionList);

		List<Object[]> results = dynamicQuery(dynamicQuery);

		for (Object[] result : results) {
			kaleoDraftDefinitionIds.add((Long)result[0]);
		}

		return kaleoDraftDefinitionIds;
	}

	protected void validate(
			long companyId, String name, int version, int draftVersion)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new KaleoDraftDefinitionNameException();
		}

		if (kaleoDraftDefinitionPersistence.countByC_N_V_D(
				companyId, name, version, draftVersion) > 0) {

			throw new DuplicateKaleoDraftDefinitionNameException();
		}
	}

	protected void validate(
			long companyId, String name, String content, int version,
			int draftVersion)
		throws PortalException {

		validate(companyId, name, version, draftVersion);
		validate(content);
	}

	protected void validate(String content) throws WorkflowException {
		try {
			WorkflowDefinitionManager workflowDefinitionManager =
				_serviceTrackerMap.getService("false");

			workflowDefinitionManager.validateWorkflowDefinition(
				content.getBytes());
		}
		catch (WorkflowException we) {
			if (we.getCause() instanceof KaleoDefinitionValidationException) {
				throw (KaleoDefinitionValidationException)we.getCause();
			}
			else {
				throw new WorkflowException(we);
			}
		}
	}

	private ServiceTrackerMap<String, WorkflowDefinitionManager>
		_serviceTrackerMap;

}
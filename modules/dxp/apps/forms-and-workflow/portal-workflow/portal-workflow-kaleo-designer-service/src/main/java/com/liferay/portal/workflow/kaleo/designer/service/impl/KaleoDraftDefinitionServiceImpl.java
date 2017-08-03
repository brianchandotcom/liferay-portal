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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.designer.constants.KaleoDesignerActionKeys;
import com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition;
import com.liferay.portal.workflow.kaleo.designer.service.base.KaleoDraftDefinitionServiceBaseImpl;
import com.liferay.portal.workflow.kaleo.designer.service.permission.KaleoDesignerPermission;
import com.liferay.portal.workflow.kaleo.designer.service.permission.KaleoDraftDefinitionPermission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * Kaleo draft definitions. This class's methods include permission checks.
 *
 * @author Gregory Amerson
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
public class KaleoDraftDefinitionServiceImpl
	extends KaleoDraftDefinitionServiceBaseImpl {

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
	 * @throws PortalException if the user did not have the required permissions
	 *         to create the Kaleo draft definition or if a portal exception
	 *         occurred
	 */
	@Override
	public KaleoDraftDefinition addKaleoDraftDefinition(
			long userId, long groupId, String name,
			Map<Locale, String> titleMap, String content, int version,
			int draftVersion, ServiceContext serviceContext)
		throws PortalException {

		KaleoDesignerPermission.check(
			getPermissionChecker(), groupId, KaleoDesignerActionKeys.ADD_DRAFT);

		return kaleoDraftDefinitionLocalService.addKaleoDraftDefinition(
			userId, groupId, name, titleMap, content, version, draftVersion,
			serviceContext);
	}

	/**
	 * Deletes the Kaleo draft definition and its resources.
	 *
	 * @param  name the Kaleo draft definition's name
	 * @param  version the Kaleo draft definition's published version
	 * @param  serviceContext the service context to be applied. This can set
	 *         guest permissions and group permissions for the Kaleo draft
	 *         definition.
	 * @throws PortalException if the user did not have the required permissions
	 *         to delete the Kaleo draft definition or if a portal exception
	 *         occurred
	 */
	@Override
	public void deleteKaleoDraftDefinitions(
			String name, int version, ServiceContext serviceContext)
		throws PortalException {

		KaleoDraftDefinition kaleoDraftDefinition =
			kaleoDraftDefinitionLocalService.getLatestKaleoDraftDefinition(
				name, version, serviceContext);

		KaleoDraftDefinitionPermission.check(
			getPermissionChecker(), kaleoDraftDefinition, ActionKeys.DELETE);

		kaleoDraftDefinitionLocalService.deleteKaleoDraftDefinitions(
			name, version, serviceContext);
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
	 * @throws PortalException if the user did not have the required permissions
	 *         to access the Kaleo draft definition or if a portal exception
	 *         occurred
	 */
	@Override
	public KaleoDraftDefinition getKaleoDraftDefinition(
			String name, int version, int draftVersion,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoDraftDefinition kaleoDraftDefinition =
			kaleoDraftDefinitionLocalService.getKaleoDraftDefinition(
				name, version, draftVersion, serviceContext);

		KaleoDraftDefinitionPermission.check(
			getPermissionChecker(), kaleoDraftDefinition, ActionKeys.VIEW);

		return kaleoDraftDefinition;
	}

	/**
	 * Returns the Kaleo draft definitions.
	 *
	 * @return the Kaleo draft definitions
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public List<KaleoDraftDefinition> getKaleoDraftDefinitions()
		throws PortalException {

		List<KaleoDraftDefinition> kaleoDraftDefinitions =
			kaleoDraftDefinitionLocalService.getKaleoDraftDefinitions(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		return filterKaleoDraftDefinitions(
			kaleoDraftDefinitions, ActionKeys.VIEW);
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
	 * @throws PortalException if a matching kaleo draft definition could not be
	 *         found or if the user did not have the required permissions to
	 *         access the Kaleo draft definition
	 */
	@Override
	public KaleoDraftDefinition getLatestKaleoDraftDefinition(
			String name, int version, ServiceContext serviceContext)
		throws PortalException {

		KaleoDraftDefinition latestKaleoDraftDefinition =
			kaleoDraftDefinitionLocalService.getLatestKaleoDraftDefinition(
				name, version, serviceContext);

		KaleoDraftDefinitionPermission.check(
			getPermissionChecker(), latestKaleoDraftDefinition,
			ActionKeys.VIEW);

		return latestKaleoDraftDefinition;
	}

	/**
	 * Returns an ordered range of the latest Kaleo draft definitions matching
	 * the company and version.
	 *
	 * @param  companyId the primary key of the Kaleo draft definition's company
	 * @param  version the Kaleo draft definition's published version
	 * @param  start the lower bound of the range of Kaleo draft definitions to
	 *         return
	 * @param  end the upper bound of the range of Kkaleo draft definitions to
	 *         return (not inclusive)
	 * @param  orderByComparator the comparator to order the Kaleo draft
	 *         definitions
	 * @return the range of matching Kaleo draft definitions ordered by the
	 *         comparator
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public List<KaleoDraftDefinition> getLatestKaleoDraftDefinitions(
			long companyId, int version, int start, int end,
			OrderByComparator orderByComparator)
		throws PortalException {

		List<KaleoDraftDefinition> latestKaleoDraftDefinitions =
			kaleoDraftDefinitionLocalService.getLatestKaleoDraftDefinitions(
				companyId, version, start, end, orderByComparator);

		return filterKaleoDraftDefinitions(
			latestKaleoDraftDefinitions, ActionKeys.VIEW);
	}

	/**
	 * Returns an ordered range of the latest Kaleo draft definitions matching
	 * the company, version, and keywords.
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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public List<KaleoDraftDefinition> getLatestKaleoDraftDefinitions(
			long companyId, String keywords, int version, int start, int end,
			OrderByComparator orderByComparator)
		throws PortalException {

		List<KaleoDraftDefinition> latestKaleoDraftDefinitions =
			kaleoDraftDefinitionLocalService.getLatestKaleoDraftDefinitions(
				companyId, keywords, version, start, end, orderByComparator);

		return filterKaleoDraftDefinitions(
			latestKaleoDraftDefinitions, ActionKeys.VIEW);
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
	 *         guest permissions and group permissions for the Kkaleo draft
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

		KaleoDesignerPermission.check(
			getPermissionChecker(), groupId, KaleoDesignerActionKeys.PUBLISH);

		return kaleoDraftDefinitionLocalService.publishKaleoDraftDefinition(
			userId, groupId, name, titleMap, content, serviceContext);
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

		KaleoDraftDefinition latestKaleoDraftDefinition =
			getLatestKaleoDraftDefinition(name, version, serviceContext);

		KaleoDraftDefinitionPermission.check(
			getPermissionChecker(), latestKaleoDraftDefinition,
			ActionKeys.UPDATE);

		return kaleoDraftDefinitionLocalService.updateKaleoDraftDefinition(
			userId, name, titleMap, content, version, serviceContext);
	}

	protected List<KaleoDraftDefinition> filterKaleoDraftDefinitions(
			List<KaleoDraftDefinition> kaleoDraftDefinitions, String actionId)
		throws PrincipalException {

		List<KaleoDraftDefinition> filteredKaleoDraftDefinitions =
			new ArrayList<>();

		for (KaleoDraftDefinition kaleoDraftDefinition :
				kaleoDraftDefinitions) {

			if (KaleoDraftDefinitionPermission.contains(
					getPermissionChecker(), kaleoDraftDefinition, actionId)) {

				filteredKaleoDraftDefinitions.add(kaleoDraftDefinition);
			}
		}

		return Collections.unmodifiableList(filteredKaleoDraftDefinitions);
	}

}
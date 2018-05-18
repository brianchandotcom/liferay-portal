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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.service.base.CPDefinitionLinkLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CPDefinitionLinkLocalServiceImpl
	extends CPDefinitionLinkLocalServiceBaseImpl {

	@Override
	public CPDefinitionLink addCPDefinitionLink(
			long cpDefinitionId1, long cpDefinitionId2, double priority,
			String type, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		CPDefinition cpDefinition1 = cpDefinitionPersistence.findByPrimaryKey(
			cpDefinitionId1);

		long cpDefinitionLinkId = counterLocalService.increment();

		CPDefinitionLink cpDefinitionLink = cpDefinitionLinkPersistence.create(
			cpDefinitionLinkId);

		cpDefinitionLink.setUuid(serviceContext.getUuid());
		cpDefinitionLink.setGroupId(cpDefinition1.getGroupId());
		cpDefinitionLink.setCompanyId(user.getCompanyId());
		cpDefinitionLink.setUserId(user.getUserId());
		cpDefinitionLink.setUserName(user.getFullName());
		cpDefinitionLink.setCPDefinitionId1(cpDefinitionId1);
		cpDefinitionLink.setCPDefinitionId2(cpDefinitionId2);
		cpDefinitionLink.setPriority(priority);
		cpDefinitionLink.setType(type);
		cpDefinitionLink.setExpandoBridgeAttributes(serviceContext);

		cpDefinitionLinkPersistence.update(cpDefinitionLink);

		reindexCPDefinition(cpDefinitionId1);

		return cpDefinitionLink;
	}

	@Override
	public void deleteCPDefinitionLinks(long cpDefinitionId) {
		cpDefinitionLinkPersistence.removeByCPDefinitionId1(cpDefinitionId);
		cpDefinitionLinkPersistence.removeByCPDefinitionId2(cpDefinitionId);
	}

	@Override
	public List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId1, String type)
		throws PortalException {

		return cpDefinitionLinkPersistence.findByC1_T(cpDefinitionId1, type);
	}

	@Override
	public List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId1, String type, int start, int end,
			OrderByComparator<CPDefinitionLink> orderByComparator)
		throws PortalException {

		return cpDefinitionLinkPersistence.findByC1_T(
			cpDefinitionId1, type, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionLinksCount(long cpDefinitionId1, String type)
		throws PortalException {

		return cpDefinitionLinkPersistence.countByC1_T(cpDefinitionId1, type);
	}

	@Override
	public List<CPDefinitionLink> getReverseCPDefinitionLinks(
		long cpDefinitionId, String type) {

		return cpDefinitionLinkPersistence.findByC2_T(cpDefinitionId, type);
	}

	@Override
	public CPDefinitionLink updateCPDefinitionLink(
			long cpDefinitionLinkId, double priority,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionLink cpDefinitionLink =
			cpDefinitionLinkPersistence.findByPrimaryKey(cpDefinitionLinkId);

		cpDefinitionLink.setPriority(priority);
		cpDefinitionLink.setExpandoBridgeAttributes(serviceContext);

		cpDefinitionLinkPersistence.update(cpDefinitionLink);

		reindexCPDefinition(cpDefinitionLink.getCPDefinitionId1());

		return cpDefinitionLink;
	}

	@Override
	public void updateCPDefinitionLinks(
			long cpDefinitionId1, long[] cpDefinitionIds2, String type,
			ServiceContext serviceContext)
		throws PortalException {

		if (cpDefinitionIds2 == null) {
			return;
		}

		List<CPDefinitionLink> cpDefinitionLinks = getCPDefinitionLinks(
			cpDefinitionId1, type);

		for (CPDefinitionLink cpDefinitionLink : cpDefinitionLinks) {
			if ((cpDefinitionLink.getCPDefinitionId1() == cpDefinitionId1) &&
				!ArrayUtil.contains(
					cpDefinitionIds2, cpDefinitionLink.getCPDefinitionId2())) {

				deleteCPDefinitionLink(cpDefinitionLink);
			}
		}

		for (long cpDefinitionId2 : cpDefinitionIds2) {
			if (cpDefinitionId1 != cpDefinitionId2) {
				CPDefinitionLink cpDefinitionLink =
					cpDefinitionLinkPersistence.fetchByC1_C2_T(
						cpDefinitionId1, cpDefinitionId2, type);

				if (cpDefinitionLink == null) {
					addCPDefinitionLink(
						cpDefinitionId1, cpDefinitionId2, 0, type,
						serviceContext);
				}
			}
		}
	}

	protected void reindexCPDefinition(long cpDefinitionId)
		throws PortalException {

		Indexer<CPDefinition> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPDefinition.class);

		indexer.reindex(CPDefinition.class.getName(), cpDefinitionId);
	}

}
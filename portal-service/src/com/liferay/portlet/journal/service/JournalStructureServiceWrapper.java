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

package com.liferay.portlet.journal.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link JournalStructureService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalStructureService
 * @generated
 */
public class JournalStructureServiceWrapper implements JournalStructureService,
	ServiceWrapper<JournalStructureService> {
	public JournalStructureServiceWrapper(
		JournalStructureService journalStructureService) {
		_journalStructureService = journalStructureService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _journalStructureService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_journalStructureService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.journal.model.JournalStructure addStructure(
		long groupId, java.lang.String structureId, boolean autoStructureId,
		java.lang.String parentStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureService.addStructure(groupId, structureId,
			autoStructureId, parentStructureId, nameMap, descriptionMap, xsd,
			serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalStructure copyStructure(
		long groupId, java.lang.String oldStructureId,
		java.lang.String newStructureId, boolean autoStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureService.copyStructure(groupId, oldStructureId,
			newStructureId, autoStructureId);
	}

	public void deleteStructure(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalStructureService.deleteStructure(groupId, structureId);
	}

	public com.liferay.portlet.journal.model.JournalStructure getStructure(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureService.getStructure(groupId, structureId);
	}

	public com.liferay.portlet.journal.model.JournalStructure getStructure(
		long groupId, java.lang.String structureId,
		boolean includeGlobalStructures)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureService.getStructure(groupId, structureId,
			includeGlobalStructures);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> getStructures(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureService.getStructures(groupId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> getStructures(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureService.getStructures(groupIds);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> search(
		long companyId, long[] groupIds, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureService.search(companyId, groupIds, keywords,
			start, end, obc);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> search(
		long companyId, long[] groupIds, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureService.search(companyId, groupIds,
			structureId, name, description, andOperator, start, end, obc);
	}

	public int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureService.searchCount(companyId, groupIds,
			keywords);
	}

	public int searchCount(long companyId, long[] groupIds,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureService.searchCount(companyId, groupIds,
			structureId, name, description, andOperator);
	}

	public com.liferay.portlet.journal.model.JournalStructure updateStructure(
		long groupId, java.lang.String structureId,
		java.lang.String parentStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureService.updateStructure(groupId, structureId,
			parentStructureId, nameMap, descriptionMap, xsd, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public JournalStructureService getWrappedJournalStructureService() {
		return _journalStructureService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedJournalStructureService(
		JournalStructureService journalStructureService) {
		_journalStructureService = journalStructureService;
	}

	public JournalStructureService getWrappedService() {
		return _journalStructureService;
	}

	public void setWrappedService(
		JournalStructureService journalStructureService) {
		_journalStructureService = journalStructureService;
	}

	private JournalStructureService _journalStructureService;
}
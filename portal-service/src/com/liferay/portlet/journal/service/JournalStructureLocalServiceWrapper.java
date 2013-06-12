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
 * Provides a wrapper for {@link JournalStructureLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see JournalStructureLocalService
 * @generated
 */
public class JournalStructureLocalServiceWrapper
	implements JournalStructureLocalService,
		ServiceWrapper<JournalStructureLocalService> {
	public JournalStructureLocalServiceWrapper(
		JournalStructureLocalService journalStructureLocalService) {
		_journalStructureLocalService = journalStructureLocalService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _journalStructureLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_journalStructureLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalStructure addJournalStructure(
		com.liferay.portlet.journal.model.JournalStructure structure)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.addJournalStructure(structure);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalStructure addStructure(
		long userId, long groupId, java.lang.String structureId,
		boolean autoStructureId, java.lang.String parentStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.addStructure(userId, groupId,
			structureId, autoStructureId, parentStructureId, nameMap,
			descriptionMap, xsd, serviceContext);
	}

	@Override
	public void addStructureResources(
		com.liferay.portlet.journal.model.JournalStructure structure,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalStructureLocalService.addStructureResources(structure,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addStructureResources(
		com.liferay.portlet.journal.model.JournalStructure structure,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalStructureLocalService.addStructureResources(structure,
			groupPermissions, guestPermissions);
	}

	@Override
	public void addStructureResources(long groupId,
		java.lang.String structureId, boolean addGroupPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalStructureLocalService.addStructureResources(groupId,
			structureId, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addStructureResources(long groupId,
		java.lang.String structureId, java.lang.String[] groupPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalStructureLocalService.addStructureResources(groupId,
			structureId, groupPermissions, guestPermissions);
	}

	@Override
	public void checkNewLine(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalStructureLocalService.checkNewLine(groupId, structureId);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalStructure copyStructure(
		long userId, long groupId, java.lang.String oldStructureId,
		java.lang.String newStructureId, boolean autoStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.copyStructure(userId, groupId,
			oldStructureId, newStructureId, autoStructureId);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalStructure createJournalStructure(
		long id) throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.createJournalStructure(id);
	}

	@Override
	public void deleteStructure(
		com.liferay.portlet.journal.model.JournalStructure structure)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalStructureLocalService.deleteStructure(structure);
	}

	@Override
	public void deleteStructure(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalStructureLocalService.deleteStructure(groupId, structureId);
	}

	@Override
	public void deleteStructures(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalStructureLocalService.deleteStructures(groupId);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalStructure fetchStructure(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.fetchStructure(groupId, structureId);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.findAll();
	}

	@Override
	public com.liferay.portlet.journal.model.JournalStructure getStructure(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.getStructure(groupId, structureId);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalStructure getStructure(
		long groupId, java.lang.String structureId,
		boolean includeGlobalStructures)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.getStructure(groupId, structureId,
			includeGlobalStructures);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> getStructures()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.getStructures();
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> getStructures(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.getStructures(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> getStructures(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.getStructures(groupId, start, end);
	}

	@Override
	public int getStructuresCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.getStructuresCount(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> search(
		long companyId, long[] groupIds, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.search(companyId, groupIds,
			keywords, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> search(
		long companyId, long[] groupIds, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.search(companyId, groupIds,
			structureId, name, description, andOperator, start, end, obc);
	}

	@Override
	public int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.searchCount(companyId, groupIds,
			keywords);
	}

	@Override
	public int searchCount(long companyId, long[] groupIds,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.searchCount(companyId, groupIds,
			structureId, name, description, andOperator);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalStructure updateJournalStructure(
		com.liferay.portlet.journal.model.JournalStructure structure)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.updateJournalStructure(structure);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalStructure updateStructure(
		long groupId, java.lang.String structureId,
		java.lang.String parentStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalStructureLocalService.updateStructure(groupId,
			structureId, parentStructureId, nameMap, descriptionMap, xsd,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public JournalStructureLocalService getWrappedJournalStructureLocalService() {
		return _journalStructureLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedJournalStructureLocalService(
		JournalStructureLocalService journalStructureLocalService) {
		_journalStructureLocalService = journalStructureLocalService;
	}

	@Override
	public JournalStructureLocalService getWrappedService() {
		return _journalStructureLocalService;
	}

	@Override
	public void setWrappedService(
		JournalStructureLocalService journalStructureLocalService) {
		_journalStructureLocalService = journalStructureLocalService;
	}

	private JournalStructureLocalService _journalStructureLocalService;
}
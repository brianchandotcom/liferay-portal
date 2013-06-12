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
 * Provides a wrapper for {@link JournalTemplateLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see JournalTemplateLocalService
 * @deprecated As of 6.2.0, see LPS-35112
 * @generated
 */
public class JournalTemplateLocalServiceWrapper
	implements JournalTemplateLocalService,
		ServiceWrapper<JournalTemplateLocalService> {
	public JournalTemplateLocalServiceWrapper(
		JournalTemplateLocalService journalTemplateLocalService) {
		_journalTemplateLocalService = journalTemplateLocalService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _journalTemplateLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_journalTemplateLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalTemplate addJournalTemplate(
		com.liferay.portlet.journal.model.JournalTemplate template)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.addJournalTemplate(template);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		long userId, long groupId, java.lang.String templateId,
		boolean autoTemplateId, java.lang.String structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsl, boolean formatXsl, java.lang.String langType,
		boolean cacheable, boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallImageFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.addTemplate(userId, groupId,
			templateId, autoTemplateId, structureId, nameMap, descriptionMap,
			xsl, formatXsl, langType, cacheable, smallImage, smallImageURL,
			smallImageFile, serviceContext);
	}

	@Override
	public void addTemplateResources(
		com.liferay.portlet.journal.model.JournalTemplate template,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalTemplateLocalService.addTemplateResources(template,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addTemplateResources(
		com.liferay.portlet.journal.model.JournalTemplate template,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalTemplateLocalService.addTemplateResources(template,
			groupPermissions, guestPermissions);
	}

	@Override
	public void addTemplateResources(long groupId, java.lang.String templateId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalTemplateLocalService.addTemplateResources(groupId, templateId,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addTemplateResources(long groupId, java.lang.String templateId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalTemplateLocalService.addTemplateResources(groupId, templateId,
			groupPermissions, guestPermissions);
	}

	@Override
	public void checkNewLine(long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalTemplateLocalService.checkNewLine(groupId, templateId);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalTemplate copyTemplate(
		long userId, long groupId, java.lang.String oldTemplateId,
		java.lang.String newTemplateId, boolean autoTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.copyTemplate(userId, groupId,
			oldTemplateId, newTemplateId, autoTemplateId);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalTemplate createJournalTemplate(
		long id) {
		return _journalTemplateLocalService.createJournalTemplate(id);
	}

	@Override
	public void deleteTemplate(
		com.liferay.portlet.journal.model.JournalTemplate template)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalTemplateLocalService.deleteTemplate(template);
	}

	@Override
	public void deleteTemplate(long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalTemplateLocalService.deleteTemplate(groupId, templateId);
	}

	@Override
	public void deleteTemplates(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalTemplateLocalService.deleteTemplates(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getStructureTemplates(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.getStructureTemplates(groupId,
			structureId);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getStructureTemplates(
		long groupId, java.lang.String structureId,
		boolean includeGlobalTemplates)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.getStructureTemplates(groupId,
			structureId, includeGlobalTemplates);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getStructureTemplates(
		long groupId, java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.getStructureTemplates(groupId,
			structureId, start, end);
	}

	@Override
	public int getStructureTemplatesCount(long groupId,
		java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.getStructureTemplatesCount(groupId,
			structureId);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalTemplate getTemplate(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.getTemplate(groupId, templateId);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalTemplate getTemplate(
		long groupId, java.lang.String templateId,
		boolean includeGlobalTemplates)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.getTemplate(groupId, templateId,
			includeGlobalTemplates);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalTemplate getTemplateBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.getTemplateBySmallImageId(smallImageId);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getTemplates()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.getTemplates();
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getTemplates(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.getTemplates(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getTemplates(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.getTemplates(groupId, start, end);
	}

	@Override
	public int getTemplatesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.getTemplatesCount(groupId);
	}

	@Override
	public boolean hasTemplate(long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.hasTemplate(groupId, templateId);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> search(
		long companyId, long[] groupIds, java.lang.String keywords,
		java.lang.String structureId, java.lang.String structureIdComparator,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.search(companyId, groupIds,
			keywords, structureId, structureIdComparator, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> search(
		long companyId, long[] groupIds, java.lang.String templateId,
		java.lang.String structureId, java.lang.String structureIdComparator,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.search(companyId, groupIds,
			templateId, structureId, structureIdComparator, name, description,
			andOperator, start, end, obc);
	}

	@Override
	public int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords, java.lang.String structureId,
		java.lang.String structureIdComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.searchCount(companyId, groupIds,
			keywords, structureId, structureIdComparator);
	}

	@Override
	public int searchCount(long companyId, long[] groupIds,
		java.lang.String templateId, java.lang.String structureId,
		java.lang.String structureIdComparator, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.searchCount(companyId, groupIds,
			templateId, structureId, structureIdComparator, name, description,
			andOperator);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalTemplate updateJournalTemplate(
		com.liferay.portlet.journal.model.JournalTemplate template)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.updateJournalTemplate(template);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalTemplate updateTemplate(
		long groupId, java.lang.String templateId,
		java.lang.String structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsl, boolean formatXsl, java.lang.String langType,
		boolean cacheable, boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallImageFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplateLocalService.updateTemplate(groupId, templateId,
			structureId, nameMap, descriptionMap, xsl, formatXsl, langType,
			cacheable, smallImage, smallImageURL, smallImageFile, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public JournalTemplateLocalService getWrappedJournalTemplateLocalService() {
		return _journalTemplateLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedJournalTemplateLocalService(
		JournalTemplateLocalService journalTemplateLocalService) {
		_journalTemplateLocalService = journalTemplateLocalService;
	}

	@Override
	public JournalTemplateLocalService getWrappedService() {
		return _journalTemplateLocalService;
	}

	@Override
	public void setWrappedService(
		JournalTemplateLocalService journalTemplateLocalService) {
		_journalTemplateLocalService = journalTemplateLocalService;
	}

	private JournalTemplateLocalService _journalTemplateLocalService;
}
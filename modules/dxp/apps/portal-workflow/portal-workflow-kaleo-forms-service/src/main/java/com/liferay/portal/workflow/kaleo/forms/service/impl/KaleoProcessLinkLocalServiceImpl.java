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

package com.liferay.portal.workflow.kaleo.forms.service.impl;

import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink;
import com.liferay.portal.workflow.kaleo.forms.service.base.KaleoProcessLinkLocalServiceBaseImpl;

import java.util.List;

/**
 * Provides the local service for accessing, adding, deleting, and updating
 * Kaleo process links.
 *
 * @author Marcellus Tavares
 */
public class KaleoProcessLinkLocalServiceImpl
	extends KaleoProcessLinkLocalServiceBaseImpl {

	/**
	 * Adds a Kaleo process link referencing the Kaleo process.
	 *
	 * @param  kaleoProcessId the primary key of the Kaleo process
	 * @param  workflowTaskName the name of the Kaleo process link's workflow
	 *         task
	 * @param  ddmTemplateId the primary key of the Kaleo process link's DDM
	 *         template
	 * @return the Kaleo process link
	 */
	@Override
	public KaleoProcessLink addKaleoProcessLink(
		long kaleoProcessId, String workflowTaskName, long ddmTemplateId) {

		long kaleoProcessLinkId = counterLocalService.increment();

		KaleoProcessLink kaleoProcessLink = kaleoProcessLinkPersistence.create(
			kaleoProcessLinkId);

		kaleoProcessLink.setKaleoProcessId(kaleoProcessId);
		kaleoProcessLink.setWorkflowTaskName(workflowTaskName);
		kaleoProcessLink.setDDMTemplateId(ddmTemplateId);

		kaleoProcessLinkPersistence.update(kaleoProcessLink);

		_ddmTemplateLinkLocalService.addTemplateLink(
			classNameLocalService.getClassNameId(KaleoProcessLink.class),
			kaleoProcessLinkId, ddmTemplateId);

		return kaleoProcessLink;
	}

	/**
	 * Deletes the Kaleo process links associated with the Kaleo process and
	 * their resources.
	 *
	 * @param kaleoProcessId the primary key of the Kaleo process from which to
	 *        delete Kaleo process links
	 */
	@Override
	public void deleteKaleoProcessLinks(long kaleoProcessId) {
		List<KaleoProcessLink> kaleoProcessLinks =
			kaleoProcessLinkPersistence.findByKaleoProcessId(kaleoProcessId);

		for (KaleoProcessLink kaleoProcessLink : kaleoProcessLinks) {
			deleteKaleoProcessLink(kaleoProcessLink);

			_ddmTemplateLinkLocalService.deleteTemplateLink(
				classNameLocalService.getClassNameId(KaleoProcessLink.class),
				kaleoProcessLink.getKaleoProcessLinkId());
		}
	}

	/**
	 * Returns the Kaleo process link matching the Kaleo process and workflow
	 * task name.
	 *
	 * @param  kaleoProcessId the primary key of the Kaleo process link's Kaleo
	 *         process
	 * @param  workflowTaskName the name of the Kaleo process link's workflow
	 *         task
	 * @return the Kaleo process link matching the Kaleo process and workflow
	 *         task name, or <code>null</code> if a matching Kaleo process link
	 *         could not be found
	 */
	@Override
	public KaleoProcessLink fetchKaleoProcessLink(
		long kaleoProcessId, String workflowTaskName) {

		return kaleoProcessLinkPersistence.fetchByKPI_WTN(
			kaleoProcessId, workflowTaskName);
	}

	/**
	 * Returns the Kaleo process links matching the Kaleo process.
	 *
	 * @param  kaleoProcessId the primary key of the Kaleo process link's Kaleo
	 *         process
	 * @return the Kaleo process links matching the Kaleo process, or
	 *         <code>null</code> if a matching Kaleo process link could not be
	 *         found
	 */
	@Override
	public List<KaleoProcessLink> getKaleoProcessLinks(long kaleoProcessId) {
		return kaleoProcessLinkPersistence.findByKaleoProcessId(kaleoProcessId);
	}

	/**
	 * Updates the Kaleo process link, setting the primary key of the associated
	 * Kaleo process.
	 *
	 * @param  kaleoProcessLinkId the primary key of the Kaleo process link
	 * @param  kaleoProcessId the primary key of the Kaleo process link's Kaleo
	 *         process
	 * @return the Kaleo process link
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public KaleoProcessLink updateKaleoProcessLink(
			long kaleoProcessLinkId, long kaleoProcessId)
		throws PortalException {

		KaleoProcessLink kaleoProcessLink =
			kaleoProcessLinkPersistence.findByPrimaryKey(kaleoProcessLinkId);

		kaleoProcessLink.setKaleoProcessId(kaleoProcessId);

		kaleoProcessLinkPersistence.update(kaleoProcessLink);

		return kaleoProcessLink;
	}

	/**
	 * Updates the Kaleo process link, replacing its values with new ones. New
	 * values are set for the primary key of the associated Kaleo process, the
	 * name of the associated workflow task, and the primary key of the
	 * associated DDM Template.
	 *
	 * @param  kaleoProcessLinkId the primary key of the Kaleo process link
	 * @param  kaleoProcessId the primary key of the Kaleo process link's Kaleo
	 *         process
	 * @param  workflowTaskName the name of the Kaleo process link's workflow
	 *         task
	 * @param  ddmTemplateId the primary key of the Kaleo process link's DDM
	 *         template
	 * @return the Kaleo process link
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public KaleoProcessLink updateKaleoProcessLink(
			long kaleoProcessLinkId, long kaleoProcessId,
			String workflowTaskName, long ddmTemplateId)
		throws PortalException {

		KaleoProcessLink kaleoProcessLink =
			kaleoProcessLinkPersistence.findByPrimaryKey(kaleoProcessLinkId);

		kaleoProcessLink.setKaleoProcessId(kaleoProcessId);
		kaleoProcessLink.setWorkflowTaskName(workflowTaskName);
		kaleoProcessLink.setDDMTemplateId(ddmTemplateId);

		kaleoProcessLinkPersistence.update(kaleoProcessLink);

		_ddmTemplateLinkLocalService.updateTemplateLink(
			classNameLocalService.getClassNameId(KaleoProcessLink.class),
			kaleoProcessLink.getKaleoProcessLinkId(), ddmTemplateId);

		return kaleoProcessLink;
	}

	/**
	 * Updates the Kaleo process link. If no Kaleo process link is found
	 * matching the primary key of the Kaleo process and the workflow task name,
	 * a new link is created.
	 *
	 * @param  kaleoProcessId the primary key of the Kaleo process link's Kaleo
	 *         process
	 * @param  workflowTaskName the name of the Kaleo process link's workflow
	 *         task
	 * @param  ddmTemplateId the primary key of the Kaleo process link's DDM
	 *         template
	 * @return the Kaleo process link
	 */
	@Override
	public KaleoProcessLink updateKaleoProcessLink(
		long kaleoProcessId, String workflowTaskName, long ddmTemplateId) {

		KaleoProcessLink kaleoProcessLink =
			kaleoProcessLinkPersistence.fetchByKPI_WTN(
				kaleoProcessId, workflowTaskName);

		if (kaleoProcessLink == null) {
			return addKaleoProcessLink(
				kaleoProcessId, workflowTaskName, ddmTemplateId);
		}

		kaleoProcessLink.setDDMTemplateId(ddmTemplateId);

		kaleoProcessLinkPersistence.update(kaleoProcessLink);

		_ddmTemplateLinkLocalService.updateTemplateLink(
			classNameLocalService.getClassNameId(KaleoProcessLink.class),
			kaleoProcessLink.getKaleoProcessLinkId(), ddmTemplateId);

		return kaleoProcessLink;
	}

	@ServiceReference(type = DDMTemplateLinkLocalService.class)
	private DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;

}
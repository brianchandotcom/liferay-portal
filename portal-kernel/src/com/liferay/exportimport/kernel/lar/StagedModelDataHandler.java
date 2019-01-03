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

package com.liferay.exportimport.kernel.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

/**
 * @author Máté Thurzó
 * @author Daniel Kocsis
 * @author Zsolt Berentey
 */
public interface StagedModelDataHandler<T extends StagedModel> {

	/**
	 * Deletes the staged model matching the parameters. This method is called
	 * when deletions are imported.
	 *
	 * @param  uuid the staged model's UUID
	 * @param  groupId the primary key of the entity's group
	 * @param  className the staged model's class name
	 * @param  extraData the extra data containing useful information about the
	 *         staged model. This information makes the staged model easier to
	 *         identify and fetch for deletion. It is populated when a deletion
	 *         system event is added, usually in the
	 *         <code>*LocalServiceImpl</code> class of the model.
	 * @throws PortalException if a portal exception occurred
	 */
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException;

	/**
	 * Deletes the staged model. This method is called when deletions are
	 * imported.
	 *
	 * @param  stagedModel the staged model to delete
	 * @throws PortalException if a portal exception occurred
	 */
	public void deleteStagedModel(T stagedModel) throws PortalException;

	/**
	 * Exports the staged model and its references. See the {@link
	 * BaseStagedModelDataHandler#exportStagedModel(PortletDataContext, T)}
	 * method for a reference implementation. Refrain from overriding this
	 * method; instead, override the {@link
	 * BaseStagedModelDataHandler#doExportStagedModel(PortletDataContext, T)}
	 * method.
	 *
	 * @param  portletDataContext the portlet data context of the current
	 *         process
	 * @param  stagedModel the staged model to export
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public void exportStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException;

	/**
	 * Returns a reference of the staged model. This method is typically used in
	 * validation and import methods.
	 *
	 * @param  uuid the reference's UUID
	 * @param  groupId the primary key of the group
	 * @return a reference of the staged model
	 */
	public T fetchMissingReference(String uuid, long groupId);

	/**
	 * Returns the staged model with the UUID and group. This method is used in
	 * cases with grouped models.
	 *
	 * @param  uuid the staged model's UUID
	 * @param  groupId the primary key of the group
	 * @return the staged model with the UUID and group
	 */
	public T fetchStagedModelByUuidAndGroupId(String uuid, long groupId);

	/**
	 * Returns the staged models with the UUID and company.
	 *
	 * @param  uuid the staged model's UUID
	 * @param  companyId the primary key of the company
	 * @return the staged models with the UUID and company
	 */
	public List<T> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns the class names of the models the data handler handles.
	 *
	 * @return the class names of the models the data handler handles
	 */
	public String[] getClassNames();

	/**
	 * Returns the staged model's display name. The display name is presented in
	 * the UI so users can follow the export/import process.
	 *
	 * @param  stagedModel the staged model from which to extract the display
	 *         name
	 * @return the staged model's display name
	 */
	public String getDisplayName(T stagedModel);

	/**
	 * Returns the workflow statuses that are used as filters during the export
	 * process.
	 *
	 * @return the workflow statuses that are used as filters during the export
	 *         process
	 */
	public int[] getExportableStatuses();

	/**
	 * Returns the attributes that are automatically merged into the XML element
	 * of the staged model reference. These attributes are available during the
	 * import process in, for example, the {@link
	 * #importMissingReference(PortletDataContext, Element)} and {@link
	 * #validateReference(PortletDataContext, Element)} methods.
	 *
	 * @param  portletDataContext the current process's portlet data context
	 * @param  stagedModel the staged model for which to get attributes
	 * @return the attributes for the staged model
	 */
	public Map<String, String> getReferenceAttributes(
		PortletDataContext portletDataContext, T stagedModel);

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #importMissingReference(PortletDataContext, Element)}
	 */
	@Deprecated
	public void importCompanyStagedModel(
			PortletDataContext portletDataContext, Element element)
		throws PortletDataException;

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #importMissingReference(PortletDataContext, String, long,
	 *             long)}
	 */
	@Deprecated
	public void importCompanyStagedModel(
			PortletDataContext portletDataContext, String uuid, long classPK)
		throws PortletDataException;

	/**
	 * Maps the ID of the existing staged model to the old ID in the reference
	 * element. When a reference is exported as missing, the Data Handler
	 * framework calls this method during the import process and updates the new
	 * primary key map in the portlet data context.
	 *
	 * @param  portletDataContext the portlet data context of the current
	 *         process
	 * @param  referenceElement the XML element that contains information about
	 *         the staged model reference
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public void importMissingReference(
			PortletDataContext portletDataContext, Element referenceElement)
		throws PortletDataException;

	/**
	 * Maps the ID of the existing staged model to the old ID in the reference
	 * element. When the staged model data handler instance extends {@link
	 * BaseStagedModelDataHandler}, this method is called to override the {@link
	 * BaseStagedModelDataHandler#importMissingReference(PortletDataContext,
	 * Element)} and {@link
	 * BaseStagedModelDataHandler#doImportMissingReference(PortletDataContext,
	 * Element)} methods.
	 *
	 * @param  portletDataContext the portlet data context of the current
	 *         process
	 * @param  uuid the staged model's UUID from the reference element
	 * @param  groupId the primary key of the entity's group from the reference
	 *         element
	 * @param  classPK the class primary key of the staged model from the
	 *         reference element
	 * @throws PortletDataException if a portlet data exception occurred
	 * @see    #importMissingReference(PortletDataContext, Element)
	 */
	public void importMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long classPK)
		throws PortletDataException;

	/**
	 * Imports the staged model. All the staged model's references must be
	 * imported before this method is called. See the {@link
	 * BaseStagedModelDataHandler#importStagedModel(PortletDataContext,
	 * StagedModel)} method for a reference implementation. Refrain from
	 * overriding this method; instead, override the {@link
	 * BaseStagedModelDataHandler#doImportStagedModel(PortletDataContext, T)}
	 * method.
	 *
	 * @param  portletDataContext the portlet data context of the current
	 *         process
	 * @param  stagedModel the staged model to import
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public void importStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException;

	/**
	 * Restores the staged model from the trash. This method is called during
	 * the import process to ensure the imported staged model is not in the
	 * trash. See the {@link
	 * BaseStagedModelDataHandler#restoreStagedModel(PortletDataContext,
	 * StagedModel)} method for a reference implementation. Refrain from
	 * overriding this method; instead, override the {@link
	 * BaseStagedModelDataHandler#doRestoreStagedModel(PortletDataContext,
	 * StagedModel)} method.
	 *
	 * @param  portletDataContext the portlet data context of the current
	 *         process
	 * @param  stagedModel the staged model to restore from the trash
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public void restoreStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException;

	/**
	 * Returns <code>true</code> if the staged model reference validation is
	 * successful.
	 *
	 * @param  portletDataContext the portlet data context of the current
	 *         process
	 * @param  referenceElement the XML element that contains information about
	 *         the reference
	 * @return <code>true</code> if the reference validation is successful;
	 *         <code>false</code> otherwise
	 */
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement);

}
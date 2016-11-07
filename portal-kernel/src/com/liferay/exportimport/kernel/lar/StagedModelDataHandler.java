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
 * @author Mate Thurzo
 * @author Daniel Kocsis
 * @author Zsolt Berentey
 */
public interface StagedModelDataHandler<T extends StagedModel> {

	/**
	 * Deletes a staged model based on the parameters. It is called when
	 * deletions are imported.
	 *
	 * @param  uuid the uuid of the staged model
	 * @param  groupId the group ID of the entity's group
	 * @param  className the name of the staged model class
	 * @param  extraData can be any useful information about the staged model.
	 *         This information makes the staged model easier to identify /
	 *         fetch for deletion. It is populated when a deletion system event
	 *         is added usually in the <code>*LocalServiceImpl</code> class of
	 *         the model.
	 * @throws PortalException
	 */
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException;

	/**
	 * Deletes the given staged model
	 *
	 * @param  stagedModel staged model to be deleted
	 * @throws PortalException
	 */
	public void deleteStagedModel(T stagedModel) throws PortalException;

	/**
	 * Exports a given staged model and its references. See {@link
	 * BaseStagedModelDataHandler#exportStagedModel(PortletDataContext, T)} for
	 * reference implementation. Normally this method should not be overridden,
	 * but {@link
	 * BaseStagedModelDataHandler#doExportStagedModel(PortletDataContext, T)}.
	 *
	 * @param  portletDataContext the portlet data context of the current
	 *         process
	 * @param  stagedModel staged model to be exported
	 * @throws PortletDataException
	 */
	public void exportStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException;

	/**
	 * Returns a reference of this staged model. Usually used in validation and
	 * import methods.
	 *
	 * @param  uuid uuid of the reference
	 * @param  groupId the primary key of the group
	 * @return a reference of this staged model
	 */
	public T fetchMissingReference(String uuid, long groupId);

	/**
	 * Returns a staged model based on the parameters. Needs to be implemented
	 * in case of grouped models.
	 *
	 * @param  uuid uuid of the staged model
	 * @param  groupId the primary key of the group
	 * @return a staged model based on the parameters
	 */
	public T fetchStagedModelByUuidAndGroupId(String uuid, long groupId);

	/**
	 * Returns a staged model based on the parameters. Always needs to be
	 * implemented.
	 *
	 * @param  uuid uuid of the staged model
	 * @param  companyId the primary key of the company
	 * @return a staged model based on the parameters
	 */
	public List<T> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns the name of the classes of the models this data handler handles.
	 *
	 * @return the name of the classes of the models this data handler handles
	 */
	public String[] getClassNames();

	/**
	 * Returns the display name of a given staged model. This is used on the UI
	 * so the users can follow what the export/import process is doing.
	 *
	 * @param  stagedModel
	 * @return the display name of a given staged model
	 */
	public String getDisplayName(T stagedModel);

	/**
	 * Returns an array of statuses that are automatically used as a filter
	 * during the export process
	 *
	 * @return an array of statuses that are automatically used as a filter
	 *         during the export process
	 */
	public int[] getExportableStatuses();

	/**
	 * Returns a Map of attributes that are automatically merged to the XML
	 * element of the reference. These attributes will be available during the
	 * import process in e.g. {@link #importMissingReference(PortletDataContext,
	 * Element)} or {@link #validateReference(PortletDataContext, Element)}.
	 *
	 * @param portletDataContext the portlet data context of the current process
	 * @param stagedModel
	 * @return
	 */
	public Map<String, String> getReferenceAttributes(
		PortletDataContext portletDataContext, T stagedModel);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #importMissingReference(PortletDataContext, Element)}
	 */
	@Deprecated
	public void importCompanyStagedModel(
			PortletDataContext portletDataContext, Element element)
		throws PortletDataException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #importMissingReference(PortletDataContext, String, long,
	 *             long)}
	 */
	@Deprecated
	public void importCompanyStagedModel(
			PortletDataContext portletDataContext, String uuid, long classPK)
		throws PortletDataException;

	/**
	 * "Imports" missing reference. When a reference is exported as missing, the
	 * framework calls this method during the import process. This method
	 * updates the new primary key map in <code>portletDataContext</code>. In
	 * other words it maps the ID of the existing staged model to the old ID
	 * which is in the <code>referenceElement</code>.
	 *
	 * @param  portletDataContext the portlet data context of the current
	 *         process
	 * @param  referenceElement XML element that contains information about the
	 *         reference
	 * @throws PortletDataException
	 */
	public void importMissingReference(
			PortletDataContext portletDataContext, Element referenceElement)
		throws PortletDataException;

	/**
	 * When this <code>StagedModelDataHandler</code> extends {@link
	 * BaseStagedModelDataHandler}, {@link
	 * BaseStagedModelDataHandler#importMissingReference(PortletDataContext,
	 * Element)} and {@link
	 * BaseStagedModelDataHandler#doImportMissingReference(PortletDataContext,
	 * Element)} are not overridden, this method is called.
	 *
	 * @param  portletDataContext
	 * @param  uuid the uuid of the staged model coming from the reference
	 *         element
	 * @param  groupId the group ID of the entity's group coming from the
	 *         reference element
	 * @param  classPK the class primary key of the staged model coming from the
	 *         reference element
	 * @throws PortletDataException
	 */
	public void importMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long classPK)
		throws PortletDataException;

	/**
	 * Imports a given staged model. All its references must be imported
	 * beforehand. See {@link
	 * BaseStagedModelDataHandler#importStagedModel(PortletDataContext,
	 * StagedModel)} for reference implementation. Normally this method should
	 * not be overridden, but {@link
	 * BaseStagedModelDataHandler#doImportStagedModel(PortletDataContext, T)}.
	 *
	 * @param  portletDataContext the portlet data context of the current
	 *         process
	 * @param  stagedModel staged model to be imported
	 * @throws PortletDataException
	 */
	public void importStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException;

	/**
	 * Restores a given staged model from Trash. This method is called during
	 * the import process to make sure that the imported staged model is not in
	 * the Trash. See {@link
	 * BaseStagedModelDataHandler#restoreStagedModel(PortletDataContext,
	 * StagedModel)} for reference implementation. Normally this method should
	 * not be overridden, but {@link
	 * BaseStagedModelDataHandler#doRestoreStagedModel(PortletDataContext,
	 * StagedModel)}.
	 *
	 * @param  portletDataContext the portlet data context of the current
	 *         process
	 * @param  stagedModel staged model to be restored from Trash
	 * @throws PortletDataException
	 */
	public void restoreStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException;

	/**
	 * Validates a given reference. Returns <code>true</code> if the validation
	 * successful.
	 *
	 * @param  portletDataContext the portlet data context of the current
	 *         process
	 * @param  referenceElement XML element that contains information about the
	 *         reference
	 * @return <code>true</code> if the validation successful
	 */
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement);

}
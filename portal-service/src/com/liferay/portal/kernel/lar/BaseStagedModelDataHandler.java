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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.model.WorkflowedModel;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 * @author Zsolt Berentey
 */
public abstract class BaseStagedModelDataHandler<T extends StagedModel>
	implements StagedModelDataHandler<T> {

	@Override
	public abstract void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException;

	@Override
	public void exportStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		if (!isExportable(portletDataContext, stagedModel)) {
			return;
		}

		try {
			ManifestSummary manifestSummary =
				portletDataContext.getManifestSummary();

			PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
				"stagedModel", stagedModel, manifestSummary);

			doExportStagedModel(portletDataContext, (T)stagedModel.clone());

			if (countStagedModel(portletDataContext, stagedModel)) {
				manifestSummary.incrementModelAdditionCount(
					stagedModel.getStagedModelType());
			}
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public abstract String[] getClassNames();

	@Override
	public String getDisplayName(T stagedModel) {
		return stagedModel.getUuid();
	}

	@Override
	public int[] getExportableStatuses() {
		return new int[] {WorkflowConstants.STATUS_APPROVED};
	}

	@Override
	public void importCompanyStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		try {
			doImportCompanyStagedModel(portletDataContext, stagedModel);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public void importStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		String path = ExportImportPathUtil.getModelPath(stagedModel);

		if (portletDataContext.isPathProcessed(path)) {
			return;
		}

		try {
			ManifestSummary manifestSummary =
				portletDataContext.getManifestSummary();

			PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
				"stagedModel", stagedModel, manifestSummary);

			if (stagedModel instanceof TrashedModel) {
				restoreStagedModel(portletDataContext, stagedModel);
			}

			doImportStagedModel(portletDataContext, stagedModel);

			manifestSummary.incrementModelAdditionCount(
				stagedModel.getStagedModelType());
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		try {
			doRestoreStagedModel(portletDataContext, stagedModel);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element rootElement,
		Element referenceElement) {

		String elementName = referenceElement.getName();

		if (elementName.equals("missing-reference")) {
			String uuid = referenceElement.attributeValue("uuid");

			try {
				boolean valid = validateMissingReference(
					uuid, portletDataContext.getCompanyId(),
					portletDataContext.getScopeGroupId());

				if (!valid) {
					valid = validateMissingReference(
						uuid, portletDataContext.getCompanyId(),
						portletDataContext.getCompanyGroupId());
				}

				return valid;
			}
			catch (Exception e) {
				return false;
			}
		}

		return true;
	}

	protected boolean countStagedModel(
		PortletDataContext portletDataContext, T stagedModel) {

		return !portletDataContext.isStagedModelCounted(stagedModel);
	}

	protected abstract void doExportStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws Exception;

	protected void doImportCompanyStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	protected abstract void doImportStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws Exception;

	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	protected boolean isExportable(
		PortletDataContext portletDataContext, T stagedModel) {

		String path = ExportImportPathUtil.getModelPath(stagedModel);

		if (portletDataContext.isPathExportedInScope(path)) {
			return false;
		}

		if (stagedModel instanceof WorkflowedModel) {
			WorkflowedModel workflowedModel = (WorkflowedModel)stagedModel;

			if (!ArrayUtil.contains(
					getExportableStatuses(), workflowedModel.getStatus())) {

				return false;
			}
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			stagedModel.getModelClassName());

		if (trashHandler != null) {
			try {
				long classPK = (Long)stagedModel.getPrimaryKeyObj();

				if (trashHandler.isInTrash(classPK) ||
					trashHandler.isInTrashContainer(classPK)) {

					return false;
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to check trash status for " +
							stagedModel.getModelClassName());
				}
			}
		}

		return true;
	}

	protected boolean validateMissingReference(
			String uuid, long companyId, long groupId)
		throws Exception {

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(
		BaseStagedModelDataHandler.class);

}
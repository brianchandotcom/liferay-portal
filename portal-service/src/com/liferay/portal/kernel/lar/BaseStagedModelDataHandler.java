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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.model.WorkflowedModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 * @author Zsolt Berentey
 */
public abstract class BaseStagedModelDataHandler<T extends StagedModel>
	implements StagedModelDataHandler<T> {

	public static final int STAGING_HIBERNATE_CACHE_FLUSH_FREQUENCY =
		GetterUtil.getInteger(PropsUtil.get(
			"staging.hibernate.cache.flush.frequency"));

	@Override
	public abstract void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException;

	@Override
	public void exportStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		validateExport(portletDataContext, stagedModel);

		String path = ExportImportPathUtil.getModelPath(stagedModel);

		if (portletDataContext.isPathExportedInScope(path)) {
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

			portletDataContext.cleanUpMissingReferences(stagedModel);
		}
		catch (PortletDataException pde) {
			throw pde;
		}
		catch (Exception e) {
			PortletDataException pde = new PortletDataException(e);

			if (e instanceof NoSuchModelException) {
				pde.setStagedModel(stagedModel);
				pde.setType(PortletDataException.MISSING_DEPENDENCY);
			}

			throw pde;
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
	public Map<String, String> getReferenceAttributes(
		PortletDataContext portletDataContext, T stagedModel) {

		return new HashMap<String, String>();
	}

	@Override
	public void importCompanyStagedModel(
			PortletDataContext portletDataContext, Element element)
		throws PortletDataException {

		String uuid = element.attributeValue("uuid");
		long classPK = GetterUtil.getLong(element.attributeValue("class-pk"));

		importCompanyStagedModel(portletDataContext, uuid, classPK);
	}

	@Override
	public void importCompanyStagedModel(
			PortletDataContext portletDataContext, String uuid, long classPK)
		throws PortletDataException {

		try {
			doImportCompanyStagedModel(portletDataContext, uuid, classPK);
		}
		catch (PortletDataException pde) {
			throw pde;
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

			restoreStagedModel(portletDataContext, stagedModel);

			doImportStagedModel(portletDataContext, stagedModel);

			manifestSummary.incrementModelAdditionCount(
				stagedModel.getStagedModelType());

			maintainSessionCache(portletDataContext);
		}
		catch (PortletDataException pde) {
			throw pde;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected void maintainSessionCache(PortletDataContext portletDataContext) {
		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		int hibernateCacheFlushFrequency = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.STAGING_HIBERNATE_CACHE_FLUSH_FREQUENCY));

		if ((manifestSummary.getModelAdditionCount() %
				hibernateCacheFlushFrequency) != 0) {

			return;
		}

		Session session = sessionFactory.getCurrentSession();

		session.flush();

		session.clear();
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		try {
			if (stagedModel instanceof TrashedModel) {
				doRestoreStagedModel(portletDataContext, stagedModel);
			}
		}
		catch (PortletDataException pde) {
			throw pde;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

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

	protected boolean countStagedModel(
		PortletDataContext portletDataContext, T stagedModel) {

		return !portletDataContext.isStagedModelCounted(stagedModel);
	}

	protected abstract void doExportStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws Exception;

	protected void doImportCompanyStagedModel(
			PortletDataContext portletDataContext, String uuid, long classPK)
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

	protected void validateExport(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		if (!portletDataContext.isInitialPublication() &&
			(stagedModel instanceof WorkflowedModel)) {

			WorkflowedModel workflowedModel = (WorkflowedModel)stagedModel;

			if (!ArrayUtil.contains(
					getExportableStatuses(), workflowedModel.getStatus())) {

				PortletDataException pde = new PortletDataException(
					PortletDataException.STATUS_UNAVAILABLE);

				pde.setStagedModel(stagedModel);

				throw pde;
			}
		}

		if (stagedModel instanceof TrashedModel) {
			TrashedModel trashedModel = (TrashedModel)stagedModel;

			if (trashedModel.isInTrash()) {
				PortletDataException pde = new PortletDataException(
					PortletDataException.STATUS_IN_TRASH);

				pde.setStagedModel(stagedModel);

				throw pde;
			}
		}
	}

	protected boolean validateMissingReference(
			String uuid, long companyId, long groupId)
		throws Exception {

		return true;
	}

	protected final SessionFactory sessionFactory =
		(SessionFactory)PortalBeanLocatorUtil.locate("liferaySessionFactory");

}
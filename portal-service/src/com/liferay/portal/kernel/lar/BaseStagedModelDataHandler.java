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

import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 * @author Zsolt Berentey
 */
public abstract class BaseStagedModelDataHandler<T extends StagedModel>
	implements StagedModelDataHandler<T> {

	@Override
	public void exportStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		String path = ExportImportPathUtil.getModelPath(stagedModel);

		if (portletDataContext.isPathExportedInScope(path)) {
			return;
		}

		try {
			doExportStagedModel(portletDataContext, (T)stagedModel.clone());

			if (countStagedModel(portletDataContext, stagedModel)) {
				ManifestSummary manifestSummary =
					portletDataContext.getManifestSummary();

				manifestSummary.incrementModelAdditionCount(
					getManifestSummaryKey(stagedModel));
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
	public String getManifestSummaryKey(StagedModel stagedModel) {
		if (stagedModel == null) {
			return getClassNames()[0];
		}

		return stagedModel.getModelClassName();
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
			doImportStagedModel(portletDataContext, stagedModel);

			ManifestSummary manifestSummary =
				portletDataContext.getManifestSummary();

			manifestSummary.incrementModelAdditionCount(
				getManifestSummaryKey(stagedModel));
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

			return validateMissingReference(
				uuid, portletDataContext.getCompanyId(),
				portletDataContext.getScopeGroupId());
		}

		return true;
	}

	protected boolean countStagedModel(
		PortletDataContext portletDataContext, T stagedModel) {

		return true;
	}

	protected abstract void doExportStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws Exception;

	protected abstract void doImportStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws Exception;

	protected boolean validateMissingReference(
		String uuid, long companyId, long groupId) {

		return true;
	}

}
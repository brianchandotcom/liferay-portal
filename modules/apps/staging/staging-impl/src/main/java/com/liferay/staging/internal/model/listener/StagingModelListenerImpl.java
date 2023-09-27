/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.internal.model.listener;

import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.version.VersionedModel;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(service = StagingModelListener.class)
public class StagingModelListenerImpl<T extends BaseModel<T>>
	implements StagingModelListener<T> {

	@Override
	public void onAfterCreate(T model) throws ModelListenerException {
		if (!_checkVersionedModel(model)) {
			return;
		}

		try {
			_staging.addModelToChangesetCollection(model);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to add created model to the changeset",
				portalException);
		}
	}

	@Override
	public void onAfterRemove(T model) throws ModelListenerException {
		if (!_checkVersionedModel(model)) {
			return;
		}

		try {
			_staging.removeModelFromChangesetCollection(model);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to remove model from the changeset", portalException);
		}
	}

	@Override
	public void onAfterUpdate(T model) throws ModelListenerException {
		if (!_checkVersionedModel(model)) {
			return;
		}

		try {
			_staging.addModelToChangesetCollection(model);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to add updated model to the changeset",
				portalException);
		}
	}

	private boolean _checkVersionedModel(T model) {
		if (model == null) {
			return false;
		}

		boolean checkedModel = false;

		if (model instanceof VersionedModel) {
			VersionedModel versionedModel = (VersionedModel)model;

			if (versionedModel.isHead()) {
				checkedModel = true;
			}
		}
		else {
			checkedModel = true;
		}

		return checkedModel;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingModelListenerImpl.class);

	@Reference
	private Staging _staging;

}
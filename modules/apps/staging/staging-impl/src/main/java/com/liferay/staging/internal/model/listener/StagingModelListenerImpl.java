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
@Component(immediate = true, service = StagingModelListener.class)
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
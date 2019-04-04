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

package com.liferay.portal.kernel.service.version;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.version.VersionModel;
import com.liferay.portal.kernel.model.version.VersionedModel;

/**
 * @author Preston Crary
 */
public abstract class BaseVersionServiceListener
	<E extends VersionedModel<V>, V extends VersionModel<E>>
		implements VersionServiceListener<E, V> {

	@Override
	public void afterCheckout(E draftVersionedModel, int version)
		throws PortalException {
	}

	@Override
	public void afterCreateDraft(E draftVersionedModel) throws PortalException {
	}

	@Override
	public void afterDelete(E publishedVersionedModel) throws PortalException {
	}

	@Override
	public void afterDeleteDraft(E draftVersionedModel) throws PortalException {
	}

	@Override
	public void afterDeleteVersion(V versionModel) throws PortalException {
	}

	@Override
	public void afterPublishDraft(E draftVersionedModel, int version)
		throws PortalException {
	}

	@Override
	public void afterUpdateDraft(E draftVersionedModel) throws PortalException {
	}

}
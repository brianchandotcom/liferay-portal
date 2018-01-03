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

package com.liferay.user.associated.data.exporter;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.user.associated.data.entity.UADEntity;

import java.util.List;

/**
 * @author William Newbury
 */
public abstract class BaseUADEntityExporter implements UADEntityExporter {

	@Override
	public abstract void export(UADEntity uadEntity) throws PortalException;

	@Override
	public void exportAll(long userId) throws PortalException {
		for (UADEntity uadEntity : getUADEntities(userId)) {
			export(uadEntity);
		}
	}

	protected String getJSON(Object object) {
		return JSONFactoryUtil.looseSerialize(object);
	}

	protected abstract List<UADEntity> getUADEntities(long userId);

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedGroupedModel;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Alexander Chow
 */
public interface RepositoryModel<T> extends Serializable, StagedGroupedModel {

	public void execute(RepositoryModelOperation repositoryModelOperation)
		throws PortalException;

	public Map<String, Serializable> getAttributes();

	public Object getModel();

	public long getPrimaryKey();

	public boolean isEscapedModel();

	public T toEscapedModel();

	public T toUnescapedModel();

}
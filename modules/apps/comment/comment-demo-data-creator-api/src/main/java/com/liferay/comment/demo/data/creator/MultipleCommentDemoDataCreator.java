/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.comment.demo.data.creator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;

/**
 * @author Alejandro Hernández
 */
public interface MultipleCommentDemoDataCreator {

	public void create(ClassedModel classedModel) throws PortalException;

	public void delete() throws PortalException;

}
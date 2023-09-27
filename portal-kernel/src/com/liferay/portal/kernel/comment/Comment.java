/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.comment;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Adolfo Pérez
 */
@ProviderType
public interface Comment {

	public String getBody();

	public String getClassName();

	public long getClassPK();

	public long getCommentId();

	public Date getCreateDate();

	public String getExternalReferenceCode();

	public long getGroupId();

	public Class<?> getModelClass();

	public String getModelClassName();

	public Date getModifiedDate();

	public long getParentCommentId();

	public String getTranslatedBody(String pathThemeImages);

	public User getUser() throws PortalException;

	public long getUserId();

	public String getUserName();

	public String getUuid();

	public boolean isRoot();

}
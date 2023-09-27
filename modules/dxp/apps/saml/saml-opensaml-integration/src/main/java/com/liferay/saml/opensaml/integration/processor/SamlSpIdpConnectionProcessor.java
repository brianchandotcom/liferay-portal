/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.processor;

import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;

/**
 * @author Stian Sigvartsen
 */
public interface SamlSpIdpConnectionProcessor
	extends Processor<SamlSpIdpConnection> {

	public void setFileItemArray(String fieldExpression, FileItem[] fileItems);

}
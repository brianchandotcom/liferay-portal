/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class SourceImpl extends SourceBaseImpl {

	@Override
	public String getAttachmentsDir() {
		return "reports_templates/".concat(String.valueOf(getSourceId()));
	}

	@Override
	public String[] getAttachmentsFiles() throws PortalException {
		return DLStoreUtil.getFileNames(
			getCompanyId(), CompanyConstants.SYSTEM, getAttachmentsDir());
	}

}
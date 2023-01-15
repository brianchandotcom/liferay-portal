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

package com.liferay.document.library.google.docs.internal.verify;

import com.liferay.document.library.google.docs.internal.helper.GoogleDocsDLFileEntryTypeHelper;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.verify.VerifyProcess;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "verify.process.name=com.liferay.document.library.google.docs",
	service = VerifyProcess.class
)
public class DLGoogleDocsVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		_checkGoogleDocsDLFileEntryType();
	}

	private void _checkGoogleDocsDLFileEntryType() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				GoogleDocsDLFileEntryTypeHelper
					googleDocsDLFileEntryTypeHelper =
						new GoogleDocsDLFileEntryTypeHelper(
							company, _defaultDDMStructureHelper,
							_classNameLocalService.getClassNameId(
								DLFileEntryMetadata.class),
							_ddmStructureLocalService,
							_dlFileEntryTypeLocalService, _userLocalService);

				googleDocsDLFileEntryTypeHelper.addGoogleDocsDLFileEntryType(
					false);
			});
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DefaultDDMStructureHelper _defaultDDMStructureHelper;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
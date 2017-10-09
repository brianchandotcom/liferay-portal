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

package com.liferay.document.library.file.rank.internal.service;

import com.liferay.document.library.file.rank.service.DLFileRankLocalService;
import com.liferay.document.library.kernel.service.DLFileRankLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularDLFileRankLocalServiceWrapper
	extends DLFileRankLocalServiceWrapper {

	public ModularDLFileRankLocalServiceWrapper() {
		super(null);
	}

	public ModularDLFileRankLocalServiceWrapper(
		com.liferay.document.library.kernel.service.DLFileRankLocalService
			dlFileRankLocalService) {

		super(dlFileRankLocalService);
	}

	@Reference(unbind = "-")
	protected void setDLFileRankLocalService(
		DLFileRankLocalService dlFileRankLocalService) {

		_dlFileRankLocalService = dlFileRankLocalService;
	}

	private DLFileRankLocalService _dlFileRankLocalService;

}
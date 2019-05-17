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

package com.liferay.adaptive.media.document.library.thumbnails.internal.model.listener;

import com.liferay.adaptive.media.document.library.thumbnails.internal.util.AMCompanyThumbnailConfigurationInitializer;
import com.liferay.adaptive.media.exception.AMImageConfigurationException;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = ModelListener.class)
public class CompanyModelListener extends BaseModelListener<Company> {

	@Override
	public void onAfterCreate(Company company) throws ModelListenerException {
		try {
			_amCompanyThumbnailConfigurationInitializer.initializeCompany(
				company);
		}
		catch (AMImageConfigurationException | IOException e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to automatically create Adaptive Media thumbnail " +
						"configurations",
					e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyModelListener.class);

	@Reference
	private AMCompanyThumbnailConfigurationInitializer
		_amCompanyThumbnailConfigurationInitializer;

}
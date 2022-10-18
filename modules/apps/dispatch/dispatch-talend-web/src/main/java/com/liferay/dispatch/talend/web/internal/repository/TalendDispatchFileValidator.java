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

package com.liferay.dispatch.talend.web.internal.repository;

import com.liferay.dispatch.repository.BaseDispatchFileValidator;
import com.liferay.dispatch.repository.DispatchFileValidator;
import com.liferay.dispatch.repository.exception.DispatchRepositoryException;
import com.liferay.dispatch.talend.web.internal.configuration.DispatchTalendConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.FileUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	configurationPid = "com.liferay.dispatch.talend.web.internal.configuration.DispatchTalendConfiguration",
	property = "dispatch.file.validator.type=" + TalendDispatchFileValidator.FILE_VALIDATOR_TYPE_TALEND,
	service = DispatchFileValidator.class
)
public class TalendDispatchFileValidator extends BaseDispatchFileValidator {

	public static final String FILE_VALIDATOR_TYPE_TALEND = "talend";

	@Override
	public void validateExtension(String fileName)
		throws DispatchRepositoryException {

		if (isValidExtension(
				StringPool.PERIOD + FileUtil.getExtension(fileName),
				_dispatchTalendConfiguration.fileExtensions())) {

			return;
		}

		throw new DispatchRepositoryException(
			"Invalid file extension for " + fileName);
	}

	@Override
	public void validateSize(long size) throws DispatchRepositoryException {
		if (isValidSize(size, _dispatchTalendConfiguration.fileMaxSize())) {
			return;
		}

		throw new DispatchRepositoryException(
			String.format(
				"File size exceeds %d bytes limit",
				_dispatchTalendConfiguration.fileMaxSize()));
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_dispatchTalendConfiguration = ConfigurableUtil.createConfigurable(
			DispatchTalendConfiguration.class, properties);
	}

	private volatile DispatchTalendConfiguration _dispatchTalendConfiguration;

}
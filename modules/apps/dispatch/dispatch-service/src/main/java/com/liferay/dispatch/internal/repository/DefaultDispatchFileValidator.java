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

package com.liferay.dispatch.internal.repository;

import com.liferay.dispatch.configuration.DispatchConfiguration;
import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.repository.BaseDispatchFileValidator;
import com.liferay.dispatch.repository.DispatchFileValidator;
import com.liferay.dispatch.repository.exception.DispatchRepositoryException;
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
	configurationPid = "com.liferay.dispatch.configuration.DispatchConfiguration",
	property = "dispatch.file.validator.type=" + DispatchConstants.FILE_VALIDATOR_TYPE_DEFAULT,
	service = DispatchFileValidator.class
)
public class DefaultDispatchFileValidator extends BaseDispatchFileValidator {

	@Override
	public void validateExtension(String fileName)
		throws DispatchRepositoryException {

		if (isValidExtension(
				StringPool.PERIOD + FileUtil.getExtension(fileName),
				_dispatchConfiguration.fileExtensions())) {

			return;
		}

		throw new DispatchRepositoryException(
			"Invalid file extension for " + fileName);
	}

	@Override
	public void validateSize(long size) throws DispatchRepositoryException {
		if (isValidSize(size, _dispatchConfiguration.fileMaxSize())) {
			return;
		}

		throw new DispatchRepositoryException(
			String.format(
				"File size exceeds %d bytes limit",
				_dispatchConfiguration.fileMaxSize()));
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_dispatchConfiguration = ConfigurableUtil.createConfigurable(
			DispatchConfiguration.class, properties);
	}

	private volatile DispatchConfiguration _dispatchConfiguration;

}
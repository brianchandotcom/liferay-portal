/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
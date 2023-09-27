/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
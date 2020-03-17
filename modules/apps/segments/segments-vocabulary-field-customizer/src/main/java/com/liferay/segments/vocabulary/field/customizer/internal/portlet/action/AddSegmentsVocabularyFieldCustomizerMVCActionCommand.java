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

package com.liferay.segments.vocabulary.field.customizer.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.segments.vocabulary.field.customizer.internal.configuration.SegmentsVocabularyFieldCustomizerConfiguration;

import java.io.File;
import java.io.IOException;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/add_segments_vocabulary_field_customizer"
	},
	service = MVCActionCommand.class
)
public class AddSegmentsVocabularyFieldCustomizerMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String pid = ParamUtil.getString(actionRequest, "pid");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_updateConfiguration(
			themeDisplay.getCompanyId(), _getConfiguration(pid),
			GetterUtil.get(
				ParamUtil.getString(actionRequest, "fieldName"),
				StringPool.BLANK),
			GetterUtil.get(
				ParamUtil.getString(actionRequest, "vocabularyName"),
				StringPool.BLANK));

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			sendRedirect(actionRequest, actionResponse, redirect);
		}
	}

	private Configuration _getConfiguration(String pid) throws IOException {
		if (Validator.isNull(pid)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Creating factory PID");
			}

			return _configurationAdmin.createFactoryConfiguration(
				SegmentsVocabularyFieldCustomizerConfiguration.class.
					getCanonicalName(),
				StringPool.QUESTION);
		}

		return _configurationAdmin.getConfiguration(pid, StringPool.QUESTION);
	}

	private String _getFileName(Configuration configuration) {
		String pid = configuration.getPid();

		int index = pid.lastIndexOf('.');

		String factoryPid = pid.substring(index + 1);

		StringBundler sb = new StringBundler(4);

		sb.append(configuration.getFactoryPid());
		sb.append(StringPool.DASH);
		sb.append(factoryPid);
		sb.append(".config");

		File file = new File(
			PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR, sb.toString());

		file = file.getAbsoluteFile();

		URI uri = file.toURI();

		return uri.toString();
	}

	private void _updateConfiguration(
			long companyId, Configuration configuration, String fieldName,
			String vocabularyName)
		throws ConfigurationModelListenerException, PortletException {

		try {
			Dictionary<String, Object> configuredProperties =
				Optional.ofNullable(
					configuration.getProperties()
				).orElseGet(
					() -> new Hashtable<>()
				);

			configuredProperties.put("fieldName", fieldName);
			configuredProperties.put("vocabularyName", vocabularyName);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Configuration properties: " +
						configuration.getProperties());
			}

			ExtendedObjectClassDefinition.Scope scope =
				ExtendedObjectClassDefinition.Scope.COMPANY;

			configuredProperties.put(
				scope.getPropertyKey(), String.valueOf(companyId));

			configuredProperties.put("configuration.cleaner.ignore", "true");

			String fileName = _getFileName(configuration);

			String oldFileName = (String)configuredProperties.put(
				"felix.fileinstall.filename", fileName);

			if ((oldFileName != null) &&
				!Objects.equals(fileName, oldFileName)) {

				try {
					Path oldFilePath = Paths.get(new URI(oldFileName));

					Files.deleteIfExists(oldFilePath);

					if (_log.isInfoEnabled()) {
						_log.info(
							"Delete inconsistent factory configuration " +
								oldFileName);
					}
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to delete inconsistent factory " +
								"configuration " + oldFileName,
							exception);
					}
				}
			}

			configuration.update(configuredProperties);
		}
		catch (ConfigurationModelListenerException
					configurationModelListenerException) {

			throw configurationModelListenerException;
		}
		catch (IOException ioException) {
			throw new PortletException(ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddSegmentsVocabularyFieldCustomizerMVCActionCommand.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.display.context;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.display.context.DLMimeTypeDisplayContext;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.ContentTypes;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Roberto Díaz
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	service = DLMimeTypeDisplayContext.class
)
public class DefaultDLMimeTypeDisplayContext
	implements DLMimeTypeDisplayContext {

	@Override
	public String getCssClassFileMimeType(String mimeType) {
		if (_containsMimeType(_dlConfiguration.codeFileMimeTypes(), mimeType)) {
			return "file-icon-color-0";
		}
		else if (_containsMimeType(
					_dlConfiguration.compressedFileMimeTypes(), mimeType)) {

			return "file-icon-color-0";
		}
		else if (_containsMimeType(
					_dlConfiguration.presentationFileMimeTypes(), mimeType)) {

			return "file-icon-color-4";
		}
		else if (_containsMimeType(
					_dlConfiguration.spreadSheetFileMimeTypes(), mimeType)) {

			return "file-icon-color-2";
		}
		else if (_containsMimeType(
					_dlConfiguration.textFileMimeTypes(), mimeType)) {

			return "file-icon-color-6";
		}
		else if (_containsMimeType(
					_dlConfiguration.vectorialFileMimeTypes(), mimeType)) {

			return "file-icon-color-3";
		}
		else if (_isMultimediaFileMimeType(mimeType)) {
			return "file-icon-color-5";
		}

		return "file-icon-color-0";
	}

	@Override
	public String getIconFileMimeType(String mimeType) {
		if (_containsMimeType(_dlConfiguration.codeFileMimeTypes(), mimeType)) {
			return "document-code";
		}
		else if (_containsMimeType(
					_dlConfiguration.compressedFileMimeTypes(), mimeType)) {

			return "document-compressed";
		}
		else if (_containsMimeType(
					_dlConfiguration.presentationFileMimeTypes(), mimeType)) {

			return "document-presentation";
		}
		else if (_containsMimeType(
					_dlConfiguration.spreadSheetFileMimeTypes(), mimeType)) {

			return "document-table";
		}
		else if (_containsMimeType(
					_dlConfiguration.textFileMimeTypes(), mimeType)) {

			return "document-text";
		}
		else if (_containsMimeType(
					_dlConfiguration.vectorialFileMimeTypes(), mimeType)) {

			return "document-vector";
		}
		else if (_isMultimediaFileMimeType(mimeType)) {
			if (mimeType.startsWith("image")) {
				return "document-image";
			}

			return "document-multimedia";
		}

		return "document-default";
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);
	}

	private boolean _containsMimeType(String[] mimeTypes, String mimeType) {
		for (String curMimeType : mimeTypes) {
			int pos = curMimeType.indexOf("/");

			if (pos != -1) {
				if (mimeType.equals(curMimeType)) {
					return true;
				}
			}
			else {
				if (mimeType.startsWith(curMimeType)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean _isMultimediaFileMimeType(String mimeType) {
		if (Objects.equals(
				mimeType,
				ContentTypes.
					APPLICATION_VND_LIFERAY_VIDEO_EXTERNAL_SHORTCUT_HTML) ||
			_containsMimeType(
				_dlConfiguration.multimediaFileMimeTypes(), mimeType)) {

			return true;
		}

		return false;
	}

	private volatile DLConfiguration _dlConfiguration;

}
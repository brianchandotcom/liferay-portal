/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.handler;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.internal.util.Tuple;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = PathInterpreter.class)
public class PathInterpreter {

	public Tuple<FileVersion, Map<String, String>> interpretPath(
		String pathInfo) {

		try {
			if (pathInfo == null) {
				throw new IllegalArgumentException("Path information is null");
			}

			Matcher matcher = _pattern.matcher(pathInfo);

			if (!matcher.matches()) {
				return null;
			}

			long fileEntryId = Long.valueOf(matcher.group(1));

			FileVersion fileVersion = _getFileVersion(
				_dlAppService.getFileEntry(fileEntryId),
				_getFileVersionId(matcher));

			AMImageConfigurationEntry amImageConfigurationEntry =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					fileVersion.getCompanyId(),
					_getConfigurationEntryUUID(matcher));

			if (amImageConfigurationEntry == null) {
				return Tuple.of(fileVersion, new HashMap<>());
			}

			Map<String, String> curProperties =
				amImageConfigurationEntry.getProperties();

			AMAttribute<?, String> configurationUuidAMAttribute =
				AMAttribute.getConfigurationUuidAMAttribute();

			curProperties.put(
				configurationUuidAMAttribute.getName(),
				amImageConfigurationEntry.getUUID());

			return Tuple.of(fileVersion, curProperties);
		}
		catch (PortalException portalException) {
			throw new AMRuntimeException(portalException);
		}
	}

	private String _getConfigurationEntryUUID(Matcher matcher) {
		return matcher.group(3);
	}

	private FileVersion _getFileVersion(FileEntry fileEntry, long fileVersionId)
		throws PortalException {

		if (fileVersionId == 0) {
			return fileEntry.getFileVersion();
		}

		return _dlAppService.getFileVersion(fileVersionId);
	}

	private long _getFileVersionId(Matcher matcher) {
		if (matcher.group(2) == null) {
			return 0;
		}

		return Long.valueOf(matcher.group(2));
	}

	private static final Pattern _pattern = Pattern.compile(
		"/image/(\\d+)(?:/(\\d+))?/([^/]+)/(?:[^/]+)");

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Reference
	private DLAppService _dlAppService;

}
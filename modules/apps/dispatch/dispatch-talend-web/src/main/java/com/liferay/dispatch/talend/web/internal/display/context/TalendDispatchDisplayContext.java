/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.talend.web.internal.display.context;

import com.liferay.dispatch.metadata.DispatchTriggerMetadata;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;

/**
 * @author Mahmoud Azzam
 */
public class TalendDispatchDisplayContext {

	public TalendDispatchDisplayContext(
		DispatchTriggerMetadata dispatchTriggerMetadata) {

		_dispatchTriggerMetadata = dispatchTriggerMetadata;
	}

	public String getTalendArchiveFileName() {
		Map<String, String> attributes =
			_dispatchTriggerMetadata.getAttributes();

		return GetterUtil.getString(attributes.get("talend-archive-file-name"));
	}

	private final DispatchTriggerMetadata _dispatchTriggerMetadata;

}
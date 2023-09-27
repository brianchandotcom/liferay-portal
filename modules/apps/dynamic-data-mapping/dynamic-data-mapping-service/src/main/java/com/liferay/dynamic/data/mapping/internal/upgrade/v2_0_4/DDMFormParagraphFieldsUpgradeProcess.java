/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_4;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Laszlo Hudak
 */
public class DDMFormParagraphFieldsUpgradeProcess
	extends com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_3.
				DDMFormParagraphFieldsUpgradeProcess {

	public DDMFormParagraphFieldsUpgradeProcess(JSONFactory jsonFactory) {
		super(jsonFactory);
	}

	@Override
	protected long getClassNameId() {
		return PortalUtil.getClassNameId(DDMFormInstance.class);
	}

}
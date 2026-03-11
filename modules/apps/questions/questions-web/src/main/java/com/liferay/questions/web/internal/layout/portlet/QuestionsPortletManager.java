/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.questions.web.internal.layout.portlet;

import com.liferay.layout.portlet.PortletManager;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.questions.web.internal.constants.QuestionsPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	property = "jakarta.portlet.name=" + QuestionsPortletKeys.QUESTIONS,
	service = PortletManager.class
)
public class QuestionsPortletManager implements PortletManager {

	@Override
	public boolean isDeprecated() {
		return true;
	}

	@Override
	public boolean isVisible(Layout layout) {
		return FeatureFlagManagerUtil.isEnabled(
			layout.getCompanyId(), "LPD-82301");
	}

}
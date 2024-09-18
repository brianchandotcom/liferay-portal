/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.questions.web.internal.configuration.admin.definition;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;

/**
 * @author José Abelenda
 */
@DDMForm
@DDMFormLayout(
	paginationMode = com.liferay.dynamic.data.mapping.model.DDMFormLayout.SINGLE_PAGE_MODE,
	value = {
		@DDMFormLayoutPage(
			{
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"enableRedirectToLogin",
								"enableCustomAssetRenderer",
								"enableAnonymousRead",
								"showCardsForTopicNavigation",
								"historyRouterBasePath", "useTopicNamesInURL",
								"rootTopicExternalReferenceCode", "rootTopicId"
							}
						)
					}
				)
			}
		)
	}
)
public interface QuestionsConfigurationForm {
}
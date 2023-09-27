/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.ckeditor.web.internal.spa.navigation.exception.selector.contributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Chema Balsas
 */
@Component(
	property = "javascript.single.page.application.navigation.exception.selector=:not([data-cke-saved-href])",
	service = Object.class
)
public class CKEditorSPANavigationExceptionSelectorContributor {
}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.pdf.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alicia García
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.document.library.preview.pdf.internal.configuration.PDFPreviewConfiguration",
	localization = "content/Language", name = "pdf-preview-configuration-name"
)
public interface PDFPreviewConfiguration {

	@Meta.AD(
		deflt = "0", description = "maximum-number-of-pages-help",
		name = "maximum-number-of-pages", required = false
	)
	public int maxNumberOfPages();

}
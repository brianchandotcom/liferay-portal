/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.definitions.web.internal.servlet.taglib.ui;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategory;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = "form.navigator.category.order:Integer=100",
	service = FormNavigatorCategory.class
)
public class CPAttachmentFileEntryDetailsFormNavigatorCategory
	implements FormNavigatorCategory {

	@Override
	public String getFormNavigatorId() {
		return CPAttachmentFileEntryFormNavigatorConstants.
			FORM_NAVIGATOR_ID_CP_ATTACHMENT_FILE_ENTRY;
	}

	@Override
	public String getKey() {
		return CPAttachmentFileEntryFormNavigatorConstants.
			CATEGORY_KEY_CP_ATTACHMENT_FILE_ENTRY_DETAILS;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "details");
	}

}
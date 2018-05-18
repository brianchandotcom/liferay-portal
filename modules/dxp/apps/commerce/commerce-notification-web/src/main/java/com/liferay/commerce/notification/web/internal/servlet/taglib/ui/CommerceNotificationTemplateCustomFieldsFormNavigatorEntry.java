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

package com.liferay.commerce.notification.web.internal.servlet.taglib.ui;

import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPFormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.taglib.util.CustomAttributesUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "form.navigator.entry.order:Integer=20",
	service = FormNavigatorEntry.class
)
public class CommerceNotificationTemplateCustomFieldsFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<CommerceNotificationTemplate> {

	@Override
	public String getCategoryKey() {
		return CommerceNotificationTemplateFormNavigatorConstants.
			CATEGORY_KEY_COMMERCE_NOTIFICATION_TEMPLATE_GENERAL;
	}

	@Override
	public String getFormNavigatorId() {
		return CommerceNotificationTemplateFormNavigatorConstants.
			FORM_NAVIGATOR_ID_COMMERCE_NOTIFICATION_TEMPLATE;
	}

	@Override
	public String getKey() {
		return "custom-fields";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "custom-fields");
	}

	@Override
	public boolean isVisible(
		User user, CommerceNotificationTemplate commerceNotificationTemplate) {

		boolean hasCustomAttributesAvailable = false;

		try {
			long classPK = 0;

			if (commerceNotificationTemplate != null) {
				classPK =
					commerceNotificationTemplate.
						getCommerceNotificationTemplateId();
			}

			hasCustomAttributesAvailable =
				CustomAttributesUtil.hasCustomAttributes(
					user.getCompanyId(),
					CommerceNotificationTemplate.class.getName(), classPK,
					null);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return hasCustomAttributesAvailable;
	}

	@Override
	protected String getJspPath() {
		return "/notification_template/custom_fields.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceNotificationTemplateCustomFieldsFormNavigatorEntry.class);

}
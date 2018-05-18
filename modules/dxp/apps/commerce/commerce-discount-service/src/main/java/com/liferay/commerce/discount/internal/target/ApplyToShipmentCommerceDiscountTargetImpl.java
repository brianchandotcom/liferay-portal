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

package com.liferay.commerce.discount.internal.target;

import com.liferay.commerce.discount.model.CommerceDiscountConstants;
import com.liferay.commerce.discount.target.CommerceDiscountTarget;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.discount.target.key=" + CommerceDiscountConstants.TARGET_SHIPMENT,
		"commerce.discount.target.order:Integer=30"
	}
)
public class ApplyToShipmentCommerceDiscountTargetImpl
	implements CommerceDiscountTarget {

	@Override
	public String getKey() {
		return CommerceDiscountConstants.TARGET_SHIPMENT;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "apply-to-shipment");
	}

}
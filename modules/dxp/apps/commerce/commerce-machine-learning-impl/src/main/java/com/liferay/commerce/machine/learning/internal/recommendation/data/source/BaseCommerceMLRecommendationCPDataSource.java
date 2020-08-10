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

package com.liferay.commerce.machine.learning.internal.recommendation.data.source;

import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.product.data.source.CPDataSource;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
public abstract class BaseCommerceMLRecommendationCPDataSource
	implements CPDataSource {

	protected ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	@Reference(unbind = "-")
	protected CommerceAccountHelper commerceAccountHelper;

	@Reference(unbind = "-")
	protected CPDefinitionHelper cpDefinitionHelper;

	@Reference(unbind = "-")
	protected Portal portal;

}
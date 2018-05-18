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

package com.liferay.commerce.currency.util;

import aQute.bnd.annotation.ProviderType;

import java.math.BigDecimal;

import java.util.Locale;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface RoundingType {

	public String getLabel(Locale locale);

	public String getName();

	public String round(BigDecimal value);

}
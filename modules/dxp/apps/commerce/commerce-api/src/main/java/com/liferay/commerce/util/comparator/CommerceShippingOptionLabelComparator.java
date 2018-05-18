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

package com.liferay.commerce.util.comparator;

import com.liferay.commerce.model.CommerceShippingOption;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceShippingOptionLabelComparator
	implements Comparator<CommerceShippingOption>, Serializable {

	@Override
	public int compare(
		CommerceShippingOption commerceShippingOption1,
		CommerceShippingOption commerceShippingOption2) {

		String label1 = commerceShippingOption1.getLabel();
		String label2 = commerceShippingOption2.getLabel();

		return label1.compareTo(label2);
	}

}
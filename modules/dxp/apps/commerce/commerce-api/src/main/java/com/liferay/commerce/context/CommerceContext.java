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

package com.liferay.commerce.context;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.product.model.CPRule;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;

import java.io.Serializable;

import java.util.List;
import java.util.Optional;

/**
 * @author Marco Leo
 */
public interface CommerceContext extends Serializable {

	public CommerceCurrency getCommerceCurrency() throws PortalException;

	public CommerceOrder getCommerceOrder() throws PortalException;

	public Optional<CommercePriceList> getCommercePriceList()
		throws PortalException;

	public long[] getCommerceUserSegmentEntryIds() throws PortalException;

	public List<CPRule> getCPRules() throws PortalException;

	public Organization getOrganization() throws PortalException;

}
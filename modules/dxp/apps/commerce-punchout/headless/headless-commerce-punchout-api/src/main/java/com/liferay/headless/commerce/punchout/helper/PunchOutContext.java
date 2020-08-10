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

package com.liferay.headless.commerce.punchout.helper;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.headless.commerce.punchout.dto.v1_0.PunchOutSession;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;

/**
 * @author Jaclyn Ong
 */
public class PunchOutContext {

	public PunchOutContext(
		CommerceAccount businessCommerceAccount, Group buyerGroup,
		User buyerLiferayUser, CommerceChannel commerceChannel,
		CommerceOrder editCartCommerceOrder, PunchOutSession punchOutSession) {

		_businessCommerceAccount = businessCommerceAccount;
		_buyerGroup = buyerGroup;
		_buyerLiferayUser = buyerLiferayUser;
		_commerceChannel = commerceChannel;
		_editCartCommerceOrder = editCartCommerceOrder;
		_punchOutSession = punchOutSession;
	}

	public CommerceAccount getBusinessCommerceAccount() {
		return _businessCommerceAccount;
	}

	public Group getBuyerGroup() {
		return _buyerGroup;
	}

	public User getBuyerLiferayUser() {
		return _buyerLiferayUser;
	}

	public CommerceChannel getCommerceChannel() {
		return _commerceChannel;
	}

	public CommerceOrder getEditCartCommerceOrder() {
		return _editCartCommerceOrder;
	}

	public PunchOutSession getPunchOutSession() {
		return _punchOutSession;
	}

	public void setBusinessCommerceAccount(
		CommerceAccount businessCommerceAccount) {

		_businessCommerceAccount = businessCommerceAccount;
	}

	public void setBuyerGroup(Group buyerGroup) {
		_buyerGroup = buyerGroup;
	}

	public void setBuyerLiferayUser(User buyerLiferayUser) {
		_buyerLiferayUser = buyerLiferayUser;
	}

	public void setCommerceChannel(CommerceChannel commerceChannel) {
		_commerceChannel = commerceChannel;
	}

	public void setEditCartCommerceOrder(CommerceOrder editCartCommerceOrder) {
		_editCartCommerceOrder = editCartCommerceOrder;
	}

	public void setPunchOutSession(PunchOutSession punchOutSession) {
		_punchOutSession = punchOutSession;
	}

	private CommerceAccount _businessCommerceAccount;
	private Group _buyerGroup;
	private User _buyerLiferayUser;
	private CommerceChannel _commerceChannel;
	private CommerceOrder _editCartCommerceOrder;
	private PunchOutSession _punchOutSession;

}
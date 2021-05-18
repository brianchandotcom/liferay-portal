/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import React, {useContext} from 'react';

import {liferayNavigate} from '../../utilities/index';
import MiniCartContext from './MiniCartContext';
import {
	REVIEW_ORDER,
	SUBMIT_ORDER,
	WORKFLOW_STATUS_APPROVED,
} from './util/constants';
import {hasErrors} from './util/index';

function OrderButton() {
	const {actionURLs, cartState, labels} = useContext(MiniCartContext);

	const {checkoutURL, orderDetailURL} = actionURLs;
	const {cartItems = [], workflowStatusInfo = {}} = cartState;

	const {
		code: workflowStatus = WORKFLOW_STATUS_APPROVED,
	} = workflowStatusInfo;

	const canSubmit =
		!hasErrors(cartItems) && workflowStatus === WORKFLOW_STATUS_APPROVED;

	return (
		<div className="mini-cart-submit">
			<ClayButton
				block
				disabled={!cartItems.length}
				onClick={() => {
					liferayNavigate(canSubmit ? checkoutURL : orderDetailURL);
				}}
			>
				{canSubmit ? labels[SUBMIT_ORDER] : labels[REVIEW_ORDER]}
			</ClayButton>
		</div>
	);
}

export default OrderButton;

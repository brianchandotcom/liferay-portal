/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import DefaultView from './info_box/DefaultView';
import OrderNotesView from './info_box/OrderNotesView';
import OrderSummaryView from './info_box/OrderSummaryView';
import PurchaseOrderDocumentView from './info_box/PurchaseOrderDocumentView';
import TermsView from './info_box/TermsView';

const InfoBox = (props) => {
	if (props.field === 'deliveryTermId' || props.field === 'paymentTermId') {
		return <TermsView {...props} />;
	}
	if (props.field === 'notes') {
		return <OrderNotesView {...props} />;
	}
	if (props.field === 'orderSummary') {
		return <OrderSummaryView {...props} />;
	}

	if (props.field === 'purchaseOrderDocument') {
		return <PurchaseOrderDocumentView {...props} />;
	}

	return <DefaultView {...props} />;
};

export default InfoBox;

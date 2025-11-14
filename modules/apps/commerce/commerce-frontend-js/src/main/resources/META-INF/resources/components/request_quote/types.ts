/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBaseButtonProps} from './components/RequestQuoteButton';

export interface IRequestQuoteChannel {
	currencyCode: string;
	id: string | number;
}

export interface IRequestQuoteCPInstance {
	quantity: number;
	skuId: string | number;
	skuOptions: any;
	skuUnitOfMeasure: string;
}

export interface IRequestQuoteData {
	accountId: string;
	baseOrderDetailURL: string;
	cartId?: string;
	channel: IRequestQuoteChannel;
	cpDefinitionId: string | number;
	cpInstance: IRequestQuoteCPInstance;
	createCart: boolean;
	email?: string;
	namespace: string;
	notesPermission?: boolean;
	orderDetailURL: string;
	orderUUID?: string;
	requestQuoteElementId: string;
	restrictedNotesPermission?: boolean;
}

export interface IRequestQuoteStyle
	extends Omit<IBaseButtonProps, 'onClick' | 'onError'> {}

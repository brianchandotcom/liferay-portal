/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './request_quote.scss';

import {useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import {
	CP_INSTANCE_CHANGED,
	CP_QUANTITY_SELECTOR_CHANGED,

	// @ts-ignore

} from '../../utilities/eventsDefinitions';
import RequestQuoteButton from './components/RequestQuoteButton';
import RequestQuoteModal from './components/RequestQuoteModal';
import {
	IRequestQuoteCPInstance,
	IRequestQuoteData,
	IRequestQuoteStyle,
} from './types';

interface IRequestQuoteProps {
	data: IRequestQuoteData;
	style?: IRequestQuoteStyle;
	trigger?: React.ReactElement;
}

const buildRequestQuoteData = (data: IRequestQuoteData): IRequestQuoteData => {
	return {
		...data,
		...(Liferay.CommerceContext.account?.accountId && {
			accountId: Liferay.CommerceContext.account.accountId,
		}),
		...(Liferay.CommerceContext.currency?.currencyCode &&
			Liferay.CommerceContext.commerceChannelId && {
				channel: {
					currencyCode: Liferay.CommerceContext.currency.currencyCode,
					id: Liferay.CommerceContext.commerceChannelId,
				},
			}),
	};
};

const RequestQuote = ({data, style, trigger}: IRequestQuoteProps) => {
	const {observer, onOpenChange, open} = useModal();
	const [requestQuoteData, setRequestQuoteData] = useState<IRequestQuoteData>(
		buildRequestQuoteData(data)
	);

	const onQuantityChange = ({quantity}: {quantity: number}) => {
		setRequestQuoteData((prevState) => ({
			...prevState,
			cpInstance: {
				...prevState.cpInstance,
				quantity,
			},
		}));
	};

	const onCpInstanceChange = ({
		cpInstance,
	}: {
		cpInstance: IRequestQuoteCPInstance;
	}) => {
		setRequestQuoteData((prevState) => ({
			...prevState,
			cpInstance: {
				...prevState.cpInstance,
				...cpInstance,
			},
		}));
	};

	useEffect(() => {
		Liferay.on(
			`${data.namespace}${CP_QUANTITY_SELECTOR_CHANGED}`,
			onQuantityChange
		);
		Liferay.on(
			`${data.namespace}${CP_INSTANCE_CHANGED}`,
			onCpInstanceChange
		);

		return () => {
			Liferay.detach(
				`${data.namespace}${CP_QUANTITY_SELECTOR_CHANGED}`,
				onQuantityChange
			);
			Liferay.detach(
				`${data.namespace}${CP_INSTANCE_CHANGED}`,
				onCpInstanceChange
			);
		};
	}, [data.namespace]);

	const RequestQuoteModalButton = trigger ? (
		React.cloneElement(trigger, {onClick: () => onOpenChange(true)})
	) : (
		<RequestQuoteButton onClick={() => onOpenChange(true)} {...style} />
	);

	return (
		<>
			{RequestQuoteModalButton}
			<RequestQuoteModal
				data={requestQuoteData}
				observer={observer}
				onOpenChange={onOpenChange}
				open={open}
			/>
		</>
	);
};

export default RequestQuote;

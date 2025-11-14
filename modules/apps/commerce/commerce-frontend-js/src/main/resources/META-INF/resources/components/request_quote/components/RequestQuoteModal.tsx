/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayModal from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import React from 'react';

import LABELS from '../labels';
import {IRequestQuoteCPInstance, IRequestQuoteData} from '../types';
import RequestQuoteForm from './RequestQuoteForm';

interface IRequestQuoteModalProps {
	data: IRequestQuoteData;
	observer: Observer;
	onOpenChange: (value: boolean) => void;
	open: boolean;
}

const RequestQuoteModal = ({
	data,
	observer,
	onOpenChange,
	open,
}: IRequestQuoteModalProps) => {
	if (!open) {
		return null;
	}

	const email =
		data.email || Liferay.ThemeDisplay?.getUserEmailAddress() || '';
	const cpInstance: IRequestQuoteCPInstance | undefined = data.cpInstance
		? {
				quantity: data.cpInstance.quantity || 1,
				skuId: data.cpInstance.skuId,
				skuOptions: JSON.stringify(data.cpInstance.skuOptions || []),
				skuUnitOfMeasure: data.cpInstance.skuUnitOfMeasure,
			}
		: undefined;

	return (
		<ClayModal observer={observer}>
			<ClayModal.Header closeButtonAriaLabel={LABELS.CLOSE}>
				{LABELS.REQUEST_QUOTE}
			</ClayModal.Header>

			<ClayModal.Body className="p-3">
				{data.createCart && (
					<ClayAlert
						displayType="warning"
						symbol="info-circle"
						title={LABELS.NEW_ORDER_WARNING}
						variant="inline"
					/>
				)}

				<RequestQuoteForm
					{...data}
					cpInstance={cpInstance}
					defaultEmail={email}
					onOpenChange={onOpenChange}
				/>
			</ClayModal.Body>
		</ClayModal>
	);
};

export default RequestQuoteModal;

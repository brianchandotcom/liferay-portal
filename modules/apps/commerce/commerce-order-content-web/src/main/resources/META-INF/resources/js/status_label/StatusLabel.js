/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {openToast} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

import {getLabelDisplay} from '../getLabelDisplay';
import {getOrder} from '../util';

const StatusLabel = ({isOpenOrder, namespace, orderId, selectedStatus}) => {
	const [status, setStatus] = useState(null);

	const onStatusChange = useCallback(
		({order = null}) => {
			getOrder(isOpenOrder, order, orderId)
				.then((order) => {
					setStatus(
						getLabelDisplay(
							order[
								isOpenOrder
									? selectedStatus
									: 'workflowStatusInfo'
							]
						)
					);
				})
				.catch((error) => {
					openToast({
						message:
							error.message ||
							Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						type: 'danger',
					});
				});
		},
		[isOpenOrder, orderId, selectedStatus]
	);

	useEffect(() => {
		onStatusChange({order: null});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		Liferay.on('order-information-altered', onStatusChange);

		return () => {
			Liferay.detach('order-information-altered', onStatusChange);
		};
	}, [onStatusChange]);

	return (
		<>
			{status ? (
				<ClayLabel
					displayType={status.displayType}
					id={`${namespace}statusLabel`}
				>
					{Liferay.Language.get(status.label_i18n)}
				</ClayLabel>
			) : null}
		</>
	);
};

export default StatusLabel;

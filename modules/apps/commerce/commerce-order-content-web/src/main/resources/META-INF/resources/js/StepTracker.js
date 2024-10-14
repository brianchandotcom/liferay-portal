/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiStepNav from '@clayui/multi-step-nav';
import {commerceEvents} from 'commerce-frontend-js';
import {openToast} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

import {getOrder} from './util';

const StepTracker = ({isOpen, orderId, stepModels}) => {
	const [steps, setSteps] = useState(stepModels);

	const onStatusChange = useCallback(
		({order = null}) => {
			getOrder(isOpen, order, orderId)
				.then((order) => {
					setSteps(order.steps);
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
		[isOpen, orderId]
	);

	useEffect(() => {
		onStatusChange({order: {steps}});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		Liferay.on(commerceEvents.ORDER_INFORMATION_ALTERED, onStatusChange);

		return () => {
			Liferay.detach(
				commerceEvents.ORDER_INFORMATION_ALTERED,
				onStatusChange
			);
		};
	}, [onStatusChange]);

	return (
		<ClayMultiStepNav indicatorLabel="top">
			{steps.map(({label, state}, i) => {
				const complete = state === 'completed';

				return (
					<ClayMultiStepNav.Item
						active={state === 'active'}
						expand={i + 1 !== steps.length}
						key={i}
						state={complete ? 'complete' : undefined}
					>
						<ClayMultiStepNav.Divider />

						<ClayMultiStepNav.Indicator
							complete={complete}
							subTitle={label}
						/>
					</ClayMultiStepNav.Item>
				);
			})}
		</ClayMultiStepNav>
	);
};

export default StepTracker;

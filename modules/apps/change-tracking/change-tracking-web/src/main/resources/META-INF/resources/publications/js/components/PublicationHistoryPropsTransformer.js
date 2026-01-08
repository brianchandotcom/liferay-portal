/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayProgressBar from '@clayui/progress-bar';
import {createPortletURL, fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import StatusRenderer from './StatusRenderer';

export default function propsTransformer({
	additionalProps: {getPublicationStatusURL},
	...otherProps
}) {
	const PublicationHistoryStatusRenderer = (props) => {
		const [percentage, setPercentage] = useState(0);

		useEffect(() => {
			if (props.value.label !== 'in-progress') {
				return;
			}

			const publicationStatusURL = createPortletURL(
				getPublicationStatusURL,
				{
					ctProcessId: props.itemId,
				}
			);

			let label = null;

			const interval = setInterval(() => {
				if (label) {
					clearInterval(interval);

					props.loadData();
				}

				fetch(publicationStatusURL)
					.then((response) => response.json())
					.then((json) => {
						if (json) {
							if (
								Object.hasOwnProperty.call(json, 'percentage')
							) {
								setPercentage(json.percentage);
							}

							if (json.label) {
								label = json.label;
							}
						}
					})
					.catch(() => {});
			}, 200);

			return () => clearInterval(interval);
		}, [props]);

		return props.value.label === 'in-progress' ? (
			<ClayProgressBar
				messages={{
					ariaLabelAttention: Liferay.Language.get(
						'attention-value-is-at-x'
					),
					ariaLabelComplete: Liferay.Language.get('complete'),
					ariaLabelInProgress: Liferay.Language.get('progress-x'),
				}}
				value={percentage}
			/>
		) : (
			<StatusRenderer value={props.value} />
		);
	};

	const customPublicationHistoryStatusRenderer = {
		component: PublicationHistoryStatusRenderer,
		name: 'customPublicationHistoryStatusRenderer',
		type: 'internal',
	};

	return {
		...otherProps,
		customRenderers: {
			tableCell: [customPublicationHistoryStatusRenderer],
		},
	};
}

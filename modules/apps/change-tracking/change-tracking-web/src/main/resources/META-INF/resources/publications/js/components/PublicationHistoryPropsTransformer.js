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

		const [status, setStatus] = useState(null);

		useEffect(() => {
			const publicationStatusURL = createPortletURL(
				getPublicationStatusURL,
				{
					ctProcessId: props.itemId,
				}
			);

			fetch(publicationStatusURL)
				.then((response) => response.json())
				.then((json) => {
					if (json) {
						if (json.label) {
							setStatus({
								label: json.label,
							});
						}
						else if (
							Object.hasOwnProperty.call(json, 'percentage')
						) {
							setPercentage(json.percentage);

							let label = null;

							const interval = setInterval(() => {
								if (label) {
									setStatus({label});

									clearInterval(interval);

									props.loadData();

									return;
								}

								fetch(publicationStatusURL)
									.then((response) => response.json())
									.then((json) => {
										if (json) {
											if (json.label) {
												setPercentage(100);

												label = json.label;
											}
											else if (
												Object.hasOwnProperty.call(
													json,
													'percentage'
												)
											) {
												setPercentage(json.percentage);
											}
										}
									})
									.catch(() => {});
							}, 1000);

							return () => clearInterval(interval);
						}
					}
				});
		}, [props]);

		if (props.value.label !== 'in-progress' || status) {
			return StatusRenderer(props);
		}

		return <ClayProgressBar value={percentage} />;
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

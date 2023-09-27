/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import ClayProgressBar from '@clayui/progress-bar';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

export default function PublicationStatus({
	dataURL,
	displayType,
	label,
	renderComplete,
	spritemap,
	updateStatus,
}) {
	const [percentage, setPercentage] = useState(0);

	let initialStatus = null;

	if (label) {
		initialStatus = {
			displayType,
			label,
		};
	}

	const [status, setStatus] = useState(initialStatus);

	useEffect(() => {
		if (label) {
			setStatus({
				displayType,
				label,
			});

			return;
		}

		setStatus(null);

		fetch(dataURL)
			.then((response) => response.json())
			.then((json) => {
				if (json) {
					if (json.label) {
						setStatus({
							displayType: json.displayType,
							label: json.label,
						});

						if (updateStatus) {
							updateStatus(
								json.displayType,
								json.label,
								json.published
							);
						}
					}
					else if (Object.hasOwnProperty.call(json, 'percentage')) {
						setPercentage(json.percentage);

						let displayType = null;
						let label = null;
						let published = false;

						const interval = setInterval(() => {
							if (label) {
								setStatus({displayType, label});

								if (updateStatus) {
									updateStatus(displayType, label, published);
								}

								clearInterval(interval);

								return;
							}

							fetch(dataURL)
								.then((response) => response.json())
								.then((json) => {
									if (json) {
										if (json.label) {
											setPercentage(100);

											displayType = json.displayType;
											label = json.label;
											published = json.published;
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
	}, [dataURL, displayType, label, updateStatus]);

	if (status) {
		return renderComplete ? (
			renderComplete(status)
		) : (
			<ClayLabel displayType={status.displayType} spritemap={spritemap}>
				{status.label}
			</ClayLabel>
		);
	}

	return <ClayProgressBar spritemap={spritemap} value={percentage} />;
}

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

import ClayLabel from '@clayui/label';
import ClayProgressBar from '@clayui/progress-bar';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

export default ({dataURL, spritemap}) => {
	const [percentage, setPercentage] = useState(0);
	const [status, setStatus] = useState(null);

	useEffect(() => {
		fetch(dataURL)
			.then((response) => response.json())
			.then((json) => {
				if (json) {
					if (json.status) {
						setStatus(json.status);
					}
					else if (Object.hasOwnProperty.call(json, 'percentage')) {
						setPercentage(json.percentage);

						let fetchedStatus = null;

						const interval = setInterval(() => {
							if (fetchedStatus) {
								setStatus(fetchedStatus);

								clearInterval(interval);

								return;
							}

							fetch(dataURL)
								.then((response) => response.json())
								.then((json) => {
									if (json) {
										if (json.status) {
											setPercentage(100);

											fetchedStatus = json.status;
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
								});
						}, 1000);

						return () => clearInterval(interval);
					}
				}
			});
	}, [dataURL]);

	if (status) {
		return (
			<ClayLabel
				displayType={status === 'published' ? 'success' : 'danger'}
				spritemap={spritemap}
			>
				{status}
			</ClayLabel>
		);
	}

	return <ClayProgressBar spritemap={spritemap} value={percentage} />;
};

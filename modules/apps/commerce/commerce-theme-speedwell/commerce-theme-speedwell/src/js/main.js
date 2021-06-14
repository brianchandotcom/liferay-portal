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

AUI().ready(() => {
	const Speedwell = window.Speedwell;

	const RETRY_TIMES = 3,
		RETRY_INTERVAL = 333;

	function retrySlidersBootUp() {
		let currentRetry = 0;

		return new Promise((resolve, reject) => {
			const retryCycle = setInterval(() => {
				currentRetry++;

				if (currentRetry <= RETRY_TIMES) {
					const componentReady = Liferay.component('SpeedwellSlider');

					if (componentReady) {
						clearInterval(retryCycle);

						resolve(componentReady);
					}
				}
				else {
					clearInterval(retryCycle);

					reject(
						new Error(
							'SpeedwellSlider component failed to initialize'
						)
					);
				}
			}, RETRY_INTERVAL);
		});
	}

	if (!!Speedwell && !!Speedwell.features) {
		Speedwell.features.sliders = [];

		if (
			'sliderCallbacks' in Speedwell.features &&
			Speedwell.features.sliderCallbacks.length
		) {
			Liferay.componentReady('SpeedwellSlider')
				.catch(retrySlidersBootUp)
				.then((sliderComponent) => {
					Speedwell.features.sliderCallbacks.forEach((callback) => {
						Speedwell.features.sliders.push(
							callback(sliderComponent)
						);
					});

					return Promise.resolve();
				})
				.catch((initError) => {
					console.error(initError);

					return Promise.resolve();
				})
				.then(() => {
					Speedwell.features.sliderCallbacks = [];
				});
		}
	}
});

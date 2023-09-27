/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI().ready(() => {
	const Speedwell = window.Speedwell;

	const RETRY_TIMES = 3;
	const RETRY_INTERVAL = 333;

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

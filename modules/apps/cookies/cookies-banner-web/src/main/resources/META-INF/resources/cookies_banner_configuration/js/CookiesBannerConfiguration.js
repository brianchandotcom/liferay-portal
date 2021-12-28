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

export default function () {
	const toggleFunctional = document.querySelector(
		'.toggle-switch-check-functional'
	);
	const togglePerformance = document.querySelector(
		'.toggle-switch-check-performance'
	);
	const togglePersonalization = document.querySelector(
		'.toggle-switch-check-personalization'
	);
	if (
		localStorage.getItem('liferay.cookie.consent.functional') === 'accepted'
	) {
		toggleFunctional.checked = true;
	}

	if (
		localStorage.getItem('liferay.cookie.consent.performance') ===
		'accepted'
	) {
		togglePerformance.checked = true;
	}

	if (
		localStorage.getItem('liferay.cookie.consent.personalization') ===
		'accepted'
	) {
		togglePersonalization.checked = true;
	}

	toggleFunctional.addEventListener(
		'click',
		function handletoggleFunctional() {
			if (toggleFunctional.checked) {
				localStorage.setItem(
					'liferay.cookie.consent.functional',
					'accepted'
				);
			}
			else {
				localStorage.setItem(
					'liferay.cookie.consent.functional',
					'decline'
				);
			}
		}
	);

	togglePerformance.addEventListener(
		'click',
		function handleTogglePerformance() {
			if (togglePerformance.checked) {
				localStorage.setItem(
					'liferay.cookie.consent.performance',
					'accepted'
				);
			}
			else {
				localStorage.setItem(
					'liferay.cookie.consent.performance',
					'decline'
				);
			}
		}
	);

	togglePersonalization.addEventListener(
		'click',
		function handletogglePersonalization() {
			if (togglePersonalization.checked) {
				localStorage.setItem(
					'liferay.cookie.consent.personalization',
					'accepted'
				);
			}
			else {
				localStorage.setItem(
					'liferay.cookie.consent.personalization',
					'decline'
				);
			}
		}
	);

	toggleFunctional.removeAttribute('disabled');
	togglePerformance.removeAttribute('disabled');
	togglePersonalization.removeAttribute('disabled');
}

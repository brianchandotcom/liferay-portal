/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function initializeLock(
	name,
	{lockedIndicator, namespace, onLockChange, unlockedIndicator}
) {
	let locked = false;

	const toggle = (nextValue) => {
		if (nextValue === locked) {
			throw new Error(
				`${name} is already ${locked ? 'locked' : 'unlocked'}`
			);
		}

		locked = nextValue;

		requestAnimationFrame(() => {
			onLockChange?.({isLocked: locked});

			if (locked) {
				lockedIndicator?.classList.replace('d-none', 'd-flex');
				unlockedIndicator?.classList.replace('d-flex', 'd-none');
			}
			else {
				lockedIndicator?.classList.replace('d-flex', 'd-none');
				unlockedIndicator?.classList.replace('d-none', 'd-flex');
			}
		});
	};

	Liferay.component(`${namespace}${name}`, {
		isLocked: () => locked,
		lock: () => toggle(true),
		unlock: () => toggle(false),
	});
}

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */

const accountSelectorDropdownHeader = fragmentElement.querySelector(
	`#${fragmentEntryLinkNamespace}-account-selector-dropdown-header`
);
const accountSelectorDropdownNextButton = fragmentElement.querySelector(
	`#${fragmentEntryLinkNamespace}-account-selector-dropdown-next-button`
);
const accountSelectorDropdownPrevButton = fragmentElement.querySelector(
	`#${fragmentEntryLinkNamespace}-account-selector-dropdown-prev-button`
);
const accountSelectorPanelTitle = fragmentElement.querySelector(
	`#${fragmentEntryLinkNamespace}-account-selector-panel-title`
);
const accountSelectorPanelSlider = fragmentElement.querySelector(
	`#${fragmentEntryLinkNamespace}-account-selector-panel-slider`
);
const panels = Array.from(
	fragmentElement.querySelectorAll('.account-selector-panel-content')
).map((value, index) => {
	return {
		index,
		key: value.querySelector('.account-selector-panel-title')?.dataset
			.accountSelectorKey,
		value,
	};
});

if (layoutMode !== 'edit') {
	Liferay.on('current-account-updated', () => window.location.reload());
}

handleTitleChange(panels[0]);
accountSelectorDropdownNextButton.addEventListener('click', () => {
	const step = Number(accountSelectorDropdownHeader.dataset.step);

	handleNav(step, step + 1);
});

accountSelectorDropdownPrevButton.addEventListener('click', () => {
	const step = Number(accountSelectorDropdownHeader.dataset.step);

	handleNav(step, step - 1);
});

function handleTitleChange(panel) {
	accountSelectorPanelTitle.innerHTML = panel.value.querySelector(
		'.account-selector-panel-drop-zone-container'
	).dataset.panelTitle;
}

function handleNav(step, nextStep) {
	if (nextStep < 0 || nextStep > panels.length - 1) {
		return;
	}

	const panel = panels[nextStep];

	accountSelectorDropdownNextButton.classList.toggle(
		'invisible',
		panel.index === panels.length - 1
	);
	accountSelectorDropdownPrevButton.classList.toggle(
		'invisible',
		panel.index === 0
	);

	accountSelectorDropdownHeader.dataset.step = nextStep;

	accountSelectorPanelSlider.style.transform = `translate(-${nextStep * configuration.dropdownWidth}px, 0)`;

	handleTitleChange(panel);
}

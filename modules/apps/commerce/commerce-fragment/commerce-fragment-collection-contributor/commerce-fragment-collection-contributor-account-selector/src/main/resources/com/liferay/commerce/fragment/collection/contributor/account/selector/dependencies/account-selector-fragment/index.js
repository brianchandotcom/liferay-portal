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
	handleTitleChange(panels[0]);

	Liferay.on('current-account-updated', () => window.location.reload());

	const accountSelectorDropdownMenu = fragmentElement.querySelector(
		`#${fragmentEntryLinkNamespace}-account-selector-dropdown-menu`
	);

	accountSelectorDropdownMenu.style.maxHeight = `${configuration.dropdownHeight}px`;
	accountSelectorDropdownMenu.style.minHeight = `${configuration.dropdownHeight}px`;
	accountSelectorDropdownMenu.style.maxWidth = `${configuration.dropdownWidth}px`;
	accountSelectorDropdownMenu.style.minWidth = `${configuration.dropdownWidth}px`;

	accountSelectorDropdownNextButton.addEventListener('click', () => {
		const step = Number(accountSelectorDropdownHeader.dataset.step);

		handleNav(step, step + 1);
	});

	accountSelectorDropdownPrevButton.addEventListener('click', () => {
		const step = Number(accountSelectorDropdownHeader.dataset.step);

		handleNav(step, step - 1);
	});
}

function handleTitleChange(panel) {
	accountSelectorPanelTitle.innerHTML = panel.value.querySelector(
		'.account-selector-panel-title'
	)?.innerHTML;
}

function handleNav(step, nextStep) {
	if (nextStep < 0 || nextStep > panels.length - 1) {
		return;
	}

	const panel = panels[nextStep];

	if (panel.index === panels.length - 1) {
		accountSelectorDropdownNextButton.classList.add('invisible');
	}
	else {
		accountSelectorDropdownNextButton.classList.remove('invisible');
	}

	if (panel.index === 0) {
		accountSelectorDropdownPrevButton.classList.add('invisible');
	}
	else {
		accountSelectorDropdownPrevButton.classList.remove('invisible');
	}

	accountSelectorDropdownHeader.dataset.step = nextStep;

	panels[step].value.classList.add('d-none');

	panel.value.classList.remove('d-none');

	handleTitleChange(panel);
}

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
const accountSelectorPanelSlider = fragmentElement.querySelector(
	`#${fragmentEntryLinkNamespace}-account-selector-panel-slider`
);
const accountSelectorPanelTitle = fragmentElement.querySelector(
	`#${fragmentEntryLinkNamespace}-account-selector-panel-title`
);
const panels = Array.from(
	fragmentElement.querySelectorAll('.account-selector-panel-content')
).map((value, index) => {
	return {
		index,
		key: value.querySelector('.account-selector-panel-drop-zone-container')
			?.dataset.panelKey,
		value,
	};
});

const accountSelectorContainerSelector = `#${fragmentEntryLinkNamespace}-account-selector-container`;
const accountSelectorCtaContainerSelector = `#${fragmentEntryLinkNamespace}-account-selector-cta-container`;
const accountSelectorDropdownMenuSelector = `#${fragmentEntryLinkNamespace}-account-selector-dropdown-menu`;

function handleAccountSelectorEditClick(event) {
	const accountSelectorContainer = document.querySelector(
		accountSelectorContainerSelector
	);
	const accountSelectorCtaContainer = document.querySelector(
		accountSelectorCtaContainerSelector
	);
	const accountSelectorDropdownMenu = document.querySelector(
		accountSelectorDropdownMenuSelector
	);

	if (
		!accountSelectorContainer ||
		!accountSelectorCtaContainer ||
		!accountSelectorDropdownMenu
	) {
		return;
	}

	const shown = accountSelectorDropdownMenu.classList.contains('show');

	let willShow;

	if (isWithin(event, accountSelectorCtaContainer)) {
		willShow = !shown;
	}
	else if (shown && !isWithin(event, accountSelectorDropdownMenu)) {
		willShow = false;
	}
	else {
		return;
	}

	accountSelectorContainer.classList.toggle('show', willShow);
	accountSelectorDropdownMenu.classList.toggle('show', willShow);
	accountSelectorCtaContainer.setAttribute('aria-expanded', String(willShow));
}

function handleEditNav(nextStep) {
	if (nextStep < 0 || nextStep > panels.length - 1) {
		return;
	}

	accountSelectorDropdownHeader.dataset.step = nextStep;

	const panel = panels[nextStep];

	accountSelectorDropdownNextButton.classList.toggle(
		'invisible',
		panel.index === panels.length - 1
	);
	accountSelectorDropdownPrevButton.classList.toggle(
		'invisible',
		panel.index === 0
	);

	accountSelectorPanelSlider.style.transform = `translate(-${nextStep * configuration.dropdownWidth}px, 0)`;

	handleTitleChange(panel);
}

function handleNav(nextPanelKey) {
	const nextPanel = panels.find((panel) => panel.key === nextPanelKey);

	if (!nextPanel) {
		return;
	}

	accountSelectorDropdownHeader.dataset.activePanelKey = nextPanelKey;

	accountSelectorDropdownNextButton.classList.toggle(
		'invisible',
		nextPanel.index === panels.length - 1
	);
	accountSelectorDropdownPrevButton.classList.toggle(
		'invisible',
		nextPanel.index === 0
	);

	accountSelectorPanelSlider.style.transform = `translate(-${nextPanel.index * configuration.dropdownWidth}px, 0)`;

	handleTitleChange(nextPanel);
}

function handleTitleChange(panel) {
	const currentPanelTitle = panel.value.querySelector(
		'.account-selector-panel-drop-zone-container'
	)?.dataset?.panelTitle;

	if (currentPanelTitle) {
		accountSelectorPanelTitle.innerHTML = currentPanelTitle;
	}
}

function isWithin(event, element) {
	if (element.contains(event.target)) {
		return true;
	}

	const {bottom, left, right, top} = element.getBoundingClientRect();

	return (
		event.clientX >= left &&
		event.clientX <= right &&
		event.clientY >= top &&
		event.clientY <= bottom
	);
}

function main() {
	if (layoutMode === 'edit') {
		handleEditNav(0);

		const accountSelectorEditClickListenerKey = `${fragmentEntryLinkNamespace}AccountSelectorEditClickListener`;

		if (!window[accountSelectorEditClickListenerKey]) {
			window[accountSelectorEditClickListenerKey] = true;

			document.addEventListener(
				'click',
				handleAccountSelectorEditClick,
				true
			);
		}
	}
	else {
		Liferay.on('current-account-updated', () => window.location.reload());

		const activePanel = panels.find(
			(panel) =>
				panel.key ===
				accountSelectorDropdownHeader.dataset.activePanelKey
		);

		if (Liferay.CommerceContext.account && activePanel) {
			handleNav(panels[activePanel.index].key);
		}
		else {
			handleNav(panels[0]?.key);
		}
	}

	accountSelectorDropdownNextButton?.addEventListener('click', () => {
		if (layoutMode === 'edit') {
			const step = Number(accountSelectorDropdownHeader.dataset.step);

			handleEditNav(step + 1);
		}
		else {
			const activePanel = panels.find(
				(panel) =>
					panel.key ===
					accountSelectorDropdownHeader.dataset.activePanelKey
			);

			if (!activePanel || activePanel.index + 1 > panels.length) {
				return;
			}

			handleNav(panels[activePanel.index + 1].key);
		}
	});

	accountSelectorDropdownPrevButton?.addEventListener('click', () => {
		if (layoutMode === 'edit') {
			const step = Number(accountSelectorDropdownHeader.dataset.step);

			handleEditNav(step - 1);
		}
		else {
			const activePanel = panels.find(
				(panel) =>
					panel.key ===
					accountSelectorDropdownHeader.dataset.activePanelKey
			);

			if (!activePanel || activePanel.index - 1 < 0) {
				return;
			}

			handleNav(panels[activePanel.index - 1].key);
		}
	});
}

main();

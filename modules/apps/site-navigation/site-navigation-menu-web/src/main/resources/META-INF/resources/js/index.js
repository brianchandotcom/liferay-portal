/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	addParams,
	debounce,
	delegate,
	getFormElement,
	openSelectionModal,
	toggleDisabled,
	toggleSelectBox,
} from 'frontend-js-web';

export function NavigationMenuConfiguration({
	itemSelectorNamespace,
	namespace,
	portletResource,
	rootMenuItemEventName,
	rootMenuItemSelectorURL,
	siteNavigationMenuEventName,
	siteNavigationMenuItemSelectorURL,
}) {
	const form = document.getElementById(`${namespace}fm`);

	const displayStyle = document.getElementById(
		`${namespace}preferences--displayStyle--`
	);

	const resetPreview = (option) => {
		const displayDepthSelect = getFormElement(form, 'displayDepth');
		const displayStyleValue = option || displayStyle.value;
		const expandedLevelsSelect = getFormElement(form, 'expandedLevels');
		const rootMenuItemIdInput = getFormElement(form, 'rootMenuItemId');
		const rootMenuItemLevelSelect = getFormElement(
			form,
			'rootMenuItemLevel'
		);
		const rootMenuItemTypeSelect = getFormElement(form, 'rootMenuItemType');
		const siteNavigationMenuIdInput = getFormElement(
			form,
			'siteNavigationMenuId'
		);
		const siteNavigationMenuTypeInput = getFormElement(
			form,
			'siteNavigationMenuType'
		);

		let data = {
			preview: true,
		};

		if (
			displayDepthSelect &&
			displayStyle &&
			expandedLevelsSelect &&
			rootMenuItemIdInput &&
			rootMenuItemLevelSelect &&
			rootMenuItemTypeSelect &&
			siteNavigationMenuIdInput &&
			siteNavigationMenuTypeInput
		) {
			data.displayDepth = displayDepthSelect.value;
			data.displayStyle = displayStyleValue;
			data.expandedLevels = expandedLevelsSelect.value;
			data.rootMenuItemLevel = rootMenuItemLevelSelect.value;
			data.rootMenuItemType = rootMenuItemTypeSelect.value;
			data.rootMenuItemId = rootMenuItemIdInput.value;
			data.siteNavigationMenuId = siteNavigationMenuIdInput.value;
			data.siteNavigationMenuType = siteNavigationMenuTypeInput.value;
		}

		data = Liferay.Util.ns(`_${portletResource}_`, data);

		Liferay.Portlet.refresh(`#p_p_id_${portletResource}_`, data);
	};

	const debouncedResetPreview = debounce(resetPreview, 200);

	form.addEventListener('change', debouncedResetPreview);
	form.addEventListener('select', debouncedResetPreview);

	const chooseRootMenuItemButton = document.getElementById(
		`${namespace}chooseRootMenuItem`
	);
	const rootMenuItemIdInput = document.getElementById(
		`${namespace}rootMenuItemId`
	);
	const rootMenuItemNameSpan = document.getElementById(
		`${namespace}rootMenuItemName`
	);
	const selectSiteNavigationMenuTypeSelect = document.getElementById(
		`${namespace}selectSiteNavigationMenuType`
	);
	const siteNavigationMenuIdInput = document.getElementById(
		`${namespace}siteNavigationMenuId`
	);

	if (
		chooseRootMenuItemButton &&
		rootMenuItemIdInput &&
		rootMenuItemNameSpan &&
		selectSiteNavigationMenuTypeSelect &&
		siteNavigationMenuIdInput
	) {
		chooseRootMenuItemButton.addEventListener('click', (event) => {
			event.preventDefault();

			let uri = rootMenuItemSelectorURL;

			uri = addParams(
				`${itemSelectorNamespace}siteNavigationMenuType=${selectSiteNavigationMenuTypeSelect.value}`,
				uri
			);
			uri = addParams(
				`${itemSelectorNamespace}siteNavigationMenuId=${siteNavigationMenuIdInput.value}`,
				uri
			);

			openSelectionModal({
				height: '70vh',
				onSelect(selectedItem) {
					if (selectedItem) {
						rootMenuItemIdInput.value =
							selectedItem.selectSiteNavigationMenuItemId;
						rootMenuItemNameSpan.innerText =
							selectedItem.selectSiteNavigationMenuItemName;

						debouncedResetPreview();
					}
				},
				selectEventName: rootMenuItemEventName,
				size: 'md',
				title: Liferay.Language.get('select-site-navigation-menu-item'),
				url: uri,
			});
		});

		const chooseSiteNavigationMenuButton = document.getElementById(
			`${namespace}chooseSiteNavigationMenu`
		);
		const navigationMenuName = document.getElementById(
			`${namespace}navigationMenuName`
		);
		const removeSiteNavigationMenu = document.getElementById(
			`${namespace}removeSiteNavigationMenu`
		);

		if (
			chooseSiteNavigationMenuButton &&
			navigationMenuName &&
			removeSiteNavigationMenu &&
			rootMenuItemIdInput &&
			rootMenuItemNameSpan &&
			siteNavigationMenuIdInput
		) {
			chooseSiteNavigationMenuButton.addEventListener('click', () => {
				openSelectionModal({
					id: `${namespace}selectSiteNavigationMenu`,
					onSelect(selectedItem) {
						const itemValue = JSON.parse(selectedItem.value);

						if (itemValue) {
							navigationMenuName.innerText = itemValue.name;
							rootMenuItemIdInput.value = '0';
							rootMenuItemNameSpan.innerText = itemValue.name;
							siteNavigationMenuIdInput.value = itemValue.id;

							removeSiteNavigationMenu.classList.toggle('hide');

							debouncedResetPreview();
						}
					},
					selectEventName: siteNavigationMenuEventName,
					title: Liferay.Language.get('select-site-navigation-menu'),
					url: siteNavigationMenuItemSelectorURL,
				});
			});
		}

		const removeSiteNavigationMenuButton = document.getElementById(
			`${namespace}removeSiteNavigationMenu`
		);

		if (
			navigationMenuName &&
			removeSiteNavigationMenu &&
			removeSiteNavigationMenuButton &&
			rootMenuItemIdInput &&
			rootMenuItemNameSpan &&
			siteNavigationMenuIdInput
		) {
			removeSiteNavigationMenuButton.addEventListener('click', () => {
				navigationMenuName.innerText = '';
				rootMenuItemIdInput.value = '0';
				rootMenuItemNameSpan.innerText = '';
				siteNavigationMenuIdInput.value = '0';

				removeSiteNavigationMenu.classList.toggle('hide');

				debouncedResetPreview();
			});
		}

		toggleSelectBox(
			`${namespace}rootMenuItemType`,
			'select',
			`${namespace}rootMenuItemIdPanel`
		);

		toggleSelectBox(
			`${namespace}rootMenuItemType`,
			(currentValue) => {
				return (
					currentValue === 'absolute' || currentValue === 'relative'
				);
			},
			`${namespace}rootMenuItemLevel`
		);

		const siteNavigationMenuType = document.getElementById(
			`${namespace}siteNavigationMenuType`
		);

		if (
			rootMenuItemNameSpan &&
			selectSiteNavigationMenuTypeSelect &&
			siteNavigationMenuType
		) {
			selectSiteNavigationMenuTypeSelect.addEventListener(
				'change',
				() => {
					const selectedSelectSiteNavigationMenuType =
						document.querySelector(
							`${namespace}selectSiteNavigationMenuType option:checked`
						);

					if (selectedSelectSiteNavigationMenuType) {
						rootMenuItemNameSpan.innerText =
							selectedSelectSiteNavigationMenuType.innerText;
					}

					siteNavigationMenuType.value =
						selectSiteNavigationMenuTypeSelect.value;
				}
			);
		}

		const chooseSiteNavigationMenu = document.getElementById(
			`${namespace}chooseSiteNavigationMenu`
		);

		if (
			chooseSiteNavigationMenu &&
			navigationMenuName &&
			removeSiteNavigationMenu &&
			siteNavigationMenuIdInput &&
			siteNavigationMenuType
		) {
			delegate(
				document.getElementById(`${namespace}fm`),
				'change',
				'.select-navigation',
				() => {
					const siteNavigationDisabled =
						selectSiteNavigationMenuTypeSelect.disabled;

					toggleDisabled(
						chooseSiteNavigationMenu,
						siteNavigationDisabled
					);
					toggleDisabled(
						selectSiteNavigationMenuTypeSelect,
						!siteNavigationDisabled
					);

					navigationMenuName.innerText = '';
					siteNavigationMenuIdInput.value = 0;
					siteNavigationMenuType.value = -1;

					removeSiteNavigationMenu.classList.add('hide');

					debouncedResetPreview();
				}
			);
		}
	}

	Liferay.on('templateSelector:changedTemplate', (event) => {
		debouncedResetPreview(event.value);
	});
}

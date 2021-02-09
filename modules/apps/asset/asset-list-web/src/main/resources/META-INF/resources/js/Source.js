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

import {buildFragment, delegate} from 'frontend-js-web';

export default function ({classTypes, namespace}) {
	const mapDDMStructures = {};

	const assetMultipleSelector = document.getElementById(
		`${namespace}currentClassNameIds`
	);
	const assetSelector = document.getElementById(`${namespace}anyAssetType`);
	const ddmStructureDisplayFieldValueInput = document.getElementById(
		`${namespace}ddmStructureDisplayFieldValue`
	);
	const ddmStructureFieldNameInput = document.getElementById(
		`${namespace}ddmStructureFieldName`
	);
	const ddmStructureFieldValueInput = document.getElementById(
		`${namespace}ddmStructureFieldValue`
	);
	const orderByColumn1 = document.getElementById(
		`${namespace}orderByColumn1`
	);
	const orderByColumn2 = document.getElementById(
		`${namespace}orderByColumn2`
	);
	const orderingPanel = document.getElementById(`${namespace}ordering`);
	const saveButton = document.getElementById(`${namespace}saveButton`);
	const sourcePanel = document.querySelector('.source-container');

	const options = classTypes.reduce(
		(acc, {className}) => ({
			...acc,
			[className]: document.getElementById(
				`${namespace}${className}Options`
			),
		}),
		{}
	);

	const subtypeSelector = classTypes.reduce(
		(acc, {className}) => ({
			...acc,
			[className]: document.getElementById(
				`${namespace}anyClassType${className}`
			),
		}),
		{}
	);

	const eventDelegates = [];

	const togglePopupButtons = (enabledInput, enabledInputChecked) => {
		const popupButtons = document.querySelectorAll(
			'.asset-subtypefields-popup .btn'
		);

		if (enabledInput) {
			popupButtons.forEach((popupButton) => {
				Liferay.Util.toggleDisabled(popupButton, !enabledInputChecked);
			});
		}
	};

	const toggleSaveButton = (valueIsEmpty) => {
		if (valueIsEmpty) {
			saveButton.classList.add('disabled');
		}
		else {
			saveButton.classList.remove('disabled');
		}
		saveButton.disabled = valueIsEmpty;
	};

	const toggleSubclassesFields = (hideSubtypeFieldsWrapper, className) => {
		const selectedSubtype = subtypeSelector[className].value;
		const structureOptions = document.getElementById(
			`${namespace}${selectedSubtype}_${className}Options`
		);

		if (structureOptions) {
			structureOptions.classList.remove('hide');
		}

		const subtypeFieldsWrappers = document.querySelectorAll(
			`#${namespace}${className}subtypeFieldsWrapper, #${namespace}${className}subtypeFieldsFilterEnableWrapper`
		);

		subtypeFieldsWrappers.forEach((subtypeFieldsWrapper) => {
			if (selectedSubtype != 'false' && selectedSubtype != 'true') {
				if (orderingPanel) {
					orderingPanel
						.querySelectorAll('.order-by-subtype')
						.forEach((option) => {
							option.remove();
						});

					const optTextOrderByColumn1 =
						mapDDMStructures[
							`${className}_${selectedSubtype}_optTextOrderByColumn1`
						];
					const optTextOrderByColumn2 =
						mapDDMStructures[
							`${className}_${selectedSubtype}_optTextOrderByColumn2`
						];

					if (optTextOrderByColumn1) {
						orderByColumn1.append(
							buildFragment(optTextOrderByColumn1)
						);
					}
					if (optTextOrderByColumn2) {
						orderByColumn2.append(
							buildFragment(optTextOrderByColumn2)
						);
					}
				}

				if (structureOptions) {
					subtypeFieldsWrapper.classList.remove('hide');
				}
				else if (hideSubtypeFieldsWrapper) {
					subtypeFieldsWrapper.classList.add('hide');
				}
			}
			else if (hideSubtypeFieldsWrapper) {
				subtypeFieldsWrapper.classList.add('hide');
			}
		});
	};

	const toggle = (assetSelectorValue, {className, classNameId}) => {
		const assetOptions = assetMultipleSelector.options;
		const showOptions =
			assetSelector.value == `${classNameId}` ||
			(assetSelector.value == 'false' &&
				assetOptions.length == 1 &&
				assetOptions[0].value == `${classNameId}`);

		if (showOptions) {
			options[className].classList.remove('hide');
		}
		else {
			options[className].classList.add('hide');
		}

		if (assetSelectorValue && classNameId === assetSelectorValue) {
			toggleSubclassesFields(true, className);
		}
	};

	const toggleSubclasses = (assetSelectorValue) => {
		classTypes.forEach((classType) => {
			toggle(assetSelectorValue, classType);
		});
	};

	const setDDMFields = ({
		className,
		displayValue = '',
		message = '',
		name = '',
		value = '',
	}) => {
		ddmStructureFieldNameInput.value = name;
		ddmStructureFieldValueInput.value = value;
		ddmStructureDisplayFieldValueInput.value = displayValue;

		const ddmStructureFieldMessageContainer = document.getElementById(
			`${namespace}${className}ddmStructureFieldMessage`
		);

		if (ddmStructureFieldMessageContainer) {
			ddmStructureFieldMessageContainer.innerHTML = Liferay.Util.escape(
				message
			);
		}
	};

	classTypes.forEach(({className, classSubtypes}) => {
		Liferay.Util.toggleSelectBox(
			`${namespace}anyClassType${className}`,
			'false',
			`${namespace}${className}Boxes`
		);

		classSubtypes.forEach(({classTypeFields, classTypeId, name}) => {
			const optgroupClose = '</optgroup>';
			const optgroupOpen = `<optgroup class="order-by-subtype" label="${name}">`;

			const columnBuffer1 = [optgroupOpen];
			const columnBuffer2 = [optgroupOpen];

			classTypeFields.forEach(
				({
					label,
					selectedOrderByColumn1,
					selectedOrderByColumn2,
					value,
				}) => {
					columnBuffer1.push(
						`<option ${selectedOrderByColumn1} value="${value}">${label}</option>`
					);
					columnBuffer2.push(
						`<option ${selectedOrderByColumn2} value="${value}">${label}</option>`
					);
				}
			);

			columnBuffer1.push(optgroupClose);
			columnBuffer2.push(optgroupClose);

			mapDDMStructures[
				`${className}_${classTypeId}_optTextOrderByColumn1`
			] = columnBuffer1.join('');
			mapDDMStructures[
				`${className}_${classTypeId}_optTextOrderByColumn2`
			] = columnBuffer2.join('');
		});

		const onChangeSubtypeSelector = () => {
			const subtypeSelectorIsEmpty =
				subtypeSelector[className].value === '';

			toggleSaveButton(subtypeSelectorIsEmpty);

			setDDMFields({className});

			const enableCheckbox = document.getElementById(
				`${namespace}subtypeFieldsFilterEnabled${className}`
			);

			if (enableCheckbox) {
				enableCheckbox.checked = false;

				togglePopupButtons(enableCheckbox, false);
			}

			sourcePanel
				.querySelectorAll('.asset-subtypefields')
				.forEach((subtypeField) => {
					subtypeField.classList.add('hide');
				});

			toggleSubclassesFields(true, className);
		};

		const changeSubtypeSelector = delegate(
			sourcePanel,
			'change',
			`select#${namespace}anyClassType${className}`,
			onChangeSubtypeSelector
		);

		eventDelegates.push(changeSubtypeSelector);
	});

	toggleSubclasses(assetSelector.value);

	const onChangeAssetSelector = () => {
		const assetSelectorValueIsEmpty = assetSelector.value === '';
		toggleSaveButton(assetSelectorValueIsEmpty);

		ddmStructureFieldNameInput.value = '';
		ddmStructureFieldValueInput.value = '';

		toggleSubclasses(assetSelector.value);
	};

	const changeAssetSelector = delegate(
		sourcePanel,
		'change',
		`select#${namespace}anyAssetType`,
		onChangeAssetSelector
	);

	eventDelegates.push(changeAssetSelector);

	const enablePopupButtons = ({delegateTarget}) => {
		const enabledInput = delegateTarget.querySelector('input');

		togglePopupButtons(enabledInput, enabledInput.checked);
	};

	const clickEnablePopupButtons = delegate(
		sourcePanel,
		'click',
		'.asset-subtypefields-wrapper-enable label',
		enablePopupButtons
	);

	eventDelegates.push(clickEnablePopupButtons);

	Liferay.after('inputmoveboxes:moveItem', ({fromBox, toBox}) => {
		const id = `${namespace}currentClassNameIds`;

		if (fromBox.attr('id') === id || toBox.attr('id') === id) {
			toggleSubclasses();
		}
	});

	const openModal = ({delegateTarget}) => {
		const btn = delegateTarget.querySelector('.btn');
		let url = btn.dataset.href;

		url = Liferay.Util.addParams(
			`${namespace}ddmStructureDisplayFieldValue=${encodeURIComponent(
				ddmStructureDisplayFieldValueInput.value
			)}`,
			url
		);
		url = Liferay.Util.addParams(
			`${namespace}ddmStructureFieldName=${encodeURIComponent(
				ddmStructureFieldNameInput.value
			)}`,
			url
		);
		url = Liferay.Util.addParams(
			`${namespace}ddmStructureFieldValue=${encodeURIComponent(
				ddmStructureFieldValueInput.value
			)}`,
			url
		);

		Liferay.Util.openSelectionModal({
			customSelectEvent: true,
			id: `${namespace}selectDDMStructure${delegateTarget.id}`,
			onSelect: (selectedItem) => {
				setDDMFields(
					selectedItem.className,
					selectedItem.name,
					selectedItem.value,
					selectedItem.displayValue,
					`${selectedItem.label}: ${selectedItem.displayValue}`
				);
			},
			selectEventName: `${namespace}selectDDMStructureField`,
			title: Liferay.Util.sub(
				Liferay.Language.get('select-x'),
				Liferay.Language.get('structure-field')
			),
			url,
		});
	};

	const clickOpenModal = delegate(
		sourcePanel,
		'click',
		'.asset-subtypefields-popup',
		openModal
	);

	eventDelegates.push(clickOpenModal);

	Liferay.Util.toggleSelectBox(
		`${namespace}anyAssetType`,
		'false',
		`${namespace}classNamesBoxes`
	);

	return {
		dispose() {
			eventDelegates.forEach((eventDelegate) => eventDelegate.dispose());
		},
	};
}

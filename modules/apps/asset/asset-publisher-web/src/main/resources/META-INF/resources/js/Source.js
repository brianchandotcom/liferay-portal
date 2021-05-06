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

import {delegate} from 'frontend-js-web';

const ANY = 'any';
const SELECT_MORE_THAN_ONE = 'select-more-than-one';

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

	const createElement = (label, classNames, attributes, content) => {
		const element = document.createElement(label);

		if (classNames) {
			element.classList.add(classNames);
		}

		Object.keys(attributes).forEach((key) => {
			element.setAttribute(key, attributes[key]);
		});

		if (content) {
			element.innerHTML = content;
		}

		return element;
	};

	const createOptgroup = (attributes) =>
		createElement('optgroup', 'order-by-subtype', attributes);

	const createOption = (attributes, content) =>
		createElement('option', null, attributes, content);

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

	const removeOptionsOrderByFilter = () => {
		const options = orderingPanel.querySelectorAll('.order-by-subtype');
		options.forEach((option) => option.remove());
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
			if (
				selectedSubtype !== ANY &&
				selectedSubtype !== SELECT_MORE_THAN_ONE
			) {
				if (orderingPanel) {
					removeOptionsOrderByFilter();

					const optOrderByColumn1 =
						mapDDMStructures[
							`${className}_${selectedSubtype}_column1`
						];
					const optOrderByColumn2 =
						mapDDMStructures[
							`${className}_${selectedSubtype}_column2`
						];

					if (optOrderByColumn1) {
						orderByColumn1.append(optOrderByColumn1);
					}
					if (optOrderByColumn2) {
						orderByColumn2.append(optOrderByColumn2);
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
			assetSelector.value === `${classNameId}` ||
			(assetSelector.value === SELECT_MORE_THAN_ONE &&
				assetOptions.length === 1 &&
				assetOptions[0].value === `${classNameId}`);

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
			SELECT_MORE_THAN_ONE,
			`${namespace}${className}Boxes`
		);

		classSubtypes.forEach(({classTypeFields, classTypeId, name}) => {
			const columnBuffer1 = createOptgroup({label: name});
			const columnBuffer2 = createOptgroup({label: name});

			classTypeFields.forEach(
				({
					label,
					selectedOrderByColumn1,
					selectedOrderByColumn2,
					value,
				}) => {
					const option1 = createOption(
						{
							value,
							...(selectedOrderByColumn1 && {
								selected: '',
							}),
						},
						label
					);

					const option2 = createOption(
						{
							value,
							...(selectedOrderByColumn2 && {
								selected: '',
							}),
						},
						label
					);

					columnBuffer1.append(option1);
					columnBuffer2.append(option2);
				}
			);

			mapDDMStructures[
				`${className}_${classTypeId}_column1`
			] = columnBuffer1;

			mapDDMStructures[
				`${className}_${classTypeId}_column2`
			] = columnBuffer2;
		});

		const onChangeSubtypeSelector = () => {
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
		ddmStructureFieldNameInput.value = '';
		ddmStructureFieldValueInput.value = '';

		const classTypeSelected = classTypes.find(
			({classNameId}) => classNameId === assetSelector.value
		);

		if (classTypeSelected) {
			const enableCheckbox = document.getElementById(
				`${namespace}subtypeFieldsFilterEnabled${classTypeSelected.className}`
			);

			if (enableCheckbox) {
				togglePopupButtons(enableCheckbox, enableCheckbox.checked);
			}
		}

		if (orderingPanel) {
			removeOptionsOrderByFilter();
		}

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
		let url = delegateTarget.dataset.href;

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
				setDDMFields({
					className: selectedItem.className,
					displayValue: selectedItem.displayValue,
					message: `${selectedItem.label}: ${selectedItem.displayValue}`,
					name: selectedItem.name,
					value: selectedItem.value,
				});
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
		'.asset-subtypefields-popup .btn',
		openModal
	);

	eventDelegates.push(clickOpenModal);

	Liferay.Util.toggleSelectBox(
		`${namespace}anyAssetType`,
		SELECT_MORE_THAN_ONE,
		`${namespace}classNamesBoxes`
	);

	return {
		dispose() {
			eventDelegates.forEach((eventDelegate) => eventDelegate.dispose());
		},
	};
}

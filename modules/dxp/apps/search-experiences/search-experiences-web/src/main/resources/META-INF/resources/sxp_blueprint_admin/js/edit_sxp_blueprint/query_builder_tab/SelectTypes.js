/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import React, {useContext, useState} from 'react';

import ThemeContext from '../../shared/ThemeContext';
import SearchableTypesModal from './SearchableTypesModal';
import SelectSubtypes from './SelectSubtypes';

/**
 * Grabs all the selected subtypes of a certain type.
 *
 * @param {array} selected
 * @param {string} className
 * @returns {array}
 */
const getSelectedSubtypes = (selected, className) => {
	return selected.find((item) => item.type === className)?.subtypes;
};

/**
 * Turns a flat list of types and subtypes into a nested array of the types
 * with its subtypes as children.
 *
 * @param {*} selectedTypes
 * @returns {array}
 */
const setupSelected = (selectedTypes, subtypeMap = {}) => {

	// TODO: Fetch subtypes map

	const arrayOfTypes = [];

	selectedTypes.forEach((item) => {
		if (!item.includes('#')) {
			arrayOfTypes.push({subtypes: [], type: item});
		}
		else {
			const typeClassName = item.split('#')[0];

			const itemIndex = arrayOfTypes.findIndex(
				({type}) => type === typeClassName
			);

			if (itemIndex > -1) {
				const subtypesArray = arrayOfTypes[itemIndex].subtypes || [];

				subtypesArray.push({
					label: subtypeMap[item] || item,
					value: item,
				});

				arrayOfTypes[itemIndex] = {
					subtypes: subtypesArray,
					type: typeClassName,
				};
			}
			else {
				arrayOfTypes.push({
					subtypes: [{label: subtypeMap[item] || item, value: item}],
					type: typeClassName,
				});
			}
		}
	});

	return arrayOfTypes;
};

/**
 * Transforms the selected types and subtypes into a flat array of types.
 *
 * @param {*} selected
 * @returns Array
 */
const transformSelected = (selected) => {
	const newSelected = [];

	selected.forEach((type) => {
		if (type.subtypes.length) {
			type.subtypes.forEach(({value}) => {
				newSelected.push(value);
			});
		}
		else {
			newSelected.push(type.type);
		}
	});

	return newSelected;
};

function SelectTypes({
	onFrameworkConfigChange,
	onFetchSearchableTypes,
	searchableTypes = [],
	selectedTypes = [],
}) {
	const {locale} = useContext(ThemeContext);

	const [selected, setSelected] = useState(setupSelected(selectedTypes));

	const mainSearchableTypesSorted = searchableTypes.sort((a, b) =>
		a.displayName.localeCompare(b.displayName, locale.replaceAll('_', '-'))
	);

	const _handleRemoveType = (className) => {
		const newSelected = selected.filter(({type}) => type !== className);

		setSelected(newSelected);

		onFrameworkConfigChange({
			searchableAssetTypes: transformSelected(newSelected),
		});
	};

	const _handleRemoveSubtype = (subtype) => {
		const newSelected = selected.map(({subtypes, type}) => ({
			subtypes: subtypes.filter(({value}) => value !== subtype),
			type,
		}));

		_handleChangeSelected(newSelected);
	};

	const _handleChangeSubtypes = (type) => (subtypes) => {
		const newSelected = selected.map((item) => {

			// Handles changing the subtypes of one type

			if (item.type === type) {
				return {
					subtypes,
					type,
				};
			}

			return item;
		});

		_handleChangeSelected(newSelected);
	};

	const _handleChangeTypes = (types) => {
		const newSelected = types.map((type) => {

			// Check if the type already exists in the selected array.
			// If it does, return the object, otherwise return a brand new object.

			const prevTypeObject = selected.find((item) => item.type === type);

			return prevTypeObject || {subtypes: [], type};
		});

		_handleChangeSelected(newSelected);
	};

	const _handleChangeSelected = (newSelected) => {
		setSelected(newSelected);

		onFrameworkConfigChange({
			searchableAssetTypes: transformSelected(newSelected),
		});
	};

	return (
		<>
			<SearchableTypesModal
				initialSelectedTypes={selected.map(({type}) => type)}
				onChangeTypes={_handleChangeTypes}
				onFetchSearchableTypes={onFetchSearchableTypes}
				searchableTypes={mainSearchableTypesSorted}
			>
				<ClayButton
					className="select-types-button"
					displayType="secondary"
					small
				>
					{Liferay.Language.get('select-asset-types')}
				</ClayButton>
			</SearchableTypesModal>

			{!!selected.length && (
				<ClayList>
					{mainSearchableTypesSorted
						.filter(({className}) =>
							selected.some(({type}) => type === className)
						)
						.map(({className, displayName, hasSubtypes = true}) => (
							<ClayList.Item flex key={className}>
								<ClayList.ItemField expand>
									<ClayList.ItemTitle>
										{displayName}
									</ClayList.ItemTitle>

									{hasSubtypes && (
										<SelectSubtypes
											className={className}
											onChangeSubtypes={_handleChangeSubtypes(
												className
											)}
											onRemoveSubtype={
												_handleRemoveSubtype
											}
											selectedSubtypes={getSelectedSubtypes(
												selected,
												className
											)}
										/>
									)}
								</ClayList.ItemField>

								<ClayList.ItemField>
									<ClayButton
										aria-label={Liferay.Language.get(
											'delete'
										)}
										className="secondary"
										displayType="unstyled"
										onClick={() =>
											_handleRemoveType(className)
										}
										size="sm"
										style={{margin: 'auto'}}
									>
										<ClayIcon symbol="times-circle" />
									</ClayButton>
								</ClayList.ItemField>
							</ClayList.Item>
						))}
				</ClayList>
			)}
		</>
	);
}

export default React.memo(SelectTypes);

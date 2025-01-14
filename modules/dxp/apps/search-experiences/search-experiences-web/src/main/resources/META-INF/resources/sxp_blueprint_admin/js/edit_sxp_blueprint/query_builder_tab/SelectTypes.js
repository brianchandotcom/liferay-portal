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
 * with its subtypes as children. This is called when setting up the 'selected'
 * state and allows for easier manipulation of the data.
 *
 * @param {*} initialSelectedTypes
 * @param {*} ddmStructureMap
 * @returns {array}
 */
const setupSelected = (initialSelectedTypes, ddmStructureMap = {}) => {
	const selected = [];

	initialSelectedTypes.forEach((item) => {
		const selectedTypes = item.split('#');

		if (selectedTypes.length < 3) {
			selected.push({subtypes: [], type: item});
		}
		else {
			const typeClassName = selectedTypes[0];

			const itemIndex = selected.findIndex(
				({type}) => type === typeClassName
			);

			if (itemIndex > -1) {
				const subtypesArray = selected[itemIndex].subtypes || [];

				subtypesArray.push({
					label: ddmStructureMap[item] || item,
					value: item,
				});

				selected[itemIndex] = {
					subtypes: subtypesArray,
					type: typeClassName,
				};
			}
			else {
				selected.push({
					subtypes: [
						{label: ddmStructureMap[item] || item, value: item},
					],
					type: typeClassName,
				});
			}
		}
	});

	return selected;
};

/**
 * Transforms the selected types and subtypes into a flat array that
 * the backend expects for submission.
 *
 * @param {*} newSelected
 * @returns {array}
 */
const transformSelected = (newSelected) => {
	const newSearchableAssetTypes = [];

	newSelected.forEach(({subtypes, type}) => {
		if (subtypes.length) {
			newSearchableAssetTypes.push(...subtypes.map(({value}) => value));
		}
		else {
			newSearchableAssetTypes.push(type);
		}
	});

	return newSearchableAssetTypes;
};

function SelectTypes({
	onDDMStructureMapChange,
	onFrameworkConfigChange,
	onFetchSearchableTypes,
	searchableTypes = [],
	initialSelectedTypes = [],
	ddmStructureMap,
}) {
	const {locale} = useContext(ThemeContext);

	const [selected, setSelected] = useState(
		setupSelected(initialSelectedTypes, ddmStructureMap)
	);

	const mainSearchableTypesSorted = searchableTypes.sort((a, b) =>
		a.displayName.localeCompare(b.displayName, locale.replaceAll('_', '-'))
	);

	const _handleRemoveType = (className) => {
		const newSelected = selected.filter(({type}) => type !== className);

		_handleChangeSelected(newSelected);
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

		// If any new subtypes are in this array, they should be
		// added to the ddmStructure map.

		onDDMStructureMapChange(subtypes);
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
						.map(({className, displayName, hasSubtype = true}) => (
							<ClayList.Item flex key={className}>
								<ClayList.ItemField expand>
									<ClayList.ItemTitle>
										{displayName}
									</ClayList.ItemTitle>

									{hasSubtype && (
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

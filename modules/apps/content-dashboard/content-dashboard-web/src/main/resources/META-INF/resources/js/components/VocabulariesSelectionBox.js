/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayDualListBox} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

const MAX_VOCABULARIES_ON_GRAPH = 2;

const getItemByvalue = (array, value) =>
	array.find((item) => item.value === value);

const VocabulariesSelectionBox = ({
	leftBoxName,
	leftList,
	portletNamespace,
	rightBoxName,
	rightList,
}) => {
	const [items, setItems] = useState([leftList, rightList]);
	const [leftSelected, setLeftSelected] = useState([]);
	const [rightSelected, setRightSelected] = useState([]);

	const [leftElements, rightElements] = items;

	const selectorRef = useRef();

	const previousLeftElementsRef = useRef();
	useEffect(() => {
		previousLeftElementsRef.current = leftElements;
	});

	const enableAllOptions = () => {
		const options = Array.from(
			selectorRef.current.querySelectorAll(
				'.vocabularies-selection option'
			)
		);

		options.forEach((option) => {
			option.disabled = false;
		});
	};

	const disableOptionsFromOtherSites = (vocabulary) => {
		leftElements.forEach((elem) => {
			if (!elem.global && elem.site !== vocabulary.site) {
				selectorRef.current.querySelector(
					`option[value='${elem.value}']`
				).disabled = true;
			}
		});
	};

	const disableNonSelectableOptions = () => {
		const selectedVocabulaboriesFromNonGlobalSite = rightElements.filter(
			(elem) => !elem.global
		);

		if (selectedVocabulaboriesFromNonGlobalSite.length) {
			disableOptionsFromOtherSites(
				selectedVocabulaboriesFromNonGlobalSite[0]
			);
		}
	};

	const handleLeftSelectionChange = () => {
		leftSelected.forEach((selectedVocabularyValue) => {
			const vocabulary = getItemByvalue(
				leftElements,
				selectedVocabularyValue
			);

			if (!vocabulary.global) {
				disableOptionsFromOtherSites(vocabulary);
			}
		});
	};

	const handleDisableLeftToRight = () => {
		const noRoomForCurrentSelection =
			leftSelected.length + rightElements.length >
			MAX_VOCABULARIES_ON_GRAPH;

		if (
			leftSelected.length < MAX_VOCABULARIES_ON_GRAPH &&
			!noRoomForCurrentSelection
		) {
			return false;
		}

		const itemsAsFlattenedArray = items.flat();
		const firstSelectedItemAsControlItem = getItemByvalue(
			itemsAsFlattenedArray,
			leftSelected[0]
		);

		const allSitesAreNonGlobal = leftSelected.every((itemValue) => {
			const currentItem = getItemByvalue(
				itemsAsFlattenedArray,
				itemValue
			);

			return !currentItem?.global;
		});

		const mixedNonGlobalSites = leftSelected.some((itemValue) => {
			const currentItem = getItemByvalue(
				itemsAsFlattenedArray,
				itemValue
			);

			return currentItem.site !== firstSelectedItemAsControlItem.site;
		});

		return (
			noRoomForCurrentSelection ||
			(allSitesAreNonGlobal && mixedNonGlobalSites)
		);
	};

	const maintainSelectedVocabularies = () => {
		const previousLeftElements = previousLeftElementsRef.current;

		if (typeof previousLeftElements === 'undefined') {
			return;
		}

		if (previousLeftElements.length < leftElements.length) {
			const intersection = leftElements.filter(
				(x) => !previousLeftElements.includes(x)
			);
			const newLeftSelection = intersection.map((elem) => elem.value);
			previousLeftElementsRef.current = leftElements;
			setLeftSelected(newLeftSelection);
		}
		else if (previousLeftElements.length > leftElements.length) {
			const intersection = previousLeftElements.filter(
				(x) => !leftElements.includes(x)
			);
			const newRightSelection = intersection.map((elem) => elem.value);
			previousLeftElementsRef.current = leftElements;
			setRightSelected(newRightSelection);
		}
	};

	maintainSelectedVocabularies();

	useEffect(() => {
		enableAllOptions();
		disableNonSelectableOptions();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [items]);

	useEffect(() => {
		enableAllOptions();
		disableNonSelectableOptions();
		handleLeftSelectionChange();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [leftSelected]);

	return (
		<div ref={selectorRef}>
			<ClayDualListBox
				className="vocabularies-selection"
				disableLTR={handleDisableLeftToRight()}
				disableRTL={!rightElements.length}
				items={items}
				left={{
					id: leftBoxName,
					label: Liferay.Language.get('available'),
					onSelectChange: setLeftSelected,
					selected: leftSelected,
				}}
				onItemsChange={setItems}
				right={{
					id: `${portletNamespace}${rightBoxName}`,
					label: Liferay.Language.get('in-use'),
					onSelectChange: setRightSelected,
					selected: rightSelected,
				}}
			/>

			<input
				name={`${portletNamespace}assetVocabularyIds`}
				readOnly
				type="hidden"
				value={rightElements
					.map((rightItem) => rightItem.value)
					.join(',')}
			/>
		</div>
	);
};

VocabulariesSelectionBox.propTypes = {
	leftBoxName: PropTypes.string.isRequired,
	leftList: PropTypes.array.isRequired,
	portletNamespace: PropTypes.string.isRequired,
	rightBoxName: PropTypes.string.isRequired,
	rightList: PropTypes.array.isRequired,
};

export default VocabulariesSelectionBox;

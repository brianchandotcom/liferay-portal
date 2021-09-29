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

import {ClayDualListBox} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

const addLabel = (myArray) => {
	let myArrayWithLabel = [];
	myArray.forEach((elem) => {
		myArrayWithLabel.push({...elem, label: elem.value});
	});

	return myArrayWithLabel;
};

const addFakeSiteId = (vocabularies) => {
	let myArrayWithSiteId = [];
	vocabularies.forEach((elem) => {
		myArrayWithSiteId.push({...elem, site: Math.floor(Math.random() * 3)});
	});

	return myArrayWithSiteId;
};

const VocabulariesSelectionBox = ({
	leftBoxName,
	leftList,
	rightBoxName,
	rightList,
}) => {
	const [items, setItems] = useState([
		addFakeSiteId(addLabel(leftList)),
		addFakeSiteId(addLabel(rightList)),
	]);

	const [leftElements, rightElements] = items;

	useEffect(() => {
		let selectedVocabulaboriesFromNonGlobalSite = rightElements.filter(
			(elem) => elem.site !== 0
		);

		// Enable all available options
		document.querySelector(
			'.vocabularies-selection option'
		).disabled = false;

		// Disable options that are not selectable
		if (selectedVocabulaboriesFromNonGlobalSite.length) {
			leftElements.forEach((elem) => {
				if (
					elem.site !== 0 &&
					elem.site !==
						selectedVocabulaboriesFromNonGlobalSite[0].site
				) {
					document.querySelector(
						`option[value='${elem.value}']`
					).disabled = true;
				}
			});
		}
	});

	return (
		<ClayDualListBox
			className="vocabularies-selection"
			disableLTR={rightElements.length >= 2}
			disableRTL={rightElements.length === 0}
			items={items}
			left={{
				id: leftBoxName,
				label: Liferay.Language.get('available'),
			}}
			onItemsChange={setItems}
			right={{
				id: rightBoxName,
				label: Liferay.Language.get('in-use'),
			}}
		/>
	);
};

VocabulariesSelectionBox.propTypes = {
	leftBoxName: PropTypes.string,
	leftList: PropTypes.array.isRequired,
	rightBoxName: PropTypes.string,
	rightList: PropTypes.array.isRequired,
};

export default VocabulariesSelectionBox;

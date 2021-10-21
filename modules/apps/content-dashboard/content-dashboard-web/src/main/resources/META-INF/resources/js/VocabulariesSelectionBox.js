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
import React, {useEffect, useRef, useState} from 'react';

const VocabulariesSelectionBox = ({
	leftBoxName,
	leftList,
	portletNamespace,
	rightBoxName,
	rightList,
}) => {
	const [items, setItems] = useState([leftList, rightList]);

	const [leftElements, rightElements] = items;

	const selectorRef = useRef();

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

	const disableNonSelectableOptions = () => {
		const selectedVocabulaboriesFromNonGlobalSite = rightElements.filter(
			(elem) => !elem.global
		);

		if (selectedVocabulaboriesFromNonGlobalSite.length) {
			leftElements.forEach((elem) => {
				if (
					!elem.global &&
					elem.site !==
						selectedVocabulaboriesFromNonGlobalSite[0].site
				) {
					selectorRef.current.querySelector(
						`option[value='${elem.value}']`
					).disabled = true;
				}
			});
		}
	};

	useEffect(() => {
		enableAllOptions();
		disableNonSelectableOptions();
	});

	return (
		<div ref={selectorRef}>
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
					id: `${portletNamespace}${rightBoxName}`,
					label: Liferay.Language.get('in-use'),
				}}
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

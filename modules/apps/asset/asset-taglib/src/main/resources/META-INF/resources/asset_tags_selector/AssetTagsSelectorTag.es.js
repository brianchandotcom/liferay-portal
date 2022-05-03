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

import PropTypes from 'prop-types';
import React, {useState} from 'react';

import AssetTagsSelector from './AssetTagsSelector.es';

function AssetTagsSelectorTag({
	addCallback,
	eventName,
	groupIds = [],
	id,
	initialInputValue = '',
	initialSelectedItems = [],
	inputName,
	label,
	portletURL,
	removeCallback,
	showSelectButton,
}) {
	const [selectedItems, setSelectedItems] = useState(initialSelectedItems);
	const [inputValue, setInputValue] = useState(initialInputValue);

	return (
		<AssetTagsSelector
			addCallback={addCallback}
			eventName={eventName}
			groupIds={groupIds}
			id={id}
			inputName={inputName}
			inputValue={inputValue}
			label={label}
			onInputValueChange={setInputValue}
			onSelectedItemsChange={setSelectedItems}
			portletURL={portletURL}
			removeCallback={removeCallback}
			selectedItems={selectedItems}
			showSelectButton={showSelectButton}
		/>
	);
}

AssetTagsSelectorTag.propTypes = {
	addCallback: PropTypes.string,
	eventName: PropTypes.string,
	groupIds: PropTypes.array,
	id: PropTypes.string,
	initialInputValue: PropTypes.string,
	initialSelectedItems: PropTypes.array,
	inputName: PropTypes.string,
	label: PropTypes.string,
	portletURL: PropTypes.string,
	removeCallback: PropTypes.string,
};

export default function (props) {
	return (
		<AssetTagsSelectorTag
			{...props}
			initialInputValue={props.inputValue}
			initialSelectedItems={props.selectedItems}
		/>
	);
}

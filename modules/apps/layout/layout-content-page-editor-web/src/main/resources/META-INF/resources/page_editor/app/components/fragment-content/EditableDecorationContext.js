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
import React, {useCallback, useContext, useMemo, useReducer} from 'react';

import EditableDecorationMask from './EditableDecorationMask';

const EditableDecorationContext = React.createContext(null);

const REMOVE_ITEM = 'REMOVE_ITEM';
const SET_ITEM = 'SET_ITEM';

const collectionReducer = (collection, action) => {
	if (action.type === SET_ITEM && collection[action.itemId] !== action.item) {
		return {...collection, [action.itemId]: action.item};
	}
	else if (action.type === REMOVE_ITEM && collection[action.itemId]) {
		const newCollection = {...collection};
		delete newCollection[action.itemId];

		return newCollection;
	}

	return collection;
};

const useCollection = () => {
	const [collection, dispatch] = useReducer(collectionReducer, {});

	const removeItem = useCallback(
		itemId => dispatch({itemId, type: REMOVE_ITEM}),
		[]
	);

	const setItem = useCallback(
		(itemId, item) => dispatch({item, itemId, type: SET_ITEM}),
		[dispatch]
	);

	return [collection, removeItem, setItem];
};

export function EditableDecorationProvider({children}) {
	const [elements, removeElement, setElement] = useCollection();
	const [classNames, removeClassName, setClassName] = useCollection();

	const decorationContextValue = useMemo(
		() => ({
			registerElement: (editableId, node) => {
				setElement(editableId, node);
			},

			unregisterElement: editableId => {
				removeClassName(editableId);
				removeElement(editableId);
			},

			updateClassName: (editableId, className) => {
				setClassName(editableId, className);
			}
		}),
		[removeClassName, removeElement, setClassName, setElement]
	);

	return (
		<>
			<EditableDecorationMask
				classNames={classNames}
				elements={elements}
			/>

			<EditableDecorationContext.Provider value={decorationContextValue}>
				{children}
			</EditableDecorationContext.Provider>
		</>
	);
}

EditableDecorationProvider.propTypes = {
	children: PropTypes.node
};

export function useEditableDecoration() {
	const {registerElement, unregisterElement, updateClassName} = useContext(
		EditableDecorationContext
	);

	return useMemo(
		() => ({
			registerElement,
			unregisterElement,
			updateClassName
		}),
		[registerElement, unregisterElement, updateClassName]
	);
}

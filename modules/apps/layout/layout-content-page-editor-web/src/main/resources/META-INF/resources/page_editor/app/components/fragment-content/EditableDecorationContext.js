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
import React, {useContext, useMemo, useState} from 'react';

import EditableDecorationMask from './EditableDecorationMask';

const EditableDecorationContext = React.createContext(null);

export function EditableDecorationProvider({children}) {
	const [elements, setElements] = useState({});
	const [classNames, setClassNames] = useState({});

	const decorationContextValue = useMemo(
		() => ({
			registerElement: (editableId, node) => {
				setElements(_elements => ({..._elements, [editableId]: node}));
			},

			unregisterElement: editableId => {
				setClassNames(_classNames => {
					const newClassNames = {..._classNames};

					delete newClassNames[editableId];

					return newClassNames;
				});

				setElements(_elements => {
					const newElements = {..._elements};

					delete newElements[editableId];

					return newElements;
				});
			},

			updateClassName: (editableId, className) => {
				setClassNames(_classNames => ({
					..._classNames,
					[editableId]: className
				}));
			}
		}),
		[]
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

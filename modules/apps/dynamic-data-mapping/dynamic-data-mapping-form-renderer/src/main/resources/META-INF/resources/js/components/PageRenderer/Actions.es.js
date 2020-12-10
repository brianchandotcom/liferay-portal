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

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import React, {
	createContext,
	forwardRef,
	useContext,
	useEffect,
	useState,
} from 'react';

import {EVENT_TYPES} from '../../actions/eventTypes.es';
import {useForm} from '../../hooks/useForm.es';

const ActionsContext = createContext({
	activeField: '',
	hoveredField: '',
	setActiveField: () => {},
	setHoveredField: () => {},
});

/**
 * ActionsContext is responsible for store which field is being hovered or active
 */
export const ActionsProvider = ({children}) => {
	const [hoveredField, setHoveredField] = useState('');
	const [activeField, setActiveField] = useState('');
	const dispatch = useForm();

	useEffect(() => {
		if (hoveredField) {
			return dispatch({
				payload: {fieldName: hoveredField},
				type: EVENT_TYPES.FIELD_HOVERED,
			});
		}
	}, [hoveredField, dispatch]);

	return (
		<ActionsContext.Provider
			value={{activeField, hoveredField, setActiveField, setHoveredField}}
		>
			{children}
		</ActionsContext.Provider>
	);
};

export const useActions = () => {
	return useContext(ActionsContext);
};

ActionsContext.displayName = 'ActionsContext';

const Actions = forwardRef(({activePage, children, expanded, field}, ref) => {
	const {setActiveField, setHoveredField} = useActions();

	const dispatch = useForm();

	const DROPDOWN_ACTIONS = [
		{
			label: Liferay.Language.get('duplicate'),
			onClick: () =>
				dispatch({
					payload: {activePage, fieldName: field.fieldName},
					type: EVENT_TYPES.FIELD_DUPLICATED,
				}),
		},
		{
			label: Liferay.Language.get('delete'),
			onClick: () =>
				dispatch({
					payload: {activePage, fieldName: field.fieldName},
					type: EVENT_TYPES.FIELD_DELETED,
				}),
		},
	];

	const handleFieldInteractions = (event) => {
		event.stopPropagation();

		if (ref.current?.contains(event.target)) {
			return;
		}

		switch (event.type) {
			case 'click':
				dispatch({
					payload: {activePage, fieldName: field.fieldName},
					type: EVENT_TYPES.FIELD_CLICKED,
				});

				setActiveField(field.fieldName);

				break;

			case 'mouseover':
				setHoveredField(field.fieldName);

				break;

			case 'mouseleave':
				setHoveredField('');

				break;

			default:
				break;
		}
	};

	return (
		<div
			className="row"
			onClick={handleFieldInteractions}
			onMouseLeave={handleFieldInteractions}
			onMouseOver={handleFieldInteractions}
		>
			{expanded && (
				<div className="ddm-field-actions-container" ref={ref}>
					<span className="actions-label">{field.label}</span>

					<ClayDropDownWithItems
						className="dropdown-action"
						items={DROPDOWN_ACTIONS}
						trigger={
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get('actions')}
								data-title={Liferay.Language.get('actions')}
								displayType="unstyled"
								symbol="ellipsis-v"
							/>
						}
					/>
				</div>
			)}

			{children}
		</div>
	);
});

Actions.displayName = 'Actions';

export default Actions;

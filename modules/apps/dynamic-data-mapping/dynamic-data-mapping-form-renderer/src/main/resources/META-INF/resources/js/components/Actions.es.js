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
import classNames from 'classnames';
import domAlign from 'dom-align';
import React, {
	createContext,
	forwardRef,
	useContext,
	useEffect,
	useLayoutEffect,
	useState,
} from 'react';

import {EVENT_TYPES} from '../actions/eventTypes.es';
import {useForm} from '../hooks/useForm.es';
import {useResizeObserver} from '../hooks/useResizeObserver.es';

const ActionsContext = createContext({});

/**
 * ActionsContext is responsible for store which field is being hovered or active
 */
export const ActionsProvider = ({children}) => {
	const [hoveredField, setHoveredField] = useState('');
	const [activeField, setActiveField] = useState('');
	const dispatch = useForm();

	// App Builder needs information when the field is hovered, it
	// will be removed later.

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

const ACTIONS_CONTAINER_OFFSET = [0, 1];

export const ActionsControls = ({
	actionsRef,
	activePage,
	children,
	columnRef,
	editable,
	field,
}) => {
	const {
		activeField,
		hoveredField,
		setActiveField,
		setHoveredField,
	} = useActions();

	const dispatch = useForm();

	const contentRect = useResizeObserver(columnRef);

	useLayoutEffect(() => {
		if (actionsRef.current && columnRef.current) {
			domAlign(actionsRef.current, columnRef.current, {
				offset: ACTIONS_CONTAINER_OFFSET,
				points: ['bl', 'tl'],
			});
		}
	}, [actionsRef, activeField, columnRef, contentRect, hoveredField]);

	const handleFieldInteractions = (event) => {
		event.stopPropagation();

		if (actionsRef.current?.contains(event.target) && !editable) {
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

	return React.cloneElement(children, {
		onClick: handleFieldInteractions,
		onMouseLeave: handleFieldInteractions,
		onMouseOver: handleFieldInteractions,
	});
};

export const Actions = forwardRef(
	({activePage, expanded, field, isFieldSet}, actionsRef) => {
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

		if (expanded) {
			return (
				<div
					className={classNames('ddm-field-actions-container', {
						'ddm-fieldset': isFieldSet,
					})}
					ref={actionsRef}
				>
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
			);
		}

		return null;
	}
);

ActionsControls.displayName = 'ActionsControls';
Actions.displayName = 'Actions';

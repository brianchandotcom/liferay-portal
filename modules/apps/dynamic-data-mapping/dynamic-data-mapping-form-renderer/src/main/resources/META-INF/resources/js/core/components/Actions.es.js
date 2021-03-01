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
	useCallback,
	useContext,
	useEffect,
	useLayoutEffect,
	useMemo,
	useReducer,
} from 'react';

import {useConfig} from '../../core/hooks/useConfig.es';
import {EVENT_TYPES} from '../actions/eventTypes.es';
import {useForm} from '../hooks/useForm.es';
import {useResizeObserver} from '../hooks/useResizeObserver.es';

const ActionsContext = createContext({});

const ACTIONS_TYPES = {
	ACTIVE: 'ACTIVE',
	HOVER: 'hover',
};

const ACTIONS_INITAL_REDUCER = {
	activeId: null,
	hoveredId: null,
};

const reducer = (state, action) => {
	switch (action.type) {
		case ACTIONS_TYPES.ACTIVE:
			return {
				...state,
				activeId: action.payload,
			};
		case ACTIONS_TYPES.HOVER:
			return {
				...state,
				hoveredId: action.payload,
			};
		default:
			return state;
	}
};

/**
 * ActionsContext is responsible for store which field is being hovered or active
 */
export const ActionsProvider = ({actions, children, focusedFieldId}) => {
	const [state, dispatch] = useReducer(reducer, ACTIONS_INITAL_REDUCER);
	const dispatchForm = useForm();

	const newDispatch = useCallback(
		({payload: {activePage, field}, type}) => {
			switch (type) {
				case ACTIONS_TYPES.ACTIVE:
					dispatchForm({
						payload: {activePage, field},
						type: EVENT_TYPES.FIELD.CLICK,
					});
					break;

				// App Builder needs information when the field is hovered, it
				// will be removed later.

				case ACTIONS_TYPES.HOVER: {
					dispatchForm({
						payload: {activePage, fieldName: field?.fieldName},
						type: EVENT_TYPES.FIELD.HOVER,
					});
					break;
				}
				default:
					break;
			}

			dispatch({payload: field?.fieldName, type});
		},
		[dispatchForm, dispatch]
	);

	useEffect(() => {
		dispatch({payload: focusedFieldId, type: ACTIONS_TYPES.ACTIVE});
	}, [focusedFieldId]);

	return (
		<ActionsContext.Provider
			value={{actions, dispatch: newDispatch, state}}
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
	field,
}) => {
	const {
		dispatch,
		state: {activeId, hoveredId},
	} = useActions();
	const contentRect = useResizeObserver(columnRef);

	useLayoutEffect(() => {
		if (actionsRef.current && columnRef.current) {
			domAlign(actionsRef.current, columnRef.current, {
				offset: ACTIONS_CONTAINER_OFFSET,
				points: ['bl', 'tl'],
			});
		}
	}, [actionsRef, columnRef, contentRect, hoveredId, activeId]);

	const handleFieldInteractions = (event) => {
		event.stopPropagation();

		if (
			actionsRef.current?.contains(event.target) ||
			!columnRef.current?.contains(event.target)
		) {
			return;
		}

		switch (event.type) {
			case 'click':
				dispatch({
					payload: {activePage, field},
					type: ACTIONS_TYPES.ACTIVE,
				});

				break;

			case 'mouseover':
				dispatch({
					payload: {activePage, field},
					type: ACTIONS_TYPES.HOVER,
				});

				break;

			case 'mouseleave':
				dispatch({
					payload: {activePage, field: null},
					type: ACTIONS_TYPES.HOVER,
				});

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
	(
		{activePage, fieldId, fieldType, isFieldSet, parentFieldName},
		actionsRef
	) => {
		const {fieldTypes} = useConfig();
		const {actions} = useActions();

		const label = useMemo(() => {
			if (isFieldSet) {
				return Liferay.Language.get('fieldset');
			}

			return fieldTypes.find(({name}) => name === fieldType).label;
		}, [fieldType, isFieldSet, fieldTypes]);

		return (
			<div
				className={classNames('ddm-field-actions-container', {
					'ddm-fieldset': isFieldSet,
				})}
				ref={actionsRef}
			>
				<span className="actions-label">{label}</span>

				<ClayDropDownWithItems
					className="dropdown-action"
					items={actions.map(({action, ...otherProps}) => ({
						onClick: () =>
							action({
								activePage,
								fieldName: fieldId,
								parentFieldName,
							}),
						...otherProps,
					}))}
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
);

ActionsControls.displayName = 'ActionsControls';
Actions.displayName = 'Actions';

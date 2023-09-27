/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import classNames from 'classnames';
import domAlign from 'dom-align';
import {sub} from 'frontend-js-web';
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
import {useSetSourceItem as useSetKeyboardDNDSourceItem} from '../components/KeyboardDNDContext';
import {useForm, useFormState} from '../hooks/useForm.es';
import {useResizeObserver} from '../hooks/useResizeObserver.es';
import {getFieldChildren} from '../utils/getFieldChildren';

import './Actions.scss';

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
export function ActionsProvider({actions, children, focusedFieldId}) {
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
}

export function useActions() {
	return useContext(ActionsContext);
}

ActionsContext.displayName = 'ActionsContext';

const ACTIONS_CONTAINER_OFFSET = [0, 1];

export function ActionsControls({
	actionsRef,
	activePage,
	children,
	columnRef,
	field,
}) {
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
}

export const Actions = forwardRef(
	(
		{
			activePage,
			field,
			fieldId,
			fieldType,
			isFieldSelected,
			isFieldSet,
			itemPath,
			parentFieldName,
		},
		actionsRef
	) => {
		const {fieldTypes} = useConfig();
		const formState = useFormState();
		const {actions, dispatch} = useActions();

		const setKeyboardDNDSourceItem = useSetKeyboardDNDSourceItem();

		const label = useMemo(() => {
			if (isFieldSet) {
				return Liferay.Language.get('fieldset');
			}

			return fieldTypes.find(({name}) => name === fieldType).label;
		}, [fieldType, isFieldSet, fieldTypes]);

		const handleEditButtonClick = () => {
			dispatch({
				payload: {activePage, field},
				type: ACTIONS_TYPES.ACTIVE,
			});
		};

		const handleDragButtonClick = () => {
			let parentField;
			let pageIndex;

			const hasFieldId = (itemWithRows) =>
				itemWithRows.rows?.some((row) =>
					row.columns?.some((column) =>
						column.fields?.some(
							(field) =>
								field.fieldName === fieldId ||
								hasFieldId({
									...field,
									rows: getFieldChildren(field),
								})
						)
					)
				);

			formState.pages.forEach((page, index) => {
				if (typeof pageIndex === 'number') {
					return;
				}

				if (hasFieldId(page)) {
					pageIndex = index;
					parentField = {};

					page.rows.forEach((row) => {
						row.columns.forEach((column) => {
							column.fields.forEach((field) => {
								if (hasFieldId(field)) {
									parentField = field;
								}
							});
						});
					});
				}
			});

			setKeyboardDNDSourceItem({
				dragType: 'move',
				fieldName: fieldId,
				itemPath,
				pageIndex,
				parentField,
			});
		};

		return (
			<div
				className={classNames('ddm-field-actions-container', {
					'ddm-field-actions-container--selected': isFieldSelected,
					'ddm-fieldset': isFieldSet,
				})}
				ref={actionsRef}
			>
				<div className="ddm-field-action-buttons">
					<ClayButtonWithIcon
						aria-label={sub(Liferay.Language.get('move-x'), [
							label,
						])}
						className="ddm-field-action-button mr-2 sr-only sr-only-focusable"
						displayType="primary"
						onClick={handleDragButtonClick}
						role="application"
						symbol="drag"
					/>

					<ClayButtonWithIcon
						aria-label={sub(Liferay.Language.get('edit-x'), [
							label,
						])}
						className="ddm-field-action-button mr-2 sr-only sr-only-focusable"
						displayType="primary"
						onClick={handleEditButtonClick}
						role="application"
						symbol="cog"
					/>
				</div>

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
					menuElementAttrs={{className: 'ddm-field-dropdown'}}
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

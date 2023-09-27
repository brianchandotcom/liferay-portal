/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';
import React, {
	Dispatch,
	ReactNode,
	SetStateAction,
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useRef,
	useState,
} from 'react';

// @ts-ignore

import {useForm, useFormState} from '../hooks/useForm.es';
import {getFieldChildren} from '../utils/getFieldChildren';

const ARROW_DOWN_KEYCODE = 40;
const ARROW_UP_KEYCODE = 38;
const END_KEYCODE = 35;
const ENTER_KEYCODE = 13;
const ESCAPE_KEYCODE = 27;
const HOME_KEYCODE = 36;

type ITargetPosition = 'bottom' | 'middle' | 'top';
type IItemType = 'root' | 'page' | 'row' | 'column' | 'field';

const INITIAL_STATE = {
	setState: () => {},
	setText: () => {},
	state: null,
	text: null,
};

interface IState {
	currentTarget: {
		itemPath: number[];
		itemType: IItemType;
		position: ITargetPosition;
	};

	sourceItem:
		| {
				dragType: 'add';
				fieldType: Record<string, any>;
		  }
		| {
				dragType: 'move';
				fieldName: string;
				itemPath: number[];
				pageIndex: number;
				parentField: Record<string, any>;
		  };
}

const KeyboardDNDContext = React.createContext<{
	setState: Dispatch<SetStateAction<IState | null>>;
	setText: Dispatch<SetStateAction<string | null>>;
	state: IState | null;
	text: string | null;
}>(INITIAL_STATE);

export function KeyboardDNDContextProvider({children}: {children: ReactNode}) {
	const dispatch = useForm();
	const formState = useFormState();
	const formStateRef = useRef(formState);
	const [state, setState] = useState<IState | null>(null);
	const [text, setText] = useState<string | null>(null);

	const isDragging = useMemo(() => Boolean(state as any), [state]);
	const stateRef = useRef(state);

	formStateRef.current = formState;
	stateRef.current = state;

	useEffect(() => {
		if (!isDragging) {
			return;
		}

		const handleDrop = (state: IState) => {
			const {
				itemPath: targetItemPath,
				itemType: targetItemType,
				position: targetPosition,
			} = state.currentTarget;

			const [
				targetPageIndex,
				targetRowIndex = 0,
				targetColumnIndex = 0,
			] = targetItemPath;

			const targetIndexes = {
				columnIndex: targetColumnIndex,
				pageIndex: targetPageIndex,
				rowIndex:
					targetPosition === 'bottom'
						? targetRowIndex + 1
						: targetRowIndex,
			};

			const [targetField, targetParentField] = targetItemPath
				.map((_, index) =>
					getItem(
						formStateRef.current,
						targetItemPath.slice(0, index + 1)
					)
				)
				.filter((item) => getItemType(item) === 'field')
				.reverse();

			if (state.sourceItem.dragType === 'add') {
				const {fieldType: sourceFieldType} = state.sourceItem;

				dispatch({
					payload: {
						data: {
							fieldName: targetField?.fieldName,
							parentFieldName: targetParentField?.fieldName,
						},
						fieldType: sourceFieldType,
						indexes: targetIndexes,
					},
					type:
						targetItemType === 'field'
							? 'section_add'
							: 'field_add',
				});
			}
			else if (state.sourceItem.dragType === 'move') {
				const {
					fieldName: sourceFieldName,
					pageIndex: sourcePageIndex,
					parentField: sourceParentField,
				} = state.sourceItem;

				dispatch({
					payload: {
						sourceFieldName,
						sourceFieldPage: sourcePageIndex,
						sourceParentField,
						targetFieldName: targetField?.fieldName,
						targetIndexes,
						targetParentFieldName: targetParentField?.fieldName,
					},
					type: 'field_move',
				});
			}
		};

		const handleKeyDown = (event: KeyboardEvent) => {
			event.preventDefault();
			event.stopImmediatePropagation();

			if (
				event.keyCode === ARROW_DOWN_KEYCODE ||
				event.keyCode === ARROW_UP_KEYCODE
			) {
				const nextState = getNextValidTarget(
					formStateRef.current,
					stateRef.current!,
					event.keyCode === ARROW_DOWN_KEYCODE ? 'down' : 'up'
				);

				setState(nextState);

				const {path, position} = getTextData(
					nextState,
					formStateRef.current
				);

				setText(
					sub(Liferay.Language.get('targeting-x-of-x'), [
						position,
						path,
					])
				);
			}
			else if (event.keyCode === ESCAPE_KEYCODE) {
				setState(null);
			}
			else if (event.keyCode === ENTER_KEYCODE) {
				handleDrop(stateRef.current!);

				const {path, position} = getTextData(
					stateRef.current!,
					formStateRef.current
				);

				setText(
					sub(Liferay.Language.get('field-placed-on-x-of-x'), [
						position,
						path,
					])
				);

				setTimeout(() => setText(null), 1000);

				setState(null);
			}
			else if (event.keyCode === HOME_KEYCODE) {
				const nextState = getNextValidTarget(
					formStateRef.current,
					{
						...stateRef.current!,
						currentTarget: {
							itemPath: [0],
							itemType: 'page',
							position: 'top',
						},
					},
					'down'
				);

				setState(nextState);

				const {path, position} = getTextData(
					nextState,
					formStateRef.current
				);

				setText(
					sub(Liferay.Language.get('targeting-x-of-x'), [
						position,
						path,
					])
				);
			}
			else if (event.keyCode === END_KEYCODE) {
				const nextState = getNextValidTarget(
					formStateRef.current,
					{
						...stateRef.current!,
						currentTarget: {
							itemPath: [formStateRef.current.pages.length - 1],
							itemType: 'page',
							position: 'bottom',
						},
					},
					'up'
				);

				setState(nextState);

				const {path, position} = getTextData(
					nextState,
					formStateRef.current
				);

				setText(
					sub(Liferay.Language.get('targeting-x-of-x'), [
						position,
						path,
					])
				);
			}
		};

		document.addEventListener('keydown', handleKeyDown, true);

		return () => {
			document.removeEventListener('keydown', handleKeyDown, true);
		};
	}, [dispatch, isDragging]);

	return (
		<KeyboardDNDContext.Provider value={{setState, setText, state, text}}>
			{children}
		</KeyboardDNDContext.Provider>
	);
}

export function useSetSourceItem() {
	const formState = useFormState();
	const {setState, setText} = useContext(KeyboardDNDContext);

	return useCallback(
		(nextSourceItem: IState['sourceItem'] | null) => {
			setState((state) => {
				if (nextSourceItem && !state) {
					return getNextValidTarget(
						formState,
						{
							currentTarget: {
								itemPath: [formState.pages.length - 1],
								itemType: 'page',
								position: 'bottom',
							},
							sourceItem: nextSourceItem,
						},
						'up'
					);
				}

				if (!nextSourceItem && state) {
					return null;
				}

				return state;
			});

			setText(
				Liferay.Language.get(
					'use-up-and-down-arrows-to-move-the-field-and-press-enter-to-place-it-in-desired-position'
				)
			);
		},
		[formState, setState, setText]
	);
}

export function useText() {
	return useContext(KeyboardDNDContext).text;
}

export function useIsOverTarget(
	itemPath: number[],
	position: ITargetPosition
): boolean {
	const formState = useFormState();
	const {state} = useContext(KeyboardDNDContext);

	if (!state) {
		return false;
	}

	const item = getItem(formState, itemPath);
	const itemType = getItemType(item);

	const {
		itemPath: currentItemPath,
		itemType: currentItemType,
		position: currentPosition,
	} = state.currentTarget;

	return (
		currentItemType === itemType &&
		currentPosition === position &&
		currentItemPath.length === itemPath.length &&
		currentItemPath.every((chunk, i) => chunk === itemPath[i])
	);
}

function getItemType(item: any): IItemType {
	if (item.pages) {
		return 'root';
	}
	else if (item.fieldName) {
		return 'field';
	}
	else if (item.rows) {
		return 'page';
	}
	else if (item.fields) {
		return 'column';
	}
	else if (item.columns) {
		return 'row';
	}

	throw new Error('Unknown item type');
}

function getItemChildren(item: any): any[] {
	switch (getItemType(item)) {
		case 'root':
			return item.pages || [];
		case 'page':
			return item.rows || [];
		case 'row':
			return item.columns || [];
		case 'column':
			return item.fields || [];
		case 'field': {
			return getFieldChildren(item);
		}
		default:
			return [];
	}
}

function getItem(
	formState: any,
	itemPath: IState['currentTarget']['itemPath']
): any {
	const findStateItem = (
		parentItem: any,
		childPath: IState['currentTarget']['itemPath']
	): any => {
		const [childIndex, ...grandChildPath] = childPath;
		const children = getItemChildren(parentItem);

		if (!children || children.length <= childIndex) {
			throw new Error('Invalid child index');
		}

		const child = children[childIndex];

		if (grandChildPath.length) {
			return findStateItem(child, grandChildPath);
		}

		return child;
	};

	return itemPath.length ? findStateItem(formState, itemPath) : formState;
}

function getNextValidTarget(
	formState: ReturnType<typeof useFormState>,
	{currentTarget: initialTarget, sourceItem}: IState,
	direction: 'down' | 'up'
): IState {
	const getNextTarget = (
		currentTarget: IState['currentTarget']
	): IState['currentTarget'] => {
		const {itemPath, itemType, position} = currentTarget;

		if (direction === 'up') {
			if (position === 'bottom') {
				const children = getItemChildren(getItem(formState, itemPath));

				if (children.length) {
					const childItemPath = [...itemPath, children.length - 1];

					return {
						itemPath: childItemPath,
						itemType: getItemType(
							getItem(formState, childItemPath)
						),
						position: 'bottom',
					};
				}

				return {
					itemPath,
					itemType,
					position: 'middle',
				};
			}
			else if (position === 'middle') {
				return {
					itemPath,
					itemType,
					position: 'top',
				};
			}
			else if (position === 'top') {
				const [itemIndex] = itemPath.slice(-1);
				const parentItemPath = itemPath.slice(0, -1);

				if (itemIndex > 0) {
					return {
						itemPath: [...parentItemPath, itemIndex - 1],
						itemType,
						position: 'bottom',
					};
				}
				else if (itemPath.length > 1) {
					return {
						itemPath: parentItemPath,
						itemType: getItemType(
							getItem(formState, parentItemPath)
						),
						position: 'top',
					};
				}
			}
		}
		else if (direction === 'down') {
			if (position === 'top') {
				const children = getItemChildren(getItem(formState, itemPath));

				if (children.length) {
					const childItemPath = [...itemPath, 0];

					return {
						itemPath: childItemPath,
						itemType: getItemType(
							getItem(formState, childItemPath)
						),
						position: 'top',
					};
				}

				return {
					itemPath,
					itemType,
					position: 'middle',
				};
			}
			else if (position === 'middle') {
				return {
					itemPath,
					itemType,
					position: 'bottom',
				};
			}
			else if (position === 'bottom') {
				const [itemIndex] = itemPath.slice(-1);
				const parentItemPath = itemPath.slice(0, -1);

				const parentItem = getItem(formState, parentItemPath);
				const parentItemType = getItemType(parentItem);
				const siblings = getItemChildren(parentItem);

				if (itemIndex < siblings.length - 1) {
					return {
						itemPath: [...parentItemPath, itemIndex + 1],
						itemType,
						position: 'top',
					};
				}
				else {
					return {
						itemPath: parentItemPath,
						itemType: parentItemType,
						position: 'bottom',
					};
				}
			}
		}

		return currentTarget;
	};

	const isValidTarget = ({
		itemPath,
		itemType,
		position,
	}: IState['currentTarget']): boolean => {
		const [index] = itemPath.slice(-1);
		const item = getItem(formState, itemPath);
		const itemChildren = getItemChildren(item);

		const isEmpty =
			itemType === 'row'
				? itemChildren.every((col) => !getItemChildren(col).length)
				: !itemChildren.length;

		if (
			itemType === 'root' ||
			(itemType === 'page' && position !== 'middle') ||
			(itemType === 'page' && item.contentRenderer) ||
			(itemType === 'column' && position !== 'middle') ||

			// Cannot drag over/below empty rows.

			(itemType === 'row' && position !== 'middle' && isEmpty) ||

			// Cannot drag over non-first item, it must target previous item's
			// bottom position.

			(position === 'top' && index > 0) ||

			// Cannot drag inside elements with children.

			(position === 'middle' && itemChildren.length) ||

			// Current dnd restriction: you can only drop items over/below rows.

			(itemType === 'field' && position !== 'middle') ||

			// Do not allow items inside themselves

			(sourceItem.dragType === 'move' &&
				sourceItem.itemPath.length <= itemPath.length &&
				sourceItem.itemPath.every(
					(value, index) => itemPath[index] === value
				))
		) {
			return false;
		}

		return true;
	};

	const isSameTarget = (
		targetA: IState['currentTarget'],
		targetB: IState['currentTarget']
	) =>
		targetA.itemType === targetB.itemType &&
		targetA.position === targetB.position &&
		targetA.itemPath.length === targetB.itemPath.length &&
		targetA.itemPath.every((chunk, i) => chunk === targetB.itemPath[i]);

	const findNextValidTarget = (
		currentTarget: IState['currentTarget']
	): IState['currentTarget'] => {
		const nextTarget = getNextTarget(currentTarget);

		if (isValidTarget(nextTarget)) {
			return nextTarget;
		}
		else if (!isSameTarget(nextTarget, currentTarget)) {
			const recursiveTarget = findNextValidTarget(nextTarget);

			if (isValidTarget(recursiveTarget)) {
				return recursiveTarget;
			}
		}

		return currentTarget;
	};

	return {
		currentTarget: findNextValidTarget(initialTarget),
		sourceItem,
	};
}

function getTextData(
	state: IState,
	formState: ReturnType<typeof useFormState>
) {
	const {itemPath, position} = state.currentTarget;
	const {paginationMode} = formState;

	const pathItems: Array<string> = [];

	itemPath.forEach((itemIndex, i) => {
		const item = getItem(formState, itemPath.slice(0, i + 1));
		const itemType = getItemType(item);
		const itemTypeLabel = getItemTypeLabel(itemType);

		if (
			itemType === 'page' &&
			paginationMode === 'single-page' &&
			itemPath.length > 1
		) {
			return;
		}

		pathItems.push(item.label || `${itemTypeLabel} ${itemIndex + 1}`);
	});

	return {
		path: pathItems.join(','),
		position,
	};
}

function getItemTypeLabel(itemType: string) {
	switch (itemType) {
		case 'root':
			return Liferay.Language.get('root');
		case 'page':
			return Liferay.Language.get('page');
		case 'row':
			return Liferay.Language.get('row');
		case 'column':
			return Liferay.Language.get('column');
		case 'field': {
			return Liferay.Language.get('field');
		}
		default:
			return itemType;
	}
}

import React, {useReducer} from 'react';
import {OrderedMap} from 'immutable';

export const ACTION_TYPES: {[key: string]: ActionType} = {
	add: 'add',
	clearAll: 'clear-all',
	remove: 'remove',
	toggle: 'toggle'
};

export enum ActionTypes {
	Add = 'add',
	ClearAll = 'clear-all',
	Toggle = 'toggle',
	Remove = 'remove'
}

type ActionType = 'add' | 'clear-all' | 'toggle' | 'remove';

type Action = {
	payload?: {
		item?: {[key: string]: any};
		items?: {[key: string]: any}[];
	};
	type: ActionType;
};
type Dispatch = (action: Action) => void;
type State = {selectedItems: OrderedMap<any, any>};
type SelectionProviderProps = {
	children: React.ReactNode;
	selectedItems?: {id: string}[];
};

export const SelectionContext = React.createContext<{
	selectedItems: OrderedMap<any, any>;
	selectionDispatch?: Dispatch;
}>({selectedItems: OrderedMap()});

export const selectionReducer = (
	{selectedItems}: State,
	{payload, type}: Action
) => {
	switch (type) {
		case 'add': {
			return {
				selectedItems: selectedItems.merge(
					OrderedMap(payload.items.map(item => [item.id, item]))
				)
			};
		}
		case 'clear-all': {
			return {
				selectedItems: OrderedMap()
			};
		}
		case 'remove': {
			return {
				selectedItems: selectedItems.filter(
					(_, key) => !payload.items.some(({id}) => id === key)
				) as OrderedMap<any, any> // Assert return type from .filter() is OrderedMap until we can update Immutable package
			};
		}
		case 'toggle': {
			if (selectedItems.has(payload.item.id)) {
				return {selectedItems: selectedItems.delete(payload.item.id)};
			} else {
				return {
					selectedItems: selectedItems.set(
						payload.item.id,
						payload.item
					)
				};
			}
		}
		default: {
			throw new Error(`Unhandled action type: ${type}`);
		}
	}
};

export const SelectionProvider = ({
	children,
	selectedItems: initialValue
}: SelectionProviderProps) => {
	const [state, dispatch] = useReducer(selectionReducer, {
		selectedItems: initialValue
			? OrderedMap(initialValue.map(item => [item.id, item]))
			: OrderedMap()
	});

	return (
		<SelectionContext.Provider
			value={{
				selectedItems: state.selectedItems,
				selectionDispatch: dispatch
			}}
		>
			{children}
		</SelectionContext.Provider>
	);
};

export const useSelectionContext = () => {
	const context = React.useContext(SelectionContext);
	if (context === undefined) {
		throw new Error(
			'useSelectionContext must be used within a SelectionProvider'
		);
	}
	return context;
};

export const withSelectionProvider = WrappedComponent => props => (
	<SelectionProvider>
		<WrappedComponent {...props} />
	</SelectionProvider>
);

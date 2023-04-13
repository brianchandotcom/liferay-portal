import React, {useReducer} from 'react';
import {isNil} from 'lodash';
import {Segment} from 'shared/util/records';

export enum ActionType {
	setSegments = 'setSegments',
	updateShowAlert = 'updateShowAlert'
}

type Action = {
	payload?: any;
	type: ActionType;
};
type Dispatch = (action: Action) => void;

type State = {
	showUnassignedAlert: boolean;
	unassignedSegments: Array<Segment>;
};

type unassignedSegmentsProviderProps = {
	children: React.ReactNode;
	unassignedSegments?: Array<Segment>;
};

export const UnassignedSegmentsContext = React.createContext<{
	showUnassignedAlert: boolean;
	unassignedSegments: Array<Segment>;
	unassignedSegmentsDispatch?: Dispatch;
}>({
	showUnassignedAlert: true,
	unassignedSegments: []
});

export const unassignedSegmentsReducer = (
	state: State,
	{payload, type}: Action
) => {
	switch (type) {
		case 'setSegments': {
			return {
				...state,
				unassignedSegments: payload
			};
		}
		case 'updateShowAlert': {
			return {
				...state,
				showUnassignedAlert: false
			};
		}
		default:
			return state;
	}
};

export const UnassignedSegmentsProvider = ({
	children,
	unassignedSegments: initialSegments
}: unassignedSegmentsProviderProps) => {
	const [
		{showUnassignedAlert, unassignedSegments},
		unassignedSegmentsDispatch
	] = useReducer(unassignedSegmentsReducer, {
		showUnassignedAlert: true,
		unassignedSegments: initialSegments || []
	});

	return (
		<UnassignedSegmentsContext.Provider
			value={{
				showUnassignedAlert,
				unassignedSegments,
				unassignedSegmentsDispatch
			}}
		>
			{children}
		</UnassignedSegmentsContext.Provider>
	);
};

export const useUnassignedSegmentsContext = () => {
	const context = React.useContext(UnassignedSegmentsContext);
	if (isNil(context)) {
		throw new Error(
			'UnassignedSegmentsContext must be used within a UnassignedSegmentsProvider'
		);
	}
	return context;
};

export const withUnassignedSegmentsProvider = WrappedComponent => props => (
	<UnassignedSegmentsProvider>
		<WrappedComponent {...props} />
	</UnassignedSegmentsProvider>
);

export default UnassignedSegmentsProvider;

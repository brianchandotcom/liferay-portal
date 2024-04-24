import React, {createContext, useContext, useReducer} from 'react';
import {ReportContainer} from './DownloadPDFReport';

export const Context = createContext<{
	reportContainers: ReportContainer[];
	setReportContainer: (reportContainer: ReportContainer) => void;
	clearReportContainers: () => void;
}>({
	clearReportContainers: () => {},
	reportContainers: [],
	setReportContainer: () => {}
});

enum ActionTypes {
	ClearReportContainers = 'CLEAR_CONTEXT',
	SetReportContainer = 'SET_REPORT_CONTAINER'
}

type DownloadReportAction = {
	type: ActionTypes;
	payload: ReportContainer;
};

const downloadReportReducer = (state, action: DownloadReportAction) => {
	switch (action.type) {
		case ActionTypes.SetReportContainer:
			return {
				...state,
				reportContainers: [...state.reportContainers, action.payload]
			};
		case ActionTypes.ClearReportContainers:
			return {
				...state,
				reportContainers: []
			};
		default: {
			throw new Error(
				'Unhandled action type for Download Report Reducer'
			);
		}
	}
};

export const DownloadReportProvider = ({children}) => {
	const [{reportContainers}, dispatch] = useReducer(downloadReportReducer, {
		reportContainers: []
	});

	const setReportContainer = reportContainer => {
		dispatch({
			payload: reportContainer,
			type: ActionTypes.SetReportContainer
		});
	};

	const clearReportContainers = () => {
		dispatch({
			payload: null,
			type: ActionTypes.ClearReportContainers
		});
	};

	return (
		<Context.Provider
			value={{
				clearReportContainers,
				reportContainers,
				setReportContainer
			}}
		>
			{children}
		</Context.Provider>
	);
};

Context.displayName = 'DownloadReportContext';

export const useDownloadReportContext = () => useContext(Context);

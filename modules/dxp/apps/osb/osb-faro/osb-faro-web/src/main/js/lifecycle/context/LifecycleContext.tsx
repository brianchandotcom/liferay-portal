import React, {
	createContext,
	ReactNode,
	useCallback,
	useContext,
	useMemo,
	useState
} from 'react';
import {
	buildQueryString,
	ILifecycleFilterValues
} from '../utils/buildQueryString';
import {LifecycleStages} from 'contacts/pages/account/utils/constants';

interface ILifecycleFilters extends ILifecycleFilterValues {
	filterString: string;
}

interface ILifecycleContext {
	filters: ILifecycleFilters;
	updateFilters: (newFilters: Partial<ILifecycleFilterValues>) => void;
	resetFilters: () => void;
}

const LifecycleContext = createContext<ILifecycleContext>({
	filters: {
		countryFilter: '',
		filterString: '',
		industryFilter: '',
		lifecycleStageFilter: LifecycleStages.AT_RISK
	},
	resetFilters: () => {},
	updateFilters: () => {}
});

export const useLifecycle = (): ILifecycleContext =>
	useContext(LifecycleContext);

const initialValues: ILifecycleFilterValues = {
	countryFilter: '',
	industryFilter: '',
	lifecycleStageFilter: LifecycleStages.AT_RISK
};

export const LifecycleContextProvider = ({children}: {children: ReactNode}) => {
	const [filterValues, setFilterValues] =
		useState<ILifecycleFilterValues>(initialValues);

	const filters = useMemo<ILifecycleFilters>(
		() => ({
			...filterValues,
			filterString: buildQueryString(filterValues)
		}),
		[filterValues]
	);

	const updateFilters = useCallback(
		(newValues: Partial<ILifecycleFilterValues>) =>
			setFilterValues(prev => ({...prev, ...newValues})),
		[]
	);

	const resetFilters = useCallback(() => setFilterValues(initialValues), []);

	const value = useMemo(
		() => ({filters, resetFilters, updateFilters}),
		[filters, resetFilters, updateFilters]
	);

	return (
		<LifecycleContext.Provider value={value}>
			{children}
		</LifecycleContext.Provider>
	);
};

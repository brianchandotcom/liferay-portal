import React, {
	createContext,
	ReactNode,
	useContext,
	useMemo,
	useState
} from 'react';
import {
	buildQueryString,
	ILifecycleFilterValues
} from '../utils/buildQueryString';

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
		industryFilter: ''
	},
	resetFilters: () => {},
	updateFilters: () => {}
});

export const useLifecycle = (): ILifecycleContext =>
	useContext(LifecycleContext);

const initialValues: ILifecycleFilterValues = {
	countryFilter: '',
	industryFilter: ''
};

export const LifecycleContextProvider = ({children}: {children: ReactNode}) => {
	const [filterValues, setFilterValues] = useState<ILifecycleFilterValues>(
		initialValues
	);

	const filters = useMemo<ILifecycleFilters>(
		() => ({
			...filterValues,
			filterString: buildQueryString(filterValues)
		}),
		[filterValues]
	);

	const updateFilters = (newValues: Partial<ILifecycleFilterValues>) =>
		setFilterValues(prev => ({...prev, ...newValues}));

	const resetFilters = () => setFilterValues(initialValues);

	return (
		<LifecycleContext.Provider
			value={{filters, resetFilters, updateFilters}}
		>
			{children}
		</LifecycleContext.Provider>
	);
};

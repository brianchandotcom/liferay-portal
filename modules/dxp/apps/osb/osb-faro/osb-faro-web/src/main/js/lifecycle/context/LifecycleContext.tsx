import React, {createContext, ReactNode, useContext, useState} from 'react';
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

export const LifecycleContextProvider = ({children}: {children: ReactNode}) => {
	const initialValues: ILifecycleFilterValues = {
		countryFilter: '',
		industryFilter: ''
	};

	const [filters, setFilters] = useState<ILifecycleFilters>({
		...initialValues,
		filterString: ''
	});

	const updateFilters = (newValues: Partial<ILifecycleFilterValues>) => {
		setFilters(prev => {
			const merged = {...prev, ...newValues};
			return {...merged, filterString: buildQueryString(merged)};
		});
	};

	const resetFilters = () => setFilters({...initialValues, filterString: ''});

	return (
		<LifecycleContext.Provider
			value={{filters, resetFilters, updateFilters}}
		>
			{children}
		</LifecycleContext.Provider>
	);
};

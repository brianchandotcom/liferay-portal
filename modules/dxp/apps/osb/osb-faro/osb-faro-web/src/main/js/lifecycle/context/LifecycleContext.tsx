import React, {createContext, ReactNode, useState} from 'react';

interface ILifecycleFilters {
	countryFilter: string;
	industryFilter: string;
	revenueFilter: string;
}

interface ILifecycleContext {
	filters: ILifecycleFilters;
	updateFilters: (newFilters: Partial<ILifecycleFilters>) => void;
	resetFilters: () => void;
}

export const LifecycleContext = createContext<ILifecycleContext | undefined>(
	undefined
);

export const LifecycleContextProvider = ({children}: {children: ReactNode}) => {
	const initialFilters: ILifecycleFilters = {
		countryFilter: '',
		industryFilter: '',
		revenueFilter: ''
	};

	const [filters, setFilters] = useState<ILifecycleFilters>(initialFilters);

	const updateFilters = (newFilters: Partial<ILifecycleFilters>) => {
		setFilters(prev => ({...prev, ...newFilters}));
	};

	const resetFilters = () => setFilters(initialFilters);

	return (
		<LifecycleContext.Provider
			value={{filters, resetFilters, updateFilters}}
		>
			{children}
		</LifecycleContext.Provider>
	);
};

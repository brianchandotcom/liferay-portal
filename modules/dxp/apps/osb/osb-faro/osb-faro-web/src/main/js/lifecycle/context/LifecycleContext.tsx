import React, {createContext, ReactNode, useContext, useState} from 'react';

interface ILifecycleFilterValues {
	countryFilter: string;
	industryFilter: string;
}

interface ILifecycleFilters extends ILifecycleFilterValues {
	filterString: string;
}

interface ILifecycleContext {
	filters: ILifecycleFilters;
	updateFilters: (newFilters: Partial<ILifecycleFilterValues>) => void;
	resetFilters: () => void;
}

const FILTER_CONFIG: {
	field: keyof ILifecycleFilterValues;
	fieldName: string;
	op: string;
}[] = [
	{field: 'countryFilter', fieldName: 'country', op: 'eq'},
	{field: 'industryFilter', fieldName: 'industry', op: 'eq'}
];

const buildQueryString = (values: ILifecycleFilterValues): string =>
	FILTER_CONFIG.map(({field, fieldName, op}) => {
		const val = values[field];
		return val !== '' ? `${fieldName} ${op} '${val}'` : null;
	})
		.filter(Boolean)
		.join(' and ');

const LifecycleContext = createContext<ILifecycleContext | undefined>(
	undefined
);

export const useLifecycle = (): ILifecycleContext => {
	const ctx = useContext(LifecycleContext);
	if (!ctx) {
		throw new Error('useLifecycle must be used within a LifecycleContextProvider');
	}
	return ctx;
};

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

	const resetFilters = () =>
		setFilters({...initialValues, filterString: ''});

	return (
		<LifecycleContext.Provider
			value={{filters, resetFilters, updateFilters}}
		>
			{children}
		</LifecycleContext.Provider>
	);
};

import React from 'react';
import {useFDSBundle} from './useFDSBundle';

export const useFrontendDataSet = (): React.ComponentType<any> | null => {
	const bundle = useFDSBundle();

	return bundle?.FrontendDataSet ?? null;
};

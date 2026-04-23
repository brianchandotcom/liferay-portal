import React from 'react';
import {useQueryRangeSelectors} from 'shared/hooks/useQueryRangeSelectors';

const withQueryRangeSelectors =
	() => (WrappedComponent: React.ComponentType<any>) => (props: any) => {
		const rangeSelectors = useQueryRangeSelectors();

		return <WrappedComponent {...props} rangeSelectors={rangeSelectors} />;
	};

export default withQueryRangeSelectors;

import React from 'react';
import {useQueryRangeSelectors} from 'shared/hooks';

const withQueryRangeSelectors = initialParams => WrappedComponent => (
	props: any
) => {
	const rangeSelectors = useQueryRangeSelectors(initialParams);

	return <WrappedComponent {...props} rangeSelectors={rangeSelectors} />;
};

export default withQueryRangeSelectors;

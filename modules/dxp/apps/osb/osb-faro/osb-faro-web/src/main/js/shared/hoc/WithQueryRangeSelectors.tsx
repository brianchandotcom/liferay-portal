import React from 'react';
import {useQueryRangeSelectors} from 'shared/hooks/useQueryRangeSelectors';

const withQueryRangeSelectors = () => WrappedComponent => (props: any) => {
	const rangeSelectors = useQueryRangeSelectors();

	return <WrappedComponent {...props} rangeSelectors={rangeSelectors} />;
};

export default withQueryRangeSelectors;

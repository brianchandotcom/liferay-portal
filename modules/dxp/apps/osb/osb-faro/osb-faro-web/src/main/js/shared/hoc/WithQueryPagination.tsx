import React from 'react';
import {useQueryPagination} from 'shared/hooks/useQueryPagination';

const withQueryPagination = initialParams => WrappedComponent => (
	props: any
) => {
	const paginationParams = useQueryPagination(initialParams);

	return <WrappedComponent {...props} {...paginationParams} />;
};

export default withQueryPagination;

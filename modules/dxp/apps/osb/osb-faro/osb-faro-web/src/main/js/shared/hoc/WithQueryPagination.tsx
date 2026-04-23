import React from 'react';
import {useQueryPagination} from 'shared/hooks/useQueryPagination';

const withQueryPagination =
	(initialParams: Parameters<typeof useQueryPagination>[0]) =>
	(WrappedComponent: React.ComponentType<any>) =>
	(props: any) => {
		const paginationParams = useQueryPagination(initialParams);

		return <WrappedComponent {...props} {...paginationParams} />;
	};

export default withQueryPagination;

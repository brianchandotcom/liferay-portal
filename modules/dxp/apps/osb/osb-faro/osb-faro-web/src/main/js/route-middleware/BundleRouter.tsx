import React from 'react';
import {matchPath, Route} from 'react-router-dom';
import {useQueryParams} from 'shared/hooks/useQueryParams';

const BundleRouter = ({
	componentProps = {},
	data: Component,
	destructured = true,
	...otherRouteProps
}) => {
	const query = useQueryParams();

	return (
		<Route
			{...otherRouteProps}
			render={({history, match: {params, path}}) => {
				if (destructured) {
					return (
						<Component
							history={history}
							{...query}
							{...params}
							{...componentProps}
						/>
					);
				}

				const matchedPath = matchPath<any>(window.location.pathname, {
					path
				});

				return (
					<Component
						history={history}
						router={{
							params: {
								...params,
								touchpoint: matchedPath.params.touchpoint
							},
							query
						}}
						{...componentProps}
					/>
				);
			}}
		/>
	);
};

export default BundleRouter;

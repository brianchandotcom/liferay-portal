import React from 'react';
import {useHistoryAdapter} from 'shared/hooks/useHistoryAdapter';
import {useParams} from 'react-router-dom';
import {useQueryParams} from 'shared/hooks/useQueryParams';

/**
 * Compatibility wrapper used as the `element` of a v6 `<Route>`:
 *
 * 	<Route path="..." element={<BundleRouter data={SomeComponent} />} />
 *
 * It preserves the prop contract the ~50 existing route components rely on:
 * the route params and query string are injected as props, alongside a
 * `history` adapter shaped like the v5 `history` object (see
 * `useHistoryAdapter`). This keeps the many `history.push(...)` call sites
 * unchanged across the v6 migration.
 *
 * Tech debt: each consumer should eventually call `useNavigate`/`useParams`/
 * `useSearchParams` directly so this wrapper can be deleted.
 */

interface IBundleRouterProps {
	componentProps?: Record<string, unknown>;
	data: React.ComponentType<any>;
	destructured?: boolean;
}

const BundleRouter = ({
	componentProps = {},
	data: Component,
	destructured = true
}: IBundleRouterProps) => {
	const history = useHistoryAdapter();
	const params = useParams();
	const query = useQueryParams();

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

	return (
		<Component
			history={history}
			router={{params, query}}
			{...componentProps}
		/>
	);
};

export default BundleRouter;

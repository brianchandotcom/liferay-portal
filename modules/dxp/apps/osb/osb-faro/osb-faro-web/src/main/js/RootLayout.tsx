import AlertFeed from 'shared/components/AlertFeed';
import Loading from 'shared/components/Loading';
import ModalRenderer from 'shared/components/ModalRenderer';
import React, {Suspense, useEffect} from 'react';
import {Outlet, useMatch} from 'react-router-dom';
import {Pendo} from 'shared/util/pendo';
import {Project} from 'shared/util/records';
import {useFetchCurrentUser} from 'shared/hooks/useCurrentUser';
import {useSelector} from 'react-redux';

/**
 * Root layout for the data router. Renders the global chrome (alerts, modals)
 * and the matched route via `<Outlet />`. It also owns the cross-route
 * `currentUser` fetch and Pendo bootstrap that previously lived in App's
 * `RoutesContainer`; the `notFoundError` redirect handled there is now covered
 * by the catch-all route and the root `errorElement`.
 */
const RootLayout = () => {
	const match = useMatch('/workspace/:groupId/*');

	const groupId = match?.params.groupId ?? '0';

	const project: Project = useSelector<any, any>(state =>
		state.getIn(['projects', groupId, 'data'])
	);

	const {data: currentUser, loading} = useFetchCurrentUser(groupId);

	useEffect(() => {
		if (currentUser?.id && project?.corpProjectName) {
			const pendo = new Pendo();

			pendo.initialize({currentUser, project});
		}
	}, [currentUser?.id, project?.corpProjectName]);

	if (loading) {
		return <Loading />;
	}

	return (
		<>
			<AlertFeed />

			<ModalRenderer />

			<Suspense fallback={<Loading />}>
				<Outlet />
			</Suspense>
		</>
	);
};

export default RootLayout;

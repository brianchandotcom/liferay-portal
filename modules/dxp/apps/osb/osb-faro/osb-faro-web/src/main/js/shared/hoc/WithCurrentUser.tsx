import React from 'react';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';

/**
 * CurrentUser HOC
 * @deprecated Use useCurrentUser Hook for functional components.
 */
export default <P extends object>(Component: React.ComponentType<P>) =>
	(props: P) => {
		const currentUser = useCurrentUser();

		return <Component {...props} currentUser={currentUser} />;
	};

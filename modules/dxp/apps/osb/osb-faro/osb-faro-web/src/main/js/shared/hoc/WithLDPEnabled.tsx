import React from 'react';
import {useLDPEnabled} from 'shared/hooks/useLDPEnabled';

const WithLDPEnabled =
	<P extends {groupId: string}>(Component: React.ComponentType<P>) =>
	(props: P) => {
		const {groupId} = props;
		const LDPEnabled = useLDPEnabled({groupId});

		return <Component {...props} LDPEnabled={LDPEnabled} />;
	};

export default WithLDPEnabled;

import React from 'react';
import {useLDPEnabled} from 'shared/hooks/useLDPEnabled';

const WithLDPEnabled = Component => props => {
	const {groupId} = props;
	const LDPEnabled = useLDPEnabled({groupId});

	return <Component {...props} LDPEnabled={LDPEnabled} />;
};

export default WithLDPEnabled;

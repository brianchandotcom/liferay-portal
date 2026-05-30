import React, {ReactNode} from 'react';
import {DEVELOPER_MODE} from 'shared/util/constants';
import {Navigate, useParams} from 'react-router-dom';
import {useLDPEnabled} from 'shared/hooks/useLDPEnabled';

export type FeatureFlag = 'LDPEnabled' | 'DEVELOPER_MODE';

interface IFeatureGateProps {
	children: ReactNode;
	fallback?: ReactNode;
	flag: FeatureFlag;
}

const useFeatureFlag = (flag: FeatureFlag): boolean => {
	const {groupId = ''} = useParams<{groupId?: string}>();
	const ldpEnabled = useLDPEnabled({groupId});

	if (flag === 'LDPEnabled') {
		return ldpEnabled;
	}

	if (flag === 'DEVELOPER_MODE') {
		return DEVELOPER_MODE;
	}

	return true;
};

const FeatureGate = ({children, fallback, flag}: IFeatureGateProps) => {
	const enabled = useFeatureFlag(flag);

	if (!enabled) {
		return <>{fallback ?? <Navigate replace to='/' />}</>;
	}

	return <>{children}</>;
};

export default FeatureGate;

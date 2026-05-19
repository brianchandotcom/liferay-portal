import {isLDPPlan} from 'shared/util/subscriptions';
import {useSubscriptionName} from 'shared/hooks/useSubscriptionName';

export const useLDPEnabled = ({groupId}: {groupId: string}): boolean =>
	isLDPPlan(useSubscriptionName({groupId}));

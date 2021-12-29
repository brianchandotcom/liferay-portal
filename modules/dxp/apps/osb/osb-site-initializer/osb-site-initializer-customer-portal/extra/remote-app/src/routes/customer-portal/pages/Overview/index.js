import {useQuery} from '@apollo/client';
import {useEffect, useState} from 'react';
import {useCustomEvent} from '../../../../common/hooks/useCustomEvent';
import {usePageGuard} from '../../../../common/hooks/usePageGuard';
import {getAccountSubscriptionGroups} from '../../../../common/services/liferay/graphql/queries';
import Subscriptions from '../../components/Subscriptions';
import {useCustomerPortal} from '../../context';
import {actionTypes} from '../../context/reducer';
import {CUSTOM_EVENTS} from '../../utils/constants';
import {getWebContents} from '../../utils/webContentsGenerator';
const Overview = ({project, userAccount}) => {
	const [, dispatch] = useCustomerPortal();
	const [
		slaCurrentVersionAndProducts,
		setSLACurrentVersionAndProducts,
	] = useState([]);
	const [
		SLACurrentVersionAndProductsComplete,
		setSLACurrentVersionAndProductsComplete,
	] = useState(false);
	const dispatchEvent = useCustomEvent(CUSTOM_EVENTS.PROJECT);
	const {loading} = usePageGuard(userAccount, project.accountKey, 'overview');

	useEffect(() => {
		setSLACurrentVersionAndProducts((prevSlaCurrentVersionAndProducts) => [
			...prevSlaCurrentVersionAndProducts,
			project.slaCurrent,
			project.dxpVersion,
		]);
		dispatchEvent(project);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [project]);

	const {
		data: dataSubscriptionGroups,
		loading: isLoadingSubscritionsGroups,
	} = useQuery(getAccountSubscriptionGroups, {
		variables: {
			filter: `accountKey eq '${project.accountKey}' and hasActivation eq true`,
		},
	});

	useEffect(() => {
		if (dataSubscriptionGroups) {
			const subscriptionGroupsItems =
				dataSubscriptionGroups.c?.accountSubscriptionGroups?.items;
			dispatch({
				payload: subscriptionGroupsItems,
				type: actionTypes.UPDATE_SUBSCRIPTION_GROUPS,
			});

			setSLACurrentVersionAndProducts(
				(prevSlaCurrentVersionAndProducts) => [
					...prevSlaCurrentVersionAndProducts,
					...subscriptionGroupsItems.map((group) => group.name),
				]
			);
			setSLACurrentVersionAndProductsComplete(true);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dataSubscriptionGroups]);

	useEffect(() => {
		if (SLACurrentVersionAndProductsComplete) {
			dispatch({
				payload: getWebContents(slaCurrentVersionAndProducts),
				type: actionTypes.UPDATE_QUICK_LINKS,
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [SLACurrentVersionAndProductsComplete, slaCurrentVersionAndProducts]);

	if (loading || isLoadingSubscritionsGroups) {
		return <div>Overview Skeleton</div>;
	}

	return <Subscriptions accountKey={project.accountKey} />;
};
export default Overview;

import {useQuery} from '@apollo/client';
import {useEffect, useState} from 'react';
import {useCustomEvent} from '../../../../common/hooks/useCustomEvent';
import {usePageGuard} from '../../../../common/hooks/usePageGuard';
import {getAccountSubscriptionGroups} from '../../../../common/services/liferay/graphql/queries';
import {Storage} from '../../../../common/services/liferay/storage';
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
			filter: `accountKey eq '${project.accountKey}'`,
		},
	});

	useEffect(() => {
		if (userAccount){
			sessionStorage.setItem(
				'customer-portal-account-briefs',
				JSON.stringify(userAccount.accountBriefs)
			);
		}
	}, [userAccount]);

	useEffect(() => {
		if (dataSubscriptionGroups) {
			const subscriptionGroupsItems =
				dataSubscriptionGroups.c?.accountSubscriptionGroups?.items;
			dispatch({
				payload: subscriptionGroupsItems,
				type: actionTypes.UPDATE_SUBSCRIPTION_GROUPS,
			});
			setSLACurrentVersionAndProducts(
				...slaCurrentVersionAndProducts,
				...subscriptionGroupsItems.map((group) => group.name)
			);
		}
		Storage.setItem(
			'cp-tip-container-primary',
			JSON.stringify(getWebContents(slaCurrentVersionAndProducts))
		);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dataSubscriptionGroups, slaCurrentVersionAndProducts]);

	if (loading || isLoadingSubscritionsGroups) {
		return <div>Overview Skeleton</div>;
	}

	return <Subscriptions accountKey={project.accountKey} />;
};
export default Overview;

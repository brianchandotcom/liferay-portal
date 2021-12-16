import {useQuery} from '@apollo/client';
import {useContext, useEffect, useState} from 'react';
import {useCustomEvent} from '../../../../common/hooks/useCustomEvent';
import {usePageGuard} from '../../../../common/hooks/usePageGuard';
import {
	getAccountSubscriptionGroups,
	getKoroneikiAccounts,
} from '../../../../common/services/liferay/graphql/queries';
import {Storage} from '../../../../common/services/liferay/storage';
import Subscriptions from '../../components/Subscriptions';
import {AppContext} from '../../context';
import {actionTypes} from '../../context/reducer';
import {CUSTOM_EVENTS} from '../../utils/constants';
import {getWebContents} from '../../utils/webContentsGenerator';

const Overview = ({userAccount}) => {
	const [{project}, dispatch] = useContext(AppContext);
	const [
		slaCurrentVersionAndProducts,
		setSLACurrentVersionAndProducts,
	] = useState([]);

	const dispatchEvent = useCustomEvent(CUSTOM_EVENTS.PROJECT);
	const {isLoading} = usePageGuard(
		userAccount,
		project.accountKey,
		'overview'
	);

	const {data, loading: isLoadingKoroneiki} = useQuery(getKoroneikiAccounts, {
		variables: {
			filter: `accountKey eq '${project.accountKey}'`,
		},
	});

	useEffect(() => {
		if (!isLoadingKoroneiki && data) {
			const koroneikiAccount = data.c?.koroneikiAccounts?.items[0];

			setSLACurrentVersionAndProducts([
				...slaCurrentVersionAndProducts,
				koroneikiAccount.slaCurrent,
				koroneikiAccount.dxpVersion,
			]);

			dispatch({
				payload: koroneikiAccount,
				type: actionTypes.UPDATE_PROJECT,
			});

			dispatchEvent(koroneikiAccount);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [data, dispatch, isLoadingKoroneiki, slaCurrentVersionAndProducts]);

	const {
		data: dataSubscriptionGroups,
		loading: isLoadingSubscritionsGroups,
	} = useQuery(getAccountSubscriptionGroups, {
		variables: {
			filter: `accountKey eq '${project.accountKey}'`,
		},
	});

	useEffect(() => {
		if (!isLoadingSubscritionsGroups && dataSubscriptionGroups) {
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
	}, [
		dataSubscriptionGroups,
		dispatch,
		isLoadingSubscritionsGroups,
		slaCurrentVersionAndProducts,
	]);

	if (isLoading || isLoadingKoroneiki) {
		return <div>Overview Skeleton</div>;
	}

	return <Subscriptions accountKey={project.accountKey} />;
};

export default Overview;

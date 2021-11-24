import {useQuery} from '@apollo/client';
import {useContext, useEffect} from 'react';
import {
	onboardingPageGuard,
	overviewPageGuard,
	usePageGuard,
} from '~/common/hooks/usePageGuard';
import {getKoroneikiAccounts} from '~/common/services/liferay/graphql/queries';
import {AppContext} from '../../context';
import {actionTypes} from '../../context/reducer';
import {CUSTOM_EVENTS} from '../../utils/constants';

const Overview = ({userAccount}) => {
	const [{project}, dispatch] = useContext(AppContext);
	const {isLoading} = usePageGuard(
		userAccount,
		overviewPageGuard,
		onboardingPageGuard,
		project.accountKey
	);
	const {data, isLoading: isLoadingKoroneiki} = useQuery(
		getKoroneikiAccounts,
		{
			variables: {
				filter: `accountKey eq '${project.accountKey}'`,
			},
		}
	);

	useEffect(() => {
		if (!isLoading && data) {
			const koroneikiAccount = data.c?.koroneikiAccounts?.items[0];

			dispatch({
				payload: koroneikiAccount,
				type: actionTypes.UPDATE_PROJECT,
			});

			window.dispatchEvent(
				new CustomEvent(CUSTOM_EVENTS.PROJECT, {
					bubbles: true,
					composed: true,
					detail: koroneikiAccount,
				})
			);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [data, isLoading]);

	if (isLoading || isLoadingKoroneiki) {
		return <div>Overview Skeleton</div>;
	}

	return <div>Overview Page</div>;
};

export default Overview;

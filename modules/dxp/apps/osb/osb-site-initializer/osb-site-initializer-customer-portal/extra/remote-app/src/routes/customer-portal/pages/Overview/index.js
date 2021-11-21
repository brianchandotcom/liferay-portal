import {useContext, useEffect} from 'react';
import useGraphQL from '~/common/hooks/useGraphQL';
import { onboardingPageGuard, overviewPageGuard, usePageGuard } from '~/common/hooks/usePageGuard';
import {getKoroneikiAccountsByFilter} from '~/common/services/liferay/graphql/koroneiki-accounts';
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
	const {data, isLoading: isLoadingKoroneiki} = useGraphQL([
		getKoroneikiAccountsByFilter({
			accountKeys: [project.accountKey],
		}),
	]);

	useEffect(() => {
		if (!isLoading && data) {
			const koroneikiAccount = data.koroneikiAccounts[0];

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

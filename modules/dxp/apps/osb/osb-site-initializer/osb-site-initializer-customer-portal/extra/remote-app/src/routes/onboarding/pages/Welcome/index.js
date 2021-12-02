/* eslint-disable no-unused-vars */
import { useLazyQuery, useMutation } from '@apollo/client';
import { useContext, useEffect } from 'react';
import BaseButton from '../../../../common/components/BaseButton';
import {
	onboardingPageGuard,
	overviewPageGuard,
	usePageGuard,
} from '../../../../common/hooks/usePageGuard';
import { addAccountFlag, getAccountSubscriptionGroups } from '../../../../common/services/liferay/graphql/queries';
import Layout from '../../components/Layout';
import {AppContext} from '../../context';
import {actionTypes} from '../../context/reducer';
import {steps} from '../../utils/constants';
import WelcomeSkeleton from './Skeleton';

const ACCOUNT_SUBSCRIPTION_GROUP_NAME = 'DXP Cloud';

const Welcome = ({ userAccount }) => {
	const [{ assetsPath, project }, dispatch] = useContext(AppContext);
	const accountBriefs = userAccount.accountBriefs;

	const { isLoading: isLoadingPageGuard } = usePageGuard(
		userAccount,
		onboardingPageGuard,
		overviewPageGuard,
		project.accountKey
	);

	const [createAccountFlag, { called, loading: addAccountFlagLoading }] = useMutation(addAccountFlag);
	const [fetchAccountSubscriptionGroups, { data: accountSubscriptionGroupsData, loading: getAccountSubscriptionGroupsLoading }] =
		useLazyQuery(getAccountSubscriptionGroups);

	useEffect(() => {
		if (!isLoadingPageGuard && !called) {
			createAccountFlag({
				variables: {
					accountFlag: {
						accountKey: project.accountKey,
						name: 'onboarding',
						userUuid: userAccount.externalReferenceCode,
						value: 1,
					},
				},
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [called, isLoadingPageGuard, project]);

	useEffect(() => {
		if (!isLoadingPageGuard && !addAccountFlagLoading) {
			fetchAccountSubscriptionGroups({
				variables: {
					filter: '(' + accountBriefs
						.map(
							(
								{ externalReferenceCode },
								index,
								{ length: totalAccountBriefs }
							) =>
								`accountKey eq '${externalReferenceCode}' ${index + 1 < totalAccountBriefs ? ' or ' : ' '
								}`
						)
						.join('') + `) and name eq '${ACCOUNT_SUBSCRIPTION_GROUP_NAME}'`,
				},
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [accountBriefs, isLoadingPageGuard, addAccountFlagLoading]);

	useEffect(() => {
		if (accountSubscriptionGroupsData) {
			const accountSubscriptionsGroups =
				accountSubscriptionGroupsData?.c?.accountSubscriptionGroups?.items || [];

			dispatch({
				payload: !!accountSubscriptionsGroups.length,
				type: actionTypes.UPDATE_HAS_SUBSCRIPTION_DXP
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [accountSubscriptionGroupsData])

	if (isLoadingPageGuard || addAccountFlagLoading || getAccountSubscriptionGroupsLoading) {
		return <WelcomeSkeleton />;
	}

	return (
		<Layout
			className="align-items-center d-flex flex-column pt-4 px-6"
			footerProps={{
				middleButton: (
					<BaseButton
						displayType="primary"
						onClick={() =>
							dispatch({
								payload: steps.invites,
								type: actionTypes.CHANGE_STEP,
							})
						}
					>
						Get Started
					</BaseButton>
				),
			}}
			headerProps={{
				greetings: `Hello ${userAccount.name},`,
				title: 'Welcome to Liferay’s Customer Portal',
			}}
		>
			<img
				alt="Costumer Service Intro"
				className="mb-4 pb-1"
				draggable={false}
				height={300}
				src={`${assetsPath}/assets/intro_onboarding.svg`}
				width={391.58}
			/>

			<p className="mb-0 px-1 text-center text-neutral-8">
				Let&apos;s download your DXP activation keys, add any team
				members to your projects and give you a quick tour of the space.
			</p>
		</Layout>
	);
};

Welcome.Skeleton = WelcomeSkeleton;

export default Welcome;

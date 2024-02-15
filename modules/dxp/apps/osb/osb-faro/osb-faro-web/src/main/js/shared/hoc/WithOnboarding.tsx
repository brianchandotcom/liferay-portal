import React, {useContext, useEffect} from 'react';
import SitesDashboardQuery from 'shared/queries/SitesDashboardQuery';
import {close, modalTypes, open} from 'shared/actions/modals';
import {compose} from 'redux';
import {connect} from 'react-redux';
import {graphql} from '@apollo/react-hoc';
import {isArray} from 'lodash';
import {OnboardingContext} from 'shared/context/onboarding';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {User} from 'shared/util/records';

const withOnboarding = (
	WrappedComponent: React.ComponentType<{
		currentUser: User;
		groupId: string;
	}>
) =>
	compose<any>(
		connect(null, {close, open}),
		graphql(SitesDashboardQuery, {options: {variables: {type: null}}})
	)(({close, data, groupId, open, ...otherProps}) => {
		const {onboardingTriggered, setOnboardingTriggered} = useContext(
			OnboardingContext
		);
		const currentUser = useCurrentUser();

		useEffect(() => {
			const {dataSources, loading} = data;

			if (!onboardingTriggered && currentUser.isAdmin()) {
				const triggerCondition =
					!loading && isArray(dataSources) && !dataSources.length;

				if (triggerCondition) {
					open(modalTypes.ONBOARDING_MODAL, {
						groupId,
						onClose: close
					});
					setOnboardingTriggered();
				}
			}
		}, [data]);

		return (
			<WrappedComponent
				currentUser={currentUser}
				groupId={groupId}
				{...otherProps}
			/>
		);
	});

export default withOnboarding;

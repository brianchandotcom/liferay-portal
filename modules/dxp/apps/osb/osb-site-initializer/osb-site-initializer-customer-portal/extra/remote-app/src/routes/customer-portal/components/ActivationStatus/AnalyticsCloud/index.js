/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayIcon from '@clayui/icon';
import {useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';
import client from '../../../../../apolloClient';
import {Button} from '../../../../../common/components';
import {getAccountSubscriptionsTerms} from '../../../../../common/services/liferay/graphql/queries';
import getActivationStatusDateRange from '../../../../../common/utils/getActivationStatusDateRange';
import AnalyticsCloudModal from '../../../components/AnalyticsCloudModal';
import {useCustomerPortal} from '../../../context';
import {
	STATUS_TAG_TYPES,
	STATUS_TAG_TYPE_NAMES,
} from '../../../utils/constants';
import ActivationStatusLayout from '../Layout';

const ActivationStatusAnalyticsCloud = ({
	analyticsCloudEnvironment,
	project,
	subscriptionGroupAnalyticsCloud,
	userAccount,
}) => {
	const [{assetsPath}] = useCustomerPortal();
	const [activationStatusDate, setActivationStatusDate] = useState('');
	const [isVisible, setIsVisible] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setIsVisible(false),
	});

	const subscriptionGroupActivationStatus =
		subscriptionGroupAnalyticsCloud?.activationStatus;

	const currentActivationStatus = {
		[STATUS_TAG_TYPE_NAMES.active]: {
			buttonLink: (
				<a
					className="font-weight-semi-bold m-0 p-0 text-brand-primary text-paragraph"
					href={`https://analytics.liferay.com/workspace/${analyticsCloudEnvironment?.workspaceName}/sites`}
					rel="noopener noreferrer"
					target="_blank"
				>
					Go to Workspace
					<ClayIcon className="ml-1" symbol="order-arrow-right" />
				</a>
			),
			id: STATUS_TAG_TYPES.active,
			subtitle:
				'Your Analytics Cloud environments are ready. Visit the workspace to view Analytics Cloud details.',
			title: 'Analytics Cloud Activation',
		},
		[STATUS_TAG_TYPE_NAMES.inProgress]: {
			id: STATUS_TAG_TYPES.inProgress,
			subtitle:
				'Your Analytics Cloud workspace is being set up and will be available soon',
			title: 'Analytics Cloud Activation',
		},
		[STATUS_TAG_TYPE_NAMES.notActivated]: {
			buttonLink: userAccount.isAdmin && (
				<Button
					appendIcon="order-arrow-right"
					className="btn btn-link font-weight-semi-bold p-0 text-brand-primary text-paragraph"
					displayType="link"
					onClick={() => setIsVisible(true)}
				>
					Finish Activation
				</Button>
			),
			id: STATUS_TAG_TYPES.notActivated,
			subtitle:
				'Almost there! Setup Analytics Cloud by finishing the activation form',
			title: 'Analytics Cloud Activation',
		},
	};

	const activationStatus =
		currentActivationStatus[
			subscriptionGroupActivationStatus ||
				STATUS_TAG_TYPE_NAMES.notActivated
		];

	useEffect(() => {
		const getSubscriptionTerms = async () => {
			const filterAccountSubscriptionERC = `accountSubscriptionGroupERC eq '${project.accountKey}_analytics-cloud'`;
			const {data} = await client.query({
				query: getAccountSubscriptionsTerms,
				variables: {
					filter: filterAccountSubscriptionERC,
				},
			});

			if (data) {
				const ActivationStatusDateRange = getActivationStatusDateRange(
					data.c?.accountSubscriptionTerms?.items
				);
				setActivationStatusDate(ActivationStatusDateRange);
			}
		};

		getSubscriptionTerms();
	}, [project]);

	return (
		<>
			{isVisible && (
				<AnalyticsCloudModal observer={observer} onClose={onClose} />
			)}
			<ActivationStatusLayout
				activationStatus={activationStatus}
				activationStatusDate={activationStatusDate}
				iconPath={`${assetsPath}/assets/navigation-menu/analytics_icon.svg`}
				project={project}
				subscriptionGroupActivationStatus={
					subscriptionGroupActivationStatus
				}
			/>
		</>
	);
};

export default ActivationStatusAnalyticsCloud;

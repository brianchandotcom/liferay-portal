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

import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';
import client from '../../../../apolloClient';
import BaseButton from '../../../../common/components/BaseButton';
import {getAccountSubscriptionsTerms} from '../../../../common/services/liferay/graphql/queries';
import {getCurrentEndDate} from '../../../../common/utils/';
import {useCustomerPortal} from '../../context';
import {status} from '../../utils/constants';
import StatusTag from '../StatusTag';

const ACTIVATION_STATUS_DXP_CLOUD = {
	active: 'Active',
	inProgress: 'In Progress',
	notActivated: 'Not Activated',
};

const ActivationStatus = ({project, subscriptionGroups, userAccount}) => {
	const [{assetsPath}] = useCustomerPortal();
	const [activationStatusDate, setActivationStatusDate] = useState('');
	const subscriptionGroupActivationStatus =
		subscriptionGroups[0]?.activationStatus;

	const currentActivationStatus = {
		[ACTIVATION_STATUS_DXP_CLOUD.active]: {
			link: (
				<a
					className="font-weight-semi-bold m-0 p-0 text-brand-primary text-paragraph"
					href={`https://console.liferay.cloud/projects/${project.code}/overview`}
					rel="noopener noreferrer"
					target="_blank"
				>
					Go to Product Console
					<ClayIcon className="ml-1" symbol="order-arrow-right" />
				</a>
			),
			message:
				'Your DXP Cloud environments are ready. Go to the Product Console to view DXP Cloud details.',
			status: status.active,
		},
		[ACTIVATION_STATUS_DXP_CLOUD.inProgress]: {
			message:
				'Your DXP Cloud environments are being set up and will be available soon.',
			status: status.inProgress,
		},
		[ACTIVATION_STATUS_DXP_CLOUD.notActivated]: {
			link: userAccount.isAdmin && (
				<BaseButton
					appendIcon="order-arrow-right"
					className="btn btn-link font-weight-semi-bold p-0 text-brand-primary text-paragraph"
					displayType="link"
				>
					Finish Activation
				</BaseButton>
			),
			message:
				'Almost there! Setup DXP Cloud by finishing the activation form.',

			status: status.notActivated,
		},
	};

	const activationStatus = {
		buttonLink:
			currentActivationStatus[
				subscriptionGroupActivationStatus || 'Not Activated'
			].link,
		id:
			currentActivationStatus[
				subscriptionGroupActivationStatus || 'Not Activated'
			].status,
		subtitle:
			currentActivationStatus[
				subscriptionGroupActivationStatus || 'Not Activated'
			].message,
	};

	useEffect(() => {
		const getSubscriptionTerms = async () => {
			const filterAccountSubscriptionERC = `accountSubscriptionGroupERC eq '${project.accountKey}_dxp-cloud'`;
			const {data} = await client.query({
				query: getAccountSubscriptionsTerms,
				variables: {
					filter: filterAccountSubscriptionERC,
				},
			});

			if (data) {
				const endDates = data.c?.accountSubscriptionTerms?.items.map(
					(accountSubscriptionTerm) => accountSubscriptionTerm.endDate
				);
				const startDates = data.c?.accountSubscriptionTerms?.items.map(
					(accountSubscriptionTerm) =>
						accountSubscriptionTerm.startDate
				);

				const earliestStartDate = new Date(Math.max(...endDates));
				const farthestEndDate = new Date(Math.max(...startDates));
				setActivationStatusDate(
					`${getCurrentEndDate(
						earliestStartDate
					)} - ${getCurrentEndDate(farthestEndDate)}`
				);
			}
		};

		getSubscriptionTerms();
	}, [project]);

	return (
		<>
			<h2>Activation Status</h2>
			<p className="font-weight-normal text-neutral-7 text-paragraph">
				{activationStatus.subtitle}
			</p>
			<div>
				<ClayCard className="activation-status-container m-0 rounded shadow-none">
					<ClayCard.Body className="px-4 py-3">
						<div className="align-items-center d-flex position-relative">
							<img
								className={classNames(
									'ml-2 mr-4 img-activation-status',
									{
										'in-progress':
											subscriptionGroupActivationStatus ===
											ACTIVATION_STATUS_DXP_CLOUD.inProgress,
										'not-active':
											subscriptionGroupActivationStatus ===
												ACTIVATION_STATUS_DXP_CLOUD.notActivated ||
											!subscriptionGroupActivationStatus,
									}
								)}
								draggable={false}
								height={30.55}
								src={`${assetsPath}/assets/navigation-menu/dxp_icon.svg`}
								width={30.55}
							/>

							<ClayCard.Description
								className="h5 ml-3"
								displayType="title"
								tag="h5"
								title={project.name}
							>
								{project.name}

								<p className="font-weight-normal mb-2 text-neutral-7 text-paragraph">
									{activationStatusDate}
								</p>

								{activationStatus.buttonLink}
							</ClayCard.Description>

							<div className="d-flex justify-content-between ml-auto">
								<ClayCard.Description
									className="label-activation-status position-absolute"
									displayType="text"
									tag="div"
									title={null}
									truncate={false}
								>
									<StatusTag
										currentStatus={activationStatus.id}
									/>
								</ClayCard.Description>
							</div>
						</div>
					</ClayCard.Body>
				</ClayCard>
			</div>
		</>
	);
};

export default ActivationStatus;

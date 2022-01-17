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
import React, {useEffect, useState} from 'react';
import client from '../../../../apolloClient';
import {getAccountSubscriptionsTerms} from '../../../../common/services/liferay/graphql/queries';
import {getCurrentEndDate} from '../../../../common/utils/';
import {useCustomerPortal} from '../../context';
import {status} from '../../utils/constants';
import StatusTag from '../StatusTag';

const ActivationStatus = () => {
	const [
		{assetsPath, project, subscriptionGroups, userAccount},
	] = useCustomerPortal();
	const [activationStatusDate, setActivationStatusDate] = useState('');

	const currentActivationStatus = {
		'Active': {
			className: 'img-activation-status-active',
			link: () => (
				<a
					className="font-weight-semi-bold m-0 p-0 text-brand-primary text-paragraph"
					href={`https://console.liferay.cloud/projects/${project?.code}/overview`}
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
		'In Progress': {
			className: 'img-activation-status-in-progress',
			link: () => {},
			message:
				'Your DXP Cloud environments are being set up and will be available soon.',
			status: status.inProgress,
		},
		'Not Activated': {
			className: 'img-activation-status-not-active',
			link: () => (
				<>
					{userAccount.isAdmin && (
						<a className="btn btn-link font-weight-semi-bold m-0 p-0 text-brand-primary text-paragraph">
							Finish Activation
							<ClayIcon
								className="ml-1"
								symbol="order-arrow-right"
							/>
						</a>
					)}
				</>
			),
			message:
				'Almost there! Setup DXP Cloud by finishing the activation form.',

			status: status.notActivated,
		},
	};

	const activationStatus = {
		buttonLink:
			currentActivationStatus[subscriptionGroups[0]?.activationStatus]
				?.link || currentActivationStatus['Not Activated'].link,
		className:
			currentActivationStatus[subscriptionGroups[0]?.activationStatus]
				?.className ||
			currentActivationStatus['Not Activated'].className,
		id:
			currentActivationStatus[subscriptionGroups[0]?.activationStatus]
				?.status || currentActivationStatus['Not Activated'].status,
		subtitle:
			currentActivationStatus[subscriptionGroups[0]?.activationStatus]
				?.message || currentActivationStatus['Not Activated'].message,
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
				const endDates = data?.c?.accountSubscriptionTerms?.items.map(
					(accountSubscriptionTerm) => accountSubscriptionTerm.endDate
				);
				const startDates = data?.c?.accountSubscriptionTerms?.items.map(
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
	}, [project.accountKey]);

	return (
		<>
			<h2>Activation Status</h2>
			<p className="font-weight-normal text-neutral-7 text-paragraph">
				{activationStatus.subtitle}
			</p>
			<div>
				<ClayCard className="activation-status-container m-0 rounded">
					<ClayCard.Body className="pb-3 pl-4 pr-4 pt-3">
						<div className="align-items-center d-flex position-relative">
							<img
								className={`mr-5 ${activationStatus.className}`}
								draggable={false}
								height={31.98}
								src={`${assetsPath}/assets/navigation-menu/dxp_icon.svg`}
								width={34.36}
							/>

							<ClayCard.Description
								className="h5"
								displayType="title"
								tag="h5"
								title={project?.name}
							>
								{project?.name}

								<p className="font-weight-normal mb-2 text-neutral-7 text-paragraph">
									{activationStatusDate}
								</p>

								{activationStatus.buttonLink()}
							</ClayCard.Description>

							<div className="d-flex justify-content-between ml-auto">
								<ClayCard.Description
									className="label-activation-status"
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

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
import ClayModal, {useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';
import client from '../../../../../apolloClient';
import {Button} from '../../../../../common/components';
import SetupDXPCloud from '../../../../../common/containers/setup-forms/SetupDXPCloudForm';
import {
	getAccountSubscriptionGroups,
	getAccountSubscriptionsTerms,
} from '../../../../../common/services/liferay/graphql/queries';
import getActivationStatusDateRange from '../../../../../common/utils/getActivationStatusDateRange';
import {useCustomerPortal} from '../../../context';
import {actionTypes} from '../../../context/reducer';
import {
	STATUS_TAG_TYPES,
	STATUS_TAG_TYPE_NAMES,
} from '../../../utils/constants';
import ActivationStatusLayout from '../Layout';

const SetupDXPCloudModal = ({
	observer,
	onClose,
	project,
	setVisibleModal,
	subscriptionGroupId,
}) => {
	const [formAlreadySubmitted, setFormAlreadySubmitted] = useState(false);

	return (
		<ClayModal center observer={observer}>
			{formAlreadySubmitted ? (
				<AlreadySubmittedFormModal setVisibleModal={setVisibleModal} />
			) : (
				<SetupDXPCloud
					handlePage={onClose}
					leftButton="Cancel"
					project={project}
					setFormAlreadySubmitted={setFormAlreadySubmitted}
					subscriptionGroupId={subscriptionGroupId}
				/>
			)}
		</ClayModal>
	);
};

const AlreadySubmittedFormModal = ({setVisibleModal}) => {
	return (
		<div className="pt-4 px-4">
			<div className="flex-row mb-2">
				<header className="mb-5">
					<h2 className="mb-1 text-neutral-10">Set up DXP Cloud</h2>

					<p className="text-neutral-7 text-paragraph-sm">
						We&#39;ll need a few details to finish building your DXP
						environment(s).
					</p>
				</header>

				<h5 className="my-1 text-neutral-10">
					Another user already submitted the DXP Cloud activation
					request.
				</h5>

				<p className="mb-5 text-neutral-10">
					Return to the product activation page to view the current
					Activation Status
				</p>
			</div>

			<div className="d-flex justify-content-center mb-4 mt-5">
				<Button
					className="px-3 py-2"
					displayType="primary"
					onClick={() => setVisibleModal(false)}
				>
					Done
				</Button>
			</div>
		</div>
	);
};

const ActivationStatusDXPCloud = ({
	dxpCloudEnvironment,
	project,
	subscriptionGroupDXPCloud,
	userAccount,
}) => {
	const [{assetsPath}, dispatch] = useCustomerPortal();
	const [activationStatusDate, setActivationStatusDate] = useState('');
	const [visible, setVisible] = useState(false);
	const modalProps = useModal({
		onClose: () => setVisible(false),
	});

	const subscriptionGroupActivationStatus =
		subscriptionGroupDXPCloud?.activationStatus;

	const onCloseModal = async (isSuccess) => {
		setVisible(false);

		if (isSuccess) {
			const getSubscriptionGroups = async (accountKey) => {
				const {data: dataSubscriptionGroups} = await client.query({
					query: getAccountSubscriptionGroups,
					variables: {
						filter: `accountKey eq '${accountKey}' and hasActivation eq true`,
					},
				});

				if (dataSubscriptionGroups) {
					const items =
						dataSubscriptionGroups?.c?.accountSubscriptionGroups
							?.items;
					dispatch({
						payload: items,
						type: actionTypes.UPDATE_SUBSCRIPTION_GROUPS,
					});
				}
			};

			getSubscriptionGroups(project.accountKey);
		}
	};

	const currentActivationStatus = {
		[STATUS_TAG_TYPE_NAMES.active]: {
			buttonLink: (
				<a
					className="font-weight-semi-bold m-0 p-0 text-brand-primary text-paragraph"
					href={`https://console.liferay.cloud/projects/${dxpCloudEnvironment?.projectId}/overview`}
					rel="noopener noreferrer"
					target="_blank"
				>
					Go to Product Console
					<ClayIcon className="ml-1" symbol="order-arrow-right" />
				</a>
			),
			id: STATUS_TAG_TYPES.active,
			subtitle:
				'Your DXP Cloud environments are ready. Go to the Product Console to view DXP Cloud details.',
			title: 'Activation Status',
		},
		[STATUS_TAG_TYPE_NAMES.inProgress]: {
			id: STATUS_TAG_TYPES.inProgress,
			subtitle:
				'Your DXP Cloud environments are being set up and will be available soon.',
			title: 'Activation Status',
		},
		[STATUS_TAG_TYPE_NAMES.notActivated]: {
			buttonLink: userAccount.isAdmin && (
				<Button
					appendIcon="order-arrow-right"
					className="btn btn-link font-weight-semi-bold p-0 text-brand-primary text-paragraph"
					displayType="link"
					onClick={() => setVisible(true)}
				>
					Finish Activation
				</Button>
			),
			id: STATUS_TAG_TYPES.notActivated,
			subtitle:
				'Almost there! Setup DXP Cloud by finishing the activation form.',
			title: 'Activation Status',
		},
	};

	const activationStatus =
		currentActivationStatus[
			subscriptionGroupActivationStatus ||
				STATUS_TAG_TYPE_NAMES.notActivated
		];

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
			{visible && (
				<SetupDXPCloudModal
					{...modalProps}
					onClose={onCloseModal}
					project={project}
					setVisibleModal={setVisible}
					subscriptionGroupId={
						subscriptionGroupDXPCloud.accountSubscriptionGroupId
					}
				/>
			)}
			<ActivationStatusLayout
				activationStatus={activationStatus}
				activationStatusDate={activationStatusDate}
				dxpCloudEnvironment={dxpCloudEnvironment}
				iconPath={`${assetsPath}/assets/navigation-menu/dxp_icon.svg`}
				project={project}
				subscriptionGroupActivationStatus={
					subscriptionGroupActivationStatus
				}
				userAccount={userAccount}
			/>
		</>
	);
};

export default ActivationStatusDXPCloud;

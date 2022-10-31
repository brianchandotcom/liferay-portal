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
import ClayAlert from '@clayui/alert';
import {ButtonWithIcon} from '@clayui/core';
import {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';
import i18n from '../../../../../common/I18n';
import {Button, ButtonDropDown} from '../../../../../common/components';
import {useAppPropertiesContext} from '../../../../../common/contexts/AppPropertiesContext';
import {
	getAccountSubscriptionGroups,
	getCommerceOrderItems,
	updateAccountSubscriptionGroups,
} from '../../../../../common/services/liferay/graphql/queries';
import getActivationStatusDateRange from '../../../../../common/utils/getActivationStatusDateRange';
import AnalyticsCloudModal from '../../../components/AnalyticsCloudModal';
import {ALERT_UPDATE_ANALYTICS_CLOUD_STATUS} from '../../../containers/ActivationKeysTable/utils/constants/alertUpdateAnalyticsCloudStatus';
import {useCustomerPortal} from '../../../context';
import {actionTypes} from '../../../context/reducer';
import {
	AUTO_CLOSE_ALERT_TIME,
	STATUS_TAG_TYPES,
	STATUS_TAG_TYPE_NAMES,
} from '../../../utils/constants';
import ActivationStatusLayout from '../Layout';
import AnalyticsCloudStatusModal from './AnalyticsCloudStatusModal';

const ActivationStatusAnalyticsCloud = ({
	analyticsCloudWorkspace,
	project,
	subscriptionGroupAnalyticsCloud,
	userAccount,
}) => {
	const [, dispatch] = useCustomerPortal();
	const {client, liferayWebDAV} = useAppPropertiesContext();
	const [activationStatusDate, setActivationStatusDate] = useState('');
	const [isVisible, setIsVisible] = useState(false);
	const [visible, setVisible] = useState(false);
	const {observer: observerSetupModal, onClose} = useModal({
		onClose: () => setIsVisible(false),
	});
	const {
		observer: observerStatusModal,
		onClose: onCloseStatusModal,
	} = useModal({
		onClose: () => setVisible(false),
	});

	const [
		subscriptionGroupActivationStatus,
		setSubscriptionGroupActivationStatus,
	] = useState(subscriptionGroupAnalyticsCloud?.activationStatus);

	const onCloseSetupModal = async (isSuccess) => {
		onClose();

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

					setSubscriptionGroupActivationStatus(
						STATUS_TAG_TYPE_NAMES.inProgress
					);
				}
			};

			getSubscriptionGroups(project.accountKey);
		}
	};

	const [hasFinishedUpdate, setHasFinishedUpdate] = useState(false);

	const currentActivationStatus = {
		[STATUS_TAG_TYPE_NAMES.active]: {
			buttonLink: (
				<a
					className="font-weight-semi-bold m-0 p-0 text-brand-primary text-paragraph"
					href={`https://analytics.liferay.com/workspace/${analyticsCloudWorkspace?.workspaceGroupId}/sites`}
					rel="noopener noreferrer"
					target="_blank"
				>
					{i18n.translate('go-to-workspace')}

					<ClayIcon className="ml-1" symbol="order-arrow-right" />
				</a>
			),
			id: STATUS_TAG_TYPES.active,
			subtitle: i18n.translate(
				'your-analytics-cloud-environments-are-ready-visit-the-workspace-to-view-analytics-cloud-details'
			),
			title: i18n.translate('analytics-cloud-activation'),
		},
		[STATUS_TAG_TYPE_NAMES.inProgress]: {
			dropdownIcon: (userAccount.isStaff ||
				userAccount.isProvisioning) && (
				<ButtonDropDown
					align={Align.BottomRight}
					customDropDownButton={
						<ButtonWithIcon
							displayType="null"
							small
							symbol="caret-bottom"
						/>
					}
					items={[
						{
							label: i18n.translate('set-to-active'),
							onClick: () => setVisible(true),
						},
					]}
					menuElementAttrs={{
						className: 'p-0 cp-activation-key-icon rounded-xs',
					}}
				/>
			),
			id: STATUS_TAG_TYPES.inProgress,
			subtitle: i18n.translate(
				'your-analytics-cloud-workspace-is-being-set-up-and-will-be-available-soon'
			),
			title: i18n.translate('analytics-cloud-activation'),
		},
		[STATUS_TAG_TYPE_NAMES.notActivated]: {
			buttonLink: userAccount.isAdmin && (
				<Button
					appendIcon="order-arrow-right"
					className="btn btn-link font-weight-semi-bold p-0 text-brand-primary text-paragraph"
					displayType="link"
					onClick={() => setIsVisible(true)}
				>
					{i18n.translate('finish-activation')}
				</Button>
			),
			id: STATUS_TAG_TYPES.notActivated,
			subtitle: i18n.translate(
				'almost-there-setup-analytics-cloud-by-finishing-the-activation-form'
			),
			title: i18n.translate('analytics-cloud-activation'),
		},
	};

	const activationStatus =
		currentActivationStatus[
			subscriptionGroupActivationStatus ||
				STATUS_TAG_TYPE_NAMES.notActivated
		];

	useEffect(() => {
		const fetchCommerceOrderItems = async () => {
			const filterAccountSubscriptionERC = `customFields/accountSubscriptionGroupERC eq '${project.accountKey}_analytics-cloud'`;
			const {data} = await client.query({
				query: getCommerceOrderItems,
				variables: {
					filter: filterAccountSubscriptionERC,
				},
			});

			if (data) {
				const activationStatusDateRange = getActivationStatusDateRange(
					data?.orderItems?.items
				);
				setActivationStatusDate(activationStatusDateRange);
			}
		};

		fetchCommerceOrderItems();
	}, [client, project]);

	const updateGroupId = async () => {
		await Promise.all([
			await client.mutate({
				context: {
					displaySuccess: false,
					type: 'liferay-rest',
				},
				mutation: updateAccountSubscriptionGroups,
				variables: {
					accountSubscriptionGroup: {
						accountEntryId: project.id,
						accountKey: project.accountKey,
						activationStatus: STATUS_TAG_TYPE_NAMES.active,
					},
					id:
						subscriptionGroupAnalyticsCloud?.accountSubscriptionGroupId,
				},
			}),
		]);

		setSubscriptionGroupActivationStatus(STATUS_TAG_TYPE_NAMES.active);
		setVisible(false);
		setHasFinishedUpdate(true);
	};

	return (
		<>
			{isVisible && (
				<AnalyticsCloudModal
					observer={observerSetupModal}
					onClose={onCloseSetupModal}
					project={project}
					subscriptionGroupId={
						subscriptionGroupAnalyticsCloud.accountSubscriptionGroupId
					}
				/>
			)}
			<ActivationStatusLayout
				activationStatus={activationStatus}
				activationStatusDate={activationStatusDate}
				iconPath={`${liferayWebDAV}/assets/navigation-menu/analytics_icon.svg`}
				project={project}
				subscriptionGroupActivationStatus={
					subscriptionGroupActivationStatus
				}
			/>
			{visible && (
				<AnalyticsCloudStatusModal
					groupIdValue={project?.acWorkspaceGroupId}
					observer={observerStatusModal}
					onClose={onCloseStatusModal}
					project={project}
					updateCardStatus={updateGroupId}
				/>
			)}
			{hasFinishedUpdate && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={AUTO_CLOSE_ALERT_TIME.success}
						displayType="success"
						onClose={() => setHasFinishedUpdate(false)}
					>
						{ALERT_UPDATE_ANALYTICS_CLOUD_STATUS.success}
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}
		</>
	);
};

export default ActivationStatusAnalyticsCloud;

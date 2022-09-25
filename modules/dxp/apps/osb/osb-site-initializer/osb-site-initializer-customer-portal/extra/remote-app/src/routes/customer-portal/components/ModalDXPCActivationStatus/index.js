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

import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {useState} from 'react';
import i18n from '../../../../common/I18n';
import {Badge, Button} from '../../../../common/components';
import {useAppPropertiesContext} from '../../../../common/contexts/AppPropertiesContext';
import {
	getDXPCloudEnvironment,
	updateAccountSubscriptionGroups,
	updateDXPCloudEnvironment,
} from '../../../../common/services/liferay/graphql/queries';
import {isLowercaseAndNumbers} from '../../../../common/utils/validations.form';
import {useCustomerPortal} from '../../context';

import {actionTypes} from '../../context/reducer';

import {PRODUCT_TYPES, STATUS_TAG_TYPE_NAMES} from '../../utils/constants';

const ModalDXPCActivationStatus = ({
	accountKey,
	observer,
	onClose,
	projectID,
	projectIdValue,
	setHasFinishedUpdate,
	setProjectIdValue,
	setSubscriptionGroupActivationStatus,
}) => {
	const [hasError, setHasError] = useState();

	const [{project, subscriptionGroups}, dispatch] = useCustomerPortal();
	const {client} = useAppPropertiesContext();

	const handleOnConfirm = () => {
		const errorMessageProductId = isLowercaseAndNumbers(projectIdValue);

		if (errorMessageProductId) {
			setHasError(errorMessageProductId);

			return;
		}
		updateProjectId(accountKey);
		updateSubscriptionGroupsStatus();
		onClose();
	};

	const updateSubscriptionGroupsStatus = async () => {
		const dxpCloudSubscriptionGroup = subscriptionGroups.find(
			(subscription) => subscription.name === PRODUCT_TYPES.dxpCloud
		);

		await client.mutate({
			context: {
				displaySuccess: false,
			},

			mutation: updateAccountSubscriptionGroups,
			variables: {
				accountSubscriptionGroup: {
					accountKey: project.accountKey,
					activationStatus: STATUS_TAG_TYPE_NAMES.active,
					manageContactsURL: `https://console.liferay.cloud/projects/${projectIdValue}/overview`,
				},
				id: dxpCloudSubscriptionGroup?.accountSubscriptionGroupId,
			},
		});

		setSubscriptionGroupActivationStatus(STATUS_TAG_TYPE_NAMES.active);
		setHasFinishedUpdate(true);

		const newSubscriptionGroups = subscriptionGroups.map((subscription) => {
			if (
				subscription.accountSubscriptionGroupId ===
				dxpCloudSubscriptionGroup?.accountSubscriptionGroupId
			) {
				return {
					...subscription,
					activationStatus: STATUS_TAG_TYPE_NAMES.active,
				};
			}

			return subscription;
		});

		dispatch({
			payload: newSubscriptionGroups,
			type: actionTypes.UPDATE_SUBSCRIPTION_GROUPS,
		});
	};

	const updateProjectId = async (accountKey) => {
		const {data: dataDXPCEnvironment} = await client.query({
			query: getDXPCloudEnvironment,
			variables: {
				filter: `accountKey eq '${accountKey}'`,
				scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
			},
		});

		const dxpCloudEnvironment =
			dataDXPCEnvironment?.c?.dXPCloudEnvironments?.items[0];

		if (dxpCloudEnvironment) {
			await client.mutate({
				context: {
					displaySuccess: false,
				},

				mutation: updateDXPCloudEnvironment,
				variables: {
					DXPCloudEnvironment: {
						projectId: projectIdValue,
					},
					dxpCloudEnvironmentId:
						dxpCloudEnvironment.dxpCloudEnvironmentId,
				},
			});
		}
	};

	return (
		<>
			<ClayModal center observer={observer}>
				<div className="bg-neutral-1 cp-liferay-experience-cloud-status-modal">
					<div className="d-flex justify-content-between">
						<h4 className="ml-4 mt-4 text-brand-primary text-paragraph">
							{i18n.translate('lxc-sm-setup').toUpperCase()}
						</h4>

						<div className="mr-4 mt-3">
							<Button
								appendIcon="times"
								aria-label="close"
								displayType="unstyled"
								onClick={onClose}
							/>
						</div>
					</div>

					<h2 className="ml-4 text-neutral-10">
						{i18n.translate('project-id-confirmation')}
					</h2>

					<p className="mb-2 ml-4 mt-4">
						{i18n.translate(
							'confirm-the-final-project-id-used-to-create-the-customer-s-lxc-sm-environments'
						)}
					</p>

					<div className="mx-2">
						<ClayForm.Group
							className={classNames('w-100 mb-1', {
								'has-error': hasError,
							})}
						>
							<label>
								<ClayInput
									id="basicInputText"
									onChange={({target}) =>
										setProjectIdValue(target.value)
									}
									placeholder={projectID}
									type="text"
									value={projectIdValue}
								/>
							</label>
						</ClayForm.Group>

						{hasError ? (
							<Badge>
								<span className="pl-1">{hasError}</span>
							</Badge>
						) : (
							<p className="pl-3 pr-2 text-neutral-7 text-paragraph-sm">
								{i18n.translate(
									'please-enter-the-project-id-here'
								)}
							</p>
						)}
					</div>

					<div className="d-flex my-4 px-4">
						<Button
							displayType="secondary ml-auto mt-2"
							onClick={onClose}
						>
							{i18n.translate('cancel')}
						</Button>

						<Button
							disabled={!projectIdValue}
							displayType="primary ml-3 mt-2"
							onClick={handleOnConfirm}
						>
							{i18n.translate('confirm')}
						</Button>
					</div>
				</div>
			</ClayModal>
		</>
	);
};

export default ModalDXPCActivationStatus;

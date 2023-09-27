/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {useState} from 'react';
import i18n from '../../../../../common/I18n';
import {Badge, Button} from '../../../../../common/components';
import {useUpdateAccountSubscriptionGroup} from '../../../../../common/services/liferay/graphql/account-subscription-groups/queries/useUpdateAccountSubscriptionGroup';
import {useUpdateLiferayExperienceCloudEnvironment} from '../../../../../common/services/liferay/graphql/liferay-experience-cloud-environments/queries/useUpdateLiferayExperienceCloudEnvironment';
import getHandleOnConfirm from './utils/getHandleOnConfirm';
import getUpdateProjectId from './utils/getUpdateProjectId';
import getUpdateSubscriptionGroupsStatus from './utils/getUpdateSubscriptionGroupsStatus';

const LiferayExperienceCloudModal = ({
	accountKey,
	dispatch,
	handleFinishUpdate,
	handleStatusLxcActivation,
	lxcEnvironment,
	observer,
	onClose,
	project,
	subscriptionGroupLxcEnvironment,
	subscriptionGroups,
}) => {
	const [hasError, setHasError] = useState();
	const handleError = (error) => setHasError(error);
	const projectId = lxcEnvironment?.projectId;

	const [projectIdValue, setProjectIdValue] = useState('');

	const [
		updateLiferayExperienceCloudEnvironment,
	] = useUpdateLiferayExperienceCloudEnvironment();

	const [
		updateAccountSubscriptionGroup,
	] = useUpdateAccountSubscriptionGroup();

	const handleOnConfirm = () => {
		getHandleOnConfirm(
			updateSubscriptionGroupsStatus,
			handleError,
			projectIdValue,
			onClose
		);

		updateSubscriptionGroupsStatus();
		updateProjectId(accountKey);
		onClose();
	};

	const updateSubscriptionGroupsStatus = async () => {
		getUpdateSubscriptionGroupsStatus(
			dispatch,
			handleFinishUpdate,
			handleStatusLxcActivation,
			project,
			projectIdValue,
			subscriptionGroupLxcEnvironment,
			subscriptionGroups,
			updateAccountSubscriptionGroup
		);
	};

	const updateProjectId = async () => {
		getUpdateProjectId(
			projectIdValue,
			lxcEnvironment,
			updateLiferayExperienceCloudEnvironment
		);
	};

	return (
		<div>
			<ClayModal center className="lg" observer={observer}>
				<div className="bg-neutral-1 cp-liferay-experience-cloud-status-modal">
					<div className="d-flex justify-content-between">
						<h4 className="ml-4 mt-4 text-brand-primary text-paragraph">
							{i18n
								.translate('liferay-experience-cloud-setup')
								.toUpperCase()}
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
						{i18n.translate('lxc-project-id')}
					</h2>

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
									placeholder={projectId}
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
							<p className="mb-2 ml-4 mt-4">
								{i18n.translate(
									'please-confirm-the-lxm-project-id'
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
		</div>
	);
};
export default LiferayExperienceCloudModal;

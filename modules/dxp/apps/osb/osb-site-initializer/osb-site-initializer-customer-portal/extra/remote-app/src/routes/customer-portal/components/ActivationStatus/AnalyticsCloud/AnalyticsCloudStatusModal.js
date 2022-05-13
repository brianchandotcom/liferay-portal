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
import i18n from '../../../../../common/I18n';
import {Badge, Button} from '../../../../../common/components';
import {isLowercaseAndNumbers} from '../../../../../common/utils/validations.form';

const AnalyticsCloudStatusModal = ({
	groupIdValue,
	observer,
	onClose,
	setGroupIdValue,
	updateCardStatus,
}) => {
	const [hasError, setHasError] = useState();

	const handleOnConfirm = () => {
		const errorMessageGroupId = isLowercaseAndNumbers(groupIdValue);

		if (errorMessageGroupId) {
			setHasError(errorMessageGroupId);

			return;
		}

		updateCardStatus();
	};

	return (
		<>
			<ClayModal center observer={observer}>
				<div className="bg-neutral-1 cp-analytics-cloud-status-modal">
					<div className="d-flex justify-content-between">
						<h4 className="ml-4 mt-4 text-brand-primary text-paragraph">
							{i18n
								.translate('analytics-cloud-setup')
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
						{i18n.translate('group-id-confirmation')}
					</h2>

					<p className="mb-2 ml-4 mt-4">
						{i18n.translate(
							'confirm-the-final-group-id-used-to-create-the-customer-s-analytics-cloud-environments'
						)}
					</p>

					<div className="mx-2">
						<ClayForm.Group
							className={classNames('w-100', {
								'has-error': hasError,
							})}
						>
							<label>
								<ClayInput
									onChange={({target}) =>
										setGroupIdValue(target.value)
									}
									placeholder="Group ID"
									value={groupIdValue}
								/>
							</label>

							{hasError ? (
								<Badge>
									<span className="pl-1">{hasError}</span>
								</Badge>
							) : (
								<p className="ml-3 pl-3 pr-2 text-neutral-7 text-paragraph-sm">
									{i18n.translate(
										'please-enter-here-the-workspace-s-group-id'
									)}
								</p>
							)}
						</ClayForm.Group>
					</div>

					<div className="d-flex my-4 px-4">
						<Button
							displayType="secondary ml-auto mt-2"
							onClick={onClose}
						>
							{i18n.translate('cancel')}
						</Button>

						<Button
							disabled={!groupIdValue}
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
export default AnalyticsCloudStatusModal;

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

import ClayModal from '@clayui/modal';
import i18n from '../../../../../common/I18n';
import {Button} from '../../../../../common/components';

const AnalyticsCloudStatusModal = ({
	groupIdValue,
	observer,
	onClose,
	updateCardStatus,
}) => {
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
							'please-make-sure-the-correct-workspace-group-id-is-saved-in-raysource'
						)}
					</p>

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
							onClick={() => updateCardStatus()}
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

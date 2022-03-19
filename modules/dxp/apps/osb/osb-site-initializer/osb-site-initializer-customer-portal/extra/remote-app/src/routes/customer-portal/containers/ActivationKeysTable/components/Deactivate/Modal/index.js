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
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import Button from '../../../../../../../common/components/Button';
import {ALERT_DOWNLOAD_TYPE} from '../../../../../utils/constants';

const DeactivateKeysModal = ({
	deactivateKeysConfirm,
	deactivateKeysStatus,
	isDeactivating,
	observer,
	onClose,
}) => {
	return (
		<ClayModal center observer={observer}>
			<div className="pt-4 px-4">
				<div className="flex-row mb-1">
					<div className="d-flex justify-content-between">
						<h2 className="text-neutral-10">
							Confirm Deactivation Terms
						</h2>

						<Button
							appendIcon="times"
							aria-label="close"
							className="align-self-start"
							displayType="unstyled"
							onClick={onClose}
						/>
					</div>

					<p className="mb-6 mt-5 text-neutral-10">
						I certify that the instance(s) activated with the
						selected activation key(s) has/have been shut down and
						that there is no Liferay software installed, deployed,
						used or executed that is activated with the selected
						activation key(s).
					</p>
				</div>

				<div className="d-flex justify-content-end my-4">
					<Button displayType="secondary" onClick={onClose}>
						Cancel
					</Button>

					<Button
						className={classNames('bg-danger d-flex ml-2', {
							'cp-deactivate-loading': isDeactivating,
						})}
						onClick={deactivateKeysConfirm}
					>
						{isDeactivating ? (
							<>
								<span className="cp-spinner mr-2 mt-1 spinner-border spinner-border-sm"></span>
								Deactivating...
							</>
						) : (
							'Confirm & Deactivate Keys'
						)}
					</Button>
				</div>
			</div>

			{!isDeactivating &&
				deactivateKeysStatus === ALERT_DOWNLOAD_TYPE.danger && (
					<div className="allign cp-error-alert d-flex px-4 py-3">
						<ClayIcon
							className="mr-2 mt-1 text-danger"
							symbol="info-circle"
						/>

						<p className="m-0 text-danger text-paragraph">
							There was an unexpected error while attempting to
							deactivate keys. Please try again in a few moments
						</p>
					</div>
				)}
		</ClayModal>
	);
};

export default DeactivateKeysModal;

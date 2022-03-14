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

import {ClayInput} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {Button} from '../../../../common/components';

const ModalDXPCActivationStatus = ({observer, onClose, projectID}) => {
	return (
		<>
			<ClayModal center observer={observer}>
				<div className="bg-neutral-1 cp-analytics-cloud-status-modal">
					<div className="d-flex justify-content-between">
						<h4 className="ml-4 mt-4 text-brand-primary text-paragraph">
							DXP CLOUD SETUP
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
						Project ID Confirmation
					</h2>

					<p className="mb-2 ml-4 mt-4">
						Confirm the final Project ID used to create the
						customer&apos;s DXP Cloud environments.
					</p>

					<div className="ml-4 mr-4">
						<ClayInput
							id="basicInputText"
							placeholder={projectID}
							type="text"
						/>
					</div>

					<p className="ml-4 mt-1 px-3 text-neutral-7 text-paragraph-sm">
						Please enter the Project ID here.
					</p>

					<div className="d-flex my-4 px-4">
						<Button
							displayType="secondary ml-auto mt-2"
							onClick={onClose}
						>
							Cancel
						</Button>

						<Button displayType="primary ml-3 mt-2">Confirm</Button>
					</div>
				</div>
			</ClayModal>
		</>
	);
};
export default ModalDXPCActivationStatus;

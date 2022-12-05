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

import Button, {ClayButtonWithIcon} from '@clayui/button';
import ClayModal from '@clayui/modal';

import {DealRegistrationItem} from '../../DealRegistrationList';
import ModalFormatedInformation from '../ModalFormatedInformation';

interface ModalContentProps {
	content: DealRegistrationItem;
	onClose: () => void;
}

export default function ModalContent({content, onClose}: ModalContentProps) {
	return (
		<ClayModal.Body>
			<div className="align-items-center d-flex justify-content-between mb-4">
				<h3 className="col-6 mb-0">Opportunities Details</h3>

				<ClayButtonWithIcon
					displayType={null}
					onClick={onClose}
					symbol="times"
				/>
			</div>

			<div className="mb-4">
				<div className="align-items-center d-flex justify-content-between mb-4">
					<div className="col-8">
						<ModalFormatedInformation
							className="mb-4"
							information={content['ACCOUNT-NAME']}
							label="Account Name"
						/>

						<ModalFormatedInformation
							information={content['STAGE']}
							label="Stage"
						/>
					</div>

					<div className="col-4 d-flex flex-column">
						<ModalFormatedInformation
							className="mb-4"
							information={content['START-DATE']}
							label="Start Date"
						/>

						<ModalFormatedInformation
							information={content['END-DATE']}
							label="End Date"
						/>
					</div>
				</div>

				<div className="d-flex">
					<ModalFormatedInformation
						className="col"
						information={content['DEAL-AMOUNT']}
						label="Amount"
					/>

					<ModalFormatedInformation
						className="col"
						information={content['PARTNER-REP']}
						label="Partner Rep"
					/>

					<ModalFormatedInformation
						className="col"
						information={content['LIFERAY-REP']}
						label="Liferay Rep"
					/>
				</div>
			</div>

			<div className="d-flex justify-content-end">
				<Button displayType="secondary" onClick={onClose}>
					Close
				</Button>
			</div>
		</ClayModal.Body>
	);
}

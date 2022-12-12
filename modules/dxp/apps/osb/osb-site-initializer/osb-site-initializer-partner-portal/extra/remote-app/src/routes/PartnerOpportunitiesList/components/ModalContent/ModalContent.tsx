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

import ModalFormatedInformation from '../../../../common/components/ModalFormatedInformation';
import {PartnerOpportunitiesColumnKey} from '../../../../common/enums/partnerOpportunitiesColumnKey';
import {PartnerOpportunitiesItem} from '../../PartnerOpportunitiesList';

interface ModalContentProps {
	content: PartnerOpportunitiesItem;
	onClose: () => void;
}

export default function ModalContent({content, onClose}: ModalContentProps) {
	return (
		<ClayModal.Body>
			<div className="align-items-center d-flex justify-content-between mb-4">
				<h3 className="col-6 mb-0">Leads Details</h3>

				<ClayButtonWithIcon
					displayType={null}
					onClick={onClose}
					symbol="times"
				/>
			</div>

			<div className="mb-4">
				<div className="d-flex mb-3">
					<ModalFormatedInformation
						className="col"
						information={
							content[PartnerOpportunitiesColumnKey.ACCOUNT_NAME]
						}
						label="Account Name"
					/>

					<ModalFormatedInformation
						className="col"
						information={
							content[
								PartnerOpportunitiesColumnKey.PARTNER_REP_NAME
							]
						}
						label="Partner Rep Name"
					/>
				</div>

				<div className="d-flex mb-3">
					<ModalFormatedInformation
						className="col"
						information={
							content[PartnerOpportunitiesColumnKey.START_DATE]
						}
						label="Start Date"
					/>

					<ModalFormatedInformation
						className="col"
						information={
							content[
								PartnerOpportunitiesColumnKey.PARTNER_REP_EMAIL
							]
						}
						label="Partner Rep Email"
					/>
				</div>

				<div className="d-flex mb-3">
					<ModalFormatedInformation
						className="col"
						information={
							content[PartnerOpportunitiesColumnKey.END_DATE]
						}
						label="End Date

						"
					/>

					<ModalFormatedInformation
						className="col"
						information={
							content[PartnerOpportunitiesColumnKey.LIFERAY_REP]
						}
						label="Liferay Rep"
					/>
				</div>

				<div className="d-flex mb-3">
					<ModalFormatedInformation
						className="col"
						information={
							content[PartnerOpportunitiesColumnKey.DEAL_AMOUNT]
						}
						label="Deal Amount"
					/>

					<ModalFormatedInformation
						className="col"
						information={
							content[PartnerOpportunitiesColumnKey.STAGE]
						}
						label="Stage"
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

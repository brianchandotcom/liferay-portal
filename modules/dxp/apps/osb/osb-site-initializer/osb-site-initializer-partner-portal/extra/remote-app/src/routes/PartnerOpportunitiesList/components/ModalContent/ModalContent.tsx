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
import PartnerOpportunitiesItem from '../../interfaces/partnerOpportunitiesItem';

interface IProps {
	content: PartnerOpportunitiesItem | undefined;
	onClose: () => void;
}

export default function ModalContent({content, onClose}: IProps) {
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

			<div className="d-md-flex">
				<div className="col">
					{content?.[
						PartnerOpportunitiesColumnKey.PARTNER_ACCOUNT_NAME
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey
										.PARTNER_ACCOUNT_NAME
								]
							}
							label="Partner Account Name"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.CURRENCY] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey.CURRENCY
								]
							}
							label="Currency"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.PARTNER_NAME] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey.PARTNER_NAME
								]
							}
							label="Partner Name"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.ACCOUNT_NAME] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey.ACCOUNT_NAME
								]
							}
							label="Account Name"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.START_DATE] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey.START_DATE
								]
							}
							label="Start Date"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.END_DATE] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey.END_DATE
								]
							}
							label="End Date"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.CLOSE_DATE] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey.CLOSE_DATE
								]
							}
							label="Close Date"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.DEAL_AMOUNT] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey.DEAL_AMOUNT
								]
							}
							label="Deal Amount"
						/>
					)}
				</div>

				<div className="col">
					{content?.[
						PartnerOpportunitiesColumnKey.PARTNER_REP_NAME
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey
										.PARTNER_REP_NAME
								]
							}
							label="Partner Rep Name"
						/>
					)}

					{content?.[
						PartnerOpportunitiesColumnKey.PARTNER_REP_EMAIL
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey
										.PARTNER_REP_EMAIL
								]
							}
							label="Partner Rep Email"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.LIFERAY_REP] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey.LIFERAY_REP
								]
							}
							label="Liferay Rep"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.STAGE] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[PartnerOpportunitiesColumnKey.STAGE]
							}
							label="Stage"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.TYPE] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[PartnerOpportunitiesColumnKey.TYPE]
							}
							label="Type"
						/>
					)}
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

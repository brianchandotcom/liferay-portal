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
import {DealRegistrationColumnKey} from '../../../../common/enums/dealRegistrationColumnKey';
import {DealRegistrationItem} from '../../DealRegistrationList';

interface ModalContentProps {
	content: DealRegistrationItem;
	onClose: () => void;
}

export default function ModalContent({content, onClose}: ModalContentProps) {	

	return (
		<ClayModal.Body>
			<div className="align-items-center d-flex justify-content-between mb-4">
				<h3 className="col-6 mb-0">Partner Deal Registration</h3>

				<ClayButtonWithIcon
					displayType={null}
					onClick={onClose}
					symbol="times"
				/>
			</div>

			<div className="d-flex">
				<div className="col">
					{content[DealRegistrationColumnKey.ACCOUNT_NAME] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[DealRegistrationColumnKey.ACCOUNT_NAME]
							}
							label="Account Name"
						/>
					)}

					{content[DealRegistrationColumnKey.DATE_SUBMITTED] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey.DATE_SUBMITTED
								]
							}
							label="Date Submitted"
						/>
					)}

					{content[DealRegistrationColumnKey.STATUS] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[DealRegistrationColumnKey.STATUS]
							}
							label="Status"
						/>
					)}

					{content[DealRegistrationColumnKey.STATUS_DETAIL] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[DealRegistrationColumnKey.STATUS_DETAIL]
							}
							label="Status Detail"
						/>
					)}

					{content[DealRegistrationColumnKey.PROSPECT_ADDRESS] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey.PROSPECT_ADDRESS
								]
							}
							label="Prospect Address"
						/>
					)}
					
					{content[DealRegistrationColumnKey.CURRENCY_NAME] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey.CURRENCY_NAME
								]
							}
							label="Currency"	
						/>
					)}		

					{content[
						DealRegistrationColumnKey.CURRENCY_KEY
					] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey
										.CURRENCY_KEY
								]
							}
							label="Currency abreviation"
						/>
					)}

					{content[
						DealRegistrationColumnKey.COUTRY_CODE
					] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey
										.COUTRY_CODE
								]
							}
							label="Country Code"
						/>
					)}								
				</div>

				<div className="col">
					{content[
						DealRegistrationColumnKey.PROPECT_BUSINES_UNIT
					] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey.PROPECT_BUSINES_UNIT
								]
							}
							label="Prospect Busines Unit"
						/>
					)}

					{content[
						DealRegistrationColumnKey.PROSPECT_ACCOUNT_NAME
					] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey.PROSPECT_ACCOUNT_NAME
								]
							}
							label="Prospect Account Name"
						/>
					)}

					{content[
						DealRegistrationColumnKey.PROSPECT_DEPARTMENT
					] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey.PROSPECT_DEPARTMENT
								]
							}
							label=" Prospect Department"
						/>
					)}		

					{content[
						DealRegistrationColumnKey.PROSPECT_JOB_ROLE
					] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey.PROSPECT_JOB_ROLE
								]
							}
							label=" Prospect Job Role"
						/>
					)}			

					{content[DealRegistrationColumnKey.PROSPECT_CITY] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[DealRegistrationColumnKey.PROSPECT_CITY]
							}
							label="Prospect City"
						/>
					)}

					{content[DealRegistrationColumnKey.PROSPECT_INDUSTRY] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey.PROSPECT_INDUSTRY
								]
							}
							label="Prospect Industry"
						/>
					)}

					{content[
						DealRegistrationColumnKey.PROSPECT_POSTAL_CODE
					] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey
										.PROSPECT_POSTAL_CODE
								]
							}
							label="Prospect Postal Code"
						/>
					)}

					{content[
						DealRegistrationColumnKey.PARTNER_ACCOUNT_NAME
					] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey
										.PARTNER_ACCOUNT_NAME
								]
							}
							label="Partner Accoount Name"
						/>
					)}

					{content[
						DealRegistrationColumnKey.ADDITIONAL_CONTACTS
					] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey
										.ADDITIONAL_CONTACTS
								]
							}
							label="Additional Contacts"
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

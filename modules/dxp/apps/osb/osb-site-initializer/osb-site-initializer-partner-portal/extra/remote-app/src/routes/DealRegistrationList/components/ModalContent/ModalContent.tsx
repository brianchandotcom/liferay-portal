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
				</div>

				<div className="col">
					{content[
						DealRegistrationColumnKey.PRIMARY_PROSPECT_NAME
					] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey
										.PRIMARY_PROSPECT_NAME
								]
							}
							label="Primary Prospect Name"
						/>
					)}

					{content[
						DealRegistrationColumnKey.PRIMARY_PROSPECT_EMAIL
					] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey
										.PRIMARY_PROSPECT_EMAIL
								]
							}
							label="Primary Prospect Email"
						/>
					)}

					{content[
						DealRegistrationColumnKey.PRIMARY_PROSPECT_PHONE
					] && (
						<ModalFormatedInformation
							className="col mb-3"
							information={
								content[
									DealRegistrationColumnKey
										.PRIMARY_PROSPECT_PHONE
								]
							}
							label="Primary Prospect Phone"
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

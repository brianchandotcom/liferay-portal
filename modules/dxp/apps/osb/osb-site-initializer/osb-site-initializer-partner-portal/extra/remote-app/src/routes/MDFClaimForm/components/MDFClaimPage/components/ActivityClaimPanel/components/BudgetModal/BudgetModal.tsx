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

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import {useState} from 'react';

import PRMForm from '../../../../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../../../../common/components/PRMFormik';
import MDFClaimBudget from '../../../../../../../../common/interfaces/mdfClaimBudget';
import getIconSpriteMap from '../../../../../../../../common/utils/getIconSpriteMap';

interface IProps {
	name: string;
	onCancel: React.MouseEventHandler<HTMLButtonElement>;
	onConfirm: (claimAmount?: number, invoice?: File) => void;
}

const BudgetModal = ({
	name,
	observer,
	onCancel,
	onConfirm,
	...budget
}: Partial<MDFClaimBudget> &
	IProps &
	Pick<ReturnType<typeof useModal>, 'observer'>) => {
	const [currentClaimAmount, setCurrentClaimAmount] = useState<
		number | undefined
	>(budget?.claimAmount);

	const [currentInvoiceFile, setCurrentInvoiceFile] = useState<
		File | undefined
	>(budget?.invoice);

	return (
		<ClayModal center observer={observer} spritemap={getIconSpriteMap()}>
			<div className="bg-brand-primary-lighten-6 p-4">
				<ClayModal.Header className="p-0">
					<h5 className="text-neutral-9">{budget?.expenseName}</h5>

					<h6 className="text-neutral-6">
						Enter the amount of claim and upload proof of
						performance
					</h6>
				</ClayModal.Header>

				<ClayModal.Body className="p-0 pt-4">
					<PRMFormik.Field
						component={PRMForm.InputCurrency}
						description="Silver Partner can claim up to 50%"
						label="Claim Amount"
						name={`${name}.claimAmount`}
						onAccept={(value: number) =>
							setCurrentClaimAmount(value)
						}
						required
						value={currentClaimAmount}
					/>

					<PRMFormik.Field
						component={PRMForm.InputFile}
						displayType="secondary"
						label="Third Party Invoice"
						name={`${name}.invoice`}
						onAccept={(value: File) => {
							if (value) {
								setCurrentInvoiceFile(value);
							}
						}}
						outline
						required
						small
						value={currentInvoiceFile}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					className="pb-0 pt-3 px-0"
					last={
						<div>
							<ClayButton
								className="mr-4"
								displayType="secondary"
								onClick={onCancel}
							>
								Cancel
							</ClayButton>

							<ClayButton
								onClick={() =>
									onConfirm(
										currentClaimAmount,
										currentInvoiceFile
									)
								}
							>
								Save
							</ClayButton>
						</div>
					}
				/>
			</div>
		</ClayModal>
	);
};
export default BudgetModal;

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

import PRMForm from '../../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../../common/components/PRMFormik';
import MDFClaimBudget from '../../../../../../common/interfaces/mdfClaimBudget';

interface IProps {
	activityId?: number;
	currentActivityIndex?: number;
	currentBudgetIndex?: number;
	setFieldValue: (
		field: string,
		value: any,
		shouldValidate?: boolean
	) => void;
}
type BudgetCost = {
	[key: number]: {
		inputedValue?: number;
		savedValue?: number;
	};
};

const BudgetModal = ({
	activityId,
	currentActivityIndex,
	currentBudgetIndex,
	observer,
	onOpenChange,
	open,
	setFieldValue,
	...budget
}: Omit<ReturnType<typeof useModal>, 'onClose'> & MDFClaimBudget & IProps) => {
	const [budgetCost, setBudgetCost] = useState<BudgetCost>({});

	const key = budget.id ?? '';

	return (
		<>
			{open && (
				<ClayModal center disableAutoClose observer={observer}>
					<div className="bg-brand-primary-lighten-6 p-6">
						<ClayModal.Header>
							{budget.expense && budget?.expense?.name}

							<h6 className="text-neutral-6">
								Enter the amount of claim and upload proof of
								performance
							</h6>
						</ClayModal.Header>

						<ClayModal.Body>
							<div>
								<PRMFormik.Field
									component={PRMForm.InputCurrency}
									description="Silver Partner can claim up to 50%"
									label="Claim Amount"
									name={`mdfClaimActivities[${currentActivityIndex}].mdfClaimBudgets[${currentBudgetIndex}].cost`}
									onAccept={(value: number) => {
										setBudgetCost(
											key
												? {
														...budgetCost,
														[key]: {
															...budgetCost[key],
															inputedValue: value,
															savedValue: budgetCost[
																key
															]?.savedValue
																? budgetCost[
																		key
																  ]?.savedValue
																: budget.cost,
														},
												  }
												: budgetCost
										);
										setFieldValue(
											`mdfClaimActivities[${currentActivityIndex}].mdfClaimBudgets[${currentBudgetIndex}].cost`,
											value
										);
									}}
									placeholder={budget.cost}
									required
								/>

								<PRMFormik.Field
									activityId={activityId}
									budgetId={budget.id}
									component={PRMForm.InputFile}
									label="Third Party Invoices"
									name={`mdfClaimDocuments.budgets[${currentActivityIndex}.thirdPartyInvoices]`}
									required
									setFieldValue={setFieldValue}
									typeDocument="ListLeads"
								/>
							</div>
						</ClayModal.Body>

						<ClayModal.Footer
							last={
								<div>
									<ClayButton
										className="mr-4"
										displayType="secondary"
										onClick={() => {
											onOpenChange(false);
											setFieldValue(
												`mdfClaimActivities[${currentActivityIndex}].mdfClaimBudgets[${currentBudgetIndex}].cost`,

												key &&
													budgetCost[key]?.savedValue
											);
											setFieldValue(
												`mdfClaimActivities[${currentActivityIndex}].mdfClaimBudgets[${currentBudgetIndex}].cost`,

												key &&
													budgetCost[key]?.savedValue
											);
										}}
									>
										Cancel
									</ClayButton>

									<ClayButton
										onClick={() => {
											onOpenChange(false);
											setFieldValue(
												`mdfClaimActivities[${currentActivityIndex}].mdfClaimBudgets[${currentBudgetIndex}].cost`,

												key &&
													budgetCost[key]
														?.inputedValue
											);
											setBudgetCost(
												key
													? {
															...budgetCost,
															[key]: {
																...budgetCost[
																	key
																],
																savedValue:
																	budgetCost[
																		key
																	]
																		?.inputedValue,
															},
													  }
													: budgetCost
											);
										}}
									>
										Confirm
									</ClayButton>
								</div>
							}
						/>
					</div>
				</ClayModal>
			)}
		</>
	);
};
export default BudgetModal;

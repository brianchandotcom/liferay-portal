import ClayModal from '@clayui/modal';
import ClayButton from '@clayui/button';
import PRMFormik from '../../../../../../common/components/PRMFormik';
import PRMForm from '../../../../../../common/components/PRMForm';
import {useModal} from '@clayui/modal';
import {useState} from 'react';
import MDFClaimBudget from '../../../../../../common/interfaces/mdfClaimBudget';

interface IProps {
	currentBudgetIndex: number | undefined;
	currentActivityIndex: number | undefined;
	activityId?: number;
	setFieldValue: (
		field: string,
		value: any,
		shouldValidate?: boolean | undefined
	) => void;
}
type BugetCost = {
	[key: number]: {
		savedValue?: number;
		inputedValue?: number;
	};
};

const BudgetModal = ({
	currentActivityIndex,
	activityId,
	observer,
	setFieldValue,
	onOpenChange,
	open,
	currentBudgetIndex,
	...budget
}: Omit<ReturnType<typeof useModal>, 'onClose'> & MDFClaimBudget & IProps) => {
	const [bugetCost, setBugetCost] = useState<BugetCost>({});

	const key = budget.id || '';

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
									label="Claim Amount"
									name={`mdfClaimActivities[${currentActivityIndex}].mdfClaimBudgets[${currentBudgetIndex}].cost`}
									placeholder={budget.cost}
									onAccept={(value: number) => {
										setBugetCost(
											key
												? {
														...bugetCost,
														[key]: {
															...bugetCost[key],
															savedValue: bugetCost[
																key
															]?.savedValue
																? bugetCost[key]
																		?.savedValue
																: budget.cost,
															inputedValue: value,
														},
												  }
												: bugetCost
										);
										setFieldValue(
											`mdfClaimActivities[${currentActivityIndex}].mdfClaimBudgets[${currentBudgetIndex}].cost`,
											value
										);
									}}
									required
									description="Silver Partner can claim up to 50%"
								/>

								<PRMFormik.Field
									component={PRMForm.InputFile}
									label="Third Party Invoices"
									name={`mdfClaimDocuments.budgets[${currentActivityIndex}.thirdPartyInvoices]`}
									budgetId={budget.id}
									activityId={activityId}
									setFieldValue={setFieldValue}
									typeDocument="ListLeads"
									required
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
													bugetCost[key]?.savedValue
											);
											setFieldValue(
												`mdfClaimActivities[${currentActivityIndex}].mdfClaimBudgets[${currentBudgetIndex}].cost`,

												key &&
													bugetCost[key]?.savedValue
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
													bugetCost[key]?.inputedValue
											);
											setBugetCost(
												key
													? {
															...bugetCost,
															[key]: {
																...bugetCost[
																	key
																],
																savedValue:
																	bugetCost[
																		key
																	]
																		?.inputedValue,
															},
													  }
													: bugetCost
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

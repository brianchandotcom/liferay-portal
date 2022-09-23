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

import {useModal} from '@clayui/modal';
import ClayPanel from '@clayui/panel';
import MDFRequestActivity from '../../../../common/interfaces/mdfRequestActivity';
import useGetActivityToBudgets from '../../../../common/services/liferay/object/activity/useGetActivityToBudgets';
import BudgetModal from './components/BudgetModal/BudgetModal';
import BudgetButton from './components/BudgetButton/BudgetButton';
import {useState, useEffect} from 'react';
import MDFRequestBudget from '../../../../common/interfaces/mdfRequestBudget';
import MDFClaim from '../../../../common/interfaces/mdfClaim';
import PRMFormik from '../../../../common/components/PRMFormik';
import PRMForm from '../../../../common/components/PRMForm';
import MDFRequest from '../../../../common/interfaces/mdfRequest';
import getBudgetCost from '../../utils/getBudgetCost';
import getSumBudgetsOfActivity from '../../utils/getSumBudgetsOfActivity';
import {ClayCheckbox} from '@clayui/form';

interface IProps {
	activity: MDFRequestActivity;
	currentActivityIndex: number | 0;
	mdfRequest?: MDFRequest | undefined;
	mdfClaim: MDFClaim;
	setFieldValue: (
		field: string,
		value: any,
		shouldValidate?: boolean | undefined
	) => void;
}

const ClaimPanel = ({
	activity,
	currentActivityIndex,
	mdfRequest,
	mdfClaim,
	setFieldValue,
}: IProps) => {
	const budgets = useGetActivityToBudgets(activity.id)?.data;

	const {observer, onOpenChange, open} = useModal();
	const [currentBudget, setCurrentBudget] = useState<MDFRequestBudget>();
	const [currentBudgetIndex, setCurrentBudgetIndex] = useState<number>();
	const [valueCheckBox, setValueCheckBox] = useState<boolean>(false);

	useEffect(() => {
		budgets?.items.forEach((budget, budgetIndex) => {
			setFieldValue(
				`mdfClaimActivities[${currentActivityIndex}].mdfClaimBudgets[${budgetIndex}].cost`,
				budget.cost
			);
		});
	}, [budgets]);

	return (
		<ClayPanel
			className="bg-brand-primary-lighten-6 border-brand-primary-lighten-5 text-neutral-7 "
			collapsable
			displayTitle={
				<ClayPanel.Title>
					<div className="d-flex">
						<div className="mr-3 mb-2 d-flex align-items-center">
							<ClayCheckbox
								checked={valueCheckBox}
								onChange={() => {
									setValueCheckBox((value) => !value);
									setFieldValue(
										`mdfClaimActivities[${currentActivityIndex}].checkedPanel`,
										!valueCheckBox
									);
								}}
							/>
						</div>

						<div className="w-100">
							<h6>{mdfRequest?.overallCampaign}</h6>

							<h4 className="text-neutral-10">
								{activity.name} ({activity.id})
							</h4>
							<div className="d-flex justify-content-end">
								<div>
									<h5 className="text-neutral-10">
										{getSumBudgetsOfActivity(
											mdfClaim,
											currentActivityIndex
										)}
									</h5>
								</div>
							</div>
						</div>
					</div>
				</ClayPanel.Title>
			}
			displayType="secondary"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				{budgets?.items.map((budget, index) => (
					<BudgetButton
						mdfClaim={mdfClaim}
						budget={budget}
						onClick={() => {
							if (budget) {
								setCurrentBudget(budget);
								setCurrentBudgetIndex(index);
								onOpenChange(true);
							}
						}}
						key={`${budget.id}-${index}`}
						cost={getBudgetCost(
							index,
							mdfClaim,
							currentActivityIndex
						)}
					/>
				))}
				<div className="mt-4">
					<PRMFormik.Field
						component={PRMForm.InputFile}
						label="List of Qualified Leads"
						name={`mdfClaimDocuments.activities[${currentActivityIndex}.listLeads]`}
						activityId={activity.id}
						setFieldValue={setFieldValue}
						typeDocument="listLeads"
						required
					/>
				</div>
				<div className="mt-4">
					<PRMFormik.Field
						component={PRMForm.InputFile}
						label="All Contents"
						name={`mdfClaimDocuments.activities[${currentActivityIndex}.contents]`}
						activityId={activity.id}
						setFieldValue={setFieldValue}
						typeDocument="contents"
						required
					/>
				</div>
				<div className="mt-4">
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Metrics"
						name={`mdfClaimActivities[${currentActivityIndex}].metrics`}
					/>
				</div>
			</ClayPanel.Body>
			<BudgetModal
				{...currentBudget}
				open={open}
				currentActivityIndex={currentActivityIndex}
				currentBudgetIndex={currentBudgetIndex}
				onOpenChange={onOpenChange}
				observer={observer}
				setFieldValue={setFieldValue}
				activityId={activity.id}
			/>
		</ClayPanel>
	);
};

export default ClaimPanel;

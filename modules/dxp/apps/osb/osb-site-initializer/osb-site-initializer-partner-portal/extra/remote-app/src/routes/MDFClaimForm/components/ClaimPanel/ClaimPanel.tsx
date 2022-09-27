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

import {ClayCheckbox} from '@clayui/form';
import {useModal} from '@clayui/modal';
import ClayPanel from '@clayui/panel';
import {useEffect, useState} from 'react';

import PRMForm from '../../../../common/components/PRMForm';
import PRMFormik from '../../../../common/components/PRMFormik';
import MDFClaim from '../../../../common/interfaces/mdfClaim';
import MDFRequest from '../../../../common/interfaces/mdfRequest';
import MDFRequestActivity from '../../../../common/interfaces/mdfRequestActivity';
import MDFRequestBudget from '../../../../common/interfaces/mdfRequestBudget';
import useGetActivityToBudgets from '../../../../common/services/liferay/object/activity/useGetActivityToBudgets';
import getBudgetCost from '../../utils/getBudgetCost';
import getSumBudgetsOfActivity from '../../utils/getSumBudgetsOfActivity';
import BudgetButton from './components/BudgetButton/BudgetButton';
import BudgetModal from './components/BudgetModal/BudgetModal';

interface IProps {
	activity: MDFRequestActivity;
	currentActivityIndex: number;
	mdfClaim: MDFClaim;
	mdfRequest?: MDFRequest;
	setFieldValue: (
		field: string,
		value: any,
		shouldValidate?: boolean
	) => void;
}

const ClaimPanel = ({
	activity,
	currentActivityIndex,
	mdfClaim,
	mdfRequest,
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
	}, [budgets, setFieldValue, currentActivityIndex]);

	return (
		<ClayPanel
			className="bg-brand-primary-lighten-6 border-brand-primary-lighten-5 text-neutral-7"
			collapsable
			displayTitle={
				<ClayPanel.Title>
					<div className="d-flex">
						<div className="align-items-center d-flex mb-2 mr-3">
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
			showCollapseIcon
		>
			<ClayPanel.Body>
				{budgets?.items.map((budget, index) => (
					<BudgetButton
						budget={budget}
						cost={getBudgetCost(
							index,
							mdfClaim,
							currentActivityIndex
						)}
						key={`${budget.id}-${index}`}
						mdfClaim={mdfClaim}
						onClick={() => {
							if (budget) {
								setCurrentBudget(budget);
								setCurrentBudgetIndex(index);
								onOpenChange(true);
							}
						}}
					/>
				))}

				<div className="mt-4">
					<PRMFormik.Field
						activityId={activity.id}
						component={PRMForm.InputFile}
						label="List of Qualified Leads"
						name={`mdfClaimDocuments.activities[${currentActivityIndex}.listLeads]`}
						required
						setFieldValue={setFieldValue}
						typeDocument="listLeads"
					/>
				</div>

				<div className="mt-4">
					<PRMFormik.Field
						activityId={activity.id}
						component={PRMForm.InputFile}
						label="All Contents"
						name={`mdfClaimDocuments.activities[${currentActivityIndex}.contents]`}
						required
						setFieldValue={setFieldValue}
						typeDocument="contents"
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
				activityId={activity.id}
				currentActivityIndex={currentActivityIndex}
				currentBudgetIndex={currentBudgetIndex}
				observer={observer}
				onOpenChange={onOpenChange}
				open={open}
				setFieldValue={setFieldValue}
			/>
		</ClayPanel>
	);
};

export default ClaimPanel;

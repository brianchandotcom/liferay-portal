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
import MDFClaimActivity from '../../../../common/interfaces/mdfClaimActivity';
import MDFClaimBudget from '../../../../common/interfaces/mdfClaimBudget';
import getIntlNumberFormat from '../../../../common/utils/getIntlNumberFormat';
import BudgetButton from './components/BudgetButton/BudgetButton';
import BudgetModal from './components/BudgetModal';

interface IProps {
	activity: MDFClaimActivity;
	activityIndex: number;
	overallCampaignDescription: string;
}

const ActivityClaimPanel = ({
	activity,
	activityIndex,
	overallCampaignDescription,
}: IProps) => {
	const [currentBudget, setCurrentBudget] = useState<MDFClaimBudget>();
	const {observer, onOpenChange, open} = useModal();

	useEffect(() => onOpenChange(!!currentBudget), [
		currentBudget,
		onOpenChange,
	]);

	return (
		<ClayPanel
			className="bg-brand-primary-lighten-6 border-brand-primary-lighten-5 text-neutral-7"
			collapsable
			displayTitle={
				<ClayPanel.Title>
					<div className="d-flex">
						<div className="align-items-center d-flex mb-2 mr-3">
							<PRMFormik.Field
								component={ClayCheckbox}
								name={`activities[${activityIndex}].finished`}
							/>
						</div>

						<div className="w-100">
							<h6>{overallCampaignDescription}</h6>

							<h4 className="text-neutral-10">
								{activity.name} ({activity.id})
							</h4>

							<div className="d-flex justify-content-end">
								<h5 className="text-neutral-10">
									{getIntlNumberFormat().format(
										activity.totalCost
									)}
								</h5>
							</div>
						</div>
					</div>
				</ClayPanel.Title>
			}
			displayType="secondary"
			showCollapseIcon
		>
			<ClayPanel.Body>
				{activity.budgets?.map((budget, index) => (
					<BudgetButton
						budget={budget}
						key={`${budget.id}-${index}`}
						onClick={() => setCurrentBudget({...budget})}
					/>
				))}

				<div className="mt-4">
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Metrics"
						name={`mdfClaimActivities[${activityIndex}].metrics`}
					/>
				</div>
			</ClayPanel.Body>

			{open && (
				<BudgetModal
					{...currentBudget}
					observer={observer}
					onCancel={() => onOpenChange(false)}
					onConfirm={(value) => {
						// eslint-disable-next-line no-console
						console.log(value);

						onOpenChange(false);
					}}
				/>
			)}
		</ClayPanel>
	);
};

export default ActivityClaimPanel;

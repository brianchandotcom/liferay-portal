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

import Button from '@clayui/button';
import {useFormikContext} from 'formik';
import {useMemo} from 'react';

import PRMFormikPageProps from '../../../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import MDFRequest from '../../../../common/interfaces/mdfRequest';
import MDFRequestActivity from '../../../../common/interfaces/mdfRequestActivity';
import ActivityPanel from '../../components/ActivityPanel';
import {StepType} from '../../enums/stepType';
import MDFRequestStepProps from '../../interfaces/mdfRequestStepProps';
import BudgetResumeCard from '../Activities/Form/components/BudgetBreakdownSection/components/BudgetResumeCard';
import ActivitiesEntries from './ActivitiesEntries/ActivitiesEntries';
import GoalsEntries from './GoalsEntries/';
import ReviewBody from './ReviewBody/ReviewBody';
import ReviewHeader from './ReviewHeader/ReviewHeader';

const Review = ({
	onCancel,
	onContinue,
	onPrevious,
	onSaveAsDraft,
}: PRMFormikPageProps & MDFRequestStepProps<MDFRequest>) => {
	const {isSubmitting, isValid, values, ...formikHelpers} = useFormikContext<
		MDFRequest
	>();

	const totalBudget = useMemo(
		() =>
			values.activities.reduce((previousValue, currentValue) => {
				const sumBudgets = currentValue.budgets.reduce<number>(
					(previousValue, currentValue) =>
						previousValue + +currentValue.cost,
					0
				);

				return previousValue + sumBudgets;
			}, 0),
		[values.activities]
	);

	return (
		<div>
			<ReviewHeader
				description="Please ensure that all the information below is accurate before submitting your request."
				name="Review"
				title="Review Campaign MDF Request"
			/>

			<ReviewBody name="Goals" title="Campaign Information">
				<GoalsEntries values={values} />
			</ReviewBody>

			<ReviewBody name="Activities" title="Insurance Industry Lead Gen">
				{values?.activities.map(
					(value: MDFRequestActivity, index: number) => (
						<ActivityPanel
							activity={value}
							detail={true}
							key={index}
							overallCampaign={values.overallCampaign}
						>
							<ActivitiesEntries key={index} values={value} />
						</ActivityPanel>
					)
				)}
			</ReviewBody>

			<ReviewBody>
				<div>
					<div className="my-3">
						<BudgetResumeCard
							leftContent="Total Budget"
							rightContent={String(totalBudget)}
						/>

						<BudgetResumeCard
							className="mt-3"
							leftContent="Claim Percent"
							rightContent="0.5"
						/>

						<BudgetResumeCard
							className="mt-3"
							leftContent="Total MDF Requested Amount"
							rightContent={String(totalBudget * 0.5)}
						/>
					</div>

					<div className="sheet-subtitle"></div>

					<div className="d-flex justify-content-between">
						<div className="mr-auto pl-0 py-3">
							<Button
								className="mr-4"
								displayType="secondary"
								onClick={() =>
									onPrevious?.(StepType.ACTIVITIES)
								}
							>
								Previous
							</Button>

							<Button
								className="pl-0"
								disabled={isSubmitting}
								displayType={null}
								onClick={() =>
									onSaveAsDraft?.(values, formikHelpers)
								}
							>
								Save as Draft
							</Button>
						</div>

						<div className="p-2">
							<Button
								className="mr-4"
								displayType="secondary"
								onClick={onCancel}
							>
								Cancel
							</Button>

							<Button
								disabled={!isValid}
								onClick={() =>
									onContinue?.(formikHelpers, StepType.REVIEW)
								}
							>
								Continue
							</Button>
						</div>
					</div>
				</div>
			</ReviewBody>
		</div>
	);
};

export default Review;

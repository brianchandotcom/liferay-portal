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

import PRMFormikPageProps from '../../../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import MDFRequest from '../../../../common/interfaces/mdfRequest';
import MDFRequestActivity from '../../../../common/interfaces/mdfRequestActivity';
import ActivityPanel from '../../components/ActivityPanel';
import {StepType} from '../../enums/stepType';
import MDFRequestStepProps from '../../interfaces/mdfRequestStepProps';
import BudgetResumeCard from '../Activities/Form/components/BudgetBreakdownSection/components/BudgetResumeCard';
import Body from './components/Body';
import ActivityReviewEntry from './components/Body/components/ActivityReviewEntry';
import GoalsEntries from './components/Body/components/GoalsEntries';
import Header from './components/Header';
import GetTotalBudget from './utils/GetTotalBudget';

const Review = ({
	onCancel,
	onPrevious,
	onSaveAsDraft,
}: PRMFormikPageProps & MDFRequestStepProps<MDFRequest>) => {
	const {isSubmitting, values, ...formikHelpers} = useFormikContext<
		MDFRequest
	>();

	const totalBudget = GetTotalBudget(values);

	return (
		<div className="d-flex flex-column">
			<Header />

			<Body name="Goals" title="Campaign Information">
				<GoalsEntries values={values} />
			</Body>

			<Body name="Activities" title="Insurance Industry Lead Gen">
				<div className="border mb-3"></div>

				{values?.activities.map(
					(value: MDFRequestActivity, index: number) => (
						<ActivityPanel
							activity={value}
							detail
							key={index}
							overallCampaign={values.overallCampaign}
						>
							<ActivityReviewEntry values={value} />
						</ActivityPanel>
					)
				)}
			</Body>

			<Body>
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

					<div className="border mb-1"></div>

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

							<Button disabled={isSubmitting} type="submit">
								Submit
							</Button>
						</div>
					</div>
				</div>
			</Body>
		</div>
	);
};

export default Review;

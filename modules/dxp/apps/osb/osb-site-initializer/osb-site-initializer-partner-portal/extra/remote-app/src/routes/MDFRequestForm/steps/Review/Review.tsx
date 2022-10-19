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
import ResumeCard from '../../../../common/components/ResumeCard';
import MDFRequest from '../../../../common/interfaces/mdfRequest';
import MDFRequestActivity from '../../../../common/interfaces/mdfRequestActivity';
import getIntlNumberFormat from '../../../../common/utils/getIntlNumberFormat';
import getTotalBudget from '../../../../common/utils/getTotalBudget';
import getTotalMDFRequest from '../../../../common/utils/getTotalMDFRequest';
import ActivityPanel from '../../components/ActivityPanel';
import {StepType} from '../../enums/stepType';
import MDFRequestStepProps from '../../interfaces/mdfRequestStepProps';
import Body from './components/Body';
import ActivityReviewEntry from './components/Body/components/ActivityReviewEntry';
import GoalsEntries from './components/Body/components/GoalsEntries';
import Header from './components/Header';

const Review = ({
	onCancel,
	onPrevious,
	onSaveAsDraft,
}: PRMFormikPageProps & MDFRequestStepProps) => {
	const {isSubmitting, values, ...formikHelpers} = useFormikContext<
		MDFRequest
	>();

	const totalBudget = useMemo(() => getTotalBudget(values.activities), [
		values.activities,
	]);

	const totalMDFRequest = useMemo(
		() => getTotalMDFRequest(values.activities),
		[values.activities]
	);

	return (
		<div className="d-flex flex-column">
			<Header />

			<Body name="Goals" title="Campaign Information">
				<GoalsEntries mdfRequest={values} />
			</Body>

			<Body name="Activities" title="Insurance Industry Lead Gen">
				<div className="border mb-3"></div>

				{values?.activities.map(
					(activity: MDFRequestActivity, index: number) => (
						<ActivityPanel
							activity={activity}
							detail
							key={index}
							overallCampaignName={values.overallCampaignName}
						>
							<ActivityReviewEntry
								mdfRequestActivity={activity}
							/>
						</ActivityPanel>
					)
				)}
			</Body>

			<Body>
				<div>
					<div className="my-3">
						<ResumeCard
							leftContent="Total Budget"
							rightContent={getIntlNumberFormat().format(
								totalBudget
							)}
						/>

						<ResumeCard
							className="mt-3"
							leftContent="Claim Percent"
							rightContent={`${0.5 * 100}%`}
						/>

						<ResumeCard
							className="mt-3"
							leftContent="Total MDF Requested Amount"
							rightContent={getIntlNumberFormat().format(
								totalMDFRequest
							)}
						/>
					</div>

					<div className="border mb-1"></div>

					<div className="d-flex justify-content-between">
						<div className="mr-auto pl-0 py-3">
							<Button
								className="mr-4"
								displayType={null}
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

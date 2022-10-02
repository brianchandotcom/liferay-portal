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
import {useFormikContext} from 'formik';

import PRMForm from '../../../../common/components/PRMForm';
import PRMFormik from '../../../../common/components/PRMFormik';
import PRMFormikPageProps from '../../../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import ResumeCard from '../../../../common/components/ResumeCard';
import MDFRequestDTO from '../../../../common/interfaces/dto/mdfRequestDTO';
import MDFClaim from '../../../../common/interfaces/mdfClaim';
import MDFClaimProps from '../../../../common/interfaces/mdfClaimProps';
import getIntlNumberFormat from '../../../../common/utils/getIntlNumberFormat';
import ActivityClaimPanel from '../ActivityClaimPanel';

interface IProps {
	mdfRequest: MDFRequestDTO;
}

const MDFClaimPage = ({
	mdfRequest,
	onCancel,
	onSaveAsDraft,
}: PRMFormikPageProps & MDFClaimProps & IProps) => {
	const {
		isSubmitting,
		isValid,
		setFieldValue,
		values,
		...formikHelpers
	} = useFormikContext<MDFClaim>();

	return (
		<PRMForm name="New" title="Reimbursement Claim">
			<PRMForm.Section
				subtitle="Check each expense you would like claim and please provide proof of performance for each of the selected expenses."
				title={`${mdfRequest?.overallCampaignDescription} (${mdfRequest?.id})`}
			>
				<h5 className="my-4">Upload Proof of Performance Documents</h5>

				{values.activities?.map((activity, index) => (
					<ActivityClaimPanel
						activity={activity}
						activityIndex={index}
						key={`${activity.id}-${index}`}
						overallCampaignDescription={
							mdfRequest.overallCampaignDescription
						}
					/>
				))}
			</PRMForm.Section>

			<PRMForm.Section
				subtitle="Total Claim is the reimbursement of your expenses, and is up to the Total MDF Requested. In case need to claim more than the MDF Requested you need to apply for a New MDF Request."
				title="Total Claim"
			>
				<div className="my-3">
					<ResumeCard
						leftContent="Total MDF Requested Amount"
						rightContent={getIntlNumberFormat().format(
							mdfRequest?.totalMDFRequestAmount || 0
						)}
					/>
				</div>

				<PRMFormik.Field
					component={PRMForm.InputCurrency}
					label="Total Claim Amount"
					name="totalClaimAmount"
					onAccept={(value: number) =>
						setFieldValue('totalClaimAmount', value)
					}
					required
				/>
			</PRMForm.Section>

			<PRMForm.Footer>
				<div className="d-flex mr-auto">
					<ClayButton
						className="pl-0"
						disabled={isSubmitting}
						displayType={null}
						onClick={() => onSaveAsDraft(values, formikHelpers)}
					>
						Save as Draft
					</ClayButton>
				</div>

				<div>
					<ClayButton
						className="mr-4"
						displayType="secondary"
						onClick={() => onCancel()}
					>
						Cancel
					</ClayButton>

					<ClayButton
						disabled={!isValid || isSubmitting}
						type="submit"
					>
						Submit
					</ClayButton>
				</div>
			</PRMForm.Footer>
		</PRMForm>
	);
};

export default MDFClaimPage;

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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useFormikContext} from 'formik';
import {useCallback} from 'react';

import PRMForm from '../../../../common/components/PRMForm';
import PRMFormik from '../../../../common/components/PRMFormik';
import PRMFormikPageProps from '../../../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import ResumeCard from '../../../../common/components/ResumeCard';
import MDFRequestDTO from '../../../../common/interfaces/dto/mdfRequestDTO';
import MDFClaim from '../../../../common/interfaces/mdfClaim';
import MDFClaimProps from '../../../../common/interfaces/mdfClaimProps';
import {Status} from '../../../../common/utils/constants/status';
import getIntlNumberFormat from '../../../../common/utils/getIntlNumberFormat';
import useDynamicFieldEntries from '../../../MDFClaimList/hooks/useDynamicFieldEntries';
import ActivityClaimPanel from './components/ActivityClaimPanel';
import useActivitiesAmount from './hooks/useActivitiesAmount';

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
		status: submitted,
		values,
		...formikHelpers
	} = useFormikContext<MDFClaim>();

	useActivitiesAmount(
		values.activities,
		useCallback(
			(amountValue) =>
				setFieldValue('totalClaimAmount', amountValue * 0.5),
			[setFieldValue]
		)
	);

	const {companiesEntries, fieldEntries} = useDynamicFieldEntries();

	const claimsFiltered = mdfRequest.mdfReqToMDFClms?.filter(
		(mdfRequestToMdfClaim) => {
			const ignoreStatus = [
				Status.DRAFT.key,
				Status.EXPIRED.key,
				Status.REJECT.key,
			];

			return !ignoreStatus.includes(
				mdfRequestToMdfClaim.mdfClaimStatus.key as string
			);
		}
	).length;

	const getClaimPage = () => {
		if (!fieldEntries || !companiesEntries) {
			return <ClayLoadingIndicator />;
		}

		if (
			values.id &&
			values.mdfClaimStatus?.key !== 'draft' &&
			values.mdfClaimStatus?.key !== 'moreInfoRequested'
		) {
			return (
				<PRMForm name="" title="MDF Claim">
					<div className="d-flex justify-content-center mt-4">
						<ClayAlert
							className="m-0 w-100"
							displayType="info"
							title="Info:"
						>
							This MDF Claim can not be edited.
						</ClayAlert>
					</div>

					<PRMForm.Footer>
						<div className="d-flex mr-auto">
							<ClayButton
								className="mr-4"
								displayType="secondary"
								onClick={() => onCancel()}
							>
								Cancel
							</ClayButton>
						</div>
					</PRMForm.Footer>
				</PRMForm>
			);
		}

		if (claimsFiltered && claimsFiltered >= 2 && !values.id) {
			return (
				<PRMForm name="New" title="Reimbursement Claim">
					<div className="d-flex justify-content-center mt-4">
						<ClayAlert
							className="m-0 w-100"
							displayType="info"
							title="Info:"
						>
							You already submitted 2 claims.
						</ClayAlert>
					</div>

					<PRMForm.Footer>
						<div className="d-flex mr-auto">
							<ClayButton
								className="mr-4"
								displayType="secondary"
								onClick={() => onCancel()}
							>
								Cancel
							</ClayButton>
						</div>
					</PRMForm.Footer>
				</PRMForm>
			);
		}

		if (mdfRequest.mdfRequestStatus?.key !== 'approved') {
			return (
				<PRMForm name="New" title="Reimbursement Claim">
					<div className="d-flex justify-content-center mt-4">
						<ClayAlert
							className="m-0 w-100"
							displayType="info"
							title="Info:"
						>
							Waiting for Manager approval
						</ClayAlert>
					</div>

					<PRMForm.Footer>
						<div className="d-flex mr-auto">
							<ClayButton
								className="mr-4"
								displayType="secondary"
								onClick={() => onCancel()}
							>
								Cancel
							</ClayButton>
						</div>
					</PRMForm.Footer>
				</PRMForm>
			);
		}

		return (
			<PRMForm name="New" title="Reimbursement Claim">
				<PRMForm.Section
					subtitle="Check each expense you would like claim and please provide proof of performance for each of the selected expenses."
					title={`${mdfRequest?.overallCampaignDescription} (${mdfRequest?.id})`}
				>
					<p className="font-weight-bold my-4 text-paragraph">
						Upload Proof of Performance Documents
						<span className="text-danger">*</span>
					</p>

					{values.activities?.map((activity, index) => (
						<ActivityClaimPanel
							activity={activity}
							activityIndex={index}
							key={`${activity.id}-${index}`}
							overallCampaignDescription={
								mdfRequest.overallCampaignDescription
							}
							setFieldValue={setFieldValue}
						/>
					))}
				</PRMForm.Section>

				<PRMForm.Section
					subtitle="Total Claim is the reimbursement of your expenses, and is up to the Total MDF Requested. In case need to claim more than the MDF Requested you need to apply for a New MDF Request."
					title="Total Claim"
				>
					<PRMFormik.Field
						component={PRMForm.InputFile}
						description="Upload an invoice for the Total Claim Amount"
						displayType="secondary"
						label="Reimbursement Invoice"
						name="reimbursementInvoice"
						onAccept={(value: File) =>
							setFieldValue('reimbursementInvoice', value)
						}
						outline
						small
					/>

					<ResumeCard
						className="mb-4"
						leftContent="Total MDF Requested Amount"
						rightContent={getIntlNumberFormat(
							values.currency
						).format(values.totalMDFRequestedAmount || 0)}
					/>

					<PRMFormik.Field
						component={PRMForm.InputCurrency}
						description="The amount to be claimed for the Total of  selected expenses"
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
							className="inline-item inline-item-after pl-0"
							disabled={isSubmitting || submitted}
							displayType={null}
							onClick={() => onSaveAsDraft(values, formikHelpers)}
						>
							Save as Draft
							{isSubmitting && (
								<ClayLoadingIndicator className="inline-item inline-item-after ml-2" />
							)}
						</ClayButton>
					</div>

					<div>
						<ClayButton
							className="mr-4"
							disabled={isSubmitting || submitted}
							displayType="secondary"
							onClick={() => onCancel()}
						>
							Cancel
						</ClayButton>

						<ClayButton
							className="inline-item inline-item-after"
							disabled={!isValid || isSubmitting || submitted}
							type="submit"
						>
							Submit
							{isSubmitting && (
								<ClayLoadingIndicator className="inline-item inline-item-after ml-2" />
							)}
						</ClayButton>
					</div>
				</PRMForm.Footer>
			</PRMForm>
		);
	};

	return getClaimPage();
};

export default MDFClaimPage;

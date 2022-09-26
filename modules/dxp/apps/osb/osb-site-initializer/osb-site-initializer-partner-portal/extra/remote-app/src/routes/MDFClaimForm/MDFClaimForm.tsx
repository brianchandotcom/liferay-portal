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
import {useEffect, useMemo} from 'react';

import PRMForm from '../../common/components/PRMForm';
import PRMFormik from '../../common/components/PRMFormik';
import PRMFormikPageProps from '../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import MDFClaim from '../../common/interfaces/mdfClaim';
import mdfClaimProps from '../../common/interfaces/mdfClaimProps';
import useGetMDFRequest from '../../common/services/liferay/object/mdf-requests/useGetMDFRequest';
import useGetMDFRequestToActivities from '../../common/services/liferay/object/mdf-requests/useGetMDFRequestToActivities';
import getIntlNumberFormat from '../../common/utils/getIntlNumberFormat';
import isObjectEmpty from '../MDFRequestForm/utils/isObjectEmpty';
import ClaimPanel from './components/ClaimPanel';
import ClaimTotalResumeCard from './components/ClaimTotalResumeCard';
import getTotalBudgetByClaim from './utils/getTotalBudgetByClaim';

const MDFClaimForm = ({
	onSaveAsDraft,
}: PRMFormikPageProps & mdfClaimProps<MDFClaim>) => {
	const {
		errors,
		isSubmitting,
		isValid,
		setFieldValue,
		values,
		...formikHelpers
	} = useFormikContext<MDFClaim>();

	const mdfRequest = useGetMDFRequest(45314)?.data;

	const activities = useGetMDFRequestToActivities(45314)?.data;

	useEffect(() => {
		if (values.mdfClaimActivities) {
			setFieldValue(
				'totalClaimAmount',
				getTotalBudgetByClaim(values.mdfClaimActivities) * 0.5
			);
		}
	}, [values.mdfClaimActivities, setFieldValue]);

	const claimErrors = useMemo(() => {
		return errors;
	}, [errors]);

	return (
		<PRMForm className="mb-4" name="NEW" title="Reimbursement Claim">
			<PRMForm.Section
				subtitle="Check each expense you would like claim and please provide proof of performance for each of the selected expenses."
				title={mdfRequest?.overallCampaign}
			>
				<h5 className="my-4">Upload Proof of Performance Documents </h5>

				{activities &&
					activities.items.map((activity, index) => (
						<ClaimPanel
							activity={activity}
							currentActivityIndex={index}
							key={`${activity.id}-${index}`}
							mdfClaim={values}
							mdfRequest={mdfRequest}
							setFieldValue={setFieldValue}
						/>
					))}
			</PRMForm.Section>

			<PRMForm.Section
				subtitle="Total Claim is the reimbursement of your expenses, and is up to the Total MDF Requested. In case need to claim more than the MDF Requested you need to apply for a  New MDF Request."
				title="Total Claim"
			>
				<div className="my-3">
					<ClaimTotalResumeCard
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

				<PRMFormik.Field
					component={PRMForm.InputFile}
					label="Reimbursement Invoice"
					mdfRequestId={mdfRequest?.id}
					name="mdfClaimDocuments.claims[0.reimbursementInvoice]"
					required
					setFieldValue={setFieldValue}
					typeDocument="reimbursementInvoice"
				/>
			</PRMForm.Section>

			<PRMForm.Footer>
				<div className="d-flex mr-auto">
					<ClayButton
						className="pl-0"
						disabled={isSubmitting}
						displayType={null}
						onClick={() => onSaveAsDraft?.(values, formikHelpers)}
					>
						Save as Draft
					</ClayButton>
				</div>

				<div>
					<ClayButton className="mr-4" displayType="secondary">
						Cancel
					</ClayButton>

					<ClayButton
						disabled={
							(!isValid && !isObjectEmpty(claimErrors)) ||
							isSubmitting
						}
						type="submit"
					>
						Submit
					</ClayButton>
				</div>
			</PRMForm.Footer>
		</PRMForm>
	);
};

export default MDFClaimForm;

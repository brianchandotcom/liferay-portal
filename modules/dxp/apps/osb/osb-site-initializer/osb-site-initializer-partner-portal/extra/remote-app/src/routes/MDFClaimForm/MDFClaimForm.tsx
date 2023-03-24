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

import ClayLoadingIndicator from '@clayui/loading-indicator';

import PRMFormik from '../../common/components/PRMFormik';
import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import MDFClaimDTO from '../../common/interfaces/dto/mdfClaimDTO';
import MDFRequestActivityDTO from '../../common/interfaces/dto/mdfRequestActivityDTO';
import LiferayPicklist from '../../common/interfaces/liferayPicklist';
import MDFClaim from '../../common/interfaces/mdfClaim';
import {Liferay} from '../../common/services/liferay';
import useGetDocumentFolder from '../../common/services/liferay/headless-delivery/useGetDocumentFolders';
import useGetMDFClaimById from '../../common/services/liferay/object/mdf-claim/useGetMDFClaimById';
import useGetMDFRequestById from '../../common/services/liferay/object/mdf-requests/useGetMDFRequestById';
import {Status} from '../../common/utils/constants/status';
import {getMDFClaimFromDTO} from '../../common/utils/dto/mdf-claim/getMDFClaimFromDTO';
import MDFClaimPage from './components/MDFClaimPage';
import claimSchema from './components/MDFClaimPage/schema/yup';
import useGetMDFRequestIdByHash from './hooks/useGetMDFRequestIdByHash';
import submitForm from './utils/submitForm';

const getInitialFormValues = (
	mdfRequestId: number,
	currency: LiferayPicklist,
	activitiesDTO?: MDFRequestActivityDTO[],
	totalMDFRequestAmount?: number,
	mdfClaim?: MDFClaim
): MDFClaim => ({
	...mdfClaim,
	activities: activitiesDTO?.map((activity) => {
		const mdfClaimActivity = mdfClaim?.activities?.find(
			(claimActivity) =>
				claimActivity.r_actToMDFClmActs_c_activityId === activity.id
		);

		if (mdfClaimActivity) {
			return {
				...mdfClaimActivity,
				activityStatus: activity.activityStatus,
				budgets: activity?.actToBgts?.map((budget) => {
					const mdfClaimBudget = mdfClaimActivity.budgets?.find(
						(claimBudget) =>
							claimBudget.r_bgtToMDFClmBgts_c_budgetId ===
							budget.id
					);

					if (mdfClaimBudget) {
						return {
							...mdfClaimBudget,
							r_bgtToMDFClmBgts_c_budgetId: budget.id,
							requestAmount: budget.cost,
						};
					}

					return {
						expenseName: budget.expense.name,
						invoiceAmount: budget.cost,
						r_bgtToMDFClmBgts_c_budgetId: budget.id,
						requestAmount: budget.cost,
						selected: false,
					};
				}),
				claimed: activity.actToMDFClmActs
					?.map((mdfClaimActivity) => {
						return (
							mdfClaimActivity?.r_mdfClmToMDFClmActs_c_mdfClaim
								?.mdfClaimStatus.key !== 'draft'
						);
					})
					.includes(true),
				name: activity.name,
				r_actToMDFClmActs_c_activityId: activity.id,
			};
		}

		return {
			activityStatus: activity.activityStatus,
			budgets: activity?.actToBgts?.map((budget) => {
				return {
					expenseName: budget.expense.name,
					invoiceAmount: budget.cost,
					r_bgtToMDFClmBgts_c_budgetId: budget.id,
					requestAmount: budget.cost,
					selected: false,
				};
			}),
			claimed: activity.actToMDFClmActs
				?.map((mdfClaimActivity) => {
					return (
						mdfClaimActivity?.r_mdfClmToMDFClmActs_c_mdfClaim
							?.mdfClaimStatus.key !== 'draft'
					);
				})
				.includes(true),
			currency: activity.currency,
			metrics: '',
			name: activity.name,
			r_actToMDFClmActs_c_activityId: activity.id,
			selected: false,
			totalCost: 0,
		};
	}),
	currency: mdfClaim?.currency ? mdfClaim?.currency : currency,
	mdfClaimStatus: mdfClaim?.mdfClaimStatus
		? mdfClaim.mdfClaimStatus
		: Status.PENDING,
	r_mdfReqToMDFClms_c_mdfRequestId: mdfRequestId,
	totalMDFRequestedAmount: mdfClaim?.totalMDFRequestedAmount
		? mdfClaim.totalMDFRequestedAmount
		: totalMDFRequestAmount,
});

const SECOND_POSITION_AFTER_HASH = 1;
const FOURTH_POSITION_AFTER_HASH = 3;

const MDFClaimForm = () => {
	const {
		data: claimParentFolder,
		isValidating: isValidatingClaimFolder,
	} = useGetDocumentFolder(Liferay.ThemeDisplay.getScopeGroupId(), 'claim');

	const claimParentFolderId = claimParentFolder?.items[0].id;

	const mdfRequestId = useGetMDFRequestIdByHash(SECOND_POSITION_AFTER_HASH);
	const mdfClaimId = useGetMDFRequestIdByHash(FOURTH_POSITION_AFTER_HASH);

	const {
		data: mdfRequest,
		isValidating: isValidatingMDFRequestById,
	} = useGetMDFRequestById(Number(mdfRequestId));

	const {
		data: mdfClaimDTO,
		isValidating: isValidatingMDFClaimById,
	} = useGetMDFClaimById(Number(mdfClaimId));

	const siteURL = useLiferayNavigate();

	const onCancel = () =>
		Liferay.Util.navigate(`${siteURL}/${PRMPageRoute.MDF_CLAIM_LISTING}`);

	if (
		!mdfRequest ||
		(mdfClaimId && !mdfClaimDTO) ||
		isValidatingMDFRequestById ||
		(mdfClaimId && isValidatingMDFClaimById) ||
		isValidatingClaimFolder ||
		!claimParentFolderId
	) {
		return <ClayLoadingIndicator />;
	}

	const mdfClaim = getMDFClaimFromDTO(mdfClaimDTO as MDFClaimDTO);
	// eslint-disable-next-line no-console
	console.log(
		'🚀 ~ file: MDFClaimForm.tsx:131 ~ MDFClaimForm ~ mdfClaim:',
		mdfClaim
	);

	return (
		<PRMFormik
			initialValues={getInitialFormValues(
				Number(mdfRequestId),
				mdfRequest.currency,
				mdfRequest.mdfReqToActs,
				mdfRequest.totalMDFRequestAmount,
				mdfClaim
			)}
			onSubmit={(values, formikHelpers) =>
				submitForm(
					values,
					formikHelpers,
					mdfRequest,
					claimParentFolderId,
					siteURL
				)
			}
		>
			<MDFClaimPage
				mdfRequest={mdfRequest}
				onCancel={onCancel}
				onSaveAsDraft={(values, formikHelpers) =>
					submitForm(
						values,
						formikHelpers,
						mdfRequest,
						claimParentFolderId,
						siteURL,
						Status.DRAFT
					)
				}
				validationSchema={claimSchema}
			/>
		</PRMFormik>
	);
};

export default MDFClaimForm;

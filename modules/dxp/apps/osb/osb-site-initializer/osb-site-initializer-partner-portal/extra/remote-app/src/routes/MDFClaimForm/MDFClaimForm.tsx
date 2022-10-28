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
import MDFRequestActivityDTO from '../../common/interfaces/dto/mdfRequestActivityDTO';
import MDFClaim from '../../common/interfaces/mdfClaim';
import {Liferay} from '../../common/services/liferay';
import useGetDocumentFolder from '../../common/services/liferay/headless-delivery/useGetDocumentFolders';
import useGetMDFRequestById from '../../common/services/liferay/object/mdf-requests/useGetMDFRequestById';
import MDFClaimPage from './components/MDFClaimPage';
import claimSchema from './components/MDFClaimPage/schema/yup';
import useGetMDFRequestIdByHash from './hooks/useGetMDFRequestIdByHash';
import submitForm from './utils/submitForm';

const getInitialFormValues = (
	mdfRequestId: number,
	activitiesDTO?: MDFRequestActivityDTO[],
	totalrequestedAmount?: number
): MDFClaim => ({
	activities: activitiesDTO?.map((activity) => ({
		budgets: activity.activityToBudgets?.map((budget) => ({
			claimAmount: budget.cost,
			expenseName: budget.expense.name,
			id: budget.id,
		})),
		id: activity.id,
		metrics: '',
		name: activity.name,
		selected: false,
		totalCost: 0,
	})),
	r_mdfRequestToMdfClaims_c_mdfRequestId: mdfRequestId,
	totalClaimAmount: 0,
	totalrequestedAmount,
});

const MDFClaimForm = () => {
	const {
		data: claimParentFolder,
		isValidating: isValidatingClaimFolder,
	} = useGetDocumentFolder(
		Liferay.ThemeDisplay.getScopeGroupId(),
		`?filter=name eq 'claim'`
	);

	const claimParentFolderId = claimParentFolder?.items[0].id;

	const mdfRequestId = useGetMDFRequestIdByHash();

	const {
		data: mdfRequest,
		isValidating: isValidatingMDFRequestById,
	} = useGetMDFRequestById(Number(mdfRequestId));

	const siteURL = useLiferayNavigate();

	const onCancel = () =>
		Liferay.Util.navigate(`${siteURL}/${PRMPageRoute.MDF_CLAIM_LISTING}`);

	if (
		!mdfRequest ||
		isValidatingMDFRequestById ||
		isValidatingClaimFolder ||
		!claimParentFolderId
	) {
		return <ClayLoadingIndicator />;
	}

	return (
		<PRMFormik
			initialValues={getInitialFormValues(
				Number(mdfRequestId),
				mdfRequest.mdfRequestToActivities,
				mdfRequest.totalMDFRequestAmount
			)}
			onSubmit={(values, formikHelpers) =>
				submitForm(values, formikHelpers, claimParentFolderId)
			}
		>
			<MDFClaimPage
				mdfRequest={mdfRequest}
				onCancel={onCancel}
				onSaveAsDraft={(values, formikHelpers) =>
					submitForm(values, formikHelpers, claimParentFolderId)
				}
				validationSchema={claimSchema}
			/>
		</PRMFormik>
	);
};

export default MDFClaimForm;

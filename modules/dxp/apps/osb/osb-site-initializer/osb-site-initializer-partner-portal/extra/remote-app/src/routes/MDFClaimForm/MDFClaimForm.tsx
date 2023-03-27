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
import {Liferay} from '../../common/services/liferay';
import useGetDocumentFolder from '../../common/services/liferay/headless-delivery/useGetDocumentFolders';
import useGetMDFClaimById from '../../common/services/liferay/object/mdf-claim/useGetMDFClaimById';
import useGetMDFRequestById from '../../common/services/liferay/object/mdf-requests/useGetMDFRequestById';
import {Status} from '../../common/utils/constants/status';
import {getMDFClaimFromDTO} from '../../common/utils/dto/mdf-claim/getMDFClaimFromDTO';
import MDFClaimPage from './components/MDFClaimPage';
import claimSchema from './components/MDFClaimPage/schema/yup';
import useGetMDFRequestIdByHash from './hooks/useGetMDFRequestIdByHash';
import getInitialFormValues from './utils/getInitialFormValues';
import submitForm from './utils/submitForm';

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

	const mdfClaim =
		mdfClaimDTO && getMDFClaimFromDTO(mdfClaimDTO as MDFClaimDTO);

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

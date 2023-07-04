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

import {useFormikContext} from 'formik';

import PRMForm from '../../../../../../../../common/components/PRMForm';
import InputMultipleFilesListing from '../../../../../../../../common/components/PRMForm/components/fields/InputMultipleFilesListing/InputMultipleFilesListing';
import PRMFormik from '../../../../../../../../common/components/PRMFormik';
import LiferayFile from '../../../../../../../../common/interfaces/liferayFile';
import MDFClaimActivity from '../../../../../../../../common/interfaces/mdfClaimActivity';
import MDFRequest from '../../../../../../../../common/interfaces/mdfRequest';
import {getFileFromLiferayDocument} from '../../../../../../../../common/utils/dto/mdf-claim/getFileFromLiferayDocument';
import uploadDocument from '../../../../../../utils/uploadDocument';

interface IProps {
	activity: MDFClaimActivity;
	claimParentFolderId: number;
	currentActivityIndex: number;
}

const ContentMarketingPopFields = ({
	activity,
	claimParentFolderId,
	currentActivityIndex,
}: IProps) => {
	const {setFieldValue} = useFormikContext<MDFRequest>();

	return (
		<>
			<PRMFormik.Field
				component={PRMForm.InputText}
				label="Video Link"
				name={`activities[${currentActivityIndex}].videoLink`}
				required
			/>

			<InputMultipleFilesListing
				acceptedFilesExtensions="doc, docx, jpg, jpeg, png, tif, tiff, pdf"
				description="Drag and drop your files here to upload."
				label="All Contents"
				name={`activities[${currentActivityIndex}].proofOfPerformance.allContents`}
				onAccept={async (value: LiferayFile[]) => {
					const uploadedLiferayDocuments = await Promise.all(
						value.map(async (liferayFile) => {
							const uploadedliferayFile = await uploadDocument(
								liferayFile,
								claimParentFolderId
							);

							if (uploadedliferayFile) {
								return getFileFromLiferayDocument(
									uploadedliferayFile
								);
							}
						})
					);

					setFieldValue(
						`activities[${currentActivityIndex}].proofOfPerformance.allContents`,
						activity.proofOfPerformance?.allContents
							? activity.proofOfPerformance.allContents.concat(
									uploadedLiferayDocuments as LiferayFile[]
							  )
							: uploadedLiferayDocuments
					);
				}}
				required
				value={activity.proofOfPerformance?.allContents}
			/>
		</>
	);
};

export default ContentMarketingPopFields;

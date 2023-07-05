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

import {FormikContextType} from 'formik';

import PRMForm from '../../../../../../../../common/components/PRMForm';
import InputMultipleFilesListing from '../../../../../../../../common/components/PRMForm/components/fields/InputMultipleFilesListing/InputMultipleFilesListing';
import PRMFormik from '../../../../../../../../common/components/PRMFormik';
import LiferayFile from '../../../../../../../../common/interfaces/liferayFile';
import MDFClaim from '../../../../../../../../common/interfaces/mdfClaim';
import MDFClaimActivity from '../../../../../../../../common/interfaces/mdfClaimActivity';
import {getFileFromLiferayDocument} from '../../../../../../../../common/utils/dto/mdf-claim/getFileFromLiferayDocument';
import uploadDocument from '../../../../../../utils/uploadDocument';

interface IProps {
	activity: MDFClaimActivity;
	claimParentFolderId: number;
	currentActivityIndex: number;
}

const MiscellaneousMarketingPopFields = ({
	activity,
	claimParentFolderId,
	currentActivityIndex,
	setFieldValue,
}: IProps & Pick<FormikContextType<MDFClaim>, 'setFieldValue'>) => {
	return (
		<>
			<PRMFormik.Field
				component={PRMForm.InputText}
				label="Telemarketing Metrics"
				name={`activities[${currentActivityIndex}].telemarketingMetrics`}
				required={activity.selected}
				textArea
			/>

			<PRMFormik.Field
				component={PRMForm.InputFile}
				description="Only files with the following extensions wil be accepted: doc, docx, jpg, jpeg, png, tif, tiff, pdf"
				displayType="secondary"
				label="Telemarketing Script"
				name={`activities[${currentActivityIndex}].telemarketingScript`}
				onAccept={async (liferayFile: LiferayFile) => {
					const uploadedLiferayDocument = await uploadDocument(
						liferayFile,
						claimParentFolderId
					);

					if (uploadedLiferayDocument) {
						setFieldValue(
							`activities[${currentActivityIndex}].telemarketingScript`,
							getFileFromLiferayDocument(uploadedLiferayDocument)
						);
					}
				}}
				outline
				small
			/>

			<InputMultipleFilesListing
				acceptedFilesExtensions="jpg, jpeg, png, tif, tiff, pdf"
				description="Drag and drop your files here to upload."
				label="Images"
				name={`activities[${currentActivityIndex}].proofOfPerformance.images`}
				onAccept={async (liferayFiles: LiferayFile[]) => {
					const uploadedLiferayDocuments = await Promise.all(
						liferayFiles.map(async (liferayFile) => {
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
						`activities[${currentActivityIndex}].proofOfPerformance.images`,
						activity.proofOfPerformance?.images
							? activity.proofOfPerformance.images.concat(
									uploadedLiferayDocuments as LiferayFile[]
							  )
							: uploadedLiferayDocuments
					);
				}}
				value={activity.proofOfPerformance?.images}
			/>

			<InputMultipleFilesListing
				acceptedFilesExtensions="doc, docx, jpg, jpeg, png, tif, tiff, pdf"
				description="Drag and drop your files here to upload."
				label="All Contents"
				name={`activities[${currentActivityIndex}].proofOfPerformance.allContents`}
				onAccept={async (liferayFiles: LiferayFile[]) => {
					const uploadedLiferayDocuments = await Promise.all(
						liferayFiles.map(async (liferayFile) => {
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
				required={activity.selected}
				value={activity.proofOfPerformance?.allContents}
			/>
		</>
	);
};

export default MiscellaneousMarketingPopFields;

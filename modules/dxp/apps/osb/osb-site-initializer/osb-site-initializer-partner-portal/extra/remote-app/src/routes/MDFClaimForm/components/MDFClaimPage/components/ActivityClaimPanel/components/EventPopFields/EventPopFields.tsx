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

const EventPopFields = ({
	activity,
	claimParentFolderId,
	currentActivityIndex,
}: IProps) => {
	const {setFieldValue} = useFormikContext<MDFRequest>();

	return (
		<>
			<PRMFormik.Field
				component={PRMForm.InputFile}
				description="Only files with the following extensions wil beaccepted: doc, docx, jpeg, jpg, pdf, tif, tiff"
				displayType="secondary"
				label="Event Program"
				name={`activities[${currentActivityIndex}].eventProgram`}
				onAccept={async (value: LiferayFile) => {
					const uploadedLiferayDocument = await uploadDocument(
						value,
						claimParentFolderId
					);

					if (uploadedLiferayDocument) {
						setFieldValue(
							`activities[${currentActivityIndex}].eventProgram`,
							getFileFromLiferayDocument(uploadedLiferayDocument)
						);
					}
				}}
				outline
				small
			/>

			<InputMultipleFilesListing
				acceptedFilesExtensions="doc, docx, jpeg, jpg, pdf, tif, tiff"
				description="Drag and drop your files here to upload."
				label="Event Invitations"
				name={`activities[${currentActivityIndex}].proofOfPerformance.eventInvitations`}
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
						`activities[${currentActivityIndex}].proofOfPerformance.eventInvitations`,
						activity.proofOfPerformance?.eventInvitations
							? activity.proofOfPerformance.eventInvitations.concat(
									uploadedLiferayDocuments as LiferayFile[]
							  )
							: uploadedLiferayDocuments
					);
				}}
				value={activity.proofOfPerformance?.eventInvitations}
			/>

			<InputMultipleFilesListing
				acceptedFilesExtensions="doc, docx, jpeg, jpg, pdf, tif, tiff"
				description="Drag and drop your files here to upload."
				label="Event Photos"
				name={`activities[${currentActivityIndex}].proofOfPerformance.eventPhotos`}
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
						`activities[${currentActivityIndex}].proofOfPerformance.eventPhotos`,
						activity.proofOfPerformance?.eventPhotos
							? activity.proofOfPerformance.eventPhotos.concat(
									uploadedLiferayDocuments as LiferayFile[]
							  )
							: uploadedLiferayDocuments
					);
				}}
				value={activity.proofOfPerformance?.eventPhotos}
			/>

			<InputMultipleFilesListing
				acceptedFilesExtensions="doc, docx, jpeg, jpg, pdf, tif, tiff"
				description="Drag and drop your files here to upload."
				label="Event Collaterals"
				name={`activities[${currentActivityIndex}].proofOfPerformance.eventCollaterals`}
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
						`activities[${currentActivityIndex}].proofOfPerformance.eventCollaterals`,
						activity.proofOfPerformance?.eventCollaterals
							? activity.proofOfPerformance.eventCollaterals.concat(
									uploadedLiferayDocuments as LiferayFile[]
							  )
							: uploadedLiferayDocuments
					);
				}}
				value={activity.proofOfPerformance?.eventCollaterals}
			/>
		</>
	);
};

export default EventPopFields;

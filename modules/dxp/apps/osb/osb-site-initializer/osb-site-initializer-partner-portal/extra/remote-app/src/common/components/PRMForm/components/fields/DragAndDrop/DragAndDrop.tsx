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

import {FormikContextType, useFormikContext} from 'formik';
import React from 'react';
import Dropzone, {useDropzone} from 'react-dropzone';

import MDFClaim from '../../../../../interfaces/mdfClaim';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

interface IProps {
	activityIndex: number;
	values: MDFClaim;
}

const DragAndDrop = ({
	activityIndex,
	description,
	label,
	setFieldValue,
}: PRMFormFieldProps &
	PRMFormFieldStateProps<File> &
	Pick<FormikContextType<MDFClaim>, 'setFieldValue'> &
	IProps) => {
	const {values} = useFormikContext<MDFClaim>();

	const {getInputProps, getRootProps, open} = useDropzone({
		noClick: true,
		noKeyboard: true,
		onDrop: (acceptedFiles) => {
			setFieldValue(
				`activities[${activityIndex}].contents`,
				acceptedFiles
			);
		},
	});

	return (
		<div className="mb-3">
			<h4 className="text-neutral-10">{label}</h4>

			<div
				{...getRootProps({
					className:
						'bg-white d-flex text-dark align-items-center justify-content-center rounded flex-column dropzone border-neutral-4 border',
				})}
			>
				<input {...getInputProps()} />

				<p className="font-weight-bold text-neutral-10 text-paragraph">
					{description}
				</p>

				<p className="text-neutral-7">
					Only files with the following extensions wil be accepted:
					doc, docx.jpeg, jpg, pdf, tif, tiff
				</p>

				<p className="font-weight-bold text-neutral-7">OR</p>

				<button className="btn btn-secondary" onClick={open}>
					Select Files
				</button>

				<div className="mt-3 overflow-auto" style={{height: '12rem'}}>
					<h5>Files</h5>

					{values?.activities &&
						values?.activities[
							activityIndex
						].contents?.map((file, i) => (
							<li key={i}>
								{`File:${file.name} Type:${file.type} Size:${file.size} bytes`}{' '}
							</li>
						))}
				</div>
			</div>
		</div>
	);
};
<Dropzone />;

export default DragAndDrop;

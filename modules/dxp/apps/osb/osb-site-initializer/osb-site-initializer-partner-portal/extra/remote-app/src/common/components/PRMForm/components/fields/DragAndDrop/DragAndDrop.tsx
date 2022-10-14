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

import {ClayInput} from '@clayui/form';
import {FormikContextType} from 'formik';
import Dropzone, {useDropzone} from 'react-dropzone';

import MDFClaim from '../../../../../interfaces/mdfClaim';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

interface IProps {
	onAccept: (value: File[]) => void;
}

const DragAndDrop = ({
	description,
	field,
	label,
	onAccept,
	required,
}: PRMFormFieldProps &
	PRMFormFieldStateProps<File[]> &
	Pick<FormikContextType<MDFClaim>, 'setFieldValue'> &
	IProps) => {
	const {acceptedFiles, getInputProps, getRootProps, open} = useDropzone({
		noClick: true,
		noKeyboard: true,
		onDrop: (acceptedFiles) => {
			onAccept(acceptedFiles);
		},
	});

	return (
		<div className="mb-3">
			{label && (
				<label className="font-weight-semi-bold ml-0">
					{label}

					{required && <span className="text-danger">*</span>}
				</label>
			)}

			<div
				{...getRootProps({
					className:
						'bg-white d-flex text-dark align-items-center justify-content-center rounded flex-column border-neutral-4 border',
				})}
			>
				<ClayInput
					{...getInputProps({
						name: field.name,
						required,
						type: 'file',
					})}
				/>

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

					{acceptedFiles?.map((file, i) => (
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

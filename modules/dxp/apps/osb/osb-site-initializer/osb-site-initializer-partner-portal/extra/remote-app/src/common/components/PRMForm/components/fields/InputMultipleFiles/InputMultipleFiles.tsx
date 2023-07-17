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

import ClayForm, {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import {FormikContextType} from 'formik';
import {useDropzone} from 'react-dropzone';

import LiferayFile from '../../../../../interfaces/liferayFile';
import MDFClaim from '../../../../../interfaces/mdfClaim';
import PRMFormik from '../../../../PRMFormik';
import ListFiles from '../InputMultipleFilesListing/components/ListFiles';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

interface IProps {
	acceptedFilesExtensions: string;
	onAccept: (liferayFiles: LiferayFile[]) => void;
	value?: LiferayFile[] | Object[];
}

const InputMultipleFiles = ({
	acceptedFilesExtensions,
	description,
	field,
	label,
	meta,
	onAccept,
	required,
	value,
}: PRMFormFieldProps &
	PRMFormFieldStateProps<LiferayFile[]> &
	Pick<FormikContextType<MDFClaim>, 'setFieldValue'> &
	IProps) => {
	const {getInputProps, getRootProps, open} = useDropzone({
		noClick: true,
		noKeyboard: true,
		onDrop: (acceptedFiles) => {
			onAccept(acceptedFiles);
		},
	});

	return (
		<>
			<ClayForm.Group
				className={classNames('d-flex flex-column mb-0 pt-3', {
					'has-error': meta.error,
				})}
			>
				{label && (
					<label className="font-weight-semi-bold">
						{label}

						{required && <span className="text-danger">*</span>}
					</label>
				)}

				<div
					{...getRootProps({
						className: classNames(
							'bg-white d-flex align-items-center rounded flex-column 4 border',
							{
								'border-danger': meta.error,
								'border-neutral-4': !meta.touched,
								'border-success': !meta.error,
							}
						),
					})}
				>
					<ClayInput
						{...getInputProps({
							name: field.name,
						})}
					/>

					<div className="align-items-center d-flex flex-column p-3 row">
						<p className="font-weight-bold text-neutral-10 text-paragraph">
							{description}
						</p>

						<p className="mb-0 text-neutral-7">
							Only files with the following extensions wil be
							accepted:
						</p>

						<p className="font-weight-bold text-neutral-7">
							{acceptedFilesExtensions}
						</p>

						<button
							className="btn btn-secondary"
							onClick={open}
							type="button"
						>
							Select Files
						</button>
					</div>
				</div>

				{meta.error && (
					<ClayForm.FeedbackGroup>
						<ClayForm.FeedbackItem>
							{meta.error}
						</ClayForm.FeedbackItem>
					</ClayForm.FeedbackGroup>
				)}
			</ClayForm.Group>

			{value && (
				<PRMFormik.Array
					component={ListFiles}
					files={value}
					meta={meta}
					name={field.name}
				/>
			)}
		</>
	);
};

export default InputMultipleFiles;

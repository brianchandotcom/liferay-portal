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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm from '@clayui/form';
import classNames from 'classnames';
import {ArrayHelpers} from 'formik';

import LiferayFile from '../../../../../../../interfaces/liferayFile';
import deleteDocument from '../../../../../../../services/liferay/headless-delivery/deleteDocument';
import deleteObjectEntry from '../../../../../../../services/liferay/object/deleteObjectEntry/deleteObjectEntry';
import {ResourceName} from '../../../../../../../services/liferay/object/enum/resourceName';

interface IProps {
	arrayHelpers: ArrayHelpers;
	files: LiferayFile[];
	meta: {
		error?: string[];
		touched: boolean;
	};
}

const ListFiles = ({arrayHelpers, files, meta}: IProps) => {
	return (
		<div>
			{files.map((file, index) => (
				<ClayForm.Group
					className={classNames('mb-0', {
						'has-error': meta.error?.[index],
					})}
					key={index}
				>
					<div
						className={classNames(
							'align-items-center bg-neutral-0 border d-flex justify-content-between mt-2 px-2 rounded-xs shadow-sm',
							{
								'border-danger': meta.error?.[index],
								'border-success': !meta.error?.[index],
							}
						)}
						key={index}
					>
						<div className="font-weight-bold text-neutral-8">
							{file.name}
						</div>

						<ClayButtonWithIcon
							className="text-neutral-7"
							displayType={null}
							onClick={async () => {
								if (file.documentId) {
									const deletedDocument = await deleteDocument(
										file.documentId
									);

									if (deletedDocument) {
										arrayHelpers.remove(index);
									}
								}
								else {
									arrayHelpers.remove(index);
								}

								if (file.activityDocumentId) {
									await deleteObjectEntry(
										ResourceName.MDF_CLAIM_ACTIVITY_DOCUMENTS,
										file.activityDocumentId
									);
								}
							}}
							small
							symbol="times-circle"
						/>
					</div>

					{meta.error?.[index] && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								{meta.error?.[index]}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}
				</ClayForm.Group>
			))}
		</div>
	);
};
export default ListFiles;

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
import {ArrayHelpers} from 'formik';

interface IProps {
	arrayHelpers: ArrayHelpers;
	files: File[];
}

const ListFiles = ({arrayHelpers, files}: IProps) => {
	return (
		<div>
			{files.map((file, index) => (
				<div
					className="align-items-center bg-neutral-0 border border-neutral-4 d-flex justify-content-between mt-2 p-2 rounded-xs shadow-sm"
					key={index}
				>
					<div className="font-weight-bold">
						<div className="text-neutral-8">{file.name}</div>
					</div>

					<ClayButtonWithIcon
						className="text-neutral-7"
						displayType={null}
						onClick={() => arrayHelpers.remove(index)}
						small
						symbol="times-circle"
					/>
				</div>
			))}
		</div>
	);
};
export default ListFiles;

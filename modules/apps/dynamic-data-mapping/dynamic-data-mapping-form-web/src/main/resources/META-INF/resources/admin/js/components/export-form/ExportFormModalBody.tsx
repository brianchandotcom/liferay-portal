/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayForm, {ClaySelect} from '@clayui/form';
import React, {useState} from 'react';

const ExportFormModalBody: React.FC<IProps> = ({
	csvExport = 'enabled-with-warning',
	fileExtensions,
	portletNamespace,
}) => {
	const [fileExtension, setFileExtension] = useState(fileExtensions[0]);

	return (
		<>
			<ClayAlert>
				{Liferay.Language.get('timezone-warning-message')}
			</ClayAlert>

			<ClayAlert>
				{Liferay.Language.get(
					'the-export-includes-data-from-all-fields-and-form-versions'
				)}
			</ClayAlert>

			{fileExtension === 'csv' &&
				csvExport === 'enabled-with-warning' && (
					<ClayAlert displayType="warning">
						{Liferay.Language.get('csv-warning-message')}
					</ClayAlert>
				)}

			{fileExtension === 'xls' && (
				<>
					<ClayAlert displayType="warning">
						{Liferay.Language.get(
							'the-total-number-of-characters-that-a-cell-can-contain-is-32767-characters'
						)}
					</ClayAlert>

					<ClayAlert displayType="warning">
						{Liferay.Language.get(
							'the-total-number-of-columns-that-a-worksheet-can-contain-is-256-columns'
						)}
					</ClayAlert>
				</>
			)}

			<ClayForm.Group>
				<label htmlFor={`${portletNamespace}fileExtension`}>
					{Liferay.Language.get('file-extension')}
				</label>

				<ClaySelect
					id={`${portletNamespace}fileExtension`}
					name={`${portletNamespace}fileExtension`}
					onChange={({currentTarget: {value}}) =>
						setFileExtension(value)
					}
					value={fileExtension}
				>
					{fileExtensions.map((fileExtension) =>
						fileExtension === 'csv' &&
						csvExport === 'disabled' ? null : (
							<ClaySelect.Option
								key={fileExtension}
								label={fileExtension.toUpperCase()}
								value={fileExtension}
							/>
						)
					)}
				</ClaySelect>
			</ClayForm.Group>
		</>
	);
};

interface IProps {
	csvExport: string;
	fileExtensions: string[];
	portletNamespace: string;
}

export default ExportFormModalBody;

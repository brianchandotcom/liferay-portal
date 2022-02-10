/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayAlert from '@clayui/alert';
import ClayForm, {ClaySelect} from '@clayui/form';
import React, {useState} from 'react';

import {TFileExtensions} from './types';

const ExportFormModalBody: React.FC<IProps> = ({
	csvExport = 'enabled-with-warning',
	fileExtensions,
	portletNamespace,
}) => {
	const [fileExtension, setFileExtension] = useState(
		Object.keys(fileExtensions)[0]
	);

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
					{Object.entries(
						fileExtensions
					).map(([fileExtension, label]) =>
						fileExtension === 'csv' &&
						csvExport === 'disabled' ? null : (
							<ClaySelect.Option
								key={fileExtension}
								label={label}
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
	fileExtensions: TFileExtensions;
	portletNamespace: string;
}

export default ExportFormModalBody;

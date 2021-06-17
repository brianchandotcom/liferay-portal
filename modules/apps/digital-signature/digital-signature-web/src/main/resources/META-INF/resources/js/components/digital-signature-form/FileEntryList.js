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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayList from '@clayui/list';
import classNames from 'classnames';
import React from 'react';

const FileEntryList = ({errors, fileEntries = [], setFieldValue}) => {
	const hasError = (fileEntryId) => {
		if (errors) {
			return !!errors.find((entry) => entry.fileEntryId === fileEntryId);
		}

		return false;
	};

	const onRemoveFileEntry = (fileEntryIndex) => {
		setFieldValue(
			'fileEntries',
			fileEntries.filter((_, index) => index !== fileEntryIndex)
		);
	};

	if (!fileEntries.length) {
		return null;
	}

	return (
		<ClayList className="file-entries-list mt-1">
			<ClayList.Header>
				{Liferay.Language.get('documents-added')}
			</ClayList.Header>

			{fileEntries.map(({fileEntryId, title}, index) => (
				<ClayList.Item
					className={classNames({
						'has-error': hasError(fileEntryId),
					})}
					flex
					key={fileEntryId}
				>
					<ClayList.ItemField expand>
						<ClayList.ItemTitle>{title}</ClayList.ItemTitle>
						{hasError(fileEntryId) && (
							<ClayList.ItemTitle className="text-error">
								{Liferay.Language.get(
									'this-file-extension-is-not-supported-by-docusign'
								)}
							</ClayList.ItemTitle>
						)}
					</ClayList.ItemField>
					<ClayList.ItemField>
						<ClayButtonWithIcon
							displayType="unstyled"
							monospaced={false}
							onClick={() => onRemoveFileEntry(index)}
							symbol="trash"
						/>
					</ClayList.ItemField>
				</ClayList.Item>
			))}
		</ClayList>
	);
};

export default FileEntryList;

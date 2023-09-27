/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

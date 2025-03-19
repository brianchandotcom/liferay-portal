/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {sub} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

const FieldFile = ({
	label,
	validExtensions,
}: {
	label: string;
	validExtensions: string;
}) => {
	const fileInputRef = useRef<HTMLInputElement | null>(null);
	const [file, setFile] = useState<File | null>(null);

	const resetFileInput = () => {
		if (fileInputRef && fileInputRef.current) {
			fileInputRef.current.value = '';
		}

		setFile(null);
	};

	const onChangeClick = () => {
		fileInputRef.current?.click();

		resetFileInput();
	};

	const handleFileInputChange = async ({
		target,
	}: React.ChangeEvent<HTMLInputElement>) => {
		if (
			!target.value.endsWith(validExtensions) ||
			!target.files ||
			target.files?.length === 0
		) {
			return;
		}

		setFile(target.files[0]);
	};

	return (
		<ClayInput.Group>
			<ClayInput.GroupItem>
				<label htmlFor="inputId">{label}</label>

				<ClayInput id="inputId" value={file?.name || ''} />

				<input
					accept={validExtensions}
					className="d-none"
					id="fileInputId"
					onChange={handleFileInputChange}
					ref={fileInputRef}
					type="file"
				/>
			</ClayInput.GroupItem>

			<ClayInput.GroupItem className="mt-4" shrink>
				{file ? (
					<>
						<ClayButtonWithIcon
							aria-label={sub(
								Liferay.Language.get('change-x'),
								Liferay.Language.get('file')
							)}
							className="lfr-portal-tooltip"
							displayType="secondary"
							onClick={onChangeClick}
							symbol="change"
							title={sub(
								Liferay.Language.get('change-x'),
								Liferay.Language.get('file')
							)}
							type="button"
						/>

						<ClayButtonWithIcon
							aria-label={sub(
								Liferay.Language.get('remove-x'),
								Liferay.Language.get('file')
							)}
							className="lfr-portal-tooltip"
							displayType="unstyled"
							onClick={resetFileInput}
							symbol="trash"
							title={sub(
								Liferay.Language.get('remove-x'),
								Liferay.Language.get('file')
							)}
							type="button"
						/>
					</>
				) : (
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('add')}
						className="lfr-portal-tooltip"
						displayType="secondary"
						onClick={() => fileInputRef.current?.click()}
						symbol="plus"
						title={Liferay.Language.get('add')}
						type="button"
					/>
				)}
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
};

export default FieldFile;

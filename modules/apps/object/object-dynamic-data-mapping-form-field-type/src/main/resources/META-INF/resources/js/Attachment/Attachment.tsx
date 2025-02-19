/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type';
import {
	FieldChangeEventHandler,
	LocalizedValue,
} from 'dynamic-data-mapping-form-field-type/src/main/resources/META-INF/resources/types';
import {
	AvailableLocale,
	EditingLocale,
} from 'dynamic-data-mapping-form-field-type/src/main/resources/META-INF/resources/util/localizable/LocalesDropdown';
import React, {useState} from 'react';

import AttachmentBase, {
	AttachmentBaseProps,
	AttachmentFile,
} from './AttachmentBase';
import AttachmentLocalizedObjectField from './AttachmentLocalizedObjectField';

export interface AttachmentProps
	extends AttachmentBaseProps<string | LocalizedValue<string>> {
	availableLocales: AvailableLocale[];
	contentURL?: string;
	defaultLocale: EditingLocale;
	fieldName: string;
	fileEntryProperties: AttachmentFile | LocalizedValue<AttachmentFile>;
	localizedObjectField: boolean;
	onChange: FieldChangeEventHandler<string | LocalizedValue<string>>;
	title?: string;
}

export default function Attachment({
	contentURL,
	fileEntryProperties,
	localizedObjectField,
	onChange,
	readOnly,
	tip,
	title,
	value,
	...otherProps
}: AttachmentProps) {
	const isLocalizedObjectField: boolean =
		Liferay.FeatureFlags['LPD-32050'] && !!localizedObjectField;

	const getAttachment = () => {
		if (Liferay.FeatureFlags['LPD-32050']) {
			return fileEntryProperties;
		}
		else if (contentURL && title) {
			return {contentURL, title};
		}

		return null;
	};

	const [attachment, setAttachment] = useState<AttachmentFile | null>(
		getAttachment() as AttachmentFile | null
	);
	const [error, setError] = useState({});

	const handleAttachmentChange = (
		attachmentValue: AttachmentFile,
		fileId: string
	) => {
		setAttachment(attachmentValue);

		onChange({target: {value: fileId}});
	};

	const handleDelete = () => {
		setAttachment(null);

		onChange({target: {value: ''}}); // TODO: fix backend to support null
	};

	return (
		<FieldBase
			readOnly={readOnly}
			tip={!readOnly ? tip : ''}
			{...otherProps}
			{...error}
		>
			{isLocalizedObjectField ? (
				<AttachmentLocalizedObjectField
					{...otherProps}
					error={error}
					fileEntryProperties={
						fileEntryProperties as LocalizedValue<AttachmentFile>
					}
					onChange={onChange}
					readOnly={readOnly}
					setError={setError}
					tip={tip}
					value={value as LocalizedValue<string>}
				/>
			) : (
				<AttachmentBase
					{...otherProps}
					attachment={attachment}
					error={error}
					handleDelete={handleDelete}
					onAttachmentChange={handleAttachmentChange}
					readOnly={readOnly}
					setError={setError}
					tip={tip}
					value={value as string}
				/>
			)}
		</FieldBase>
	);
}

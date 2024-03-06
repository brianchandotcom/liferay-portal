/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FieldChangeEventHandler} from 'dynamic-data-mapping-form-field-type';
import './Attachment.scss';
export default function Attachment({
	acceptedFileExtensions,
	contentURL,
	fileSource,
	maximumFileSize,
	onChange,
	overallMaximumUploadRequestSize,
	readOnly,
	tip,
	title,
	url,
	...otherProps
}: IProps): JSX.Element;
interface IProps {
	acceptedFileExtensions: string;
	contentURL: string;
	fileSource: string;
	maximumFileSize: number;
	onChange: FieldChangeEventHandler<string>;
	overallMaximumUploadRequestSize: number;
	readOnly: boolean;
	tip: string;
	title: string;
	url: string;
}
export {};

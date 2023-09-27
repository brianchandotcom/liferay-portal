/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {ObjectFieldErrors} from '../../ObjectFieldFormBase';
interface IAttachmentPropertiesProps {
	errors: ObjectFieldErrors;
	objectFieldSettings: ObjectFieldSetting[];
	onSettingsChange: (setting: ObjectFieldSetting) => void;
}
export declare function AttachmentProperties({
	errors,
	objectFieldSettings,
	onSettingsChange,
}: IAttachmentPropertiesProps): JSX.Element;
export {};

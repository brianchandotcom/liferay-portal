/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare function deleteObjectField(
	objectFieldId: number,
	objectFieldLabel: string
): Promise<void>;
interface handleTriggerDeleteObjectFieldProps {
	baseResourceURL: string;
	objectFieldId: number;
	objectFieldLabel: string;
	onAfterDelete: () => void;
	setObjectFieldDeleteInfo: (value: ObjectFieldDeleteInfoProps) => void;
}
export declare function handleTriggerDeleteObjectField({
	baseResourceURL,
	objectFieldId,
	objectFieldLabel,
	onAfterDelete,
	setObjectFieldDeleteInfo,
}: handleTriggerDeleteObjectFieldProps): Promise<void>;
export {};

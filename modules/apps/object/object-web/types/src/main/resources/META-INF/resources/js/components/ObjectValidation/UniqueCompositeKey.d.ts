/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

export interface UniqueCompositeKeyProps {
	setShowUniqueCompositeKeyCardAlert: (value: boolean) => void;
	showUniqueCompositeKeyCardAlert: boolean;
}
export declare function UniqueCompositeKey({
	setShowUniqueCompositeKeyCardAlert,
	showUniqueCompositeKeyCardAlert,
}: UniqueCompositeKeyProps): JSX.Element;

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import '../../EditObjectFieldContent.scss';
interface SearchableProps {
	isApproved: boolean;
	modelBuilder?: boolean;
	onSubmit?: (value?: Partial<ObjectField>) => void;
	readOnly: boolean;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}
export declare function SearchableContainer({
	isApproved,
	modelBuilder,
	onSubmit,
	readOnly,
	setValues,
	values,
}: SearchableProps): JSX.Element;
export {};

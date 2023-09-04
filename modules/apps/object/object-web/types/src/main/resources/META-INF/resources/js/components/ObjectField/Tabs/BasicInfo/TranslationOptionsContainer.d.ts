/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './TranslationOptionsContainer.scss';
interface TranslationOptionsContainerProps {
	modelBuilder?: boolean;
	objectDefinition: Partial<ObjectDefinition>;
	published: boolean;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}
export declare function TranslationOptionsContainer({
	modelBuilder,
	objectDefinition,
	published,
	setValues,
	values,
}: TranslationOptionsContainerProps): JSX.Element;
export {};

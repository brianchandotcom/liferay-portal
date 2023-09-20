/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './BuilderScreen.scss';
export declare function BuilderScreen({
	builderScreenItems,
	creationLanguageId,
	defaultSort,
	disableEdit,
	emptyState,
	filter,
	firstColumnHeader,
	hasDragAndDrop,
	onChangeColumnOrder,
	onDeleteColumn,
	onEditing,
	onEditingObjectFieldName,
	onVisibleEditModal,
	openModal,
	secondColumnHeader,
	thirdColumnHeader,
}: IProps): JSX.Element;
declare type TLabelValueObject = {
	label: string;
	value: string;
};
declare type TBuilderScreenItem = {
	defaultSort?: boolean;
	disableEdit?: boolean;
	fieldLabel?: string;
	filterBy?: string;
	label: LocalizedValue<string>;
	objectFieldBusinessType?: string;
	objectFieldName: string;
	priority?: number;
	sortOrder?: string;
	type?: string;
	value?: string;
	valueList?: TLabelValueObject[];
};
interface IProps {
	builderScreenItems: TBuilderScreenItem[];
	creationLanguageId?: Liferay.Language.Locale;
	defaultSort?: boolean;
	disableEdit?: boolean;
	emptyState: {
		buttonText: string;
		description: string;
		title: string;
	};
	filter?: boolean;
	firstColumnHeader: string;
	hasDragAndDrop?: boolean;
	onChangeColumnOrder?: (draggedIndex: number, targetIndex: number) => void;
	onDeleteColumn: (objectFieldName: string) => void;
	onEditing?: (boolean: boolean) => void;
	onEditingObjectFieldName?: (objectFieldName: string) => void;
	onVisibleEditModal?: (boolean: boolean) => void;
	openModal: () => void;
	secondColumnHeader: string;
	thirdColumnHeader?: string;
}
export {};

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IFDSViewSectionProps} from '../../../FDSView';
import '../../../../css/TableVisualizationMode.scss';
import {IBaseVisualizationMode} from '../VisualizationModes';
declare type LocalizedValue<T> = Liferay.Language.LocalizedValue<T>;
export interface ITable extends IBaseVisualizationMode<'table'> {}
export interface IFDSField {
	contextPath: string;
	externalReferenceCode: string;
	id: number;
	label: string;
	label_i18n: LocalizedValue<string>;
	name: string;
	renderer: string;
	rendererLabel?: string;
	sortable: boolean;
	type: string;
}
declare function Table({
	fdsClientExtensionCellRenderers,
	fdsView,
	namespace,
	saveFDSFieldsURL,
	title,
}: IFDSViewSectionProps & {
	title?: string;
}): JSX.Element;
export declare function Fields(props: IFDSViewSectionProps): JSX.Element;
export default Table;

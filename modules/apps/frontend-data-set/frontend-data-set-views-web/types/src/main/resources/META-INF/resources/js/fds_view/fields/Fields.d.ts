/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IFDSViewSectionProps} from '../../FDSView';
import '../../../css/Fields.scss';
declare type LocalizedValue<T> = Liferay.Language.LocalizedValue<T>;
export interface IFDSField {
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
declare const Fields: ({
	fdsClientExtensionCellRenderers,
	fdsView,
	namespace,
	saveFDSFieldsURL,
}: IFDSViewSectionProps) => JSX.Element;
export default Fields;

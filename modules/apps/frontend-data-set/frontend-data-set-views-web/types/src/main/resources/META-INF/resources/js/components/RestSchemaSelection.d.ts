/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import '../../css/FDSEntries.scss';
interface IRestSchemaSelectionProps {
	namespace: string;
	onChange: Function;
	restApplications: string[];
}
declare function RestSchemaSelection({
	namespace,
	onChange,
	restApplications,
}: IRestSchemaSelectionProps): JSX.Element;
export default RestSchemaSelection;

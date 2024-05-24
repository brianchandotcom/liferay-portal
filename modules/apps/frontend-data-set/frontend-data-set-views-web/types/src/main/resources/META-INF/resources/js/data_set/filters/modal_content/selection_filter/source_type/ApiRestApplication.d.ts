/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface IApiRestApplicationModalContentProps {
	onChange: ({
		selectedItemKey,
		selectedItemLabel,
		selectedRESTApplication,
		selectedRESTEndpoint,
		selectedRESTSchema,
	}: {
		selectedItemKey: string;
		selectedItemLabel: string;
		selectedRESTApplication: string;
		selectedRESTEndpoint: string;
		selectedRESTSchema: string;
	}) => void;
	restApplications: string[];
}
declare function ApiRestApplication({
	onChange,
	restApplications,
}: IApiRestApplicationModalContentProps): JSX.Element;
export default ApiRestApplication;

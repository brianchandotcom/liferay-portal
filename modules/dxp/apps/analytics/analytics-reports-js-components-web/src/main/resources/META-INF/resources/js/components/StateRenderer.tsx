/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React from 'react';

interface IStateRendererProps<TData> {
	children: (props: {data: TData}) => JSX.Element;
	data: TData | null;
	error: string;
	loading: boolean;
}

const StateRenderer = <TData,>({
	children,
	data,
	error,
	loading,
}: IStateRendererProps<TData>) => {
	if (loading) {
		return <ClayLoadingIndicator className="my-5" />;
	}

	if (error) {
		return <ClayAlert displayType="danger" title={error} />;
	}

	if (data) {
		return children({data});
	}

	return null;
};

export default StateRenderer;

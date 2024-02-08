/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayChart from '@clayui/charts';
import React, {useCallback, useRef} from 'react';

export default function ChartWrapper({data, loading}) {
	const chartRef = useRef();

	const resize = useCallback(
		() => chartRef.current && chartRef.current.resize(),
		[chartRef]
	);

	const wrapper = useCallback(
		(node) => {
			if (node !== null) {
				new ResizeObserver(resize).observe(node);
			}
		},
		[resize]
	);

	if (loading) {
		return <span aria-hidden="true" className="loading-animation" />;
	}
	else if (!data.data.columns.length) {
		return <p>{Liferay.Language.get('no-data-available')}</p>;
	}
	else {
		return (
			<div ref={wrapper}>
				<ClayChart {...data} ref={chartRef} />
			</div>
		);
	}
}

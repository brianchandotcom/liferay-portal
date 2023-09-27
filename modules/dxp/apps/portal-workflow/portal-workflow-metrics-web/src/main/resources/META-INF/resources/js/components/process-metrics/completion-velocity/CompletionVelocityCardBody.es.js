/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import React from 'react';

import ContentView from '../../../shared/components/content-view/ContentView.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import {formatNumber} from '../../../shared/util/numeral.es';
import VelocityChart from './VelocityChart.es';

function Body(props) {
	const statesProps = {
		errorProps: {
			actionButton: <ReloadButton />,
			className: 'mb-3 mt-5 py-8',
			hideAnimation: true,
			message: Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			),
			messageClassName: 'small',
		},
		loadingProps: {className: 'mb-5 mt-4 py-8'},
	};

	return (
		<ClayPanel.Body className="pt-0">
			<ContentView {...statesProps}>
				{props.data ? (
					<>
						<Body.Info {...props} />

						<VelocityChart velocityData={props.data} {...props} />
					</>
				) : (
					<></>
				)}
			</ContentView>
		</ClayPanel.Body>
	);
}

function Info({data, velocityUnit}) {
	const formattedValue = formatNumber(data.value, '0[.]00');

	return (
		<div className="pb-2">
			<span className="velocity-value">{formattedValue}</span>

			<span className="velocity-unit">{velocityUnit.name}</span>
		</div>
	);
}

Body.Info = Info;

export default Body;

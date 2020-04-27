/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';

import Panel from '../../../shared/components/Panel.es';
import ContentView from '../../../shared/components/content-view/ContentView.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import {formatNumber} from '../../../shared/util/numeral.es';
import VelocityChart from './VelocityChart.es';

const Body = (props) => {
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
		<Panel.Body elementClasses="pt-0">
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
		</Panel.Body>
	);
};

const Info = ({data, velocityUnit}) => {
	const formattedValue = formatNumber(data.value, '0[.]00');

	return (
		<div className="pb-2">
			<span className="velocity-value">{formattedValue}</span>
			<span className="velocity-unit">{velocityUnit.name}</span>
		</div>
	);
};

Body.Info = Info;

export {Body};

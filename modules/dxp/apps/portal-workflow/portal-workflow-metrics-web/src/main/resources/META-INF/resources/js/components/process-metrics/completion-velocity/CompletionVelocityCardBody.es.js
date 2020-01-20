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
import EmptyState from '../../../shared/components/empty-state/EmptyState.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import LoadingState from '../../../shared/components/loading/LoadingState.es';
import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {formatNumber} from '../../../shared/util/numeral.es';
import VelocityChart from './VelocityChart.es';

const Body = props => {
	return (
		<Panel.Body elementClasses="pt-0">
			<PromisesResolver.Pending>
				<Body.Loading />
			</PromisesResolver.Pending>

			<PromisesResolver.Resolved>
				{props.data && (
					<>
						<Body.Info {...props} />

						<VelocityChart velocityData={props.data} {...props} />
					</>
				)}
			</PromisesResolver.Resolved>

			<PromisesResolver.Rejected>
				<Body.Error />
			</PromisesResolver.Rejected>
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

const ErrorView = () => {
	return (
		<EmptyState
			actionButton={<ReloadButton />}
			className="border-0 mb-5"
			hideAnimation={true}
			message={Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			)}
			messageClassName="small"
		/>
	);
};

const LoadingView = () => {
	return <LoadingState className="border-0 mt-8 pb-5 pt-5 sheet" />;
};

Body.Error = ErrorView;
Body.Info = Info;
Body.Loading = LoadingView;

export {Body};

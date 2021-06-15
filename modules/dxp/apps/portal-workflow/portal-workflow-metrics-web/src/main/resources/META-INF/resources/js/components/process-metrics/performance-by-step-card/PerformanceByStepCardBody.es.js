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

import ClayIcon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import React, {useContext} from 'react';

import ContentView from '../../../shared/components/content-view/ContentView.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import ChildLink from '../../../shared/components/router/ChildLink.es';
import {AppContext} from '../../AppContext.es';
import Table from './PerformanceByStepCardTable.es';

function Body({items, totalCount}) {
	const statesProps = {
		emptyProps: {
			className: 'mt-5 py-8',
			hideAnimation: true,
			messageClassName: 'small',
		},
		errorProps: {
			actionButton: <ReloadButton />,
			className: 'mt-4 py-8',
			hideAnimation: true,
			message: Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			),
			messageClassName: 'small',
		},
		loadingProps: {className: 'mt-4 py-8'},
	};

	return (
		<ClayPanel.Body>
			<ContentView {...statesProps}>
				{totalCount > 0 && <Body.Table items={items} />}
			</ContentView>
		</ClayPanel.Body>
	);
}

function Footer({processId, processVersion, timeRange, totalCount}) {
	const {defaultDelta} = useContext(AppContext);
	const filters = {};

	const {dateEnd, dateStart, key} = timeRange;

	if (dateEnd && dateStart && key) {
		filters.dateEnd = dateEnd;
		filters.dateStart = dateStart;
		filters.timeRange = [key];
	}

	filters.processVersion = processVersion;

	const viewAllStepsQuery = {filters};
	const viewAllStepsUrl = `/performance/step/${processId}/${defaultDelta}/1/breachedInstancePercentage:desc`;

	return (
		<ClayPanel.Footer className="fixed-bottom">
			<div className="mb-1 text-right">
				<ChildLink
					className="border-0 btn btn-secondary btn-sm"
					query={viewAllStepsQuery}
					to={viewAllStepsUrl}
				>
					<span className="mr-2">
						{`${Liferay.Language.get(
							'view-all-steps'
						)} (${totalCount})`}
					</span>

					<ClayIcon symbol="caret-right-l" />
				</ChildLink>
			</div>
		</ClayPanel.Footer>
	);
}

Body.Table = Table;

export {Body, Footer};

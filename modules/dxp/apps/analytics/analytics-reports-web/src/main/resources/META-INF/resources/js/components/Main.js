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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {StoreStateContext} from '../context/StoreContext';
import BasicInformation from './BasicInformation';
import Chart from './Chart';
import Hint from './Hint';
import TotalCount from './TotalCount';
import TrafficSources from './TrafficSources';
import Translation from './Translation';

export default function Main({
	author,
	canonicalURL,
	onSelectedLanguageClick,
	onTrafficSourceClick,
	pagePublishDate,
	pageTitle,
	timeSpanOptions,
	viewURLs,
}) {
	const {endpoints, loading, totalReads, totalViews} = useContext(
		StoreStateContext
	);

	return loading ? (
		<ClayLoadingIndicator small />
	) : (
		<div className="c-p-3">
			<BasicInformation
				author={author}
				canonicalURL={canonicalURL}
				publishDate={pagePublishDate}
				title={pageTitle}
			/>

			<div className="mt-4">
				<Translation
					onSelectedLanguageClick={onSelectedLanguageClick}
					viewURLs={viewURLs}
				/>
			</div>

			<h5 className="mt-3 sheet-subtitle">
				{Liferay.Language.get('engagement')}
			</h5>

			<TotalCount
				className="mb-2"
				label={Liferay.Util.sub(Liferay.Language.get('total-views'))}
				popoverHeader={Liferay.Language.get('total-views')}
				popoverMessage={Liferay.Language.get(
					'this-number-refers-to-the-total-number-of-views-since-the-content-was-published'
				)}
				value={totalViews}
			/>

			{endpoints.analyticsReportsTotalReadsURL && (
				<TotalCount
					label={Liferay.Util.sub(
						Liferay.Language.get('total-reads')
					)}
					popoverHeader={Liferay.Language.get('total-reads')}
					popoverMessage={Liferay.Language.get(
						'this-number-refers-to-the-total-number-of-reads-since-the-content-was-published'
					)}
					value={totalReads}
				/>
			)}

			<Chart
				publishDate={pagePublishDate}
				timeSpanOptions={timeSpanOptions}
			/>

			<h5 className="mt-3 sheet-subtitle">
				{Liferay.Language.get('traffic-channels')}
				<Hint
					message={Liferay.Language.get('traffic-channels-help')}
					secondary={true}
					title={Liferay.Language.get('traffic-channels')}
				/>
			</h5>

			<TrafficSources onTrafficSourceClick={onTrafficSourceClick} />
		</div>
	);
}

Main.proptypes = {
	author: PropTypes.object.isRequired,
	canonicalURL: PropTypes.string.isRequired,
	onSelectedLanguageClick: PropTypes.func.isRequired,
	onTrafficSourceClick: PropTypes.func.isRequired,
	pagePublishDate: PropTypes.string.isRequired,
	pageTitle: PropTypes.string.isRequired,
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	viewURLs: PropTypes.arrayOf(
		PropTypes.shape({
			default: PropTypes.bool.isRequired,
			languageId: PropTypes.string.isRequired,
			languageLabel: PropTypes.string.isRequired,
			selected: PropTypes.bool.isRequired,
			viewURL: PropTypes.string.isRequired,
		})
	).isRequired,
};

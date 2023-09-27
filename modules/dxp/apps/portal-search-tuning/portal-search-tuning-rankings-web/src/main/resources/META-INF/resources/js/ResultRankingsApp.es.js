/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LearnResourcesContext} from 'frontend-js-components-web';
import React from 'react';

import ThemeContext from './ThemeContext.es';
import ResultRankingsForm from './components/ResultRankingsForm.es';
import ErrorBoundary from './components/shared/ErrorBoundary.es';

export default function ({context, props}) {
	const {learnResources, ...additionalProps} = props;

	return (
		<ThemeContext.Provider value={context}>
			<LearnResourcesContext.Provider value={learnResources}>
				<div className="result-rankings-root">
					<ErrorBoundary
						component={Liferay.Language.get('result-rankings')}
					>
						<ResultRankingsForm {...additionalProps} />
					</ErrorBoundary>
				</div>
			</LearnResourcesContext.Provider>
		</ThemeContext.Provider>
	);
}

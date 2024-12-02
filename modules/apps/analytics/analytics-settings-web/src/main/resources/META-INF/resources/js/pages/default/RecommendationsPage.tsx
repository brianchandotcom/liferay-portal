/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React from 'react';

import Recommendations from '../.././components/recommendations/Recommendations';
import BasePage from '../../components/BasePage';
import {IGenericPageProps} from './DefaultPage';

const RecommendationsPage: React.FC<IGenericPageProps> = ({title}) => (
	<BasePage
		description={
			<>
				{Liferay.Language.get(
					'content-recommendations-personalize-user-experiences-by-suggesting-relevant-items-based-on-user-behavior-and-preferences'
				)}

				<ClayLink
					className="ml-1"
					href="https://learn.liferay.com/w/analytics-cloud/getting-started/connecting-liferay-dxp-to-analytics-cloud"
				>
					{Liferay.Language.get('learn-more-about-recommendations')}
				</ClayLink>
			</>
		}
		title={title}
	>
		<Recommendations />
	</BasePage>
);

export default RecommendationsPage;

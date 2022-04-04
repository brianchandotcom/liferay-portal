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

import ClayButton from '@clayui/button';
import {ManagementToolbar} from 'frontend-js-components-web';
import {PropTypes} from 'prop-types';
import React, {Component} from 'react';

import {sub} from '../../utils/language.es';

class FilterDisplay extends Component {
	static propTypes = {
		onClear: PropTypes.func,
		searchBarTerm: PropTypes.string,
		totalResultsCount: PropTypes.number,
	};

	render() {
		const {onClear, searchBarTerm, totalResultsCount} = this.props;

		return (
			<ManagementToolbar.ResultsBar
				title={Liferay.Language.get('filter')}
			>
				<ManagementToolbar.ResultsBarItem expand>
					<span className="component-text text-truncate-inline">
						<span className="text-truncate">
							{sub(
								totalResultsCount === 1
									? Liferay.Language.get('x-result-for-x')
									: Liferay.Language.get('x-results-for-x'),
								[totalResultsCount, searchBarTerm]
							)}
						</span>
					</span>
				</ManagementToolbar.ResultsBarItem>

				<ManagementToolbar.ResultsBarItem>
					<ClayButton
						className="component-link tbar-link"
						displayType="unstyled"
						onClick={onClear}
						small
						title={Liferay.Language.get('clear')}
					>
						{Liferay.Language.get('clear')}
					</ClayButton>
				</ManagementToolbar.ResultsBarItem>
			</ManagementToolbar.ResultsBar>
		);
	}
}

export default FilterDisplay;

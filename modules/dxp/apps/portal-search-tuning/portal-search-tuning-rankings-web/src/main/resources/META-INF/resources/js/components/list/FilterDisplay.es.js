/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

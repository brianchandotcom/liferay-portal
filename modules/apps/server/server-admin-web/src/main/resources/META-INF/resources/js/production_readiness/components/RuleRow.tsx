/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

import {RuleResult} from '../types';
import IgnoreButton from './IgnoreButton';
import StatusIcon from './StatusIcon';
import StatusLabel from './StatusLabel';

interface Props {
	onToggleIgnore: (result: RuleResult) => void;
	result: RuleResult;
	toggling: boolean;
}

const RuleRow: React.FC<Props> = ({onToggleIgnore, result, toggling}) => {
	const failed = result.status === 'FAIL' && !result.ignored;

	return (
		<li
			className={classNames(
				'align-items-start d-flex list-group-item py-3',
				{
					'text-muted': result.ignored,
				}
			)}
			data-rule-key={result.ruleKey}
		>
			<StatusIcon ignored={result.ignored} status={result.status} />

			<div className="flex-grow-1 ml-3">
				<div className="font-weight-semi-bold">{result.name}</div>

				{failed && (
					<>
						<div className="mb-2">{result.message}</div>

						{result.currentValue && (
							<div className="font-italic small text-danger">
								{`${Liferay.Language.get('current-value')}: ${
									result.currentValue
								}`}
							</div>
						)}

						{result.recommendedValue && (
							<div className="font-italic small text-danger">
								{`${Liferay.Language.get('recommended-value')}: ${
									result.recommendedValue
								}`}
							</div>
						)}
					</>
				)}
			</div>

			<div className="align-items-center d-flex ml-3">
				<StatusLabel result={result} />

				<IgnoreButton
					disabled={toggling}
					ignored={result.ignored}
					onClick={() => onToggleIgnore(result)}
					ruleKey={result.ruleKey}
				/>
			</div>
		</li>
	);
};

export default RuleRow;

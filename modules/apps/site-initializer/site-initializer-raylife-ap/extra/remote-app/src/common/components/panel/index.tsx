/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import classNames from 'classnames';
import React from 'react';

type Props = {
	Description?: React.ReactNode;
	children: React.ReactNode;
	hasExpandedButton?: boolean;
	isPanelExpanded: boolean;
	setIsPanelExpanded: () => void;
};

const PanelComponent: React.FC<Props> = ({
	Description,
	children,
	hasExpandedButton,
	isPanelExpanded,
	setIsPanelExpanded,
}) => {
	const toggleShow = () => {
		setIsPanelExpanded();
	};

	const hasButtonLabel = isPanelExpanded ? 'Hide Detail' : 'View Detail';

	return (
		<div className="panel-container">
			<div
				className={classNames(
					'align-items-center d-flex justify-content-between layout-panel ml-auto py-2 px-3',
					{
						'blue-line-activites position-relative box-shadow':
							isPanelExpanded && !hasExpandedButton,
					}
				)}
				onClick={!hasExpandedButton ? toggleShow : undefined}
			>
				<div className="align-items-center d-flex font-weight-bold">
					{Description}
				</div>

				{hasExpandedButton && (
					<ClayButton
						className={classNames('text-nowrap ml-1', {
							'font-weight-bold': isPanelExpanded,
						})}
						displayType="link"
						onClick={toggleShow}
					>
						{hasButtonLabel}
					</ClayButton>
				)}
			</div>

			<div
				className={classNames('box pb-1', {
					'show-box': isPanelExpanded,
				})}
				onClick={!hasExpandedButton ? toggleShow : undefined}
			>
				{children}
			</div>
		</div>
	);
};

export default PanelComponent;

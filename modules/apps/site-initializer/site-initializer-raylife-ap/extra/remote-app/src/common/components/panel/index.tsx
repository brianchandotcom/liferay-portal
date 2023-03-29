/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

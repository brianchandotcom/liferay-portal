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
	Description?: () => JSX.Element;
	children: React.ReactNode;
	setShowPanel: () => void;
	showPanel: boolean;
};

const Panel: React.FC<Props> = ({
	Description,
	children,
	setShowPanel,
	showPanel,
}) => {
	const toggleShow = () => {
		setShowPanel();
	};

	const buttonLabel = showPanel ? 'Hide Detail' : 'View Detail';

	return (
		<>
			<div className="align-items-center d-flex justify-content-between layout-panel ml-auto w-75">
				<div className="align-items-center d-flex font-weight-bold">
					{Description && <Description />}
				</div>

				<div className="container-button-panel">
					<ClayButton
						className={classNames('', {
							'font-weight-bold': showPanel,
						})}
						displayType="link"
						onClick={toggleShow}
					>
						{buttonLabel}
					</ClayButton>
				</div>
			</div>

			<div
				className={classNames('box pb-1', {
					'show-box': showPanel,
				})}
			>
				{children}
			</div>
		</>
	);
};

export default Panel;

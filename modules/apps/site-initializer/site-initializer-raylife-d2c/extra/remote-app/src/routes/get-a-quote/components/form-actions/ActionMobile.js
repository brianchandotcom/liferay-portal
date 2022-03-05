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
import React from 'react';

export function ActionMobile({
	onClickSaveAndExit,
	onPrevious,
	onSaveDisabled,
	showSaveAndExit,
}) {
	return (
		<div className="d-flex justify-content-between mx-0 row">
			{onPrevious && (
				<ClayButton
					className="btn-borderless font-weight-bolder previous text-neutral-0 text-paragraph text-small-caps"
					displayType="style-neutral"
					onClick={onPrevious}
				>
					Previous
				</ClayButton>
			)}

			<div className="d-flex">
				{showSaveAndExit && (
					<ClayButton
						className="btn btn-ghost btn-inverted font-weight-bolder mr-3 save-exit text-neutral-0 text-paragraph text-small-caps"
						disabled={onSaveDisabled}
						displayType="style-neutral"
						onClick={onClickSaveAndExit}
					>
						Save & Exit
					</ClayButton>
				)}
			</div>
		</div>
	);
}

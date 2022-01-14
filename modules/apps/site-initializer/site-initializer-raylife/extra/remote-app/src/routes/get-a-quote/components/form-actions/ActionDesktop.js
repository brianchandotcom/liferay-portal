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
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

export function ActionDesktop({
	isMobileDevice,
	isValid = true,
	onClickSaveAndExit,
	onNext,
	onPrevious,
	onSaveDisabled,
	showSaveAndExit,
}) {
	return (
		<div
			className={classNames('d-flex justify-content-between', {
				'mt-5': !isMobileDevice,
			})}
		>
			{!isMobileDevice && onPrevious && (
				<ClayButton
					className="btn-borderless btn-style-neutral font-weight-bolder previous text-paragraph text-small-caps"
					displayType="null"
					onClick={onPrevious}
				>
					Previous
				</ClayButton>
			)}

			<div className={classNames('d-flex', {'w-100': isMobileDevice})}>
				{!isMobileDevice && showSaveAndExit && (
					<ClayButton
						className="font-weight-bolder mr-3 save-exit text-paragraph text-small-caps"
						disabled={onSaveDisabled}
						displayType="secondary"
						onClick={onClickSaveAndExit}
					>
						Save & Exit
					</ClayButton>
				)}

				{onNext && (
					<ClayButton
						className={classNames(
							'btn-solid btn-style-secondary continue font-weight-bolder text-paragraph text-small-caps',
							{'w-100': isMobileDevice}
						)}
						disabled={!isValid}
						onClick={onNext}
					>
						Continue
						<span className="inline-item inline-item-before ml-1">
							<ClayIcon symbol="angle-right" />
						</span>
					</ClayButton>
				)}
			</div>
		</div>
	);
}

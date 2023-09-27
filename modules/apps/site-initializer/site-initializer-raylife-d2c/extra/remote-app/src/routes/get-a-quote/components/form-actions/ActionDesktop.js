/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	showContinueButton,
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

				{onNext && showContinueButton && (
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

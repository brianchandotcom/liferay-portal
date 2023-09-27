/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import {ClayRadio} from '@clayui/form';
import classNames from 'classnames';
import {forwardRef} from 'react';

export const Radio = forwardRef(
	(
		{
			description,
			hasCustomAlert,
			isActivationKeyAvailable,
			label,
			name,
			onChange,
			renderActions,
			selected = false,
			sideLabel,
			subtitle,
			value,
			...props
		},
		ref
	) => {
		return (
			<>
				<ClayCard
					className={classNames(
						'align-items-baseline cp-radio-card d-flex flex-row mb-3 py-3 px-3 rounded user-select-auto',
						{
							'bg-brand-primary-lighten-5 border-primary text-brand-primary': selected,
							'card-outlined': !selected,
						}
					)}
					disabled={!isActivationKeyAvailable}
					onClick={() => {
						if (isActivationKeyAvailable) {
							onChange({
								target: {
									value,
								},
							});
						}
					}}
					selected={selected}
				>
					<ClayRadio
						{...props}
						checked={selected}
						disabled={!isActivationKeyAvailable}
						inline={true}
						name={name}
						onChange={() =>
							onChange({
								target: {
									value,
								},
							})
						}
						ref={ref}
						value={value}
					/>

					<div className="d-flex flex-wrap p-0">
						<div className="align-items-start align-self-start col-12 d-flex flex-wrap justify-content-between mb-0 p-0">
							<label
								className={classNames(
									'd-flex cp-radio-card-label flex-wrap flex-lg-nowrap font-weight-bolder text-paragraph-lg p-0',
									{
										'cp-clay-card-disabled': !isActivationKeyAvailable,
										'text-brand-primary': selected,
									}
								)}
								htmlFor={name}
							>
								<p className="mb-0 p-0">{label} &nbsp;</p>

								<small className="font-weight-normal justify-content-md-end mb-1 ml-0 p-0 text-neutral-10 text-paragraph-lg">
									{sideLabel}
								</small>
							</label>

							<div className="d-flex justify-content-end p-0">
								{renderActions}
							</div>
						</div>

						<p
							className={classNames(
								'col-12 mb-0 p-0 text-success text-paragraph-sm',
								{
									'cp-clay-card-disabled': !isActivationKeyAvailable,
									'text-danger': !isActivationKeyAvailable,
								}
							)}
						>
							{description}
						</p>

						<p
							className={classNames(
								'col-12 p-0 text-neutral-8 text-paragraph-sm',
								{
									'cp-clay-card-disabled': !isActivationKeyAvailable,
								}
							)}
						>
							{subtitle}
						</p>
					</div>
				</ClayCard>
				{hasCustomAlert && hasCustomAlert}
			</>
		);
	}
);

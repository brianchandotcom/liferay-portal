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

import ClayCard from '@clayui/card';
import {ClayRadio} from '@clayui/form';
import classNames from 'classnames';
import {forwardRef} from 'react';

export const Radio = forwardRef(
	(
		{
			description,
			label,
			name,
			onChange,
			renderActions,
			selected = false,
			sideLabel,
			value,
			...props
		},
		ref
	) => {
		return (
			<ClayCard
				className={classNames(
					'align-items-baseline d-flex flex-row mb-3 py-2 py-sm-3 px-sm-3 px-2 radio-card rounded user-select-auto',
					{
						'bg-brand-primary-lighten-5 border border-primary text-brand-primary': selected,
						'card-outlined': !selected,
					}
				)}
				onClick={() =>
					onChange({
						target: {
							value,
						},
					})
				}
				selected={selected}
			>
				<ClayRadio
					{...props}
					checked={selected}
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

				<div className="d-flex flex-wrap ml-2 p-0">
					<div className="align-items-start align-self-start col-12 d-flex flex-wrap justify-content-between mb-0 p-0">
						<label
							className={classNames(
								'd-flex flex-wrap radio-card-label flex-lg-nowrap font-weight-bolder text-paragraph-lg col-8 col-sm-8 col-md-10 col-lg-auto p-0 ',
								{
									'text-brand-primary': selected,
								}
							)}
							htmlFor={name}
						>
							<p className="col-12 col-lg-auto col-md-auto col-sm-12 p-0">
								{label} &nbsp;
							</p>

							<small className="col-12 col-lg-auto col-md-5 col-sm-12 font-weight-normal justify-content-md-end mb-1 ml-0 p-0 text-neutral-10 text-paragraph-lg">
								{sideLabel}
							</small>
						</label>

						<div className="col-4 col-lg-auto col-md-auto col-sm-4 d-flex justify-content-end p-0">
							{renderActions}
						</div>
					</div>

					<p className="col-12 p-0 text-neutral-8 text-paragraph-sm">
						{description}
					</p>
				</div>
			</ClayCard>
		);
	}
);

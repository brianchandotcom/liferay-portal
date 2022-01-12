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

import ClayCard from '@clayui/card';
import {ClayRadio} from '@clayui/form';
import classNames from 'classnames';
import React from 'react';

export function Radio({
	description,
	label,
	name,
	onChange,
	renderActions,
	selected = false,
	sideLabel,
	value,
	...props
}) {
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
				value={value}
			/>

			<div className="d-flex flex-wrap p-0">
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

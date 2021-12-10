import ClayCard from '@clayui/card';
import {ClayRadio} from '@clayui/form';
import React from 'react';

export function Radio({
	description,
	label,
	name,
	renderActions,
	selected = false,
	sideLabel,
	value,
	...props
}) {
	return (
		<ClayCard
			className={`align-items-baseline flex-row d-flex mb-3 pb-3 pr-3 pl-3
			pt-3 radio-card rounded user-select-auto  ${
				selected &&
				'bg-brand-primary-lighten-5 border border-primary text-brand-primary'
			}`}
			onClick={() =>
				props.onChange({
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
					props.onChange({
						target: {
							value,
						},
					})
				}
				value={value}
			/>

			<div className="content d-flex flex-column flex-grow-1 flex-shrink-1">
				<div className="align-items-center d-flex justify-content-between">
					<label
						className={`font-weight-bolder text-paragraph-lg ${
							selected && 'text-brand-primary'
						}`}
						htmlFor={name}
					>
						{label}

						<small className="font-weight-normal ml-0 text-paragraph-lg">
							{sideLabel}
						</small>
					</label>

					{renderActions}
				</div>

				<p className="text-neutral-8 text-paragraph-sm">
					{description}
				</p>
			</div>
		</ClayCard>
	);
}

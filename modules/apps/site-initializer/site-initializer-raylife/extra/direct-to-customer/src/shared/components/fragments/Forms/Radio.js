import React from 'react';

export const Radio = ({
	description,
	label,
	name,
	renderActions,
	selected = false,
	sideLabel,
	value,
	...props
}) => (
	<div
		className={`radio-card ${selected && 'selected'}`}
		onClick={() =>
			props.onChange({
				target: {
					value,
				},
			})
		}
	>
		<input
			{...props}
			checked={selected}
			className="radio"
			name={name}
			onChange={() =>
				props.onChange({
					target: {
						value,
					},
				})
			}
			type="radio"
			value={value}
		/>

		<div className="content">
			<div className="content-header">
				<label htmlFor={name}>
					{label}
					<small>{sideLabel}</small>
				</label>
				{renderActions}
			</div>

			<p>{description}</p>
		</div>
	</div>
);

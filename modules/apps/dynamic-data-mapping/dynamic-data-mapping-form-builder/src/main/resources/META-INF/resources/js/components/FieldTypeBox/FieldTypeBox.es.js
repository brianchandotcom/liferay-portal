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

const FieldTypeBox = ({fieldType, spritemap}) => {
	const {description, icon, label, name} = fieldType;

	return (
		<div
			class="ddm-drag-item list-group-item list-group-item-flex"
			data-field-type-name={name}
			key={`fieldType_${name}`}
			ref={`fieldType_${name}`}
		>
			<div class="autofit-col">
				<span class="sticker sticker-secondary">
					<span class="inline-item">
						<svg
							aria-hidden="true"
							class={`lexicon-icon lexicon-icon-${icon}`}
						>
							<use xlink:href={`${spritemap}#${icon}`} />
						</svg>
					</span>
				</span>
			</div>
			<div class="autofit-col autofit-col-expand">
				<h4 class="list-group-title text-truncate">
					<span>{label}</span>
				</h4>
				{description && (
					<p class="list-group-subtitle text-truncate">
						{description}
					</p>
				)}
			</div>
		</div>
	);
};

export default FieldTypeBox;

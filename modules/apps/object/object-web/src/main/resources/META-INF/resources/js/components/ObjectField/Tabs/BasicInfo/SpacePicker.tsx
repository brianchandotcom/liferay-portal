/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {FieldBase} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {forwardRef, useEffect, useState} from 'react';

import SpaceSticker from './SpaceSticker';

interface SpacePickerProps {
	error?: string;
	onChange: (value: string) => void;
	value?: string;
}

interface SpaceTriggerProps {
	children?: React.ReactNode;
	space?: Space;
}

const SpaceTrigger = forwardRef<HTMLDivElement, SpaceTriggerProps>(
	({children, space, ...otherProps}, ref) => {
		return (
			<div
				ref={ref}
				{...otherProps}
				className="form-control"
				tabIndex={0}
			>
				<div className="align-items-center d-flex justify-content-between">
					<div className="align-items-center c-gap-2 d-flex">
						{space && (
							<div aria-hidden="true">
								<SpaceSticker
									displayType={space.settings?.logoColor}
									name={space.name}
									size="sm"
								/>
							</div>
						)}

						<span className="text-truncate">{children}</span>
					</div>

					<ClayIcon
						className="text-secondary"
						symbol="caret-double"
					/>
				</div>
			</div>
		);
	}
);

export default function SpacePicker({
	error,
	onChange,
	value,
}: SpacePickerProps) {
	const [spaces, setSpaces] = useState<Space[]>([]);
	const [loading, setLoading] = useState(false);

	const selectedSpace = spaces.find(
		(space) => String(space.externalReferenceCode) === value
	);

	useEffect(() => {
		setLoading(true);

		fetch(
			"/o/headless-asset-library/v1.0/asset-libraries?filter=type eq 'Space'"
		)
			.then((response) => response.json())
			.then((data) => {
				setSpaces(data.items ?? []);
			})
			.finally(() => setLoading(false));
	}, []);

	return (
		<FieldBase
			errorMessage={error}
			id="spaceStoragePicker"
			label={Liferay.Language.get('cms-space')}
			required
		>
			<Picker
				as={SpaceTrigger}
				id="spaceStoragePicker"
				items={spaces}
				loading={loading}
				onSelectionChange={(key) => onChange(String(key))}
				placeholder={Liferay.Language.get('choose-a-space')}
				selectedKey={value}
				space={selectedSpace}
			>
				{(space) => (
					<Option
						key={space.externalReferenceCode}
						textValue={space.name}
					>
						<div className="align-items-center c-gap-2 d-flex">
							<div aria-hidden="true">
								<SpaceSticker
									displayType={space.settings?.logoColor}
									name={space.name}
									size="sm"
								/>
							</div>

							<span className="text-truncate">{space.name}</span>
						</div>
					</Option>
				)}
			</Picker>
		</FieldBase>
	);
}

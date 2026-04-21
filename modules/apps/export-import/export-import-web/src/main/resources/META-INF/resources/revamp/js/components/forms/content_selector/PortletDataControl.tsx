/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React from 'react';

import {PortletDataHandlerControl} from '../../../utils/mockPortletDataHandlerSections';
import PortletDataControlChoice from './PortletDataControlChoice';

export type HandlerSelection =
	| {
			[key: string]: HandlerSelection;
	  }
	| string
	| true;

export function isSelected(
	value: HandlerSelection | undefined,
	entry: PortletDataHandlerControl
): boolean {
	if (!value) {
		return false;
	}

	if (entry.type === 'choice') {
		return true;
	}

	if (!entry.controls?.length || typeof value !== 'object') {
		return true;
	}

	return entry.controls.every((control) =>
		isSelected(value[control.name], control)
	);
}

export function getInitialSelection(
	entry: PortletDataHandlerControl
): HandlerSelection {
	if (entry.type === 'choice') {
		return entry.choices[0].name;
	}

	if (!entry.controls?.length) {
		return true;
	}

	const selection: Record<string, HandlerSelection> = {};

	entry.controls.forEach((control) => {
		selection[control.name] = getInitialSelection(control);
	});

	return selection;
}

interface PortletDataControlProps {
	className?: string;
	control: PortletDataHandlerControl;
	level?: number;
	onChange: (value: HandlerSelection | undefined) => void;
	value: HandlerSelection | undefined;
}

export default function PortletDataControl({
	className = 'mb-2',
	control,
	level = 0,
	onChange,
	value,
}: PortletDataControlProps) {
	if (control.type === 'choice') {
		return (
			<PortletDataControlChoice
				className="mt-2 pl-2"
				control={control}
				onChange={(_, newValue) => onChange(newValue)}
				value={typeof value === 'string' ? value : ''}
			/>
		);
	}

	const isPresent = !!value;
	const selected = isSelected(value, control);
	const currentSelection = typeof value === 'object' ? value : {};

	return (
		<ClayLayout.ContentRow className={className}>
			<ClayLayout.ContentCol className="pr-2" expand={false}>
				<ClayCheckbox
					checked={selected}
					indeterminate={isPresent && !selected}
					onChange={() =>
						onChange(
							selected ? undefined : getInitialSelection(control)
						)
					}
				/>
			</ClayLayout.ContentCol>

			<ClayLayout.ContentCol expand>
				<div className="align-items-center d-flex justify-content-between">
					<span
						className={`small ${level === 0 ? 'font-weight-semi-bold' : ''}`}
					>
						{control.label}
					</span>
				</div>

				{control.controls &&
					control.controls.map((nestedControl) => (
						<PortletDataControl
							className="mt-2"
							control={nestedControl}
							key={nestedControl.name}
							level={level + 1}
							onChange={(controlValue) => {
								const {
									[nestedControl.name]: _,
									...newSelection
								} = currentSelection;

								if (controlValue) {
									newSelection[nestedControl.name] =
										controlValue;
								}

								onChange(
									Object.keys(newSelection).length
										? (newSelection as HandlerSelection)
										: undefined
								);
							}}
							value={currentSelection[nestedControl.name]}
						/>
					))}
			</ClayLayout.ContentCol>
		</ClayLayout.ContentRow>
	);
}

/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React from 'react';

import {PreviewPortletDataHandlerControl} from '../../../types/portletDataHandler';
import {
	HandlerSelection,
	getInitialSelection,
	isSelected,
	updateSelection,
} from '../../../utils/contentSelection';
import PortletDataControlChoice from './PortletDataControlChoice';
import SectionTags from './SectionTags';

interface PortletDataControlProps {
	className?: string;
	control: PreviewPortletDataHandlerControl;
	level?: number;
	onChange: (value: HandlerSelection | undefined) => void;
	showDeletions?: boolean;
	value: HandlerSelection | undefined;
}

export default function PortletDataControl({
	className = 'mb-2',
	control,
	level = 0,
	onChange,
	showDeletions,
	value,
}: PortletDataControlProps) {
	if (control.type === 'Choice') {
		return (
			<PortletDataControlChoice
				className="mt-2 pl-2"
				control={control}
				onChange={onChange}
				value={typeof value === 'string' ? value : ''}
			/>
		);
	}

	const selected = isSelected(value, control);
	const currentSelection = typeof value === 'object' ? value : {};

	return (
		<ClayLayout.ContentRow className={className}>
			<ClayLayout.ContentCol className="pr-2" expand={false}>
				<ClayCheckbox
					checked={selected}
					indeterminate={!!value && !selected}
					onChange={() =>
						onChange(
							selected ? undefined : getInitialSelection(control)
						)
					}
				/>
			</ClayLayout.ContentCol>

			<ClayLayout.ContentCol expand>
				<div className="align-items-center d-flex">
					<span
						className={`small ${level === 0 ? 'font-weight-semi-bold' : ''}`}
					>
						{control.label}
					</span>

					{control.type === 'Boolean' && (
						<SectionTags
							additionCount={control.additionCount}
							deletionCount={
								showDeletions
									? control.deletionCount
									: undefined
							}
						/>
					)}
				</div>

				{control.previewPortletDataHandlerControls?.map(
					(nestedControl) =>
						nestedControl.type === 'Choice' && !selected ? null : (
							<PortletDataControl
								className="mt-2"
								control={nestedControl}
								key={nestedControl.name}
								level={level + 1}
								onChange={(controlValue) =>
									onChange(
										updateSelection(
											currentSelection,
											nestedControl.name,
											controlValue
										)
									)
								}
								showDeletions={showDeletions}
								value={currentSelection[nestedControl.name]}
							/>
						)
				)}
			</ClayLayout.ContentCol>
		</ClayLayout.ContentRow>
	);
}

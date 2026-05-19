/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import React, {useId, useState} from 'react';

import {PageTreeModalConfiguration} from '../../../pages/export/components/PageTreeModal';
import {PreviewPortletDataHandlerControl} from '../../../types/portletDataHandler';
import {
	HandlerSelection,
	LAYOUT_SET_LAYOUTS_PORTLET_DATA_KEY,
	getInitialSelection,
	isSelected,
	updateSelection,
} from '../../../utils/contentSelection';
import LayoutSetControl from './LayoutSetControl';
import PortletDataControlChoice from './PortletDataControlChoice';
import SectionTags from './SectionTags';

interface PortletDataControlProps {
	className?: string;
	control: PreviewPortletDataHandlerControl;
	level?: number;
	onChange: (value: HandlerSelection | undefined) => void;
	pageTreeModalConfiguration?: PageTreeModalConfiguration;
	showDeletions?: boolean;
	value: HandlerSelection | undefined;
}

export default function PortletDataControl({
	className,
	control,
	level = 0,
	onChange,
	pageTreeModalConfiguration,
	showDeletions,
	value,
}: PortletDataControlProps) {
	const [expanded, setExpanded] = useState(false);
	const bodyId = useId();
	const checkboxId = useId();

	if (
		control.name === LAYOUT_SET_LAYOUTS_PORTLET_DATA_KEY &&
		pageTreeModalConfiguration
	) {
		return (
			<LayoutSetControl
				className={className}
				label={control.label}
				onChange={onChange}
				pageTreeModalConfiguration={pageTreeModalConfiguration}
				value={value}
			/>
		);
	}

	if (control.type === 'Choice') {
		return (
			<PortletDataControlChoice
				className="mt-3"
				control={control}
				onChange={onChange}
				value={typeof value === 'string' ? value : ''}
			/>
		);
	}

	const selected = isSelected(value, control);
	const currentSelection: Record<string, HandlerSelection> =
		typeof value === 'object' ? value : {};

	const nestedControls = control.previewPortletDataHandlerControls ?? [];

	const expandable = !!nestedControls.length;

	const childrenSummary = nestedControls
		.filter((nested) => currentSelection[nested.name] !== undefined)
		.map((nested) => nested.label)
		.join(', ');

	const collapsible = expandable && level === 0;

	return (
		<div className={level === 0 ? 'p-3' : ''}>
			<ClayLayout.ContentRow className="align-items-center">
				<ClayLayout.ContentCol className="pr-2" expand={false}>
					<ClayCheckbox
						checked={selected}
						id={checkboxId}
						indeterminate={!!value && !selected}
						onChange={() =>
							onChange(
								selected
									? undefined
									: getInitialSelection(control)
							)
						}
					/>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand>
					<span className="align-items-center d-inline-flex">
						<label
							className={`cursor-pointer mb-0 ${
								level === 0 ? 'font-weight-bold' : 'small'
							}`}
							htmlFor={checkboxId}
						>
							{control.label}
						</label>

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
					</span>
				</ClayLayout.ContentCol>

				{collapsible && (
					<ClayLayout.ContentCol expand={false}>
						<ClayButton
							aria-controls={bodyId}
							aria-expanded={expanded}
							displayType="link"
							onClick={() => setExpanded((prev) => !prev)}
							small
						>
							{expanded
								? Liferay.Language.get('hide-all')
								: Liferay.Language.get('show-all')}

							<ClayIcon
								className="ml-1"
								symbol={expanded ? 'angle-down' : 'angle-right'}
							/>
						</ClayButton>
					</ClayLayout.ContentCol>
				)}
			</ClayLayout.ContentRow>

			{collapsible && (
				<small className="d-block pl-4 text-secondary">
					{childrenSummary || <>&nbsp;</>}
				</small>
			)}

			{expandable && (!collapsible || expanded) && (
				<div
					className={`c-gap-1 d-flex flex-column pl-4${collapsible ? ' mt-2' : ''}`}
					id={bodyId}
				>
					{nestedControls.map((nestedControl) => (
						<PortletDataControl
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
							pageTreeModalConfiguration={
								pageTreeModalConfiguration
							}
							showDeletions={showDeletions}
							value={currentSelection[nestedControl.name]}
						/>
					))}
				</div>
			)}
		</div>
	);
}

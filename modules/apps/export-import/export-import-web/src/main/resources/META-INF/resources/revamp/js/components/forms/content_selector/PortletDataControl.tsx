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
				className="pl-2"
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

	return (
		<div
			className={`${level === 0 ? 'p-3' : ''}${expandable ? ' cursor-pointer' : ''}`}
			onClick={
				expandable ? () => setExpanded((prev) => !prev) : undefined
			}
		>
			<ClayLayout.ContentRow>
				<ClayLayout.ContentCol className="pr-2" expand={false}>
					<div onClick={(event) => event.stopPropagation()}>
						<ClayCheckbox
							checked={selected}
							indeterminate={!!value && !selected}
							onChange={() =>
								onChange(
									selected
										? undefined
										: getInitialSelection(control)
								)
							}
						/>
					</div>
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

					{expandable && childrenSummary && (
						<small className="d-block mt-2 text-secondary">
							{childrenSummary}
						</small>
					)}
				</ClayLayout.ContentCol>

				{expandable && (
					<ClayLayout.ContentCol expand={false}>
						<ClayButton
							aria-controls={bodyId}
							aria-expanded={expanded}
							displayType="link"
							onClick={(event) => {
								event.stopPropagation();
								setExpanded((prev) => !prev);
							}}
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

			{expandable && expanded && (
				<div
					className="c-gap-1 d-flex flex-column mt-2"
					id={bodyId}
					onClick={(event) => event.stopPropagation()}
					style={{paddingLeft: '44px'}}
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

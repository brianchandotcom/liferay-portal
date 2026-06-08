/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classnames from 'classnames';
import React, {ReactNode, useId, useState} from 'react';

import ControlRow from './ControlRow';

export default function CollapsibleGroup({
	bodyClassName,
	bodyVisibleClassName,
	checkboxId,
	children,
	description,
	disclosure,
	indeterminate,
	label,
	labelClassName,
	onToggle,
	selected,
	summary,
	tags,
}: {
	bodyClassName?: string;
	bodyVisibleClassName?: string;
	checkboxId: string;
	children: ReactNode;
	description?: string;
	disclosure: (props: {
		'aria-controls': string;
		'aria-expanded': boolean;
		'expanded': boolean;
		'onClick': () => void;
	}) => ReactNode;
	indeterminate: boolean;
	label: string;
	labelClassName?: string;
	onToggle: () => void;
	selected: boolean;
	summary?: string;
	tags?: ReactNode;
}) {
	const [expanded, setExpanded] = useState(false);
	const bodyId = useId();

	return (
		<>
			<ControlRow
				checkboxId={checkboxId}
				description={description}
				disclosure={disclosure({
					'aria-controls': bodyId,
					'aria-expanded': expanded,
					expanded,
					'onClick': () => setExpanded((prev) => !prev),
				})}
				indeterminate={indeterminate}
				label={label}
				labelClassName={labelClassName}
				onToggle={onToggle}
				selected={selected}
				tags={tags}
			/>

			<small className="d-block pl-4 text-secondary text-truncate">
				{summary || Liferay.Language.get('nothing-selected')}
			</small>

			<div
				className={classnames(
					bodyClassName,
					expanded ? bodyVisibleClassName : 'd-none'
				)}
				hidden={!expanded}
				id={bodyId}
			>
				{children}
			</div>
		</>
	);
}

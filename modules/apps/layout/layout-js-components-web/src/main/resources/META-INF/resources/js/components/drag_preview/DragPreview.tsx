/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React, {useMemo, useRef} from 'react';
import {useDragLayer} from 'react-dnd';

import './DragPreview.scss';

interface DragItem {
	icon?: string;
	name?: string;
}

interface Props<T> {
	getIcon: (item: T) => string;
	getLabel: (item: T) => string;
}

const getItemStyles = (
	currentOffset: {x: number; y: number} | null,
	ref: React.RefObject<HTMLDivElement>,
	rtl: boolean
) => {
	if (!currentOffset || !ref.current) {
		return {
			display: 'none',
		};
	}

	const rect = ref.current.getBoundingClientRect();
	const x = rtl
		? currentOffset.x + rect.width * 0.5 - window.innerWidth
		: currentOffset.x - rect.width * 0.5;
	const y = currentOffset.y - rect.height * 0.5;

	const transform = `translate(${x}px, ${y}px)`;

	return {
		WebkitTransform: transform,
		transform,
	};
};

function getDefaultLabel(item: DragItem) {
	if (item?.name) {
		return item.name;
	}

	return Liferay.Language.get('element');
}

export default function DragPreview<T extends DragItem>({
	getIcon,
	getLabel = getDefaultLabel,
}: Props<T>) {
	const ref = useRef<HTMLDivElement>(null);

	const {currentOffset, isDragging, item} = useDragLayer((monitor) => ({
		currentOffset: monitor.getClientOffset(),
		isDragging: monitor.isDragging(),
		item: monitor.getItem(),
	}));

	const icon = useMemo(() => {
		if (getIcon) {
			return item?.icon ?? getIcon(item);
		}
	}, [getIcon, item]);

	const label = useMemo(() => getLabel(item), [getLabel, item]);

	if (!isDragging) {
		return null;
	}

	const rtl =
		Liferay.Language.direction[Liferay.ThemeDisplay.getLanguageId()] ===
		'rtl';

	const dir =
		Liferay.Language.direction[Liferay.ThemeDisplay.getLanguageId()];

	return (
		<div className="cadmin">
			<div className="drag-preview position-fixed">
				<div
					className="align-items-center d-flex drag-preview__content p-2 position-absolute text-2"
					dir={dir}
					ref={ref}
					style={getItemStyles(currentOffset, ref, rtl)}
				>
					{icon && (
						<ClayIcon
							className="flex-shrink-0 mr-3 mt-0"
							symbol={icon}
						/>
					)}

					<span className="text-truncate">{label}</span>
				</div>
			</div>
		</div>
	);
}

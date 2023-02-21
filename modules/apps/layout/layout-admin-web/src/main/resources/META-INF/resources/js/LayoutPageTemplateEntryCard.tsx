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

import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import {KeyboardEvent, default as React} from 'react';

interface IProps {
	addLayoutURL: string;
	portletNamespace: string;
	previewURL: string;
	subtitle: string;
	title: string;
}

export default function LayoutPageTemplateEntryCard({
	addLayoutURL,
	subtitle,
	title,
}: IProps) {
	const onClick = () => {
		Liferay.Util.openModal({
			disableAutoClose: true,
			height: '60vh',
			id: 'addLayoutDialog',
			size: 'md',
			title: Liferay.Language.get('add-page'),
			url: addLayoutURL,
		});
	};

	const onKeyDown = (event: KeyboardEvent) => {
		if (event.key === 'Enter' || event.key === 'Space') {
			event.preventDefault();
			event.stopPropagation();
			onClick();
		}
	};

	return (
		<div
			className="btn card card-type-asset file-card p-0 position-relative"
			onClick={onClick}
			onKeyDown={onKeyDown}
			role="button"
			tabIndex={0}
		>
			<ClayCard.AspectRatio containerAspectRatio="16/9">
				<div className="aspect-ratio-item aspect-ratio-item-center-middle card-type-asset-icon">
					<ClayIcon symbol="page" />
				</div>
			</ClayCard.AspectRatio>

			<ClayCard.Body className="text-left">
				<ClayCard.Row className="c-gap-2">
					<div className="autofit-col autofit-col-expand autofit-col-shrink">
						<ClayCard.Description
							className="mb-0"
							displayType="title"
						>
							{title}
						</ClayCard.Description>

						<ClayCard.Description displayType="subtitle">
							{subtitle}
						</ClayCard.Description>
					</div>
				</ClayCard.Row>
			</ClayCard.Body>
		</div>
	);
}

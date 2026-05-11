/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

function FileTypeSticker({extension}) {
	const symbol =
		{
			doc: 'document-text',
			docx: 'document-text',
			gif: 'document-image',
			jpeg: 'document-image',
			jpg: 'document-image',
			pdf: 'document-pdf',
			png: 'document-image',
			xls: 'document-table',
			xlsx: 'document-table',
		}[(extension || '').toLowerCase()] || 'document-default';

	return (
		<span className="bg-transparent mr-2 sticker sticker-md">
			<span className="inline-item">
				<ClayIcon symbol={symbol} />
			</span>
		</span>
	);
}

export default function CommerceOrderAttachmentTitleDataRenderer({
	actions,
	itemData,
	openSidePanel,
	value,
}) {
	const editAction = (actions || []).find(
		(action) => action?.data?.id === 'edit'
	);

	const hasUpdatePermission =
		Boolean(itemData?.actions?.update) && editAction;

	const content = (
		<>
			<FileTypeSticker extension={itemData?.extension} />
			{value}
		</>
	);

	if (!hasUpdatePermission) {
		return <span className="table-list-title">{content}</span>;
	}

	const href = (editAction.href || '#').replace(
		encodeURIComponent('{id}'),
		itemData?.id
	);

	return (
		<a
			className="table-list-title"
			data-senna-off
			href={href}
			onClick={(event) => {
				if (editAction.target === 'sidePanel') {
					event.preventDefault();

					openSidePanel({
						size: editAction.data?.size || 'lg',
						title: editAction.data?.title,
						url: href,
					});
				}
			}}
		>
			{content}
		</a>
	);
}

CommerceOrderAttachmentTitleDataRenderer.propTypes = {
	actions: PropTypes.array,
	itemData: PropTypes.object,
	openSidePanel: PropTypes.func,
	value: PropTypes.string,
};

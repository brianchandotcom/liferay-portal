/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React from 'react';

import {Attachment} from './AttachmentBase';

interface FileProps {
	attachment: Attachment | null;
	loading?: boolean;
	onDelete: () => void;
	readOnly: boolean;
}

export default function FileContainer({
	attachment,
	loading,
	onDelete,
	readOnly,
}: FileProps) {
	if (loading) {
		return (
			<ClayLoadingIndicator className="lfr-objects__attachment-loading" />
		);
	}
	else if (attachment) {
		return (
			<>
				<div className="lfr-objects__attachment-title">
					<ClayButton
						displayType="unstyled"
						onClick={() => {
							window.open(attachment.contentURL, '_blank');
						}}
					>
						{attachment.title}
					</ClayButton>

					<a
						className="lfr-objects__attachment-download"
						href={attachment.contentURL}
					>
						<ClayIcon symbol="download" />
					</a>

					{!readOnly && (
						<>
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get('delete')}
								borderless
								displayType="secondary"
								monospaced
								onClick={() => onDelete()}
								symbol="times-circle-full"
								title={Liferay.Language.get('delete')}
							/>
						</>
					)}
				</div>
			</>
		);
	}

	return null;
}

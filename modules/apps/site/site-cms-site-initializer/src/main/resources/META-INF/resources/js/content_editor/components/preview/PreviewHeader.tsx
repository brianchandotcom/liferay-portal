/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {PopoverTooltip} from '@liferay/layout-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

export default function PreviewHeader({
	onClosePreview,
	title,
	titleId,
}: {
	onClosePreview: () => void;
	title: string;
	titleId: string;
}) {
	return (
		<div className="border-bottom d-flex flex-row-reverse justify-content-between p-3">
			<ClayButtonWithIcon
				aria-label={sub(
					Liferay.Language.get('close-x'),
					Liferay.Language.get('preview')
				)}
				borderless
				displayType="secondary"
				monospaced
				onClick={onClosePreview}
				size="sm"
				symbol="times"
			/>

			<div className="d-flex">
				<span className="font-weight-bold text-6" id={titleId}>
					{sub(Liferay.Language.get('x-preview'), title)}
				</span>

				<PopoverTooltip
					alignPosition="bottom"
					content={Liferay.Language.get(
						'save-as-draft-or-publish-content-to-see-your-latest-changes-in-the-preview'
					)}
					header={Liferay.Language.get('preview-help')}
					trigger={
						<ClayButtonWithIcon
							aria-description={Liferay.Language.get(
								'save-as-draft-or-publish-content-to-see-your-latest-changes-in-the-preview'
							)}
							aria-label={Liferay.Language.get('preview-help')}
							borderless
							className="ml-1 text-secondary"
							displayType="unstyled"
							monospaced
							size="sm"
							symbol="question-circle"
						/>
					}
				/>
			</div>
		</div>
	);
}

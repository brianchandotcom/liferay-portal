/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import {useIsMobileDevice} from '@clayui/shared';
import React, {useState} from 'react';

import {PreviewSelectorContent} from './PreviewSelectorContent';

export default function PreviewSelector() {
	const isMobile = useIsMobileDevice();
	const [active, setActive] = useState(false);

	if (isMobile) {
		return (
			<ClayDropDown
				active={active}
				alignmentPosition={Align.BottomLeft}
				closeOnClick={false}
				menuElementAttrs={{
					containerProps: {
						className: 'cadmin',
					},
				}}
				onActiveChange={setActive}
				trigger={
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('preview')}
						displayType="secondary"
						size="sm"
						symbol="simulation-menu"
						title={Liferay.Language.get('preview')}
					/>
				}
			>
				<div className="p-3 style-book-editor__preview-selector-container">
					<PreviewSelectorContent isMobile />
				</div>
			</ClayDropDown>
		);
	}

	return (
		<div className="d-flex flex-row">
			<PreviewSelectorContent />
		</div>
	);
}

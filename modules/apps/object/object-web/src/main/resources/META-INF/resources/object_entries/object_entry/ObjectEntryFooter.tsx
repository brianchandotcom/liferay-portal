/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {navigate} from 'frontend-js-web';
import React from 'react';

import {callWindowGlobalFunction} from '../../js/utils/callWindowGlobalFunction';

interface ObjectEntryFooterProps {
	backURL: string;
	portletNamespace: string;
	submitRef: string;
}

export default function ObjectEntryFooter({
	backURL,
	portletNamespace,
	submitRef,
}: ObjectEntryFooterProps) {
	return (
		<div className="sheet-footer sheet-footer-btn-block-sm-down">
			<div className="btn-group">
				<div className="btn-group-item">
					<ClayDropDown
						closeOnClick
						hasLeftSymbols
						trigger={
							<ClayButton displayType="primary" type="button">
								{Liferay.Language.get('publish')}
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							<ClayDropDown.Item
								id={portletNamespace + 'saveObjectEntryButton'}
								onClick={(event) => {
									event.preventDefault();

									callWindowGlobalFunction(submitRef);
								}}
								symbolLeft="arrow-right-full"
							>
								{Liferay.Language.get('publish')}
							</ClayDropDown.Item>

							<ClayDropDown.Item
								onClick={() => {}}
								symbolLeft="date-time"
							>
								{Liferay.Language.get('schedule-publication')}
							</ClayDropDown.Item>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</div>

				<div className="btn-group-item">
					<ClayButton
						displayType="secondary"
						onClick={() => {
							navigate(backURL);
						}}
						type="button"
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</div>
			</div>
		</div>
	);
}

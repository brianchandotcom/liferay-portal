/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import React, {useState} from 'react';

import './ObjectRelationshipInheritanceCheckbox.scss';

interface ObjectRelationshipInheritanceCheckbox {
	onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
	values: Partial<ObjectRelationship>;
}

export function ObjectRelationshipInheritanceCheckbox({
	onChange,
	values,
}: ObjectRelationshipInheritanceCheckbox) {
	const [showPopover, setShowPopover] = useState(false);

	return (
		<>
			<div className="form-group lfr__object-relationship-inheritance-container">
				<ClayCheckbox
					checked={!!values.edge}
					label={Liferay.Language.get('enable-inheritance')}
					onChange={onChange}
				/>

				<ClayPopover
					alignPosition="top"
					disableScroll
					header={Liferay.Language.get('inheritance')}
					onShowChange={setShowPopover}
					show={showPopover}
					trigger={
						<ClayIcon
							className="field-base-tooltip-icon"
							onBlur={() => setShowPopover(false)}
							onFocus={() => setShowPopover(true)}
							onMouseLeave={() => setShowPopover(false)}
							onMouseOver={() => setShowPopover(true)}
							symbol="question-circle-full"
						/>
					}
				>
					{Liferay.Language.get(
						'enable-inheritance-to-share-settings-between-related-data-models'
					)}
				</ClayPopover>
			</div>

			<ClayAlert
				displayType="info"
				title={`${Liferay.Language.get('info')}:`}
			>
				{Liferay.Language.get(
					'when-enabled,-permissions-are-inherited,-all-api-endpoints-are-grouped-under-the-parent,-and-the-relationship-field-is-always-mandatory'
				)}
			</ClayAlert>
		</>
	);
}

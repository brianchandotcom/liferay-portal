/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import React, {useState} from 'react';

import './ObjectRelationshipInheritanceCheckbox.scss';

interface ObjectRelationshipInheritanceCheckbox {
	onSubmit: (values?: Partial<ObjectRelationship>) => Promise<void>;
	setValues: (values: Partial<ObjectRelationship>) => void;
	values: Partial<ObjectRelationship>;
}

export function ObjectRelationshipInheritanceCheckbox({
	onSubmit,
	setValues,
	values,
}: ObjectRelationshipInheritanceCheckbox) {
	const [showPopover, setShowPopover] = useState(false);

	return (
		<>
			<div className="form-group lfr__object-relationship-inheritance-container">
				<ClayCheckbox
					checked={!!values.edge}
					label={Liferay.Language.get('enable-inheritance')}
					onChange={({target}) => {
						if (target.checked) {
							setValues({
								...values,
								edge: true,
							});
						}
						else {
							const parentWindow = Liferay.Util.getOpener();

							parentWindow.Liferay.fire(
								'openModalDisableInheritance',
								{
									handleDisable: async () => {
										await onSubmit({
											...values,
											edge: false,
										});
									},
								}
							);
						}
					}}
				/>

				<ClayPopover
					alignPosition="top-left"
					closeOnClickOutside
					disableScroll
					header={Liferay.Language.get('inheritance')}
					onShowChange={setShowPopover}
					show={showPopover}
					trigger={
						<ClayIcon
							className="field-base-tooltip-icon"
							symbol="question-circle-full"
						/>
					}
				>
					{Liferay.Language.get(
						'enable-inheritance-to-share-settings-between-related-data-models'
					)}
				</ClayPopover>
			</div>
		</>
	);
}

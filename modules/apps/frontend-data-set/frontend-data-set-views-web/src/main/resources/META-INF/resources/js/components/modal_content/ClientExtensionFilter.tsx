/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import classNames from 'classnames';
import {IClientExtensionRenderer} from 'frontend-js-web';
import React from 'react';

function Header() {
	return <>{Liferay.Language.get('new-client-extension-filter')}</>;
}

interface IBodyProps {
	fdsFilterClientExtensions: IClientExtensionRenderer[];
	namespace: string;
	onSelectedClientExtensionChange: (val: IClientExtensionRenderer) => void;
	selectedClientExtension?: IClientExtensionRenderer;
}

function Body({
	fdsFilterClientExtensions,
	namespace,
	onSelectedClientExtensionChange,
	selectedClientExtension,
}: IBodyProps) {
	const fdsFilterClientExtensionFormElementId = `${namespace}fdsFilterClientExtensionERC`;

	if (!fdsFilterClientExtensions.length) {
		return (
			<ClayAlert displayType="info" title="Info">
				{Liferay.Language.get(
					'no-client-extensions-are-available.-create-a-frontend-data-set-filter-in-order-to-use-it'
				)}
			</ClayAlert>
		);
	}

	return (
		<ClayForm.Group className="form-group-autofit">
			<div className={classNames('form-group-item')}>
				<label htmlFor={fdsFilterClientExtensionFormElementId}>
					{Liferay.Language.get('client-extension')}
				</label>

				<ClayDropDown
					closeOnClick
					menuElementAttrs={{
						className: 'fds-cell-renderers-dropdown-menu',
					}}
					trigger={
						<ClayButton
							aria-labelledby={`${namespace}cellRenderersLabel`}
							className="form-control form-control-select form-control-select-secondary"
							displayType="secondary"
							name={fdsFilterClientExtensionFormElementId}
						>
							{selectedClientExtension
								? selectedClientExtension.name
								: Liferay.Language.get('select')}
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList
						items={fdsFilterClientExtensions}
						role="listbox"
					>
						{fdsFilterClientExtensions.map(
							(
								filterClientExtension: IClientExtensionRenderer
							) => (
								<ClayDropDown.Item
									className="align-items-center d-flex justify-content-between"
									key={filterClientExtension.name}
									onClick={() =>
										onSelectedClientExtensionChange(
											filterClientExtension
										)
									}
									roleItem="option"
								>
									{filterClientExtension.name}
								</ClayDropDown.Item>
							)
						)}
					</ClayDropDown.ItemList>
				</ClayDropDown>
			</div>
		</ClayForm.Group>
	);
}

export default {
	Body,
	Header,
};

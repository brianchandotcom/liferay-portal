/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ButtonWithIcon} from '@clayui/core';
import i18n from '../../../../../../../../../../common/I18n';
import {ButtonDropDown} from '../../../../../../../../../../common/components';
import MenuUserActions from './components/MenuUserActions';

const OptionsColumn = ({edit, onCancel, onEdit, onRemove, onSave}) => {
	const userOptions = [
		{
			label: i18n.translate('edit'),
			onClick: () => onEdit(),
		},
		{
			customOptionStyle: 'cp-remove-member-option',
			label: i18n.translate('remove'),
			onClick: () => onRemove(),
		},
	];

	return edit ? (
		<MenuUserActions onCancel={() => onCancel()} onSave={() => onSave()} />
	) : (
		<ButtonDropDown
			customDropDownButton={
				<ButtonWithIcon displayType="null" small symbol="ellipsis-v" />
			}
			items={userOptions}
			menuElementAttrs={{
				className: 'p-0',
			}}
		/>
	);
};

export default OptionsColumn;

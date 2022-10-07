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

import {Button} from '@clayui/core';
import i18n from '../../../../../../../../../../../../common/I18n';

const MenuUserActions = ({onCancel, onSave}) => (
	<div className="align-items-center d-flex">
		<Button
			className="mr-2"
			displayType="secondary"
			onClick={onCancel}
			small
		>
			{i18n.translate('cancel')}
		</Button>

		<Button onClick={() => onSave} small>
			{i18n.translate('save')}
		</Button>
	</div>
);

export default MenuUserActions;

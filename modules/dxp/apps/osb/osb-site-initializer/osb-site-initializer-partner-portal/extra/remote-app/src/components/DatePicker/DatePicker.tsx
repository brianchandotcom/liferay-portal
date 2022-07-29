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

import ClayDatePicker from '@clayui/date-picker';
import {ClayIconSpriteContext} from '@clayui/icon';

import getIconSpriteMap from '../../utils/getIconSpriteMap';

type Props = {
	dateFormat: string;
	label: string;
	name: string;
	onChange: any;
	placeholder: string;
	value: any;
	years: any;
};

const DatePicker = ({label, ...props}: Props) => {
	return (
		<>
			<label>{label}</label>
			<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
				<ClayDatePicker {...props} />
			</ClayIconSpriteContext.Provider>
		</>
	);
};

export default DatePicker;
